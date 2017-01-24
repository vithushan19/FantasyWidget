package com.cardillsports.fantasystats.fantasyv.service;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.cardillsports.fantasystats.R;
import com.cardillsports.fantasystats.SimpleWidgetProvider;
import com.cardillsports.fantasystats.fantasyv.model.Scoreboard;
import com.cardillsports.fantasystats.fantasyv.model.User;
import com.cardillsports.fantasystats.fantasyv.model.UserTeam;
import com.cardillsports.fantasystats.fantasyv.network.NetworkUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

/**
 * Created by vithushan on 1/23/17.
 */

public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
        private List<Scoreboard> mWidgetItems = new ArrayList<Scoreboard>();
        private Context mContext;
        private int mAppWidgetId;

        public StackRemoteViewsFactory(Context context, Intent intent) {
            mContext = context;
            mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        public void onCreate() {
            // In onCreate() you setup any connections / cursors to your data source. Heavy lifting,
            // for example downloading or creating content etc, should be deferred to onDataSetChanged()
            // or getViewAt(). Taking more than 20 seconds in this call will result in an ANR.
            for (int i = 0; i < 1; i++) {
                mWidgetItems.add(new Scoreboard(0, null));
            }
            // We sleep for 3 seconds here to show how the empty view appears in the interim.
            // The empty view is set in the StackWidgetProvider and should be a sibling of the
            // collection view.
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void onDestroy() {
            // In onDestroy() you should tear down anything that was setup for your data source,
            // eg. cursors, connections, etc.
            mWidgetItems.clear();
        }

        public int getCount() {
            return mWidgetItems.size();
        }

        public RemoteViews getViewAt(int position) {
            // position will always range from 0 to getCount() - 1.
            // We construct a remote views item based on our widget item xml file, and set the
            // text based on the position.
            RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_main);

            String name = "EMPTY";
            String name2 = "EMPTY";
            String score1 = "9";
            String score2 = "9";
            try {
                name = mWidgetItems.get(position).matchupList.get(0).matchupTeamList.get(0).name;
                name2 = mWidgetItems.get(position).matchupList.get(0).matchupTeamList.get(1).name;
                score1 = String.valueOf(mWidgetItems.get(position).matchupList.get(0).matchupTeamList.get(0).score);
                score2 = String.valueOf(mWidgetItems.get(position).matchupList.get(0).matchupTeamList.get(1).score);
            } catch (NullPointerException | IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            rv.setTextViewText(R.id.player1Name, name);
            rv.setTextViewText(R.id.player2Name, name2);
            rv.setTextViewText(R.id.player1Score, score1);
            rv.setTextViewText(R.id.player2Score, score2);
            // Next, we set a fill-intent which will be used to fill-in the pending intent template
            // which is set on the collection view in StackWidgetProvider.
            Bundle extras = new Bundle();
            extras.putInt(SimpleWidgetProvider.EXTRA_ITEM, position);
            Intent fillInIntent = new Intent();
            fillInIntent.putExtras(extras);
            rv.setOnClickFillInIntent(R.id.actionButton, fillInIntent);
            // You can do heaving lifting in here, synchronously. For example, if you need to
            // process an image, fetch something from the network, etc., it is ok to do it here,
            // synchronously. A loading view will show up in lieu of the actual contents in the
            // interim.
            try {
                System.out.println("Loading view " + position);
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Return the remote views object.
            return rv;
        }

        public RemoteViews getLoadingView() {
            // You can create a custom loading view (for instance when getViewAt() is slow.) If you
            // return null here, you will get the default loading view.
            return null;
        }

        public int getViewTypeCount() {
            return 1;
        }

        public long getItemId(int position) {
            return position;
        }

        public boolean hasStableIds() {
            return true;
        }

        public void onDataSetChanged() {
            // This is triggered when you call AppWidgetManager notifyAppWidgetViewDataChanged
            // on the collection view corresponding to this factory. You can do heaving lifting in
            // here, synchronously. For example, if you need to process an image, fetch something
            // from the network, etc., it is ok to do it here, synchronously. The widget will remain
            // in its current state while work is being done here, so you don't need to worry about
            // locking up the widget.
            try {
                Log.d("VITHUSHAN", "DOWNLOADING USERS");
                User user = NetworkUtil.getInstance().getUser("");
                if (user != null) {
                    mWidgetItems.clear();
                    for (UserTeam team : user.userTeamList) {
                        mWidgetItems.add(NetworkUtil.getInstance().getScoreboard("", team.leagueKey));
                    }
                }

            } catch (OAuthMessageSignerException e) {
                e.printStackTrace();
            } catch (OAuthExpectationFailedException e) {
                e.printStackTrace();
            } catch (OAuthCommunicationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (OAuthNotAuthorizedException e) {
                e.printStackTrace();
            }
        }
    }
}
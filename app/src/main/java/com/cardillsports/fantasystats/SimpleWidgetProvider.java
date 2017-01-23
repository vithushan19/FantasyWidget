package com.cardillsports.fantasystats;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;

import com.cardillsports.fantasystats.fantasyv.network.NetworkUtil;

import java.io.IOException;
import java.util.Random;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

/**
 * Created by vithushan on 1/22/17.
 */

public class SimpleWidgetProvider extends AppWidgetProvider {

    private static final String ACTION_CLICK = "ACTION_CLICK";

    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager,
                         final int[] appWidgetIds) {

        // Get all ids
        ComponentName thisWidget = new ComponentName(context, SimpleWidgetProvider.class);

        // If the user has multiple widgets on screen, we need to update all of them
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        for (final int widgetId : allWidgetIds) {
            // create some random data
            final int number = (new Random().nextInt(100));

            new AsyncTask<Void, Void, String>() {

                @Override
                protected String doInBackground(Void... voids) {
                    String scoreboardString = "";
                    try {
                        scoreboardString = NetworkUtil.getInstance().getScoreboard("");
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

                    return scoreboardString;
                }

                @Override
                protected void onPostExecute(String scoreboardString) {
                    RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                            R.layout.widget_main);
                    Log.w("WidgetExample", String.valueOf(number));
                    // Set the text
                    remoteViews.setTextViewText(R.id.textView, String.valueOf(scoreboardString));

                    // Register an onClickListener
                    Intent intent = new Intent(context, SimpleWidgetProvider.class);

                    intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                            0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    remoteViews.setOnClickPendingIntent(R.id.actionButton, pendingIntent);
                    appWidgetManager.updateAppWidget(widgetId, remoteViews);
                }
            }.execute();
        }
    }
}
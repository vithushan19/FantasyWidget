package com.cardillsports.fantasystats.fantasyv.activity;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.cardillsports.fantasystats.fantasyv.model.User;
import com.cardillsports.fantasystats.fantasyv.model.UserTeam;
import com.cardillsports.fantasystats.fantasyv.network.NetworkUtil;

import java.io.IOException;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

/**
 * Created by vithushan on 1/23/17.
 */

public class ConfigurationActivity extends Activity {
    static final String TAG = "ExampleAppWidgetConfigure";

    private static final String PREFS_NAME
            = "com.example.android.apis.appwidget.ExampleAppWidgetProvider";
    private static final String PREF_PREFIX_KEY = "prefix_";

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    EditText mAppWidgetPrefix;

    public ConfigurationActivity() {
        super();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if they press the back button.
        setResult(RESULT_CANCELED);

        final LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams linLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        new AsyncTask<Void, Void, User>() {

            @Override
            protected User doInBackground(Void... voids) {
                try {
                    return NetworkUtil.getInstance().getUser("");
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
                return null;
            }

            @Override
            protected void onPostExecute(User user) {
                if (user == null) return;

                for (UserTeam team : user.userTeamList) {
                    Switch switchWidget = new Switch(ConfigurationActivity.this);
                    switchWidget.setText(team.teamName);
                    linearLayout.addView(switchWidget);
                }

                Button button = new Button(ConfigurationActivity.this);
                button.setText("SAVE");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setResult(RESULT_OK, null);
                        finish();
                    }
                });
                linearLayout.addView(button);
            }
        }.execute();

        // Set the view layout resource to use.
        setContentView(linearLayout, linLayoutParams);

        /*// Find the EditText
        mAppWidgetPrefix = (EditText)findViewById(R.id.appwidget_prefix);

        // Bind the action for the save button.
        findViewById(R.id.save_button).setOnClickListener(mOnClickListener);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If they gave us an intent without the widget id, just bail.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

        mAppWidgetPrefix.setText(loadTitlePref(ConfigurationActivity.this, mAppWidgetId));*/
    }

   /* View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = ConfigurationActivity.this;

            // When the button is clicked, save the string in our prefs and return that they
            // clicked OK.
            String titlePrefix = mAppWidgetPrefix.getText().toString();
            saveTitlePref(context, mAppWidgetId, titlePrefix);

            // Push widget update to surface with newly set prefix
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ExampleAppWidgetProvider.updateAppWidget(context, appWidgetManager,
                    mAppWidgetId, titlePrefix);

            // Make sure we pass back the original appWidgetId
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        }
    };

    // Write the prefix to the SharedPreferences object for this widget
    static void saveTitlePref(Context context, int appWidgetId, String text) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId, text);
        prefs.commit();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static String loadTitlePref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String prefix = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null);
        if (prefix != null) {
            return prefix;
        } else {
            return context.getString(R.string.appwidget_prefix_default);
        }
    }

    static void deleteTitlePref(Context context, int appWidgetId) {
    }

    static void loadAllTitlePrefs(Context context, ArrayList<Integer> appWidgetIds,
                                  ArrayList<String> texts) {
    }*/
}

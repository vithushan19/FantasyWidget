package com.cardillsports.fantasystats.fantasyv.asynctasks;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.cardillsports.fantasystats.fantasyv.activity.WebViewActivity;
import com.cardillsports.fantasystats.fantasyv.network.NetworkUtil;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

/**
 * Created by vithushan on 1/23/17.
 */

/**
 * Executing this task creates a request token and uses that token
 * to create a url for the user to navigate to, in order to authorize
 * our application. In post excecute we pop open a webview with that URL
 */
public class LoginTask extends AsyncTask<Void, Void, String> {

    private final Context mContext;

    public LoginTask(Context context) {
        mContext = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            return NetworkUtil.getInstance().getRequestToken();
        } catch (OAuthMessageSignerException e) {
            e.printStackTrace();
        } catch (OAuthNotAuthorizedException e) {
            e.printStackTrace();
        } catch (OAuthExpectationFailedException e) {
            e.printStackTrace();
        } catch (OAuthCommunicationException e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

    @Override
    protected void onPostExecute(String result) {
        Intent intent = new Intent(mContext, WebViewActivity.class);
        intent.setData(Uri.parse(result));
        mContext.startActivity(intent);
    }
}

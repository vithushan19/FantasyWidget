package com.cardillsports.fantasystats.fantasyv.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import com.cardillsports.fantasystats.fantasyv.network.NetworkUtil;

import java.io.IOException;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

/**
 * Created by vithushan on 1/23/17.
 */

public class GetScoreboardTask extends AsyncTask<Void, Void, String> {

    private final String mVerifierUrl;

    public GetScoreboardTask(String verifierUrl) {
        mVerifierUrl = verifierUrl;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            return NetworkUtil.getInstance().getScoreboard(mVerifierUrl);
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
        return "ERROR";
    }

    @Override
    protected void onPostExecute(String scoreboardString) {
        Log.d("VITHUSHAN", scoreboardString);
    }
}

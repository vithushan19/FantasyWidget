package com.cardillsports.fantasystats.fantasyv.asynctasks;

import android.os.AsyncTask;

import com.cardillsports.fantasystats.fantasyv.model.Scoreboard;
import com.cardillsports.fantasystats.fantasyv.model.ScoreboardHandler;
import com.cardillsports.fantasystats.fantasyv.network.NetworkUtil;

import java.io.IOException;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

/**
 * Created by vithushan on 1/23/17.
 */

public class GetScoreboardTask extends AsyncTask<Void, Void, Scoreboard> {

    private final String mVerifierUrl;
    private final ScoreboardHandler mHandler;
    private final String mLeagueKey;


    public GetScoreboardTask(ScoreboardHandler handler, String verifierUrl, String leagueKey) {
        mHandler = handler;
        mVerifierUrl = verifierUrl;
        mLeagueKey = leagueKey;
    }

    @Override
    protected Scoreboard doInBackground(Void... voids) {
        try {
            return NetworkUtil.getInstance().getScoreboard(mVerifierUrl, mLeagueKey);
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
    protected void onPostExecute(Scoreboard scoreboard) {
        mHandler.handleScoreboard(scoreboard);
    }
}

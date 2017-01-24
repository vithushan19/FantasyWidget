package com.cardillsports.fantasystats.fantasyv.asynctasks;

import android.os.AsyncTask;

import com.cardillsports.fantasystats.fantasyv.model.User;
import com.cardillsports.fantasystats.fantasyv.network.NetworkUtil;

import java.io.IOException;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

/**
 * Created by vithushan on 1/23/17.
 */

public class GetUserTask extends AsyncTask<Void, Void, User> {

    private final String mVerifierUrl;

    public GetUserTask(String verifierUrl) {
        mVerifierUrl = verifierUrl;
    }

    @Override
    protected User doInBackground(Void... voids) {
        try {
            return NetworkUtil.getInstance().getUser(mVerifierUrl);
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
    protected void onPostExecute(User result) {
        System.out.println(result.userTeamList.get(0).teamName);
    }
}

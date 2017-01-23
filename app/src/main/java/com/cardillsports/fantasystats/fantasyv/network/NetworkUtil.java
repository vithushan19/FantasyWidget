package com.cardillsports.fantasystats.fantasyv.network;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

public class NetworkUtil {

    private final OkHttpClient client;
    private OkHttpOAuthConsumer mainConsumer; // Our app is the Oaauth consumer
	private CommonsHttpOAuthProvider mainProvider; // Yahoo is the OAuth provider
	private String oAuthVerifier;

	private static NetworkUtil mInstance;
	private static final String REQUEST_TOKEN_ENDPOINT_URL = "https://api.login.yahoo.com/oauth/v2/get_request_token";
	private static final String AUTHORIZE_WEBSITE_URL = "https://api.login.yahoo.com/oauth/v2/request_auth";
	private static final String ACCESS_TOKEN_ENDPOINT_URL = "https://api.login.yahoo.com/oauth/v2/get_token";

	private static final String YAHOO_CALLBACK_URL = "http://vithushan.com";
	private static final String YAHOO_CONSUMER_KEY = "dj0yJmk9SG1OeGxuQUhCdWxKJmQ9WVdrOVdGWkJOSEJDTldFbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD1lNw--";
	private static final String YAHOO_CONSUMER_SECRET = "8e6b6c1e5f2d1f4e3b4fd74d428ae38c7a02a8e9";

    private static final String YAHOO_SCOREBOARD_URL = "http://fantasysports.yahooapis.com/fantasy/v2/league/364.l.49207/scoreboard?format=json";

	private static final String TAG = "NetworkUtil";

	private NetworkUtil() {

        mainConsumer = new OkHttpOAuthConsumer(YAHOO_CONSUMER_KEY, YAHOO_CONSUMER_SECRET);

        client = new OkHttpClient.Builder()
                .addInterceptor(new SigningInterceptor(mainConsumer))
                .build();

      	mainProvider = new CommonsHttpOAuthProvider(REQUEST_TOKEN_ENDPOINT_URL,
				ACCESS_TOKEN_ENDPOINT_URL, AUTHORIZE_WEBSITE_URL);
		mainProvider.setOAuth10a(false);
	}

    // Used the singleton pattern to make sure multiple http clients weren't instatiated
    // TODO This is bad, change to using dagger to manage dependencies
	public static NetworkUtil getInstance() {
		if (mInstance == null) {
			mInstance = new NetworkUtil();
		}
		return mInstance;
	}

	private void getAccessToken(String verifierUrl) throws OAuthMessageSignerException,
            OAuthNotAuthorizedException, OAuthExpectationFailedException,
            OAuthCommunicationException {
		Uri uriData = Uri.parse(verifierUrl);
		this.oAuthVerifier = uriData.getQueryParameter("oauth_verifier");
		mainProvider.retrieveAccessToken(mainConsumer, oAuthVerifier);
	}

	public String getScoreboard(String verifierURL) throws OAuthMessageSignerException,
			OAuthExpectationFailedException, OAuthCommunicationException, IOException, OAuthNotAuthorizedException {

        if (!verifierURL.equals("")) {
            getAccessToken(verifierURL);
        }

		URL url = new URL(YAHOO_SCOREBOARD_URL);
		OAuthConsumer consumer = mainConsumer;

        Request request2 = new Request.Builder().url(url).build();
		consumer.sign(request2);

        Call call = client.newCall(request2);
        Response response = call.execute();

        //TODO parse into java object
        String responseBody = new String(response.body().bytes(), "UTF-8");

        return responseBody;
	}


    /**
     *
     * @return The URL that the user has to go to authorize our application
     * @throws OAuthMessageSignerException
     * @throws OAuthNotAuthorizedException
     * @throws OAuthExpectationFailedException
     * @throws OAuthCommunicationException
     */
	public String getRequestToken() throws OAuthMessageSignerException,
			OAuthNotAuthorizedException, OAuthExpectationFailedException,
			OAuthCommunicationException {

		Log.i(TAG, "Retrieving request token from Yahoo servers");
		final String url = mainProvider.retrieveRequestToken(mainConsumer, YAHOO_CALLBACK_URL);
		Log.i(TAG, "Popping a browser with the authorize URL : " + url);

		return url;
	}

    private void showToken() {
        // Log.d("SubPlurkV2", "Token = " + mainConsumer.getToken() +
        // " and secret = " + mainConsumer.getTokenSecret());
        String str = "verifier = "
                + this.oAuthVerifier
                + "<br>"
                + "Token = "
                + mainConsumer.getToken()
                + "<br>"
                + "secret = "
                + mainConsumer.getTokenSecret()
                + "<br>"
                + "oauth_expires_in = "
                + mainProvider.getResponseParameters().getFirst(
                "oauth_expires_in")
                + "<br>"
                + "oauth_session_handle = "
                + mainProvider.getResponseParameters().getFirst(
                "oauth_session_handle")
                + "<br>"
                + "oauth_authorization_expires_in = "
                + mainProvider.getResponseParameters().getFirst(
                "oauth_authorization_expires_in")
                + "<br>"
                + "xoauth_yahoo_guid = "
                + mainProvider.getResponseParameters().getFirst(
                "xoauth_yahoo_guid") + "<br>";
        Log.i("YahooScreen", "str : " + str);
    }
}

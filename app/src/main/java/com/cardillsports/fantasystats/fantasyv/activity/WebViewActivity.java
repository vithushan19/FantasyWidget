package com.cardillsports.fantasystats.fantasyv.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cardillsports.fantasystats.R;


public class WebViewActivity extends Activity {

	private WebView webView1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);

		webView1 = (WebView) findViewById(R.id.webview);
		webView1.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // After the user authorizes they'll be redirected to the YAHOO_CALLBACK_URL
                // We intercept that redirection and open the LoginActivity instead
                // We pass this CALLBACK_URL back to the LoginActivty because Yahoo has appended
                // a verifier parameters that prove that the user authorized our app
                // We use the verifier to make authenticated calls to Yahoo
				if (url.startsWith("vithushan")  || url.startsWith("http://vithushan")) {
					Intent verifierIntent = new Intent(getApplicationContext(), LoginActivity.class);
					verifierIntent.putExtra("url", url);
					startActivity(verifierIntent);
					return true;
				} else {
					return false;
				}
			}
		});

        // Navigate to authorization url
		Intent intent = getIntent();
		if (intent != null) {
            String authorizationUrl = intent.getData().toString();
			webView1.loadUrl(authorizationUrl);
		}
	}
}
package com.cardillsports.fantasystats.fantasyv.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.cardillsports.fantasystats.R;
import com.cardillsports.fantasystats.fantasyv.asynctasks.GetScoreboardTask;
import com.cardillsports.fantasystats.fantasyv.asynctasks.LoginTask;

public class LoginActivity extends Activity {

	private Button button1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Login
		button1 = (Button) this.findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
            new LoginTask(getApplicationContext()).execute();
			}
		});

        Intent intent = getIntent();
        final String verifierUrl = intent.getStringExtra("url");
        if (verifierUrl != null) {
            // We can only make this call if we are authenticated and have a verifier url
            // that was passed back from the YAHOO_CALLBACK_URL when the user authorized our app
            new GetScoreboardTask(verifierUrl).execute();
        }
	}

}
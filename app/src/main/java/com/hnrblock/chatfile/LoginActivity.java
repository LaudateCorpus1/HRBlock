package com.hnrblock.chatfile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText emailIdEdit, passEdit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		emailIdEdit = (EditText) findViewById(R.id.email_id);
		passEdit = (EditText) findViewById(R.id.password);
	}

	public void submitLogin(View view) {
		if (emailValidator(emailIdEdit.getText().toString())) {
			// login
			DatabaseHelper helper = new DatabaseHelper(this);
			helper.addLogin(emailIdEdit.getText().toString());
			// showToast("Logged in");
			startChatActivity();
		} else {
			showToast("Please enter valid email");
		}
	}

	private void startChatActivity() {
		Intent loginIntent = new Intent(this, ChatActivity.class);
		startActivity(loginIntent);
		this.finish();
	}

	public boolean emailValidator(String email) {
		Pattern pattern;
		Matcher matcher;
		final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email);
		return matcher.matches();
	}

	private void showToast(final String text) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(LoginActivity.this, text, Toast.LENGTH_SHORT)
						.show();
			}
		});
	}
}

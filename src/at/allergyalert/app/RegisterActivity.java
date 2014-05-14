package at.allergyalert.app;

import android.app.Activity;
import android.graphics.PathMeasure;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import at.allergyalert.R;
import at.allergyalert.app.web.*;
import at.allergyalert.app.web.API.APIUserCallback;
import at.allergyalert.app.utility.*;
import at.allergyalert.app.model.*;

public class RegisterActivity extends Activity {

	protected static final String TAG = "REGISTER";

	private EditText mTextUsername;
	private EditText mTextPassword;
	private EditText mTextPasswordRepeat;
	private EditText mTextEmail;
	private EditText mTextFirstname;
	private EditText mTextLastname;
	private EditText mTextStreet;
	private EditText mTextCity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		mTextUsername = (EditText) findViewById(R.id.register_textUsername);
		mTextPassword = (EditText) findViewById(R.id.register_textPassword);
		mTextPasswordRepeat = (EditText) findViewById(R.id.register_textPasswordRepeat);
		mTextEmail = (EditText) findViewById(R.id.register_textEmail);
		mTextFirstname = (EditText) findViewById(R.id.register_textFirstname);
		mTextLastname = (EditText) findViewById(R.id.register_textLastname);
		mTextStreet = (EditText) findViewById(R.id.register_textStreet);
		mTextCity = (EditText) findViewById(R.id.register_textCity);

		findViewById(R.id.register_buttonRegister).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						doRegister();
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	private void doRegister() {
		boolean valid = true;
		RegisterDataBundle bundle = new RegisterDataBundle();

		final String sUsername = mTextUsername.getText().toString();
		final String sPassword = mTextPassword.getText().toString();
		String sPasswordRepeat = mTextPasswordRepeat.getText().toString();
		String sEmail = mTextEmail.getText().toString();
		String sFirstname = mTextFirstname.getText().toString();
		String sLastname = mTextLastname.getText().toString();
		String sStreet = mTextStreet.getText().toString();
		String sCity = mTextCity.getText().toString();

		if (!TextUtils.isEmpty(sUsername)) {
			bundle.username = sUsername;
		} else {
			mTextUsername.setError(getString(R.string.error_field_required));
			valid = false;
		}

		if (!TextUtils.isEmpty(sPassword)
				&& !TextUtils.isEmpty(sPasswordRepeat)
				&& TextUtils.equals(sPasswordRepeat, sPassword)) {
			bundle.password = sPassword;
			bundle.repeat = sPassword;
		} else {
			mTextPassword
					.setError(getString(R.string.error_incorrect_password));
			mTextPasswordRepeat
					.setError(getString(R.string.error_incorrect_password));
			valid = false;
		}

		if (!TextUtils.isEmpty(sEmail)) {
			bundle.email = sEmail;
		} else {
			mTextEmail.setError(getString(R.string.error_field_required));
			valid = false;
		}

		if (!TextUtils.isEmpty(sFirstname)) {
			bundle.firstname = sFirstname;
		} else {
			mTextFirstname.setError(getString(R.string.error_field_required));
			valid = false;
		}

		if (!TextUtils.isEmpty(sLastname)) {
			bundle.lastname = sLastname;
		} else {
			mTextLastname.setError(getString(R.string.error_field_required));
			valid = false;
		}

		if (!TextUtils.isEmpty(sStreet)) {
			bundle.street = sStreet;
		} else {
			mTextStreet.setError(getString(R.string.error_field_required));
			valid = false;
		}

		if (!TextUtils.isEmpty(sCity)) {
			bundle.city = sCity;
		} else {
			mTextCity.setError(getString(R.string.error_field_required));
			valid = false;
		}

		if (valid) {
			API.getInstance().register(bundle, new APIUserCallback() {

				@Override
				public void onSuccess(User u) {
					try {
						Credentials c = new Credentials(sUsername, sPassword);
						XML.Write(
								c,
								PathManager
										.getAbsoluteFilePath(PathManager.FILE_CREDENTIALS));
					} catch (Exception e) {
						Log.d(TAG, "No writing here!");
					}
					Utility.switchToActivity(AllergyActivity.class,
							getApplicationContext());
					Log.d(TAG, "Success!");
				}

				@Override
				public void onError() {
					Log.d(TAG, "FAIL!");
				}
			});
		}
	}
}

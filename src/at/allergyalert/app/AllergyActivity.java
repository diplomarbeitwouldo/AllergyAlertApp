package at.allergyalert.app;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import at.allergyalert.R;
import at.allergyalert.app.model.Credentials;
import at.allergyalert.app.model.User;
import at.allergyalert.app.utility.PathManager;
import at.allergyalert.app.utility.XML;
import at.allergyalert.app.utility.Utility;
import at.allergyalert.app.web.API;
import at.allergyalert.app.web.API.APIUserCallback;

public class AllergyActivity extends Activity {

	protected static final String TAG = "ADDALLERGY";
	EditText mTextAllergy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_allergy);

		mTextAllergy = (EditText) findViewById(R.id.allergy_textAllergy);

		findViewById(R.id.allergy_buttonAdd).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						addAllergy(false);
					}
				});

		findViewById(R.id.allergy_buttonFinish).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						addAllergy(true);
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	public void addAllergy(final boolean end) {
		String sAllergy = mTextAllergy.getText().toString();

		if (!TextUtils.isEmpty(sAllergy)) {
			try {
				Credentials c = (Credentials) XML
						.Read(Credentials.class,
								PathManager
										.getAbsoluteFilePath(PathManager.FILE_CREDENTIALS));
				API.getInstance().addAllergy(c, sAllergy,
						new APIUserCallback() {

							@Override
							public void onSuccess(User u) {

								if (end) {
									try {
										XML.Write(
												u,
												PathManager
														.getAbsoluteFilePath(PathManager.FILE_USER));
									} catch (Exception e) {
										Log.d(TAG, "User Write Error!");
									}
									Utility.switchToActivity(
											MainActivity.class,
											getApplicationContext());
								}
							}

							@Override
							public void onError() {
								Log.d(TAG, "Couldn't add Allergy!");
							}
						});
			} catch (Exception e) {
				Log.d(TAG, "Credentials Read Error!");
			}
			mTextAllergy.setText("");
		} else {
			mTextAllergy.setError(getString(R.string.error_field_required));
		}

	}
}

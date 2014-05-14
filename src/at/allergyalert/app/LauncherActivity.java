package at.allergyalert.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import at.allergyalert.R;
import at.allergyalert.app.model.Credentials;
import at.allergyalert.app.model.User;
import at.allergyalert.app.utility.PathManager;
import at.allergyalert.app.utility.Utility;
import at.allergyalert.app.utility.XML;
import at.allergyalert.app.web.API;
import at.allergyalert.app.web.API.APIUserCallback;

public class LauncherActivity extends Activity {

	protected static final String TAG = "LAUNCHER";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher);
		getActionBar().hide();
		try {
			Credentials c = (Credentials) XML.Read(Credentials.class,
					PathManager
							.getAbsoluteFilePath(PathManager.FILE_CREDENTIALS));
			API.getInstance().login(c, new APIUserCallback() {

				@Override
				public void onSuccess(User u) {
					try {
						XML.Write(u, PathManager
								.getAbsoluteFilePath(PathManager.FILE_USER));
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Utility.switchToActivity(MainActivity.class,
										getApplicationContext());
							}
						});
					} catch (Exception e) {
						Log.d(TAG, e.getMessage());
					}
				}

				@Override
				public void onError() {
					try {
						XML.Read(User.class, PathManager
								.getAbsoluteFilePath(PathManager.FILE_USER));

						Utility.switchToActivity(MainActivity.class,
								getApplicationContext());
					} catch (Exception e) {
						Utility.switchToActivity(LoginActivity.class,
								getApplicationContext());
					}
				}
			});
		} catch (Exception e) {
			Utility.switchToActivity(LoginActivity.class,
					getApplicationContext());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

}

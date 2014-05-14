package at.allergyalert.app;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Path;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import at.allergyalert.R;
import at.allergyalert.app.model.Allergy;
import at.allergyalert.app.model.User;
import at.allergyalert.app.utility.PathManager;
import at.allergyalert.app.utility.Utility;
import at.allergyalert.app.utility.XML;

public class MainActivity extends Activity {

	private static final String TAG = "MAIN";
	TextView mTextFirstname;
	TextView mTextLastname;
	TextView mTextStreet;
	TextView mTextCity;
	LinearLayout mList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try {
			User u = (User) XML.Read(User.class,
					PathManager.getAbsoluteFilePath(PathManager.FILE_USER));

			mTextFirstname = (TextView) findViewById(R.id.textFirstname);
			mTextLastname = (TextView) findViewById(R.id.textLastname);
			mTextStreet = (TextView) findViewById(R.id.textStreet);
			mTextCity = (TextView) findViewById(R.id.textCity);

			mTextFirstname.setText(u.getFirstname());
			mTextLastname.setText(u.getLastname());
			mTextStreet.setText(u.getStreet());
			mTextCity.setText(u.getCity());

			mList = (LinearLayout) findViewById(R.id.argList);

			for (Allergy a : u.getAllergies()) {
				Log.d(TAG, a.getName());
				/*
				 * TextView view = (TextView) LayoutInflater.from(
				 * getApplicationContext()).inflate( R.layout.allergylist_item,
				 * null);
				 */
				TextView view = new TextView(this);
				view.setText(a.getName());
				mList.addView(view);
			}
			mList.invalidate();
		} catch (Exception e) {
			Log.d(TAG, "User XML doesn't exist");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_addAllergy:
			Utility.switchToActivity(AllergyActivity.class, this);
			return true;
		case R.id.action_logout:
			XML.Delete(PathManager
					.getAbsoluteFilePath(PathManager.FILE_CREDENTIALS));
			XML.Delete(PathManager.getAbsoluteFilePath(PathManager.FILE_USER));
			Utility.switchToActivity(LauncherActivity.class, this);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}

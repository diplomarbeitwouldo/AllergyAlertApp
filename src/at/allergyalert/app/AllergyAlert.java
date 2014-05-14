package at.allergyalert.app;

import android.app.Application;
import android.content.Context;

public class AllergyAlert extends Application {
	private static Context context;

	public void onCreate() {
		super.onCreate();
		AllergyAlert.context = getApplicationContext();
	}

	public static Context getAppContext() {
		return AllergyAlert.context;
	}
}
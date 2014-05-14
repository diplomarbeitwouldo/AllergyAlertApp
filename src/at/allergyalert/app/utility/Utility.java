package at.allergyalert.app.utility;

import android.content.Context;
import android.content.Intent;

public class Utility {

	public static void switchToActivity(Class<?> c, Context context) {
		switchToActivity(c, context, true);
	}

	public static void switchToActivity(Class<?> c, Context context,
			boolean clear) {
		Intent intent = new Intent(context, c);
		if (clear) {
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_NEW_TASK);
		}
		context.startActivity(intent);
	}
}

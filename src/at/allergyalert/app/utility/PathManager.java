package at.allergyalert.app.utility;

import java.io.File;

import at.allergyalert.app.AllergyAlert;

public class PathManager {
	public static final String FILE_CONFIG = "config.xml";
	public static final String FILE_CREDENTIALS = "Credentials.xml";
	public static final String FILE_USER = "user.xml";

	public static File getAbsoluteFilePath(String filename) {
		return new File(AllergyAlert.getAppContext().getFilesDir().getPath(),
				filename);
	}
}

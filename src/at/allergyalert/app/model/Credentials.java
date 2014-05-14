package at.allergyalert.app.model;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Credentials")
public class Credentials {
	@Element(name = "username")
	private String username;
	@Element(name = "password")
	private String password;

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Credentials(@Element(name = "username") String username,
			@Element(name = "password") String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public ArrayList<NameValuePair> getHttpParameter() {
		ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("username", username));
		pairs.add(new BasicNameValuePair("password", password));
		return pairs;
	}
}

package at.allergyalert.app.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class RegisterDataBundle {
	public String username;
	public String password;
	public String repeat;
	public String firstname;
	public String lastname;
	public String street;
	public String city;
	public String email;

	public List<NameValuePair> getHttpParameter() {
		ArrayList<NameValuePair> args = new ArrayList<NameValuePair>();
		args.add(new BasicNameValuePair("username", username));
		args.add(new BasicNameValuePair("password", password));
		args.add(new BasicNameValuePair("repeat", repeat));
		args.add(new BasicNameValuePair("firstname", firstname));
		args.add(new BasicNameValuePair("lastname", lastname));
		args.add(new BasicNameValuePair("street", street));
		args.add(new BasicNameValuePair("city", city));
		args.add(new BasicNameValuePair("email", email));
		return args;
	}
}

package at.allergyalert.app.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.util.Log;
import at.allergyalert.app.model.Credentials;
import at.allergyalert.app.model.User;

public class API {
	public static final String API_PATIENT = "/Patient";
	public static final String API_LOGIN = "/Login";
	public static final String API_REGISTER = "/Register";

	public static final String TAG = "API";

	private String url;
	private Credentials c;
	private static API instance;

	private API() {
		this.url = "http://192.168.0.30:8080/AllergyAlertServer";
	}

	public static API getInstance() {
		if (instance == null)
			instance = new API();
		return instance;
	}

	public Credentials getCredentials() {
		return this.c;
	}

	public void login(final Credentials c, final APIUserCallback callback) {
		this.c = c;
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					JSONObject o = request(API_LOGIN, c.getHttpParameter());
					if (callback != null)
						callback.onSuccess(new User(o.getJSONObject("patient")));
				} catch (Exception e) {
					if (callback != null)
						callback.onError();
				}
			}
		}).start();
	}

	public void dataRequest(final Credentials c, final APIUserCallback callback) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					JSONObject o = request(API_LOGIN, c.getHttpParameter());
					if (callback != null)
						callback.onSuccess(new User(o));
				} catch (Exception e) {
					if (callback != null)
						callback.onError();
				}

			}
		}).start();

	}

	public void register(final RegisterDataBundle bundle,
			final APIUserCallback callback) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				JSONObject o;
				try {
					o = request(API_REGISTER, bundle.getHttpParameter());
					if (callback != null)
						callback.onSuccess(new User(o.getJSONObject("user")));
				} catch (Exception e) {
					if (callback != null)
						callback.onError();
				}
			}
		}).start();
	}

	public void addAllergy(Credentials c, String allergy,
			final APIUserCallback callback) {
		final ArrayList<NameValuePair> args = c.getHttpParameter();
		args.add(new BasicNameValuePair("allergy", allergy));
		args.add(new BasicNameValuePair("do", "addAllergy"));
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					JSONObject o = request(API_PATIENT, args);
					if (callback != null)
						callback.onSuccess(new User(o.getJSONObject("patient")));
				} catch (Exception e) {
					if (callback != null)
						callback.onError();
				}
			}
		}).start();
	}

	private URI buildApiRequestURL(String api) throws URISyntaxException {
		return new URI(this.url + api);
	}

	private JSONObject request(String api, List<NameValuePair> args)
			throws Exception {
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
		DefaultHttpClient client = new DefaultHttpClient(httpParams);
		client.getParams().setParameter("http.protocol.single-cookie-header",
				true);
		HttpRequestBase request = new HttpPost(buildApiRequestURL(api));
		if (args instanceof List) {
			List<NameValuePair> nameValuePairs = (List<NameValuePair>) args;
			((HttpPost) request).setEntity(new UrlEncodedFormEntity(
					nameValuePairs));
		}
		HttpResponse response = null;
		response = client.execute(request);
		HttpEntity responseEntity = response.getEntity();
		if (responseEntity != null) {
			String s = EntityUtils.toString(responseEntity);
			Log.d(TAG, s);
			return new JSONObject(s);
		} else
			Log.d(TAG, "?");
		throw new Exception("Something's wrong in here..");
	}

	public interface APIUserCallback {
		public void onSuccess(User u);

		public void onError();
	}
}

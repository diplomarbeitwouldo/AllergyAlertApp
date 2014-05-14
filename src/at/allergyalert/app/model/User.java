package at.allergyalert.app.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "User")
public class User {
	@Element(name = "Id")
	private int id;
	@Element(name = "Username")
	private String username;
	@Element(name = "Password")
	private String password;
	@Element(name = "Firstname")
	private String firstname;
	@Element(name = "Lastname")
	private String lastname;
	@Element(name = "City")
	private String city;
	@Element(name = "Street")
	private String street;
	@ElementList(name = "Allergies")
	private List<Allergy> allergies;

	public User(JSONObject o) throws JSONException {
		this.id = o.getInt("id");
		this.username = o.getString("username");
		this.password = o.getString("password");
		this.firstname = o.getString("firstname");
		this.lastname = o.getString("lastname");
		this.city = o.getString("city");
		this.street = o.getString("street");
		this.allergies = new ArrayList<Allergy>();
		JSONArray alls = o.getJSONArray("allergies");
		for (int i = 0; i < alls.length(); i++) {
			JSONObject obj = alls.getJSONObject(i);
			this.allergies.add(new Allergy(obj.getInt("id"), obj
					.getString("name")));
		}
	}

	public User(@Element(name = "Id") int id,
			@Element(name = "Username") String username,
			@Element(name = "Password") String password,
			@Element(name = "Firstname") String firstname,
			@Element(name = "Lastname") String lastname,
			@Element(name = "City") String city,
			@Element(name = "Street") String street,
			@ElementList(name = "Allergies") ArrayList<Allergy> allergies) {
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.city = city;
		this.street = street;
		this.allergies = allergies;
	}

	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getCity() {
		return city;
	}

	public String getStreet() {
		return street;
	}

	public ArrayList<Allergy> getAllergies() {
		return new ArrayList<Allergy>(allergies);
	}

}

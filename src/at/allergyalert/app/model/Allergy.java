package at.allergyalert.app.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Allergy {
	@Attribute(name = "Id")
	private int id;
	@Element(name = "Name")
	private String name;

	public Allergy(@Attribute(name = "Id") int id,
			@Element(name = "Name") String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}

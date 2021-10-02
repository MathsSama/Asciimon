package asciimon.asciiobject;

import java.io.FileReader;
import java.io.Reader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import asciimon.Entity;

public class ASCIIObject {

	protected String name;
	protected ObjectType type;
	protected String description;

	public ASCIIObject(String name, ObjectType type, String description) {
		this.name = name;
		this.type = type;
		this.description = description;
	}

	/**
	 * Create an object from a jsonObject (useful for OtherObject)
	 * 
	 * @param jsonObject
	 */
	public ASCIIObject(JSONObject jsonObject) {
		this.name = (String) jsonObject.get("name");
		this.description = (String) jsonObject.get("description");
		this.type = (ObjectType) ObjectType.valueOf((String) jsonObject.get("type"));
	}

	public static ASCIIObject loadObject(String filename) {

		String path = Entity.class.getProtectionDomain().getCodeSource().getLocation().getPath()
				+ "../ressources/asciiobject/" + filename + ".json";
		JSONParser parser = new JSONParser();
		// Initialise object
		ASCIIObject object = null;
		// Trying to read a json file
		try (Reader reader = new FileReader(path)) {
			JSONObject jsonObject = (JSONObject) parser.parse(reader);
			// Resolve type of object
			ObjectType type = (ObjectType) ObjectType.valueOf((String) jsonObject.get("type"));
			if (type == ObjectType.BALL) {
				object = new BallObject(jsonObject);
			} else if (type == ObjectType.HEALING) {
				object = new HealingObject(jsonObject);
			} else {

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ASCIIObject other = (ASCIIObject) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ASCIIObject [name=" + name + ", type=" + type + ", description=" + description + "]";
	}

}

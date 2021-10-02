package asciimon;
public enum Type {

	
	
	FIRE("fire"),
	WATER("water"),
	EARTH("earth"),
	NORMAL("normal");
	
	String name;
	Type badType;

	Type(String string) {
		this.name = string;
	}
	
	public Type getType(String string) {
		if(string == "fire") {
			return FIRE;
		}else if (string == "water") {
			return WATER;
		}else if(string == "earth") {
			return EARTH;
		}else {
			return NORMAL;
		}
	}
	
	
}

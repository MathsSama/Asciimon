package asciimon.asciiobject;

import org.json.simple.JSONObject;


public class HealingObject extends ASCIIObject{

	private int healingValue;

	public HealingObject(JSONObject json) {
		super(json);
		long healing = (long) json.get("healing");
		this.healingValue = (int) healing;
	}

	public void setHealingValue(int healingValue) {
		this.healingValue = healingValue;
	}

	public int getHealingValue() {
		return healingValue;
	}

	@Override
	public String toString() {
		return super.toString() + healingValue;
	}
	
	
	
	

	

	

}

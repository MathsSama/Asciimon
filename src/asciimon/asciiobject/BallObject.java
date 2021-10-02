package asciimon.asciiobject;

import org.json.simple.JSONObject;

public class BallObject extends ASCIIObject {

	
	private double rate;
	public BallObject(JSONObject json) {
		super(json);
		double rateJson = (double) json.get("rate");
		this.rate = (double) rateJson;
	}
	
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString() + rate;
	}
}

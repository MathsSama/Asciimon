package asciimon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum Ability {

	FLAMECHE("Flamèche",Type.FIRE,10, 35),
	UWU("UwU",Type.WATER,10,80),
	COPYCAT("Copy-Cat",Type.NORMAL,15,25),
	SPLASH("SPLASH",Type.WATER, 20,15),
	CUT("Cut",Type.NORMAL,10,50),
	LANCEFLAMME("Lance-Flamme",Type.FIRE,20,20),
	TREMBLEMENT("Tremblement",Type.EARTH,10,30);
	
	//
	private String name;
	private Type type;
	private int damage;
	private int critical;

	
	Ability(String name, Type type, int damage, int critical){
		this.name = name;
		this.type = type;
		this.damage = damage;
		this.critical=critical;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Type getType() {
		return type;
	}


	public void setType(Type type) {
		this.type = type;
	}


	public int getDamage() {
		return damage;
	}


	public void setDamage(int damage) {
		this.damage = damage;
	}
	

	public int getCritical() {
		return critical;
	}
	public static ArrayList<Ability> randomizedAbility(){
		List<Ability> abilityList = new ArrayList<Ability>();
		ArrayList<Ability> rdmList = new ArrayList<Ability>();
		abilityList.addAll(Arrays.asList(Ability.values()));
		Random rand = new Random();
		int i = 0;
		while(i<4) {
			Ability ability = abilityList.get(rand.nextInt(abilityList.size()));
			if(!rdmList.contains(ability)) {
				rdmList.add(ability);		
				i++;
			}
		}
		return rdmList;
	}


	public void setCritical(int critical) {
		this.critical = critical;
	}


	@Override
	public String toString() {
		return name+":"+",d:"+damage+",type:"+type;
	}
}

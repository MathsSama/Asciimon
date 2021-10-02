package asciimon;

import java.io.File;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Entity {

	private String name;
	private Type type;
	private int level;
	private int pvMax;
	private int pv;
	private ArrayList<Ability> abilities;

	public Entity(String name, Type type, int level, int pv, ArrayList<Ability> abilities) {
		this.name = name;
		this.type = type;
		this.level = level;
		this.pvMax = pv;
		this.pv = pv;
		this.abilities = abilities;
	}

	public Entity(String name, Type type, int level, int pv) {
		this(name, type, level, pv, new ArrayList<Ability>());
	}

	public static Entity loadEntity(String filename) {
		// get file on the folder ressources/asciimon
		String path = Entity.class.getProtectionDomain().getCodeSource().getLocation().getPath()
				+ "../ressources/asciimon/" + filename + ".json";
		JSONParser parser = new JSONParser();
		Entity newEntity = null;
		// Trying to read a json file
		try (Reader reader = new FileReader(path)) {
			JSONObject jsonObject = (JSONObject) parser.parse(reader);
			// Resolve entity name
			String name = (String) jsonObject.get("name");
			// Resolve type of entity
			Type type = (Type) Type.valueOf((String) jsonObject.get("type"));
			// Resolve level of entity
			long level = (long) jsonObject.get("level");

			// Resolve level of entity
			long pv = (long) jsonObject.get("pv");

			// Resolve Abilities of entity
			JSONArray abilitiesJSON = (JSONArray) jsonObject.get("abilities");
			ArrayList<Ability> abilities = new ArrayList<Ability>();
			Iterator<String> iterator = abilitiesJSON.iterator();
			while (iterator.hasNext()) {
				abilities.add(Ability.valueOf(iterator.next()));
			}
			// Create entity with resolved data
			newEntity = new Entity(name, type, (int) level, (int) pv, abilities);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return newEntity;
	}

	public static Entity loadRandomizedBoss(Player player) {
		List<String> results = collectRessources("asciimon");

		Random rand = new Random();
		String filename = results.get(rand.nextInt(results.size()));
		String path = Entity.class.getProtectionDomain().getCodeSource().getLocation().getPath()
				+ "../ressources/asciimon/" + filename;

		JSONParser parser = new JSONParser();
		Entity newEntity = null;
		try (Reader reader = new FileReader(path)) {

			JSONObject jsonObject = (JSONObject) parser.parse(reader);
			// Resolve entity name
			String name = (String) jsonObject.get("name");
			// Resolve type of entity
			Type type = (Type) Type.valueOf((String) jsonObject.get("type"));
			// Resolve level of entity
			Entity playerEntity = player.getDeck().get(0);
			long level = getRandomNumber(playerEntity.getLevel()+2, playerEntity.getLevel() + 5);

			// Resolve level of entity
			long pv = getRandomNumber((int)(playerEntity.getPvMax()*1), (int)(playerEntity.getPvMax()*1.25));

			// Resolve Abilities of entity
			ArrayList<Ability> abilities = Ability.randomizedAbility();
			// Create entity with resolved data
			newEntity = new Entity("Boss " +name, type, (int) level, (int) pv, abilities);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return newEntity;
	}
	

	public static List<String> collectRessources(String folder) {
		List<String> results = new ArrayList<String>();
		// Search all files with asciimon
		File[] files = new File(Entity.class.getProtectionDomain().getCodeSource().getLocation().getPath()
				+ "../ressources/" + folder + "/").listFiles();
		for (File file : files) {
			if (file.isFile()) {
				results.add(file.getName());
			}
		}
		return results;
	}

	public static Entity loadRandomizedEntity(Player player) {
		List<String> results = collectRessources("asciimon");

		Random rand = new Random();
		String filename = results.get(rand.nextInt(results.size()));
		String path = Entity.class.getProtectionDomain().getCodeSource().getLocation().getPath()
				+ "../ressources/asciimon/" + filename;

		JSONParser parser = new JSONParser();
		Entity newEntity = null;
		// Trying to read a json file
		try (Reader reader = new FileReader(path)) {

			JSONObject jsonObject = (JSONObject) parser.parse(reader);
			// Resolve entity name
			String name = (String) jsonObject.get("name");
			// Resolve type of entity
			Type type = (Type) Type.valueOf((String) jsonObject.get("type"));
			// Resolve level of entity
			Entity playerEntity = player.getDeck().get(0);
			long level = getRandomNumber(playerEntity.getLevel() - 1, playerEntity.getLevel() + 1);

			// Resolve level of entity
			long pv = getRandomNumber((int)(playerEntity.getPvMax()*0.75), (int)(playerEntity.getPvMax()*1));

			// Resolve Abilities of entity
			ArrayList<Ability> abilities = Ability.randomizedAbility();
			// Create entity with resolved data
			newEntity = new Entity(name, type, (int) level, (int) pv, abilities);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return newEntity;
	}

	public static int getRandomNumber(int min, int max) {
		Random random = new Random();
		return random.nextInt(max - min) + min;
	}

	@Override
	public String toString() {
		return "Entity [name=" + name + " , type=" + type + ", level=" + level + ", pv=" + pv + ", abilities="
				+ abilities + "]";
	}

	public String getName() {
		return name;
	}

	public Type getType() {
		return type;
	}

	public int getLevel() {
		return level;
	}
	public String getLeveledName() {
		return name + " Niv." + level;
	}

	public int getPv() {
		return pv;
	}

	public int getPvMax() {
		return pvMax;
	}

	public void setPvMax(int pvMax) {
		this.pvMax = pvMax;
	}

	public ArrayList<Ability> getAbilities() {
		return abilities;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setPv(int pv) {
		this.pv = pv;
	}

}

package asciimon;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import asciimon.asciiobject.ASCIIObject;

public class Player {

	private String name;
	private List<Entity> deck;
	private Map<ASCIIObject, Integer> inventory;
	private int niveau;
	private int x;
	private int y;
	private boolean bossDefeated;
	private int scoreKill;

	public Player(String n, int x , int y) {
		this(n,new ArrayList<Entity>(),1, x, y);
	}
	
	public Player(String name, List<Entity> deck, int niveau, int x, int y) {
		this(name, deck, new HashMap<ASCIIObject,Integer>(),niveau,x,y);
	}
	

	public Player(String name, List<Entity> deck, Map<ASCIIObject, Integer> inventory, int niveau, int x, int y) {
		this.name = name;
		this.deck = deck;
		this.inventory = inventory;
		this.niveau = niveau;
		this.x = x;
		this.y = y;
		this.bossDefeated = false;
		startObject(this);
	}
	
	

	public boolean isBossDefeated() {
		return bossDefeated;
	}

	public void setBossDefeated(boolean bossDefeated) {
		this.bossDefeated = bossDefeated;
	}

	public int getScoreKill() {
		return scoreKill;
	}

	public void setScoreKill(int scoreKill) {
		this.scoreKill = scoreKill;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	public boolean addAsciimon(Entity entity) {
		if(this.deck.size()<=6) {
			this.deck.add(entity);
			return true;
		}
		return false;
	}
	
	
	
	public List<Entity> getDeck() {
		return deck;
	} 
	
	public boolean addObject(ASCIIObject object, int qty) {
		if (!isInventoryFull()) {
			if (this.inventory.containsKey(object)) {
				this.inventory.replace(object, this.inventory.get(object) + qty);
				return true;
			} else {
				this.inventory.put(object, qty);
			}
		}
		return false;
	}
	
	public void removeObject(ASCIIObject object, int qty) {
		if(this.inventory.containsKey(object)) {
			if(this.inventory.get(object)>0) {
				this.inventory.replace(object, this.inventory.get(object) - qty);	
			}
		}
	}

	/**
	 * 
	 * @param obj
	 */
	public void removeObject(ASCIIObject obj) {
		removeObject(obj,1);
	}
	public boolean addObject(ASCIIObject obj){
		return addObject(obj,1);
	}
	
	public boolean isThereAPotion() {
		if(inventory.get(Main.potion)==0) return false;
		return true;
	}
	public boolean isInventoryFull() {
		int qty = 0;
		for (ASCIIObject obj : inventory.keySet()) {
			qty += inventory.get(obj);
		}
		if (qty <= 10) {
			return false;
		}
		return true;
	}


	public Map<ASCIIObject, Integer> getInventory() {
		return inventory;
	}

	public int getNiveau() {
		return niveau;
	}
	
	public static void startObject(Player player) {
		player.addObject(Main.potion, 3);
	}
	
	/**
	 * write the data of the board in a file
	 */
	public void fileWriter() {
		this.fileDelete();
		String myPath = System.getProperty("user.dir")+File.separator+"ressources"+File.separator;
		try(PrintWriter pw = new PrintWriter(new File(myPath+"PlayerData"))){
			
			pw.println(String.valueOf(this.name));
			pw.println(String.valueOf(this.niveau));
			pw.println(String.valueOf(this.x));
			pw.println(String.valueOf(this.y));
			for(int i = 0; i<this.deck.size();i++) {
				pw.println(String.valueOf(this.deck.get(i).getName()));
				pw.println(String.valueOf(this.deck.get(i).getType()));
				pw.println(String.valueOf(this.deck.get(i).getLevel()));
				pw.println(String.valueOf(this.deck.get(i).getPv()));
			}
			

		} catch (IOException e) {
			System.out.println("Writing error : " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * delete the data of the save file
	 */
	public void fileDelete() {
		String myPath = System.getProperty("user.dir")+File.separator+"ressources"+File.separator;
		try(PrintWriter pw = new PrintWriter(new File(myPath+"Playerdata"))){
			pw.println();	
		} catch (IOException e) {
			System.out.println("Writing error : " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * look if the file is empty
	 * @return if the file is empty
	 */
	public static boolean fileIsEmpty() {
		String string = "";
		boolean res = true;
		String myPath = System.getProperty("user.dir")+File.separator+"ressources"+File.separator;
		try {
			try {
				FileInputStream file = new FileInputStream(myPath+"PlayerData");
				Scanner scanner = new Scanner(file);
				string=scanner.nextLine();
				if(string.charAt(0) == ' ') {
					res = true;
				}else {
					res = false;
				}

				scanner.close();

			}catch(NoSuchElementException i) {
				res = true;
			}catch(StringIndexOutOfBoundsException e) {
				res = true;
			
			}catch(NullPointerException e) {
				res = true;
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(NullPointerException e) {
			res = true;
		}
		return res;
	}

	/**
	 * return a board with the data of the file 
	 * @return a new board with the new data 
	 */
	public Player fileReader() {
		String text;
		String playerName = null;
		String asciiName;

		Player test = new Player("test", 0, 0); 

		String objectName;

	    List<Entity> deck = new ArrayList<Entity>();
	    Map<ASCIIObject , Integer>inventory = new HashMap<ASCIIObject , Integer>();
	    Entity etmp = Entity.loadEntity("blob");
	    Object otmp;
		int level = 0;
		int x = 0;
		int y = 0;
		String myPath = System.getProperty("user.dir")+File.separator+"ressources"+File.separator;
		try {
			FileInputStream file = new FileInputStream(myPath+"PlayerData");
			Scanner scanner = new Scanner(file);
			int line = 1;
			while(scanner.hasNextLine())
			{
				
				text=scanner.nextLine()+"\n";
				if(line == 1) {
					playerName=text;
					System.out.println(playerName);
				}
				else if(line ==2) {
					level= Integer.parseInt(text.substring(0,1));
					System.out.println(level);
				}else if(line == 3) {
					x = Integer.parseInt(text.substring(0,1));
					System.out.println(x);
				}else if(line ==4) {
					y = Integer.parseInt(text.substring(0,1));
					System.out.println(y);
				}else if(line ==5) {
					for(int i = 0 ; i<this.deck.size(); i++) {
						asciiName=scanner.nextLine()+"\n";
						etmp = Entity.loadEntity("blob");
						System.out.println(etmp.getName());
					}
				}else {
					for(int i = 0; i<this.inventory.size(); i++) {
						objectName = scanner.nextLine()+"\n";
					}
				}
				
				
				line++;
				
			}
			scanner.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Player res = new Player(playerName, deck, level,x,y );
		res.addAsciimon(etmp);
		System.out.println(playerName);
		System.out.println(deck);
		System.out.println(deck.get(0).getName());
		System.out.println(deck.get(0).getLevel());
		System.out.println(deck.get(0).getPv());
		System.out.println(level);
		
		return res;
	}

	
}
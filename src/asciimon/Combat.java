package asciimon;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

import asciimon.asciiobject.ASCIIObject;

public class Combat {

	private Player player;
	private Entity ennemi;
	private Entity selectedAsciimon;
	private boolean end;
	
	
	public Combat(Player player, Entity ennemi) {
		super();
		this.player = player;
		this.ennemi = ennemi;
		selectedAsciimon=null;
		end=false;
	}
	
	public void lunchCombat() {
		Scanner scan = new Scanner(System.in);
		start(scan);
		
		
		while(!this.end) {
			affichageStats(scan);
			attaque(this.ennemi, choixAttaqueEnnemi(), this.selectedAsciimon, scan);
			this.end=endOfCombat();
			if(!end) {
				choixPlayer(scan);			
				this.end=endOfCombat();
			}
		}
		
		end();
	}

	private void start(Scanner scan) {
		System.out.println("Fight "+ennemi.getName()+"!");
		boolean valide = false;
		while (!valide) {
			System.out.println("Choose an ASCIIMON : ");
			for (int i = 0; i < player.getDeck().size(); i++) {
				System.out.println((i+1)+" -> " + player.getDeck().get(i).getLeveledName());
			}
			int choix = 0;
			choix = scan.nextInt();
			if (choix >= 1 && choix <= player.getDeck().size()) {
				selectedAsciimon=player.getDeck().get(choix-1);
				valide = true;
			} else {
				System.out.println("Choose an ASCIIMON !");
			}
		}
	}
	
	private void end() {
		System.out.println("End of the fight, let's explore !\n");
	}
	
	private void affichageStats(Scanner scan) {
		System.out.println("Opponent : "+ennemi.getName()+" -> Level : "+ennemi.getLevel()+" | Type : "+ennemi.getType() + " | PV :"+ennemi.getPv());
		System.out.println("Your : "+selectedAsciimon.getName()+"-> Level : "+selectedAsciimon.getLevel()+" |  Type : "+selectedAsciimon.getType()+" | PV :"+selectedAsciimon.getPv());
		Main.continueConsole();
	}
	
	private void affichageAttaque() {
		for (int i = 0; i < selectedAsciimon.getAbilities().size(); i++) {
			System.out.println((i+1)+" -> "+selectedAsciimon.getAbilities().get(i).getName()
					+" -> Damage : "+selectedAsciimon.getAbilities().get(i).getDamage()
					+" |  Type : "+selectedAsciimon.getAbilities().get(i).getType());
		}
	}
	
	private int choixPlayer(Scanner scan) {
		int retour = 6;
		boolean valide = false;
		while (!valide) {
			affichageStats(scan);
			System.out.println("Choose an action : ");
			affichageAttaque();
			affichageInventaire();
			int choix = scan.nextInt();
			if (choix >= 1 && choix <= 4) {
				choixAttaque(choix, scan);
				valide = true;
			} else if(choix == 5){
				System.out.println("You choose to use a heal potion !");
				if(player.isThereAPotion()) {
					if(selectedAsciimon.getPvMax()-selectedAsciimon.getPv()>20) {
						selectedAsciimon.setPv(selectedAsciimon.getLevel()+20);
					}else {
						selectedAsciimon.setPv(selectedAsciimon.getPvMax());
					}
					player.removeObject(Main.potion);
				}else {
					System.out.println("You have no potion left !");
				}
			}else {
				choixAttaque(1, scan);
				valide = true;
			}
				
//			}else if(choix == 6){
//				System.out.println("You use an Ball ");
//				this.end=true;
//			}else if(choix == 7){
//				System.out.println("You choose to run away ! ");
//				this.end=true;
//			}else {
				System.out.println("Choose an action !");
			
		}
		return retour;
	}
	
	private void affichageInventaire() {
		System.out.println("5 -> Use a heal potion");
		//+ "\n6 -> Use a ball "+"\n7 -> Run away");
		
		
	}

	private Ability choixAttaque(int attack, Scanner scan) {
		Ability attaque=null;
		attaque=selectedAsciimon.getAbilities().get(attack-1);
		
		System.out.println(selectedAsciimon.getName()+" attack with "+attaque.getName());
		
		attaque(selectedAsciimon, attaque, ennemi, scan);
		
		return attaque;
	}
	
	/*private void choixObjet() {
		boolean valide=false;
		Scanner scan = new Scanner(System.in);
		int choix = 0;
		List<ASCIIObject> objects;
		
		
		for (ASCIIObject i : player.getInventory()) {
			
		}
		
		
		while(!valide) {
			System.out.println("Select the object you want to use ");
			choix = scan.nextInt();
			if (choix <= objects.size()) {
				if(objects.get(choix).)
				valide = true;
			}else {
				System.out.println("Select a valid object !");
			}
		}
		scan.close();
	}*/

	private Ability choixAttaqueEnnemi() {
		Ability attaque=null;
		Random rand = new Random();
		int random = rand.nextInt(ennemi.getAbilities().size());
		attaque=ennemi.getAbilities().get(random);
		System.out.println("The ennemi attack you with : "+attaque.getName());
		return attaque;
	}
	
	private boolean endOfCombat() {
		boolean end = false;
		Random rand = new Random();
		int random = rand.nextInt(100);
		if (ennemi.getPv()<=0) {
			System.out.println("You defeated the enemy "+ennemi.getName()+" !");
			System.out.println("New level !");
			if(ennemi.getName().contains("Boss")) {
				player.setScoreKill(player.getScoreKill()+10);
				player.setBossDefeated(true);
			}
			player.setScoreKill(player.getScoreKill()+ennemi.getLevel());
			end=true;
			if(random<+50) {
				for (int i = 0; i < player.getDeck().size(); i++) {
					player.getDeck().get(i).setLevel(player.getDeck().get(i).getLevel()+1);
				}
			}
		}
		int cpt = isPlayerDeckAlive();
		if (cpt ==  player.getDeck().size()) end=true;
		return end;
	}

	public int isPlayerDeckAlive() {
		int cpt = 0;
		for (int i = 0; i < player.getDeck().size(); i++) {
			if (player.getDeck().get(i).getPv()<=0) cpt++;
		}
		return cpt;
	}
	
	private void attaque(Entity selectedAsciimon, Ability choixAttaque, Entity ennemi, Scanner scan) {
		Random rand = new Random();
		int random=rand.nextInt(100);
		int addDamage=0;
		int addDamageByLevel=(int)(selectedAsciimon.getLevel()/2);
		int addDamagebyType = damageByType(choixAttaque.getDamage(), choixAttaque.getType(), ennemi.getType());
		if (random<=choixAttaque.getCritical()) {
			addDamage=(int)(choixAttaque.getDamage()*(0.25));
			System.out.println("Critical damage !");
		}
		int damage=(int)(choixAttaque.getDamage()+addDamage+addDamageByLevel+addDamagebyType);
		System.out.println("The ability does "+damage+" damages !");
		ennemi.setPv(ennemi.getPv()-damage);
		
		Main.continueConsole();
		Main.clearScreen();
		
	}
	
	private int damageByType(int baseDamage, Type a, Type b) {
		int retour = 0;
		String affichage = "It is very effective";
		if(a==Type.FIRE&&b==Type.EARTH) {
			retour = (int)(baseDamage*0.25);
			System.out.println(affichage);
		}
		if(a==Type.EARTH&&b==Type.WATER) {
			retour = (int)(baseDamage*0.25);
			System.out.println(affichage);
		}
		if(a==Type.WATER&&b==Type.FIRE) {
			retour = (int)(baseDamage*0.25);
			System.out.println(affichage);
		}
		
		return retour;
	}
}
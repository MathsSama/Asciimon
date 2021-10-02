package asciimon;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import asciimon.asciiobject.ASCIIObject;
import asciimon.asciiobject.BallObject;
import asciimon.asciiobject.HealingObject;

public class Main {

	public static Entity asciicram = Entity.loadEntity("asciicram");
	public static Entity blob = Entity.loadEntity("blob");
	public static Entity grocaillou = Entity.loadEntity("grocaillou");
	public static Player pierre = new Player("Pierre", 5,5);

	public static HealingObject potion = (HealingObject) ASCIIObject.loadObject("potion");
	public static BallObject ball = (BallObject) ASCIIObject.loadObject("ball");

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		Player player = new Player("test", 0, 0);
		boolean correct = false;
		Niveau niveau = new Niveau(1);
		int debut = 0;
		while(debut != 1) {
			clearScreen();
			fileReader("menu");
			try{
				debut = scan.nextInt();
				if(debut == 1) {
					System.out.println();
				}else if(debut == 2) { 
					fileReader("menuRules");
					continueConsole();
					debut = 0;
				}else if(debut == 3){
					System.out.println("Goodbye !");
					System.exit(debut);
				}else {
					System.out.println("Incorrect Choice");
					debut = 0;
				}
			}catch(InputMismatchException e){
				System.out.println("Error ");
				System.exit(0);
			}
			
		}
		System.out.println("Do you want to load a save ?\n (1) Yes / (2) No ");
		while(!correct) {
			int save = scan.nextInt();
			if(save == 1) {
				if(!Player.fileIsEmpty()) {
					player = player.fileReader();
					correct = true;
					System.out.println("");
					MovementMain.PlayerMove(player);
					correct=true;
					player.fileWriter();
					
				}else {
					System.out.println("No save file detected");
				}
				
			}else if(save == 2) {
				System.out.println("What is your name ?");
				
				String name = scan.next();
				player = new Player(name, 5, 5);
				System.out.println("Your name is " + name);
				boolean valide = false;

				continueConsole();
				clearScreen();
				
				while(!valide) {
					System.out.println("1. "+ grocaillou.getLeveledName());
					System.out.println("2. "+ asciicram.getLeveledName());
					System.out.println("3. "+ blob.getLeveledName());
					System.out.println("> Choose an ASCIIMON to start the game : 1 | 2 | 3");
					
					int choix = 0;
					try {
						choix = scan.nextInt();
						if(choix == 1) {
							player.addAsciimon(grocaillou);
							valide = true;
						}else if(choix == 2) {
							player.addAsciimon(asciicram);
							valide = true;
						}else if(choix == 3) {
							player.addAsciimon(blob);
							valide = true;
						}else {
							System.out.println("Wrong choice, try again !");
							choix = scan.nextInt();
						}						
					} catch(Exception e) {
						pierre.addAsciimon(asciicram);
						player.addAsciimon(Entity.loadRandomizedEntity(pierre));
						System.out.println("Secret choice ! ");
						valide = true;
					}
					continueConsole();

				}
				System.out.println("You choosed " + player.getDeck().get(0).getName());
				continueConsole();
				System.out.println("Start of the game ! Have fun in ASCIIMON \nz/q/s/d to move");
				player.fileWriter();
				MovementMain.PlayerMove(player);
				
				correct = true;
				player.fileWriter();
			}else {
				System.out.println("Wrong choice");
			}
		}
		
		
		}

	public static void continueConsole() {
        try {
        	System.out.println("Press [Enter] to continue");
            int read = System.in.read(new byte[2]);
        } catch (IOException e) {
        }
	}
	public static void clearScreen() {
		for (int i = 0; i < 5000; ++i) System.out.println(); 
	}
	public static String fileReader(String string) {
		String board = "";
		String myPath = System.getProperty("user.dir") + File.separator + "ressources" + File.separator;
		try {
			FileInputStream file = new FileInputStream(myPath + string);
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				board += scanner.nextLine() + "\n";
			}
			scanner.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(board);
		return board;

	}

	public static String UpdateStr(char[][] _tab, int _x, int _y) {
		String res = "";
		for (int i = 0; i < _x; i++) {
			for (int j = 0; j < _y; j++) {

				res += _tab[i][j];
			}
			res += "\n";
		}
		return res;
	}
}

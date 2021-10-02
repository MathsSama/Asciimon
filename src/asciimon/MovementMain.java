package asciimon;

import java.util.Random;
import java.util.Scanner;

public class MovementMain {

	public static void PlayerMove(Player player){
		
		PlayerController controller = new PlayerController();
		Scanner scan = new Scanner (System.in);
		Niveau niv = new Niveau (1);
		char res = ' ';
		Score score = new Score(0);
		score.setScore(player.getDeck());
		int sco= score.GetScore()+player.getScoreKill();
		
		Infirmary infirmary = new Infirmary();
		Loot loot = new Loot();
		
		char[][] tab = niv.getTab();
		int[][] tabAspect = new int[20][20]; // --> tab to determine if there is bush or nothing
		String tabStr = "";
		for(int i = 0; i <20; i++) {
			for(int y = 0 ; y<20; y++) {
				if(tab[y][i]== '/') {
					tabAspect[y][i] = 1;
				}else if (tab[y][i] == '+') {
					tabAspect[y][i] = 2;
				}else if (tab[y][i] == '$') {
					tabAspect[y][i] = 3;
				}else if (tab[y][i] == '*') {
					tabAspect[y][i] = 4;
				}
				else{
					tabAspect[y][i] = 0;
				}
			}
		}

		System.out.println(tabStr);

		tab[5][5] = '@';
		player.setX(5);
		player.setY(5);
		tabStr = UpdateStr(tab, 20, 20);
		System.out.println(tabStr);
		Random rand = new Random();
		int _r;

		while (res != 'c') 
		{
			do {
				res = scan.next().charAt(0);
				controller.Move(player, tab, tabAspect, res);
				if (tabAspect[player.getX()][player.getY()] == 1) {
					_r = rand.nextInt(100);
					if (_r > 70) {
						_r = rand.nextInt(100);
						if(_r >25) {
							Combat fight = new Combat(player,  Entity.loadRandomizedEntity(player));
							if(fight.isPlayerDeckAlive()==player.getDeck().size()) {
							} else {
								System.out.println("You meet an ASCIIMON!");
								fight.lunchCombat();	
							}
						}else {
							Main.clearScreen();
							loot.ApplyCapacity(player);
							System.out.println("You get some potions");
							Main.continueConsole();
							
							
						}
					}
				} else if(tabAspect[player.getX()][player.getY()] == 2){
					infirmary.ApplyCapacity(player);
					Main.clearScreen();
					System.out.println("All your ASCIIMON's have been healed");
					Main.continueConsole();
				} else if(tabAspect[player.getX()][player.getY()] == 3) {
					Combat fight = new Combat(player,  Entity.loadRandomizedBoss(player));
					if(fight.isPlayerDeckAlive()==player.getDeck().size()) {
					} else {
						System.out.println("You meet the Boss !");
						fight.lunchCombat();	
					}
					if(player.isBossDefeated()) {
						MovementMain.PlayerMove(player);
						player.setBossDefeated(false);
						Loot.isEmpty = false;
					}
				} else if(tabAspect[player.getX()][player.getY()] == 4) {
					Main.clearScreen();
					if(!Loot.isEmpty) {
						Loot.isEmpty = true;
						loot.ApplyCapacityFromShop(player);
						System.out.println("You get ASCIIBall and some potions");
					}else {
						System.out.println("Shop empty !");
					}
					Main.continueConsole();
				}
				tabStr = UpdateStr(tab, 20, 20);
		
				score.setScore(player.getDeck());
				sco = score.GetScore()+player.getScoreKill();
				score.affichageScore();
				Main.clearScreen();
				score.affichageScore();
				System.out.println(tabStr);
			}while (tabAspect[player.getX()][player.getY()] == 1);
		}
	}

	public static String UpdateStr (char[][] _tab, int _x, int _y)
	{
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

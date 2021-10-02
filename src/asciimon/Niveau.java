package asciimon;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

import asciimon.asciiobject.ASCIIObject;

public class Niveau {

	private int num;
	private char[][] tab;

	public Niveau(int num) {
		super();
		this.num = num;
		this.tab = fileReader();
	}


	@SuppressWarnings("resource")
	public char[][] fileReader() {
		String text;
		char[][] tab = new char[20][20];
		String myPath = System.getProperty("user.dir")+File.separator+"ressources"+File.separator;
		Random rand = new Random();
		int r = 0;
		try {
			r = rand.nextInt(5);
			FileInputStream file = new FileInputStream(myPath+"niveau1");
			if(r < 1) {
				file = new FileInputStream(myPath+"niveau1");				
			}else if(r < 2 && r >= 1) {
				file = new FileInputStream(myPath+"niveau2");
			}else if(r<3 && r>= 2){
				file = new FileInputStream(myPath+"niveau3");
			}else if(r<4 && r>= 3){
				file = new FileInputStream(myPath+"niveau4");
			}else {
			
				file = new FileInputStream(myPath+"niveau5");
			}
			Scanner scanner = new Scanner(file);
			int line = 1;
			while(scanner.hasNextLine())
			{
				
				text=scanner.nextLine()+"\n";
				for(int i = 0 ; i<20; i++) {
					tab[line-1][i] = text.charAt(i);
				}
				line++;
				//System.out.println(tab);
				
			}
			scanner.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return tab;
	}

	public int getNum() {
		return num;
	}

	public char[][] getTab() {
		return tab;
	}
}

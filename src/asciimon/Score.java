package asciimon;

import java.util.List;

public class Score {
	
	private int score;
	
	public Score(int score) {
		this.score=score;
	}
	
	public void setScore(List<Entity> deck) {
		int level=0;
		for (Entity i : deck) {
			level+=i.getLevel();
		}
		score=level;
	}

	public int GetScore() {
		return this.score = score;
	}

	public void affichageScore() {
		System.out.println("Your score is : "+GetScore()+"\nz/q/s/d to moove");
	}
}

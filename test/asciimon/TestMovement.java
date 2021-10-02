package asciimon;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestMovement {
	
	Player player;
	PlayerController controller = new PlayerController();
	char[][] tab = new char[5][5];
	int[][] tabAspect = new int[5][5];
	
	@BeforeEach
	public void Initialisation()
	{
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				tab[i][j] = ' ';
			}
		}
		
		player = new Player ("OUI", 3,3);
	}
	
	@Test
	public void testMoveForward()
	{
		controller.Move(player, tab, tabAspect, 'z');
		assertFalse(player.getX() == 3);
		assertTrue(player.getX() == 2);
	}
	
	@Test
	public void testMoveBackward() {
		controller.Move(player, tab, tabAspect, 's');
		assertFalse(player.getX() == 2);
		assertTrue(player.getX() == 4);
	}
	
	@Test
	public void testMoveLeft() {
		controller.Move(player, tab, tabAspect, 'q');
		assertFalse(player.getY() == 3);
		assertTrue(player.getY() == 2);
	}
	
	@Test
	public void testMoveRight() {
		controller.Move(player, tab, tabAspect, 'd');
		assertFalse(player.getY() == 3);
		assertTrue(player.getY() == 4);
	}
	
	@Test
	public void testMoveBlockedThanksWall() {
		tab[2][3] = '#';
		controller.Move(player, tab, tabAspect, 'z');
		assertFalse(player.getX() == 2);
		assertFalse(player.getX() == 4);
		assertTrue(player.getX() == 3);
	}
}

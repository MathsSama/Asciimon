package asciimon;

import asciimon.asciiobject.BallObject;
import asciimon.asciiobject.HealingObject;

public class Loot implements Building {

	TypeBuilding type = TypeBuilding.LOOT;
	HealingObject potion = Main.potion;
	BallObject ball = Main.ball;
	public static boolean isEmpty;
	
	public void ApplyCapacity(Player player) {
		int randQty = Entity.getRandomNumber(1, 2);
		
		player.addObject(potion, randQty);
	}
	
	public void ApplyCapacityFromShop(Player player)
	{
		if (!isEmpty) {
			player.addObject(potion, 2);
			player.addObject(ball, 1);
		}
		isEmpty = true;
	}
	
	public TypeBuilding getTypeBuilding() { return this.type; }

}

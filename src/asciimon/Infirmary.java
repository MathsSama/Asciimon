package asciimon;

public class Infirmary implements Building{
	
	private TypeBuilding type;

	public void ApplyCapacity(Player player) {
		for (Entity e : player.getDeck()) {
			e.setPv(e.getPvMax());
		}
	}

	public TypeBuilding getTypeBuilding() {return this.type;}

}

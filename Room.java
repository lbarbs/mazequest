public class Room{
	boolean nDoor;
	boolean eDoor;
	boolean sDoor;
	boolean wDoor;
	Console c = new Console();
	boolean isItem = false;
	boolean isTrap = false;
	boolean explored = false;
	Dice itemDice = new Dice(1,2);
	Dice trapDice = new Dice(1,5);
	Item item;
	Trap trap;
	
	public Room(){
		new Room(false, false, false, false);
	}
	
	public Room(boolean nD, boolean eD, boolean sD, boolean wD){
		nDoor = nD;
		eDoor = eD;
		sDoor = sD;
		wDoor = wD;
		if(itemDice.flip()==0) {
			isItem = true;
			item = new Item();
		}
		if(trapDice.flip()==0) {
			isTrap = true;
			trap = new Trap();
		}
	}
	
	public void describeRoom(){
		c.println("You are in a room in a maze. ");
		if (nDoor) c.println("There is a passageway to the North. ");
		if (eDoor) c.println("There is a passageway to the East. ");
		if (sDoor) c.println("There is a passageway to the South. ");
		if (wDoor) c.println("There is a passageway to the West. ");
		if (this.countDoors() == 0) c.println("There are walls on all sides.");
		c.println();
		if (isItem) item.findItem();
		c.println();
	}
	
	public int countDoors(){
		int noDoors = 0;
		if (nDoor) noDoors++;
		if (eDoor) noDoors++;
		if (sDoor) noDoors++;
		if (wDoor) noDoors++;
		return noDoors;
	}
}
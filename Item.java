public class Item{
	int type;
	String item[] = {"explosive", "weapon","armour","gold","teleporter","potion"};
	int gold;
	Dice picker = new Dice(1,item.length);
	Dice goldDice = new Dice(1,10);
	Dice d20 = new Dice(1,20);
	Console c = new Console();
	Weapon w;
	Armour a;
	boolean  tr;
	boolean gp;
	
	public Item(){
		int t = picker.flip();
		setItem(t);
	}
	public Item(int t){
		setItem(t);
	}
	public void setItem(int t){
		type = t;
		switch(t){
			case 1 :  w = new Weapon();
				break;
			case 2 :  a = new Armour();
				break;
			case 3 : gold = goldDice.roll();
				break;
			case 4:
				if(d20.roll()>=18) tr = true;
				else tr = false;
				break;
			case 5:
				if(d20.roll()>=15) gp = true;
				else gp = false;
				break;
		}
	}
	public void findItem(){
		switch(type){
			case 0 : c.println("You see some explosives on the floor");
				break;
			case 1 : c.println("There is a weapon on the floor, it is a " + w.name[w.type]);
				break;
			case 2 : c.println("You see a piece of armour on the floor. \nUpon inspection you see it is a " + a.name[a.type]);
				break;
			case 3 : c.println("You see " + gold + " gold pieces on the floor");
				break;
			case 4:
				if(tr) c.println("You see a ring of teleportation on the floor");
				else c.println("You see a scroll of teleportation on the floor");
				break;
			case 5:
				if(gp) c.println("You see a large health potion on the floor");
				else c.println("You see a health potion on the floor");
				break;
		}
	}
	
}
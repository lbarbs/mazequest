public class Armour extends Item{
	int type;
	int ac;
	int dexmod;
	int maxdex;
	String[] name = {"jacket", "leather jacket", "suit of leather armour", "suit of studded leather", "chain shirt", "suit of scale mail", "suit of chain mail", "breastplate", "suit of banded mail", "full plate suit"};
	Dice d20 = new Dice(1,20);
	
	public Armour(){
		int at = d20.roll();
		if(at<=4) type = 1;
		else if(at<=8) type = 2;
		else if(at<=12) type = 5;
		else if(at<=14) type = 4;
		else if(at<=16) type = 5;
		else if(at<=18) type = 6;
		else if(at<=19) type = 7;
		else type = 8;
		setArmour(type);
	}
	
	public Armour(int t){
		setArmour(t);
	}
	public void setArmour(int t){
		type = t;
		switch(type){
			case 0 : ac=0;
				dexmod = 0;
				maxdex = 8;
				break;
			case 1 : ac=1;
				dexmod = 0;
				maxdex = 8;
				break;
			case 2 : ac=2;
				dexmod = 0;
				maxdex = 6;
				break;
			case 3 : ac=3;
				dexmod = -1;
				maxdex = 5;
				break;
			case 4 : ac=4;
				dexmod = -2;
				maxdex = 4;
				break;
			case 5 : ac=4;
				dexmod = -4;
				maxdex = 3;
				break;
			case 6 : ac=5;
				dexmod = -5;
				maxdex = 2;
				break;
			case 7 : ac=5;
				dexmod = -4;
				maxdex = 2;
				break;
			case 8 : ac=6;
				dexmod = -6;
				maxdex = 1;
				break;
			case 9 : ac=8;
				dexmod = -6;
				maxdex = 1;
				break;
		}
	
	}
}
public class Weapon extends Item{
	int type;
	int crit;
	int critMult;
	Dice damage;
	String[] name = {"fists", "dagger", "mace", "spear", "handaxe", "kukri", "short sword", "battleaxe", "longsword", "rapier", "greataxe", "greatsword"};
	Dice d100 = new Dice(1,100);
	
	public Weapon(){
		int wt = d100.roll();
		if(wt>=97) type = 11;		// greatsword
		else if(wt>=95) type = 10;	// greataxe
		else if(wt>=90) type = 9;	// rapier
		else if(wt>=80) type = 8;	// longsword
		else if(wt>=75) type = 7;	// battleaxe
		else if(wt>=65) type = 6;	// short sword
		else if(wt>=60) type = 5;	// kukri
		else if(wt>=50) type = 4;	// handaxe
		else if(wt>=45) type = 3;	// spear
		else if(wt>=30) type = 2;	// mace
		else type = 1;				// dagger
		setWeapon(type);
	}
	
	public Weapon(int t){
		setWeapon(t);
	}
	public int doDamage(int hit){
		int dam = damage.roll();
		if (hit>=crit) dam*=critMult;
		return dam;
	}
	public void setWeapon(int t){
		type = t;
		switch(type){
			case 0 : crit = 20;
				critMult = 2;
				damage = new Dice(1,3);
				break;
			case 1 : crit = 19;
				damage = new Dice(1,4);
				critMult = 2;
				break;
			case 2 : crit = 20;
				critMult = 2;
				damage = new Dice(1,6);
				break;
			case 3 : crit = 20;
				damage = new Dice(1,8);
				critMult = 2;
				break;
			case 4 : crit = 20;
				critMult = 3;
				damage = new Dice(1,6);
				break;
			case 5 : crit = 18;
				critMult = 2;
				damage = new Dice(1,4);
				break;
			case 6 : crit = 19;
				critMult = 2;
				damage = new Dice(1,6);
				break;
			case 7 : crit = 20;
				critMult = 3;
				damage = new Dice(1,8);
				break;
			case 8 : crit = 19;
				critMult = 2;
				damage = new Dice(1,8);
				break;
			case 9 : crit = 18;
				critMult = 2;
				damage = new Dice(1,6);
				break;
			case 10 : crit = 20;
				critMult = 3;
				damage = new Dice(1,12);
				break;
			case 11 : crit = 19;
				critMult = 2;
				damage = new Dice(2,6);
				break;
		}
	
	}
}
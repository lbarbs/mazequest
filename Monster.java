
public class Monster{
	int type;
	String[] name = {"rat","fox","giant spider","wolf","wild boar","bear","gnome","dwarf"};
	Dice picker = new Dice(1,name.length);
	Dice d10 = new Dice(1,10);
	int str;
	int dex;
	int con;
	int maxhp;
	int hp;
	Console c = new Console();
	int ac;
	int crit;
	int critMult;
	Dice damage;

	public Monster(){
		int t = picker.flip();
		setMonster(t);
	}
	public Monster(int level){
		int t;
		int mod = d10.roll();
		if (mod <= 1) t=level -2;
		else if (mod <= 3) t=level -1;
		else if (mod <= 7) t=level;
		else if (mod <= 9) t=level +1;
		else t=level +2;
		t--;
		setMonster(t);
	}
	public void setMonster(int t){
		if (t<0) t=1;
		if (t>=name.length) t=name.length-1;
		type = t;
		switch(t){
			case 0 :		//rat  
				str = 6;
				dex = 10;
				con = 6;
				maxhp = 3;
				crit = 20;
				critMult = 2;
				damage = new Dice(1,3);
				break;
			case 1 :  		//fox
				str = 8;
				dex = 12;
				con = 8;
				maxhp = 5;
				crit = 20;
				critMult = 2;
				damage = new Dice(1,4);
				break;
			case 2 :		//spider
				str = 10;
				dex = 6;
				con = 10;
				maxhp = 4;
				crit = 20;
				critMult = 2;
				damage = new Dice(2,2);
				break;
			case 3 :		//wolf
				str = 12;
				dex = 14;
				con = 10;
				maxhp = 6;
				crit = 18;
				critMult = 2;
				damage = new Dice(2,3);
				break;
			case 4 :  		// boar
				str = 14;
				dex = 10;
				con = 14;
				maxhp = 8;
				crit = 20;
				critMult = 3;
				damage = new Dice(1,6);
				break;
			case 5 :  		//bear
				str = 16;
				dex = 10;
				con = 16;
				maxhp = 10;
				crit = 20;
				critMult = 3;
				damage = new Dice(2,4);
				break;
			case 6 :  		//gnome
				str = 8;
				dex = 16;
				con = 10;
				maxhp = 10;
				crit = 18;
				critMult = 2;
				damage = new Dice(1,6);
				break;
			case 7 :  		//dwarf
				str = 14;
				dex = 8;
				con = 16;
				maxhp = 12;
				crit = 20;
				critMult = 2;
				damage = new Dice(2,6);
				break;
		}
		hp = maxhp;
		ac = 10 + mod(dex);
	}
	public int mod(int stat){
		int mod; 
		if (stat>=10) mod = (stat-10)/2;
		else mod = (stat -11)/2;
		return mod;
	}
	public int doDamage(int hit){
		int dam = damage.roll();
		if (hit>=crit) dam*=critMult;
		return dam;
	}
}
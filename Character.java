public class Character{
	String name;
	String role;
	int level;
	int str;
	int dex;
	int con;
	int xp;
	int maxhp;
	int hp;
	int gold;
	Dice statdice = new Dice(4,6);
	Dice hpdice = new Dice(5,4);
	Dice hpgaindice = new Dice(1,3);
	Dice largepotiondice = new Dice(3,6);
	Dice potiondice = new Dice(3,4);
	Console c = new Console();
	Weapon weapon = new Weapon(0);
	Armour armour = new Armour(0);
	int explosives;
	int teleportationScrolls = 0;
	boolean teleportationRing = false;
	int potions = 0;
	int largepotions = 0;

	public Character(){
		name = c.readLine("Please enter your name: ");
		if (name.equals("")) name = "Anon";
		role = c.readLine("Please enter your role: ");
		if (role.equals("")) role = "Explorer";
		char keepStats = 'n';
		do{
			str = statdice.rollMinusLowest();
			dex = statdice.rollMinusLowest();
			con = statdice.rollMinusLowest();
			maxhp = hpdice.rollMinusLowest()+mod(con);
			hp = maxhp;
			xp = 0;
			level = 1;
			explosives = 3;
			gold = 0;
			c.println("");
			c.println("Your stats:");
			c.println("");
			showStats();
			do{
				keepStats = c.readChar("Are you happy with these stats? (y/n) > ");
			}while(keepStats != 'n' && keepStats != 'y');
		}while(keepStats != 'y');
	}
	
	public int mod(int stat){
		int mod; 
		if (stat>=10) mod = (stat-10)/2;
		else mod = (stat -11)/2;
		return mod;
	}
	public int ac(){
		int ac = 10 + armour.ac;
		if (mod(dex)<=armour.maxdex) ac += mod(dex);
		else ac+= armour.maxdex;
		return ac;
	}
	public String dispMod(int stat){
		int mod; 
		if (stat>=10) mod = (stat-10)/2;
		else mod = (stat -11)/2;
		String out = String.format("(%+d)", mod);
		return out;
	}
	public void showStats(){
		c.println("Name: " + name + "\tLevel " + level + " " + role + " (" + xp + "xp)");
		c.print("Str: " + str + dispMod(str)+"\t");
		c.print("Dex: " + dex + dispMod(dex)+"\t");
		c.println("Con: " + con + dispMod(con)+"\t");
		c.println("HP: " + hp + "/" + maxhp + "\t\tAC: " + ac());
		c.println("Gold: " + gold);
		c.println("Weapon: " + weapon.name[weapon.type]);
		c.println("Armour: " + armour.name[armour.type]);		
		c.println();
	}
	public void increaseLevel(){
		c.println("");
		level++;
		c.println("You gain a level. You are now a level " + level + " " + role + ".");
		char stat;
		c.print("Str: " + str + dispMod(str)+"\t");
		c.print("Dex: " + dex + dispMod(dex)+"\t");
		c.println("Con: " + con + dispMod(con)+"\t");
		do{
			stat = c.readChar("Which stat would you like to boost? (s/d/c)");
		}while(stat != 's' && stat != 'd' && stat != 'c');
		c.println("");
		switch(stat){
			case 's' : str++;
				break;
			case 'd' : dex++;
				break;
			case 'c' : con++;
				break;
		}
		maxhp+=hpgaindice.roll()+mod(con);
		showStats();
		c.readLine("Press enter to continue");
	}
	public void takePotion(){
		if(largepotions > 0){
			int hpgain = gainhp(largepotiondice);
			c.println("You drink the large potion and recover " + hpgain + " hitpoints. You now have " + hp + "hp.");
			largepotions--;
			c.println("You now have " + largepotions + " large potions");
		}
		else if(potions > 0){
			int hpgain = gainhp(potiondice);
			c.println("You drink the potion and recover " + hpgain + " hitpoints. You now have " + hp + "hp.");
			potions--;
			c.println("You now have " + potions + " potions");
		}
		else {
			c.println("You have no potions");
		}
	}
	public int gainhp(Dice dice){
		int hpgain = dice.roll();
		if ((hp + hpgain)>maxhp ) {
			hpgain = maxhp - hp;
		}
		hp += hpgain;
		return hpgain;
	}
}
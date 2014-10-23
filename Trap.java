public class Trap{
	int type;
	String[] name = {"poison dart","boulder"};
	Dice picker = new Dice(1,name.length);
	Dice damage;
	Dice d10 = new Dice(1,10);
	Dice d20 = new Dice(1,20);
	int CR;
	boolean live;
	Console c = new Console();

	public Trap(){
		int t = picker.flip();
		type = t;
		live = true;
	}
	public void setCR(int level){
		int mod = d10.roll();
		if (mod <= 1) CR=level -2;
		else if (mod <= 2) CR=level -1;
		else if (mod <= 8) CR=level;
		else if (mod <= 9) CR=level +1;
		else CR=level +2;
		if (CR<0) CR=1;
	}
	public void setTrap(int level){
		setCR(level);
		switch(type){
			case 0 : 
				damage = new Dice(1,CR);
				break;
			case 1 : 
				damage = new Dice(CR,2);
				break;
		}
	}
	public void engageTrap(Character player){
		setTrap(player.level);
		if (live){
			c.println("As you enter the next room you set off a " + name[type] + " trap.");
			if((d20.roll()+player.mod(player.dex)+player.armour.dexmod)>(10+CR)){
				c.println("You successfully dodge the " + name[type] + " trap.");
				int xpgain = (CR+1)/2;
				c.println("You gain " + xpgain + " xp.");
				player.xp+=xpgain;
			}
			else{
				c.print("The " + name[type] + " trap hits you, ");
				int dam = damage.roll();
				if((d20.roll()+player.mod(player.con))>(15+CR)){
					dam /= 2;
					c.print("but you manage to stand your ground, ");
					c.print("and it only deals " + dam + " damage. ");
					int xpgain = (CR+1)/2;
					c.println("You gain " + xpgain + " xp.");
					player.xp+=xpgain;
				}
				else c.print("and it deals " + dam + " damage. ");
				player.hp -= dam;
				c.println("You now have " + player.hp + " hp.");
			}
			live = false;
			if(player.hp<=0){
				c.println("You are dead.");
			}
		}
		else{
			c.println("As you enter the next room you see evidence of a " + name[type] + " trap.");
		}
	}
}
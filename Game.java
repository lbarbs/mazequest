public class Game{
	int x;
	int y;
	final int size = 35;
	Maze maze;
	Character player;
	Console c = new Console();
	Dice d20 = new Dice(1,20);
	Dice dSize = new Dice(1,size);
	Dice d6 = new Dice(1,6);
	Dice d8 = new Dice(1,8);
	Dice d3 = new Dice(1,3);
	char a;
	int noRooms;
	int turns;
	
	
	public Game(){
		x=dSize.flip(); y=dSize.flip();
		maze = new Maze(size);
		welcome();
		player = new Character();
		maze.room[y][x].explored=true;
		c.println("______________________________________________________________________");
		noMonster();
		while(a!='q' && player.hp>0){
			c.println("______________________________________________________________________");
			turns++;
			c.println();
			if((player.level*player.level)<player.xp) player.increaseLevel();
			if(player.hp<player.maxhp){
				 if(d20.roll()+player.mod(player.con)>19){
					int hpgain = player.gainhp(d3);
					c.println("You recover " + hpgain + " hitpoints. You now have " + player.hp + "hp.");
					c.readLine("Press enter to continue");
				}
			}
			c.println("You have been alive for " + turns + " turns and have explored " + noRooms + " rooms.");
			if(d20.roll()<17) noMonster();
			else fight();
		}
		quit();
	}
	
	public void welcome(){
		c.println("");
		c.println("        #######################################");
		c.println("        #                                     #");
		c.println("        #        WELCOME TO MAZE QUEST        #");
		c.println("        #        ---------------------        #");
		c.println("        #                                     #");
		c.println("        #######################################");
		c.println("");
		c.readLine("Press enter to continue");
		c.println("");
	}
	
	public void quit(){
		c.println("");
		c.println("Thank you for playing Maze Quest.");
		c.println("");
		c.println("You were alive for " + turns + " turns and explored " + noRooms + " rooms.");
		c.println("");
		maze.drawMaze();
		c.println("");
		c.println("Your final stats:");
		player.showStats();
		c.println("");
		int score = player.xp + player.gold + player.hp - player.maxhp + noRooms;
		WriteTextFile writer = new WriteTextFile();
		writer.openFile("scores.txt",true);
		writer.writeToTextFile("Name: " + player.name + "\tLevel " + player.level + " " + player.role + "\tScore: " + score + "\tRooms: " + noRooms + "\tTurns: " + turns + ".");
		writer.writeToTextFile("Weapon: " + player.weapon.name[player.weapon.type] + "\tArmour: " + player.armour.name[player.armour.type] +".");
		writer.writeToTextFile("");
		writer.closeFile();
		c.println("You scored " + score + " points.");
		c.println("");
		c.readLine("Press enter to quit.");
	}
	
	public void noMonster(){
		c.println();
		c.print("("+(y+1)+","+(x+1)+") ");
		maze.room[y][x].describeRoom();
		displayMenu();
		a = c.readChar("     > ");
		c.println();
		switch(a){
			case 'g' : go();
				break;
			case '%' : maze.drawCheatMaze();
				break;
			case 'm' : maze.drawMaze();
				break;
			case 'e' : if (player.explosives >=1) breakWall();
				else c.println("You have run out of explosives.");
				break;
			case 't' : if (player.teleportationScrolls>0 || player.teleportationRing) teleport();
				else c.println("You have no way of teleporting.");
				break;
			case 'p' : if (player.potions>0 || player.largepotions >0 ) player.takePotion();
				else c.println("You have no potions.");
				break;
			case 'l' : if (maze.room[y][x].isItem) lootRoom();
				else c.println("There is nothing to loot in this room.");
				break;
			case 's' : player.showStats();
				break;
			case 'q' : return;
			default : c.println("Invalid choice!");
				break;
		}
		c.readLine("Press enter to continue");
	}
	
	public void displayMenu(){
		c.println("What shall we do? ");
		c.println("   g: Go somewhere ");
		c.println("   m: Show map ");
		if (player.explosives>0) c.println("   e: Explode a wall ");
		if (player.teleportationScrolls>0 || player.teleportationRing) c.println("   t: Teleport ");
		if (player.potions>0 || player.largepotions >0 ) c.println("   p: Take a health Potion ");
		if (maze.room[y][x].isItem) c.println("   l: Loot room ");
		c.println("   s: Character stats ");
		c.println("   q: Quit ");
	}
	
	public void go(){
		char d = c.readChar("Which way would you like to go? ");
		boolean ok = false;
		while(!ok){
			switch(d){
				case 'n' : ok = maze.room[y][x].nDoor;
					if(!ok) c.println("There is no door there!");
					break;
				case 'e' : ok = maze.room[y][x].eDoor;
					if(!ok) c.println("There is no door there!");
					break;
				case 's' : ok = maze.room[y][x].sDoor;
					if(!ok) c.println("There is no door there!");
					break;
				case 'w' : ok = maze.room[y][x].wDoor;
					if(!ok) c.println("There is no door there!");
					break;
				case 'q' : ok = true;
					break;
				default : c.println("Invalid choice!");
					break;
			}
			if(!ok)	{
				d = c.readChar("Which way would you like to go? ");
			}
		}
		switch(d){
			case 'n' : y--;
				c.println("You leave the room to the North.");
				break;
			case 'e' : x++;
				c.println("You leave the room to the East.");
				break;
			case 's' : y++;
				c.println("You leave the room to the South.");
				break;
			case 'w' : x--;
				c.println("You leave the room to the West.");
				break;
			case 'q' : break;
		}
		if (maze.room[y][x].isTrap){
			maze.room[y][x].trap.engageTrap(player);
		}
		if (!maze.room[y][x].explored){
			maze.room[y][x].explored=true;
			noRooms++;
		}
		
	}
	public void breakWall(){
		char d = c.readChar("Which wall would you like to blow a hole in? ");
		boolean ok = false;
		while(!ok){
			switch(d){
				case 'n' : ok = !maze.room[y][x].nDoor;
					if(!ok) c.println("There is no wall there!");
					if (y==0) {
						c.println("You cannot destroy that wall");
						ok=false;
					}
					break;
				case 'e' : ok = !maze.room[y][x].eDoor;
					if(!ok) c.println("There is no wall there!");
					if (x==(maze.size-1)) {
						c.println("You cannot destroy that wall");
						ok=false;
					}
					break;
				case 's' : ok = !maze.room[y][x].sDoor;
					if(!ok) c.println("There is no wall there!");
					if (y==(maze.size-1)) {
						c.println("You cannot destroy that wall");
						ok=false;
					}
					break;
				case 'w' : ok = !maze.room[y][x].wDoor;
					if(!ok) c.println("There is no wall there!");
					if (x==0) {
						c.println("You cannot destroy that wall");
						ok=false;
					}
					break;
				case 'q' : ok = true;
					break;
				default : c.println("Invalid choice!");
					break;
			}		
			if(!ok)	{
				d = c.readChar("Which wall would you like to blow a hole in? ");
			}
		}
		switch(d){
			case 'n' : maze.room[y][x].nDoor = true;
				maze.room[y-1][x].sDoor = true;
				break;
			case 'e' : maze.room[y][x].eDoor = true;
				maze.room[y][x+1].wDoor = true;
				break;
			case 's' : maze.room[y][x].sDoor = true;
				maze.room[y+1][x].nDoor = true;
				break;
			case 'w' : maze.room[y][x].wDoor = true;
				maze.room[y][x-1].eDoor = true;
				break;
			case 'q' : break;
		}
		player.explosives--;
		c.println("You use one of your explosives to blow a hole in a wall.");
		if((d20.roll()+player.mod(player.dex)+player.armour.dexmod)>15){
			c.println("You safely avoid the blast. ");
			c.println("You now have " + player.explosives + " explosives left.");
			if (player.explosives == 1) c.println("Use it wisely...");

		}
		else{
			int dam = d6.roll();
			player.hp-=dam;
			c.print("You get caught in the explosion and take " + dam + " damage. ");
			if(player.hp<=0) {
				c.println("You die.");
			}
			else {
				c.println("You have " + player.hp + "hp.");
				c.println("You now have " + player.explosives + " explosives left.");
				if (player.explosives == 1) c.println("Use it wisely...");
			}
		}
	}
	public void teleport(){
		if(player.teleportationRing) c.println("You activate your ring of teleportation.");
		else{
			c.println("You activate one of your scrolls of teleportation.");
			player.teleportationScrolls--;
		}
		if(d20.roll()>10){
			c.println("You fail to control the spell and you end up in a random location.");
			x=dSize.flip(); y=dSize.flip();
		}
		else{
			int xShift=c.readInteger("How far east do you want to travel? ");
			int yShift=c.readInteger("How far south do you want to travel? ");
			x+=xShift;
			y+=yShift;
			if(x>=size)x=size-1;
			if(x<0)x=0;
			if(y>=size)y=size-1;
			if(y<0)y=0;
		}
		c.println("Whoosh!");
		if (!maze.room[y][x].explored){
			maze.room[y][x].explored=true;
			noRooms++;
		}
		if(player.teleportationRing){
			if(d20.roll()>18) {
				c.println("Your ring of teleportation breaks under the stress of constant use!");
				player.teleportationRing=false;
			}
		}
	}
	public void lootRoom(){
		char opt;
		switch(maze.room[y][x].item.type){
			case 0 : 
				if(d20.roll()>10){
					c.print("You take the explosives. ");
					player.explosives++;
					c.println("You now have " + player.explosives + " explosives.");
				}
				else if((d20.roll()+player.mod(player.dex)+player.armour.dexmod)>15){
					c.println("The explosives explode, but you jump out of the way. ");
				}
				else{
					int dam = d6.roll();
					player.hp-=dam;
					c.print("The explosives explode, and you take " + dam + " damage. ");
					if(player.hp<=0) c.println("You die.");
					else c.println("You have " + player.hp + "hp.");
				}
				maze.room[y][x].isItem=false;
				break;
			case 1 : 
				c.println("You pick up the "  + maze.room[y][x].item.w.name[maze.room[y][x].item.w.type] + ".");
				do{
					opt = c.readChar("Do you want to replace your " + player.weapon.name[player.weapon.type] + " with this "  + maze.room[y][x].item.w.name[maze.room[y][x].item.w.type] + "? (y/n) > ");
				}while(opt != 'n' && opt != 'y');
				if (opt == 'y') {
					int oldtype=player.weapon.type;
					player.weapon.setWeapon(maze.room[y][x].item.w.type);
					if(oldtype==0) maze.room[y][x].isItem=false;
					else maze.room[y][x].item.w.type = oldtype;
				}
				break;
			case 2 : c.println("You pick up the "  + maze.room[y][x].item.a.name[maze.room[y][x].item.a.type] + ".");
				do{
					opt = c.readChar("Do you want to replace your " + player.armour.name[player.armour.type] + " with this "  + maze.room[y][x].item.a.name[maze.room[y][x].item.a.type] + "? (y/n) > ");
				}while(opt != 'n' && opt != 'y');
				if (opt == 'y') {
					int oldtype=player.armour.type;
					player.armour.setArmour(maze.room[y][x].item.a.type);
					maze.room[y][x].item.a.type = oldtype;
				}
				break;
			case 3 : c.println("You pick up " + maze.room[y][x].item.gold + " gold pieces");
				player.gold+=maze.room[y][x].item.gold;
				maze.room[y][x].isItem=false;
				break;
			case 4 :
				if(maze.room[y][x].item.tr){
					c.println("You pick up and put on the ring of teleportation.");
					player.teleportationRing=true;
				}
				else{
					c.println("You pick up the scroll of teleportation.");
					player.teleportationScrolls++;
				}
				maze.room[y][x].isItem=false;
				break;
			case 5 :
				if(maze.room[y][x].item.gp){
					c.println("You pick up the large bottle of potion.");
					player.largepotions++;
				}
				else{
					c.println("You pick up the bottle of potion.");
					player.potions++;
				}
				maze.room[y][x].isItem=false;
				break;
		}
	}
	public void fight(){
		Monster monster = new Monster(player.level);
		boolean runAway = false;
		boolean teleport = false;
		c.println();
		c.println("A " + monster.name[monster.type] + " appears!");
		if(d20.roll()+player.mod(player.dex)>d20.roll()+monster.mod(monster.dex)){
			c.println("The " + monster.name[monster.type] + " strikes first.");
			int mhitroll = d20.roll()+monster.mod(monster.str);
			if(mhitroll>=player.ac()){
				int mdam = monster.doDamage(mhitroll);
				player.hp-=mdam;
				c.println("The " + monster.name[monster.type] + " hits you for " + mdam + " damage. You now have " + player.hp +"hp.");
			}
			else{
				c.println("The " + monster.name[monster.type] + " tries to hit you but misses.");
			}
		}
		else{
			c.println("You take the " + monster.name[monster.type] + " by surprise.");
		}
		c.readLine("Press enter to continue");
		while(monster.hp>0 && player.hp>0 && runAway==false){
			c.println("______________________________________________________________________");
			displayFightMenu(monster);
			a = c.readChar("     > ");
			c.println();
			switch(a){
				case 'f' : 
					int hitroll = d20.roll()+player.mod(player.str);
					if(hitroll>=monster.ac){
						int dam = player.weapon.doDamage(hitroll);
						c.println("You hit the " + monster.name[monster.type] + " with your " + player.weapon.name[player.weapon.type] + " for " + dam + " damage.");
						monster.hp-=dam;
					}
					else {
						c.println("You try to hit the " + monster.name[monster.type] + " with your " + player.weapon.name[player.weapon.type] + " but miss.");
					}
					break;
				case 'r' : 
					if(d20.roll()+player.mod(player.dex)>d20.roll()+monster.mod(monster.dex)){
						c.println("You run away");
						runAway = true;
					}
					else{
						c.println("The " + monster.name[monster.type] + " catches you up.");
					}
					break;
				case 'e' : 
					if (player.explosives >=1) {
						player.explosives--;
						c.println("You use one of your explosives.");
						c.println("You now have " + player.explosives + " explosives left.");
						if (player.explosives == 1) c.println("Use it wisely...");
						int dam = d8.roll();
						if((d20.roll()+player.mod(player.dex)+player.armour.dexmod)>15){
							c.println("You safely avoid the blast. ");
						}
						else{
							player.hp-=dam;
							c.println("You get caught in the explosion and take " + dam + " damage. ");
							c.println("You have " + player.hp + "hp.");
							if(player.hp<=0) {
								c.println("You are dead.");
							}
						}
						if((d20.roll()+monster.mod(monster.dex))>15){
							c.println("The " + monster.name[monster.type] + " safely avoids the blast. ");
						}
						else{
							monster.hp-=dam;
							c.println("The " + monster.name[monster.type] + " gets caught in the explosion and takes " + dam + " damage. ");
						}
					}
					else c.println("You have run out of explosives.");
					break;
				case 't' :
					if (player.teleportationScrolls>0 || player.teleportationRing) {
						if(d20.roll()+player.mod(player.dex)>d20.roll()+monster.mod(monster.dex)){
							c.println("You run away");
							runAway = true;
							teleport = true;
						}
						else{
							c.println("The " + monster.name[monster.type] + " attacks you before you get a chance to teleport.");
						}
					}
					else c.println("You have no way of teleporting.");
					break;
				case 'p' : if (player.potions>0 || player.largepotions >0 ) player.takePotion();
					else c.println("You have no potions.");
					break;
				default : c.println("Invalid choice!");
					break;

			}
			if(monster.hp>0 && !runAway){
				int mhitroll = d20.roll()+monster.mod(monster.str);
				if(mhitroll>=player.ac()){
					int mdam = monster.doDamage(mhitroll);
					player.hp-=mdam;
					c.println("The " + monster.name[monster.type] + " hits you for " + mdam + " damage. You now have " + player.hp +"hp.");
				}
				else{
					c.println("The " + monster.name[monster.type] + " tries to hit you but misses.");
				}
				c.readLine("Press enter to continue");
			}
		}
		if(monster.hp<=0){
			int xpgain = (monster.type+2)/2;
			c.println("The " + monster.name[monster.type] + " dies! You win the fight! You gain " + xpgain + " xp.");
			player.xp+=xpgain;
			c.readLine("Press enter to continue");
		}
		if(player.hp<=0){
			c.println("You are dead.");
			c.readLine("Press enter to continue");
		}
		if(runAway){
			if(teleport) teleport();
			else go();
		}
		
	}
	public void displayFightMenu(Monster monster){
		c.println();
		c.println("You are fighting a " + monster.name[monster.type] + ".");
		c.println("What shall we do? ");
		c.println("   f: Attack the " + monster.name[monster.type] + " with your " + player.weapon.name[player.weapon.type] + ".");
		c.println("   r: Run away ");
		if(player.explosives>0) c.println("   e: Use an explosive");
		if (player.teleportationScrolls>0 || player.teleportationRing) c.println("   t: Teleport ");
		if (player.potions>0 || player.largepotions >0 ) c.println("   p: Take a health Potion ");
	}

	public static void main(String[] args){
		new Game();
	}
}
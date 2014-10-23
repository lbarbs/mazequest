public class Maze{
	Console c = new Console();
	Dice coin = new Dice(1,2);
	int size;
	Room[][] room;
	public Maze(int s){
		size = s;
		room = new Room[s][s];
		for(int i=0; i<s; i++){
			for(int j=0; j<s; j++){
				boolean nDoor;
				boolean eDoor;
				boolean sDoor;
				boolean wDoor;
				if(i==0) nDoor = false;
				else nDoor = this.room[i-1][j].sDoor;
				if(j==0) wDoor = false;
				else wDoor = this.room[i][j-1].eDoor;
				if(i==(s-1)) sDoor = false;
				else {
					if(coin.flip()==0) sDoor = true;
					else sDoor = false;
				}
				if(j==(s-1)) eDoor = false;
				else {
					if(coin.flip()==0) eDoor = true;
					else eDoor = false;
				}
				room[i][j] = new Room(nDoor, eDoor, sDoor, wDoor);
			}
		}
	}
	public void drawMaze(){
		c.print((char)218);
		for(int i=0; i<size-1; i++){
			c.print((char)196);
			c.print((char)194);
		}
		c.print((char)196);
		c.print((char)191);
		c.println();
		int i; int j;
		for(i=0; i<size-1; i++){
			c.print((char)179);
			for(j=0; j<size-1; j++){
			    if (this.room[i][j].explored){
					c.print(" ");
				}
				else{
					c.print((char)219);
				}
				if (this.room[i][j].explored || this.room[i][j+1].explored){
					if (this.room[i][j].eDoor) c.print(" ");
					else c.print((char)179);
				}
				else c.print((char)219);
			}
			if (this.room[i][j].explored){
				c.print(" ");
			}
			else{
				c.print((char)219);
			}
			c.print((char)179);
			c.println();
			c.print((char)195);
			for(j=0; j<size-1; j++){
				if (this.room[i][j].explored || this.room[i+1][j].explored){
					if (this.room[i][j].sDoor) c.print(" ");
					else c.print((char)196);
				}
				else c.print((char)219);
				if (this.room[i][j].explored || this.room[i+1][j].explored || this.room[i][j+1].explored || this.room[i+1][j+1].explored){
					c.print((char)197);
				}
				else c.print((char)219);
			}
			if (this.room[i][j].explored || this.room[i+1][j].explored){
				if (this.room[i][j].sDoor) c.print(" ");
				else c.print((char)196);
			}
			else c.print((char)219);
			c.print((char)180);
			c.println();
		}
		c.print((char)179);
		for(j=0; j<size-1; j++){
			if (this.room[i][j].explored){
				c.print(" ");
			}
			else{
				c.print((char)219);
			}
			if (this.room[i][j].explored || this.room[i][j+1].explored){
				if (this.room[i][j].eDoor) c.print(" ");
				else c.print((char)179);
			}
			else c.print((char)219);
		}
		if (this.room[i][j].explored){
			c.print(" ");
		}
		else{
			c.print((char)219);
		}
		c.print((char)179);
		c.println();
		c.print((char)192);
		for(j=0; j<size-1; j++){
			c.print((char)196);
			c.print((char)193);
		}
		c.print((char)196);
		c.print((char)217);
		c.println();
	}
	
	public void drawCheatMaze(){
		c.print((char)218);
		for(int i=0; i<size-1; i++){
			c.print((char)196);
			c.print((char)194);
		}
		c.print((char)196);
		c.print((char)191);
		c.println();
		int i; int j;
		for(i=0; i<size-1; i++){
			c.print((char)179);
			for(j=0; j<size-1; j++){
			    if (this.room[i][j].explored){
					c.print(" ");
				}
				else{
					c.print((char)176);
				}
				if (this.room[i][j].eDoor) c.print(" ");
				else c.print((char)179);
			}
			if (this.room[i][j].explored){
				c.print(" ");
			}
			else{
				c.print((char)176);
			}
			c.print((char)179);
			c.println();
			c.print((char)195);
			for(j=0; j<size-1; j++){
				if (this.room[i][j].sDoor) c.print(" ");
				else c.print((char)196);
				c.print((char)197);
			}
			if (this.room[i][j].sDoor) c.print(" ");
			else c.print((char)196);
			c.print((char)180);
			c.println();
		}
		c.print((char)179);
		for(j=0; j<size-1; j++){
			if (this.room[i][j].explored){
				c.print(" ");
			}
			else{
				c.print((char)176);
			}
			if (this.room[i][j].eDoor) c.print(" ");
			else c.print((char)179);
		}
		if (this.room[i][j].explored){
			c.print(" ");
		}
		else{
			c.print((char)176);
		}
		c.print((char)179);
		c.println();
		c.print((char)192);
		for(j=0; j<size-1; j++){
			c.print((char)196);
			c.print((char)193);
		}
		c.print((char)196);
		c.print((char)217);
		c.println();
	}
/*	public void drawMaze(){
		c.print(" ");
		for(int i=0; i<size; i++){
			c.print("_.");
		}
		c.println();
		int i; int j;
		for(i=0; i<size-1; i++){
			c.print("|");
			for(j=0; j<size-1; j++){
				if (this.room[i][j].explored || this.room[i+1][j].explored){
					if (this.room[i][j].sDoor) c.print(" ");
					else c.print("_");
				}
				else c.print("#");
				if (this.room[i][j].explored || this.room[i][j+1].explored){
					if (this.room[i][j].eDoor) c.print(".");
					else c.print("|");
				}
				else c.print(" ");
			}
			if (this.room[i][j].explored || this.room[i+1][j].explored){
				if (this.room[i][j].sDoor) c.print(" ");
				else c.print("_");
			}
			else c.print("#");
			c.print("|");
			c.println();
		}
		c.print("|");
		for( j=0; j<size-1; j++){
			c.print("_");
			if (this.room[i][j].explored || this.room[i][j+1].explored){
				if (this.room[i][j].eDoor) c.print(".");
				else c.print("|");
			}
			else c.print(".");
		}
		c.print("#");
		c.print("|");
		c.println();
	}*/
}

package com.mygdx.game;
//this class is to create the elephant object that the player uses throughout the entire game. It Keeps track of what position, level, and certain level specifications.
//Different levels have different specifications and physics, and that requires modification in the elephant class to do as seen fit.
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Elephant {
	/*Direction is what direction the elephant Sprite is facing
	 *x,y are coordinates of elephant
	 *level is what level elephant is currently on
	 *death is the current deathcounter
	 *jump count is a counter for # of times jumped for level 4
	 *rect is a bounding rect of the elephant
	 *velocity is a variable keeping rack of how many much the y component moves up or down per iteration
	 *width/height r used to create rect
	 *friendNum is a variable to determine what speech bubble to display on level 13 for friend
	 *turn is a boolean to shake the elephant while its moving
	 */
	int direction, x, y, level,count,death, jumpCount;
	Rectangle rect;
	//double upVelocity, downVelocity;
	double velocity;
	
	int width = 28;
	int height = 22;
	static int friendNum = 0;//used in level 13 to determine
	boolean turn = true;
	
	//ArrayLists, Lever, and Gate let u access everything that can be accessed in elephant class
	
	ArrayList<Block> blocks;
	ArrayList<Spike> spikes;
	ArrayList<Door> doorlist;
	ArrayList<Pipe> pipes;
	ArrayList<Bullet> bullets;
	Lever lever;
	//ArrayList<Button> butt;
	Gate gate;
	
	
	boolean jump,allowedJump;
	
	public Elephant(int dir, int x, int y, ArrayList<Block> blocks, ArrayList<Spike> spikes,ArrayList<Door> doorlist,ArrayList<Pipe> pipes,int level,int death, Lever lever, Gate gate, int friendNum, int jumpCount){
		direction = dir;
		this.x=x;
		this.y=y;
		this.level = level;
		//Level = level bescause when player starts from a save state u can start at different levels
		jump = false;
		
		this.death = death;
		allowedJump = true;
		this.blocks = blocks;
		this.spikes = spikes;
		this.doorlist = doorlist;
		this.pipes = pipes;
		rect = updateRect();
		velocity = 0;//start veloicty at 0 and if jump, velocity increases
		
		
		this.jumpCount = jumpCount;
	}
	//Self explanatory retrieval methods
	public int getX(){return x;}
	public int getY(){return y;}
	public int getWidth(){return width;}
	public int getDir(){return direction;}
	public int getLevel(){return level;}
	public int getDeath(){return death;}
	public boolean getJump(){return jump;}
	
	public int getJumpCount(){return jumpCount;}
	public static int getFriendNum(){return friendNum;}
	

	//set x,y,level,deaths when u start the game from a save state
	public void setX(int posx){
		this.x = posx;
	}
	public void setY(int posy){
		this.y = posy;
	}
	public void setLevel(int level){
		this.level = level;
	}
	public void setDeath(int death){
		this.death = death;
	}
	/*move right function
	 *if youre allowed to move right, you can move right.
	 *this is differnt when its level 12 because you cant use moveCheck  on that level due to different moving specifications
	 *
	 *In level 9 you are underwater so your horizontal movement is slowed down, but in any other case you move 4
	 *if your moving right then direction is 1 so u can display the right sprite
	 */
	public void moveRight(){
		if(moveCheck(1) && level!=12){
			if(level == 9){
				x+=2;
			}
			else{
				x+=4;
			}
			rect = updateRect();//update the rectangles once movement is made
			direction = 1;
		}
		if(level==12){
			x+=2;
			rect=updateRect();
			direction = 1;
		}
	}
	//similar process to moveRight(), but direction =0 so it can face left
	public void moveLeft(){
		if(moveCheck(0) && level!=12){
			if(level == 9){
				x-=2;
			}
			else{
				x-=4;
			}
			rect = updateRect();
			direction = 0;
		}
		if(level==12){
			x-=2;
			rect=updateRect();
			direction = 0;
		}
	}
	//l12 moveUp and down are functions solely for movement in level 12
	//no gravity so moving at same speed as moveRight and moveLeft when level 12
	//no different direction when going up and down so no changes to those
	public void l12MoveDown(){
		
		y-=2;
			
		rect = updateRect();

	}
	public void l12MoveUp(){
		y+=2;
		
		rect = updateRect();
	}
	//function set jump = true
	//also sets the up veloicty to the desired amount
	//L9 has decreased up velocity due to underwater
	//L12 has no y velocity as movement is done differently
	public void jumpJump(){
		jump = true;
		if(level!=9 && level!=12){
			velocity = 7.8;
		}
		else if(level==9){
			velocity = 3;
		}
		
	}
	//set jump = false and y velocity back to 0;
	public void unJump(){
		jump = false;
		velocity = 0;
	}
	//flag for allowed or not allowed to jump
	//if in air u end up setting it  to false as u can no longer jump
	
	public void notAllowedJump(){
		allowedJump = false;
	}
	//find out if it can jump
	public boolean isAllowedJump(){
		return allowedJump;
	}
	//when u die in level 4 reset the jump count to 0 so u can jump again
	public void setJumpCount(int count){
		jumpCount = count;
	}
	//return the rect
	public Rectangle getRect(){
		return rect;
	}
	//function to do draw the srites's rotation
	//if moving, rotate to +20 degrees, then rotate counterclockwise 40 deg to -20 deg and continue process
	public void draw(Sprite ele,boolean run){
		if (run == true){
			if(turn == true){
				ele.rotate(4);
				if(ele.getRotation() == 20){
					turn = false;
				}
			}
			
			if(turn == false){
				ele.rotate(-4);
				if(ele.getRotation() == -20){
					turn = true;
				}
			}
		}
		if (run == false){
			turn = true;
		}
	}
	//check if the game is a winState, if its in a win state the elephant cannot move or do anything
	public void level(boolean winState){
		if(level ==3 && !winState){
			if(moveCheck(5)){
				x-=3;
			}
		}
		
		//level 4 has a restriction on only one jump 
		if(level== 4){
			if (getJumpCount() >1 ){
				notAllowedJump();
			}
		}
		
		if (level == 13){// this is used for level 13 where you type the word "friend"
						 // with every letter is friend you type it increases friendnum
						 // of all the letters are typed friend num will be 6
			if(Gdx.input.isKeyJustPressed(Input.Keys.F)){
				if (friendNum ==0){
					friendNum=1;
				}
				
			}
				if(Gdx.input.isKeyJustPressed(Input.Keys.R)){
					if (friendNum ==1){
						friendNum=2;
					}
				}
					
				if(Gdx.input.isKeyJustPressed(Input.Keys.I)){
					if (friendNum ==2){
						friendNum=3;
					}
				}
					
				if(Gdx.input.isKeyJustPressed(Input.Keys.E)){
					if (friendNum ==3){
						friendNum=4;
					}
				}
				
				if(Gdx.input.isKeyJustPressed(Input.Keys.N)){
					if (friendNum ==4){
						friendNum=5;
					}
				}

				if(Gdx.input.isKeyJustPressed(Input.Keys.D)){
					if (friendNum ==5){
						friendNum=6;
					}
				}
			
				
		}
		
		
		
	}
	/*When jumpJump is called, y velocity is set to around 8
	 *In our game, the normal situation acceleration due gravity is 0.1px per iteration
	 *Intitialy the player moves up while gravity decreases the velocity
	 *when the velocity is reaches to 0, jump is set to false and it starts falling ie moving in dir(4)
	 *0 keeps decreasing into the negatives until it reaches a platform, in which case allowedJump is set to true
	 *and velocity is reset to 0;
	 */
	public void jump(){ //this method is used to make the elephant jump
		updateRect();
		if(jump == true){
			if(moveCheck(3)){
				if(level == 9){
					//in level 9, gravity is smaller due the 'water'
					velocity -=.1;
				}
				else{
					velocity -=0.3;
				}
				if(velocity < 0){
					velocity = 0;
					unJump();
				}
				y+=(int)(velocity);
				rect = updateRect();
			}
			else{
				unJump();
				//fallFall();
				velocity = 0;
			}
		}
		else{
			if(moveCheck(4)){
				if(level == 9){
					//water-> less gravity
					velocity -=.1;
				}
				else{
					velocity -=0.3;
				}
				y+=velocity;
				rect = updateRect();
			}
			else{
				velocity = 0;
				allowedJump = true;
			}
		}
		
		//System.out.println(y+" "+rect.getY());

	}
	//when level is won, the coordinates are reset to where the positions are
	//update rect so you have updates progress and increase level by 1 to advance to next level
	public void winLevel(){
		x=50;
		y = 500;
		rect = updateRect();
		level+=1;
		
	}
	//similar to winLevel, reset variables.
	//in level 4 where you should be allowed to have one jump when you die, jump count is reset to 0
	//reset whatever position of the friendNum level you were at
	//increase death counter by 1
	public void dead(){//used to signify when the elephant has died
		x = 50; //resets everything and adds to death int
		y = 500;
		setJumpCount(0);
		rect = updateRect();
		friendNum = 0;
		death+=1;
			
	}
	//need a updated version of the gate so you can use figure out where you collide with it

	public void updateGate(Gate gate){
		this.gate = gate;
	}
	//use lever to find position of it n etc
	public void updateLever(Lever lever){
		this.lever = lever;
	}
	//find out where you are in relation to lever to find the state of gate so u can retrieve the proper bounding rectangle
	public Rectangle getGateRect(){
		if(level==18){ // if the level is 18 the gate needs to be opened by the bullet
			return gate.gateState(7);
		}
		
		return gate.gateState(lever.returnFrame(getX(), getY(), getX()+getWidth(), getY(), level, lever.getOldFrame()));
		
		
	}
	public void portalUpdate(){ //this method is used to teleport the elephant from one to the other 
		if(level!=12){
			//dont use portals on level 12 as it automatically kills it due player moving down and the resttriction on touching blocks
			for(Door d : doorlist){
				if(rect.overlaps(d.getRect())){ //check if the elephant is at the door
					boolean check = Gdx.input.isKeyJustPressed(Input.Keys.DOWN);
					if(level==2){
						//controls r inverted on l2 so u have to click up instad of down
						check = Gdx.input.isKeyJustPressed(Input.Keys.UP);
					}
					if(check){
						if(d == doorlist.get(0) ){
							x = doorlist.get(3).getX()+14;
							y = doorlist.get(3).getY();
						}
						//each door has a set door is teleport too, so depending on the door, 
						//if the key is pressed, it will update the elephants x and y to the corresponding doors x and y
						if(d == doorlist.get(1) ){
							x = doorlist.get(2).getX()+14;
							y = doorlist.get(2).getY();
						}
							
						if(d == doorlist.get(2) ){
							x = doorlist.get(1).getX()+14;
							y = doorlist.get(1).getY();
						}
							
						if(d == doorlist.get(3) ){
							x = doorlist.get(0).getX()+14;
							y = doorlist.get(0).getY();
						}
					}
					
				}
			}
		}
	}
	//rectangle to update the rectangles
	public Rectangle updateRect(){
		return new Rectangle(x,y,width,height);
	}
	//when u want to do better jump/fall, use a parameter called vel for velocity
	public boolean moveCheck(int dir){ 
		if(level!=12){
			//cannot use movecheck on l12 as its movement is restricted in a method uncompatible to htis fucntion
			boolean allowed = true;
			//variable to see if allowed to move or not, true until proven false
			Rectangle test = null;
			//test rectangle to see if its alowed to collide with anything at any point
			
			if(dir == 0){//dir 0 = left
				if(level!=12){//reduntant but painstaking to remove and unindent;
					//test rectangles positions are different just to allowed to have more realistic bounding related more to sprite n less rect
					test = new Rectangle(x-5,y+2,rect.getWidth(),rect.getHeight()-2);
				}
			}
			if(dir == 1){//dir 1 = right
				if(level!=12){
					test = new Rectangle(x+5,y+2,rect.getWidth(),rect.getHeight()-2);
				}
			}
			if(dir == 3){ //dir 3 = up
				
				if(level!=12){
					//in either up or down, it adds velocity to y because up would add, and down would add a negative value;
					test = new Rectangle(x,y+(int)(velocity),rect.getWidth(),rect.getHeight());
				}
				
				//test = new Rectangle(x,y+5,rect.getWidth(),rect.getHeight());
	
			}
			if(dir == 4){ //dir 4= down
				if(level!=12){
					test = new Rectangle(x,y+(int)(velocity),rect.getWidth(),rect.getHeight());
				}
				/*
				else if(level==12){
					test = new Rectangle(x,y-5,rect.getWidth(),rect.getHeight());
				}*/
			}
			
			if(dir == 5){
				//different movement direction for level 3 heavy wind
				test = new Rectangle(x-3,y+2,rect.getWidth(),rect.getHeight()-2);	
			}
			
			for(Block b: blocks){//checks if the elephant collides with with blocks
				//if overlapped, it cant go there
				if(test.overlaps(b.getRect())){
					allowed = false; 
					
				}
			}
			
			
			if(test.overlaps(getGateRect())){
				allowed = false; //checks if the elephant is colliding with the gate
				
			}
			
			
	
	
			return allowed;
		}
		return false;
	}
	//returns height
	public int getHeight() {
		// TODO Auto-generated method stub
		return height;
	}
}

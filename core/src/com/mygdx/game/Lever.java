package com.mygdx.game;
//this class is used to create the lever the elephant needs to switch to open the gate
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;

public class Lever {
	int x,y;
	static int broken = 0; // this int is used for level 17 to check how broken the gate is
	Rectangle rect;
	int oldFrame;//used to keep the levers position even if the elephant walks away from the lever
	boolean flag = true;
	public Lever(int x, int y){
		this.x = x;	//here we create the lever object 
		this.y = y;
		rect = new Rectangle(x,y,28*2, 28);
	}
	
	public int getOldFrame(){
		return oldFrame;
	}
	public void resetFlag(){
		flag = true;
	}
	public Rectangle getRect(){
		return rect;
	}
	
	public static int getBroken(){
		return broken;
	}
	
	public static void setBroken(int broke){
		broken += broke;
	}
	
	public int returnFrame(int startX, int startY, int endX, int endY, int level, int oldFrame){
		// this method is to tell what frame the lever should be corresponding the elephants x and y to the lever  
		if(oldFrame!=-1){
			
		
			this.oldFrame = oldFrame;
		}
		
		int val;
		endY+=5;
		if(flag){
			flag = false;
			if(level == 19){
				flag = true;
				if(rect.contains(endX, endY)){
					flag = false;
				}
				
				return 7;
			}
			return 0;
		}
		
		if(level==20){
			return (0); //used to keep the gate closed even if the elephant collides with it 
		}
		
		if (level == 13){
			if(Elephant.getFriendNum() ==6){
				val = 7; //this is used to keep the gate open when the friend num is 6
				return (val);
			}
			val = 0;//used to keep the gate closed even if the elephant collides with it 
			return (val);
			
		}
		
		if (level == 17){
			if(getBroken() == 110){//broken increases by 11 with every bullet that collides with the gate
								   //set to 110 because i want 10 bullets to break the gate (11*10 == 110)
				val = 7; //opens the gate if the is broken 
				return (val);
			}
			
			val = 0; //used to keep the gate closed even if the elephant collides with it 
			return (val);
			
		}
	
		if(level!=7){
			if(rect.contains(endX, endY)){
				//setTouch(false);
				x= endX - ((int)(16.5)*28);
				//adjusts the frame of the lever when the elephant collides with the lever
				val = x/7;
				val = Math.max(0, val);
				val = Math.min(val, 7);
				
				
				
				if(level == 16){
					if(val==4){
						val =3;
					}
					if(val==5){
						val =2;
					}
					if(val==6){
						val =1;
					}
					if(val==7){
						val =0;
					}
					
				}
				
				if(level == 19){// this is used so that in level 19 the frames reversed so "opening" the lever would close the gate and vice versa
					return (7-val);
				}
				return (val);
			}
		}
		
		
		
		if(level==7){
			if(Gdx.input.isKeyPressed(Input.Keys.EQUALS)){
				return (Math.min(7, (int)(oldFrame+1))); //this is used for level 7 where the addition sign opens the gate
			}
			else if(Gdx.input.isKeyPressed(Input.Keys.MINUS)){
				return (Math.max(0, (int)(oldFrame-1))); //and the subtraction sign is used to close the gate
			}

			
			
		}
		return -1;
		
	}
	

}

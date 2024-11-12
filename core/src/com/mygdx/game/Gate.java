package com.mygdx.game;
//this class is used to create the gate object that blocks the elephant from the end pipe
import com.badlogic.gdx.math.Rectangle;

public class Gate {
	int x,y;
	Rectangle rect;
	int height=0;
	int width;
	
	
	public Gate(int x, int y){
		this.x = x;
		this.y = y;
		
	}
	
	public Rectangle gateState(int frame){ 
		//this method changes how open the gate is depending on what frame the lever is in
		if(frame!=-1){
			height = 0;
			switch(frame){//there is 7 cases because there are 7 different frames the lever can be in
						  // depending on the case the gates height will be adjusted
			case 1: height = 8;
					break;
			case 2: height = 19;
					break;
			case 3: height = 25;
					break;
			case 4: height = 31;
					break;
			case 5: height = 38;
					break;
			case 6: height = 44;
					break;
			case 7: height = 49;
					break;
			default:height = 0;
					break;
			}
		}
		
			
		width = 6;
	
		int y = this.y+height;
		int x = this.x+width;
		//width = 28 - width*2;
		//height = 56 - height; 
		rect = new Rectangle(x,y,28 - 2*width,56 - height);//creates the gate rectangle according to the height of the gate that was updated
		return rect;
		
	}
	

}

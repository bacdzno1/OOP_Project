package com.mygdx.game;
// this class is used to create the pipe objects, that the elephant collides with to win
import com.badlogic.gdx.math.Rectangle;

public class Pipe {
	int x,y;
	boolean end;
	int width;
	int height;
	Rectangle rect;
	Rectangle halfWayInRect;
	
	public Pipe(int x, int y, boolean end){
		this.x = x;
		this.y = y;
		this.end = end;// this to tell if the pipe is the starting pipe or ending pipe
		if(end){
			width = 58;
			height = 46;
		}		
		else{
			width = 46;
			height = 58;
		}
		rect = new Rectangle(x,y,width,height);
		halfWayInRect = new Rectangle(x+(int)(width/2), y, (int)(width/2), height);//this rect is used to check if the elephant 
																				   //is halfway inside the pipe so signify that it won the level
	}
	public boolean winLevel(Elephant dumbo){
		if(end){ // this method is used to check if the elephant has won the level by colliding with the halfWayInRect
			if(dumbo.getRect().overlaps(halfWayInRect)){
				return true;
			}
		}		
		return false;
		
	}
	
	public Rectangle getRect(){
		return rect;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	public boolean getWhere(){
		return end;
	}
	
	
	

}

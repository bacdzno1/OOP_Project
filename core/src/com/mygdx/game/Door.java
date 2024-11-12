package com.mygdx.game;
//This class is to create the door objects that the elephant teleports through
import com.badlogic.gdx.math.Rectangle;

public class Door{
	//36 blocks by 16, 28 by 28
	int x,y;
	int width = 56;
	int height = 56;
	int dir;
	Rectangle rectt;
	
	public Door(int x, int y){
		this.x = x;
		this.y = y;
		rectt = new Rectangle(this.x, this.y, width, height);
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public Rectangle getRect(){
		return rectt;
	}
	public boolean colliding(Elephant dumbo){
		if(dumbo.getRect().overlaps(rectt)){
			return true;
		} //this checks if the elephant is colliding with the 
		  // door, this is mainly used so I can blit the "Push Up"
		  //Or "Push Down" pictures
	return false;
		
	}
}

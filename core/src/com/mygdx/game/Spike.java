package com.mygdx.game;
// this class is used to create the spike objects that the elephant dies from
import com.badlogic.gdx.math.Rectangle;

public class Spike{
	//36 blocks by 16, 28 by 28
	int x,y;
	int width = 28;
	int height = 18;
	int dir;
	Rectangle rectt;
	
	public Spike(int x, int y, int dir){
		this.x = x;
		this.y = y; 	//here is where we create each indiviual spike object and create their rect
		this.dir = dir;
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
	public boolean dead(Elephant dumbo){
		if(dumbo.getRect().overlaps(rectt)){
			return true;
		}	//this method checks if the elephant is colliding with the spikes
			//this method is used to tell that the elephant has died
		
		return false;
	}
	public int getDir(){return dir;} 
	
}
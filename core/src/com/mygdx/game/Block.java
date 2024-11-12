package com.mygdx.game;
// this class is used to create each block object that the elephant walks on top of
import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;

public class Block{
	//36 blocks by 16, 28 by 28
	int x,y,oldx,oldy;
	int width = 28;
	int height = 28;
	Rectangle rect;
	boolean deadFlag;//flag used to check if the elephant dies
	
	public Block(int x, int y,int oldx ,int oldy){
		this.x = x;
		this.y = y;
		this.oldx=oldx; //oldx and oldy are used for level 20 so the blocks and rectangle can be redrawn at their original position 
		this.oldy=oldy;
		rect = new Rectangle(this.x, this.y, width, height);
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getOldX(){
		return oldx;
	}
	public int getOldY(){
		return oldy;
	}
	
	public void setX(int posx){
		this.x = posx;
	}
	public void setY(int posy){
		this.y = posy;
	}
	public void newRect(int posx, int posy){
		rect = new Rectangle(posx, posy, width, height);
	} //this creates a new rectangle for level 20 to make the blocks disappear 
	
	public Rectangle getRect(){
		return rect;
	}

	public boolean dead(Elephant dumbo){
		deadFlag = false;
		if(dumbo.getRect().overlaps(rect)){
			System.out.println();
			return true;
		// this method is for level 12 to check if the elephant has collided with the blocks
		// this method is used to signify if the elephant has died from colliding with the blocks in level 12
		}
			
		return false;
	}	
	
	

}

package com.mygdx.game;
//this method is used to create the bullet objects that we use for levels 17 and 18
import com.badlogic.gdx.math.Rectangle;

public class Bullet {
	int x,y,dir;
	
	int width = 8;
	int height = 8;
	Rectangle rect;
	boolean draw;
	
	
	public Bullet(int x, int y,boolean draw,int dir){
		this.x = x;
		this.y = y;
		this.draw = draw; // this boolean is used to signify if the bullet should be drawn or not
						  // when it collides with an object (ie. an block or spike) it sets draw to false which makes it stop drawing the bullet
		this.dir =dir;//this is used start to travel in the direction the elephant was initially facing
		rect = new Rectangle(this.x, this.y, width, height);
	}
	
	public int getX(){
		return x;
	}
	public int getWidth(){
		return width;
	}
	public void setX(int posx){
		this.x= posx;
	}
	public boolean getDraw(){
		return draw;
	}
	public void setDraw(boolean flag){
		this.draw = flag; //sets the draw to false or true to signify if we need to draw the bullet or not
	}
	
	public int getDir(){
		return dir; 
	}
	public void setDir(int direct){
		dir = direct; //sets our direction of the bullet so it flys in the proper direction
	}
	
	public int getY(){
		return y;
	}
	public Rectangle getRect(){
		return rect;
	}
	
	public void BullRect(int posx, int posy){
		rect = new Rectangle(posx, posy, width, height);
	}

}

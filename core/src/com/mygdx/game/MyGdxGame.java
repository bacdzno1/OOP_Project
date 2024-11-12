package com.mygdx.game;
//The premise of the game is that there is only 1 level design, the goal of the game is to flip open the switch
//and enter the end pipe. However, with every ongoing level, there is a unique alteration to the level that makes it different 
//from the previous levels


/*Breakdown of Levels and Their Solution
 *1. Simple, flip switch and open the gate
 *2. Controls are inverted
 *3. Very windy, lots of resistance when moving towards pipe. Use the wind to carry elephant to different portals and the open the lever without closing it
 *4. One jump. Fall to nearest portal, and walk to the botton right portal. Teleport and use the one jump to reach lever
 *5. Blocks and Spikes are invisible
 *6. Eveyrhting is zoomed in
 *7. Addition n Subtraction. Use +/- keys to open and close gate respectively
 *8. Upside down, similar to inverted controls but axis are inverted.
 *9. Deep sea level. Movement is slowed down including falling and jumping. Jumping restriction is also removed.
 *10.Disco level; blocks change colors randomly
 *11.Earthquake level. close ur eyes and just do level from memory
 *12.All walls are deadly, just dont touch anything and its ez. Portals dont work for the sake of making that level easier
 *13.Friend. Type in friend near the pipe to pass level
 *14.Everything is rotating. Pretty trippy
 *15.Zoomed out
 *16.Middle position. move lever to middle position via trial and error
 *17.Use spacebar to shoot the gate
 *18.use spacebar to shoot the lever once or how many times u want
 *19.level is solved so just dont solve it
 *20.really hard!! trial and error. fall to the portal, and fall on top of the gate. remove blocks that r in the way and slide into the pipe as u fall
 *21. !! the best levell 10/10 fun i promise
 */

import java.awt.geom.Point2D;

import java.util.*;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
//import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;


public class MyGdxGame extends ApplicationAdapter {
	OrthographicCamera cam;
	//have
	//public static Pipe start, end;
	
	int x,y,count,direct,level,death, jumpCount, friendNum;
	String page = "menu";
	//^very important variabel, keeps track of what page i am on for the game;
	
	
	//level death jumpcount and friendnum are variables associates with level #, death count, jumpCount, and what position of the friend pics youre at
	
	int frame = 0;
	int oldFrame = frame;
	//these variables are to find what frame the gate and lever are at. if you u leave the area, to keep the old position you have old frame to keep track of the old one
	
	float mx,my;
	//mx,my for mous pos etc
	float angleTracker = 0;
	float angle = 0;
	//above two variables are for level 14 with the rotation. Angle is increment per iteration, angletracker keeps track how much u turned so it can turn back
	float randomAngle, shakeRadius, offsetx, offsety;
	//these variables are for the earthquake level
	
	double time = 0;
	//one of the timers
	double gatePos = 0;
	double leverPos;
	//current position of lever an gate and where to draw them using the texture array
	
	long winLevelTimer, pauseTimer;
	//timers to keep track of how long i was in win state and how long i was paused
	long startTime, finishTime;
	//when i start game and when i finish game so i ca nsubtract two and get my score
	long timePaused;
	//how long i am paused so i can add this time back to the start time to keep track of the time eveb while im paused
	
	boolean jump,run, youWinState, firstTimeWin,mouseReady,musicPlaying;
	//flags for jump, running, in the youWinState, the first iteration of being in the winState
	//mouse ready is a flag and a remnant of a failed attempt at trying to get rid of the multiple click bug
	//music playing to check if im playing or not
	
	//integer n is for the txt files when i am getting the map
	//hintn is the variable going the through the hint txt file
	Integer n;
	Integer hintn;
	//background music declaration
	Music backgroundMusic;
	
	
	SpriteBatch batch, offGameBatch;
	//two different batches. batch is for drawing all game related stuff. offGame is to draw menu and timer whilst playing game
	
	//Various sprite and texture declarations
	Texture lever1, lever2, lever3, lever4, lever5, lever6, lever7, lever8;
	Texture gate1, gate2, gate3, gate4, gate5, gate6, gate7, gate8;
	Texture zero, one, two, three, four, five, six, seven, eight, nine;
	Texture colon, dot;
	Texture bBack;
	Texture winPic, whiteBGD, pausePic, winScreen;
	
	Sprite greenBlock,purpleBlock,blueBlock,redBlock ,leftPipe, downPipe,but,titlesp;
	Texture img,back,ephant,ephant2, gBlock,usp,dsp,rsp,door, pBlock, rBlock, bBlock, title, lPipe, dPipe;
	Sprite elephant, elephant2, uspik,rspik,dspik,portal;
	Texture scoreSelect, playSelect, optionsSelect;
	Texture levelReset, levelResetYes, levelResetNo;
	Texture gameMenuBack, gameMenu, menuSelect, panicSelect, pauseSelect, muteSelect;
	Texture youWinMenuButton, youWinMenuSelect;
	
	Texture highScorePic;
	
	Sprite Bubspr,Fspr,Frspr,Frispr,Friespr,Frienspr,Friendspr;
	Texture Bubtext,Ftext,Frtext,Fritext,Frietext,Frientext,Friendtext;
	
	//me and khemaka :D 
	Texture Oo,Bolisetti;
	Sprite Khem,Arvin;
	
	Sprite Upbub,Downbub;
	Texture Uptext,Downtext;
	
	Sprite blueBack;
	
	//font declaration
	BitmapFont font;
	
	
	//dumbo the elephant comes to life!!
	Elephant dumbo;
	ShapeRenderer shape;
	
	//all the array lists that keep track of any data pulled out of txt files
	ArrayList<Block> blocks;
	ArrayList<Pipe> pipes;
	ArrayList<Spike> spikes;
	ArrayList<Door> doorlist;
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	ArrayList<String> hints = new ArrayList<String>();
	
	Lever lever;
	Gate gate;
	
	Rectangle rect,playrect;
	//file handles for the map txt file and hint txt file
	FileHandle file,hintfile;
	String txtfile,txthintfile;
	
	String [] lines,hintlines;
	Sprite [] difblock;
	//the current progress load variable
	int[] load;
	
	ArrayList<Point2D> bubbles;

	@Override
	public void create (){
		
		//declaring spirte batches
		batch = new SpriteBatch();
		offGameBatch = new SpriteBatch();
		
		ephant = new Texture("yo.png");
		ephant2 = new Texture("yo2.png");
		elephant = new Sprite(ephant);
		elephant2 = new Sprite(ephant2);
		
		//retrieve all the map data
		file = Gdx.files.internal("mapData.txt");
		txtfile = file.readString();
		lines = txtfile.split("\\r?\\n");
		n = Integer.parseInt(lines[0]);
		
		//retrive hitns
		hintfile = Gdx.files.internal("hints.txt");
		txthintfile = hintfile.readString();
		hintlines = txthintfile.split("\\r?\\n");
		hintn = Integer.parseInt(hintlines[0]);
		
		//font
		font = new BitmapFont(Gdx.files.internal("font.fnt"));
		
		Oo = new Texture("Khem.png");
		Bolisetti = new Texture("Arvin.png");
		Khem = new Sprite(Oo);
		Arvin = new Sprite(Bolisetti);
		
		//any addition pictures used for the game from blocks to sprites to menubuttons
		pBlock = new Texture("purpleBlock.png");
		bBlock = new Texture("blueBlock.png");
		rBlock = new Texture("redBlock.png");
		gBlock = new Texture("greenBlock.png");
		greenBlock = new Sprite(gBlock);
		purpleBlock = new Sprite(pBlock);
		blueBlock = new Sprite(bBlock);
		redBlock = new Sprite(rBlock);
		
		title = new Texture("menu.jpg");
		playSelect = new Texture("playSelect.jpg");
		scoreSelect = new Texture("scoreSelect.jpg");
		optionsSelect = new Texture("optionsSelect.jpg");
		
		levelReset = new Texture("levelReset.png");
		levelResetYes = new Texture("levelResetYes.png");
		levelResetNo = new Texture("levelResetNo.png");
		
		gameMenuBack = new Texture("gameMenuBack.png");
		gameMenu = new Texture("gameMenu.png");
		panicSelect = new Texture("panicSelect.png");
		menuSelect = new Texture("menuSelect.png");
		pauseSelect = new Texture("pauseSelect.png");
		muteSelect = new Texture("muteSelect.png");
		
		youWinMenuButton = new Texture("youWinMenuButton.png");
		youWinMenuSelect = new Texture("youWinMenuSelect.png");
		
		highScorePic = new Texture("highScorePic.png");
		
		Uptext = new Texture("PUp.png");
		Downtext =  new Texture("PDown.png");
		Upbub = new Sprite(Uptext);
		Downbub  = new Sprite(Downtext);
		
	
		lPipe = new Texture("leftPipe.png");
		dPipe = new Texture("pipeDown.png");
		leftPipe = new Sprite(lPipe);
		downPipe = new Sprite(dPipe);
		
		gate = new Gate(31*28, 556-15*28);
		lever = new Lever((int)16.5*28, 556-7*28);
		playrect = new Rectangle(582,210,318,30);
		
		back = new Texture("back.png");
		
		door = new Texture("Door.png");
		portal = new Sprite(door);
		
		dsp = new Texture("downspik.png");
		usp = new Texture("upspik.png");
		rsp = new Texture("rightspik.png");
		uspik = new Sprite(usp);
		rspik = new Sprite(rsp);
		dspik = new Sprite(dsp);
				
		Bubtext = new Texture("bubble.png");
		
		Ftext = new Texture("F.png");
		Frtext = new Texture("FR.png");
		Fritext = new Texture("FRI.png");
		Frietext = new Texture("FRIE.png");
		Frientext = new Texture("FRIEN.png");
		Friendtext = new Texture("FRIEND.png");
		Bubspr = new Sprite(Bubtext);
		Fspr = new Sprite(Ftext);
		Frspr = new Sprite(Frtext);
		Frispr = new Sprite(Fritext);
		Friespr = new Sprite(Frietext);
		Frienspr = new Sprite(Frientext);
		Friendspr = new Sprite(Friendtext);
		
		
		
		lever1 = new Texture("lever1.png");
		lever2 = new Texture("lever2.png");
		lever3 = new Texture("lever3.png");
		lever4 = new Texture("lever4.png");
		lever5 = new Texture("lever5.png");
		lever6 = new Texture("lever6.png");
		lever7 = new Texture("lever7.png");
		lever8 = new Texture("lever8.png");
		
		gate1 = new Texture("gate1.png");
		gate2 = new Texture("gate2.png");
		gate3 = new Texture("gate3.png");
		gate4 = new Texture("gate4.png");
		gate5 = new Texture("gate5.png");
		gate6 = new Texture("gate6.png");
		gate7 = new Texture("gate7.png");
		gate8 = new Texture("gate8.png");
		
		zero = new Texture("0.png");
		one = new Texture("1.png");
		two = new Texture("2.png");
		three = new Texture("3.png");
		four = new Texture("4.png");
		five = new Texture("5.png");
		six = new Texture("6.png");
		seven = new Texture("7.png");
		eight = new Texture("8.png");
		nine = new Texture("9.png");
		
		colon = new Texture("colon.png");
		dot = new Texture("dot.png");
		
		winPic = new Texture("youWin.png");
		whiteBGD = new Texture("whiteBGD.png");
		pausePic = new Texture("pausePic.png");
		
		bBack = new Texture("blueLayer.png");
		blueBack = new Sprite(bBack);
		
		winScreen = new Texture("winScreen.png");
		
		timePaused = 0;

		//flags to see if im in the win state in order to play the animation
		//firstTimeWin is the first iteration of the win State
		youWinState = false;
		firstTimeWin = true;
		//if im running or not
		run = false;

		//music loading, looping, and playing it
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("TiTOL_BGM.mp3"));
	    backgroundMusic.setLooping(true);
	    backgroundMusic.play();
	    
	    
	    
	    
	    //this camera is very useful
	    //no clue how it works but i think it focuses on a certain portion of the screen
	    //a lot of its use was trial and error
		cam = new OrthographicCamera(1008/2,556/2);
	    cam.setToOrtho(false);
	    cam.position.set(0,0,0);
	    
	    //start level at 1, death at 0, jumpcount at 0, leverpos at 0, and shakeradius for level 11 is 0 cuz its not shaking
	    level=1;
		death = 0;
		jumpCount = 0;
		friendNum = 0;
		leverPos = 0;
		shakeRadius = 0;
		
	    cam.update();
	    
	    //retrieve all the map data for the game
	    blocks = getBlocks();
		spikes = getData1();
		doorlist =getData2();
		pipes = getPipes();
		
		load = loadCurrent();
		//load current save file
		level = load[0];
		death = load[2];
		
		//bubbles = bubblesCreate(1, false, false);
		//create the elephant
		dumbo = new Elephant(1,60,500, blocks,spikes,doorlist,pipes,level,death,lever,gate, friendNum, jumpCount);
		
	}
	@Override
	public void dispose(){
		batch.dispose();
		//dispose is a function that is called right as u click the red x
		//i save the game before i click it so when u open it u can reload again
		if(page == "game"){
			saveCurrent();
		}
		//textureAtlas.dispose();
	}
//pixmap use it
	@Override
	public void render () {
		//my page picker
		//depending on page, i run different functions which are retrun different pages for to access other apages of the game
		if(page == "menu"){
			page = menu();
		}
		//menu stuff
		else if(page == "game"){
			page = game();
		}
		else if(page == "pause"){
			page = pauseGame();
		}
		else if(page == "youWin"){
			page = youWin();
		}
		else if(page == "levelReset"){
			page = levelReset();
		}
		else if(page == "highScore"){
			page = highScore();
		}
	}
	
	private String levelReset(){
		//reseting ur prgress function
		
		//mouse pos
		mx = Gdx.input.getX();
		my = Gdx.input.getY();
		//rects for sleection
		Rectangle yesRect = new Rectangle(80,96,377,209);
		Rectangle noRect = new Rectangle(581,86,334,196);
		offGameBatch.begin();
		//yesbutton
		if(yesRect.contains(mx,556-my)){
			offGameBatch.draw(levelResetYes,0,0);
			
			if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
				resetCurrent();//code that resets the file
				offGameBatch.end();
				return "menu";
			}
			
		}
		//otherwise just reutrn menu
		else if(noRect.contains(mx,556-my)){
			offGameBatch.draw(levelResetNo,0,0);
			
			if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
				offGameBatch.end();
				return "menu";
			}
			
		}
		else{
			offGameBatch.draw(levelReset,0,0);
		}
		
		offGameBatch.end();
		return "levelReset";
		
	}
	////MY FAVOURITE!!
	
	/*this set of functions was to create bubbles at level 9 for the underwater level,
	 * but it was later used to make conffettti. confetti  bubbles :)
	 * 
	 * 
	 */
	
	public ArrayList<Point2D> bubblesCreate(int n, boolean elephant, boolean confetti){
		//enter in num of bubbles, boolean to see if the bubbles originate from elephant,
		//and to see if they are confetti or bubbles
		
		//use point2d to keep track of x,ys for bubbles
		ArrayList<Point2D> bubbles = new ArrayList<Point2D>();
		if(!confetti){
			//if its not confetti it can come here
			if(elephant){
				for(int i = 0; i < n; i++){
					//random x coordinate, from the entire map
					int random = (int)(Math.random() * 1008);
					//add the bubble to list
					bubbles.add(new Point2D.Double(random, 108));
					//y coordnate is top of the game menu, first thing u can see on the game;
				}
			}
			else{
				for(int i = 0; i < n; i++){
					//create bubbles inside of the elephant
					int random = dumbo.getX() +dumbo.getWidth()/2 + (int)(Math.random() * dumbo.getWidth()/2);
					bubbles.add(new Point2D.Double(random, dumbo.getY()+dumbo.getHeight()/2));
				}
			}
		}
		else{
			//y coordinate is top cuz its confetti and its falling
			for(int i = 0; i < n; i++){
				int random = (int)(Math.random() * 1008);
				bubbles.add(new Point2D.Double(random, 556));
			}
		}
		return bubbles;
		
	}
	public ArrayList<Point2D> bubblesUpdate(ArrayList<Point2D> bubbles, boolean confetti){
		List<Point2D> found = new ArrayList<Point2D>();
		//essentially check if they are off screen and if they are, add it to the found list.
		//and then remove them all from the bubbles
		if(!confetti){
			//if its bbbles, remove it when it reaches top
			for(Point2D point : bubbles){
			    if(point.getY()>1008){
			        found.add(point);
			    }
			}
		}
		else{
			//if its confetti remove it when they reach the bottom, cuz they fall
			for(Point2D point : bubbles){
			    if(point.getY()<0){
			        found.add(point);
			    }
			}
			
		}
		bubbles.removeAll(found);
		//remove all the ones that u found
		
		
		List<Point2D> changed = new ArrayList<Point2D>();
		//update the y and x values randomly
		for(Point2D point: bubbles){
			int randomX, randomY, randomS1;
			if(!confetti){
				//if its bubbles, randomy is positve because they rise
				randomX = (int)(Math.random() * 1)+1;
				randomY = (int)(Math.random() * 1)+1;
				randomS1 = (int)(Math.random()* 2);
				
			}
			else{
				//if its confetti y is negative cuz it goes down
				randomX = (int)(Math.random() * 1)+1;
				randomY = -((int)(Math.random() * 1)+1);
				randomS1 = (int)(Math.random()* 2);
			}
			//it can be +/- from origianl position so randomS1 changes the sign of the x
			if(randomS1 == 0){
				randomX*=-1;
			}
			//chang enewly added variables to it
			changed.add(new Point2D.Double(point.getX()+randomX, point.getY()+randomY));
		}
		bubbles = (ArrayList<Point2D>) changed;
		
		if(!confetti){
			//create 1 bubble regularly, and create 3 at the elephant
			bubbles.addAll(bubblesCreate(1,false,false));
			bubbles.addAll(bubblesCreate(3,true,false));
		}
		else{
			//create confetti
			bubbles.addAll(bubblesCreate(1,false,true));
		}
		return bubbles;
		
		
	}
	
	public void drawBubbles(ArrayList<Point2D> bubbles, boolean confetti){
		//batch.begin();
		//draw the confetti
		shape = new ShapeRenderer();
		shape.begin(ShapeType.Filled);
		shape.setColor(Color.WHITE);
		//if its not a confetti, color is white
		for(int i = 0; i<bubbles.size(); i++){
			int size = 3;
			//if its not confetti, bubble size is 3
			if(confetti){
				//else its 5
				//color can be radnomly blue red or yellow
				int random = (int)(Math.random() * 3);
				if(random == 0){
					shape.setColor(Color.BLUE);
				}
				if(random == 1){
					shape.setColor(Color.RED);
				}
				if(random == 2){
					shape.setColor(Color.YELLOW);
				}
				size = 5;
				
			}
			//draw the shape
			shape.rect((float) bubbles.get(i).getX(),(float) bubbles.get(i).getY(),size,size);
		}
		shape.end();
		//batch.end();
	}
	
		
	public String menu(){
		//mouse position
		mx = Gdx.input.getX();
		my = Gdx.input.getY();// more menu stuff
		
		//button rects
		Rectangle playRect = new Rectangle(168,66,284,97);
		Rectangle scoreRect = new Rectangle(470,66,196,97);

		Rectangle optionsRect = new Rectangle(682,66,196,97);
		
		
		offGameBatch.begin();
		if(playRect.contains(mx, 556-my)){
			offGameBatch.draw(playSelect, 0,0);
			if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
				offGameBatch.end();
				//if i start the game, i load up the currentSave file
				load = loadCurrent();
				//set the level and set how many death
				dumbo.setLevel(load[0]);
				dumbo.setDeath(load[2]);
				//using the time found, i subtract that value from current time for new start time
				
				startTime = System.currentTimeMillis() - (long)(load[1]);
				
				return "game";
			}
		}
		
		else if(scoreRect.contains(mx, 556-my)){
			offGameBatch.draw(scoreSelect,0,0);
			if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
				offGameBatch.end();
				//go to highscore page
				return "highScore";
			}
			
			
		}
		else if(optionsRect.contains(mx,556-my)){
			offGameBatch.draw(optionsSelect,0,0);
			if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
				offGameBatch.end();
				//go to levelselect page
				return "levelReset";
			}
		}
		else{
			offGameBatch.draw(title,0,0);
		}
		//System.out.print(mx);
		//System.out.println(my);
		
		offGameBatch.end();
		return "menu";
			
	}
	public String time2String(long time){
		
		// change mililiseconds into minute:seconds.milliseconds
		
		long elapsedTime = time;
		long elapsedMillis = elapsedTime/10;
		long elapsedSeconds = elapsedTime / 1000;
		long secondsDisplay = elapsedSeconds % 60;
		long elapsedMinutes = elapsedSeconds / 60;
		
		return (elapsedMinutes+":"+secondsDisplay+"."+elapsedMillis);
		
		
	}
	public String highScore(){
		Rectangle menuRect = new Rectangle(7,9,103,25);
		//just coordinates for the highscore menu
		int tx,ty,dx,dy;
		tx = 640;
		ty = 460;
		dx = 725;
		dy = 285;
		mx = Gdx.input.getX();
		my = Gdx.input.getY();// more menu stuff
		//System.out.println(mx+" "+(556-my));
		
		
		offGameBatch.begin();
		offGameBatch.draw(highScorePic, 0, 0);
		
		
		String timeString, deathString;
		int[] score = loadHighScore();
		//load up the highscore, 
		if(score[0]==(int)(Double.POSITIVE_INFINITY)){
			//if its positive infinity then time is --
			timeString = "--";
		}
		else{
			timeString = time2String(score[0]);
		}
		//System.out.println(score[0] + score[1]);
		deathString = String.valueOf(score[1]);
		//draw the times and deaths
		font.draw(offGameBatch, (timeString), tx, ty);
		font.draw(offGameBatch, (deathString), dx, dy);
		
		
		if(menuRect.contains(mx, 556-my)){
			offGameBatch.draw(youWinMenuSelect,0,0);
			if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
				offGameBatch.end();
				
				return "menu";
			}
			/*
			else{
				mouseReady = true;
			}
			*/
		}
		else{
			offGameBatch.draw(youWinMenuButton, 0, 0);
		}
		offGameBatch.end();
		return "highScore";
		
		
		
		
				
		
	}
	public String gameMenu(){
		getHints();
		if(!Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
			mouseReady = true;
		}
		//it was to help with button pressing
		
		mx = Gdx.input.getX();
		my = Gdx.input.getY();// more menu stuff
		Rectangle panicRect = new Rectangle(7,9,103,25);
		Rectangle menuRect = new Rectangle(116,9,103,25);
		Rectangle pauseRect = new Rectangle(228,9,39,25);
		Rectangle muteRect = new Rectangle(287,9,39,25);

		offGameBatch.begin();
		
		String levelstring =String.valueOf(dumbo.getLevel());  
		String deathstring =String.valueOf(dumbo.getDeath());
		//draw current level and death
		offGameBatch.draw(gameMenuBack,0,0);
		font.draw(offGameBatch, hints.get(dumbo.getLevel()-1), 20, 70);
		//draw current hint
		font.draw(offGameBatch, ("Stage: " + levelstring), 270, 98);
		font.draw(offGameBatch, ("Deaths: " + deathstring), 270, 70);
		
	
		if(panicRect.contains(mx, 556-my)){
			offGameBatch.draw(panicSelect, 0,0);
			if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
				lever.resetFlag();
				dumbo.dead();
				frame = 0;	 //if this button is pressed it does what the dead function does
				oldFrame = 0;// it will reset every value , reset the elephants x and y , and redraw the blocks in their proper spots
				for(Block b:blocks){
					b.setX(b.getOldX());
					b.setY(b.getOldY());
					b.newRect(b.getOldX(), b.getOldY());
				}
			}
			/*
			else{
				mouseReady = true;
			}
			*/
			
		}
		
		else if(menuRect.contains(mx, 556-my)){
			offGameBatch.draw(menuSelect,0,0);
			if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && mouseReady){
				offGameBatch.end();
				//save current progress when clcik menu from game
				saveCurrent();
				return "menu";
			}
			/*
			else{
				mouseReady = true;
			}
			*/
		}
		else if(pauseRect.contains(mx, 556-my)){
			offGameBatch.draw(pauseSelect, 0,0);
			if(Gdx.input.isTouched(Input.Buttons.LEFT) || Gdx.input.isKeyPressed(Input.Keys.P)){
				if(page == "game"){
					offGameBatch.end();
					pauseTimer = System.currentTimeMillis();
					return "pause";
				}
				else{
					offGameBatch.end();
					startTime = startTime + timePaused;
					timePaused = 0;
					
					return "game";
				}
			}
			/*
			else{
				mouseReady = true;
			}
			*/
		}
		else if(muteRect.contains(mx, 556-my)){
			offGameBatch.draw(muteSelect, 0,0);
			if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && mouseReady){
				backgroundMusic.setVolume(1-backgroundMusic.getVolume());
				//1-volume = 0
				//1-0 = volume
				
				
				/*
				else{
					mouseReady = true;
				}
				*/
			}
			
		}
		
		else{
			offGameBatch.draw(gameMenu,0,0);
		}
		offGameBatch.end();
		timer(false);
		
		if(page == "pause"){
			return "pause";
		}
		return "game";
		
	}
	public String youWin(){
		Rectangle menuRect = new Rectangle(7,9,103,25);
		mx = Gdx.input.getX();
		my = Gdx.input.getY();// more menu stuff
		
		
		offGameBatch.begin();
		offGameBatch.draw(winScreen, 0, 0);
		offGameBatch.draw(gameMenuBack, 0, 0);
		//offGameBatch.draw(gameMenuBack,0,0);
		
		
		if(menuRect.contains(mx, 556-my)){
			offGameBatch.draw(youWinMenuSelect,0,0);
			if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
				offGameBatch.end();
				//save the current hs
				saveHighScore(finishTime - startTime, dumbo.getDeath());
				//reset current progress
				resetCurrent();
				return "menu";
			}
			/*
			else{
				mouseReady = true;
			}
			*/
		}
		else{
			offGameBatch.draw(youWinMenuButton, 0, 0);
			
		}
		
		//draw death timer and a bunch of confetties
		String deathstring =String.valueOf(dumbo.getDeath());
		
		
		
		//font.draw(offGameBatch, hints.get(dumbo.getLevel()-1), 20, 70);
		//font.draw(offGameBatch, ("Stage: " + levelstring), 270, 98);
		font.draw(offGameBatch, ("Deaths: " + deathstring), 270, 98);
		offGameBatch.end();
		timer(true);
		bubbles = bubblesUpdate(bubbles, true);
		drawBubbles(bubbles, true);
		
		
		return "youWin";
		
		
	}
	
	public void timer(boolean end){
		//numbers array
		Texture[] numbers = {zero, one, two, three, four, five, six, seven, eight, nine};
		
		long elapsedTime;
		//if game over, then timer displays finish time - start time
		if(end){
			elapsedTime = finishTime - startTime;
		}
		else{
			//elapsed time is current time - (time + pausedTime)
			
			elapsedTime = System.currentTimeMillis() - (startTime+timePaused);
		}
		long elapsedMillis = elapsedTime/10;
		long elapsedSeconds = elapsedTime / 1000;
		long secondsDisplay = elapsedSeconds % 60;
		long elapsedMinutes = elapsedSeconds / 60;
		
		//increments of where i need to blit it
		int mili1 = 1008-56;
		int mili2 = 1008-56*2-10;
		int dotX = 1008-56*2-10*3;
		int second1 = 1008-56*3-10*3;
		int second2 = 1008-56*4-10*4;
		int colonX = 1008-56*4-10*6;
		
		int minute1 = 1008-56*5-10*6;
		int minute2 = 1008-56*6-10*7;
		
		offGameBatch.begin();
		elapsedMillis = elapsedMillis%100;
		//drawing all the things
		offGameBatch.draw(numbers[(int)(elapsedMillis%10)], mili1,0);
		offGameBatch.draw(numbers[((int)(elapsedMillis))/10], mili2,0);
		offGameBatch.draw(numbers[(int)(secondsDisplay%10)], second1, 0);
		offGameBatch.draw(numbers[((int)(secondsDisplay))/10], second2, 0);
		offGameBatch.draw(colon, colonX, 0);
		offGameBatch.draw(numbers[(int)(elapsedMinutes%10)], minute1, 0);
		offGameBatch.draw(numbers[((int)(elapsedMinutes))/10], minute2, 0);
		offGameBatch.draw(dot, dotX, 0);
		offGameBatch.end();
		
		
	}
	public String pauseGame(){
		//if i pause i start a timer, and add that timer to the start timer anad subtract the sum of from current time
		offGameBatch.begin();
		offGameBatch.draw(pausePic, 0, 0);
		offGameBatch.end();
		timePaused = System.currentTimeMillis() - pauseTimer;
		return gameMenu();
		
		
	}
	
	public void winAnimation(long timer){
		//a really ugly animation to that happens in between levels
		//i let it it accelerate by squaring the current time/50 and add that value or subtract that value depending on if its current frame
		//
		offGameBatch.begin();
		long currentTime = System.currentTimeMillis() - timer;
		int incrementWinPic = (int)((currentTime/50)*(currentTime/50)-556);
		int incrementWhiteBGD = 1024-(int)((currentTime/50)*(currentTime/50));
		offGameBatch.draw(whiteBGD, 0, incrementWhiteBGD);
		offGameBatch.draw(winPic, 0, incrementWinPic);
		
		offGameBatch.end();
	}

	
	public void winLevel(){
		//win level function
		//if won, reset variables
		if(pipes.get(1).winLevel(dumbo)){
			dumbo.winLevel();
			lever.resetFlag();
			if(dumbo.getLevel()-1 == 14){
				batch.setProjectionMatrix(cam.combined);
				cam.rotate(angleTracker+2);
				//rotoate back to sttraight up after l14
				
			}
			
			if(dumbo.getLevel()-1==8){
				//if if level 9 make bubbes
				bubbles= bubblesCreate(10,false, false);
			}
			if(dumbo.getLevel()-1 == 20){
				//make confetti
				bubbles = bubblesCreate(15, false, true);
			}
			
			if(dumbo.getLevel()-1 == 21){
				bubbles = bubblesCreate(15, false, true);
				//if win screen ie l22 make confetti
			}
			frame = 0;
			oldFrame = 0;
			//reset frames for lever and gates
			
			if(dumbo.getLevel()==21){
				for(Block b:blocks){
					b.setX(b.getOldX());
					b.setY(b.getOldY());
					b.newRect(b.getOldX(), b.getOldY());
				}
			}
		}
		
		
	}
	
	public void dead(){
		//dead checking on l12 is different from any others becausse blocks +spikes kill u
		if(dumbo.getLevel()==12){
			System.out.println(1);
			for(Block b: blocks){
				//System.out.println(2);
				//if collide it dies
				if(b.dead(dumbo)){
					System.out.println(3);
					lever.resetFlag();
					dumbo.dead();
					frame = 0;
					oldFrame = 0;
				}
			}
			
		}
		
		for(Spike s: spikes){
			if(s.dead(dumbo)){
				lever.resetFlag();
				//if it touches sikes it dies and variables reset
				dumbo.dead();
				frame = 0;
				oldFrame = 0;
				if(dumbo.getLevel() ==20){
					for(Block b:blocks){
						b.setX(b.getOldX());
						b.setY(b.getOldY());
						b.newRect(b.getOldX(), b.getOldY());
					}
				}
			}
		}
			
	}
	
	public String game(){
		Texture [] levers = {lever1, lever2, lever3, lever4, lever5, lever6, lever7, lever8};
		Texture [] gates = {gate1, gate2, gate3, gate4, gate5, gate6, gate7, gate8};
		//texture arrays for lever and gate
		
		//send the current gate and lever into dumbo, and update ht e portals
		dumbo.updateGate(gate);
		dumbo.updateLever(lever);
		dumbo.portalUpdate();
		
		Gdx.gl.glClearColor(0, 0, 0, 4);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		offGameBatch.begin();
		offGameBatch.draw(back,0,108);
		offGameBatch.end();

		


		//drawElephant();
		batch.begin();
		//all camera stuff
		if(dumbo.getLevel() == 6){
			//combine matrix, center the cam focus on dumbo, and then zoom in a to 1/4
			batch.setProjectionMatrix(cam.combined);
			//cam.lookAt(dumbo.getX()+dumbo.getWidth()/2, dumbo.getY()-dumbo.getHeight()/2, 0);
			cam.zoom = (float)0.25;
			cam.position.set(dumbo.getX()+dumbo.getWidth()/2, dumbo.getY()-dumbo.getHeight()/2, 0);
			//cam.lookAt(1008/2, (556)/2, -100);
		}
		else if(dumbo.getLevel() == 8){
			//combine matrix, set set an offset for the map so that when its upside down it will appear properly.
			//zoom is -1 which inverts it vertically
			batch.setProjectionMatrix(cam.combined);
			cam.position.set(1008/2,(556)/2+28*4,0);
			cam.zoom=(float)-1;
			//cam.normalizeUp();
			cam.lookAt(1008/2, (556)/2+28*4, 100);
			//looking at the opposite side inverts it horizontally
			//batch.setProjectionMatrix(cam.combined);
		}
		else if(dumbo.getLevel() == 14){
			//rotate the camera at two degreees every frame
			//and keep track of it on the angle tracker so i can reset it
			cam.position.set(1008/2,556/2,0);
			batch.setProjectionMatrix(cam.combined);
			angleTracker -=2;
			cam.rotate(angle);
			angle=2;
			
		}
		else if(dumbo.getLevel() == 15){
			//zoom out if level 15
			batch.setProjectionMatrix(cam.combined);
			cam.zoom = (float)5;
			cam.position.set(1008/2,556/2,0);
		}

		else{
			//reset the cam, zoom == 1, looking at -100, and position is 
			batch.setProjectionMatrix(cam.combined);
			cam.zoom = 1;
			cam.position.set(1008/2,556/2,0);
			cam.lookAt(1008/2, (556)/2, -100);
			
		}
		
		//jump();
		
		//batch.draw(back, 0, 0);
		dumbo.level(youWinState);// for the wind thing
		//enter youWinState boolean so dumbo can see if it does stuff or not
		
		if(!youWinState){
			//can only jump when not win state and level!=12
			if(dumbo.getLevel()!=12){
			
				dumbo.jump();
			}
		}
		
		for(Door d: doorlist){
			portal.setSize(56,56);
			portal.setPosition(d.getX(), d.getY());
			portal.draw(batch);
		}
		
		for (Door d: doorlist){
			if (d.colliding(dumbo)){
				if(dumbo.getLevel()!=12){
					if(dumbo.getLevel() == 2){
						Upbub.setPosition(dumbo.getX()-48,dumbo.getY()+15);
						Upbub.draw(batch);	
					}//if the level is not 12 it blits an indicator so you know what button to press to go through the doors
					else{
						Downbub.setPosition(dumbo.getX()-48,dumbo.getY()+15);
						Downbub.draw(batch);
					}
				}
			}
		}
		
		if(dumbo.getLevel() == 20){
			if (dumbo.getY()<100){
				dumbo.dead(); //if the elephant y goes below 100, the elephant will die and it will redraw the blocks
					for(Block b:blocks){
						b.setX(b.getOldX());
						b.setY(b.getOldY());
						b.newRect(b.getOldX(), b.getOldY());
					}
			}
		}
		

		if (dumbo.getLevel() ==13){
			System.out.println(dumbo.getX() + "," + dumbo.getY());
			if (dumbo.getX() >765 && dumbo.getY() <165  ){
			if (Elephant.getFriendNum() == 0){
					Bubspr.setPosition(dumbo.getX()-48,dumbo.getY()+15);
					Bubspr.draw(batch);
				}
			 //this draws the letters in the speech bubble that you have typed.
			// it figures out the letters by the freindnums
			
			if (Elephant.getFriendNum() == 1){
				Fspr.setPosition(dumbo.getX()-48,dumbo.getY()+15);
				Fspr.draw(batch);
			}
			if (Elephant.getFriendNum() == 2){
				Frspr.setPosition(dumbo.getX()-48,dumbo.getY()+15);
				Frspr.draw(batch);
			}
			if (Elephant.getFriendNum() == 3){
				Frispr.setPosition(dumbo.getX()-48,dumbo.getY()+15);
				Frispr.draw(batch);
			}
			if (Elephant.getFriendNum() == 4){
				Friespr.setPosition(dumbo.getX()-48,dumbo.getY()+15);
				Friespr.draw(batch);
			}
			if (Elephant.getFriendNum() == 5){
				Frienspr.setPosition(dumbo.getX()-48,dumbo.getY()+15);
				Frienspr.draw(batch);
			}
			if (Elephant.getFriendNum() == 6){
				Friendspr.setPosition(dumbo.getX()-48,dumbo.getY()+15);
				Friendspr.draw(batch);
			}
			}
			
		}
		
		
		//if(dumbo.getLevel() ==3){
			//dumbo.setX(-3);
		//}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.A)){level+=1;}
		if(dumbo.getLevel()!=18){
			frame = lever.returnFrame(dumbo.getX(), dumbo.getY(), dumbo.getX()+ dumbo.getWidth(), dumbo.getY(), dumbo.getLevel(), oldFrame);
		}
		
		if(dumbo.getLevel()==18){
			for(Bullet b: bullets)
			frame = lever.returnFrame(b.getX(), b.getY(), b.getX()+ b.getWidth(), b.getY(), dumbo.getLevel(), oldFrame);
		}
		
		
		
		//System.out.println(frame);
		
		if(frame!=-1){
			oldFrame = frame;
		}
		else{
			frame = oldFrame;
		}
		//leverPos = frame;
		/*if(leverPos == 8){
			leverPos = 0;
		}
		*/
		leverPos = frame;
		batch.draw(levers[(int)(leverPos)],15*28, 556-7*28);
		
		if(dumbo.getLevel() == 21){// instead of the elephant it blits Arvin and mines face
			if(dumbo.getX()<=504){
				//Arvin.setSize(40,40);
				Arvin.setPosition(dumbo.getX(),dumbo.getY());
				dumbo.draw(Arvin, true);
				Arvin.draw(batch);
				
			}
			
			if(dumbo.getX()>504){
				Khem.setPosition(dumbo.getX(),dumbo.getY());
				dumbo.draw(Khem, true);
				Khem.draw(batch);
			}
			
		}
		
		//batch.draw(back, 0, 0);
		if(dumbo.getDir() == 1){//if direction is 1, it draws the elephant looking right
			if(dumbo.getLevel() != 21){
			elephant.setSize(28,22);
			elephant.setPosition(dumbo.getX(),dumbo.getY());
			if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
				dumbo.draw(elephant,true);
			}
			else{
				dumbo.draw(elephant,false);
				elephant.setRotation(0);
			}
			elephant.draw(batch);
			}
		}
		
		if(dumbo.getDir() == 0){//if direction is 0 , it draws the elephant looking left
			if(dumbo.getLevel() != 21){
			elephant2.setSize(28,22);
			elephant2.setPosition(dumbo.getX(),dumbo.getY());
			if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
				dumbo.draw(elephant2,true);
			}
			else{
				dumbo.draw(elephant2,false);
				elephant2.setRotation(0);
			}
			
			elephant2.draw(batch);
			}
		}
		
		gatePos = leverPos;
		batch.draw(gates[(int)(gatePos)], 31*28, 556-15*28);	//leverPos+=0.25;
		/*
		 * 
		System.out.println(animation.getFrameDuration());
		
		TextureRegion currentFrame;
		
		
		oldFrame = frame;
		
		
		frame = lever.returnFrame(dumbo.getX(), dumbo.getY(), dumbo.getX()+ dumbo.getWidth(), dumbo.getY());
		
		if(frame == -1){
			//playerIdle = new Animation(0f,playerAnim.getKeyFrames());
            //playerAnim = playerIdle;
            
            leverIdle = new Animation(0.1f, animation.getKeyFrames());
			animation = leverIdle;
			currentFrame = animation.getKeyFrame((float)(oldFrame*0.1),true);
		}
		else{
		//leverIdle = new Animation(0.1f, animation.getKeyFrames());
		//animation = leverIdle;
		oldTimePassed = timepassed;
		timepassed = (float) ((float)frame*0.125);
		if(oldFrame == frame){
			timepassed = oldTimePassed;
		}
		currentFrame = animation.getKeyFrame(timepassed, true);
		
		batch.draw(currentFrame, 15*28,556-7*28);
		*/
		if(!youWinState){
			//cannot move if in youWinState
			if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
				if (dumbo.getLevel() != 2){
					dumbo.moveRight();
				}
				if (dumbo.getLevel() == 2){
					dumbo.moveLeft();
					//inverted for l2, and some for bottom
				}
			}
			if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
				if (dumbo.getLevel() != 2){
					dumbo.moveLeft();
				}
				if (dumbo.getLevel() == 2){
					dumbo.moveRight();
				}
			}
			if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
				if(dumbo.getLevel() == 2){
					if(dumbo.isAllowedJump()){
						dumbo.jumpJump();
						
						dumbo.notAllowedJump();
					}
				}
				if(dumbo.getLevel()==12){
					dumbo.l12MoveDown();
					//move differently for l12
				}
			}
			
			if(Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.UP)){
				//if its level for youo get onnly one jump, need to reset if you die
				if(dumbo.getLevel() == 4){
					if ((dumbo.getJumpCount()>0)){
						dumbo.notAllowedJump();	
						//1jump for l4
					}
					
					else{
						if(dumbo.isAllowedJump()){
							dumbo.jumpJump();
							dumbo.notAllowedJump();
						}
					}
					
					System.out.println(dumbo.getJumpCount());
					dumbo.setJumpCount(dumbo.getJumpCount() +1);
				}
				
				if(dumbo.getLevel() != 2 && dumbo.getLevel()!=12){
					if(dumbo.isAllowedJump()){
						dumbo.jumpJump();
						if(dumbo.getLevel()!=9){
							//u get a lot of jumps if l9
							dumbo.notAllowedJump();
						}
					}
				}
				else if(dumbo.getLevel()==12){
					dumbo.l12MoveUp();
				}
				
			}
		}
		if (dumbo.getLevel()==17||dumbo.getLevel()==18){
			//System.out.println(getBroken());
			flyBullet();
			checkColi();
			if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
				createBullet(dumbo.getX(),dumbo.getY());
			}
			
			for (Bullet b :bullets){
				b.BullRect(b.getX(), b.getY());
				if (b.getRect().overlaps(dumbo.getGateRect())){
					Lever.setBroken(1);
					b.setDraw(false);
					
				}
			}
		}
		for(Block b: blocks){
			if(dumbo.getLevel() ==20){
				if(dumbo.getRect().overlaps(b.getRect())){
					b.setY(2);
					b.newRect(-2, -2);
				}
			}
			if(dumbo.getLevel()!=10){
				if (dumbo.getLevel()%4 == 1 && dumbo.getLevel()!=5){
					//dont draw on l5
					greenBlock.setSize(28,28);
					greenBlock.setPosition(b.getX(), b.getY());
					greenBlock.draw(batch);
				}
				
				if (dumbo.getLevel()%4 ==2){
					redBlock.setSize(28,28);
					redBlock.setPosition(b.getX(), b.getY());
					redBlock.draw(batch);
				}
				
				if (dumbo.getLevel()%4 ==3){
					purpleBlock.setSize(28,28);
					purpleBlock.setPosition(b.getX(), b.getY());
					purpleBlock.draw(batch);
				}
				
				if (dumbo.getLevel()%4==0){
					blueBlock.setSize(28,28);
					blueBlock.setPosition(b.getX(), b.getY());
					blueBlock.draw(batch);
				}
			}
			else if(dumbo.getLevel() == 10){
				//if l10, draw random blocks
				Sprite[] blockSprites = {greenBlock, redBlock, purpleBlock, blueBlock};
				int random = (int )(Math.random() * 4);
				//System.out.println(random);
				Sprite current = blockSprites[random];
				current.setSize(28, 28);
				current.setPosition(b.getX(), b.getY());
				current.draw(batch);
				
				
			}
		}
		
		for(Spike s: spikes){
			//dont draw on l5
			//different directions draw in different orientations
			if (dumbo.getLevel()!=5){
			if (s.getDir() == 1){
				uspik.setSize(28,28);
				uspik.setPosition(s.getX(), s.getY());
				uspik.draw(batch);
			}
			if (s.getDir() == 2){
				dspik.setSize(28,28);
				dspik.setPosition(s.getX(), s.getY());
				dspik.draw(batch);
			}
			if (s.getDir() == 3){
				rspik.setSize(28,28);
				rspik.setPosition(s.getX(), s.getY());
				rspik.draw(batch);
			}
			}
		}
		for(Bullet b: bullets){
			if (b.getDraw()){
				blueBlock.setSize(8,8);
				blueBlock.setPosition(b.getX(),b.getY()+5);
				blueBlock.draw(batch);
			}
		}
		for(Pipe p: pipes){
			if(!p.getWhere()){
				downPipe.setSize(p.getWidth(), p.getHeight());
				downPipe.setPosition(p.getX(), p.getY());
				downPipe.draw(batch);
			}
			if(p.getWhere()){
				leftPipe.setSize(p.getWidth(), p.getHeight());
				leftPipe.setPosition(p.getX(), p.getY());
				leftPipe.draw(batch);
			}
		}
		batch.end();
		
		if(dumbo.getLevel() == 9){
			//draw the bbubbles in l9
			bubbles = bubblesUpdate(bubbles, false);
			drawBubbles(bubbles, false);
			/*
			try {
				timeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			//make an alpha layer on l9
			blueBack.setAlpha(0.7f);
			batch.begin();
			blueBack.draw(batch);
			batch.end();
		}
		
		if(dumbo.getLevel() == 21){
			bubbles = bubblesUpdate(bubbles, true);
			drawBubbles(bubbles, true);
			//confetti on l21
		}
		
		
		
		batch.begin();
		
		//batch.end();
		
		//batch.begin();
		/*shape.begin();
		shape.setColor(Color.RED);
		shape.rect(dumbo.updateRect().getX(),dumbo.updateRect().getY(),dumbo.updateRect().getWidth(),dumbo.updateRect().getHeight());
		shape.end();
		batch.end();*/
		if(dumbo.getLevel()==11){
			//Shake random directions
			shakeRadius = 50;
			shakeRadius *= 0.9;
			randomAngle = (float) (Math.random()*2*Math.PI);
			offsetx = (float) (Math.cos(randomAngle)*shakeRadius);
			
			offsety = (float) (Math.sin(randomAngle)*shakeRadius);
			cam.translate(offsetx, offsety);
			//earth quake
			//get random x,ys in a certain radius for a shaking effect
		}
		cam.update();
		batch.end();
		
		if(pipes.get(1).winLevel(dumbo)){
			//preliminary period of winning for the animation to happen
			if(firstTimeWin == true){
				//first iteration of win u start timer, nowehere else
				winLevelTimer = System.currentTimeMillis();
				firstTimeWin = false;
				youWinState = true;
			}
			long currentTime = System.currentTimeMillis() - winLevelTimer;
			winAnimation(winLevelTimer);
			if(currentTime>1.75*1000){
				//1.75s of win animation
				
				youWinState = false;
				firstTimeWin = true;
				
				winLevel();
			}
			
			
		}
		dead();
		if(dumbo.getLevel() == 22){
			finishTime = System.currentTimeMillis();
			//this is when you win
			return "youWin";
		}
		return gameMenu();
			
	}
	
	public void checkColi(){//this method is used to check if the bullets have collided with a block or spike
		for(Bullet b: bullets){
			for(Block bl: blocks ){ //checks if bullet collides with any block
				if (b.getRect().overlaps(bl.getRect())){
					b.setDraw(false); //if it does, it sets draw to false
				}
			}
			for(Spike s: spikes ){//checks if bullet collides with and spike
				if (b.getRect().overlaps(s.getRect())){
					b.setDraw(false);//if it does ,it sets draw to false
				}
			}
			
		}
	}
	
	public void createBullet(int posx , int posy){
		int dir = 1;
		if(dumbo.getDir() == 0){
			dir =0; //if the elephant if facing to the left the bullets will fly to the left
					// if the elephant is not facing left, the bullets will default shoot to the right
		}
		
		bullets.add(new Bullet(posx,posy+5,true,dir));
	}
	
	public void flyBullet(){
		for(Bullet b: bullets){
			if(b.getDir() == 1){ 
				b.setX(b.getX()+2);// if the bullets are facing the right , the x increase to make them fly right
			}
			
			if(b.getDir() == 0){
				b.setX(b.getX()-2); // if the bullets are facing the left , the x decreases to make them fly left
			}
			
		}
	}
	public void resetCurrent(){
		//overwrite the file with nothing
		FileHandle file = Gdx.files.local("currentProgress.txt");
		file.writeString(" ", false);
		
	}
	public void saveCurrent(){
		//make file where u save these two things
		//save the current progress onto the file
		FileHandle file = Gdx.files.local("currentProgress.txt");
		//set false to overwrite
		
		file.writeString(dumbo.getLevel()+","+(System.currentTimeMillis()-startTime)+","+dumbo.getDeath(), false);	
			
	}
	public int[] loadCurrent(){
		//load the current stuff in the file into the game
		FileHandle file = Gdx.files.local("currentProgress.txt");
		String[] currentFile = file.readString().split(",");
		//split current file
		//if it is blank, then l=1, t=0,d= 0
		int level = 1;
		int startTime = 0;
		int deathCount = 0;
		
		if(currentFile.length == 3){
			//otherwise u read the file
			level = Integer.parseInt(currentFile[0]);
			startTime = Integer.parseInt(currentFile[1]);
			deathCount = Integer.parseInt(currentFile[2]);
			
		}
		int [] ans = {level, startTime, deathCount};
		//and return the values in a int lsit
		return ans;
	}
	public void saveHighScore(long score, int death){
		//make file where u save these two but check if the ones in the file were faster/slower
		
		//to save highscore, check if the score is lower or higher. if its lower, save.
		
		if(score<loadHighScore()[0]){
			FileHandle file = Gdx.files.local("highscore.txt");
			file.writeString((System.currentTimeMillis()-startTime)+","+dumbo.getDeath(), false);
			//overwrite it
		}
		
		
	}
	public int[] loadHighScore(){
		FileHandle file = Gdx.files.local("highscore.txt");
		String[] currentFile = file.readString().split(",");
		int startTime = (int)(Double.POSITIVE_INFINITY);
		//when its blank write in positive infiinity 
		
		int deathCount = 0;
		if(currentFile.length == 2){
			//if its a valid save file, then write in the values
			startTime = Integer.parseInt(currentFile[0]);
			deathCount = Integer.parseInt(currentFile[1]);
			
		}
		//return the ans
		int[] ans = {startTime, deathCount};
		return ans;
		
		
	}
	public ArrayList<Block> getBlocks(){//this method is to used to create an ArrayList of just block objects that we will be referenced
		ArrayList<Block> blocks = new ArrayList<Block>();
		//n = Integer.parseInt(lines[0]);
		
		//this method goes through the mapData.txt and adds all the nums to an list
		for (int i = 1; i<(n+1); i++){
			
			String[] line = lines[i].split(",");
			//System.out.println(line[0]);
			
			for(int j = 0; j<36; j++){//we used the number 36 because each line in the .txt had 36 numbers
				String c = line[j];
				
				int dig = Integer.parseInt(c);
				if(dig==1){//goes through the list and if the number is a 1, it indicates that its supposed to be a block and adds it to the Block array
					blocks.add(new Block(j*28,556-(i)*28,j*28,556-(i)*28));
				}
				
			}
		}
		return blocks;
	}
	
	public ArrayList<Spike> getData1(){//this method is to used to create an ArrayList of just spikes objects that we will be referenced

		ArrayList<Spike> spikes = new ArrayList<Spike>();
		//n = Integer.parseInt(lines[0]);
	
		
		for (int i = 1; i<(n+1); i++){
			
			String[] line = lines[i].split(",");
			//System.out.println(line[0]);
			
			for(int j = 0; j<36; j++){
				String c = line[j];
				//goes through the list and if the number is a 2,3,or 4, which are its directions, it indicates that its supposed to be a spike and adds it to the Block array
				int dig = Integer.parseInt(c);
				if(dig==2){//2 indicates its a spike facing up
					spikes.add(new Spike(j*28,556-(i)*28,1));
				}
				if(dig==3){//3 indicates its a spike facing the right
					spikes.add(new Spike(j*28,556-(i)*28,2));
				}
				if(dig==4){//4 indicates its a spike facing downwards
					spikes.add(new Spike(j*28,556-(i)*28,3));
				}
			}
			 
		}
		return spikes;
	}
	
	public ArrayList<Door> getData2(){//this method is to used to create an ArrayList of just Door objects that we will be referenced
		 
		ArrayList<Door> doorlist = new ArrayList<Door>();
		//n = Integer.parseInt(lines[0]);
		for (int i = 1; i<(n+1); i++){
			String[] line = lines[i].split(",");
			//goes through the list and if the number is a 5, it indicates that its supposed to be a Door and adds it to the Block array
			for(int j = 0; j<36; j++){
				String c = line[j];
				int dig = Integer.parseInt(c);
				if(dig==5){
					doorlist.add(new Door(j*28,556-(i)*28));
				}
			}
			 
		}
		return doorlist;
	}
	public ArrayList<String> getHints(){//this method goes thorugh the hint.txt and add them to an Arraylist
		for (int i = 1; i<(21+1); i++){ // this Arraylist is used to display the proper hint and the proper level
			hints.add(hintlines[i]);
		}		
		return hints;
	}
	
	public ArrayList<Pipe> getPipes(){//this method is to used to create an ArrayList of just Pipe objects that we will be referenced
		
		//System.out.println(lines);
		//n = Integer.parseInt(lines[0]);
		ArrayList<Pipe> pipes = new ArrayList<Pipe>();
		
		for (int i = 1; i<(n+1); i++){
			
			String[] line = lines[i].split(",");
			//System.out.println(line[0]);
			//goes through the list and if the number is a 6, or 7, it indicates that its supposed to be a Door and adds it to the Block array
			for(int j = 0; j<36; j++){
				String c = line[j];
				
				int dig = Integer.parseInt(c);
				if(dig==6){//6 means its the pipe at the very beginng
					pipes.add(new Pipe(j*28+5,556-(i)*28-2, false));
				}
				if(dig==7){//7 means its the end pipe
					pipes.add(new Pipe(j*28-2,556-(i)*28-5, true));
				}
			}
		}
		return pipes;
	}
	
}


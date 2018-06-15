package GamePack;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Vector;

import javax.swing.JPanel;

import Food.Apple;
import Food.Energy;
import Food.Food;
import Food.PineApple;
import Food.RegDot;
import Food.StrawBerry;
import Ghosts.GreenGhost;
import Ghosts.PinkGhost;
import Ghosts.RedGhost;
import Ghosts.WhiteGhost;
import Ghosts.YellowGhost;
import Pacmen.AngryPacman;
import Pacmen.DefendedPacman;
import Pacmen.NicePacman;
import Pacmen.Pacman;
import Tiles.BoardTile;
import Tiles.GateTile;
import Tiles.RoadTile;
import Tiles.WallTile;

public class Board extends JPanel implements ActionListener, KeyListener { //to do what if pressed button before start game
	private BoardTile [][] boardTiles;
	private String [][] boardTilesS; 
	private PacTimer timer;
	private Vector<String> [][] neighbors;
	private int level; 
	private Pacman pacman;
	private WhiteGhost whiteGhost;
	private GreenGhost greenGhost;
	private RedGhost redGhost;
	private YellowGhost yellowGhost;
	private PinkGhost pinkGhost;
	private boolean start; 
	private boolean isFruitsOn;
	private Vector<Food> fruitsLeft;
	private Vector<Food> fruitsEaten;
	private Vector<BoardTile> fruitsTiles; 
	private int numTicksOfGame;
	private int numOfLives ;

	public Board(int level ,PacTimer timer) {
		this.level = level; 
		this.setBackground(Color.BLACK);
		this.start = true; 
		this.isFruitsOn = false; 
		this.numOfLives = 3;
		this.fruitsTiles = new Vector<>();
		this.timer =timer;
		timer.addLisenerArg(this);
		initializeFruits();
		initializeBoardTilesS();
		initializeBoard();
		initializePacman();
		inisializeNeighborsMat();
		InisializeGhosts();
		this.numTicksOfGame = 1;
		this.setFocusable(true);
		this.addKeyListener(this);
		this.setPreferredSize(new Dimension(640,640));
		this.setVisible(true);
	}

	public Pacman getPacman(){
		return this.pacman;
	}
	private void initializePacman() {
		if(level == 1) 
			this.pacman = new NicePacman(new Pair(14,16),this.boardTiles, this.boardTilesS); 
		if(level == 2)
			this.pacman = new DefendedPacman(new Pair(14,16),this.boardTiles, this.boardTilesS);
		if(level == 3)
			this.pacman = new AngryPacman(new Pair(14,16),this.boardTiles, this.boardTilesS);
	}

	private void InisializeGhosts() {

		this.greenGhost = new GreenGhost(this.boardTiles, new Pair(16,15), this.pacman, neighbors);
		this.pinkGhost = new PinkGhost(this.boardTiles, new Pair(16,17),this.pacman, neighbors);
		this.yellowGhost = new YellowGhost(this.boardTiles, new Pair(16,16),this.pacman, neighbors);
		if(level == 1 ) {
			this.redGhost = new RedGhost(this.boardTiles, new Pair(16,13),this.pacman, neighbors);
			this.whiteGhost = new WhiteGhost(this.boardTiles, new Pair(16,14),this.pacman, neighbors);
			timer.addLisenerArg(greenGhost);
			timer.addLisenerArg(pinkGhost);
			timer.addLisenerArg(whiteGhost);
		}
		if(level == 2 ) {
			this.redGhost = new RedGhost(this.boardTiles, new Pair(16,13),this.pacman, neighbors);
			this.whiteGhost = new WhiteGhost(this.boardTiles, new Pair(16,14),this.pacman, neighbors);
			timer.addLisenerArg(whiteGhost);
			timer.addLisenerArg(greenGhost);
			timer.addLisenerArg(yellowGhost);
		}
		if(level == 3 ) {
			this.redGhost = new RedGhost(this.boardTiles, new Pair(16,14),this.pacman, neighbors);
			this.whiteGhost = new WhiteGhost(this.boardTiles, new Pair(16,13),this.pacman, neighbors);
			timer.addLisenerArg(greenGhost);
			timer.addLisenerArg(redGhost);
			timer.addLisenerArg(yellowGhost);
		}	
	}
	public void initializeFruits() {
		this.fruitsLeft = new Vector<>();
		this.fruitsEaten = new Vector<>();
		if(this.level == 1) {
			for(int i=0; i< 2; i = i+1) {
				this.fruitsLeft.add(new PineApple());
			}
			for(int i=0; i< 2; i = i+1) {
				this.fruitsLeft.add(new Apple());
			}
		}
		else if(this.level == 2) {
			for(int i=0; i< 4; i = i+1) {
				this.fruitsLeft.add(new PineApple());
			}
			for(int i=0; i< 4; i = i+1) {
				this.fruitsLeft.add(new Apple());
			}
			this.fruitsLeft.add(new StrawBerry());
		}
		else {
			for(int i=0; i< 5; i = i+1) {
				this.fruitsLeft.add(new PineApple());
			}
			for(int i=0; i< 5; i = i+1) {
				this.fruitsLeft.add(new Apple());
			}
			for(int i=0; i< 2; i = i+1) {
				this.fruitsLeft.add(new StrawBerry());
			}
		}
	}
	@SuppressWarnings("unchecked")
	private void inisializeNeighborsMat() {
		neighbors=(Vector<String>[][]) new Vector[32][32];
		for(int i=0;i<32;i++) {
			for(int j=0;j<32;j++) {
				neighbors[i][j]=findneighbors(i,j);

			}
		}
	}
	private Vector<String> findneighbors(int x,int y) {
		Vector <String> possibleDirs = new Vector <>();//the frame of the board is all wals so there wont be problems of edged
		if(this.boardTilesS[x][y] == "w") {
			return null;
		}
		else {
			if(this.boardTilesS[x-1][y]!="w") //can move up
				possibleDirs.add("u");
			if(this.boardTilesS[x+1][y]!="w" & this.boardTilesS[x+1][y]!="g") //can move down
				possibleDirs.add("d");
			if(this.boardTilesS[x][y+1]!="w")//can move right
				possibleDirs.add("r");
			if(this.boardTilesS[x][y-1]!="w")//can move left
				possibleDirs.add("l");
			return possibleDirs;

		}

	}
	private void initializeBoard() {
		this.boardTiles = new BoardTile [32][32];  
		for(int i=0;i<32;i++){
			for(int j=0;j<32;j++){
				initializeBoardTile(i,j);
			}
		}
	}
	private void initializeBoardTile (int x,int y) {
		if(boardTilesS [x][y] == "w")
			boardTiles[x][y] =new WallTile(this.level, x, y);
		if(boardTilesS [x][y] == "d")
			boardTiles[x][y] =new RoadTile(x, y, true, new RegDot()); //regDot
		if(boardTilesS [x][y] == "0")
			boardTiles[x][y] =new RoadTile(x,y, false,null); //emptyTile
		if(boardTilesS[x][y] == "g")
			boardTiles [x][y] = new GateTile(x,y);
		if(boardTilesS[x][y] == "gh") // ghost on it
			boardTiles [x][y] = new RoadTile(x,y, true, null);
		if(boardTilesS[x][y] == "e") {
			boardTiles [x][y] = new RoadTile(x,y, true, new Energy());
		}
	}
	public void drawFruits() {
		Random rand = new Random();
		int x =0;
		int y=0;
		BoardTile b = this.boardTiles[x][y];
		for(int i=0; i< this.fruitsTiles.size(); i = i+1) {
			this.fruitsTiles.get(i).setFood(null);
		}
		this.fruitsTiles.clear();
		for(int i=0; i< this.fruitsLeft.size(); i = i+1) {
			while(b.getIsSomethingOn()) {
				x = rand.nextInt(32);
				y = rand.nextInt(32);
				b = this.boardTiles[x][y];
			}
			if(!b.getIsSomethingOn()) {
				b.setFood(this.fruitsLeft.get(i));
				this.fruitsTiles.add(b);
			}
		}
	}


	public int getScoreOfPlayer() {
		return this.pacman.getScore();
	}
	public boolean checkIfWinLevel() {
		if(this.pacman.getScore() > 1)
			return true;
		else
			return false;
		/*for(int i=0; i <this.boardTiles.length; i = i+1) {
			for(int j=0; j <this.boardTiles.length; j = j+1) {
				BoardTile b = boardTiles[i][j];
				if(!this.fruitsTiles.contains(b) & b.getFood()!=null) {
					return false;
				}
			}
		}
		return true;*/
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		this.requestFocusInWindow();
		int timerSpeed = this.timer.getSpeed();
		if(this.pacman.getMode().equals(Mode.DEAD)) {
			this.numOfLives = this.numOfLives - 1;
			if(this.numOfLives == 0) {
				//this.timer.stop();
			}
			else {

				repaint();
			}
		}
		else {
			if(e.getSource().equals(this.timer.getGameTimer())) {
				if(this.numTicksOfGame <12)
					checkStart();
				this.numTicksOfGame =this.numTicksOfGame + 1;
			}
			//fruits
			if(this.numTicksOfGame > 10 * timerSpeed) {
				if(this.numTicksOfGame % 6*timerSpeed == 0 ) {
					if(!this.fruitsLeft.isEmpty()) {
						drawFruits();
						this.isFruitsOn = true;
					}
					else
						this.isFruitsOn = false;
				}
				if(numTicksOfGame % 6*timerSpeed > 2*timerSpeed & numTicksOfGame% 6*timerSpeed <5*timerSpeed) { // dim
					isFruitsOn = !isFruitsOn; 
				}
				if(numTicksOfGame % 6*timerSpeed == 5*timerSpeed) { //disappear
					this.isFruitsOn = false;
				}
			}
			checkIfPacEat();
			repaint();
		}
	}
	private void checkStart() {
		if(level == 1 ) {
			if(this.numTicksOfGame == 7) 
				greenGhost.start();
			if(this.numTicksOfGame == 9) 
				pinkGhost.start();
			if(this.numTicksOfGame == 11)
				whiteGhost.start();
		}
		if(level == 2) {
			if(this.pacman.getScore() > 4000) {
				if(this.numTicksOfGame == 2) 
					greenGhost.start();
			}
			else 
			{
				if(this.numTicksOfGame == 4) 
					greenGhost.start();
			}
			if(this.numTicksOfGame == 6) 
				yellowGhost.start();
			if(this.numTicksOfGame == 9)
				whiteGhost.start();
		}
		if(level == 3) {
			if(this.pacman.getScore() > 10000) {
				greenGhost.start();
				redGhost.start();
				yellowGhost.start();		
			}
			else {
				if(this.numTicksOfGame == 4) 
					greenGhost.start();
				if(this.numTicksOfGame == 6) 
					yellowGhost.start();
				if(this.numTicksOfGame == 8)
					redGhost.start();		
			}
		}
	}

	private void revivePacman() {		
		this.pacman.initializePacman(new Pair(14,16));
		if(level == 1) {
			this.greenGhost.inisializeData(new Pair(16,15), new Pair(0,0), "u");
			this.pinkGhost.inisializeData(new Pair(16,17), new Pair(0,0), "l");
			this.whiteGhost.inisializeData(new Pair(16,14), new Pair(0,0), "r");
		}
		if(level == 2) {
			this.greenGhost.inisializeData(new Pair(16,15), new Pair(0,0), "u");
			this.yellowGhost.inisializeData(new Pair(16,16), new Pair(0,0), "l");
			this.whiteGhost.inisializeData(new Pair(16,14), new Pair(0,0), "r");
		}
		if(level == 3) {
			this.greenGhost.inisializeData(new Pair(16,15), new Pair(0,0), "u");
			this.redGhost.inisializeData(new Pair(16,14), new Pair(0,0), "r");
			this.yellowGhost.inisializeData(new Pair(16,16), new Pair(0,0), "l");
		}
		this.numTicksOfGame = 1;
	}

	public int getNumLives() {
		return this.numOfLives;
	}
	private void checkIfPacEat() {
		int i = this.pacman.getCurrentPosition().getX();
		int j = this.pacman.getCurrentPosition().getY();

		//position of pacman in matrix is for sure a RoadTile
		BoardTile pacTile = this.boardTiles[i][j];
		Food food = pacTile.getFood();
		pacTile.setFood(null);
		//if it was a fruit
		if(this.fruitsTiles.remove(pacTile)) {
		this.fruitsEaten.add(food);
		this.fruitsLeft.remove(food);
		}
	}


	@Override
	public void keyPressed(KeyEvent e) {
		this.pacman.manageMovement(e);
		checkIfPacEat();
		repaint();
	}


	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void paintComponent(Graphics g){	

		//draw board
		g.fillRect(0, 0, 640, 640);
		Image offIm = this.createImage(640 , 640);
		Graphics offGr = offIm.getGraphics();	
		for(int i=0; i<this.boardTiles.length; i = i+1) {
			for(int j=0; j<this.boardTiles.length; j =j+1) {
				offGr.drawImage(this.boardTiles[i][j].getImage(), j*20, i*20, this);
			}
		}
		g.drawImage(offIm, 0, 0, this);
		this.greenGhost.draw(this, g);
		this.whiteGhost.draw(this, g);		
		this.pinkGhost.draw(this, g);
		this.redGhost.draw(this, g);		
		this.yellowGhost.draw(this, g);
		if(this.pacman.getMode().equals(Mode.DEAD)) {
			revivePacman();
		}
		//pacman draw
		this.pacman.draw(this, g);
		//fruits draw
		for(int i=0; i<this.fruitsTiles.size(); i = i+1) {
			if(!isFruitsOn) {
				g.drawImage(RoadTile.road.getImage(), this.fruitsTiles.get(i).getY()*20, this.fruitsTiles.get(i).getX()*20, this);

			}
			else {
				g.drawImage(this.fruitsTiles.get(i).getImage(), this.fruitsTiles.get(i).getY()*20, this.fruitsTiles.get(i).getX()*20, this);
			}
		}
		if(start) {
			 g.setFont(new Font("TimesRoman", Font.PLAIN, 50));    
			    g.setColor(Color.red);
			g.drawString("Start Game", 200, 300);
			start = false;
		}
	}


	public Vector<Food> getFruitsEaten (){
		return this.fruitsEaten;
	}


	public BoardTile getBoardTile(Pair place) {
		return this.boardTiles[place.getX()][place.getY()];
	}
	public void initializeBoardTilesS() {
		if(level == 1)
			this.boardTilesS = new String[][] 
					{{"w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w"},
			{"w", "e","d","d","d","d","d","d","d","d","d","d","d","d","d","w","w","d","d","d","d","d","d","d","d","d","d","d","d","d","e","w"},
			{"w", "d","w","w","w","w","d","w","w","w","w","w","w","w","d","w","w","d","w","w","w","w","w","w","w","d","w","w","w","w","d","w"},
			{"w", "d","w","w","w","w","d","w","w","w","w","w","w","w","d","w","w","d","w","w","w","w","w","w","w","d","w","w","w","w","d","w"},
			{"w", "d","w","w","w","w","d","w","w","w","w","w","w","w","d","w","w","d","w","w","w","w","w","w","w","d","w","w","w","w","d","w"},
			{"w", "d","w","w","w","w","d","w","w","w","w","w","w","w","d","w","w","d","w","w","w","w","w","w","w","d","w","w","w","w","d","w"},
			{"w", "d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","w"},
			{"w", "d","w","w","w","w","d","w","d","w","w","w","w","w","w","w","w","w","w","w","w","w","w","d","w","d","w","w","w","w","d","w"},
			{"w", "d","w","w","w","w","d","w","d","w","w","w","w","w","w","w","w","w","w","w","w","w","w","d","w","d","w","w","w","w","d","w"},
			{"w", "d","d","d","d","d","d","w","d","d","d","d","d","d","d","w","w","d","d","d","d","d","d","d","w","d","d","d","d","d","d","w"},
			{"w", "w","w","w","w","w","d","w","w","w","w","w","w","w","0","w","w","0","w","w","w","w","w","w","w","d","w","w","w","w","w","w"},
			{"w", "w","w","w","w","w","d","w","w","w","w","w","w","w","0","w","w","0","w","w","w","w","w","w","w","d","w","w","w","w","w","w"},
			{"w", "w","w","w","w","w","d","w","0","0","0","0","0","0","0","w","w","0","0","0","0","0","0","0","w","d","w","w","w","w","w","w"},
			{"w", "w","w","w","w","w","d","w","0","w","w","w","w","w","0","w","w","0","w","w","w","w","w","0","w","d","w","w","w","w","w","w"},
			{"w", "w","w","w","w","w","d","w","0","w","w","0","0","0","0","0","0","0","0","0","0","w","w","0","w","d","w","w","w","w","w","w"},
			{"w", "w","w","w","w","w","d","0","0","w","w","0","w","g","g","g","g","g","w","w","0","w","w","0","0","d","w","w","w","w","w","w"},
			{"w", "w","w","w","w","w","d","w","0","w","w","0","w","gh","gh","gh","gh","gh","w","w","0","w","w","0","w","d","w","w","w","w","w","w"},
			{"w", "w","w","w","w","w","d","w","0","w","w","0","w","w","w","w","w","w","w","w","0","w","w","0","w","d","w","w","w","w","w","w"},
			{"w", "w","w","w","w","w","d","w","0","w","w","0","0","0","0","0","0","0","0","0","0","w","w","0","w","d","w","w","w","w","w","w"},
			{"w", "w","w","w","w","w","d","w","0","w","w","w","w","w","w","w","w","w","w","w","w","w","w","0","w","d","w","w","w","w","w","w"},
			{"w", "d","d","d","d","d","d","d","d","d","d","d","d","d","d","w","w","d","d","d","d","d","d","d","d","d","d","d","d","d","d","w"},
			{"w", "d","w","w","w","w","d","w","w","w","w","w","w","w","d","w","w","d","w","w","w","w","w","w","w","d","w","w","w","w","d","w"},
			{"w", "d","w","w","w","w","d","w","w","w","w","w","w","w","d","w","w","d","w","w","w","w","w","w","w","d","w","w","w","w","d","w"},
			{"w", "d","d","d","w","w","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","w","w","d","d","d","w"},
			{"w", "w","w","d","w","w","d","w","d","w","w","w","w","w","w","w","w","w","w","w","w","w","w","d","w","d","w","w","d","w","w","w"},
			{"w", "d","d","d","w","w","d","w","d","d","d","d","d","d","d","w","w","d","d","d","d","d","d","d","w","d","w","w","d","d","d","w"},
			{"w", "w","w","d","w","w","d","w","d","w","w","w","w","w","d","w","w","d","w","w","w","w","w","d","w","d","w","w","d","w","w","w"},
			{"w", "d","d","d","d","d","d","w","d","d","d","d","d","d","d","w","w","d","d","d","d","d","d","d","w","d","d","d","d","d","d","w"},
			{"w", "d","w","w","w","w","w","w","w","w","w","w","w","w","d","w","w","d","w","w","w","w","w","w","w","w","w","w","w","w","d","w"},
			{"w", "d","w","w","w","w","w","w","w","w","w","w","w","w","d","w","w","d","w","w","w","w","w","w","w","w","w","w","w","w","d","w"},
			{"w", "e","d","d","d","d","d","d","d","d","d","d","d","d","d","w","w","d","d","d","d","d","d","d","d","d","d","d","d","d","e","w"},
			{"w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w"}};
			else if(level == 2)
				this.boardTilesS = new String[][] 	{{"w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w"},
				{"w", "e","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","e","w"},
				{"w", "d","w","w","w","w","d","w","w","d","w","w","w","w","d","w","w","d","w","w","d","w","w","w","w","d","w","w","w","w","d","w"},
				{"w", "d","w","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","w","d","w"},
				{"w", "d","w","w","w","w","d","w","w","w","w","w","w","w","w","w","w","d","w","w","w","w","w","w","w","d","w","w","w","w","d","w"},
				{"w", "d","w","w","w","w","d","w","w","w","w","w","w","w","w","w","w","d","w","w","w","w","w","w","w","d","w","w","w","w","d","w"},
				{"w", "d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","w"},
				{"w", "d","w","w","w","w","d","w","w","w","w","w","w","w","0","w","w","0","w","w","w","w","w","w","w","d","w","w","w","w","d","w"},
				{"w", "d","w","w","w","w","d","w","w","d","d","d","w","w","0","w","w","0","w","w","d","d","d","w","w","d","w","w","w","w","d","w"},
				{"w", "d","d","d","d","d","d","w","w","0","w","0","w","w","0","w","w","0","w","w","0","w","0","w","w","d","d","d","d","d","d","w"},
				{"w", "0","w","w","w","w","d","w","w","0","w","0","w","w","0","w","w","0","w","w","0","w","0","w","w","d","w","d","w","d","w","w"},
				{"w", "0","w","w","w","w","d","w","w","0","w","0","w","w","0","w","w","0","w","w","0","w","0","w","w","d","w","d","d","d","w","w"},
				{"w", "0","w","w","w","w","d","w","0","0","0","0","0","0","0","w","w","0","0","0","0","0","0","0","w","d","w","d","w","d","w","w"},
				{"w", "d","d","d","d","d","d","w","0","w","w","w","w","w","0","w","w","0","w","w","w","w","w","0","w","d","w","d","w","d","w","w"},
				{"w", "w","w","0","w","w","d","w","0","w","w","0","0","0","0","0","0","0","0","0","0","w","w","0","w","d","w","d","w","d","w","w"},
				{"w", "d","d","d","d","d","d","0","0","w","w","0","w","g","g","g","g","g","w","w","0","w","w","0","0","d","w","d","d","d","w","w"},
				{"w", "w","w","0","w","w","d","w","0","w","w","0","w","gh","gh","gh","gh","gh","w","w","0","w","w","0","w","d","w","w","d","w","w","w"},
				{"w", "d","d","d","d","d","d","w","0","w","w","0","w","w","w","w","w","w","w","w","0","w","w","0","w","d","w","d","d","d","w","w"},
				{"w", "w","w","w","w","w","d","w","0","w","w","0","0","0","0","0","0","0","0","0","0","w","w","0","w","d","w","d","w","d","w","w"},
				{"w", "w","w","w","w","w","d","w","0","w","w","w","w","w","w","w","w","w","w","w","w","w","w","0","w","d","w","d","w","d","w","w"},
				{"w", "d","d","d","d","d","d","d","d","d","d","d","d","d","d","w","w","d","d","d","d","d","d","d","d","d","d","d","d","d","d","w"},
				{"w", "d","w","w","w","w","d","w","d","w","w","w","d","w","d","w","w","d","w","w","w","d","w","d","w","d","w","w","w","w","d","w"},
				{"w", "d","w","w","w","w","d","w","d","w","w","w","d","w","d","w","w","d","w","w","w","d","w","d","w","d","w","w","w","w","d","w"},
				{"w", "0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","w","w"},
				{"w", "w","w","d","w","w","d","w","d","w","w","w","w","w","w","w","w","w","w","w","w","w","w","d","w","d","w","w","d","w","w","w"},
				{"w", "d","d","d","w","w","d","w","d","d","d","d","d","d","d","w","w","d","d","d","d","d","d","d","w","d","w","w","d","d","d","w"},
				{"w", "w","w","d","w","w","d","w","d","w","w","w","w","w","d","w","w","d","w","w","w","w","w","d","w","d","w","w","d","w","w","w"},
				{"w", "d","d","d","d","d","d","w","d","d","d","d","d","d","d","w","w","d","d","d","d","d","d","d","w","d","d","d","d","d","d","w"},
				{"w", "d","w","w","w","w","w","w","w","w","w","w","w","w","d","w","w","d","w","w","w","w","w","w","w","w","w","w","w","w","d","w"},
				{"w", "d","w","w","w","w","w","w","w","w","w","w","w","w","d","w","w","d","w","w","w","w","w","w","w","w","w","w","w","w","d","w"},
				{"w", "e","d","d","d","d","d","d","d","d","d","d","d","d","d","w","w","d","d","d","d","d","d","d","d","d","d","d","d","d","e","w"},
				{"w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w"}};	
				else
					this.boardTilesS = new String[][]  {{"w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w"},
					{"w", "e","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","e","w"},
					{"w", "d","w","w","w","w","d","w","w","w","w","w","w","w","0","w","w","0","w","w","w","w","w","w","w","d","w","w","w","w","d","w"},
					{"w", "d","w","w","w","w","d","w","w","w","w","w","w","w","0","w","w","0","w","w","w","w","w","w","w","d","w","w","w","w","d","w"},
					{"w", "d","w","w","w","w","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","w","w","w","w","d","w"},
					{"w", "d","w","w","w","w","d","w","0","w","w","w","0","w","w","w","w","w","w","0","w","w","w","0","w","d","w","w","w","w","d","w"},
					{"w", "d","d","d","d","d","d","d","d","0","0","0","0","0","0","0","0","0","0","0","0","0","0","d","d","d","d","d","d","d","d","w"},
					{"w", "d","w","w","w","w","d","w","d","w","w","w","w","w","w","w","w","w","w","w","w","w","w","d","w","d","w","w","w","w","d","w"},
					{"w", "d","w","w","w","w","d","w","d","w","w","w","w","w","w","w","w","w","w","w","w","w","w","d","w","d","w","w","w","w","d","w"},
					{"w", "d","d","d","d","d","d","w","d","d","d","d","d","d","d","w","w","w","d","d","d","d","d","d","w","d","d","d","d","d","d","w"},
					{"w", "d","w","w","w","w","d","w","0","w","w","0","w","w","0","w","w","w","0","w","0","w","w","w","w","d","w","w","w","w","d","w"},
					{"w", "d","w","w","w","w","d","w","0","w","w","0","w","w","0","w","w","w","0","w","0","w","w","w","w","d","w","w","w","w","d","w"},
					{"w", "d","0","0","0","0","d","w","0","w","w","0","0","0","0","w","w","w","0","0","0","0","0","0","w","d","0","0","0","0","d","w"},
					{"w", "d","w","w","w","w","d","w","0","w","w","w","w","w","0","w","w","w","0","w","w","w","w","0","w","d","w","w","w","w","d","w"},
					{"w", "d","w","w","w","w","d","w","0","w","w","0","0","0","0","0","0","0","0","0","0","w","w","0","w","d","w","w","w","w","d","w"},
					{"w", "d","w","w","w","w","d","0","0","w","w","0","w","g","g","g","g","g","w","w","0","w","w","0","0","d","w","w","w","w","d","w"},
					{"w", "d","w","w","w","w","d","w","0","w","w","0","w","gh","gh","gh","gh","gh","w","w","0","w","w","0","w","d","w","w","w","w","d","w"},
					{"w", "d","0","0","0","0","d","w","0","w","w","0","w","w","w","w","w","w","w","w","0","w","w","0","w","d","0","0","0","0","d","w"},
					{"w", "d","w","w","w","w","d","w","0","w","w","0","0","0","0","0","0","0","0","0","0","w","w","0","w","d","w","w","w","w","d","w"},
					{"w", "d","w","w","w","w","d","w","0","w","w","w","w","w","w","w","w","w","w","w","w","w","w","0","w","d","w","w","w","w","d","w"},
					{"w", "d","d","d","d","d","d","d","d","d","d","d","d","d","d","w","w","d","d","d","d","d","d","d","d","d","d","d","d","d","d","w"},
					{"w", "d","w","w","w","w","d","w","w","w","w","w","w","w","d","w","w","d","w","w","w","w","w","w","w","d","w","w","w","w","d","w"},
					{"w", "d","w","w","w","w","d","w","w","w","w","w","w","w","d","w","w","d","w","w","w","w","w","w","w","d","w","w","w","w","d","w"},
					{"w", "d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","w"},
					{"w", "d","w","d","w","w","d","w","d","w","w","w","w","w","w","w","w","w","w","w","w","w","w","d","w","d","w","w","d","w","d","w"},
					{"w", "d","d","d","w","w","d","w","d","d","d","d","d","d","d","d","w","d","d","d","d","d","d","d","w","d","w","w","d","d","d","w"},
					{"w", "d","w","d","w","w","d","w","d","w","w","w","w","w","w","d","w","d","w","w","w","w","w","d","w","d","w","w","d","w","d","w"},
					{"w", "d","d","d","d","d","d","w","d","d","d","d","d","d","d","d","w","d","d","d","d","d","d","d","d","d","d","d","d","d","d","w"},
					{"w", "d","w","w","w","w","d","w","w","w","w","w","w","w","w","d","w","d","w","w","w","w","w","w","d","w","w","w","w","w","d","w"},
					{"w", "d","w","w","w","w","d","w","w","w","w","w","w","w","w","d","w","d","w","w","w","w","w","w","d","w","w","w","w","w","d","w"},
					{"w", "e","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","e","w"},
					{"w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w"}};

	}




}



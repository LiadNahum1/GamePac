package GamePack;
import java.awt.Color;
import java.awt.Container;
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

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Food.*;
import Tiles.BoardTile;
import Tiles.GateTile;
import Tiles.RoadTile;
import Tiles.WallTile;

public class Board extends JFrame implements ActionListener, KeyListener {
	private BoardTile [][] boardTiles;
	private String [][] boardTilesS; 
	private PacTimer timer;
	private Vector<String> [][] neighbors;
	private int level; 
	private Pacman pacman;
	private GreenGhost greenGhost;
	private RedGhost redGhost;
	private YellowGhost yellowGhost;
	private boolean start; 
	private boolean isFruitsOn;
	private Vector<Food> fruits; 
	private Vector<RoadTile> fruitsTiles; 
	private int numPoints; 
	private int numTicksOfGame;
	private int numOfLives = 3;

	public Board(int level) {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.level = level; 
		this.setBackground(Color.BLACK);
		this.start = true; 
		this.isFruitsOn = false; 
		this.fruitsTiles = new Vector<>();
		initializeFruits();
		initializeBoardTilesS();
		initializeBoard();
		initializePacman();
		inisializeNeighborsMat();
		InisializeGhosts();
		this.timer = new PacTimer(this ,greenGhost,redGhost,yellowGhost, this.pacman);
		this.numTicksOfGame = 1;
		this.numPoints = 0;
		this.addKeyListener(this);
		this.setSize(800,800);
		this.setVisible(true);
	}

	private void initializePacman() {
		if(level == 1) 
			this.pacman = new NicePacman(new Pair(14,16),this.boardTiles); 
		if(level == 2)
			this.pacman = new DefendedPacman(new Pair(14,16),this.boardTiles);
		if(level == 3)
			this.pacman = new AngryPacman(new Pair(14,16),this.boardTiles);
	}

	private void InisializeGhosts() {
		this.greenGhost = new GreenGhost(this.boardTiles, new Pair(16,15), this.pacman, neighbors);
		this.redGhost = new RedGhost(this.boardTiles, new Pair(16,14),this.pacman, neighbors);
		this.yellowGhost = new YellowGhost(this.boardTiles, new Pair(16,16),this.pacman, neighbors);

	}
	public void initializeFruits() {
		this.fruits = new Vector<>();
		if(this.level == 1) {
		for(int i=0; i< 2; i = i+1) {
			this.fruits.add(new PineApple());
		}
		for(int i=0; i< 2; i = i+1) {
			this.fruits.add(new Apple());
		}
		}
		else if(this.level == 2) {
			for(int i=0; i< 4; i = i+1) {
				this.fruits.add(new PineApple());
			}
			for(int i=0; i< 4; i = i+1) {
				this.fruits.add(new Apple());
			}
			this.fruits.add(new StrawBerry());
		}
		else {
			for(int i=0; i< 5; i = i+1) {
				this.fruits.add(new PineApple());
			}
			for(int i=0; i< 5; i = i+1) {
				this.fruits.add(new Apple());
			}
			for(int i=0; i< 2; i = i+1) {
				this.fruits.add(new StrawBerry());
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
			if(this.boardTilesS[x+1][y]!="w")//can move down
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
			boardTiles[x][y] =new RoadTile(x, y, new RegDot()); //regDot
		if(boardTilesS [x][y] == "0")
			boardTiles[x][y] =new RoadTile(x,y,null); //emptyTile
		if(boardTilesS[x][y] == "g")
			boardTiles [x][y] = new GateTile(x,y);
		if(boardTilesS[x][y] == "gh") {// ghost on it
			boardTiles [x][y] = new RoadTile(x,y, null);
			((RoadTile)boardTiles[x][y]).setIsSomethingOn(true);
		}
		if(boardTilesS[x][y] == "e") {
			boardTiles [x][y] = new RoadTile(x,y, new Energy());
		}
	}
	public void drawFruits() {
		Random rand = new Random();
		int x =0;
		int y=0;
		BoardTile b = this.boardTiles[x][y];
		this.fruitsTiles.clear();
		for(int i=0; i< this.fruits.size(); i = i+1) {
			while(!(b instanceof RoadTile) || ((RoadTile) b).getIsSomethingOn()) {
				x = rand.nextInt(32);
				y = rand.nextInt(32);
				b = this.boardTiles[x][y];
			}
			if(b instanceof RoadTile) {
				((RoadTile) b).setFood(this.fruits.get(i));
				this.fruitsTiles.add((RoadTile)b);
			}
		}
	}
	private void dimFruit() {
		for(int i=0; i<this.fruitsTiles.size(); i = i+1) {
			this.fruitsTiles.get(i).dimElement();
		}
	}
	private void disappearFruits() {
		for(int i=0; i<this.fruitsTiles.size(); i = i+1) {
			this.fruitsTiles.get(i).setFood(null);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(this.pacman.getMode().equals(Mode.DEAD)) {
			this.numOfLives = this.numOfLives - 1;
			if(this.numOfLives == 0) 
				endGame();
			else {
				repaint();
			}
		}
		else {
			if(e.getSource().equals(this.timer.getGameTimer())) {
				if(this.numTicksOfGame == 7) 
					greenGhost.start();
				if(this.numTicksOfGame == 9) 
					yellowGhost.start();
				if(this.numTicksOfGame == 11) 
					redGhost.start();
				this.numTicksOfGame =this.numTicksOfGame + 1;
			}
			//fruits
			if(this.numTicksOfGame % 20 == 0) {
				if(!this.fruits.isEmpty()) {
					drawFruits();
					this.isFruitsOn = true;
				}
				else
					this.isFruitsOn = false;
			}
			if(numTicksOfGame % 23 == 0 | numTicksOfGame % 25 == 0 | numTicksOfGame % 27 == 0 | numTicksOfGame % 29 == 0 ) { // dim
				dimFruit();
			}
			if(numTicksOfGame % 26 ==0) { //disappear
				disappearFruits();
				this.isFruitsOn = false;
			}
			checkIfPacEat();
			repaint();
		}

	}
	private void revivePacman() {		
		this.pacman.initializePacman(new Pair(14,16));
		this.greenGhost.inisializeData(new Pair(16,15), new Pair(0,0), "u");
		this.redGhost.inisializeData(new Pair(16,14), new Pair(0,0), "r");
		this.yellowGhost.inisializeData(new Pair(16,16), new Pair(0,0), "u");
		this.numTicksOfGame = 1;
	}

	private void endGame() {
		// TODO Auto-generated method stub

	}

	private void checkIfPacEat() {
		int i = this.pacman.getCurrentPosition().getX();
		int j = this.pacman.getCurrentPosition().getY();

		//position of pacman in matrix is for sure a RoadTile
		RoadTile pacTile = (RoadTile)this.boardTiles[i][j];
		for(int k=0; k<this.fruitsTiles.size(); k =k+1) {
			if(pacTile.equals(this.fruitsTiles.get(k))) {
				this.fruitsTiles.remove(k);
				Food food = pacTile.getFood();
				this.fruits.remove(food);
			}
		}
	}


	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			this.timer.getGameTimer().start();
		}
		else {
			this.pacman.manageMovement(e);
			checkIfPacEat();
			repaint();
		}
	}


	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	private void drawLives(Graphics g) {
		Image offIm = this.createImage(100 , 30);
		Graphics offGr = offIm.getGraphics();	
		Image im = (new ImageIcon("pictures\\boards\\lives.png")).getImage();
		for(int i=0 ;i<this.numOfLives; i = i +1) {
			offGr.drawImage(im, i*30, 0, this);
		}
		g.drawImage(offIm, 20, 650, this);
	}
	public void paint(Graphics g){	
		if(this.start) {
			//draw board
			g.fillRect(0, 0, 800, 800);
			Image offIm = this.createImage(640 , 640);
			Graphics offGr = offIm.getGraphics();	
			for(int i=0; i<this.boardTiles.length; i = i+1) {
				for(int j=0; j<this.boardTiles.length; j =j+1) {
					offGr.drawImage(this.boardTiles[i][j].getImage(), j*20, i*20, this);
				}
			}
			g.drawImage(offIm, 0, 0, this);
			start = false; 
		}
		if(this.pacman.getMode().equals(Mode.DEAD)) {
			this.start = true; 
			revivePacman();
		}

		//pacman draw
		this.pacman.draw(this, g);
		//fruits draw
		for(int i=0; i<this.fruitsTiles.size(); i = i+1) {
			g.drawImage(this.fruitsTiles.get(i).getImage(), this.fruitsTiles.get(i).getY()*20, this.fruitsTiles.get(i).getX()*20, this);
		}

		//ghosts draw
		this.greenGhost.draw(this, g);
		this.redGhost.draw(this, g);		
		this.yellowGhost.draw(this, g);

		//score draw
		g.setColor(Color.black);
		g.fillRect(400,650, 400, 150);
		g.setColor(Color.blue);
		g.setFont(new Font(Font.DIALOG_INPUT,  Font.BOLD, 30));
		g.drawString("Score: " + this.pacman.getScore(), 400, 700);

		//draw lives
		drawLives(g);

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
			{"w", "w","w","w","w","w","d","0","0","w","w","0","w","w","w","g","g","w","w","w","0","w","w","0","0","d","w","w","w","w","w","w"},
			{"w", "w","w","w","w","w","d","w","0","w","w","0","w","w","gh","gh","gh","gh","w","w","0","w","w","0","w","d","w","w","w","w","w","w"},
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
					{"w", "d","w","w","w","w","d","w","w","d","d","d","w","w","d","w","w","d","w","w","d","d","d","w","w","d","w","w","w","w","d","w"},
					{"w", "d","d","d","d","d","d","w","w","0","w","0","w","w","0","w","w","0","w","w","0","w","0","w","w","d","d","d","d","d","d","w"},
					{"w", "0","w","w","w","w","d","w","w","0","w","0","w","w","d","w","w","d","w","w","0","w","0","w","w","d","w","d","w","d","w","w"},
					{"w", "0","w","w","w","w","d","w","w","0","w","0","w","w","0","w","w","0","w","w","0","w","0","w","w","d","w","d","d","d","w","w"},
					{"w", "0","w","w","w","w","d","w","0","0","0","0","0","0","d","w","w","d","0","0","0","0","0","0","w","d","w","d","w","d","w","w"},
					{"w", "d","d","d","d","d","d","w","0","w","w","w","w","w","0","w","w","0","w","w","w","w","w","0","w","d","w","d","w","d","w","w"},
					{"w", "w","w","0","w","w","d","w","0","w","w","0","0","0","d","0","0","d","0","0","0","w","w","0","w","d","w","d","w","d","w","w"},
					{"w", "d","d","d","d","d","d","0","0","w","w","0","w","w","w","g","g","w","w","w","0","w","w","0","0","d","w","d","d","d","w","w"},
					{"w", "w","w","0","w","w","d","w","0","w","w","0","w","w","gh","gh","gh","gh","w","w","0","w","w","0","w","d","w","w","d","w","w","w"},
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
					{"w", "d","d","d","d","d","d","w","d","d","d","d","d","d","d","w","w","d","d","d","d","d","d","d","w","d","d","d","d","d","d","w"},
					{"w", "d","w","w","w","w","d","w","0","w","w","0","w","w","0","w","w","0","w","w","0","w","w","w","w","d","w","w","w","w","d","w"},
					{"w", "d","w","w","w","w","d","w","0","w","w","0","w","w","0","w","w","0","w","w","0","w","w","w","w","d","w","w","w","w","d","w"},
					{"w", "d","0","0","0","0","d","w","0","w","w","0","0","0","0","w","w","0","0","0","0","0","0","0","w","d","0","0","0","0","d","w"},
					{"w", "d","w","w","w","w","d","w","0","w","w","w","w","w","0","w","w","0","w","w","w","w","w","0","w","d","w","w","w","w","d","w"},
					{"w", "d","w","w","w","w","d","w","0","w","w","0","0","0","0","0","0","0","0","0","0","w","w","0","w","d","w","w","w","w","d","w"},
					{"w", "d","w","w","w","w","d","0","0","w","w","0","w","w","w","g","g","w","w","w","0","w","w","0","0","d","w","w","w","w","d","w"},
					{"w", "d","w","w","w","w","d","w","0","w","w","0","w","w","gh","gh","gh","gh","w","w","0","w","w","0","w","d","w","w","w","w","d","w"},
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

/*	public void initializeBoardTilesS() {
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
			{"w", "w","w","w","w","w","d","0","0","w","w","0","w","w","w","g","g","w","w","w","0","w","w","0","0","d","w","w","w","w","w","w"},
			{"w", "w","w","w","w","w","d","w","0","w","w","0","w","w","gh","gh","gh","gh","w","w","0","w","w","0","w","d","w","w","w","w","w","w"},
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

	}
	*/
	public static void main(String[]args) {
		new Board(1);
	}

}



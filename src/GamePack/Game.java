package GamePack;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Vector;

import javax.swing.JFrame;

import Food.*;
import Tiles.BoardTile;
import Tiles.GateTile;
import Tiles.RoadTile;
import Tiles.WallTile;

public class Game extends JFrame implements ActionListener, KeyListener {
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
	private Vector<Food> fruits; 
	private RoadTile fruitsTile; 

	public static void main(String[]args) {
		new Game(1);
	}


	public Game(int level) {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.level = level; 
		this.setBackground(Color.BLACK);
		this.setSize(800,800);
	
		this.start = true; 
		this.fruitsTile = new RoadTile(null);
		initializeFruits();
		initializeBoardTilesS();
		initializeBoard();
		if(level == 1) {
			this.pacman = new NicePacman(new Pair(400,350),this.boardTiles); 
		}
		inisializeNeighborsMat();
		InisializeGhosts();
		this.timer = new PacTimer(this,greenGhost,redGhost,yellowGhost);
		this.addKeyListener(this);
		this.setVisible(true);
	}

	private void InisializeGhosts() {
		this.greenGhost = new GreenGhost(new Pair(375,400),pacman, new Pair(16,15), neighbors);
		this.redGhost = new RedGhost(new Pair(350,400),pacman, new Pair(16,14), neighbors);
		this.yellowGhost = new YellowGhost(new Pair(400,400),pacman, new Pair(16,16), neighbors);
	}

	public void initializeFruits() {
		this.fruits = new Vector<>();
		//for level one
		for(int i=0; i< 2; i = i+1) {
			this.fruits.add(new PineApple());
		}
		for(int i=0; i< 2; i = i+1) {
			this.fruits.add(new Apple());
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
	private Vector<String> findneighbors(int curY,int curX) {
		if(this.boardTilesS[curY][curX] == "w") 
			return null;
		else {
			Vector <String> possibleDirs = new Vector <>();//the frame of the board is all wals so there wont be problems of edged
			if(this.boardTilesS[curY+1][curX]!="w") //can move up
				possibleDirs.add("u");
			if(this.boardTilesS[curY-1][curX]!="w")//can move down
				possibleDirs.add("d");
			if(this.boardTilesS[curY][curX+1]!="w")//can move right
				possibleDirs.add("r");
			if(this.boardTilesS[curY][curX-1]!="w")//can move left
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
			boardTiles[x][y] =new WallTile(this.level);
		if(boardTilesS [x][y] == "d")
			boardTiles[x][y] =new RoadTile(new RegDot()); //regDot
		if(boardTilesS [x][y] == "0")
			boardTiles[x][y] =new RoadTile(null); //emptyTile
		if(boardTilesS[x][y] == "g")
			boardTiles [x][y] = new GateTile();
	}

	public void drawFruits() {
		Random rand = new Random();
		int i =0;
		int j=0;
		BoardTile b = this.boardTiles[i][j];
		while(!(b instanceof RoadTile) || ((RoadTile) b).getIsSomethingOn()) {
			i = rand.nextInt(32);
			j = rand.nextInt(32);
			b = this.boardTiles[i][j];
		}
		if(b instanceof RoadTile) {
			((RoadTile) b).setFood(this.fruits.remove(0));
			this.fruitsTile= (RoadTile)b;
		}
	}
	private void dimFruit() {
		if(this.fruitsTile.getIsSomethingOn()) {
			this.fruitsTile.dimElement();
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(this.timer.getGameTimer())) {
			if(this.timer.getNumTicksGame() == 7) {
				this.timer.getGreenGhostsTimer().start();
				System.out.println("gren");}
			if(this.timer.getNumTicksGame() == 10)
				this.timer.getRedGhostsTimer().start();
			if(this.timer.getNumTicksGame() == 13)
				this.timer.getYellowGhostsTimer().start();
		}
		if(e.getSource().equals(this.timer.getDrowTimer())){
			repaint();
		}
			if(e.getSource().equals(this.timer.getPacmanTimer())) {
			this.pacman.move();
			repaint();
		}
		if(e.getSource().equals(this.timer.getFruitTimer())) {
			if(this.timer.getNumTicksFruit() == 0) {
				
				if(this.fruits.isEmpty()) {
					this.timer.getFruitTimer().stop();
				}
				else
					drawFruits();
			}
			if(this.timer.getNumTicksFruit() >= 4 & this.timer.getNumTicksFruit() < 10) { //dim
				dimFruit();
			}
			if(this.timer.getNumTicksFruit() == 10) {//disappear
				this.fruitsTile.setFood(null);
			}
		}
	}


	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			this.timer.start();
		}
		else {
			this.pacman.manageMovement(e);
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
	
	public void paint(Graphics g){	
		if(this.start) {
			for(int i=0; i<this.boardTiles.length; i = i+1) {
				for(int j=0; j<this.boardTiles.length; j =j+1) {
					g.drawImage(this.boardTiles[i][j].getImage(), j*25, i*25, this);
				}
			}
			start = false; 
		}
		this.pacman.draw(this, g);
		this.greenGhost.draw(this, g);
		this.redGhost.draw(this, g);		
		this.yellowGhost.draw(this, g);
	}

	public BoardTile getBoardTile(Pair place) {
		return this.boardTiles[place.getX()][place.getY()];
	}
	
	public void initializeBoardTilesS() {
		this.boardTilesS = new String[][] 
				{{"w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w"},
			{"w", "d","d","d","d","d","d","d","d","d","d","d","d","d","d","w","w","d","d","d","d","d","d","d","d","d","d","d","d","d","d","w"},
			{"w", "d","w","w","w","w","d","w","w","w","w","w","w","w","d","w","w","d","w","w","w","w","w","w","w","d","w","w","w","w","d","w"},
			{"w", "d","w","w","w","w","d","w","w","w","w","w","w","w","d","w","w","d","w","w","w","w","w","w","w","d","w","w","w","w","d","w"},
			{"w", "d","w","w","w","w","d","w","w","w","w","w","w","w","d","w","w","d","w","w","w","w","w","w","w","d","w","w","w","w","d","w"},
			{"w", "d","w","w","w","w","d","w","w","w","w","w","w","w","d","w","w","d","w","w","w","w","w","w","w","d","w","w","w","w","d","w"},
			{"w", "d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","w"},
			{"w", "d","w","w","w","w","d","w","d","w","w","w","w","w","w","w","w","w","w","w","w","w","w","d","w","d","w","w","w","w","d","w"},
			{"w", "d","w","w","w","w","d","w","d","w","w","w","w","w","w","w","w","w","w","w","w","w","w","d","w","d","w","w","w","w","d","w"},
			{"w", "d","d","d","d","d","d","w","d","d","d","d","d","d","d","w","w","d","d","d","d","d","d","d","w","d","w","w","w","w","d","w"},
			{"w", "w","w","w","w","w","d","w","w","w","w","w","w","w","0","w","w","0","w","w","w","w","w","w","w","d","w","w","w","w","w","w"},
			{"w", "w","w","w","w","w","d","w","w","w","w","w","w","w","0","w","w","0","w","w","w","w","w","w","w","d","w","w","w","w","w","w"},
			{"w", "w","w","w","w","w","d","w","0","0","0","0","0","0","0","w","w","0","0","0","0","0","0","0","w","d","w","w","w","w","w","w"},
			{"w", "w","w","w","w","w","d","w","0","w","w","w","w","w","0","w","w","0","w","w","w","w","w","0","w","d","w","w","w","w","w","w"},
			{"w", "w","w","w","w","w","d","w","0","w","w","0","0","0","0","0","0","0","0","0","0","w","w","0","w","d","w","w","w","w","w","w"},
			{"w", "w","w","w","w","w","d","0","0","w","w","0","w","w","w","g","g","w","w","w","0","w","w","0","w","0","w","w","w","w","w","w"},
			{"w", "w","w","w","w","w","d","w","0","w","w","0","w","w","0","0","0","0","w","w","0","w","w","0","w","d","w","w","w","w","w","w"},
			{"w", "w","w","w","w","w","d","w","0","w","w","0","w","w","w","w","w","w","w","w","0","w","w","0","w","d","w","w","w","w","w","w"},
			{"w", "w","w","w","w","w","d","w","0","w","w","0","0","0","0","0","0","0","0","0","0","w","w","0","w","d","w","w","w","w","w","w"},
			{"w", "w","w","w","w","w","d","w","0","w","w","w","w","w","w","w","w","w","w","w","w","w","w","0","w","d","w","w","w","w","w","w"},
			{"w", "d","d","d","d","d","d","d","d","d","d","d","d","d","d","w","w","d","d","d","d","d","d","d","d","d","d","d","d","d","d","w"},
			{"w", "d","w","w","w","w","d","w","w","w","w","w","w","w","d","w","w","d","w","w","w","w","w","w","w","d","w","w","w","w","d","w"},
			{"w", "d","w","w","w","w","d","w","w","w","w","w","w","w","d","w","w","d","w","w","w","w","w","w","w","d","w","w","w","w","d","w"},
			{"w", "d","d","d","w","w","d","d","d","d","d","d","d","d","d","w","w","d","d","d","d","d","d","d","d","d","w","w","d","d","d","w"},
			{"w", "w","w","d","w","w","d","w","d","w","w","w","w","w","w","w","w","w","w","w","w","w","w","d","w","d","w","w","d","w","w","w"},
			{"w", "d","d","d","w","w","d","w","d","d","d","d","d","d","d","w","w","d","d","d","d","d","d","d","w","d","w","w","d","d","d","w"},
			{"w", "w","w","d","w","w","d","w","d","w","w","w","w","w","d","w","w","d","w","w","w","w","w","d","w","d","w","w","d","w","w","w"},
			{"w", "d","d","d","d","d","d","w","d","d","d","d","d","d","d","w","w","d","d","d","d","d","d","d","w","d","d","d","d","d","d","w"},
			{"w", "d","w","w","w","w","w","w","w","w","w","w","w","w","d","w","w","d","w","w","w","w","w","w","w","w","w","w","w","w","d","w"},
			{"w", "d","w","w","w","w","w","w","w","w","w","w","w","w","d","w","w","d","w","w","w","w","w","w","w","w","w","w","w","w","d","w"},
			{"w", "d","d","d","d","d","d","d","d","d","d","d","d","d","d","w","w","d","d","d","d","d","d","d","d","d","d","d","d","d","d","w"},
			{"w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w"}};

	}


}



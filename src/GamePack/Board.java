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
		if(level == 1) {
			this.pacman = new NicePacman(new Pair(14,16),this.boardTiles); 
		}
		inisializeNeighborsMat();
		InisializeGhosts();
		this.timer = new PacTimer(this ,greenGhost,redGhost,yellowGhost);
		this.numTicksOfGame = 1;
		this.numPoints = 0;
		this.addKeyListener(this);
		this.setSize(800,800);
		this.setVisible(true);
	}

	private void InisializeGhosts() {
		this.greenGhost = new GreenGhost( new Pair(16,15), neighbors);
		this.redGhost = new RedGhost( new Pair(16,14), neighbors);
		this.yellowGhost = new YellowGhost( new Pair(16,16), neighbors);

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
	private Vector<String> findneighbors(int x,int y) {
		Vector <String> possibleDirs = new Vector <>();//the frame of the board is all wals so there wont be problems of edged
		if(this.boardTilesS[x][y] != "w") {
			if(this.boardTilesS[x-1][y]!="w") //can move up
				possibleDirs.add("u");
			if(this.boardTilesS[x+1][y]!="w")//can move down
				possibleDirs.add("d");
			if(this.boardTilesS[x][y+1]!="w")//can move right
				possibleDirs.add("r");
			if(this.boardTilesS[x][y-1]!="w")//can move left
				possibleDirs.add("l");
		}
		return possibleDirs;
	}
	private void initializeBoard() {
		int num =0;
		this.boardTiles = new BoardTile [32][32];  
		for(int i=0;i<32;i++){
			for(int j=0;j<32;j++){
				if(this.boardTilesS[i][j] == "d")
					num = num +1;
				initializeBoardTile(i,j);
			}
		}
		System.out.println(num);
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
		if(boardTilesS[x][y] == "e") {
			boardTiles [x][y] = new RoadTile(x,y, new Energy());
		}
	}

	public void drawFruits() {
		Random rand = new Random();
		int x =0;
		int y=0;
		BoardTile b = this.boardTiles[x][y];
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
		if(e.getSource().equals(this.timer.getGameTimer())) {
			if(this.numTicksOfGame == 7) 
				greenGhost.start();
			if(this.numTicksOfGame == 9) 
				yellowGhost.start();
			if(this.numTicksOfGame == 11) 
				redGhost.start();
			this.numTicksOfGame++;
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
		if(numTicksOfGame % 22 == 0 | numTicksOfGame % 23 == 0 | numTicksOfGame % 24 == 0 | numTicksOfGame % 25 == 0 ) { // dim
			dimFruit();
		}
		if(numTicksOfGame % 26 ==0) { //disappear
			disappearFruits();
			this.isFruitsOn = false;
		}
		this.pacman.move(); //move pacman
		checkIfPacEat();
		repaint();
	}


private void checkIfPacEat() {
	int i = this.pacman.getCurrentPosition().getX();
	int j = this.pacman.getCurrentPosition().getY();

	//position of pacman in matrix is for sure a RoadTile
	RoadTile pacTile = (RoadTile)this.boardTiles[i][j];
	for(int k=0; k<this.fruitsTiles.size(); k =k+1) {
		if(pacTile.equals(this.fruitsTiles.get(k))) {
			this.fruitsTiles.remove(k);
		}
	}
	this.numPoints = this.numPoints + pacTile.eaten();

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
	offGr.drawImage(im, 0, 0, this);
	offGr.drawImage(im, 35, 0, this);
	offGr.drawImage(im, 70, 0, this);
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
		drawLives(g);
		start = false; 
	}
	//pacman draw
	this.pacman.draw(this, g);
	//fruits draw
	for(int i=0; i<this.fruitsTiles.size(); i = i+1) {
		g.drawImage(this.fruitsTiles.get(i).getImage(), this.fruitsTiles.get(i).getY()*20, this.fruitsTiles.get(i).getX()*20, this);
	}
	if(!isFruitsOn) {
		this.fruitsTiles.clear();
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
	g.drawString("Score: " + this.numPoints, 400, 700);
}

public BoardTile getBoardTile(Pair place) {
	return this.boardTiles[place.getX()][place.getY()];
}

public void initializeBoardTilesS() {
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
		{"w", "w","w","w","w","w","d","w","0","w","w","0","w","w","0","0","0","0","w","w","0","w","w","0","w","d","w","w","w","w","w","w"},
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
public static void main(String[]args) {
	new Board(1);
}


}



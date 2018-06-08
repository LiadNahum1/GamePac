package GamePack;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

import Food.Food;
import Tiles.BoardTile;
import Tiles.RoadTile;

public abstract class Pacman implements Visited, ActionListener{
	protected ImageIcon [] pacmanIcons;
	protected ImageIcon currentIcon;
	private Pair currentPosition; // on board
	private Pair lastPosition;
	private int dx;
	private int dy; 
	private ImageIcon fullPac; 
	private boolean isFull;
	private BoardTile [][] board; 
	private String direction; //"l", "r", "u","d"
	private boolean isPacDie;
	private int score;
	public Pacman(Pair initialPosition, BoardTile[][]board) {
		this.pacmanIcons = new ImageIcon[5];
		this.fullPac = new ImageIcon("pictures\\figures\\NicePacman\\fullPac.png");
		this.isFull = false; 
		this.isPacDie = false;
		this.board = board; 
		this.score = 0;
		initializePacman(initialPosition);
	}
	public void initializePacman(Pair initialPosition) {
		this.currentIcon = this.pacmanIcons[0];
		this.currentPosition = initialPosition;
		this.lastPosition = new Pair(currentPosition.getX(), currentPosition.getY());
		this.dx = 0;
		this.dy = -1; 
		this.direction = "l"; 
		this.isPacDie = false; 
		
	}
	/*moves pacman if can */
	public void move() {
		if(checkIfCanMove()) {
			this.lastPosition.setX(this.currentPosition.getX());
			this.lastPosition.setY(this.currentPosition.getY());
			this.currentPosition.sumSetX(this.dx);
			this.currentPosition.sumSetY(this.dy);
		    ((RoadTile)this.board[this.currentPosition.getX()][this.currentPosition.getY()]).setIsSomethingOn(true);
		    ((RoadTile)this.board[this.lastPosition.getX()][this.lastPosition.getY()]).setIsSomethingOn(false);
		}
	}
	public boolean checkIfCanMove() {
		int x = this.currentPosition.getX() + this.dx;
		int y = this.currentPosition.getY() + this.dy; 
		if(this.board[x][y] instanceof RoadTile) {
			return true;
		}
		return false; 
	}
	public ImageIcon getCurrentIcon() {
		return this.currentIcon;
	}

	public Pair getCurrentPosition() {
		return this.currentPosition;
	}
	public void manageMovement(KeyEvent e) {
		if(e.getKeyCode()== KeyEvent.VK_LEFT) {
			this.dx = 0;
			this.dy= -1;
			this.direction = "l";
			this.currentIcon = this.pacmanIcons[0];
		}
		if(e.getKeyCode()== KeyEvent.VK_RIGHT) {
			this.dx = 0;
			this.dy= 1;
			this.direction = "r";
			this.currentIcon = this.pacmanIcons[1];
		}
		if(e.getKeyCode()== KeyEvent.VK_UP) {
			this.dx = -1;
			this.dy= 0;
			this.direction = "u";
			this.currentIcon = this.pacmanIcons[2];
		}
		if(e.getKeyCode()== KeyEvent.VK_DOWN) {
			this.dx = 1;
			this.dy= 0;
			this.direction = "d";
			this.currentIcon = this.pacmanIcons[3];
		}
			move();
	}
	public void actionPerformed(ActionEvent e) {
		move(); //move pacman	
		eat();
	}
	public void eat() {
		this.score = this.score + ((RoadTile)this.board[getCurrentPosition().getX()][getCurrentPosition().getY()]).eaten();
	}

	public int getScore() {
		return this.score;
	}
	public void reduceScore(int reduce) {
		this.score = this.score - reduce;
	}
	public void draw(Board board, Graphics g) {
		ImageIcon im = getCurrentIcon();
	/*	if(!isFull) {
			im = getCurrentIcon();
			this.isFull = true; 
		}
		else {
			im = this.fullPac;
			this.isFull = false;
		}*/
		g.fillRect(this.lastPosition.getY()*20, this.lastPosition.getX()*20, 20, 20);
		Image offIm = board.createImage(20 , 20);
		Graphics offGr = offIm.getGraphics();	
		offGr.drawImage(im.getImage(), 0,0, board);
		g.drawImage(offIm,this.currentPosition.getY() * 20, this.currentPosition.getX()*20, board);
	}
	public void dead() {
		this.isPacDie = true;

	}
	
	public boolean isPacDead() {
		return this.isPacDie;
	}

}


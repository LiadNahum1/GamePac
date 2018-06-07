package GamePack;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

import Tiles.BoardTile;
import Tiles.RoadTile;

public abstract class Pacman implements Visited{
	protected ImageIcon [] pacmanIcons;
	protected ImageIcon currentIcon;
	private ImageIcon fullPac; 
	private Pair currentPosition; // on board
	private Pair lastPosition;
	private int dx;
	private int dy; 
	private boolean isFull;
	private BoardTile [][] board; 
	private String direction; //"l", "r", "u","d"

	public Pacman(Pair initialPosition, BoardTile[][]board) {
		this.pacmanIcons = new ImageIcon[5];
		this.fullPac = new ImageIcon("pictures\\figures\\NicePacman\\fullPac.png");
		this.currentPosition = initialPosition;
		this.lastPosition = new Pair(currentPosition.getX(), currentPosition.getY());
		this.dx = 0;
		this.dy = -1; 
		this.direction = "l"; 
		this.isFull = false; 
		this.board = board; 
	}

	/*moves pacman if can */
	public void move() {
		if(checkIfCanMove()) {
			this.lastPosition.setX(this.currentPosition.getX());
			this.lastPosition.setY(this.currentPosition.getY());
			this.currentPosition.sumSetX(this.dx);
			this.currentPosition.sumSetY(this.dy);
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
	public void impact(Visitor v) {
	//	v.visit(this);
	}

}

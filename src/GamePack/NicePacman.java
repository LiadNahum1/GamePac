package GamePack;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

import Tiles.BoardTile;
import Tiles.RoadTile;

public class NicePacman extends Pacman{
	private ImageIcon [] pacmanIcons;
	private ImageIcon currentIcon;
	private ImageIcon fullPac; 
	private Pair currentPosition;
	private Pair lastPosition;
	private int dx;
	private int dy; 
	private boolean isFull;
	private BoardTile [][] board; 
	private String direction; //"l", "r", "u","d"

	public NicePacman(Pair initialPosition, BoardTile[][]board) {
		this.pacmanIcons = new ImageIcon[5];
		this.pacmanIcons[0] = new ImageIcon("pictures\\figures\\NicePacman\\left.png");
		this.pacmanIcons[1] = new ImageIcon("pictures\\figures\\NicePacman\\right.png");
		this.pacmanIcons[2] = new ImageIcon("pictures\\figures\\NicePacman\\up.png");
		this.pacmanIcons[3] = new ImageIcon("pictures\\figures\\NicePacman\\down.png");
		this.fullPac = new ImageIcon("pictures\\figures\\NicePacman\\fullPac.png");
		this.currentPosition = initialPosition;
		this.lastPosition = new Pair(currentPosition.getX(), currentPosition.getY());
		this.currentIcon = this.pacmanIcons[0];
		this.dx = -5;
		this.dy = 0; 
		this.direction = "l"; 
		this.isFull = false; 
		this.board = board; 

	}
	@Override
	public void impact(Visitor v) {
		v.visit(this);
	}

	public void move() {
		if(checkIfCanMove()) {
			this.lastPosition.setX(this.currentPosition.getX());
			this.lastPosition.setY(this.currentPosition.getY());
			this.currentPosition.sumSetX(this.dx);
			this.currentPosition.sumSetY(this.dy);
		}

	}
	public boolean checkIfCanMove() {
		int xTop = this.currentPosition.getX() + this.dx;
		int yTop = this.currentPosition.getY() + this.dy;
		int xBot = this.currentPosition.getX() + this.dx;
		int yBot = this.currentPosition.getY() + 25 + this.dy-1;
		if(this.direction.equals("l")) {
			xTop = xTop/25;
			yTop = yTop/25;
			xBot = xBot/25;
			yBot = yBot/25;
		}
		else if(this.direction.equals("u")) {
			xTop = xTop/25;
			yTop = yTop/25;
			xBot = xBot/25;
			yBot = (yBot+25)/25;
		}
		else if(this.direction.equals("r")) {
			xTop = (xTop+25)/25;
			yTop = yTop/25;
			xBot = (xBot+25)/25;
			yBot = yBot/25;
		}
		else { //d 
			xTop = xTop/25;
			yTop = (yTop+25)/25;
			xBot = xBot/25;
			yBot = yBot/25;
		}
		if(this.board[yTop][xTop] instanceof RoadTile & this.board[yBot][xBot] instanceof RoadTile )
			return true;
		else 
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
			this.dx = -5;
			this.dy= 0;
			this.direction = "l";
			this.currentIcon = this.pacmanIcons[0];
		}
		if(e.getKeyCode()== KeyEvent.VK_RIGHT) {
			this.dx = 5;
			this.dy= 0;
			this.direction = "r";
			this.currentIcon = this.pacmanIcons[1];
		}
		if(e.getKeyCode()== KeyEvent.VK_UP) {
			this.dx = 0;
			this.dy= -5;
			this.direction = "u";
			this.currentIcon = this.pacmanIcons[2];
		}
		if(e.getKeyCode()== KeyEvent.VK_DOWN) {
			this.dx = 0;
			this.dy= 5;
			this.direction = "d";
			this.currentIcon = this.pacmanIcons[3];
		}
		move();
	}

	@Override
	public void draw(Game game, Graphics g) {
		ImageIcon im;
		if(!isFull) {
			im = getCurrentIcon();
			this.isFull = true; 
		}
		else {
			im = this.fullPac;
			this.isFull = false;
		}
		g.fillRect(this.lastPosition.getX(), this.lastPosition.getY(), 25, 25);
		Image offIm = game.createImage(25 , 25);
		Graphics offGr = offIm.getGraphics();	
		offGr.drawImage(im.getImage(), 0,0, game);
		g.drawImage(offIm,this.currentPosition.getX(), this.currentPosition.getY(), game);
	}
}

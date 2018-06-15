package Pacmen;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

import Food.Food;
import GamePack.Board;
import GamePack.Mode;
import GamePack.Pair;
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
	private String [][]boardStr; 
	private String direction; //"l", "r", "u","d"
	private Mode mode;
	private int score;
	private int freezeTicks;
	private ImageIcon [] freezeIcon; 

	public Pacman(Pair initialPosition, BoardTile[][]board, String [][]boardStr) {
		this.pacmanIcons = new ImageIcon[5];
		this.freezeIcon = new ImageIcon[4];
		this.fullPac = new ImageIcon("pictures\\figures\\NicePacman\\fullPac.png");
		initialFreezeIcons();
		this.isFull = false; 
		this.board = board;
		this.boardStr = boardStr;
		this.score = 0;
		initializePacman(initialPosition);
	}
	private void initialFreezeIcons() {
		this.freezeIcon[0] = new ImageIcon("pictures\\figures\\freeze\\l.png");
		this.freezeIcon[1] = new ImageIcon("pictures\\figures\\freeze\\r.png");
		this.freezeIcon[2] = new ImageIcon("pictures\\figures\\freeze\\u.png");
		this.freezeIcon[3] = new ImageIcon("pictures\\figures\\freeze\\d.png");
	}
	public void initializePacman(Pair initialPosition) {
		this.currentIcon = this.pacmanIcons[0];
		this.currentPosition = initialPosition;
		this.lastPosition = new Pair(currentPosition.getX(), currentPosition.getY());
		this.dx = 0;
		this.dy = 0; 
		this.direction = "l"; 
		this.mode = Mode.ALIVE;  
		this.freezeTicks = 0;

	}
	/*moves pacman if can */
	public void move() {
		if(checkIfCanMove()) {
			System.out.println(dx + " " + dy);
			this.lastPosition.setX(this.currentPosition.getX());
			this.lastPosition.setY(this.currentPosition.getY());
			this.currentPosition.sumSetX(this.dx);
			this.currentPosition.sumSetY(this.dy);
		}
		
	}
	public boolean checkIfCanMove() {
		int x = this.currentPosition.getX() + this.dx;
		int y = this.currentPosition.getY() + this.dy; 
		if(this.boardStr[x][y].equals("w") |this.boardStr[x][y].equals("gh")|this.boardStr[x][y].equals("g")) {
			return false;  
		}
		return true; 
	}
	public ImageIcon getCurrentIcon() {
		return this.currentIcon;
	}

	public Pair getCurrentPosition() {
		return this.currentPosition;
	}
	public void manageMovement(KeyEvent e) {
		if(!this.mode.equals(Mode.FREEZE)) {
			if(e.getKeyCode()== KeyEvent.VK_LEFT) {
				this.dx = 0;
				this.dy= -1;
				this.direction = "l";
				this.currentIcon = this.pacmanIcons[0];
			}
			else if(e.getKeyCode()== KeyEvent.VK_RIGHT) {
				this.dx = 0;
				this.dy= 1;
				this.direction = "r";
				this.currentIcon = this.pacmanIcons[1];
			}
			else if(e.getKeyCode()== KeyEvent.VK_UP) {
				this.dx = -1;
				this.dy= 0;
				this.direction = "u";
				this.currentIcon = this.pacmanIcons[2];
			}
			else if(e.getKeyCode()== KeyEvent.VK_DOWN) {
				this.dx = 1;
				this.dy= 0;
				this.direction = "d";
				this.currentIcon = this.pacmanIcons[3];
			}
			else {
				this.dx =0;
				this.dy = 0;
			}
			move();
			eat();
		}
	}
	public void actionPerformed(ActionEvent e) {
		if(this.mode.equals(Mode.FREEZE) & this.freezeTicks < 4*3) {
			this.freezeTicks = this.freezeTicks +1;
			System.out.println(this.freezeTicks);
		}
		else {
			if(this.freezeTicks == 4*3) {
				this.mode = Mode.ALIVE;
				this.freezeTicks = 0;
			}
			
		//	move(); //move pacman	
		//	eat();
		}
	}
	public void eat() {
		BoardTile tile = this.board[getCurrentPosition().getX()][getCurrentPosition().getY()];
		this.score = this.score + tile.eaten();
	}

	public int getScore() {
		return this.score;
	}
	public void reduceScore(int reduce) {
		this.score = this.score - reduce;
	}
	public void draw(Board board, Graphics g) {
		ImageIcon im = getCurrentIcon();
		/*if(!isFull) {
			im = getCurrentIcon();
			this.isFull = true; 
		}
		else {
			im = this.fullPac;
			this.isFull = false;
		}*/
		g.setColor(Color.BLACK);
		g.fillRect(this.lastPosition.getY()*20, this.lastPosition.getX()*20, 20, 20);
		Image offIm = board.createImage(20 , 20);
		Graphics offGr = offIm.getGraphics();	
		offGr.drawImage(im.getImage(), 0,0, board);
		g.drawImage(offIm,this.currentPosition.getY() * 20, this.currentPosition.getX()*20, board);
	}
	public void dead() {
		this.mode = Mode.DEAD;

	}
	public void freeze() {
		this.mode = Mode.FREEZE;
		if(this.freezeTicks == 0)
			reduceScore(10);
		if(this.direction.equals("l"))
			this.currentIcon = this.freezeIcon[0];
		if(this.direction.equals("r"))
			this.currentIcon = this.freezeIcon[1];
		if(this.direction.equals("u"))
			this.currentIcon = this.freezeIcon[2];
		else
			this.currentIcon = this.freezeIcon[3];

	}

	public Mode getMode() {
		return this.mode;
	}

}


package Pacmen;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import GamePack.Board;
import GamePack.Mode;
import GamePack.PacTimer;
import GamePack.Pair;
import Tiles.BoardTile;

/*abstract class which defines a Pacman figure and implements Visited and ActionListener*/ 
public abstract class Pacman implements Visited, ActionListener{
	protected ImageIcon [] pacmanIcons;
	private ImageIcon [] freezeIcon; 
	protected ImageIcon currentIcon;
	private Pair currentPosition; // on board
	private Pair lastPosition;
	private int dx;
	private int dy; 
	private BoardTile [][] board;
	private String [][]boardStr; 
	private String direction; //"l", "r", "u","d"
	private Mode mode;
	private int score;
	private int appleNum;
	private int pineAppleNum;
	private int strawBerryNum;
	private int freezeTicks;
	private PacTimer timer; 

	public Pacman(Pair initialPosition, BoardTile[][]board, String [][]boardStr, PacTimer timer) {
		this.pacmanIcons = new ImageIcon[5];
		this.freezeIcon = new ImageIcon[4];	
		initializeFreezeIcons();
		this.board = board;
		this.boardStr = boardStr;
		this.timer = timer; 
		this.score = 0;
		this.pineAppleNum = 0;
		this.appleNum = 0;
		this.strawBerryNum = 0;
		initializePacman(initialPosition);
	}

	/*The function gets an initial position for pacman on the matrix of the board and initialize Pacman:
	 * The initial direction of Pacman is left, its mode is alive and in the beginning the delta x and y 
	 * that determines the movement of Pacman on the matrix cells are 0
	 * The function is called at the start o the game and also every time that Pacman dies and revives
	 */
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
	/*The function fill the freezeIcon array with freeze pacman images for each direction*/ 
	private void initializeFreezeIcons() {
		this.freezeIcon[0] = new ImageIcon("pictures\\figures\\freeze\\l.png");
		this.freezeIcon[1] = new ImageIcon("pictures\\figures\\freeze\\r.png");
		this.freezeIcon[2] = new ImageIcon("pictures\\figures\\freeze\\u.png");
		this.freezeIcon[3] = new ImageIcon("pictures\\figures\\freeze\\d.png");
	}

	/*The function make Pacman moves if possible. Updates the lastPosition and the currentPosition */
	public void move() {
		if(checkIfCanMove()) {
			this.lastPosition.setX(this.currentPosition.getX());
			this.lastPosition.setY(this.currentPosition.getY());
			this.currentPosition.sumSetX(this.dx);
			this.currentPosition.sumSetY(this.dy);
		}

	}
	/*Checks if Pacman can move according to the string matrix. If in the cell that matches the position of pacman
	 * there is a "w" - which symbols wall or "gh" or "g" - which symbol the gate opening and the cage, the
	 * pacman can't move. Otherwise, he can and the function returns true*/
	private boolean checkIfCanMove() {
		int x = this.currentPosition.getX() + this.dx;
		int y = this.currentPosition.getY() + this.dy; 
		if(this.boardStr[x][y].equals("w") |this.boardStr[x][y].equals("gh")|this.boardStr[x][y].equals("g")) {
			return false;  
		}
		return true; 
	}

	/*The function deals with KeyEvent. If pacman isn't freeze it checks which KeyBoard has been pressed
	 * and changes dx,dy, direction and currentImage according to it. If none of the arrows was pressed
	 * the function updates dx and dy to be 0 and makes pacman stand still. 
	 * Then, calls move() and eat() */
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
	/*The function updates the number of score of pacman*/
	public void eat() {
		BoardTile tile = this.board[getCurrentPosition().getX()][getCurrentPosition().getY()];
		int points =  tile.eaten();
		this.score = this.score + points;
		checkForFruit(points);
	}

	/*return current ImageIcon of pacman*/
	public ImageIcon getCurrentIcon() {
		return this.currentIcon;
	}
	/*gets an array of ImageIcon and set currentIcon according to the diresction field*/
	public void setIcon(ImageIcon [] arrayIm) {
		if(this.direction.equals("l"))
			this.currentIcon = arrayIm[0];
		else if(this.direction.equals("r"))
			this.currentIcon = arrayIm[1];
		else if(this.direction.equals("u"))
			this.currentIcon = arrayIm[2];
		else
			this.currentIcon = arrayIm[3];
	}

	/*returns current position*/
	public Pair getCurrentPosition() {
		return this.currentPosition;
	}

	/*The function is called every tick of the timer of the game.
	 *If pacman is in Freeze mode the function counts 3 seconds and then change pacman's mode
	 *back to be Alive */
	public void actionPerformed(ActionEvent e) {
		if(this.mode.equals(Mode.FREEZE) & this.freezeTicks < 3*this.timer.getSpeed()) {
			this.freezeTicks = this.freezeTicks +1;
		}
		else {
			if(this.freezeTicks == 3*this.timer.getSpeed()) {
				this.mode = Mode.ALIVE;
				setIcon(this.pacmanIcons);
				this.freezeTicks = 0;
			}
		}
	}

	/*The function counts how many fruits did pacman eat of each kind according to the points of each fruit*/
	private void checkForFruit(int points) {
		if(points == 100)
			this.pineAppleNum++;
		if(points == 200)
			this.appleNum++;
		if(points == 300)
			this.strawBerryNum++;
	}

	/*The function is called every time pacman freezes. it reduces 10 points from his score.
	 * If the score after the reduce is less than 0, the player loses*/
	public void reduceScore(int reduce) {
		if(this.score - reduce < 0)
			dead();
		else {
			this.score = this.score - reduce;
		}
	}
	/*The function draws a black rectangle in the last position of pacman and draws pacman in the new position of it */
	public void draw(Board board, Graphics g) {
		ImageIcon im = getCurrentIcon();
		g.setColor(Color.BLACK);
		g.fillRect(this.lastPosition.getY()*20, this.lastPosition.getX()*20, 20, 20);
		Image offIm = board.createImage(20 , 20);
		Graphics offGr = offIm.getGraphics();	
		offGr.drawImage(im.getImage(), 0,0, board);
		g.drawImage(offIm,this.currentPosition.getY() * 20, this.currentPosition.getX()*20, board);
	}
	
	/*change pacman mode to DEAD mode*/
	public void dead() {
		this.mode = Mode.DEAD;
	}
	
	/*change pacman mode to FREEZE mode, reduce 10 points of his score and set its icon to be frozen*/
	public void freeze() {
		if(this.mode != Mode.FREEZE) {
			this.mode = Mode.FREEZE;
			reduceScore(10);
			setIcon(this.freezeIcon);
		}
	}

	public int getScore() {
		return this.score;
	}
	
	public int getPineAppleNum() {
		return this.pineAppleNum;
	}
	public int getAppleNum() {
		return this.appleNum;
	}
	public int getStrawBerryNum() {
		return this.strawBerryNum;
	}
	
	public Mode getMode() {
		return this.mode;
	}
	
}


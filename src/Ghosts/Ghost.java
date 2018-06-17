
package Ghosts;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.swing.ImageIcon;

import GamePack.Board;
import GamePack.Mode;
import GamePack.PacTimer;
import GamePack.Pair;
import Pacmen.AngryPacman;
import Pacmen.DefendedPacman;
import Pacmen.NicePacman;
import Pacmen.Pacman;
import Tiles.BoardTile;
/* ghost is a monster that try to prevent pacman from eating the food on the board
 * it has pictures for each direction that it can move and functions that handles her behavior over the game*/
public abstract class Ghost  implements Visitor, ActionListener {
	protected String curPos;
	protected Vector<String> [][] neighbors;
	protected Pacman pacman; 
	protected Pair boardTileIn;
	protected Pair lastBoardTileIn;
	protected int timeFromStart;
	protected int timeFromChase;
	private Pair chaseWall;
	protected Boolean isChase;
	protected HashMap <String,Image> position;
	protected Image currPositionIm;
	protected Boolean isStart;
	private boolean isDim;
	protected BoardTile[][]boardTiles;
	protected Mode mode; 
	protected int ticks;
	protected PacTimer timer; 


	public Ghost(BoardTile[][]board, Pair boardTileIn ,Pacman pacman, Vector<String> [][] neighbors ,String ghostColor ,Pair closestWall ,String curPos, PacTimer timer ) {
		updateDirsPic(ghostColor);
		inisializeData(boardTileIn , new Pair(0,0) , curPos);
		this.neighbors = neighbors;
		this.chaseWall = closestWall;     
		this.pacman = pacman;
		this.boardTiles = board;
		this.timer = timer; 
	}


	public void inisializeData(Pair boardTileIn , Pair lastBoardTileIn ,String curPos) {
		this.lastBoardTileIn = lastBoardTileIn; 
		this.boardTileIn = boardTileIn;     
		this.timeFromChase = 0; //didnt Start chasing    
		this.timeFromStart = 1;
		this.curPos = curPos;  
		this.isStart = false;  
		this.isChase = false;
		this.isDim = false;
		this.mode = Mode.ALIVE;
		this.ticks = 0;
		this.currPositionIm = position.get(curPos);
	}

	private void updateDirsPic(String ghostColor) { 
		position = new HashMap<String ,Image>();
		position.put("u", new ImageIcon("pictures/figures/" + ghostColor +"_u.png").getImage());
		position.put("l", new ImageIcon("pictures/figures/" + ghostColor +"_l.png").getImage());
		position.put("r", new ImageIcon("pictures/figures/" + ghostColor +"_r.png").getImage());
		position.put("d", new ImageIcon("pictures/figures/" + ghostColor +"_d.png").getImage());
		position.put("sceard", new ImageIcon("pictures/figures/scared.png").getImage());
	}
	public void collide(Pacman pacman) {
		pacman.impact(this);
	}
	public void move() { 
		String dir = findMoveDir();
		this.currPositionIm = this.position.get(dir); //update the picture of the direction 
		this.curPos = dir;
		this.lastBoardTileIn.setX(this.boardTileIn.getX());
		this.lastBoardTileIn.setY(this.boardTileIn.getY());
		this.boardTileIn = advanceMove();
		if(this.boardTileIn.equals(chaseWall) & this.timeFromChase == 0) {
			this.isChase = true;
			this.timeFromChase = 1;
		}
	}
	public Pair advanceMove() { //this will update the next tile that the ghost would be in according to the chosen direction
		Pair nextTile = new Pair(0,0);
		if(curPos == "u") 
		{
			nextTile = new Pair(this.boardTileIn.getX()-1,this.boardTileIn.getY());
		}
		if(curPos == "d")
		{
			nextTile = new Pair(this.boardTileIn.getX()+1,this.boardTileIn.getY());
		}
		if(curPos == "l")
		{
			nextTile = new Pair(this.boardTileIn.getX(),this.boardTileIn.getY()-1);
		}
		if(curPos == "r")
		{
			nextTile = new Pair(this.boardTileIn.getX(),this.boardTileIn.getY()+1);
		}
		return nextTile;
	}
	//this will find the best move to go next
	private String findMoveDir() {
		@SuppressWarnings("unchecked")
		Vector <String> posDirs =(Vector<String>) neighbors[this.boardTileIn.getX()][this.boardTileIn.getY()].clone();
		if(posDirs.contains(curPos)) { //this prevent the ghost from changing direction while in straight line
			if(curPos == "u") posDirs.remove("d");
			if(curPos == "d") posDirs.remove("u");
			if(curPos == "r") posDirs.remove("l");
			if(curPos == "l") posDirs.remove("r");
		}
		if(posDirs.size() == 1) //if there is only one direction take it
			return posDirs.get(0);
		if(isChase) { //if the ghost already reached the wall the goes to random direction
			Random r = new Random ();
			int  n = r.nextInt(posDirs.size());
			return posDirs.get(n);
		}
		else
			return bestMove(posDirs);
	}

	private String bestMove(Vector<String> posDirs) { // if the ghost chase the wall so it goes in the direction of the wall
		if(posDirs.contains("u"))
			return "u";
		if(this.chaseWall.getY()==1  & posDirs.contains("l" ) ) 
			return "l";
		else
			return "r";
	}
	public void draw(Board board, Graphics g) { 
			Image offIm1 = board.createImage(20, 20);//this will draw last board tile
			Graphics offGr1 = offIm1.getGraphics();	
			offGr1.drawImage(board.getBoardTile(this.lastBoardTileIn).getImage(), 0,0, board);
			g.drawImage(offIm1,this.lastBoardTileIn.getY()*20,this.lastBoardTileIn.getX()*20, board);
			if(!isDim) {
				Image offIm2 = board.createImage(20 , 20);//this will draw next board tile
				Graphics offGr2 = offIm2.getGraphics();	
				offGr2.drawImage(this.currPositionIm, 0,0, board);
				g.drawImage(offIm2,this.boardTileIn.getY()*20, this.boardTileIn.getX()*20, board);
			}
	}
	public void dimGhost() {
		if(this.isDim)
			this.isDim = false;
		else
			this.isDim = true;
	}
	public void start() {
		this.isStart = true;
	}
	public void actionPerformed(ActionEvent e) {
		Pair boardTileOfPac = new Pair(this.pacman.getCurrentPosition().getX(), this.pacman.getCurrentPosition().getY());
		if(this.boardTileIn.equals(boardTileOfPac)) {
			collide(this.pacman);
		}
	}
	//Getters and setter
	public void setLastBoardTileIn(Pair boardTile) {
		this.lastBoardTileIn = boardTile;
	}
	public Pair getBoardTileIn() {
		return this.boardTileIn;
	}
	public void setStart(boolean bool) {
		this.isStart = bool;
	}
	public void setCurrentPositionIm(Image im) {
		this.currPositionIm = im;
	}
	public abstract void visit(NicePacman pacman);
	public abstract void visit(DefendedPacman pacman);
	public abstract void visit(AngryPacman pacman);
}

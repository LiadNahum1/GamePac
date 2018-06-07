
package GamePack;
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

public abstract class Ghost  implements Visitor, ActionListener {
	protected String curPos;
	protected Vector<String> [][] neighbors;
	protected Pacman pacman; 
	protected Pair boardTileIn;
	private Pair lastBoardTileIn;
	protected int timeFromStart;
	protected int timeFromChase;
	private Pair chaseWall;
	protected Boolean isChase;
	private HashMap <String,Image> position;
	private Image currPositionIm;
	protected Boolean isStart;
	private boolean isDim;


	public Ghost(Pair boardTileIn ,Pacman pacman, Vector<String> [][] neighbors ,String ghostColor ,Pair closestWall ,String curPos ) {
		this.lastBoardTileIn = new Pair(0,0); 
		this.boardTileIn = boardTileIn;     
		this.neighbors = neighbors;
		this.timeFromChase = 0; //didntStart   
		this.chaseWall = closestWall;     
		this.curPos = curPos;  
		this.timeFromStart = 1;
		this.isStart = false;  
		this.isChase = false;
		this.isDim = false;
		this.pacman = pacman;
		updateDirsPic(ghostColor);
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
		this.currPositionIm = this.position.get(dir);
		this.curPos = dir;
		this.lastBoardTileIn.setX(this.boardTileIn.getX());
		this.lastBoardTileIn.setY(this.boardTileIn.getY());
		this.boardTileIn = advanceMove();
		if(this.boardTileIn.equals(chaseWall) & this.timeFromChase == 0) {
			this.isChase = true;
			this.timeFromChase = 1;
		}
	}
	public Pair advanceMove() {
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
	private String findMoveDir() {
		@SuppressWarnings("unchecked")
		Vector <String> posDirs =(Vector<String>) neighbors[this.boardTileIn.getX()][this.boardTileIn.getY()].clone();
		if(posDirs.contains(curPos)) {
			if(curPos == "u") posDirs.remove("d");
			if(curPos == "d") posDirs.remove("u");
			if(curPos == "r") posDirs.remove("l");
			if(curPos == "l") posDirs.remove("r");
		}
		if(posDirs.size() == 1) 
			return posDirs.get(0);
			if(isChase) {
			Random r = new Random ();
			int  n = r.nextInt(posDirs.size());
			return posDirs.get(n);
			}
		else
			return bestMove(posDirs);
	}

	private String bestMove(Vector<String> posDirs) {
		if(posDirs.contains("u"))
			return "u";
		if(this.chaseWall.getY() == 1 ) 
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
	public void setLastBoardTileIn(Pair boardTile) {
		this.lastBoardTileIn = boardTile;
	}
	public Pair getBoardTileIn() {
		return this.boardTileIn;
	}
	public void setStart(boolean bool) {
		this.isStart = bool;
	}
	public abstract void visit(NicePacman pacman);
	public abstract void visit(DefendedPacman pacman);
	public abstract void visit(AngryPacman pacman);

}

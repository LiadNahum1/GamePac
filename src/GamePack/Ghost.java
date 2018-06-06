
package GamePack;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ImageIcon;

public abstract class Ghost  implements Visitor {
	protected int TimeFromExit;
	private Vector<String> [][] neighbors;
	private Pair boardTileIn;
	private Pair lastBoardTileIn;
	private Pacman pacman;
	private Pair chasePlace;
	private String curPos;
	private Boolean isChase;
	private HashMap <String,Image> position;
	private Image currPositionIm;
	protected Boolean isStart;
	
	
	public Ghost(Pacman pac ,Pair boardTileIn,Vector<String> [][] neighbors ,String ghostColor ,Pair closestWall ) {
	this.pacman = pac;
	this.lastBoardTileIn = boardTileIn;
	this.boardTileIn = boardTileIn;
	this.neighbors = neighbors;
	this.TimeFromExit = 1;
	this.chasePlace = closestWall;
	this.curPos = "u";
	this.isChase = false;
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
		if(curPos == "u")
		{
			this.boardTileIn = new Pair(this.boardTileIn.getX(),this.boardTileIn.getY()-1);
		}
		if(curPos == "d")
		{
			this.boardTileIn = new Pair(this.boardTileIn.getX(),this.boardTileIn.getY()+1);
		}
		if(curPos == "l")
		{
			this.boardTileIn = new Pair(this.boardTileIn.getX()-1,this.boardTileIn.getY());
	}
		if(curPos == "r")
		{
			this.boardTileIn = new Pair(this.boardTileIn.getX()+1,this.boardTileIn.getY());
		}
		if(this.boardTileIn.equals(chasePlace)) {
			this.isChase = true;
		}
	}
	private String findMoveDir() {
		Vector <String> posDirs =neighbors[this.boardTileIn.getX()][this.boardTileIn.getY()];
		if(posDirs.size()==2 & posDirs.contains(this.curPos))
			return this.curPos;
		else
			return bestNove(posDirs);
	}
	private String bestNove(Vector<String> posDirs) { 
		if(isChase) 
			this.chasePlace = pacman.getCurrentPosition();
		if(this.chasePlace.getY() < this.boardTileIn.getY() & posDirs.contains("u"))
			return "u";
		if(this.chasePlace.getY() > this.boardTileIn.getY() & posDirs.contains("d"))
			return "d";
		if(this.chasePlace.getX() < this.boardTileIn.getX() & posDirs.contains("r"))
			return "r";
		if(this.chasePlace.getX() > this.boardTileIn.getX() & posDirs.contains("l"))
			return "l";
		else {
			return posDirs.get(0);
		}
	}
	public void draw(Game game, Graphics g) { 
		if(!this.lastBoardTileIn.equals(this.boardTileIn)) {
		Image offIm1 = game.createImage(25 , 25);//this will draw last board tile
		Graphics offGr1 = offIm1.getGraphics();	
		offGr1.drawImage(game.getBoardTile(this.lastBoardTileIn).getImage(), 0,0, game);
		g.drawImage(offIm1,this.lastBoardTileIn.getX()*25, this.lastBoardTileIn.getY()*25, game);
		this.lastBoardTileIn.setX(this.boardTileIn.getX());
		this.lastBoardTileIn.setY(this.boardTileIn.getY());
		}
		Image offIm2 = game.createImage(25 , 25);//this will draw next board tile
		Graphics offGr2 = offIm2.getGraphics();	
		offGr2.drawImage(this.currPositionIm, 0,0, game);
		g.drawImage(offIm2,this.boardTileIn.getX()*25, this.boardTileIn.getY()*25, game);
	}
	public abstract void attack();
	public abstract void visit(NicePacman pacman);
	public abstract void visit(DefendedPacman pacman);
	public abstract void visit(AngryPacman pacman);

}

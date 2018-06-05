
package GamePack;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ImageIcon;

public abstract class Ghost  implements Visitor,ActionListener {
	protected int TimeFromExit;
	private Vector<String> [][] neighbors;
	private Pair boardTileIn;
	private Pair chasePlace;
	private Pair pxIn;
	private boolean isScared;
	private String curPos;
	private HashMap <String,Image> position;
	private Image currPositionIm;
	private Graphics g;
	
	public Ghost(Pair pxIn, Pair chasePlace ,Pair boardTileIn,Vector<String> [][] neighbors ,String ghostColor ,Graphics g) {
	this.pxIn = pxIn;
	this.chasePlace = chasePlace;
	this.boardTileIn = boardTileIn;
	this.neighbors = neighbors;
	this.TimeFromExit = 0;
	this.g =g;
	updateDirsPic(ghostColor);
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
	public void moveTile() {
		String dir;
		if(isScared) {
			dir= findsScaredMove();
		}
		else {
			dir = findMoveDir();
			this.currPositionIm = this.position.get(dir);
		}
		this.curPos = dir;
	}
	public void move() {
		
		if(curPos == "u")
		{
			pxIn.sumSetX(0);;
			pxIn.sumSetY(1);
		}
		if(curPos == "d")
		{
			pxIn.sumSetX(0);;
			pxIn.sumSetY(-1);
		}
		if(curPos == "l")
		{
			pxIn.sumSetX(1);;
			pxIn.sumSetY(0);
	}
		if(curPos == "r")
		{
			pxIn.sumSetX(-1);;
			pxIn.sumSetY(0);
		}
	}

	private String findsScaredMove() {
		// TODO Auto-generated method stub
		return null;
	}

	private String findMoveDir() {
		Vector <String> posDirs =neighbors[this.boardTileIn.getX()][this.boardTileIn.getY()];

		if(posDirs.size()==2 & posDirs.contains(this.curPos))
			return this.curPos;
		else
			return bestNove(posDirs);
	}

	private String bestNove(Vector<String> posDirs) { //this ghost try to ambush pacman from the sides
		if(this.chasePlace.getX() < this.boardTileIn.getX() & posDirs.contains("r"))
			return "r";
		if(this.chasePlace.getX() > this.boardTileIn.getX() & posDirs.contains("l"))
			return "l";
		if(this.chasePlace.getY() < this.boardTileIn.getY() & posDirs.contains("u"))
			return "u";
		if(this.chasePlace.getY() > this.boardTileIn.getY() & posDirs.contains("d"))
			return "d";
		else {
			return posDirs.get(0);
		}
	}
	public void actionPerformed(ActionEvent e) {
		if(this.TimeFromExit%25 == 0) {
			this.moveTile();
		}
		move();
		this.TimeFromExit++;
	}
	
	public Pair getBoardTileIn() {
		return boardTileIn;
	}
	public void setBoardTileIn(Pair currentPosition) {
		this.boardTileIn = currentPosition;
	}
/*	public Image draw(Game game) {
		Image offIm1 = game.createImage(25 , 25); 
		Graphics offGr = offIm1.getGraphics();	
		offGr.drawImage(this.currPositionIm, 0,0, game);
	    g.drawImage(offIm1,this.currentPositionIm, this.currentPosition.getY(), game);
	public abstract void visit(NicePacman pacman);
	public abstract void visit(DefendedPacman pacman);
	public abstract void visit(AngryPacman pacman);
*/
}

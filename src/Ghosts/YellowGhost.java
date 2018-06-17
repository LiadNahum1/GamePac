package Ghosts;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
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
/*this ghost will attack the pacman by launching  a water ball on him 
 * that will brack when hiiting a wall*/
public class YellowGhost extends Ghost{
	private  Pair tileAnderAttack;
	private Pair prevTileAnderAttack;
	private Image attackImg;
	private boolean inAttack;
	private String attackPos;
	private Image freeze; 

	public YellowGhost(BoardTile[][]board, Pair boardTileIn, Pacman pacman, Vector<String>[][] neighbors, PacTimer timer ) {
		super(board, boardTileIn,pacman, neighbors, "yellow",new Pair(1,30) ,"u", timer);
		this.attackImg = new ImageIcon("pictures/figures/iceWave.png").getImage();
		this.inAttack = false;
		prevTileAnderAttack = new Pair(0,0);
		tileAnderAttack = new Pair(0,0);
		this.freeze = new ImageIcon("pictures/figures/freeze.png").getImage();

	}
	@Override
	public void inisializeData( Pair a, Pair b, String color) {
		super.inisializeData(a, b, color);
		this.inAttack = false;
	}
	@Override
	public void visit(NicePacman pacman) {

	}

	@Override
	public void visit(DefendedPacman pacman) {
		pacman.freeze();
	}

	@Override
	/*ghost freeze for 5 seconds*/
	public void visit(AngryPacman pacman) {
		if(this.mode!= Mode.FREEZE) {
			this.mode = Mode.FREEZE; 
			this.ticks = this.ticks + 1;
			setCurrentPositionIm(this.freeze);
			this.prevTileAnderAttack.setX(this.tileAnderAttack.getX());
			this.prevTileAnderAttack.setY(this.tileAnderAttack.getY());
			this.inAttack = false; 
		}

	}
	@Override
	public void draw(Board board, Graphics g) { 
		super.draw(board, g);
		Image offIm1 = board.createImage(20, 20);//this will draw last board tile
		Graphics offGr1 = offIm1.getGraphics();	
		offGr1.drawImage(board.getBoardTile(this.prevTileAnderAttack).getImage(),0,0, board);
		g.drawImage(offIm1,this.prevTileAnderAttack.getY()*20,this.prevTileAnderAttack.getX()*20, board);
		if(inAttack) {
			Image offIm2 = board.createImage(20 , 20);//this will draw next board tile
			Graphics offGr2 = offIm2.getGraphics();	
			offGr2.drawImage(this.attackImg, 0,0, board);
			g.drawImage(offIm2,this.tileAnderAttack.getY()*20, this.tileAnderAttack.getX()*20, board);
		}
	}
	public void advanceAttack() { //if the ghost is already in the attack this will continue it the same direction
		if(inAttack) {
			this.prevTileAnderAttack.setX(this.tileAnderAttack.getX());
			this.prevTileAnderAttack.setY(this.tileAnderAttack.getY());
			Vector<String> posDirs = this.neighbors[prevTileAnderAttack.getX()][prevTileAnderAttack.getY()];
			if(attackPos == "u" & posDirs.contains("u"))  //this will find the attack direction and check if it can continue with the attack 
				this.tileAnderAttack.sumSetX(-1);
			else if(attackPos == "d" & posDirs.contains("d")) 
				this.tileAnderAttack.sumSetX(1);
			else if(attackPos == "l" & posDirs.contains("l")) 
				this.tileAnderAttack.sumSetY(-1);
			else if(attackPos == "r" & posDirs.contains("r")) 
				this.tileAnderAttack.sumSetY(1);
			else inAttack = false;
		}
		else {inAttack = true; //if the last attack is finished the it start a new one
		attackPos = this.curPos;
		this.tileAnderAttack = advanceMove();
		if(this.neighbors[tileAnderAttack.getX()][tileAnderAttack.getY()] == null)
			inAttack = false;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(this.mode.equals(Mode.FREEZE) & this.ticks < 5*this.timer.getSpeed()) {
			this.ticks = this.ticks +1;
		}
		else
		{
			if(this.ticks == 5*this.timer.getSpeed()) { //if the ghost is freezed release it after 5 timer ticks
				unfreeze();
			}
			super.actionPerformed(e);
			Pair boardTileOfPac = new Pair(this.pacman.getCurrentPosition().getX(), this.pacman.getCurrentPosition().getY());
			if(this.tileAnderAttack.equals(boardTileOfPac))
				collide(this.pacman);
			if(this.isStart) {
				if( timeFromStart%4 == 0) {
					if(timeFromChase != 1 & timeFromChase != 2)  //this wall stop the ghost moment after she hits the wall
						this.move();
				}
				if(timeFromChase >= 5 & timeFromChase < 11)  //this will dim the ghost before she can attack
					dimGhost();
				if(timeFromChase >= 11) 
					advanceAttack();
				if( timeFromChase != 0) 
					timeFromChase++;
			}
			timeFromStart++;
		}

	}
	public void unfreeze() {
		this.ticks= 0;
		this.mode = Mode.ALIVE;
		setCurrentPositionIm(this.position.get(this.curPos));
	}


}

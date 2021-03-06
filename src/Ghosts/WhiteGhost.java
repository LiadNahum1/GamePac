package Ghosts;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.ImageIcon;

import GamePack.PacTimer;
import GamePack.Pair;
import Pacmen.AngryPacman;
import Pacmen.DefendedPacman;
import Pacmen.NicePacman;
import Pacmen.Pacman;
import Tiles.BoardTile;
/* this ghost will move to pacman location every 20 timer ticks */
public class WhiteGhost extends Ghost{
private boolean canHit;
private Image cantHitIm;
	public WhiteGhost(BoardTile[][]board, Pair inisialPositionTile,Pacman pacman, Vector<String> [][] neighbors, PacTimer timer) {
		super(board, inisialPositionTile ,pacman, neighbors , "white" ,new Pair(1,1) , "u", timer);
		this.canHit = true;
		this.cantHitIm = new ImageIcon("pictures/figures/scared.png").getImage();
		
	}

	@Override
	public void visit(NicePacman pacman) {
		if(this.canHit)
	this.pacman.dead();
	}

	@Override
	public void visit(DefendedPacman pacman) {
		if(this.canHit)
	this.pacman.freeze();
		}

	@Override
	public void visit(AngryPacman pacman) {
		if(this.canHit)
		this.pacman.dead();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		Pair boardTileOfPac = new Pair(this.pacman.getCurrentPosition().getX(), this.pacman.getCurrentPosition().getY());
			if(this.isStart) { 
				if(timeFromChase % 20 == 0 & timeFromChase >= 11) { //every 20 timers ticks this ghost will transpose to the pacman location and transpose to scare mode so it wont immediately kill the pacman
				this.lastBoardTileIn.setX(this.boardTileIn.getX());
				this.lastBoardTileIn.setY(this.boardTileIn.getY());
				this.boardTileIn.setX(boardTileOfPac.getX());
				this.boardTileIn.setY(boardTileOfPac.getY());
				this.canHit = false;
				}
				else {
					if(timeFromChase % 20 > 2)
						this.canHit = true;
					if( timeFromStart%5 == 0) {
						if(timeFromChase != 1 & timeFromChase != 2) //should the ghost wait two steps or two seconds
							this.move();
						}
				}
				if(timeFromChase >= 5 & timeFromChase < 11) //this will dim the ghost before she can attack
					dimGhost();
				if(!canHit)
					this.currPositionIm = this.cantHitIm;
				if( timeFromChase != 0) 
					timeFromChase++;
			timeFromStart++;
		}
	}

}

package Ghosts;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.ImageIcon;

import GamePack.Board;
import GamePack.PacTimer;
import GamePack.Pair;
import Pacmen.AngryPacman;
import Pacmen.DefendedPacman;
import Pacmen.NicePacman;
import Pacmen.Pacman;
import Tiles.BoardTile;

public class PinkGhost extends Ghost{
	private Image fastImg;

	public PinkGhost(BoardTile[][]board, Pair inisialPositionTile,Pacman pacman, Vector<String> [][] neighbors, PacTimer timer) {
		super(board, inisialPositionTile ,pacman, neighbors , "pink" ,new Pair(1,30) , "l", timer);
		this.fastImg = new ImageIcon("pictures/figures/space.png").getImage();
	}

	@Override
	public void visit(NicePacman pacman) {
	this.pacman.dead();
	}

	@Override
	public void visit(DefendedPacman pacman) {
		this.pacman.freeze();
	}

	@Override
	public void visit(AngryPacman pacman) {
		this.pacman.dead();
	}
	@Override
	public void draw(Board board, Graphics g) { 
		super.draw(board, g);
	}
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if(this.isStart) {
			if( timeFromStart%8 == 0  & (timeFromChase % 100 > 80 | timeFromChase <= 12)) {//this will return the ghost speed to the normal tile in 8 timer tickis
				if(timeFromChase != 1 & timeFromChase != 2) 
					this.move();
			}
			else {
				
				if(timeFromChase > 12 & timeFromChase % 100 <= 80) {//this will make the ghost move fast and turn picture to crazy ghost
					move();
					this.currPositionIm = this.fastImg;
				}
			}
			if(timeFromChase >= 5 & timeFromChase < 11)  //this will make the ghost dim for two timer ticks
				dimGhost();
				if( timeFromChase != 0) 
					timeFromChase++;
			timeFromStart++;
		}

	}
}

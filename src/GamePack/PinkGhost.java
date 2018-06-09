package GamePack;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.ImageIcon;

import Tiles.BoardTile;

public class PinkGhost extends Ghost{
	private Image fastImg;

	public PinkGhost(BoardTile[][]board, Pair inisialPositionTile,Pacman pacman, Vector<String> [][] neighbors) {
		super(board, inisialPositionTile ,pacman, neighbors , "pink" ,new Pair(1,30) , "l");
		this.fastImg = new ImageIcon("pictures/figures/space.png").getImage();
	}

	@Override
	public void visit(NicePacman pacman) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(DefendedPacman pacman) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(AngryPacman pacman) {
		// TODO Auto-generated method stub

	}
	@Override
	public void draw(Board board, Graphics g) { 
		super.draw(board, g);
	}
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if(this.isStart) {
			if( timeFromStart%8 == 0  & (timeFromChase % 100 > 80 | timeFromChase <= 12)) {
				if(timeFromChase != 1 & timeFromChase != 2) //should the ghost wait two steps or two seconds
					this.move();
			}
			else {
				
				if(timeFromChase > 12 & timeFromChase % 100 <= 80) {
					move();
					this.currPositionIm = this.fastImg;
				}
			}
			if(timeFromChase >= 5 & timeFromChase < 11) 
				dimGhost();
				if( timeFromChase != 0) 
					timeFromChase++;
			timeFromStart++;
		}

	}
}

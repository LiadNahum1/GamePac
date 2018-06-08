package GamePack;

import java.awt.event.ActionEvent;
import java.util.Vector;

import Tiles.BoardTile;

public class WhiteGhost extends Ghost{
private boolean canHit;
	public WhiteGhost(BoardTile[][]board, Pair inisialPositionTile,Pacman pacman, Vector<String> [][] neighbors) {
		super(board, inisialPositionTile ,pacman, neighbors , "white" ,new Pair(1,30) , "l");
		this.canHit = true;
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
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		Pair boardTileOfPac = new Pair(this.pacman.getCurrentPosition().getX(), this.pacman.getCurrentPosition().getY());
			if(this.isStart) {
				if(timeFromChase % 20 == 0 & timeFromChase >= 11) {
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
				if(timeFromChase >= 5 & timeFromChase < 11) 
					dimGhost();
				if( timeFromChase != 0) 
					timeFromChase++;
			timeFromStart++;
		}
	}

}

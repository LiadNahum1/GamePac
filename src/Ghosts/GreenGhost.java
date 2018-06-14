package Ghosts;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.Vector;

import GamePack.Board;
import GamePack.Mode;
import GamePack.Pair;
import Pacmen.AngryPacman;
import Pacmen.DefendedPacman;
import Pacmen.NicePacman;
import Pacmen.Pacman;
import Tiles.BoardTile;

public class GreenGhost extends Ghost {
	public GreenGhost( BoardTile[][]board, Pair inisialPositionTile, Pacman pacman,Vector<String>[][] neighbors) {
		super(board, inisialPositionTile , pacman, neighbors,"green",new Pair(1,1) ,"u");
		this.ticks= 0;
	}

	@Override
	/*pacman dies*/
	public void visit(NicePacman pacman) {
		pacman.dead();
		
	}

	@Override
	/*ghost disappear for 5 seconds*/
	public void visit(DefendedPacman pacman) {
		if(this.mode.equals(Mode.ALIVE))
			disappear();
	}

	@Override
	/*ghost dies*/
	public void visit(AngryPacman pacman) {
		die();
	}
	public void disappear() {
		this.mode = Mode.DISAPPEAR;
		int x = getBoardTileIn().getX();
		int y = getBoardTileIn().getY();
		setCurrentPositionIm(this.boardTiles[x][y].getImage());
	}
	public void revive() {
		this.ticks= 0;
		this.mode = Mode.ALIVE;
		setCurrentPositionIm(this.position.get(this.curPos));
	}
	public void die() {
		disappear();
		this.mode = Mode.DEAD;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!this.mode.equals(Mode.DEAD)) {
			if(this.mode.equals(Mode.DISAPPEAR) & this.ticks < 5) {
				this.ticks= this.ticks+ 1;
			}
			else {
				super.actionPerformed(e);
				if(this.ticks== 5) {
					revive();
				}
				if(this.isStart) {
					if( timeFromStart%2 == 0) {
						if(timeFromChase != 1 & timeFromChase != 2) 
							this.move();
						if( timeFromChase!= 0)
							timeFromChase++;
					}
					timeFromStart++;
				}
			}
		}
	}
	public void draw(Board board, Graphics g) { 
		//draw one last time
		if(this.mode.equals(Mode.DEAD)){
			if(this.ticks== 0) {
				super.draw(board, g);
				this.ticks= this.ticks+1;
			}
		}
		else {
			//draw if ghost is alive or if its the first tick of the ghost in disappear mode
			if(this.mode.equals(Mode.ALIVE) | this.ticks== 1) {
				super.draw(board, g);
			}
		}
	}
}

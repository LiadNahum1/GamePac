package Pacmen;
import javax.swing.ImageIcon;
import GamePack.PacTimer;
import GamePack.Pair;
import Ghosts.Visitor;
import Tiles.BoardTile;

/*defines the Pacman figure of level 2*/
public class DefendedPacman extends Pacman {	
	public DefendedPacman(Pair initialPosition, BoardTile[][] board, String[][]boardStr, PacTimer timer) {
		super(initialPosition, board, boardStr, timer);
		this.pacmanIcons[0] = new ImageIcon("pictures\\figures\\DefendedPacman\\left.png");
		this.pacmanIcons[1] = new ImageIcon("pictures\\figures\\DefendedPacman\\right.png");
		this.pacmanIcons[2] = new ImageIcon("pictures\\figures\\DefendedPacman\\up.png");
		this.pacmanIcons[3] = new ImageIcon("pictures\\figures\\DefendedPacman\\down.png");
		this.currentIcon = this.pacmanIcons[0];
	}
	public void impact(Visitor v) {
		v.visit(this);
	}
	
}

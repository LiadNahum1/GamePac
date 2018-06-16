package Pacmen;
import javax.swing.ImageIcon;
import GamePack.PacTimer;
import GamePack.Pair;
import Ghosts.Visitor;
import Tiles.BoardTile;

/*defines the Pacman figure of level 1*/
public class NicePacman extends Pacman{
	public NicePacman(Pair initialPosition, BoardTile[][]board, String [][]boardStr, PacTimer timer) {
		super(initialPosition, board, boardStr, timer);
		this.pacmanIcons[0] = new ImageIcon("pictures\\figures\\NicePacman\\left.png");
		this.pacmanIcons[1] = new ImageIcon("pictures\\figures\\NicePacman\\right.png");
		this.pacmanIcons[2] = new ImageIcon("pictures\\figures\\NicePacman\\up.png");
		this.pacmanIcons[3] = new ImageIcon("pictures\\figures\\NicePacman\\down.png");
		this.currentIcon = this.pacmanIcons[0];
	}
	public void impact(Visitor v) {
		v.visit(this);
	}
	
	
}
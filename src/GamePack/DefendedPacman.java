package GamePack;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

import Tiles.BoardTile;

public class DefendedPacman extends Pacman {
	private ImageIcon [] pacmanIcons;
	
	public DefendedPacman(Pair initialPosition, BoardTile[][]board) {
		super(initialPosition, board);
		this.pacmanIcons[0] = new ImageIcon("pictures\\figures\\DefendedPacman\\left.png");
		this.pacmanIcons[1] = new ImageIcon("pictures\\figures\\DefendedPacman\\right.png");
		this.pacmanIcons[2] = new ImageIcon("pictures\\figures\\DefendedPacman\\up.png");
		this.pacmanIcons[3] = new ImageIcon("pictures\\figures\\DefendedPacman\\down.png");
		this.pacmanIcons[4] = new ImageIcon("pictures\\figures\\DefendedPacman\\fullPacman.png");
	}

}

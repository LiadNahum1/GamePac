package GamePack;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

import Tiles.BoardTile;

public class AngryPacman extends Pacman {
	private ImageIcon [] pacmanIcons;
	
	public AngryPacman(Pair initialPosition, BoardTile[][]board) {
		super(initialPosition, board);
		this.pacmanIcons = new ImageIcon[5];
		this.pacmanIcons[0] = new ImageIcon("pictures\\figures\\AngryPacman\\left.png");
		this.pacmanIcons[1] = new ImageIcon("pictures\\figures\\AngryPacman\\right.png");
		this.pacmanIcons[2] = new ImageIcon("pictures\\figures\\AngryPacman\\up.png");
		this.pacmanIcons[3] = new ImageIcon("pictures\\figures\\AngryPacman\\down.png");
		this.pacmanIcons[4] = new ImageIcon("pictures\\figures\\AngryPacman\\fullPacman.png");
	}
}

package Pacmen;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

import GamePack.Pair;
import Ghosts.Visitor;
import Tiles.BoardTile;

public class AngryPacman extends Pacman {	
	public AngryPacman(Pair initialPosition, BoardTile[][]board, String [][]boardStr) {
		super(initialPosition, board, boardStr);
		this.pacmanIcons[0] = new ImageIcon("pictures\\figures\\AngryPacman\\left.png");
		this.pacmanIcons[1] = new ImageIcon("pictures\\figures\\AngryPacman\\right.png");
		this.pacmanIcons[2] = new ImageIcon("pictures\\figures\\AngryPacman\\up.png");
		this.pacmanIcons[3] = new ImageIcon("pictures\\figures\\AngryPacman\\down.png");
		//this.pacmanIcons[4] = new ImageIcon("pictures\\figures\\AngryPacman\\fullPacman.png");
		this.currentIcon = this.pacmanIcons[0];
	}
	public void impact(Visitor v) {
		v.visit(this);
	}
	
}

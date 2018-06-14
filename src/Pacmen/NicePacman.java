package Pacmen;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

import GamePack.Pair;
import Ghosts.Visitor;
import Tiles.BoardTile;
import Tiles.RoadTile;

public class NicePacman extends Pacman{
	

	public NicePacman(Pair initialPosition, BoardTile[][]board, String [][]boardStr) {
		super(initialPosition, board, boardStr);
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
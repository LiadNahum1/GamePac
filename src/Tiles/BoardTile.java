package Tiles;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import Food.Food;

public abstract class BoardTile {
	private int x; //position on matrix
	private int y; 
	protected ImageIcon imageIcon; 
	private boolean isSomethingOn;

	public BoardTile(int x, int y , boolean isSomethingOn) {
		this.x = x;
		this.y = y; 
		this.isSomethingOn = isSomethingOn;

	}
	public Image getImage() {
		return this.imageIcon.getImage();
	}
	
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}

	public boolean getIsSomethingOn() {
		return this.isSomethingOn;
	}
	public void setIsSomethingOn(boolean bool) {
		this.isSomethingOn = bool;
	}
	
	
}

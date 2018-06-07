package Tiles;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public abstract class BoardTile {
	private int x; //position on matrix
	private int y; 
	protected ImageIcon imageIcon; 
	public BoardTile(int x, int y) {
		this.x = x;
		this.y = y; 
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
}

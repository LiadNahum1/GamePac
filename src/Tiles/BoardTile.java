package Tiles;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public abstract class BoardTile {
	private int x; //position on matrix
	private int y; 
	public BoardTile(int x, int y) {
		this.x = x;
		this.y = y; 
	}
	private static final long serialVersionUID = 1L;
	public ImageIcon imageIcon; 
	
	public Image getImage() {
		return imageIcon.getImage();
	}
	
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
}

package Tiles;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public abstract class BoardTile {
	private int x; //position on matrix
	private int y; 
	private boolean isMovable;
	public BoardTile(int x, int y , boolean isMovable) {
		this.x = x;
		this.y = y; 
	}
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

	public boolean isMovable() {
		return isMovable;
	}

	public void setMovable(boolean isMovable) {
		this.isMovable = isMovable;
	}
}

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
	protected Food food; 

	public BoardTile(int x, int y , boolean isSomethingOn, Food food) {
		this.x = x;
		this.y = y; 
		this.isSomethingOn = isSomethingOn;
		this.food = food;

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
	public Food getFood() {
		return this.food;
	}
	public void setFood(Food food) {
		
	}

	/*returns num of points the player gets for this eating*/
	public int eaten() {
		if(this.food!=null) {
			int worth = this.food.getWorth();
			return worth;
		}
		else
			return 0; 
	}
	
	
}

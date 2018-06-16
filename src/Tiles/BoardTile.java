package Tiles;
import java.awt.Image;
import javax.swing.ImageIcon;
import Food.Food;
import GamePack.Pair;

/*abstract class which defines a BoardTile*/
public abstract class BoardTile {
	private Pair p; //position of cell on matrix while x is row and y is column 
	protected ImageIcon imageIcon; 
	private boolean isSomethingOn;
	protected Food food; 

	public BoardTile(int x, int y , boolean isSomethingOn, Food food) {
		this.p = new Pair(x, y);
		this.isSomethingOn = isSomethingOn;
		this.food = food;

	}
	public Image getImage() {
		return this.imageIcon.getImage();
	}
	
	public int getX() {
		return this.p.getX();
	}
	public int getY() {
		return this.p.getY();
	}

	public boolean getIsSomethingOn() {
		return this.isSomethingOn;
	}
	public void setIsSomethingOn(boolean bool) {
		this.isSomethingOn = bool;
	}
	
	/*get food that is painted on top of this BoardTile*/
	public Food getFood() {
		return this.food;
	}
	/*set food that will be painted on top of this BoardTile*/
	public void setFood(Food food) {}

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

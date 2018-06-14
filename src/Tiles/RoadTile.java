package Tiles;
import java.awt.Color;
import java.awt.Image;

import javax.swing.*;

import Food.*;

public class RoadTile extends BoardTile {
	public static ImageIcon road; 
	protected Food food;

	public RoadTile(int x, int y,boolean isSomethingOn, Food food) {
		super(x,y,isSomethingOn);
		road = new ImageIcon("pictures/boards/road.png");
		this.imageIcon = road;
		this.food = food; 
		if(food!=null)
			this.imageIcon = food.getImage();
	}

	public Food getFood() {
		return this.food;
	}
	public void setFood(Food food) {
		this.food = food; 
		if(food == null) {
			this.imageIcon = road;
			setIsSomethingOn(false);
		}
		else {
			this.imageIcon = this.food.getImage();
			setIsSomethingOn(true); 
		}
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



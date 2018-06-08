package Tiles;
import java.awt.Color;
import java.awt.Image;

import javax.swing.*;

import Food.*;

public class RoadTile extends BoardTile {
	private boolean isSomethingOn; 
	private ImageIcon road; 
	private Food food;
	
	public RoadTile(int x, int y,Food food) {
		super(x,y,true);
		this.road = new ImageIcon("pictures/boards/road.png");
		setFood(food);
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
		this.food = food; 
		if(food == null) {
			this.imageIcon = this.road;
			this.isSomethingOn = false;
		}
		else {
			this.imageIcon = this.food.getImage();
			this.isSomethingOn = true; 
		}
	}
	public void dimElement() {
		if(this.imageIcon.equals(this.road) & this.food != null) {
			this.imageIcon = this.food.getImage(); 
		}
		else {
			this.imageIcon = this.road;
		}
	}
	/*returns num of points the player gets for this eating*/
	public int eaten() {
		if(this.food!=null) {
			int worth = this.food.getWorth();
			setFood(null);
			return worth;
		}
		else
			return 0; 
	}
}



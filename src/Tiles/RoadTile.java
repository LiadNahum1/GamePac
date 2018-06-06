package Tiles;
import java.awt.Color;
import java.awt.Image;

import javax.swing.*;

import Food.*;

public class RoadTile extends BoardTile {
	private boolean isSomethingOn; 
	private ImageIcon road; 
	private Food food; 
	private boolean dimmed; 
	public RoadTile(int x, int y,Food food) {
		super(x,y);
		imageIcon = new ImageIcon("pictures/boards/road.png");
		setFood(food);
		this.dimmed = false; 
	}

	public boolean getIsSomethingOn() {
		return this.isSomethingOn;
	}
	public void setIsSomethingOn(Food food) {
		setFood(food);
	}
	public Image getImage() {
		return getCurrentImage().getImage();
	}
	public ImageIcon getCurrentImage() {
		if(getIsSomethingOn()) {
			return this.food.getImage();
		}
		else
			return imageIcon;
	}
	public void setFood(Food food) {
		this.food = food; 
		if(food == null) {
			this.isSomethingOn = false;
		}
		else {
			this.isSomethingOn = true; 
		}
	}
	public void dimElement() {
		if(!this.dimmed) {
			this.dimmed = true;
		}
		else {
			this.dimmed = false; 
		}
	}
	/*returns num of points the player gets for this eating*/
	public int eat() {
		if(isSomethingOn) {
			int worth = this.food.getWorth();
			setFood(null);
			return worth;
		}
		else
			return 0; 
	}
}


package Tiles;
import java.awt.Color;
import java.awt.Image;

import javax.swing.*;

import Food.*;

public class RoadTile extends BoardTile {
	public static ImageIcon road; 

	public RoadTile(int x, int y,boolean isSomethingOn, Food food) {
		super(x,y,isSomethingOn, food);
		road = new ImageIcon("pictures/boards/road.png");
		imageIcon = road;
		if(food!=null)
			this.imageIcon = food.getImage();
	}
	@Override
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

}



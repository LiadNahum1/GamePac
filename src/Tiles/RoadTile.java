package Tiles;
import javax.swing.*;
import Food.*;

/*defines a RoadTile. Only on it, the pacman can move. All foods kind are on top of RoadTiles 
 * and not other tiles
 */
public class RoadTile extends BoardTile {
	public static ImageIcon road; //when nothing on top it, the image is a road 

	public RoadTile(int x, int y,boolean isSomethingOn, Food food) {
		super(x,y,isSomethingOn, food);
		road = new ImageIcon("pictures/boards/road.png");
		imageIcon = road;
		if(food!=null)
			this.imageIcon = food.getImage();
	}
	@Override
	/*If food in null, the image of the RoadTile is a road. Otherwise, the image is of the food that on top of it*/
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



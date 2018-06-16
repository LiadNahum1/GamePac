package Food;
import javax.swing.ImageIcon;

/*abstract class which defines a Food object*/
public abstract class Food {
	protected ImageIcon image;
	protected int worth;
	
	/*The function returns the food's worth - how many points the player gets for eating it*/
	public int getWorth() {
		return this.worth; 
	}
	/*The function returns the ImageIcon of the food*/ 
	public ImageIcon getImage() {
		return this.image; 
	}
}

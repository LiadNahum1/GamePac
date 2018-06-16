package Food;
import java.awt.Image;

import javax.swing.ImageIcon;

public abstract class Food {
	protected ImageIcon image;
	protected int worth;
	
	public int getWorth() {
		return this.worth; 
	}
	public ImageIcon getImage() {
		return this.image; 
	}


}

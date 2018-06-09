package Food;
import java.awt.Image;

import javax.swing.ImageIcon;

public abstract class Food {
	protected ImageIcon image;
	protected Image bigImage;
	protected int worth;
	
	public int getWorth() {
		return this.worth; 
	}
	public ImageIcon getImage() {
		return this.image; 
	}
	public Image getBigImage() {
		return this.bigImage; 
	}

}

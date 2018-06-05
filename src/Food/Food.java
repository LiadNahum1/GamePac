package Food;
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

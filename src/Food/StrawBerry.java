package Food;
import javax.swing.ImageIcon;

public class StrawBerry extends Food{
	public StrawBerry() {
		this.image = new ImageIcon("pictures/boards/strawBerry.png");
		this.bigImage = new ImageIcon("pictures/boards/strawBerry.png").getImage();
		this.worth = 300;

	}

}

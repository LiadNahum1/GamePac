package Food;
import javax.swing.ImageIcon;

public class PineApple extends Food {
	public PineApple() {
		this.image = new ImageIcon("pictures/boards/pineApple.png");
		this.bigImage = new ImageIcon("pictures/boards/BpineApple.png").getImage();
		this.worth = 100;

	}
}

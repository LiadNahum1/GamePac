package Tiles;
import javax.swing.*;

/* the class which defines a GateTile.*/
public class GateTile extends BoardTile {
	public GateTile (int x, int y){
        super(x, y ,true, null);
       imageIcon = new ImageIcon("pictures/boards/gate.png");
    }
}

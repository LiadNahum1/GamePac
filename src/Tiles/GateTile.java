package Tiles;
import javax.swing.*; 

import GamePack.*;

public class GateTile extends BoardTile {
	public GateTile (int x, int y){
        super(x, y ,true);
       imageIcon = new ImageIcon("pictures/boards/gate.png");
    }    
}

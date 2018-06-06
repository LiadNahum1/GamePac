package Tiles;
import javax.swing.*;

import GamePack.*;

public class GateTile extends BoardTile {
	public GateTile (int x, int y){
        super(x, y);
       imageIcon = new ImageIcon("pictures/boards/gate.png");
    }
    public boolean isMovable(Pacman pac){
        return false;
    }
    public boolean isMovable(Ghost ghost){
        return true;
    }
}

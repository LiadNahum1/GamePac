package Tiles;
import javax.swing.ImageIcon;


public class WallTile extends BoardTile {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageIcon [] pictures;
public WallTile (int level){
    super();
    inisializePictures();
    imageIcon = pictures[level-1]; 
}
    private void inisializePictures() {
    pictures = new ImageIcon[3];
        pictures[0] = new ImageIcon("pictures/boards/level1wall.png");
        pictures[1] = new ImageIcon("pictures/boards/level2wall.png");
        pictures[2] = new ImageIcon("pictures/boards/level3wall.png");
    }
    public boolean isMovable(Object Obj){
        return false;
    }
}

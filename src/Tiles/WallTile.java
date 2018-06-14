package Tiles;
import javax.swing.ImageIcon;


public class WallTile extends BoardTile {

	private ImageIcon [] pictures;
	public WallTile (int level, int x, int y){
		super(x,y ,true, null);
		inisializePictures();
		imageIcon = pictures[level-1]; 
	}
	private void inisializePictures() {
		pictures = new ImageIcon[3];
		pictures[0] = new ImageIcon("pictures/boards/level1wall.png");
		pictures[1] = new ImageIcon("pictures/boards/level2wall.png");
		pictures[2] = new ImageIcon("pictures/boards/level3wall.png");
	}
	
}

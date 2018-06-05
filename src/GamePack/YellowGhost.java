package GamePack;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.Vector;

public class YellowGhost extends Ghost{
	public YellowGhost(Pair pxIn, Pair chasePlace, Pair boardTileIn, Vector<String>[][] neighbors ,Graphics g) {
		super(pxIn, chasePlace, boardTileIn,neighbors, "yellow", g);
	}

		@Override
		public void visit(NicePacman pacman) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visit(DefendedPacman pacman) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visit(AngryPacman pacman) {
			// TODO Auto-generated method stub
			
		}


}

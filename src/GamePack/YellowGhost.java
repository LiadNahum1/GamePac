package GamePack;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.Vector;

public class YellowGhost extends Ghost{
	public YellowGhost( Pacman pac, Pair boardTileIn, Vector<String>[][] neighbors ) {
		super( pac, boardTileIn,neighbors, "yellow",new Pair(31,31));
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

		@Override
		public void attack() {
			// TODO Auto-generated method stub
			
		}

		


}

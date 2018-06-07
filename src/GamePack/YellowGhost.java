package GamePack;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.Vector;

public class YellowGhost extends Ghost{
	private  Pair tileAnderAttack;
	private Pair prevTileAnderAttack;
	private Image attackImg;
	private boolean inAttack;

	public YellowGhost( Pacman pac, Pair boardTileIn, Vector<String>[][] neighbors ) {
		super( pac, boardTileIn,neighbors, "yellow",new Pair(1,30));
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

		public void advanceAttack() { //to finish
			if(inAttack) {
			this.prevTileAnderAttack.setX(this.boardTileIn.getX());
			this.prevTileAnderAttack.setY(this.boardTileIn.getY());
		this.tileAnderAttack = advanceMove();
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			super.actionPerformed(e);
				if(timeFromChase >= 5 & timeFromChase <= 10) {
					dimGhost();
				}
				if(timeFromChase > 11)
					advanceAttack();
			}

		@Override
		public void attack() {
			// TODO Auto-generated method stub
			
		}
		


}

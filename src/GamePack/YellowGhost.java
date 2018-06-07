package GamePack;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.Vector;

public class YellowGhost extends Ghost{
	private  Pair tileAnderAttack;
	private Pair prevTileAnderAttack;
	private Image attackImg;
	private boolean inAttack;

	public YellowGhost(  Pair boardTileIn, Vector<String>[][] neighbors ) {
		super(boardTileIn,neighbors, "yellow",new Pair(1,30));
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
		if(this.isStart) {
			if( timeFromStart%4 == 0) {
				if(timeFromChase != 1 & timeFromChase != 2) 
					this.move();
				if(timeFromChase >= 5 & timeFromChase <= 10) { //problems
					dimGhost();
				}
				if(timeFromChase > 11)
					advanceAttack();
				if( timeFromChase!= 0)
					timeFromChase++;
			}
			timeFromStart++;
		}
	}


}

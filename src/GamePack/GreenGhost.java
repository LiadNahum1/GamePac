package GamePack;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.Vector;

public class GreenGhost extends Ghost {
	public GreenGhost( Pair inisialPositionTile,Vector<String>[][] neighbors) {
		super(inisialPositionTile , neighbors,"green",new Pair(1,1));

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
	public void actionPerformed(ActionEvent e) {
		if(this.isStart) {
			if( timeFromStart%2 == 0) {
				if(timeFromChase != 1 & timeFromChase != 2) 
					this.move();
				if( timeFromChase!= 0)
					timeFromChase++;
			}
			timeFromStart++;
		}
	}




}

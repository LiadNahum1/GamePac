package GamePack;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.Vector;

public class GreenGhost extends Ghost {
	public GreenGhost(Pair inisialPxIn, Pair packmanPosition,Pair inisialPositionTile,Vector<String>[][] neighbors, Graphics g) {
		super(inisialPxIn,packmanPosition , inisialPositionTile , neighbors,"green",g);
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

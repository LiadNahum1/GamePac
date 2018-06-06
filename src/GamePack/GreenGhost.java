package GamePack;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.Vector;

public class GreenGhost extends Ghost {
	public GreenGhost( Pacman pac,Pair inisialPositionTile,Vector<String>[][] neighbors) {
		super(pac , inisialPositionTile , neighbors,"green",new Pair(1,1));
		System.out.println("built");
			
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

package Ghosts;

import Pacmen.AngryPacman;
import Pacmen.DefendedPacman;
import Pacmen.NicePacman;

public interface Visitor {
	void visit(NicePacman pacman);
	void visit(DefendedPacman pacman);
	void visit(AngryPacman pacman);
}

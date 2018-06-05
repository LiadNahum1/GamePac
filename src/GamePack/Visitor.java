package GamePack;

public interface Visitor {
	void visit(NicePacman pacman);
	void visit(DefendedPacman pacman);
	void visit(AngryPacman pacman);
}

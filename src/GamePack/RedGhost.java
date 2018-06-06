package GamePack;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.ImageIcon;

public class RedGhost extends Ghost {
	private  Pair tileAnderAttack;
	private Pair prevTileAnderAttack;
	private Image attackImg;
	private boolean inAttack;

	public RedGhost(Pacman pac,Pair inisialPositionTile,Vector<String> [][] neighbors) {
		super(pac, inisialPositionTile ,neighbors , "red" ,new Pair(1,1));
		this.attackImg = new ImageIcon("pictures/figures/fire.png").getImage();
		this.inAttack = false;
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
	public void draw(Game game, Graphics g) { 
		super.draw(game, g);
		if(inAttack) {
			Image offIm1 = game.createImage(20, 20);//this will draw last board tile
			Graphics offGr1 = offIm1.getGraphics();	
			offGr1.drawImage(game.getBoardTile(this.prevTileAnderAttack).getImage(),0,0, game);
			g.drawImage(offIm1,this.prevTileAnderAttack.getY()*20,this.prevTileAnderAttack.getX()*20, game);
			Image offIm2 = game.createImage(20 , 20);//this will draw next board tile
			Graphics offGr2 = offIm2.getGraphics();	
			offGr2.drawImage(this.attackImg, 0,0, game);
			g.drawImage(offIm2,this.tileAnderAttack.getY()*20, this.tileAnderAttack.getX()*20, game);
		}
	}


	public void advanceAttack() {
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
}

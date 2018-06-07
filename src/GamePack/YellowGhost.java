package GamePack;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.ImageIcon;

public class YellowGhost extends Ghost{
	private  Pair tileAnderAttack;
	private Pair prevTileAnderAttack;
	private Image attackImg;
	private boolean inAttack;
	private String attackPos;

	public YellowGhost(  Pair boardTileIn, Vector<String>[][] neighbors ) {
		super(boardTileIn,neighbors, "yellow",new Pair(1,30) ,"u");
		this.attackImg = new ImageIcon("pictures/figures/iceWave.png").getImage();
		this.inAttack = false;
		prevTileAnderAttack = new Pair(0,0);
		tileAnderAttack = new Pair(0,0);
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
	public void draw(Board board, Graphics g) { 
		super.draw(board, g);
		Image offIm1 = board.createImage(20, 20);//this will draw last board tile
		Graphics offGr1 = offIm1.getGraphics();	
		offGr1.drawImage(board.getBoardTile(this.prevTileAnderAttack).getImage(),0,0, board);
		g.drawImage(offIm1,this.prevTileAnderAttack.getY()*20,this.prevTileAnderAttack.getX()*20, board);
		if(inAttack) {
			Image offIm2 = board.createImage(20 , 20);//this will draw next board tile
			Graphics offGr2 = offIm2.getGraphics();	
			offGr2.drawImage(this.attackImg, 0,0, board);
			g.drawImage(offIm2,this.tileAnderAttack.getY()*20, this.tileAnderAttack.getX()*20, board);
		}
	}
	public void advanceAttack() { //to finish
		if(inAttack) {
			this.prevTileAnderAttack.setX(this.tileAnderAttack.getX());
			this.prevTileAnderAttack.setY(this.tileAnderAttack.getY());
			Vector<String> posDirs = this.neighbors[prevTileAnderAttack.getX()][prevTileAnderAttack.getY()];
			if(attackPos == "u" & posDirs.contains("u")) 
				this.tileAnderAttack.sumSetX(-1);
			else if(attackPos == "d" & posDirs.contains("d")) 
				this.tileAnderAttack.sumSetX(1);
			else if(attackPos == "l" & posDirs.contains("l")) 
				this.tileAnderAttack.sumSetY(-1);
			else if(attackPos == "r" & posDirs.contains("r")) 
				this.tileAnderAttack.sumSetY(1);
			else inAttack = false;
		}
		else {inAttack = true;
		attackPos = this.curPos;
		this.tileAnderAttack = advanceMove();
		if(this.neighbors[tileAnderAttack.getX()][tileAnderAttack.getY()] == null)
			inAttack = false;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(this.isStart) {
			if( timeFromStart%4 == 0) {
				if(timeFromChase != 1 & timeFromChase != 2) //should the ghost wait two steps or two seconds
					this.move();
			}
			if(timeFromChase >= 5 & timeFromChase < 11) 
				dimGhost();
			if(timeFromChase >= 11) 
				advanceAttack();
			if( timeFromChase != 0) 
				timeFromChase++;
		}
		timeFromStart++;
	}



}

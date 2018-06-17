package Ghosts;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.ImageIcon;

import GamePack.Board;
import GamePack.PacTimer;
import GamePack.Pair;
import Pacmen.AngryPacman;
import Pacmen.DefendedPacman;
import Pacmen.NicePacman;
import Pacmen.Pacman;
import Tiles.BoardTile;
/*this ghost can attack with a fire ball that goes 
 * in one diraction and can go threw walls*/
public class RedGhost extends Ghost {
	private  Pair tileAnderAttack;
	private Pair prevTileAnderAttack;
	private Image attackImg;
	private String attackPos;
	private boolean inAttack;

	public RedGhost(BoardTile[][]board, Pair inisialPositionTile,Pacman pacman, Vector<String> [][] neighbors, PacTimer timer) {
		super(board, inisialPositionTile ,pacman, neighbors , "red" ,new Pair(1,1) , "r", timer);
		this.attackImg = new ImageIcon("pictures/figures/fire.png").getImage();
		this.inAttack = false;
		this.prevTileAnderAttack = new Pair(0,0);
		tileAnderAttack = new Pair(0,0);
	}
	@Override
	public void inisializeData( Pair a, Pair b, String color) {
		super.inisializeData(a, b, color);
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
		pacman.dead();
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

//this will launch and move attacks
	public void advanceAttack() {
		int yAxsis;
		int xAxsis;
		if(inAttack) { //if the ghost is already in the attack this will continue it the same direction
			this.prevTileAnderAttack.setX(this.tileAnderAttack.getX());
			this.prevTileAnderAttack.setY(this.tileAnderAttack.getY()); //this will find the attack direction
			if(attackPos == "u") 
				this.tileAnderAttack.sumSetX(-1);
			if(attackPos == "d") 
				this.tileAnderAttack.sumSetX(1);
			if(attackPos == "l") 
				this.tileAnderAttack.sumSetY(-1);
			if(attackPos == "r") 
				this.tileAnderAttack.sumSetY(1);
		}
		else {inAttack = true; //if the last attack is finished the it start a new one 
		attackPos = this.curPos;
		this.tileAnderAttack = advanceMove();
		}
		yAxsis = tileAnderAttack.getX();
		xAxsis = tileAnderAttack.getY();
		if(yAxsis <0 | yAxsis >31 | xAxsis < 0 | xAxsis > 31) //if the attack reached the end of the board stop it
			this.inAttack = false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		Pair boardTileOfPac = new Pair(this.pacman.getCurrentPosition().getX(), this.pacman.getCurrentPosition().getY());
		if(this.tileAnderAttack.equals(boardTileOfPac))
			collide(this.pacman);
		else {
			if(this.isStart) {
				if( timeFromStart%6 == 0) {
					if(timeFromChase != 1 & timeFromChase != 2) //should the ghost wait two steps or two seconds
						this.move();
				}
				if(timeFromChase >= 5 & timeFromChase < 11)  //this will dim the ghost before she can attack
					dimGhost();
				if(timeFromChase >= 11) 
					advanceAttack();
				if( timeFromChase != 0) 
					timeFromChase++;
			}
			timeFromStart++;
		}
	}
}


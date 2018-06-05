package GamePack;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class AngryPacman extends Pacman {
	private ImageIcon [] pacmanIcons;
	
	public AngryPacman() {
		this.pacmanIcons = new ImageIcon[5];
		this.pacmanIcons[0] = new ImageIcon("pictures\\figures\\AngryPacman\\left.png");
		this.pacmanIcons[1] = new ImageIcon("pictures\\figures\\AngryPacman\\right.png");
		this.pacmanIcons[2] = new ImageIcon("pictures\\figures\\AngryPacman\\up.png");
		this.pacmanIcons[3] = new ImageIcon("pictures\\figures\\AngryPacman\\down.png");
		this.pacmanIcons[4] = new ImageIcon("pictures\\figures\\AngryPacman\\fullPacman.png");
	}
	@Override
	public void impact(Visitor v) {
		v.visit(this);
		}
	@Override
	public ImageIcon getCurrentIcon() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Pair getCurrentPosition() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void manageMovement(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void draw(Game game, Graphics g) {
		// TODO Auto-generated method stub
	
	}
}

package GamePack;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public abstract class Pacman implements Visited{


	public abstract void impact(Visitor v);


	public void move() {
		// TODO Auto-generated method stub
	}

	public abstract void draw(Game game, Graphics g);
	public abstract ImageIcon getCurrentIcon(); 
	public abstract Pair getCurrentPosition();
	public abstract void manageMovement(KeyEvent e);
}

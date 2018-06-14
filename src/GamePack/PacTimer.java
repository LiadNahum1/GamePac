package GamePack;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class PacTimer  {
	private Timer gameTimer; // main timer of the game ;
	private int speed;

	public PacTimer(ActionListener game ,ActionListener whiteGhost,ActionListener pinkGhost,ActionListener greenGhost,ActionListener redGhost,ActionListener yellowGhost, ActionListener pacman) {
		this.gameTimer = new Timer(100, game);
		this.gameTimer.addActionListener(pinkGhost);
		this.gameTimer.addActionListener(whiteGhost);
		this.gameTimer.addActionListener(greenGhost);
		this.gameTimer.addActionListener(redGhost);
		this.gameTimer.addActionListener(yellowGhost);
		this.gameTimer.addActionListener(pacman);
		this.speed = 1;
	}

	public Timer getGameTimer() {
		return this.gameTimer;
	}
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
		updateGameSpeed();
	}
	private void updateGameSpeed() {
		this.gameTimer.setDelay(1/speed*1000);
	}
	public void stop() {
		this.gameTimer.stop();
	}
	

}

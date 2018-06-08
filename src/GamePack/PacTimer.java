package GamePack;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class PacTimer  {
	private Timer gameTimer; // main timer of the game ;
	private int speed;

	public PacTimer(ActionListener game ,ActionListener greenGhost,ActionListener redGhost,ActionListener yellowGhost, ActionListener pacman) {
		this.gameTimer = new Timer(500, game);
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
	

}

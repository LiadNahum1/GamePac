package GamePack;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class PacTimer  {
	private Timer gameTimer; // main timer of the game ;
	private int speed;

	public PacTimer(ActionListener game , ActionListener pacman) {
		this.gameTimer = new Timer(500, game);
		this.gameTimer.addActionListener(pacman);
		this.speed = 1;
	}

	
	public void addLisenerArg(ActionListener arg) 
	{
	this.gameTimer.addActionListener(arg);
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

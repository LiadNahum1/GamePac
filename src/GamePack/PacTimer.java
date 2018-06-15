package GamePack;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class PacTimer  {
	private Timer gameTimer; // main timer of the game ;
	private int speed;

	public PacTimer(ActionListener game , ActionListener pacman) {
		this.gameTimer = new Timer(1000, game);
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
		double newDely = (1/(double)speed)*1000;
		this.gameTimer.setDelay((int)newDely);
	}
	public void stop() {
		this.gameTimer.stop();
	}
	

}

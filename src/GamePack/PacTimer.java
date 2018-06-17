package GamePack;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class PacTimer  {
	private Timer gameTimer; // main timer of the game ;
	private int speed;

	public PacTimer(ActionListener game ) {
		this.gameTimer = new Timer(1000, game);
		this.speed = 1;
	}

	//this function will get an Action Listener and sign in to this timer
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
	/*this function will update the game speed
	*by changing the timer delay so that everything will move faster*/
	private void updateGameSpeed() {
		double newDely = (1/(double)speed)*1000;
		this.gameTimer.setDelay((int)newDely);
	}
	//this function will stop the timer 
	public void stop() {
		this.gameTimer.stop();
	}
	

}


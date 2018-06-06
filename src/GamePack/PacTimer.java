package GamePack;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class PacTimer  {
	private Timer gameTimer; // main timer of the game 
	private int numTicksOfGame;
	private int speed;

	public PacTimer(ActionListener game) {
		this.gameTimer = new Timer(1000, game);
		this.numTicksOfGame = 0; 
		this.speed = 1;
	}

	public Timer getGameTimer() {
		return this.gameTimer;
	}
	public int getSpeed() {
		return speed;
	}
	public int getNumOfTicks() {
		return this.numTicksOfGame;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
		updateGameSpeed();
	}
	private void updateGameSpeed() {
		this.gameTimer.setDelay(1/speed*1000);
	}
}

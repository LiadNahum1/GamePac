package GamePack;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class PacTimer implements ActionListener {
	private Timer gameTimer; // main timer of the game 
	private int numTicksOfGame;
	private int speed;

	public PacTimer(ActionListener game) {
		this.gameTimer = new Timer(100, game);
		gameTimer.addActionListener(this);
		this.numTicksOfGame = 1; 
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

	@Override
	public void actionPerformed(ActionEvent e) {
		this.numTicksOfGame++;
	}
}

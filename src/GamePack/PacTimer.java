package GamePack;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class PacTimer implements ActionListener {
	private Timer gameTimer; // main timer of the game 
	private Timer pacmanTimer; // timer that responsible for the movement of pacman
	private Timer fruitTimer;
	private Timer greenGhostsTimer;
	private Timer redGhostsTimer;
	private Timer yellowGhostsTimer;
	private int numTicksOfGame;
	private int numTicksOfFruit;
	private int speed;
	
	public PacTimer(ActionListener game , ActionListener greenGhost , ActionListener redGhost,ActionListener yellowGhost) {
		this.gameTimer = new Timer(1000, game);
		this.gameTimer.addActionListener(this);
		this.pacmanTimer = new Timer(100, game);
		this.fruitTimer = new Timer(500, game);
		this.fruitTimer.addActionListener(this);
		this.setGreenGhostsTimer(new Timer(1000,greenGhost));
		this.setGreenGhostsTimer(new Timer(1000,redGhost));
		this.setGreenGhostsTimer(new Timer(1000,yellowGhost));
		this.numTicksOfGame = 0; 
		this.numTicksOfFruit = 0;
		this.speed = 1;
	}

	public Timer getGameTimer() {
		return this.gameTimer;
	}
	public Timer getPacmanTimer() {
		return this.pacmanTimer;
	}
	public Timer getFruitTimer() {
		return this.fruitTimer;
	}
	
	public int getNumTicksFruit() {
		return this.numTicksOfFruit;
	}
	public int getNumTicksGame() {
		return this.numTicksOfGame;
	}
	public void start() {
		this.gameTimer.start();
		this.pacmanTimer.start();
	}
	public void  startYellowGhost() {
		this.yellowGhostsTimer.start();
	}
	public void  startGreenGhost() {
		this.yellowGhostsTimer.start();
	}
	public void  startRedGhost() {
		this.yellowGhostsTimer.start();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(this.gameTimer)) {
			this.numTicksOfGame = this.numTicksOfGame + 1;
			checkNumTicks();
		}		
		if(e.getSource().equals(this.fruitTimer)) {
			this.numTicksOfFruit = this.numTicksOfFruit + 1;
			checkNumTicks();
		}		
	}
	
	public void checkNumTicks() {
		if(this.numTicksOfGame == 10) {
			this.fruitTimer.start();
		}
		if(this.numTicksOfFruit == 11) {
			this.numTicksOfFruit = 0;
		}
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
		this.pacmanTimer.setDelay(1/speed*50);
		this.fruitTimer.setDelay(1/speed*500);
	}
	public Timer getGreenGhostsTimer() {
		return greenGhostsTimer;
	}

	public void setGreenGhostsTimer(Timer greenGhostsTimer) {
		this.greenGhostsTimer = greenGhostsTimer;
	}

	public Timer getRedGhostsTimer() {
		return redGhostsTimer;
	}

	public void setRedGhostsTimer(Timer redGhostsTimer) {
		this.redGhostsTimer = redGhostsTimer;
	}

	public Timer getYellowGhostsTimer() {
		return yellowGhostsTimer;
	}

	public void setYellowGhostsTimer(Timer yellowGhostsTimer) {
		this.yellowGhostsTimer = yellowGhostsTimer;
	}
}


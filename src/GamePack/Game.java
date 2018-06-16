package GamePack;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Food.Food;
import Pacmen.*;
/* this is the game class which will show all the details of the game*/
public class Game extends JFrame implements ActionListener , KeyListener {
	private int level;
	private Board board; 
	private JLabel[]lifePic;
	private PacTimer timer;
	private Pacman pacman;
	private boolean isStop; 
	private int numTicksOfTimer;
	private int numTicksWithoutStop;
	private int numOfLives;
	private JLabel scors;
	private JLabel apples;
	private JLabel pineApples;
	private JLabel strawBerry;
	private JLabel timesShow;
	private JButton stop;
	private JButton fastForword;
	private JButton exit;
	private JPanel show;
	private JPanel buttons;
	private JPanel data;
	public Game(int level) {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setContentPane(new JLabel(new ImageIcon("pictures\\extra\\pacBackground.jpg")));
		
		Container cp = this.getContentPane();	
		this.setPreferredSize(new Dimension(740,800));
		this.setLayout(new BoxLayout(cp, BoxLayout.PAGE_AXIS));
		this.level = level;
		inisializeArg();
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.board.setLayout(new FlowLayout(FlowLayout.CENTER));
		cp.add(this.board);
		cp.add(this.show);
		addKeyListener(this);
		pack();
		this.setVisible(true);
		this.setResizable(false);
	}
//Initialize the fields of the game
	private void inisializeArg() {
		this.numOfLives = 3;
		this.timer = new PacTimer(this ,this.pacman);
		this.board = new Board(level,timer);
		this.pacman = board.getPacman();
		this.numTicksOfTimer = 1;
		this.numTicksWithoutStop = 1;
		this.isStop = true;
		this.show = new JPanel();
		show.setLayout(new GridLayout(2,1));
		show.setOpaque(false);
		inisializeBattons();
		inisializeData();

	}
	//this will start the game only after pressing space
	@Override
	public void keyPressed(KeyEvent e) {
		this.requestFocus();
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			stopGame();
			this.board.start();
		}
	}


	public static void main(String[]args) {
		new Game(2);
	}

	private void inisializeBattons() {
		buttons = new JPanel();
		buttons.setOpaque(false);
		buttons.setLayout(new FlowLayout());
		this.exit = new JButton("Exit Game");
		exit.setFont(new Font("Serif", Font.BOLD, 30));
		exit.addActionListener(this);
		buttons.add(exit);
		this.stop = new JButton("pause");
		stop.setFont(new Font("Serif", Font.BOLD, 30));
		stop.addActionListener(this);
		buttons.add(stop);
		this.fastForword = new JButton("speed X1");
		fastForword.setFont(new Font("Serif", Font.BOLD, 30));
		fastForword.addActionListener(this);
		buttons.add(fastForword);
		show.add(buttons);
	}


	private void inisializeData() {
		data = new JPanel();
		data.setBackground(Color.CYAN);
		data.setLayout(new GridLayout(2,1));
		JPanel pacData = new JPanel(); 
		pacData.setLayout(new GridLayout(1,6));
		JPanel gameData = new JPanel();
		gameData.setLayout(new GridLayout(1,4));
		JLabel t = new JLabel("time:");
		JLabel s = new JLabel("score:");
		JLabel l = new JLabel("life:");
		t.setFont(new Font("Serif", Font.BOLD, 20));
		s.setFont(new Font("Serif", Font.BOLD, 20));
		l.setFont(new Font("Serif", Font.BOLD, 20));
		//this will count the number of fruits
		apples = new JLabel("Apples: 0");
		pineApples = new JLabel("Pineapples: 0");
		strawBerry = new JLabel("StrawBerry: 0");
		timesShow = new JLabel("0");
		timesShow.setFont(new Font("Serif", Font.BOLD, 20));
		scors = new JLabel("0");
		scors.setFont(new Font("Serif", Font.BOLD, 20));
		gameData.add(t);
		gameData.add(timesShow);
		gameData.add(s);
		gameData.add(scors);
		lifePic = new JLabel[numOfLives];
		pacData.add(l);
		for(int i=0 ;i<this.numOfLives; i = i +1) {
			lifePic[i] = new JLabel (new ImageIcon("pictures\\boards\\lives.png"));
			pacData.add(lifePic[i]);
		}
		pacData.add(apples);
		pacData.add(pineApples);
		pacData.add(strawBerry);
		//pacData.add(f);
		 
		data.add(gameData);
		data.add(pacData);
		show.add(data);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource().equals(timer.getGameTimer())) {
			this.pacman.getScore();
			scors.setText(String.valueOf(this.pacman.getScore())); //update the pacman score display
			timesShow.setText(String.valueOf(numTicksWithoutStop)); // update the tie display
			checkforFruits(); 
			if(numTicksOfTimer % this.timer.getSpeed() == 0) //a real second has passed if 1000 milliseconds past
				numTicksWithoutStop++;
			numTicksOfTimer++;
			if(board.getNumLives()!= this.numOfLives){
				decresLives();
			}
		}
		if(arg0.getSource().equals(stop)){
			stopGame();
		}

		if(arg0.getSource().equals(exit)){
			this.setVisible(false);
			new MainMenu();
		}
		if(arg0.getSource().equals(fastForword)){
			if(this.timer.getSpeed() < 16 )
				this.timer.setSpeed(this.timer.getSpeed()*2);
			else {
				this.timer.setSpeed(1);
			}
			this.fastForword.setText("speed X" + this.timer.getSpeed());
		}
		
		if(this.board.getNumLives()==0) {
			endGame();
		}
		if(this.board.checkIfWinLevel()) {
			stopGame();
			if(this.level == 3) {
				endGame();
			}
			else {
				this.level = this.level + 1;
				this.board= new Board(this.level, this.timer);
				this.getContentPane().remove(board);
				this.getContentPane().removeAll();
				this.getContentPane().add(this.board);
				this.getContentPane().add(this.show);
			}
		}
	}
	
	private void checkforFruits() { //update the number of the pacman eat
		apples.setText("apples:" + this.pacman.getAppleNum());
		pineApples.setText("pineApples:" +this.pacman.getPineAppleNum());
		strawBerry.setText("strawBerry:" + this.pacman.getStrawBerryNum());
		}

	private void endGame() { // this will update the date
		new EndGame(this.scors, this.level, this.timesShow );
		this.setVisible(false);

	}


	private void decresLives() { //this will update the display of the life 
		this.numOfLives--;
		if(numOfLives > 0)
			this.lifePic[numOfLives].setIcon(new ImageIcon("pictures\\boards\\��dead.png"));
	}



	private void stopGame() { //this will stop the timer and update the button display
		if(!isStop) {
			this.timer.getGameTimer().stop();
			stop.setText("continue");
		}
		else {
			this.timer.getGameTimer().start();
			stop.setText("pause");
		}
		isStop =!isStop;
	}
	



	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}



	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
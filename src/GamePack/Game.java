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

public class Game extends JFrame implements ActionListener , KeyListener {
	private int level;

	private Board board; 
	private JLabel[]lifePic;
	private PacTimer timer;
	private Pacman pacman;
	private boolean start; 
	private boolean isStop;
	private Vector<Food> fruits; 
	private int numTicksWithoutStop;
	private int numOfLives = 3;
	private JLabel scors;
	private JLabel StartGsme;
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
		this.setPreferredSize(new Dimension(1000,1000));
		this.setLayout(new BoxLayout(cp, BoxLayout.PAGE_AXIS));
		this.level = level;
		inisializeArg();
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.board.setLayout(new FlowLayout(FlowLayout.CENTER));
		cp.add(this.board);
		cp.add(this.show);
		addKeyListener(this);
	//	this.board.setFocusable(true);
		pack();
		 this.setVisible(true);
		this.setResizable(false);
	}

	
	
	private void inisializeArg() {
		this.timer = new PacTimer(this ,this.pacman);
		this.board = new Board(level,timer);
		this.pacman = board.getPacman();
		this.numTicksWithoutStop = 1;
		this.isStop = false;
		this.show = new JPanel();
		show.setLayout(new GridLayout(2,1));
		show.setOpaque(false);
		inisializeBattons();
		inisializeData();

	}
	@Override
	public void keyPressed(KeyEvent e) {
		start = true;
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
		this.timer.getGameTimer().start();
		stopGame();
	}
	}
	

	public static void main(String[]args) {
		new Game(1);
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
		this.fastForword = new JButton("next speed");
		fastForword.setFont(new Font("Serif", Font.BOLD, 30));
		fastForword.addActionListener(this);
		buttons.add(fastForword);
		show.add(buttons);
		
		
		
	}


	private void inisializeData() {
		data = new JPanel();
		data.setBackground(Color.CYAN);
		data.setLayout(new GridLayout(2,4));
		JLabel t = new JLabel("time:");
		JLabel s = new JLabel("score:");
		t.setFont(new Font("Serif", Font.BOLD, 50));
		s.setFont(new Font("Serif", Font.BOLD, 50));
		timesShow = new JLabel("0");
		timesShow.setFont(new Font("Serif", Font.BOLD, 50));
		scors = new JLabel("0");
		scors.setFont(new Font("Serif", Font.BOLD, 50));
		data.add(t);
		data.add(timesShow);
		data.add(s);
		data.add(scors);
		lifePic = new JLabel[numOfLives];
		for(int i=0 ;i<this.numOfLives; i = i +1) {
			lifePic[i] = new JLabel (new ImageIcon("pictures\\boards\\lives.png"));
			data.add(lifePic[i]);
		}
		show.add(data);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource().equals(timer.getGameTimer())) {
			this.pacman.getScore();
			scors.setText(String.valueOf(this.pacman.getScore()));
			timesShow.setText(String.valueOf(numTicksWithoutStop));
			numTicksWithoutStop++;
			}
			if(arg0.getSource().equals(stop)){
			stopGame();
			}

			if(arg0.getSource().equals(exit)){
				this.setVisible(false);
				new MainMenu();
			}
	}
	private void stopGame() {
		if(isStop) {
			this.timer.getGameTimer().stop();
			stop.setText("continue");
			}
			else {
				this.timer.getGameTimer().start();
				stop.setText("pause");
			}
			isStop =!isStop;
	}



	public void nextLevel() {
		if(level == 3) {
			//endGame();
		}
		else {
			this.board.setVisible(false);
			this.board = new Board(this.level + 1, this.timer);
			this.getContentPane().add(this.board);
			this.board.setFocusable(true);
			this.board.requestFocus();
		}
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
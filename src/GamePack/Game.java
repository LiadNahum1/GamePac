package GamePack;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Food.Food;
import Pacmen.*;

public class Game extends JFrame implements ActionListener  {
	private int level;

	private Board board; 
	private JPanel bottomPanel;
	private JLabel[]lifePic;
	private JLabel scors;
	private PacTimer timer;
	private Pacman pacman;
	private boolean start; 
	private Vector<Food> fruits; 
	private int numTicksWithoutStop;
	private int numOfLives = 3;
	public Game(int level) {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container cp = this.getContentPane();
		this.setBackground(Color.BLACK);
		this.setPreferredSize(new Dimension(1000,1000));
		this.level = level;
		this.setLayout(new BoxLayout(cp, BoxLayout.PAGE_AXIS));
		this.timer = new PacTimer(this, this.pacman);
		this.board = new Board(level,timer);
		this.pacman = board.getPacman();
		this.numTicksWithoutStop = 1;
		initializeBottomPanel();
		this.board.setLayout(new FlowLayout(FlowLayout.CENTER));
		cp.add(this.board);
		cp.add(this.bottomPanel);
		this.board.setFocusable(true);
		pack();
		this.setVisible(true);
		this.setResizable(false);
	}

	private void initializeBottomPanel() {
		this.bottomPanel = new JPanel(new GridLayout(1,3));
		this.bottomPanel.setBackground(Color.BLACK);
		scors = new JLabel("0");
		this.bottomPanel.add(scors);
		lifePic = new JLabel[numOfLives];
		for(int i=0 ;i<this.numOfLives; i = i +1) {
			lifePic[i] = new JLabel (new ImageIcon("pictures\\boards\\lives.png"));
			bottomPanel.add(lifePic[i]);
		}
	}
	
	public static void main(String[]args) {
		new Game(1);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.numTicksWithoutStop++;
		this.pacman.getScore();
		scors.setText(String.valueOf(this.pacman.getScore()));
		if(this.board.getNumLives()==0) {
			endGame();
		}
		if(this.board.checkIfWinLevel()) {
			this.timer.stop();
			nextLevel();
		}

	}
	public void endGame() {
		//need JLabel scor
		//JLabel time
		//JLabel level

		//new EndGame(this);
		this.setVisible(false);
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
}
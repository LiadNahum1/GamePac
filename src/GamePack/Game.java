package GamePack;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;




public class Game extends JFrame  {
	private int level;
	private JPanel topPanel;
	private JPanel board; 
	private JPanel bottomPanel;
	
	public Game(int level) {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container cp = this.getContentPane();
		this.setBackground(Color.BLACK);
		this.setSize(800, 800);
		this.level = level;
		this.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));
		this.topPanel = new JPanel(new FlowLayout());
		//this.board = new Board(level);
		this.bottomPanel = new JPanel(new FlowLayout());
		initializeTopPanel();
		initializeBottomPanel();
	    cp.add(this.topPanel);
		cp.add(this.board);
		cp.add(this.bottomPanel);
		this.board.setFocusable(true);
		this.setVisible(true);
		this.setResizable(false);

	}
	private void initializeBottomPanel() {
		this.bottomPanel.setBackground(Color.BLACK);
		this.bottomPanel.setPreferredSize(new Dimension(800, 40));
	}
	private void initializeTopPanel() {
		this.topPanel.setBackground(Color.BLACK);
		this.topPanel.setPreferredSize(new Dimension(800, 40));
	}
	
}
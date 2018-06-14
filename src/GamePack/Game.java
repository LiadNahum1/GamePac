package GamePack;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;




public class Game extends JFrame implements ActionListener  {
	private int level;
	private JPanel topPanel;
	private JPanel board; 
	private JPanel bottomPanel;
	
	public Game(int level) {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container cp = this.getContentPane();
		this.setBackground(Color.BLACK);
		
		this.setPreferredSize(new Dimension(800,800));
		this.level = level;
		this.topPanel = new JPanel(new FlowLayout());
		this.board = new Board(level);
		
		this.bottomPanel = new JPanel(new FlowLayout());
		//initializeTopPanel();
		//initializeBottomPanel();
		this.board.setLayout(new FlowLayout(FlowLayout.CENTER));
		cp.add(this.board);
	    //cp.add(this.topPanel);
		
		//cp.add(this.bottomPanel);
		this.board.setFocusable(true);
		pack();
		this.setVisible(true); 
		this.setResizable(false);

	}
	private void initializeBottomPanel() {
		this.bottomPanel.setBackground(Color.BLACK);
		
	}
	private void initializeTopPanel() {

	}
	public static void main(String[]args) {
		new Game(1);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
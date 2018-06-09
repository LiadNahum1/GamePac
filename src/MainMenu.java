import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import GamePack.Board;


public class MainMenu extends JFrame implements ActionListener {
	private JButton start;
	private JButton exit; 
	private JButton top5; 
	private final int size = 500;

	public MainMenu() {
		/*constructor*/ 
		super("MainWindow");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setContentPane(new JLabel(handlePicSize(new ImageIcon("pictures/extra/pacWwlcome.png"), size)));
		Container cp = getContentPane();       

		this.start = new JButton("Start Game");
		this.start.setBackground(Color.RED);
		this.start.setBounds(180, 250, 150, 35);
		start.setFont(new Font("Serif", Font.BOLD, 20));
		this.start.addActionListener(this);	
		cp.add(this.start);

		this.exit = new JButton("Exit");
		this.exit.setBackground(Color.RED);
		this.exit.setBounds(180, 300, 150, 35);
		exit.setFont(new Font("Serif", Font.BOLD, 20));
		exit.addActionListener(this);
		cp.add(this.exit);
		
		this.top5 = new JButton("Top 5 Scores");
		this.top5.setBackground(Color.RED);
		this.top5.setBounds(180, 350, 150, 35);
		top5.setFont(new Font("Serif", Font.BOLD, 20));
		this.top5.addActionListener(this);	
		cp.add(this.top5);

		this.setSize(size, size);
		this.setResizable(false);
		this.setVisible(true);
	}

	/*The function gets an image and integer size and resizes the image */
	public ImageIcon handlePicSize(ImageIcon image, int size) {
		Image imageconvert = image.getImage();  
		Image newimage = imageconvert.getScaledInstance(500,500,java.awt.Image.SCALE_SMOOTH);   
		return new ImageIcon(newimage);
	}
	@Override
	/*If the client presses on the exit button, the function raises a dialog whether he is sure or not.
	 * If he is, the program closes itself. 
	 * If the client presses on the start button, the function opens the game Window*/
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(this.exit)) {
			int result = JOptionPane.showConfirmDialog(new JFrame(), "Are you sure? ", "Dialog",JOptionPane.YES_NO_OPTION);
			if(result == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}
		if(e.getSource().equals(this.start)) {
			new Board(3);
			this.setVisible(false);
		}
	}

	/*main function*/
	public static void main(String[] args) {
		new MainMenu();

	}

}

package GamePack;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import Tables.Top5;

//this class will be the first menu when the player open the game
@SuppressWarnings("serial")
public class MainMenu extends JFrame implements ActionListener {
	private JButton level1;
	private JButton level2;
	private JButton level3;
	private JButton exit; 
	private JButton top5; 
	private final int size = 500;

	public MainMenu() {
		/*constructor*/ 
		super("MainWindow");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setContentPane(new JLabel(handlePicSize(new ImageIcon("pictures/extra/pacWwlcome.png"), size)));
		Container cp = getContentPane();       
		this.level1 = new JButton("level 1");
		this.level1.setBackground(Color.RED);
		this.level1.setBounds(100, 250, 100, 35);
		this.level2 = new JButton("level 2");
		this.level2.setBackground(Color.RED);
		this.level2.setBounds(205, 250, 100, 35);
		this.level3 = new JButton("level 3");
		this.level3.setBackground(Color.RED);
		this.level3.setBounds(310, 250, 100, 35);
		
		level1.setFont(new Font("Serif", Font.BOLD, 20));
		this.level1.addActionListener(this);	
		cp.add(this.level1);
		level2.setFont(new Font("Serif", Font.BOLD, 20));
		this.level2.addActionListener(this);	
		cp.add(this.level2);
		level3.setFont(new Font("Serif", Font.BOLD, 20));
		this.level3.addActionListener(this);	
		cp.add(this.level3);

		this.exit = new JButton("Exit");
		this.exit.setBackground(Color.RED);
		this.exit.setBounds(180, 350, 150, 35);
		exit.setFont(new Font("Serif", Font.BOLD, 20));
		exit.addActionListener(this);
		cp.add(this.exit);
		
		this.top5 = new JButton("Top 5 Scores");
		this.top5.setBackground(Color.RED);
		this.top5.setBounds(180, 300, 150, 35);
		top5.setFont(new Font("Serif", Font.BOLD, 20));
		this.top5.addActionListener(this);	
		cp.add(this.top5);

		this.setSize(size, size);
		this.setResizable(false);
		this.setVisible(true);
	}

	/*The function gets an image and integer size and resizes the image */
	private ImageIcon handlePicSize(ImageIcon image, int size) {
		Image imageconvert = image.getImage();  
		Image newimage = imageconvert.getScaledInstance(size,size,java.awt.Image.SCALE_SMOOTH);   
		return new ImageIcon(newimage);
	}
	@Override
	/*If the client presses on the exit button, the function raises a dialog whether he is sure or not.
	 * If he is, the program closes itself. 
	 * If the client presses on the start button, the function opens the game Window in level 1
	 * If the client presses on the top5 button the Top5 window is open*/
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(this.exit)) {
			int result = JOptionPane.showConfirmDialog(new JFrame(), "Are you sure? ", "Dialog",JOptionPane.YES_NO_OPTION);
			if(result == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}
		if(e.getSource().equals(this.level1)) {
			this.setVisible(false);
			new Game(1);
		}
		if(e.getSource().equals(this.level2)) {
			this.setVisible(false);
			new Game(2);
		}
		if(e.getSource().equals(this.level3)) {
			this.setVisible(false);
			new Game(3);
		}
		if(e.getSource().equals(this.top5)) {
			this.setVisible(false);
			new Top5(this);
		}
		
	}

	/*main function*/
	public static void main(String[] args) {
		new MainMenu();

	}

}
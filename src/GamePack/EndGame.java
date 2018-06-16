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

@SuppressWarnings("serial")
/*The class defines the End Game Window. 
 * Show the client his accomplishment: the amount of time and steps that took him to solve the puzzle.
 * And suggests the client two options: play again or exit.
 */
public class EndGame extends JFrame implements ActionListener {
	private JLabel scoreL; 
	private JLabel timeL;
	private JLabel levelL; 	
	private JButton playAgain; 
	private JButton records; 
	private JButton exit; 
	private int score;
	private int time; 
	private int pineApple;
	private int apple;
	private int strawBerry; 
	private final int size = 600; 
	
	/*constructor*/
	public EndGame(int score, int level, int time, int pineApple, int apple, int strawBerry) {
		super("The End");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBackground(Color.black);		
		
		this.setContentPane(new JLabel(new ImageIcon("pictures//extra//background.png")));
		Container cp = this.getContentPane();
		

		JLabel title = new JLabel("Game Over");
		title.setForeground(Color.BLUE);
		title.setFont(new Font("Serif", Font.BOLD, 100));
		title.setBounds(50, 0,600, 100);
		
		this.scoreL = new JLabel("Score: " + score);
		this.levelL = new JLabel("Level: " + level);
		this.timeL = new JLabel("Time: " + time);
		this.scoreL.setForeground(Color.LIGHT_GRAY);
		this.timeL.setForeground(Color.LIGHT_GRAY);
		this.levelL.setForeground(Color.LIGHT_GRAY);
		this.scoreL.setFont(new Font("Serif", Font.BOLD, 25));
		this.levelL.setFont(new Font("Serif", Font.BOLD, 25));
		this.timeL.setFont(new Font("Serif", Font.BOLD, 25));
		this.levelL.setBounds(200, 100, this.levelL.toString().length(), 40);
		this.scoreL.setBounds(200, 150, this.scoreL.toString().length(), 40);
		this.timeL.setBounds(200, 200, this.timeL.toString().length(), 40);
		
		
		this.playAgain = new JButton("Play Again");
		this.playAgain.setFont(new Font("Serif", Font.BOLD, 30));
		this.playAgain.setBackground(Color.LIGHT_GRAY);
		this.playAgain.setBounds(200, 350, 200,40);
		this.playAgain.addActionListener(this);
		
		this.records = new JButton("Records");
		this.records.setFont(new Font("Serif", Font.BOLD, 30));
		this.records.setBackground(Color.LIGHT_GRAY);
		this.records.setBounds(200, 400, 200,40);
		this.records.addActionListener(this);
		
		this.exit = new JButton("Exit");
		this.exit.setFont(new Font("Serif", Font.BOLD, 30));
		this.exit.setBackground(Color.LIGHT_GRAY);
		this.exit.setBounds(200, 450, 200,40);
		this.exit.addActionListener(this);
		

		cp.add(title);
		cp.add(levelL);
		cp.add(timeL);
		cp.add(scoreL);
		cp.add(playAgain);
		cp.add(records);
		cp.add(exit);

		this.score = score;
		this.time= time;
		this.pineApple = pineApple;
		this.apple = apple;
		this.strawBerry = strawBerry; 
		this.setSize(size, size);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	/*The function gets an image and integer size and resizes the image */
	public ImageIcon handlePicSize(ImageIcon image, int size) {
		Image imageconvert = image.getImage();  
		Image newimage = imageconvert.getScaledInstance(size,size,  java.awt.Image.SCALE_SMOOTH);   
		return new ImageIcon(newimage);
	}

	public int getScore() {
		return this.score;
	}
	public int getTime() {
		return this.time;
	}
	public int getPineApples() {
		return this.pineApple;
	}
	public int getApples() {
		return this.apple;
	}
	public int getStrawBerry() {
		return this.strawBerry;
	}
	@Override
	/*If the client presses on the exit button, the function raises a dialog whether he is sure or not.
     * If he is, the program closes itself. 
     * If the client presses on the play again button, the function opens the MainWindow again*/
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(this.playAgain)) {
			new MainMenu();
			this.setVisible(false);
		}
		if(e.getSource().equals(this.records)) {
			new RecordsTable(this);
			this.setVisible(false);
		}
		
		if(e.getSource().equals(this.exit)) {
			int result = JOptionPane.showConfirmDialog(new JFrame(), "Are you sure? ", "Dialog",JOptionPane.YES_NO_OPTION);
    		if(result == JOptionPane.YES_OPTION) {
    			System.exit(0);
    		}
		}
		
	}
}

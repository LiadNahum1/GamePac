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
	private Board board;
	private JLabel scoreL; 	
	private JLabel timeL;
	private JLabel levelL; 	
	private JButton playAgain; 
	private JButton records; 
	private JButton exit; 
	private final int size = 600; 
	
	/*constructor*/
	public EndGame(JLabel score, JLabel level, JLabel time, Board board) {
		super("The End");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBackground(Color.black);		
		Container cp = this.getContentPane();
		
		this.board = board; 
		
		JLabel title = new JLabel("Game Over");
		this.setForeground(Color.BLUE);
		title.setFont(new Font("Serif", Font.BOLD, 50));
		title.setBounds(80, 0,600, 80);
		
		this.scoreL = scoreL;
		this.levelL = levelL;
		this.timeL = timeL;
		this.scoreL.setFont(new Font("Serif", Font.BOLD, 25));
		this.levelL.setFont(new Font("Serif", Font.BOLD, 25));
		this.timeL.setFont(new Font("Serif", Font.BOLD, 25));
		this.levelL.setBounds(300, 150, this.levelL.toString().length(), 40);
		this.scoreL.setBounds(300, 200, this.scoreL.toString().length(), 40);
		this.timeL.setBounds(300, 250, this.timeL.toString().length(), 40);
		
		
		this.playAgain = new JButton("Play Again");
		this.playAgain.setFont(new Font("Serif", Font.BOLD, 30));
		this.playAgain.setBackground(Color.LIGHT_GRAY);
		this.playAgain.setBounds(300, 400, 200,40);
		this.playAgain.addActionListener(this);
		
		this.records = new JButton("Records");
		this.records.setFont(new Font("Serif", Font.BOLD, 30));
		this.records.setBackground(Color.LIGHT_GRAY);
		this.records.setBounds(300, 450, 200,40);
		this.records.addActionListener(this);
		
		this.exit = new JButton("Exit");
		this.exit.setFont(new Font("Serif", Font.BOLD, 30));
		this.exit.setBackground(Color.LIGHT_GRAY);
		this.exit.setBounds(300, 500, 200,40);
		this.exit.addActionListener(this);
		
		JLabel image = new JLabel (new ImageIcon("pictures//extra//background.png"));
		image.setBounds(0, 550, 600, 100);
		
		cp.add(title);
		cp.add(levelL);
		cp.add(timeL);
		cp.add(scoreL);
		cp.add(playAgain);
		cp.add(records);
		cp.add(exit);
		cp.add(image);
		
		pack();
		this.setSize(size, size);
		this.setResizable(false);
		this.setVisible(true);
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
			new RecordsTable(this.board, this);
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

package Tables;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import GamePack.EndGame;
import GamePack.MainMenu;

/*A frame of the records*/
@SuppressWarnings("serial")
public class RecordsTable extends JFrame implements ActionListener {
	private JPanel topPanel; 
	private JPanel tabelPanel;
	private JPanel restPanel;
	private Table records;
	private JLabel lblHeading; 
	private JButton enrollB;
	private JTextField nameT;
	private JTextField lastT;
	private JButton returnToMain; 
	private EndGame end; 

	public RecordsTable (EndGame end) {
		super("Records Table");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.end = end; 

		this.topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0, 0));	
		this.returnToMain = new JButton();
		this.returnToMain.setIcon(new ImageIcon("pictures\\extra\\back.png"));
		this.returnToMain.setContentAreaFilled(false);
		this.returnToMain.setBorderPainted(false);
		this.returnToMain.addActionListener(this);
		
		this.tabelPanel = new JPanel();
		this.tabelPanel.setLayout(new BorderLayout());
		this.records = new Table(); 
		this.records.fillTable();
		JScrollPane scrollPane = new JScrollPane(this.records);
		this.lblHeading = new JLabel("RECORDS");
		this.lblHeading.setFont(new Font(Font.DIALOG_INPUT,  Font.BOLD, 40));
		this.tabelPanel.add(scrollPane, BorderLayout.CENTER);

		topPanel.add(returnToMain);
		topPanel.add(lblHeading);
		this.restPanel = new JPanel();
		enrollTabel();

		Container cp = this.getContentPane();
		cp.setLayout(new BoxLayout(cp,BoxLayout.Y_AXIS));
		cp.add(topPanel);
		cp.add(tabelPanel);
		cp.add(restPanel);	

	
		this.setSize(800, 700);
		this.setVisible(true);
		this.setResizable(false);

	}

	/*Open text boxes for enroll the records table*/
	public void enrollTabel() {
		this.nameT = new JTextField(20);
		this.lastT = new JTextField(20);
		this.enrollB = new JButton("Add");
		this.enrollB.addActionListener(this);
		JLabel nameL = new JLabel("Insert First Name: ");
		JLabel lastL = new JLabel("Insert Last Name: ");
		this.restPanel.add(nameL);
		this.restPanel.add(nameT);
		this.restPanel.add(lastL);
		this.restPanel.add(lastT);
		this.restPanel.add(enrollB);
	}
		
	@Override
	/*If player press on enroll button, write his details into the records table*/
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(this.enrollB)) {
			if(this.nameT.getText().equals("") | this.lastT.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "Please insert data","", JOptionPane.OK_OPTION);
			}

			else {
				String content = this.nameT.getText() +","+ this.lastT.getText() + "," +  this.end.getTime()+ 
						","+ this.end.getScore() + ',' + this.end.getPineApples() + "," + this.end.getApples() 
						+"," + this.end.getStrawBerry();
				this.records.writeIntoFile(content);
				this.records.addToTable(content);
				this.records.sortTable();
				this.restPanel.setVisible(false);
			}
		}
		
		if(e.getSource().equals(this.returnToMain)) {
			new MainMenu();
			this.setVisible(false);
		}

	}
}
	
	


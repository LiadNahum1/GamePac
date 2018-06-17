package Tables;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import GamePack.MainMenu;

/*This JFrame shows the details of the five players that have the best scores*/ 
public class Top5 extends JFrame implements ActionListener{
	private Table top;
	private JButton returnToMain; 
	private MainMenu main; 
	public Top5(MainMenu main) {
		/*constructor*/ 
		super("Top 5");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		Container cp = getContentPane();
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0, 0));
		JPanel tablePanel = new JPanel(new BorderLayout());
		//create top 5 table
		this.top = new Table();
		takeTop();
		JScrollPane scrollPane = new JScrollPane(this.top);
		tablePanel.add(scrollPane);
		
		JLabel lblHeading = new JLabel("TOP 5");
		lblHeading.setFont(new Font(Font.DIALOG_INPUT,  Font.BOLD, 40));		
		this.returnToMain = new JButton();
		this.returnToMain.setIcon(new ImageIcon("pictures\\extra\\back.png"));
		this.returnToMain.setContentAreaFilled(false);
		this.returnToMain.setBorderPainted(false);
		this.returnToMain.addActionListener(this);
		topPanel.add(returnToMain);
		topPanel.add(lblHeading);
		
		cp.add(topPanel, BorderLayout.PAGE_START);
		cp.add(tablePanel, BorderLayout.CENTER);
		this.main = main; 
		pack();
		this.setResizable(false);
		this.setVisible(true);
	}
	
	/*Build a table that includes all the player that have been signed and take only the best five of them*/ 
	public void takeTop() {
		Table allTable = new Table();
		allTable.fillTable();
		DefaultTableModel modelAll = (DefaultTableModel) allTable.getModel();
		DefaultTableModel model = (DefaultTableModel) this.top.getModel();
		for(int i =0; i<allTable.getRowCount() && i< 5; i = i+1) {
			model.addRow(new Object[]{modelAll.getValueAt(i, 0),modelAll.getValueAt(i, 1),modelAll.getValueAt(i, 2),
					Integer.parseInt(modelAll.getValueAt(i, 3).toString()), modelAll.getValueAt(i, 4),modelAll.getValueAt(i, 5),modelAll.getValueAt(i, 6)});
		}
		this.top.sortTable();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(this.returnToMain)) {
			this.setVisible(false);
			this.main.setVisible(true);
		}
		
	}

}



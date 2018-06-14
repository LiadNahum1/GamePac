package GamePack;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


public class End extends JFrame implements ActionListener {
	private JPanel tabelPanel;
	private JPanel restPanel;
	private JTable records;
	private JLabel lblHeading; 
	private JScrollPane scrollPane;
	private JButton enrollB;
	private JTextField nameT;
	private JTextField lastT;
	private Board board;

	public End(Board board) {
		super("The end");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.board = board; 
		this.tabelPanel = new JPanel();
		this.tabelPanel.setLayout(new BorderLayout());
		creatingTable();
		this.tabelPanel.add(lblHeading, BorderLayout.PAGE_START);
		this.tabelPanel.add(scrollPane, BorderLayout.CENTER);

		this.restPanel = new JPanel();

		Container cp = this.getContentPane();
		cp.setLayout(new BoxLayout(cp,BoxLayout.Y_AXIS));
		cp.add(tabelPanel);
		cp.add(restPanel);	

		this.nameT = new JTextField(20);
		this.lastT = new JTextField(20);
		this.enrollB = new JButton("Add");
		this.enrollB.addActionListener(this);
		this.setSize(800, 700);
		this.setVisible(true);
		this.setResizable(false);

		int result =JOptionPane.showConfirmDialog(this,"Do you want to be written in the records table?","",JOptionPane.YES_NO_OPTION );
		if(result == JOptionPane.YES_OPTION) {
			enrollTabel();
			this.setVisible(true);
		}
	}

	private void enrollTabel() {
		JLabel nameL = new JLabel("Insert First Name: ");
		JLabel lastL = new JLabel("Insert Last Name: ");
		this.restPanel.add(nameL);
		this.restPanel.add(nameT);
		this.restPanel.add(lastL);
		this.restPanel.add(lastT);
		this.restPanel.add(enrollB);
	}

	private void creatingTable() {
		DefaultTableModel model = new DefaultTableModel(); 
		this.records = new JTable(model); 
		//add columns
		model.addColumn("First Name");
		model.addColumn("Last Name");
		model.addColumn("Time");
		model.addColumn("Score");
		this.scrollPane = new JScrollPane(this.records);
		this.records.setFillsViewportHeight(true);
		this.records.setEnabled(false);
		this.lblHeading = new JLabel("RECORDS");
		this.lblHeading.setFont(new Font(Font.DIALOG_INPUT,  Font.BOLD, 40));
		fillTabel();

	}
	private void fillTabel() {
		try  {
			String path=System.getProperty("user.dir")+ "/records.txt";
			File file = new File(path);
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line; 
			while((line = br.readLine()) != null){
				addToTabel(line);
			}
			br.close();
			sortTabel();
		}
		catch(Exception e){
			System.out.println(e);
		}
		
	}
	private void addToTabel(String content) {
		DefaultTableModel model = (DefaultTableModel) this.records.getModel();
		String []data = content.split(",");
		model.addRow(new Object[] {data[0], data[1], data[2], data[3]});
	}
	private void sortTabel() {
		
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(this.records.getModel());
		this.records.setRowSorter(sorter);
		List<RowSorter.SortKey> sortKeys = new ArrayList<>(2);
		sortKeys.add(new RowSorter.SortKey(3, SortOrder.DESCENDING));
		sorter.setSortKeys(sortKeys);
	}

	private void writeIntoFile(String content) {
		try  {
			String path=System.getProperty("user.dir")+ "/records.txt";
			File file = new File(path);
			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true); //append to file
			BufferedWriter bw = new BufferedWriter(fw);
			// Write in file
			bw.write(content+ "\n");
			// Close connection
			bw.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(this.enrollB)) {
			if(this.nameT.getText().equals("") | this.lastT.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "Please insert data","", JOptionPane.OK_OPTION);
			}

			else {
				String content = this.nameT.getText() +","+ this.lastT.getText() + "," + board.getNumTicksWithoutStop() + 
						","+ board.getScoreOfPlayer();
				writeIntoFile(content);
				addToTabel(content);
				sortTabel();
				this.restPanel.setVisible(false);
			}
		}

	}
	

	
}




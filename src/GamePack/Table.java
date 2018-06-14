package GamePack;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class Table extends JTable {
	DefaultTableModel model;
	public Table() {
		this.model = new DefaultTableModel(); 
		this.setModel(this.model);
		creatingTable();
	}

	public void creatingTable() {
		//add columns
		this.model.addColumn("First Name");
		this.model.addColumn("Last Name");
		this.model.addColumn("Time");
		this.model.addColumn("Score");
		this.setFillsViewportHeight(true);
		this.setEnabled(false);
	}

	public void fillTable() {
		try  {
			
			String path=System.getProperty("user.dir")+ "/records.txt";
			File file = new File(path);
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line; 
			while((line = br.readLine()) != null){
				addToTable(line);
			}
			br.close();
			sortTable();
		}
		catch(Exception e){
			System.out.println(e);
		}
		
	}
	public void addToTable(String content) {
		DefaultTableModel model = (DefaultTableModel) this.getModel();
		String []data = content.split(",");
		model.addRow(new Object[] {data[0], data[1], data[2], data[3]});
	}
	public void sortTable() {
		
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(this.getModel());
		this.setRowSorter(sorter);
		List<RowSorter.SortKey> sortKeys = new ArrayList<>(2);
		sortKeys.add(new RowSorter.SortKey(3, SortOrder.DESCENDING));
		sorter.setSortKeys(sortKeys);
	}

	public void writeIntoFile(String content) {
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
}

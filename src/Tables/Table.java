package Tables;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

@SuppressWarnings("serial")
public class Table extends JTable {
	DefaultTableModel model;
	@SuppressWarnings("serial")
	public Table() {
		 this.model = new DefaultTableModel() {
	            @Override
	            public Class<?> getColumnClass(int column) {
	                switch (column) {
	                    case 3:
	                    	return Integer.class;
	        
	                    default:
	                        return String.class;
	                }
	            }
	        };
		this.setModel(this.model);
		creatingTable();
	}

	/*adding columns to table*/
	public void creatingTable() {
		//add columns
		this.model.addColumn("First Name");
		this.model.addColumn("Last Name");
		this.model.addColumn("Time");
		this.model.addColumn("Score");
		this.model.addColumn("PineApple");
		this.model.addColumn("Apple");
		this.model.addColumn("StrawBerry");

		this.setFillsViewportHeight(true);
		this.setEnabled(false);
	}

	/*fill table with data from records.txt file*/
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
	/*add row to table which inclueds content data*/
	public void addToTable(String content) {
		DefaultTableModel model = (DefaultTableModel) this.getModel();
		String []data = content.split(",");
		model.addRow(new Object[] {data[0], data[1], data[2], new Integer(data[3]), data[4],data[5], data[6]});

	}
	/*sorting table by score*/
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class DataSet {
	
	File dataFile;
	
	public Header header;
	public ArrayList<Row> rows;
	boolean isValid = true;

	
	public DataSet(File dataFile) {
		this.dataFile = dataFile;
		loadData();
	}
	

	/**
	 * Given an Quandl code, this finds the appropriately named file
	 * in the data/stocks directory and loads it into an ArrayList of
	 * row objects.  This list is sorted from least recent to
	 * most recent.
	 * @param code
	 * @return
	 */
	public void loadData() {
		rows = new ArrayList<Row>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(dataFile));
			String line = br.readLine();
			if (line == null) {
				isValid = false;
				br.close();
				return;
			}
			header = new Header(line);
			
			line = br.readLine();
			while (line != null) {
				Row row = new Row(line);
				if (row.isValid()) rows.add(row);
				line = br.readLine();
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Collections.sort(rows);
	}
	
	public String getName() {
		return dataFile.getName().substring(0, dataFile.getName().indexOf("."));
	}
	
	/**
	 * Given a column title, returns the high
	 * @param title
	 * @return
	 */
	public Double getHigh(String title) {
		double out = Double.MIN_VALUE;
		int index = header.getIndexOfTitle(title);
		if (index == -1) return null;
		if (index >= header.getSize()) return null;
		for (Row row: rows) {
			Double value = row.getValue(index);
			if (value != null) {
				if (value > out) out = value;
			}
		}
		return out;
	}
	
	/**
	 * Given a column title, returns the low
	 * @param title
	 * @return
	 */
	public Double getLow(String title) {
		double out = Double.MAX_VALUE;
		int index = header.getIndexOfTitle(title);
		if (index == -1) return null;
		if (index >= header.getSize()) return null;
		for (Row row: rows) {
			Double value = row.getValue(index);
			if (value != null) {
				if (value < out) out = value;
			}
		}
		return out;
	}
	
}
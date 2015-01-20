import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;


public class DataSet {
	
	public String code;
	public ArrayList<Row> rows;
	public Header header;

	public DataSet(String code, File dir) {
		this.code = code;
		loadData(code, dir);
	}
	

	/**
	 * Given an Quandl code, this finds the appropriately named file
	 * in the data/stocks directory and loads it into an ArrayList of
	 * row objects.  This list is sorted from least recent to
	 * most recent.
	 * @param code
	 * @return
	 */
	public void loadData(String code, File dir) {
		rows = new ArrayList<Row>();
		File dataFile = new File(dir, code + ".csv");
		try {
			BufferedReader br = new BufferedReader(new FileReader(dataFile));
			String line = br.readLine();
			header = new Header(line);
			
			line = br.readLine();
			while (line != null) {
				Row row = new Row(line);
				rows.add(row);
				line = br.readLine();
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Collections.sort(rows);
	}
	
//	public double getHigh(String title) {
//		
//	}

}
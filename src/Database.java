import java.io.File;
import java.util.ArrayList;

/**
 * Database is a collection of data sets
 * 
 * @author brianrisk
 *
 */
public class Database {

	String code;
	File dataDir;
	ArrayList<DataSet> dataSets;

	public Database(String code) {
		this.code = code;
		dataDir = new File("data/" + code);

		File [] dataFiles = dataDir.listFiles();


		dataSets = new ArrayList<DataSet>();
		for (File dataFile: dataFiles) {
			DataSet dataSet = new DataSet(dataFile);
			dataSets.add(dataSet);
		}



	}

	public void report() {
		DataSet firstDataSet = dataSets.get(0);

		for (String title: firstDataSet.header.titles) {
			double maxValue = Double.MIN_VALUE;
			String maxName = "";
			for (DataSet dataSet: dataSets) {
				Double value = dataSet.getHigh(title);
				if (value != null) {
					if (value > maxValue) {
						maxValue = value;
						maxName = dataSet.getName();
					}
				}
			}
			U.p(title + ": " + maxName + ", " + maxValue);
		}
	}

}

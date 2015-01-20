import java.io.File;
import java.util.ArrayList;


public class QuandlOutliers {

	public static void main(String[] args) {
		File dataDir = new File("data/ZES");
		File [] dataFiles = dataDir.listFiles();
		
		ArrayList<DataSet> dataSets = new ArrayList<DataSet>();
		for (File dataFile: dataFiles) {
			DataSet dataSet = new DataSet(dataFile);
			dataSets.add(dataSet);
		}
		
		double maxValue = Double.MIN_VALUE;
		String maxName = "";
		for (DataSet dataSet: dataSets) {
			Double value = dataSet.getHigh("EPS_STD_EST");
			if (value != null) {
				if (value > maxValue) {
					maxValue = value;
					maxName = dataSet.getName();
				}
			}
		}
		U.p(maxName);
		U.p(maxValue);

	}

}

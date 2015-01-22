import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;

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

//		File [] dataFiles = dataDir.listFiles();
//		dataSets = new ArrayList<DataSet>();
//		for (File dataFile: dataFiles) {
//			if (dataFile.getName().toLowerCase().endsWith(".csv")) {
//				DataSet dataSet = new DataSet(dataFile);
//				dataSets.add(dataSet);
//			}
//		}



	}

	
	
	
	public void report() {
		File outputDir = new File("reports/" + code);
		outputDir.mkdirs();
		
		File [] dataFiles = dataDir.listFiles();
		DataSet firstDataSet = new DataSet(dataFiles[0]);

		try {
			PrintWriter pw = new PrintWriter(new FileWriter(new File(outputDir, "index.html")));
			pw.println("<html><head><title>" + code + "</title></head><body>");
			pw.println("<h1>"+code+"</h1>");
			
			// find max outliers
			Hashtable<String, Double> maxValues = new Hashtable<String, Double>();
			Hashtable<String, String> maxNames = new Hashtable<String, String>();
			Hashtable<String, Double> minValues = new Hashtable<String, Double>();
			Hashtable<String, String> minNames = new Hashtable<String, String>();
			for (File dataFile: dataFiles) {
				if (!dataFile.getName().toLowerCase().endsWith(".csv")) continue;
				DataSet dataSet = new DataSet(dataFile);
				if (!dataSet.isValid) {
					U.p("invalid: " + dataSet.getName());
					continue;
				}
				for (String title: firstDataSet.header.titles) {
					Double value = dataSet.getHigh(title);
					if (value != null) {
						// max value
						Double maxValue = maxValues.get(title);
						if (maxValue == null) maxValue = Double.MIN_VALUE;
						if (value > maxValue) {
							maxValues.put(title, value);
							maxNames.put(title, dataSet.getName());
						}
						// min value
						Double minValue = minValues.get(title);
						if (minValue == null) minValue = Double.MAX_VALUE;
						if (value < minValue) {
							minValues.put(title,  value);
							minNames.put(title, dataSet.getName());
						}
					} else {
						U.p("bad column index for " + title + ": " + dataSet.getName());
					}
				}
				
			}
			
			// print the table
			pw.println("<h2>Max values</h2>");
			printTable(pw, maxValues, maxNames);
			pw.println("<h2>Min values</h2>");
			printTable(pw, minValues, minNames);

			

			pw.println("</body></html>");
			pw.flush();
			pw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private void printTable(PrintWriter pw, Hashtable<String, Double> outlierValues, Hashtable<String, String> outlierNames) {
		pw.println("<table>");
		for (String title: outlierValues.keySet()) {
			double maxValue = outlierValues.get(title);
			String maxName = outlierNames.get(title);
			maxValue =  (double) Math.round(maxValue * 100000) / 100000;
			pw.println("<tr>");
			pw.println("<td>" + title + "</td>");
			pw.println("<td><a href='https://quandl.com/" + code + "/" + maxName + "'>" + maxName + "</a></td>");
			pw.println("<td>" + maxValue + "</td>");
			pw.println("</tr>");
		}
		pw.println("</table>");
	}

}

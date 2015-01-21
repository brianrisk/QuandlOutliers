import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
			if (dataFile.getName().toLowerCase().endsWith(".csv")) {
				DataSet dataSet = new DataSet(dataFile);
				dataSets.add(dataSet);
			}
		}



	}

	public void report() {
		File outputDir = new File("reports/" + code);
		outputDir.mkdirs();
		DataSet firstDataSet = dataSets.get(0);

		try {
			PrintWriter pw = new PrintWriter(new FileWriter(new File(outputDir, "index.html")));
			pw.println("<html><head><title>" + code + "</title></head><body>");

			pw.println("<h1>"+code+"</h1>");

			pw.println("<h2>Max values</h2>");
			pw.println("<table>");
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
				maxValue =  (double) Math.round(maxValue * 100000) / 100000;
				pw.println("<tr>");
				pw.println("<td>" + title + "</td>");
				pw.println("<td><a href='https://quandl.com/" + code + "/" + maxName + "'>" + maxName + "</a></td>");
				pw.println("<td>" + maxValue + "</td>");
				pw.println("</tr>");
			}
			pw.println("</table>");

			pw.println("<h2>Min values</h2>");
			pw.println("<table>");
			for (String title: firstDataSet.header.titles) {
				double minValue = Double.MAX_VALUE;
				String minName = "";
				for (DataSet dataSet: dataSets) {
					Double value = dataSet.getHigh(title);
					if (value != null) {
						if (value < minValue) {
							minValue = value;
							minName = dataSet.getName();
						}
					}
				}
				minValue =  (double) Math.round(minValue * 100000) / 100000;
				pw.println("<tr>");
				pw.println("<td>" + title + "</td>");
				pw.println("<td><a href='https://quandl.com/" + code + "/" + minName + "'>" + minName + "</a></td>");
				pw.println("<td>" + minValue + "</td>");
				pw.println("</tr>");
			}
			pw.println("</table>");

			pw.println("</body></html>");
			pw.flush();
			pw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}



	}

}

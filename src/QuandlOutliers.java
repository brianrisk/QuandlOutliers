import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;



public class QuandlOutliers {

	public static void main(String[] args) {
		File data = new File("data");
		File reportDir = new File("reports");
		reportDir.mkdirs();
		File [] databaseDirs = data.listFiles();
		
		PrintWriter pw;
		try {
			pw = new PrintWriter(new FileWriter(new File(reportDir, "index.html")));
			
			pw.println("<html><head><title>data</title></head><body>");
			pw.println("<ul>");
			for (File databaseDir: databaseDirs) {
				if (databaseDir.isFile()) continue;
				String code = databaseDir.getName();
				Database database = new Database(code);
				pw.println("<li><a href='" + code + "/index.html'>" + code + "</a></li>");
				database.report2();
			}
			pw.println("</ul>");
			pw.println("</body></html>");
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		U.p("done");
	}

}

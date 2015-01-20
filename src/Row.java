import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Row implements Comparable<Row>{
	Date date;
	String line;
	ArrayList<Double> values = new ArrayList<Double>();
	
	public Row(String line) {
		this.line = line;
		String [] chunks = line.split(",");
		
		//get the date from the string in form yyyy-MM-dd
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = dateFormat.parse(chunks[0]);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		for (int index = 1; index < chunks.length; index++) {
			String chunk = chunks[index];
			Double value = null;
			if (chunk.length() > 0) {
				value = Double.parseDouble(chunks[1]);
			}
			values.add(value);
		}
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	public Double getValue(int index) {
		// Subtracting 1 from index as the first column, the date column, 
		// is not part of the values array
		return values.get(index - 1);
	}
	
	
	@Override
	public int compareTo(Row other) {
		return date.compareTo(other.date);
	}
	
	public String toString() {
		return line;
	}
	

}

import java.util.ArrayList;


public class Header {
	
	String line;
	ArrayList<String> titles = new ArrayList<String>();
	
	public Header(String line) {
		this.line = line;
		String [] chunks = line.split(",");
		for (int index = 1; index < chunks.length; index++) {
			titles.add(chunks[index]);
		}
	}
	
	/**
	 * Given a string, finds the index of that string in our titles arrary
	 * returns -1 if not there.
	 * @param title
	 * @return
	 */
	public int getIndexOfTitle(String title) {
		for (int index = 0; index < titles.size(); index++) {
			if (titles.get(index).equals(title)) return index;
		}
		return -1;
	}
	
	public int getSize() {
		return titles.size();
	}
	
	public String toString() {
		return line;
	}

}

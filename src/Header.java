
public class Header {
	
	String line;
	String [] titles;
	
	public Header(String line) {
		this.line = line;
		titles = line.split(",");
	}
	
	/**
	 * Given a string, finds the index of that string in our titles arrary
	 * returns -1 if not there.
	 * @param title
	 * @return
	 */
	public int getIndexOfTitle(String title) {
		for (int index = 0; index < titles.length; index++) {
			if (titles[index].equals(title)) return index;
		}
		return -1;
	}
	
	public int getSize() {
		return titles.length;
	}
	
	public String toString() {
		return line;
	}

}

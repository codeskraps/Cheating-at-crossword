package ie.gmit;

public class WordItem {

	private String displayWord;
	private String searchWord;
	
	public WordItem (String displayWord) {
		setDisplayWord(displayWord);
	}

	public String getDisplayWord() {
		return displayWord;
	}

	public void setDisplayWord(String displayWord) {
		this.displayWord = displayWord;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
}

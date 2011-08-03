package ie.gmit;

public class CrosswordPreferences {
	// This is a class I created to save few variables that are used across the app
	
	private int searchLimit;
	private String selectedWord;
	
	public CrosswordPreferences () {
		setSearchLimit(200);
	}
	
	public CrosswordPreferences (int newSearchLimit) {
		setSearchLimit(newSearchLimit);
	}
	
	public void setSearchLimit(int newSearchLimit) {
		this.searchLimit = newSearchLimit;
	}
	
	public int getSearchLimit() {
		return this.searchLimit;
	}
	
	public void setSelectedWord(String newSelectedWord) {
		this.selectedWord = newSelectedWord;
	}
	
	public String getSelectedWord() {
		return this.selectedWord;
	}
}

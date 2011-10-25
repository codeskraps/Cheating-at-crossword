package ie.gmit;

import android.util.Log;


public class WordItem {
	private static final String TAG = WordItem.class.getSimpleName();
	
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
		setSearchWord(displayWord);
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		char[] cArray = searchWord.toLowerCase().toCharArray();
        this.searchWord = "";
        
        for (char c : cArray) {
	
        	if 		(c == 'à' || c == 'á')		this.searchWord += 'a';
            else if (c == 'è' || c == 'é')   	this.searchWord += 'e';
            else if (c == 'í' || c == 'ï')		this.searchWord += 'i';
            else if (c == 'ó' || c == 'ò')		this.searchWord += 'o';
            else if (c == 'ú' || c == 'ü')		this.searchWord += 'u';
            //else if (c == 'ç')					newWord += 'c';
            //else if (c == 'ş')					newWord += 's';
            //else if (c == '-' || c == '·')		this.searchWord = this.searchWord;
            else 								this.searchWord += c;
        }	
        
		Log.d(TAG, "Adding: " + getDisplayWord());
	}
}

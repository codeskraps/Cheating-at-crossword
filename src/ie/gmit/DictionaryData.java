package ie.gmit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;

public class DictionaryData {
	private static final String TAG = DictionaryData.class.getSimpleName();
	
	private Context context;
	private static HashMap<Integer, ArrayList<WordItem>> wordMap = null;

	public DictionaryData(Context context) {
		this.context = context;
		wordMap = new HashMap<Integer, ArrayList<WordItem>>();
	}

	public void load() throws Exception {
		Log.d(TAG, "load Start'd");

		InputStream inputStream = context.getResources().openRawResource(R.raw.catalan_clean);
		
		if (inputStream != null) {
			InputStreamReader inputReader = new InputStreamReader(inputStream, "UTF8");
			BufferedReader buffReader = new BufferedReader(inputReader);
			
			String next = null;
            
			try {
				while ((next = buffReader.readLine()) != null) {
					addWord(next);
				}
				buffReader.close();
				
			} catch (IOException e) {
				throw new Exception(e.getMessage());
			}
			inputReader.close();
		}
	}

	public ArrayList<WordItem> getKeyWordItemList(Integer key) {
		if (wordMap.containsKey(key)) {
    		return wordMap.get(key);
    	} else {
    		return null;
    	}
	}
	
	public void addWord (String next){
		WordItem wordItem = new WordItem(next);
		ArrayList<WordItem> wordItemList = new ArrayList<WordItem>();
		
		if (getKeyWordItemList(wordItem.getSearchWord().length()) != null) {
    		wordItemList = getKeyWordItemList(wordItem.getSearchWord().length());
    	} else {
    		wordItemList = new ArrayList<WordItem>();
    	}
		wordItemList.add(wordItem);
    	wordMap.put(wordItem.getSearchWord().length(), wordItemList);
    	
		// Log.d(TAG, "New word, display " + wordItem.getDisplayWord() + ", search: " + wordItem.getSearchWord());
	}
}

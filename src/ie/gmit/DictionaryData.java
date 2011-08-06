package ie.gmit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;

public class DictionaryData {
	private static final String TAG = DictionaryData.class.getSimpleName();
	
	private Context context;
	private List<WordItem> wordKey = new ArrayList<WordItem>();
	private Map<Integer, ArrayList<WordItem>> wordMap = new HashMap<Integer, ArrayList<WordItem>>();

	public DictionaryData(Context context) {
		this.context = context;
	}

	public void load() throws Exception {
		Log.d(TAG, "load Start'd");

		InputStream inputStream = context.getResources().openRawResource(
				R.raw.catalan_clean);

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

	public Map<Integer, ArrayList<WordItem>> getWordMap() {
		return wordMap;
	}
	
	public void addWord (String next){
		WordItem newWord = new WordItem(next);
		wordMap.put(next.length(), new String(next).toLowerCase());
		Log.d(TAG, "New word: " + next);
	}
}

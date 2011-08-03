package ie.gmit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.Log;

public class Dictionary {
	// Dictionay class with a hashMap
	// I could have taken this class out and put it the CrosswodApplication class 
	// or even in the CrosswordPreferences class
	// I started like that and I think it is just fine where it is... 
	private static final String TAG = Dictionary.class.getSimpleName();
	
	private Map<String, String> wordMap = new HashMap<String, String>();

	private Context context;
	
	public Dictionary(Context context) {
		this.context = context;
	}

	public void load() throws Exception {
		Log.d(TAG, "load Start'd");

		InputStream inputStream = context.getResources().openRawResource(
				R.raw.dictionary);

		if (inputStream != null) {
			InputStreamReader inputReader = new InputStreamReader(inputStream,
					"UTF8");
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

	public Map<String, String> getWordMap() {
		return wordMap;
	}
	
	public void addWord (String newWord){
		// I know I'm doing all this wrong. I'm making every word a key instead of making word.lenght the key.
		// but I follow your labs example and when I realise that I was to far and to lazy to change it.
		wordMap.put(new String(newWord).toLowerCase(), new String(newWord).toLowerCase());
		Log.d(TAG, "New word: " + newWord);
	}
}

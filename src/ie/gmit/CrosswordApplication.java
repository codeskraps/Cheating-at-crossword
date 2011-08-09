package ie.gmit;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class CrosswordApplication extends Application implements OnSharedPreferenceChangeListener {
	private static String TAG = CrosswordApplication.class.getSimpleName();
	
	private DictionaryData dictionary = null;
	private SharedPreferences prefs = null;
	private CrosswordPreferences crossPrefs = null;
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "CrosswordApplication onCreate");

		dictionary = new DictionaryData(this);
		crossPrefs = new CrosswordPreferences();
		
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(this);
		
		// We load the dictionay in the application class so that we only loaded once
		Log.d(TAG,"Loading started: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
		//new LoadDictionary().execute((String) null);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		int searchLimit = Integer.parseInt(prefs.getString("search_limit", "200"));
		crossPrefs.setSearchLimit(searchLimit);
				
		//Log.d(TAG, "searchLimit: " + Integer.toString(crossPrefs.getSearchLimit()));
	}
	
	public DictionaryData getDictionary() {
		return dictionary;
	}
	
	public CrosswordPreferences getCrosswordPreferences() {
		// Before we return crossPrefs we make sure to get the right settings from SharedPreferences
		int searchLimit = Integer.parseInt(prefs.getString("search_limit", "200"));
		crossPrefs.setSearchLimit(searchLimit);
		
		Log.d(TAG, "crossPrefs fir'd");
		
		return crossPrefs;
	}
	
//	private class LoadDictionary extends AsyncTask<String, Integer, String>{
//
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			
//			Toast.makeText(CrosswordApplication.this, "Loading the dictionary...", Toast.LENGTH_LONG).show();
//		}
//
//		@Override
//		protected String doInBackground(String... params) {
//			String result;
//			
//			try {
//				// We load the dictionary
//				dictionary.load();
//				result = getString(R.string.msgDicLoadSuccess);
//			} catch (Exception e) {
//				Log.d(TAG, "[ERROR] Encountered a problem reading the dictionary. "
//						+ e);
//				result = getString(R.string.msgDicLoadFail);
//			}
//			return result;
//		}
//
//		@Override
//		protected void onPostExecute(String result) {
//			super.onPostExecute(result);
//			
////			dialog.dismiss();
//			
//			// End the thread showing a Toast
//			Toast.makeText(CrosswordApplication.this, result, Toast.LENGTH_LONG).show();
//			Log.d(TAG,"Loading ended: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
//		}
//	}
}

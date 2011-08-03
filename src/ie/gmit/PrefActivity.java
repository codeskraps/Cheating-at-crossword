package ie.gmit;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

public class PrefActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener{
	// The PreferenceActivity class is usually a one liner class but this is a bit longer
	// just because every time a search limit is changed we display the summary in the 
	// activity accordingly to the selection. So, this required a bit more code
	private static final String TAG = PrefActivity.class.getSimpleName();
	private static final String KEY_LIST_PREFERENCE = "search_limit";
	
	private CrosswordApplication crossword = null;
	private CrosswordPreferences crossPrefs = null;
	
	private ListPreference lstSearchLimit = null;
	private String summary;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		crossword = (CrosswordApplication) getApplication();
		crossPrefs = crossword.getCrosswordPreferences();
		
		setTitle(R.string.titlePreference);
		
		addPreferencesFromResource(R.xml.prefs);
		PreferenceManager.setDefaultValues(PrefActivity.this, R.xml.prefs, false);
		
		lstSearchLimit = (ListPreference)getPreferenceScreen().findPreference(
				KEY_LIST_PREFERENCE);
		
		summary = getString(R.string.summary_search_limit);
		Log.d(TAG, Integer.toString(crossPrefs.getSearchLimit()) + ", "+ summary);
		lstSearchLimit.setSummary(Integer.toString(crossPrefs.getSearchLimit()) + " " + summary);
		
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}
	
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if (key.equals(KEY_LIST_PREFERENCE)) {
			crossPrefs = crossword.getCrosswordPreferences();
			lstSearchLimit.setSummary(Integer.toString(crossPrefs.getSearchLimit()) + " " + summary);
			Log.d(TAG, "equal");
		}else{
			Log.d(TAG, "not equal");
		}
	}
}

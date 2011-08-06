package ie.gmit;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

public class WebDefinition extends Activity{
	private static final String TAG = WebDefinition.class.getSimpleName();
		
	private CrosswordApplication crossword = null;
	private CrosswordPreferences crossPrefs = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.d(TAG, "WebDefinition Start'd");
		
		crossword = (CrosswordApplication) getApplication();
		crossPrefs = crossword.getCrosswordPreferences();

		
		WebView webview = new WebView(this);
		setContentView(webview);
		
		// we open a dictionary web page of the selected word
		// Log.d(TAG, "Loading: http://dictionary.reference.com/browse/" + crossPrefs.getSelectedWord());
		//webview.loadUrl("http://dictionary.reference.com/browse/" + crossPrefs.getSelectedWord());
		webview.loadUrl("http://ca.wiktionary.org/wiki/" + crossPrefs.getSelectedWord());
}

	@Override
	protected void onStop() {
		super.onStop();
		finish();
	}

}

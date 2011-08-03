package ie.gmit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class CrosswordActivity extends ListActivity implements OnClickListener {
	private static final String TAG = CrosswordActivity.class.getSimpleName();
	
	private CrosswordApplication crossword = null;

	private EditText txtWordClue = null;
	private Button btnSearch = null;
	private Dictionary dictionary = null;
	private ProgressDialog dialog = null;
	private CrosswordPreferences crossPrefs = null;
	
	// List
	private static final String ITEM_KEY = "key";
	private ArrayList<HashMap<String, String>> foundList = new ArrayList<HashMap<String, String>>();
	private SimpleAdapter adapter;
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.crossword_main);
		
		init();
	}

	public void init() {
		Log.d(TAG, "initStart'd");

		// Get variables
		
		txtWordClue = (EditText) findViewById(R.id.editTextClue);

		btnSearch = (Button) findViewById(R.id.btnSearch);
		btnSearch.setOnClickListener(this);
		
		// We set the adapter for the ListActivity
		this.adapter = new SimpleAdapter(this, foundList, R.layout.row,
				new String[] { ITEM_KEY }, new int[] { R.id.txtRow });
		setListAdapter(this.adapter);
		
		// Menus
		registerForContextMenu(getListView());
}

	@SuppressWarnings("unchecked")
	@Override
	public void onClick(View v) {
		// we get the entered word
		int hashcount = 0;
		String wordClue = txtWordClue.getText().toString();
		Log.d(TAG, "Entered word: " + wordClue);
		
		// count how many hashses entered
		char[] arWordClue = wordClue.toCharArray();
		for (int x = 0; x < arWordClue.length; x++) {
			if (wordClue.indexOf('#') != -1)
				hashcount++;
		}
		// Too many hashes we ran out of memory (to many possibilities)
		// and the player doesn't have a clue about crossword
		// too bad for him... :-P
		if (hashcount <= 3) {
			// we check that there isn't any blanks
			if (wordClue.indexOf(' ') == -1) {
				foundList.clear();
				adapter.notifyDataSetChanged();
				
				// We set up a new arraylist and pass it to the thread
				ArrayList<String> lstSearch = new ArrayList<String>();
				new SearchingDictionary().execute(lstSearch);
			}else {
				Toast.makeText(this, "Not a valid clue", Toast.LENGTH_SHORT).show();
			}
		}else {
			Toast.makeText(this, "Too many hashes", Toast.LENGTH_SHORT).show();
		}
	}
	
	private class SearchingDictionary extends AsyncTask<ArrayList<String>, Integer, String>{

		@Override
	    protected void onPreExecute() {
			super.onPreExecute();

			// We show a dialog to wait
	        dialog = new ProgressDialog(CrosswordActivity.this);
			dialog.setTitle(getString(R.string.msgDialogPostingTitle));
			dialog.setMessage(getString(R.string.msgDialogSearching));
			dialog.setIndeterminate(true);
			dialog.setCancelable(true);
	        dialog.show();
	    }
		
		@Override
		protected String doInBackground(ArrayList<String>... passing) {
			int total = 0;
			
			// We get Preferences and passed values
			crossword = (CrosswordApplication) getApplication();
			dictionary = crossword.getDictionary();
			crossPrefs = crossword.getCrosswordPreferences();
			ArrayList<String> lstSearch = passing[0];
			
			// Get entered word and convert it to an array
			String wordClue = new String(txtWordClue.getText().toString().toLowerCase());
			char[] arWordClue = wordClue.toCharArray();
			
			// Call the Method
			addToSearchList(arWordClue, 0, lstSearch);
			
			Map<String, String> mapDictionary = dictionary.getWordMap();
		        
			for (int y = 0; y < lstSearch.size(); y++){
				if (mapDictionary.containsKey(lstSearch.get(y))) {
					String foundWord = mapDictionary.get(lstSearch.get(y));
					//Log.d(TAG, "Found word " + lstSearch.get(y));
					try {
						HashMap<String, String> item = new HashMap<String, String>();
						item.put(ITEM_KEY, foundWord);
						foundList.add(item);
						total++;
						if (total == crossPrefs.getSearchLimit()){
							break;
						}
					} catch (NullPointerException e) {
						Log.d(TAG, "Tried to add null value");
					}
				} else {
					//Log.d(TAG, "Not Found word " + lstSearch.get(y));
				}
			}
			
			Log.d(TAG, "Finished Searching. Found " + Integer.toString(total));
			
			return Integer.toString(total);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			
			dialog.dismiss();
			
			adapter.notifyDataSetChanged();
			if (Integer.parseInt(result) == crossPrefs.getSearchLimit()){
				Toast.makeText(CrosswordActivity.this, "Search Limit of " + result + " reached", Toast.LENGTH_LONG).show();
			}else{
				Toast.makeText(CrosswordActivity.this, "Found " + result + " words", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	public void addToSearchList (char[] arWordClue, int i, List<String> lstSearch){	
		// We basically swap # for a to z if we find more # we call this method again to do the same
		// we call this method as many times as needed depending on how many # there are and swap them
		// a to z char. This creates all the different possibilities...
		for (int y = i; y < arWordClue.length; y++){
			if (arWordClue[y] == '#'){
				for (int z = 97; z <= 122; z++){
					arWordClue[y] = (char) z;
					String newWord = new String (arWordClue);
					if (newWord.indexOf('#') == -1)
						lstSearch.add(newWord);
					// Log.d(TAG, "newword: " + newWord + " indexOf " + newWord.indexOf('#'));
					if (y < arWordClue.length - 1)
						addToSearchList(arWordClue, y + 1, lstSearch);
				}
				arWordClue[y] = (char) 35;
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// creates the menu
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.mnuAdd_word:
			// we start the addWord Activity
			startActivity(new Intent(this, DialogAddWord.class));
			break; 
		case R.id.mnuPref:
			// we start PrefActivity
			startActivity(new Intent(this, PrefActivity.class));
			break;
		case R.id.mnuQuit:
			// We end ... :-(
			finish();
			break;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		// We get word pressed from the adapter
		HashMap<String, String> item = (HashMap<String, String>) adapter.getItem(position);
		String newSelectedWord = (String) item.get(ITEM_KEY);
		
		// Save the word in our Preference class and start the webActivity
		crossPrefs.setSelectedWord(newSelectedWord);
		startActivity(new Intent(this, WebDefinition.class));
		Log.d(TAG, "Word Selected: " + String.valueOf(position) + ", " + newSelectedWord);
	}
}
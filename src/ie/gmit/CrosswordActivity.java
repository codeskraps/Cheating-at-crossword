package ie.gmit;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class CrosswordActivity extends ListActivity implements OnClickListener {
	private static final String TAG = CrosswordActivity.class.getSimpleName();
	
	private EditText txtWordClue = null;
	private Button btnSearch = null;
	private DictionaryData dictionary = null;
	private ProgressDialog dialog = null;
	private CrosswordPreferences crossPrefs = null;
	
	// List
	private static final String ITEM_KEY = "key";
	private ArrayList<HashMap<String, String>> foundList = new ArrayList<HashMap<String, String>>();
	private SimpleAdapter adapter;
	
	private Integer total;
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.crossword_main);
		
		Log.d(TAG, "initStart'd");

		txtWordClue = (EditText) findViewById(R.id.editTextClue);
		btnSearch = (Button) findViewById(R.id.btnSearch);
		
		btnSearch.setOnClickListener(this);
		txtWordClue.addTextChangedListener(new CustomTextWatcher()); 
		
		adapter = new SimpleAdapter(this, foundList, R.layout.row,
									new String[] { ITEM_KEY }, new int[] { R.id.txtRow });
		setListAdapter(adapter);
		
		registerForContextMenu(getListView());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onClick(View v) {
		// we get the entered word
		String wordClue = txtWordClue.getText().toString();
		Log.d(TAG, "Entered word: " + wordClue);
		
		foundList.clear();
		adapter.notifyDataSetChanged();
		
		// We set up a new arraylist and pass it to the thread
		ArrayList<String> lstSearch = new ArrayList<String>();
		new SearchingDictionary().execute(lstSearch);
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
			total = 0;
			// We get Preferences and passed values
			dictionary = ((CrosswordApplication) getApplication()).getDictionary();
			crossPrefs = ((CrosswordApplication) getApplication()).getCrosswordPreferences();
			ArrayList<String> lstSearch = passing[0];
			
			
			// Get entered word and convert it to an array
			String wordClue = new String(txtWordClue.getText().toString().toLowerCase());
			
			int r;
			switch(wordClue.length()) {
			case 2: r = R.array.dic_cat_2; break;
			case 3: r = R.array.dic_cat_3; break;
			case 4: r = R.array.dic_cat_4; break;
			case 5: r = R.array.dic_cat_5; break;
			case 6: r = R.array.dic_cat_6; break;
			case 7: r = R.array.dic_cat_7; break;
			case 8: r = R.array.dic_cat_8; break;
			case 9: r = R.array.dic_cat_9; break;
			case 10: r = R.array.dic_cat_10; break;
			case 11: r = R.array.dic_cat_11; break;
			case 12: r = R.array.dic_cat_12; break;
			case 13: r = R.array.dic_cat_13; break;
			case 14: r = R.array.dic_cat_14; break;
			case 15: r = R.array.dic_cat_15; break;
			case 16: r = R.array.dic_cat_16; break;
			case 17: r = R.array.dic_cat_17; break;
			case 18: r = R.array.dic_cat_18; break;
			case 21: r = R.array.dic_cat_21; break;
			default: r = 0;
			}
			
			Log.d(TAG, "about to get resorces:" + r);
			String[] arr = null;
			if (r != 0) arr = getResources().getStringArray(r);
			Log.d (TAG, "Got Resources: " + r);
			
			TernarySearchTree tree = new TernarySearchTree(new TernaryTreeNode('a', false));
			for (String w : arr) {
				String[] parts = w.split("\\|");
				tree.insert(parts[1]);
				Log.d(TAG, "Adding to tree: " + parts[1]);
			}
			
			addToSearchList(wordClue, tree, lstSearch);
			for (String w : lstSearch) {
				try {
					HashMap<String, String> item = new HashMap<String, String>();
					item.put(ITEM_KEY, w);
					foundList.add(item);
				} catch (NullPointerException e) {
					Log.d(TAG, "null pointer error");
				}
			}
			
		    
			Log.d(TAG, "Finished Searching. Found " + Integer.toString(total));
			
			return Integer.toString(total);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			String toast;
			
			dialog.dismiss();
			Log.d(TAG, "here2");
			adapter.notifyDataSetChanged();
			Log.d(TAG, "here3");
//			if (Integer.parseInt(result) == crossPrefs.getSearchLimit()){
//				toast = getString(R.string.toastSearchLimit) + result + getString(R.string.toastSearchLimit2);
//				
//			}else{
//				toast = getString(R.string.toastFound )+ result + getString(R.string.toastFound2);
//			}
			Toast.makeText(CrosswordActivity.this, "found " + result, Toast.LENGTH_LONG).show();
		}
	}
	
	public void addToSearchList (String wordClue, TernarySearchTree tree, ArrayList<String> lstSearch){
		char[] arWordClue = wordClue.toCharArray();
		int quest = wordClue.indexOf('?');
		
    	if (quest != -1) {
    		for (int aToz = 97; aToz <= 122; aToz++){
				arWordClue[quest] = (char) aToz;
				String newWord = new String (arWordClue);
				if (newWord.indexOf('?') == -1){
					if (tree.containsKey(newWord)) {
						Log.d(TAG, "Found: " + newWord);
						lstSearch.add(newWord);
						total++;
						Log.d(TAG, "added to list");
					}
					
				} else {
					Log.d(TAG, "addtosearch: " + newWord);
					addToSearchList(newWord, tree, lstSearch);
				}	
					
				Log.d(TAG, "newword: " + newWord + " indexOf " + newWord.indexOf('?'));
			}
    	} else {
    		if (tree.containsKey(wordClue)) {
				Log.d(TAG, "Found: " + wordClue);
				lstSearch.add(wordClue);
				total++;
				Log.d(TAG, "added to list");
			}
    	}
	}
//		for (int y = i; y < arWordClue.length; y++){
//			if (arWordClue[y] == '?'){
//				for (int z = 97; z <= 122; z++){
//					arWordClue[y] = (char) z;
//					String newWord = new String (arWordClue);
//					if (newWord.indexOf('#') == -1)
//						lstSearch.add(newWord);
//					// Log.d(TAG, "newword: " + newWord + " indexOf " + newWord.indexOf('#'));
//					if (y < arWordClue.length - 1)
//						addToSearchList(arWordClue, y + 1, lstSearch);
//				}
//				arWordClue[y] = (char) 35;
//			}
//		}
	
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
			moveTaskToBack(true);
			break;
		}
		return true;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		// We get word pressed from the adapter
		@SuppressWarnings("unchecked")
		HashMap<Integer, String> item = (HashMap<Integer, String>) adapter.getItem(position);
		String newSelectedWord = (String) item.get(ITEM_KEY);
		
		// Save the word in our Preference class and start the webActivity
		crossPrefs.setSelectedWord(newSelectedWord);
		startActivity(new Intent(this, WebDefinition.class));
		Log.d(TAG, "Word Selected: " + String.valueOf(position) + ", " + newSelectedWord);
	}
}
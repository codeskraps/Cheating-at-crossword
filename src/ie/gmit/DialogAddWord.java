package ie.gmit;

import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DialogAddWord extends Activity {
	// This activity was suppose to be a custom message dialog but for some reason it didn't work
	// it just shows as a normal activity :-(
	private static final String TAG = DialogAddWord.class.getSimpleName();
	private static final int ADDED_SUCCESS = 1;
	private static final int ADDED_FAILED = 2;
	private static final int ADDED_CANCEL = 3;
	private static final int ADDED_ALREADY = 4;
	private static final int ADDED_NOTVALID = 5;
	
	private CrosswordApplication crossword = null;
	private EditText editAddWord = null;
	private Button btnAddOk = null;
	private Button btnAddCancel = null;
	private DictionaryData dictionary = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    
		// We setup the activity and get the handles to the editText and buttons
		Log.d(TAG, "DialogAddWord start'd");
		
	    requestWindowFeature(Window.FEATURE_LEFT_ICON);
	        
	    setContentView(R.layout.dialog_add_word);
	        
	    getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, 
	                android.R.drawable.ic_menu_add);
	    
	    crossword = (CrosswordApplication) getApplication();
	    dictionary = crossword.getDictionary();
	    
	    editAddWord = (EditText) findViewById(R.id.editAddWord);
	    btnAddOk = (Button) findViewById(R.id.btnAddOk);
	    btnAddCancel = (Button) findViewById(R.id.btnAddCancel);
	    btnAddOk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int wordAdded;
				
				// We get the word entered and check it for different things
				String newWord = editAddWord.getText().toString().toLowerCase();
    			if ((!newWord.equals("")) && (newWord.indexOf(' ') == -1)){
    				Map<Integer, String> mapDictionary = dictionary.getWordMap();
    				if (!mapDictionary.containsValue(newWord)) {
    					try {
							dictionary.addWord(newWord);
							wordAdded = ADDED_SUCCESS;
						} catch (Exception e) {
							e.printStackTrace();
							wordAdded = ADDED_FAILED;
						}
    				}else{
    					wordAdded = ADDED_ALREADY;
    				}
    			}else{
    				wordAdded = ADDED_NOTVALID;
    			}
    			// We send it to the onStop() method
				onStop(wordAdded);
			}
		});
	    
	    btnAddCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onStop(ADDED_CANCEL);
			}
		});
	}
	
	protected void onStop(int wordAdded) {
		// We show a toast accordingly and we end the activity
		// This was suppose to be a dialog after all
		super.onStop();
		
		switch(wordAdded){
		case ADDED_SUCCESS:
			Toast.makeText(this, R.string.toastWordAddedSuccessfully, Toast.LENGTH_LONG).show();
			break;
		case ADDED_FAILED:
			Toast.makeText(this, R.string.toastWordAddedFailed, Toast.LENGTH_LONG).show();
			break;
		case ADDED_ALREADY:
			Toast.makeText(this, R.string.toastWordAddedAlready, Toast.LENGTH_LONG).show();
			break;
		case ADDED_CANCEL:
			Toast.makeText(this, R.string.toastWordAddedCancel, Toast.LENGTH_LONG).show();
			break;
		case ADDED_NOTVALID:
			Toast.makeText(this, R.string.toastWordAddedNotValid, Toast.LENGTH_LONG).show();

		}
		finish();
	}
}

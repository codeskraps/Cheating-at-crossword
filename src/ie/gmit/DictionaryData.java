package ie.gmit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.util.Log;

public class DictionaryData {
	private static final String TAG = DictionaryData.class.getSimpleName();
	
	private Context context;
	private TernarySearchTree tree;

	public DictionaryData(Context context) {
		this.context = context;
		setTree(new TernarySearchTree(new TernaryTreeNode('a', false)));
	}

	public void setTree (TernarySearchTree tree) {
		this.tree = tree;
	}
	
	public TernarySearchTree getTree () {
		return tree;
	}
	
//	public void load() throws Exception {
//		Log.d(TAG, "load Start'd");
//
//		InputStream inputStream = context.getResources().openRawResource(R.raw.dictionary);
//		
//		if (inputStream != null) {
//			InputStreamReader inputReader = new InputStreamReader(inputStream, "UTF8");
//			BufferedReader buffReader = new BufferedReader(inputReader);
//			
//			String next = null;
//            int added = 0;
//			try {
//				while ((next = buffReader.readLine()) != null) {
//					tree.insert(next);
//					added++;
//					if(added % 1000 == 0) Log.d(TAG, "Added: " + added + ", at " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
//				}
//				buffReader.close();
//				
//			} catch (IOException e) {
//				throw new Exception(e.getMessage());
//			}
//			inputReader.close();
//		}
//	}
}

package ie.gmit;

import android.text.Editable;
import android.text.TextWatcher;

public class CustomTextWatcher implements TextWatcher {

	private boolean mEditing;
	
	public CustomTextWatcher () {
		setmEditing(false);
	}
	public boolean ismEditing() {
		return mEditing;
	}
	public void setmEditing(boolean mEditing) {
		this.mEditing = mEditing;
	}
	@Override
	public void afterTextChanged(Editable s) {
		if (!ismEditing()) {
			setmEditing(true);
			
			String newWord = s.toString().replaceAll("[^abcdefghijklmnopqrstuvwxyz]", "?");
			s.replace(0, s.length(), newWord);
			
			setmEditing(false);
		}
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		
	}

}

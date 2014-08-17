package klasJoensson.shipsbell;

import java.util.Calendar;
import java.util.Date;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	
	/**
	 * Handles the the language radio buttons.
	 * @param view
	 */
	public void onRadioButtonClicked(View view) {
	   
	    boolean checked = ((RadioButton) view).isChecked();
	    TextView langHeadLine = (TextView) findViewById(R.id.language_headline);
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.engLangButton:
	            if (checked)
	                // Has chosen English as language
	            	langHeadLine.setText(R.string.language_headline_eng);
	            break;
	        case R.id.seLangButton:
	            if (checked) 
	                // Has chosen Swedish as language
	            	langHeadLine.setText(R.string.language_headLine_se);
	            break;
	    }
	    
	    updateDutyPeriod();
	}
	
	/**
	 * Updates the name of the duty period.
	 */
	private void updateDutyPeriod() {
		TextView dutyPeriod = (TextView) findViewById(R.id.duty_period);
		boolean engSel = ((RadioButton) findViewById(R.id.engLangButton)).isChecked();
		int currHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		
		if (currHour < 4) {
			if (engSel)
				dutyPeriod.setText(R.string.duty_period5_eng);
			else
				dutyPeriod.setText(R.string.duty_period5_se);
		} else if (currHour < 8) {
			if (engSel)
				dutyPeriod.setText(R.string.duty_period6_eng);
			else
				dutyPeriod.setText(R.string.duty_period6_se);
		} else if (currHour < 12) {
			if (engSel)
				dutyPeriod.setText(R.string.duty_period0_eng);
			else
				dutyPeriod.setText(R.string.duty_period0_se);
		} else if (currHour < 16) {
			if (engSel)
				dutyPeriod.setText(R.string.duty_period1_eng);
			else
				dutyPeriod.setText(R.string.duty_period1_se);
		} else if (currHour < 18) {
			if (engSel)
				dutyPeriod.setText(R.string.duty_period2_eng);
			else
				dutyPeriod.setText(R.string.duty_period2_se);
		} else if (currHour < 20) {
			if (engSel)
				dutyPeriod.setText(R.string.duty_period3_eng);
			else
				dutyPeriod.setText(R.string.duty_period3_se);
		} else {
			if (engSel)
				dutyPeriod.setText(R.string.duty_period4_eng);
			else
				dutyPeriod.setText(R.string.duty_period4_se);
		}
	}
}

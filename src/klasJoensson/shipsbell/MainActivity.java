/*******************************************************************************
 * Copyright (c) 2014  Klas Jönsson <klas.joensson@gmail.com>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * https://www.apache.org/licenses/LICENSE-2.0
 ******************************************************************************/
package klasJoensson.shipsbell;

import java.util.Calendar;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.os.Build;
import android.provider.ContactsContract.CommonDataKinds.Im;

public class MainActivity extends ActionBarActivity {

	Handler updateHandler = new Handler();
	// Let's update i every minute
	int delayMilisec = 60*1000;
	BellSystem bellSystem = BellSystem.UK;
	
	private enum BellSystem {
		SWEDISH,
		UK,
		UK_OLD
	}
	
	Runnable updateTasks = new Runnable() {
		
		@Override
		public void run() {
			updateDutyPeriod();
			updateBells();
			updateHourGlass();
			
			updateHandler.postDelayed(this, delayMilisec);
		}
	};
	
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

	@Override
	protected void onStart() {
		super.onStart();

		updateHandler.postDelayed(updateTasks, 0);
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
	            if (checked) {
	                // Has chosen English as language
	            	langHeadLine.setText(R.string.language_headline_eng);
	            	bellSystem = BellSystem.UK;
	            	((RadioButton) findViewById(R.id.seLangButton)).setChecked(false);
	            	((RadioButton) findViewById(R.id.ukOldLangButton)).setChecked(false);
	            }
	            break;
	        case R.id.seLangButton:
	            if (checked) { 
	                // Has chosen Swedish as language
	            	langHeadLine.setText(R.string.language_headLine_se);
	            	bellSystem = BellSystem.SWEDISH;
	            	((RadioButton) findViewById(R.id.engLangButton)).setChecked(false);
	            	((RadioButton) findViewById(R.id.ukOldLangButton)).setChecked(false);
	            }
	            break;
	        case R.id.ukOldLangButton:
	            if (checked) {
	                // Has chosen English as language
	            	langHeadLine.setText(R.string.language_headline_eng);
	            	bellSystem = BellSystem.UK_OLD;
	            	((RadioButton) findViewById(R.id.seLangButton)).setChecked(false);
	            	((RadioButton) findViewById(R.id.engLangButton)).setChecked(false);
	            }
	    }
	    
	    updateDutyPeriod();

	}
	
	/**
	 * Updates the name of the duty period.
	 */
	private void updateDutyPeriod() {
		TextView dutyPeriod = (TextView) findViewById(R.id.duty_period);
		boolean engSel;
		if (bellSystem.equals(BellSystem.SWEDISH))
			engSel = false;
		else
			engSel = true;
		
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
	
	/**
	 * Calculates the number of bells to be shown.
	 * 
	 * @return The number of bells to show
	 */
	private int getNumberOfBells() {
		int bells = 0;
		int currHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		
		if (currHour < 4) {
			bells = currHour * 2;
		} else if (currHour < 8) {
			bells = (currHour - 4) * 2;
		} else if (currHour < 12) {
			bells = (currHour - 8) * 2;
		} else if (currHour < 16) {
			bells = (currHour - 12) * 2;
		} else if (currHour < 20) {
			if (bellSystem.equals(BellSystem.UK) && currHour > 17)
				bells = (currHour - 18) * 2;
			else
				bells = (currHour - 16) * 2;
		} else {
			bells = (currHour - 20) * 2;
		}
		
		if (Calendar.getInstance().get(Calendar.MINUTE) > 30)
			bells++;
		
		return bells;
	}
	
	/**
	 * Updates the number of bells shown.
	 */
	private void updateBells() {
		int nrOfBellsToShow = getNumberOfBells();
		int[] bellsVisibility = new int[7];
		for (int i=0;i<7;i++)
			if (i<nrOfBellsToShow)
				bellsVisibility[i] = ImageView.VISIBLE;
			else
				bellsVisibility[i] = ImageView.INVISIBLE;
		
		((ImageView) findViewById(R.id.bell1)).setVisibility(bellsVisibility[0]);
		((ImageView) findViewById(R.id.bell2)).setVisibility(bellsVisibility[1]);
		((ImageView) findViewById(R.id.bell3)).setVisibility(bellsVisibility[2]);
		((ImageView) findViewById(R.id.bell4)).setVisibility(bellsVisibility[3]);
		((ImageView) findViewById(R.id.bell5)).setVisibility(bellsVisibility[4]);
		((ImageView) findViewById(R.id.bell6)).setVisibility(bellsVisibility[5]);
		((ImageView) findViewById(R.id.bell7)).setVisibility(bellsVisibility[6]);
		
	}
	
	/**
	 * Sets which of the hour glass image to be showed.
	 */
	private void updateHourGlass() {
		int min = Calendar.getInstance().get(Calendar.MINUTE);
		ImageView hourGlass = (ImageView) findViewById(R.id.hour_glass);
		
		if ((min >= 0 && min < 5) || (min >= 30 && min < 35) )
			hourGlass.setImageResource(R.drawable.hour_glass0);
		else if ((min >= 5 && min < 10) || (min >= 35 && min < 40) )
			hourGlass.setImageResource(R.drawable.hour_glass1);
		else if ((min >= 10 && min < 15) || (min >= 40 && min < 45) )
			hourGlass.setImageResource(R.drawable.hour_glass2);
		else if ((min >= 15 && min < 20) || (min >= 45 && min < 50) )
			hourGlass.setImageResource(R.drawable.hour_glass3);
		else if ((min >= 20 && min < 25) || (min >= 50 && min < 55) )
			hourGlass.setImageResource(R.drawable.hour_glass4);
		else
			hourGlass.setImageResource(R.drawable.hour_glass5);

	}
}

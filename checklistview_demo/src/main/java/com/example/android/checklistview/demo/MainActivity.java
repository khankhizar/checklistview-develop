package com.example.android.checklistview.demo;
/*
 * Copyright (C) 2018 Khizar Heyat Khan (khizarkhan8@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.checklistview.interfaces.CheckListChangedListener;
import com.example.android.checklistview.interfaces.Constants;
import com.example.android.checklistview.models.ChecklistManager;
import com.example.android.checklistview.Settings;
import com.example.android.checklistview.exceptions.ViewNotSupportedException;

public class MainActivity extends AppCompatActivity implements CheckListChangedListener {

	View switchView;
	private SharedPreferences prefs;
	boolean isChecklist;
	private ChecklistManager mChecklistManager;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
		
		prefs = PreferenceManager.getDefaultSharedPreferences(this);

		switchView = findViewById(R.id.edittext);
		switchView.setVisibility(View.GONE);
		/*((EditText)switchView).setText(prefs.getString("text", getString(R.string.template_phrase)));*/
		
		if (prefs.getBoolean("isChecklist", false)) {
			isChecklist = false;
			toggleCheckList();
		}

		initTextView();
	}


	private void initTextView() {
		TextView textview = (TextView) findViewById(R.id.bottom_banner);
		textview.setVisibility(View.GONE);
		/*textview.setText(Html.fromHtml(getString(R.string.omninotes)));
		textview.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.omninotes_link))));
			}
		});*/
	}


	@Override
	protected void onResume() {
		super.onResume();
		if (prefs.getBoolean("refresh", false)) {
			if (!isChecklist) {
				toggleCheckList();
			}
			prefs.edit().putBoolean("refresh", false).apply();
		}
	}
	
	
	@Override
	protected void onPause() {
		save();
		super.onPause();
	}
	
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_toggle_checklist:
				toggleCheckList();
				break;

			case R.id.settings:
				Intent settingsIntent = new Intent(this, SettingsActivity.class);
				startActivity(settingsIntent);
				break;
		}
		return super.onOptionsItemSelected(item);
	}


	private void toggleCheckList() {
		View newView;

		/*
		 * Here is where the job is done. By simply calling an instance of the
		 * ChecklistManager we can call its methods.
		 */
		try {
			// Getting instance
			mChecklistManager = mChecklistManager == null ? new ChecklistManager(getApplicationContext()) : mChecklistManager;

			/*
			 * These method are useful when converting from EditText to
			 * ChecklistView (but can be set anytime, they'll be used at
			 * appropriate moment)
			 */

			// Setting new entries hint text (if not set no hint
			// will be used)
			mChecklistManager.newEntryHint(prefs.getString("settings_hint", ""));
			// Let checked items are moved on bottom
			
			mChecklistManager.moveCheckedOnBottom(Integer.valueOf(prefs.getString("settings_checked_items_behavior",
					String.valueOf(Settings.CHECKED_HOLD))));
			
			// Is also possible to set a general changes listener
			mChecklistManager.setCheckListChangedListener(this);

			
			/*
			 * These method are useful when converting from ChecklistView to
			 * EditText (but can be set anytime, they'll be used at appropriate
			 * moment)
			 */

			// Decide if keep or remove checked items when converting
			// back to simple text from checklist
			mChecklistManager.linesSeparator(prefs.getString("settings_lines_separator", Constants.LINES_SEPARATOR));
			
			// Decide if keep or remove checked items when converting
			// back to simple text from checklist
			mChecklistManager.keepChecked(prefs.getBoolean("settings_keep_checked", Constants.KEEP_CHECKED));
			
			// I want to make checks symbols visible when converting
			// back to simple text from checklist
			mChecklistManager.showCheckMarks(prefs.getBoolean("settings_show_checks", Constants.SHOW_CHECKS));

			// Enable or disable drag & drop
			mChecklistManager.dragEnabled(true);
			mChecklistManager.dragVibrationEnabled(true);
			
			// Converting actual EditText into a View that can
			// replace the source or viceversa
			newView = mChecklistManager.convert(switchView);
			
			// Replacing view in the layout
			mChecklistManager.replaceViews(switchView, newView);
			
			// Updating the instance of the pointed view for
			// eventual reverse conversion
			switchView = newView;
						
			isChecklist = !isChecklist;
		
		} catch (ViewNotSupportedException e) {
			// This exception is fired if the source view class is not supported
			e.printStackTrace();
		}
	}
	

	private void save(){
		String text = "";
		if (isChecklist) {
			try {
				text = ((EditText)mChecklistManager.convert(switchView)).getText().toString();
			} catch (ViewNotSupportedException e) {
				e.printStackTrace();
			}
		} else {
			text = ((EditText)switchView).getText().toString();
		}
		prefs.edit()
			.putString("text", text)
			.putBoolean("isChecklist", isChecklist)
			.apply();
	}


	@Override
	public void onCheckListChanged() {
		Log.v(Constants.TAG, "Some text is changed!!");
	}
}

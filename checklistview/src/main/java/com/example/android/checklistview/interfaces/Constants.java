package com.example.android.checklistview.interfaces;
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
public interface Constants {
	String TAG = "CheckListView";

	/**
	 * Default line separator to parse simple text into checklist items
	 */
	String LINES_SEPARATOR = System.getProperty("line.separator");
	/**
	 * Show or not delete icon on items
	 */
	boolean SHOW_DELETE_ICON = true;
	/**
	 * Show or not an empty item on bottom of the list
	 */
	boolean SHOW_HINT_ITEM = false;
	/**
	 * Keep cheched items when converting back to simple text. Otherwise they
	 * will be removed.
	 */
	boolean KEEP_CHECKED = true;
	/**
	 * Shows or not checks when converting back to simple text
	 */
	boolean SHOW_CHECKS = false;
	/**
	 * Default value for dragging feature
	 */
	boolean DRAG_ENABLED = true;

	String UNCHECKED_SYM = "[ ] ";
	String CHECKED_SYM = "[x] ";
    String UNCHECKED_ENTITY = "&EmptySmallSquare; ";
    String CHECKED_ENTITY = "&#x2713; ";

	int DELETE_ITEM_DELAY = 350;
	
	// Views tags
	String TAG_LIST = "checklistview_list";
	String TAG_ITEM = "checklistview_item_bak";
	String TAG_EDITTEXT = "checklistview_edittext";

	// Drag and drop parameters
	boolean DRAG_VIBRATION_ENABLED = false;
	int DRAG_VIBRATION_DURATION = 25;
	int SCROLLING_DELAY = 10;
	int SCROLLING_STEP = 5;
	int SCROLLING_THREESHOLD = 100;
}

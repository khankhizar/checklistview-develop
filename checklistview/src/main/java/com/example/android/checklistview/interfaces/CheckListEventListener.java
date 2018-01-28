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
import com.example.android.checklistview.models.CheckListViewItem;
import android.view.KeyEvent;

public interface CheckListEventListener {
	/**
	 * This is called when an item checkbox is checked.
	 * @param checkableLine
	 */
	void onItemChecked(CheckListViewItem checkableLine, boolean isChecked);
	/**
	 * This is called when the checklist item is edited
	 * @param checkableLine
	 */
	void onNewLineItemEdited(CheckListViewItem checkableLine);
	/**
	 * This is called when the ime action is performed (ex. next, done...)
	 * @param checkableLine
	 */
	void onEditorActionPerformed(CheckListViewItem checkableLine, int actionId, KeyEvent event);
	/**
	 * This is called when the ime action is performed (ex. next, done...)
	 * @param checkableLine
	 */
	void onLineDeleted(CheckListViewItem checkableLine);
}

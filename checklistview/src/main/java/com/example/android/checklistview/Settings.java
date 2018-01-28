package com.example.android.checklistview;
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
import com.example.android.checklistview.interfaces.Constants;

public class Settings {

	/**
	 * Checked items behavior: hold on place.
	 */
	public static final int CHECKED_HOLD = 0;
	/**
	 * Checked items behavior: move on bottom of list.
	 */
	public static final int CHECKED_ON_BOTTOM = 1;
	/**
	 * Checked items behavior: move on bottom of unchecked but on top of checked.
	 */
	public static final int CHECKED_ON_TOP_OF_CHECKED = 2;

	private String linesSeparator = Constants.LINES_SEPARATOR;
	private boolean showDeleteIcon = Constants.SHOW_DELETE_ICON;
	private boolean keepChecked = Constants.KEEP_CHECKED;
	private boolean showChecks = Constants.SHOW_CHECKS;
	private boolean showHintItem = Constants.SHOW_HINT_ITEM;
	private String newEntryHint = "";
	private int moveCheckedOnBottom = CHECKED_HOLD;
	private boolean dragEnabled = Constants.DRAG_ENABLED;
	private boolean dragVibrationEnabled = Constants.DRAG_VIBRATION_ENABLED;
	private int dragVibrationDuration = Constants.DRAG_VIBRATION_DURATION;


	public String getLinesSeparator() {
		return linesSeparator;
	}


	public void setLinesSeparator(String linesSeparator) {
		this.linesSeparator = linesSeparator;
	}


	public boolean getShowDeleteIcon() {
		return showDeleteIcon;
	}


	public void setShowDeleteIcon(boolean showDeleteIcon) {
		this.showDeleteIcon = showDeleteIcon;
	}


	public boolean getKeepChecked() {
		return keepChecked;
	}


	public void setKeepChecked(boolean keepChecked) {
		this.keepChecked = keepChecked;
	}


	public boolean getShowChecks() {
		return showChecks;
	}


	public void setShowChecks(boolean showChecks) {
		this.showChecks = showChecks;
	}


	public boolean getShowHintItem() {
		return showHintItem;
	}


	public void setShowHintItem(boolean showHintItem) {
		this.showHintItem = showHintItem;
	}


	public String getNewEntryHint() {
		return newEntryHint;
	}


	public void setNewEntryHint(String newEntryHint) {
		this.newEntryHint = newEntryHint;
	}


	public int getMoveCheckedOnBottom() {
		return moveCheckedOnBottom;
	}


	public void setMoveCheckedOnBottom(int moveCheckedOnBottom) {
		this.moveCheckedOnBottom = moveCheckedOnBottom;
	}


	public boolean getDragEnabled() {
		return dragEnabled;
	}


	public void setDragEnabled(boolean dragEnabled) {
		this.dragEnabled = dragEnabled;
	}


	public boolean getDragVibrationEnabled() {
		return dragVibrationEnabled;
	}


	public void setDragVibrationEnabled(boolean dragVibrationEnabled) {
		this.dragVibrationEnabled = dragVibrationEnabled;
	}


	public int getDragVibrationDuration() {
		return dragVibrationDuration;
	}


	public void setDragVibrationDuration(int dragVibrationDuration) {
		this.dragVibrationDuration = dragVibrationDuration;
	}

}

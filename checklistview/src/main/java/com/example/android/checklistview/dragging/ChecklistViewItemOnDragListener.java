package com.example.android.checklistview.dragging;
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
import android.animation.LayoutTransition;
import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.example.android.checklistview.App;
import com.example.android.checklistview.interfaces.Constants;
import com.example.android.checklistview.utils.DensityUtil;
import com.example.android.checklistview.Settings;
import com.example.android.checklistview.models.CheckListViewItem;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ChecklistViewItemOnDragListener implements OnDragListener {

	private static final String TAG = ChecklistViewItemOnDragListener.class.getSimpleName();

	private static final int DIRECTION_UP = 0;
	private static final int DIRECTION_DOWN = 1;

	private int dragDirection;
	private float y;
	private Thread scrollerThread;
	private boolean scroll = false;
	private ScrollView scrollView;
	private LayoutTransition containerLayoutTransition;


	public boolean onDrag(View target, DragEvent event) {
		int action = event.getAction();
		final View dragged = (View) ((View) event.getLocalState()).getParent();
		scrollView = (ScrollView) getScrollableAncestor(dragged);
		Log.v(TAG, "Dragging item. Action: " + action);
		switch (action) {
			case DragEvent.ACTION_DRAG_STARTED:
				return actionDragStarted(event, dragged);
			case DragEvent.ACTION_DRAG_ENTERED:
				return actionDragEntered(target, dragged);
			case DragEvent.ACTION_DRAG_EXITED:
				return actionDragExited(target, dragged);
			case DragEvent.ACTION_DRAG_LOCATION:
				return actionDragLocation(target, event);
			case DragEvent.ACTION_DROP:
				return actionDrop(dragged);
			default:
				return true;
		}
	}


	private boolean actionDrop(View dragged) {
		((ViewGroup) dragged.getParent()).setLayoutTransition(containerLayoutTransition);
		stopScrolling();
		dragged.setVisibility(View.VISIBLE);
		return true;
	}


	private boolean actionDragLocation(View target, DragEvent event) {
		// Control demanded to the container to scroll
		if (checkTag(target, Constants.TAG_LIST)) {
			y = event.getY();
			int scroll = getScroll(scrollView, target);
			if (y - scroll < Constants.SCROLLING_THREESHOLD) {
				dragDirection = DIRECTION_UP;
				startScrolling(target);
			} else if (scrollView.getHeight() - (y - scroll) < Constants.SCROLLING_THREESHOLD) {
				dragDirection = DIRECTION_DOWN;
				startScrolling(target);
			} else {
				stopScrolling();
			}
			return true;
		} else {
			return false;
		}
	}


	private boolean actionDragExited(View target, View dragged) {
		if (checkTag(target, Constants.TAG_LIST)) {
			stopScrolling();
		}
		if (target.equals(dragged.getParent())) dragged.setVisibility(View.VISIBLE);
		return true;
	}


	private boolean actionDragEntered(View target, View dragged) {
		if (targetCanAcceptDrop(dragged, target)) {
			stopScrolling();
			dragged.setVisibility(View.INVISIBLE);
			moveView(target, dragged);
		}
		return true;
	}


	public void moveView(View target, View dragged) {
		ViewGroup container = (ViewGroup) dragged.getParent();
		int index = container.indexOfChild(target);
		container.removeView(dragged);
		container.addView(dragged, index);
	}


	private boolean actionDragStarted(DragEvent event, View dragged) {
		ViewGroup container = (ViewGroup) dragged.getParent();
		if (container.getLayoutTransition() != null) {
			containerLayoutTransition = container.getLayoutTransition();
			container.setLayoutTransition(null);
		}
		return true;
	}


	private int getScroll(ScrollView scrollView, View target) {
		int scroll = 0;
		scroll += scrollView.getScrollY();
		int sum = 0;
		View child = target;
		ViewGroup parent;
		do {
			parent = (ViewGroup) child.getParent();
			int index = parent.indexOfChild(child);
			for (int i = 0; i < index; i++) {
				sum += parent.getChildAt(i).getHeight();
			}
			child = parent;
		} while (!parent.equals(scrollView));
		return scroll - sum;
	}


	private void startScrolling(View target) {
		if (!scroll) {
			scrollerThread = new Thread(new Scroller(target));
			scroll = true;
			scrollerThread.start();
		}
	}


	private void stopScrolling() {
		if (scroll) {
			scroll = false;
			if (scrollerThread != null && scrollerThread.isAlive()) {
				scrollerThread.interrupt();
			}
		}
	}

	class Scroller implements Runnable {
		View target;

		public Scroller(View target) {
			this.target = target;
		}

		@Override
		public void run() {
			int scrollStep = dragDirection == DIRECTION_UP ? -Constants.SCROLLING_STEP : Constants.SCROLLING_STEP;
			while (scroll) {
				scrollView.smoothScrollBy(0, DensityUtil.dpToPx(scrollStep, scrollView.getContext()));
				try {
					Thread.sleep(Constants.SCROLLING_DELAY);
				} catch (InterruptedException e) {
					Log.d(TAG, "InterruptedException");
				}
			}
		}
	}


	private View getScrollableAncestor(View dragged) {
		View parent = (View) dragged.getParent();
		while (parent != null) {
			if (parent.getClass().isAssignableFrom(ScrollView.class)) {
				break;
			} else {
				parent = (View) parent.getParent();
			}
		}
		return parent;
	}


	private boolean targetCanAcceptDrop(View dragged, View target) {
		if (checkTag(target, Constants.TAG_ITEM) && checkTag(dragged, Constants.TAG_ITEM)) {
			CheckListViewItem draggedItem = (CheckListViewItem) dragged;
			CheckListViewItem targetItem = (CheckListViewItem) target;
			if (App.getSettings().getMoveCheckedOnBottom() == Settings.CHECKED_HOLD
					|| draggedItem.isChecked() == targetItem.isChecked()) {
				return true;
			}
		}
		return false;
	}


	private boolean checkTag(View view, Object tag) {
		return view.getTag() != null && view.getTag().equals(tag);
	}
}

package com.htmitech.emportal.ui.chart.listener;

import com.htmitech.emportal.ui.chart.view.ObservableScrollView;

public interface ScrollViewListener {
	void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy);
}

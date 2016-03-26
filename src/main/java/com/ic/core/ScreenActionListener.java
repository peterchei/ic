package com.ic.core;

public interface ScreenActionListener {
	void OnZoomCompleted(Object actionChartScreen, int startIndex, int endIndex);

	void OnWatch(Object actionChartScreen, int watchPoint);
}
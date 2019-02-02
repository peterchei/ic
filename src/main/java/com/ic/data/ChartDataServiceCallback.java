package com.ic.data;


public interface ChartDataServiceCallback {
    void OnReceivedChartData(RequestCommand fc, Object obj);

    void OnReceivedError(RequestCommand fc);

    void OnProgress(int percent);
}
package com.ic.data;


public interface ChartDataServiceCallback {
    public void OnReceivedChartData(RequestCommand fc, Object obj);

    public void OnReceivedError(RequestCommand fc);

    public void OnProgress(int percent);
}
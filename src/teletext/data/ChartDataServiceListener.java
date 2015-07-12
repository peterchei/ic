package teletext.data;


public interface ChartDataServiceListener {
    //public void OnReceivedChartData(FCommand fc, ChartData chartData);
    public void OnReceivedChartData(FCommand fc, Object obj);
    public void OnReceivedError(FCommand fc);
    public void OnProgress(int percent);
}
/**
 * Title:        FME Chart Project for E-finet<p>
 * Description:  <p>
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author
 * @version 1.0
*/

package fmechart;

public interface ChartSourceListener
{
  public void OnReceivedChartData(FCommand fc, ChartData chartData);
  public void OnReceivedError(FCommand fc);
  //  public OnChartLoad
//  public OnError()
}
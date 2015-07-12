
/**
 * Title:        FME Chart Project for E-finet<p>
 * Description:  <p>
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author
 * @version 1.0
 */
package fmechart;

import java.awt.Panel;
import java.awt.Graphics;
import java.awt.*;

public class FBar extends Panel
{
  public void paint(Graphics g)
  {
    super.paint(g);
    drawBarLine(g,1);
    drawBarLine(g,4);

  }

  public void drawBarLine(Graphics g, int x)
  {
    int ySpace = 2;
    g.setColor(Color.white);
    g.drawLine(x,ySpace,x,getSize().height-2*ySpace+2);
    g.drawLine(x,ySpace+1,x,ySpace+1);
    g.setColor(new Color(212,208,200));
    g.drawLine(x+1,ySpace+1,x+1,getSize().height-2*ySpace+1);
    g.setColor(new Color(128,128,128));
    g.drawLine(x+2,ySpace,x+2,getSize().height-2*ySpace+1);
    g.drawLine(x,getSize().height-2*ySpace+2,x+2,getSize().height-2*ySpace+2);
  }

  public FBar()
  {
  }
}
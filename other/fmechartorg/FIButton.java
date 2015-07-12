
/**
 * Title:        FME Chart Project for E-finet<p>
 * Description:  <p>
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author
 * @version 1.0
 */
package fmechart;

import java.awt.*;
import java.awt.event.*;

public class FIButton extends Button
{
  public Image offImage = null;


  public void paint(Graphics g)
  {
    //super.paint(g);
    //System.out.println("Button repaint()");

    if (offImage!=null)
    {

      if (getSize().width >7 && getSize().height>7)

      g.drawImage(offImage,2,2,getSize().width-4,getSize().height-4,this);
     // this.imageUpdate(offImage,1,2,2,getSize().width-4,getSize().height-4);



     // super.setLabel("Y");
    }
    else
    {
      //super.setLabel("X");
      super.paint(g);
    }
  }
  public void update(Graphics g)
  {
    paint(g);
  }
  public void setLabel(String str)
  {
  }
  public FIButton()
  {
  }
}
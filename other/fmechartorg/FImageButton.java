package fmechart;

import java.awt.Panel;

/**
 * Title:        FME Chart Project for E-finet
 * Description:
 * Copyright:    Copyright (c)
 * Company:
 * @author
 * @version 1.0
 */

import java.util.EventListener;
import java.awt.*;
import java.awt.event.*;

public class FImageButton extends java.awt.Component  implements MouseListener
{
  public Image offImage = null;
  private ActionListener actionListener = null;
  private boolean bState = true;
  private boolean action = false;

  public FImageButton()
  {
    this.addMouseListener(this);
  }

  public void addActionListener(ActionListener  al)
  {
    actionListener = al;
  }

  public void paint(Graphics g)
  {
    g.setColor(Color.gray);
    g.draw3DRect(0,0,getSize().width,getSize().height,bState);
    g.drawRect(1,1,getSize().width-3,getSize().height-3);
    g.drawRect(2,2,getSize().width-2,getSize().height-2);
   // g.draw3DRect(1,1,getSize().width-2,getSize().height-2,bState);
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
  if (actionListener!=null && action == true)
  {
    action = false;
    actionListener.actionPerformed(new ActionEvent(this,0,"HELLO"));
  }
 }


  public void update(Graphics g)
  {
    paint(g);
  }

  public void setLabel(String str)
  {
  }

    /**
   * Invoked when the mouse has been clicked on a component.
   */
  public void mouseClicked(MouseEvent e)
  {
  //System.out.println(":)");
  }

    /**
     * Invoked when a mouse button has been pressed on a component.
     */
    public void mousePressed(MouseEvent e){
    bState = false;
    repaint();
    }

    /**
     * Invoked when a mouse button has been released on a component.
     */
    public void mouseReleased(MouseEvent e){
    bState = true;
    action = true;
    repaint();

    }

    /**
     * Invoked when the mouse enters a component.
     */
    public void mouseEntered(MouseEvent e){}

    /**
     * Invoked when the mouse exits a component.
     */
    public void mouseExited(MouseEvent e){
    bState = true;
    repaint();
    }



}
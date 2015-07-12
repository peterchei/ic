/**
 * Title:        <p>
 * Description:  <p>
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author
 * @version 1.0
 */
package fme;

import java.awt.*;

public class FLabel extends Label {

    public Color spFgColor = Color.blue;
    public Color orgFgColor = Color.black;
    public Color orgBgColor = Color.white;
    public Color spBgColor = Color.white;

    public FLabel() {
        super();

    }

    public FLabel(String aaa) {
        super(aaa);
    }


///////////////////////////////////////////////////////////////////////////////////////////
    public void osetText(String tempString) {
        super.setText(tempString);
    }

    public void setText(String tempString, Color bgcolor, Color fgcolor) {
        super.setForeground(fgcolor);
        super.setBackground(bgcolor);
        super.setText(tempString);
        //System.out.println("---------");
        //super.repaint();

    }

    public void setText(String tempString) {

        if (tempString.equals(super.getText())) {
            super.setForeground(this.orgFgColor);
            super.setBackground(this.orgBgColor);
            return;
        } else {
            //  System.out.println("Use sp Color");
            super.setForeground(this.spFgColor);
            super.setBackground(this.spBgColor);
            super.setText(tempString);
            return;
        }
    }


//////////////////////////////////////////////////////////////////////////////////////////

}
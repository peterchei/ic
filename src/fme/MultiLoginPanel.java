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

public class MultiLoginPanel extends Panel {
    Label label1 = new Label();

    public MultiLoginPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        label1.setBounds(new Rectangle(131, 108, 273, 72));
        label1.setFont(new java.awt.Font("Dialog", 1, 20));
        label1.setAlignment(1);
        label1.setText("Multiple Login Detected");
        this.setBackground(new Color(255, 255, 216));
        this.setLayout(null);
        this.add(label1, null);

    }
}
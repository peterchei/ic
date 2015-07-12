package pfinance.client.apps.ui;

import javax.swing.*;
import java.awt.*;


public class TestPanel extends JPanel {
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();

    public TestPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        jTabbedPane1.setEnabled(true);
        jTabbedPane1.setFont(new java.awt.Font("Dialog", 0, 8));
        jTabbedPane1.setBorder(null);
        jTabbedPane1.setDoubleBuffered(false);
        this.setLayout(borderLayout1);
        this.add(jTabbedPane1, BorderLayout.CENTER);
        jTabbedPane1.add(jPanel1, "jPanel1");
        jTabbedPane1.add(jPanel2, "jPanel2");
    }
}

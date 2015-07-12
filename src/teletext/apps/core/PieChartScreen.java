package teletext.apps.core;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;

public class PieChartScreen extends JPanel {

    public PieChartScreen() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //  The function is used to plot the chart
    public void update(Graphics g) {
        paint(g);
    }

    public synchronized void paint(Graphics g) {

        //      g2.setPaint(red);
        Graphics2D g2 = (Graphics2D) g;

        double cWidth = (this.getHeight() > this.getWidth())?getWidth():getHeight();


        g2.setColor(this.getBackground());
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        g2.setColor(Color.blue);

        g2.fill(new Arc2D.Double(0, 0,
                cWidth, cWidth,
                0, 112,
                Arc2D.PIE));

        g2.setColor(Color.red);
        g2.fill(new Arc2D.Double(0, 0,
                cWidth, cWidth,
                112, 248,
                Arc2D.PIE));


        g2.setColor(Color.black);
        g2.draw(new Arc2D.Double(1, 1,
                cWidth - 2, cWidth - 2,
                0, 360,
                Arc2D.OPEN));


    }

    private void jbInit() throws Exception {
        this.setForeground(Color.white);
    }

}

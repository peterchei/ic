package com.ic.gui;

import javax.swing.*;
import java.awt.*;

public class TabBar extends JPanel {


    public TabBar() {

    }

    public void paint(Graphics g) {
        super.paint(g);
        drawBarLine(g, 1);
        //drawBarLine(g, 4);

    }

    private void drawBarLine(Graphics g, int x) {
        int ySpace = 2;
        g.setColor(Color.white);
        g.drawLine(x, ySpace, x, getSize().height - 2 * ySpace + 2);
        g.drawLine(x, ySpace + 1, x, ySpace + 1);
        g.setColor(new Color(212, 208, 200));
        g.drawLine(x + 1, ySpace + 1, x + 1, getSize().height - 2 * ySpace + 1);
        g.setColor(new Color(128, 128, 128));
        g.drawLine(x + 2, ySpace, x + 2, getSize().height - 2 * ySpace + 1);
        g.drawLine(x, getSize().height - 2 * ySpace + 2, x + 2, getSize().height - 2 * ySpace + 2);
    }
}

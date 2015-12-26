package com.ic.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ImageButton extends JButton implements MouseListener {


    private static final long serialVersionUID = -8202597524535553406L;
    Border border1;
    private Image offImage = null;
    private boolean bState = true;
    private boolean action = false;

    public ImageButton() {
        this.addMouseListener(this);
    }

    public void setButtonImage(Image image) {
        offImage = image;
    }


    public void addActionListener(ActionListener al) {
        actionListener = al;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (!bState) {
            g.setColor(Color.black);
        } else {
            g.setColor(Color.gray);
        }

        g.draw3DRect(0, 0, getSize().width, getSize().height, bState);
        g.drawRect(1, 1, getSize().width - 3, getSize().height - 3);
        g.drawRect(2, 2, getSize().width - 2, getSize().height - 2);
        if (offImage != null) {
            if (getSize().width > 7 && getSize().height > 7) {
                g.drawImage(offImage, 2, 2, getSize().width - 4, getSize().height - 4, this);
            }
        } else {
            super.paint(g);
        }

        if (actionListener != null && action == true) {
            action = false;
            actionListener.actionPerformed(new ActionEvent(this, 0, "ImageButton"));
        }

    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
   }

    public void mousePressed(MouseEvent e) {
        bState = false;
        repaint();
    }

    public void mouseReleased(MouseEvent e) {
        bState = true;
        action = true;
        repaint();
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
        bState = true;
        repaint();
    }
}

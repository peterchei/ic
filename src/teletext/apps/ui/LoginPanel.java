package teletext.apps.ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginPanel extends JPanel {
    public Image offImage = null;
    JLabel lbLogin = new JLabel();
    JLabel lbPasswd = new JLabel();
    JPanel jPanel1 = new JPanel();
    Border border1;
    GridLayout gridLayout1 = new GridLayout(3, 2);
    Border border2;
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    FlowLayout flowLayout1 = new FlowLayout(FlowLayout.RIGHT);
    FlowLayout flowLayout2 = new FlowLayout(FlowLayout.RIGHT);
    JTextField jTextField1 = new JTextField();
    JTextField jTextField2 = new JTextField();
    JPanel jPanel4 = new JPanel();
    Border border3;
    public JButton jbLogin = new JButton();
    JButton jbReset = new JButton();
    Border border4;
    Border border5;
    Border border6;
    Border border7;

    public LoginPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean isValidUser() {
       if ("peterchei".equals(this.jTextField1.getText())) return true;
       
       return false;
    }

    private void jbInit() throws Exception {
        border1 = BorderFactory.createEmptyBorder(20, 0, 20, 0);
        border2 = BorderFactory.createLineBorder(Color.black, 2);
        border3 = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black, 1), BorderFactory.createEmptyBorder(0, 5, 0, 5));
        border4 = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black, 1), BorderFactory.createEmptyBorder(0, 5, 0, 5));
        border5 = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black, 1), BorderFactory.createEmptyBorder(0, 5, 0, 5));
        border6 = BorderFactory.createLineBorder(Color.black, 1);
        border7 = BorderFactory.createLineBorder(Color.black, 2);
        this.setBackground(Color.lightGray);
        this.setBorder(border7);
        this.setMinimumSize(new Dimension(105, 30));
        this.setLayout(null);
        lbLogin.setFont(new java.awt.Font("Arial", 0, 11));
        lbLogin.setText("Login");
        lbPasswd.setBackground(Color.white);
        lbPasswd.setFont(new java.awt.Font("Arial", 0, 11));
        lbPasswd.setText("Password");
        jPanel1.setLayout(gridLayout1);
        jPanel1.setBackground(Color.white);
        jPanel1.setBorder(border6);
        jPanel1.setBounds(new Rectangle(41, 105, 184, 77));
        jPanel3.setLayout(flowLayout1);
        jPanel3.setBackground(Color.orange);
        jPanel3.setMaximumSize(new Dimension(32767, 32767));
        jPanel2.setBackground(Color.orange);
        jPanel2.setAlignmentX((float) 0.5);
        jPanel2.setLayout(flowLayout2);
        jTextField1.setFont(new java.awt.Font("Arial", 0, 11));
        jTextField1.setBorder(BorderFactory.createLineBorder(Color.black));
        jTextField1.setPreferredSize(new Dimension(82, 19));
        jTextField1.setRequestFocusEnabled(true);
        jTextField1.setCaretPosition(0);
        jTextField1.setText("");
        jTextField1.setColumns(10);
        jTextField2.setFont(new java.awt.Font("Arial", 0, 11));
        jTextField2.setBorder(BorderFactory.createLineBorder(Color.black));
        jTextField2.setCaretPosition(0);
        jTextField2.setText("");
        jTextField2.setColumns(10);
        jbLogin.setBackground(Color.lightGray);
        jbLogin.setBorder(border3);
        jbLogin.setText("Login");
        jPanel4.setBackground(Color.orange);
        jbLogin.setText("Login");
        jbLogin.setBorder(border5);
        jbLogin.setBackground(Color.lightGray);
        jbLogin.setFont(new java.awt.Font("Arial", 0, 11));
        jbReset.setBackground(Color.lightGray);
        jbReset.setFont(new java.awt.Font("Arial", 0, 11));
        jbReset.setBorder(border4);
        jbReset.setText("Reset");
        jPanel3.add(lbPasswd, null);
        jPanel3.add(jTextField2, null);
        jPanel1.add(jPanel2, null);
        jPanel1.add(jPanel3, null);
        jPanel2.add(lbLogin, null);
        jPanel2.add(jTextField1, null);
        jPanel4.add(jbLogin, null);
        jPanel4.add(jbReset, null);
        jPanel1.add(jPanel4, null);
        this.add(jPanel1, null);
    }

    public void paint(Graphics g) {
        if (offImage != null) {
            super.paint(g);
            g.drawImage(offImage, 0, 0, getSize().width, getSize().height, this);
            g.drawRect(0, 0, getSize().width - 1, getSize().height - 1);
            jPanel1.repaint();

        } else {
            super.paint(g);
        }
    }

    void jbLogin_actionPerformed(ActionEvent e) {

    }

    void jbReset_actionPerformed(ActionEvent e) {

    }
}




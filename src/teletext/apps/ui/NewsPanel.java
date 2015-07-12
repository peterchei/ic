package teletext.apps.ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;


public class NewsPanel extends JPanel {
    JTextArea jTextArea1 = new JTextArea();
    BorderLayout borderLayout1 = new BorderLayout();
    Border border1;
    Border border2;
    JScrollPane jScrollPane1 = new JScrollPane();
    JList jList1 = new JList(new String[]
    {"26Mar04 HSI Hong Kong stocks end up as Taiwan investors move in ",
     "26Mar04 HSI GLOBAL MARKETS-Asian share slide steepens on security concerns ",
     "26Mar04 HSI HK stocks hurt by poor SMIC debut, China Life lawsuit ",
     "26Mar04 HSI GLOBAL MARKETS-Asian shares tread warily amid security fears ",
     "26Mar04 HSI GLOBAL MARKETS-Asian stocks bounce back, euro wobbles ",
     "26Mar04 HSI HK stocks fall on Spanish blasts, China plays shine ",
     "26Mar04 HSI GLOBAL MARKETS-Asian stocks fall, rattled by security fears",
     "26Mar04 HSI GLOBAL MARKETS-Asian stocks fall further, tracking U.S. drop",
     "26Mar04 HSI GLOBAL MARKETS-Asian shares follow U.S. lower, dollar firm",
     "26Mar04 HSI GLOBAL MARKETS-Asian tech stocks fall sharply, dlr steadies"});

    Border border3;
    Border border4;
    JScrollPane jScrollPane2 = new JScrollPane();

    public NewsPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        border2 = BorderFactory.createCompoundBorder(new EtchedBorder(EtchedBorder.RAISED, Color.lightGray, new Color(178, 178, 178)), BorderFactory.createEmptyBorder(5, 5, 5, 5));
        border3 = BorderFactory.createLineBorder(SystemColor.textInactiveText, 1);
        border4 = BorderFactory.createMatteBorder(10, 10, 10, 10, Color.lightGray);
        jTextArea1.setBackground(UIManager.getColor("CheckBox.background"));
        jTextArea1.setEnabled(true);
        jTextArea1.setBorder(border2);
        jTextArea1.setDebugGraphicsOptions(0);
        jTextArea1.setPreferredSize(new Dimension(12, 30));
        jTextArea1.setRequestFocusEnabled(true);
        jTextArea1.setDisabledTextColor(Color.orange);
//    jTextArea1.setCaretPosition(12);
        jTextArea1.setColumns(0);
        jTextArea1.setRows(0);
        jTextArea1.setTabSize(8);
        this.setBackground(new Color(234, 181, 21));
        this.setToolTipText("");
        this.setLayout(borderLayout1);
        jList1.setBackground(Color.white);
        jList1.setBorder(border3);
        jList1.setRequestFocusEnabled(true);
        jList1.setToolTipText("");
        jList1.setSelectionBackground(SystemColor.activeCaptionBorder);
        jList1.setSelectionForeground(Color.black);
        jList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jList1.setVisibleRowCount(5);
        jScrollPane1.getViewport().setBackground(Color.white);
        jScrollPane1.setAlignmentY((float) 0.5);
        jScrollPane1.setBorder(border4);
        jScrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.add(jScrollPane1, BorderLayout.NORTH);
        jScrollPane1.getViewport().add(jList1, null);
        this.add(jScrollPane2, BorderLayout.CENTER);
        jScrollPane2.getViewport().add(jTextArea1, null);


    }
}

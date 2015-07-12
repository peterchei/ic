/***
 * Title:        <p>
 * Description:  <p>
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author
 * @version 1.0
 */
package fme;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


public class CommentaryPanel extends Panel implements ItemListener {

    private final String lbArray[][] = {{"Finet Commentary Express", "\u5373\u5e02\u8a55\u8ad6"}};
    private int language = 0;

    List listHeader = new List();
    TextArea tnewsBody = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
    Panel upperPanel = new Panel();
    Label lbstTitle = new Label();
    CommentarySource newsSource = null;
    CommentaryEngine commentaryEngine = null;
    FinetExpress finetExpress;

    public CommentaryPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SetMainApplet(FinetExpress fe) throws Exception {
        this.finetExpress = fe;
    }

    public void SetSource(CommentarySource ns) throws Exception {
        newsSource = ns;
    }

    public void SetEngine(CommentaryEngine ne) throws Exception {
        commentaryEngine = ne;
    }

    public void setCaptionBackground(Color c) throws Exception {
        upperPanel.setBackground(c);
        lbstTitle.setBackground(c);
    }

    public void setInfoBackground(Color c) throws Exception {
        this.setBackground(c);
    }

    private void jbInit() throws Exception {
        tnewsBody.setBackground(Color.white);
        tnewsBody.setBounds(new Rectangle(0, 116, 498, 163));
        tnewsBody.setFont(new java.awt.Font("Serif", 0, 11));
        tnewsBody.setEditable(false);
        tnewsBody.setRows(20);
        this.setLayout(null);
        listHeader.setBackground(Color.white);
        listHeader.setBounds(new Rectangle(0, 33, 498, 81));
        listHeader.setFont(new java.awt.Font("Dialog", 0, 11));

        listHeader.addItemListener(this);
        upperPanel.setBackground(Color.black);
        upperPanel.setBounds(new Rectangle(0, 0, 500, 33));
        upperPanel.setLayout(null);
        lbstTitle.setBounds(new Rectangle(0, 9, 268, 24));
        lbstTitle.setFont(new java.awt.Font("Dialog", 1, 20));
        lbstTitle.setForeground(Color.white);
        lbstTitle.setText("Finet Commentary Express");
        this.add(upperPanel, null);
        upperPanel.add(lbstTitle, null);
        this.add(listHeader, null);
        this.add(tnewsBody, null);
    }

    public void UpdateInformation() throws Exception {
        try {
            listHeader.removeAll();
            for (int i = 0; i < newsSource.NumberOfHeaders; i++) {
                String HeaderString = newsSource.newsObjects[newsSource.NumberOfHeaders - i - 1].Date.substring(newsSource.newsObjects[newsSource.NumberOfHeaders - i - 1].Date.indexOf("-") + 1) + " " + newsSource.newsObjects[newsSource.NumberOfHeaders - i - 1].Time + " " + newsSource.newsObjects[newsSource.NumberOfHeaders - i - 1].Header.trim();
                if (HeaderString.length() > 40) {
                    HeaderString = HeaderString.substring(0, 41) + ".....";
                }
                listHeader.add(HeaderString);
            }
        } catch (StringIndexOutOfBoundsException se) {
            System.out.println("StringIndexOutOfBoundsException");
        }

    }

    void button1_actionPerformed(ActionEvent e) {
        try {
            newsSource.newsObjects[newsSource.NumberOfHeaders - 1].ID = "=1";
        } catch (Exception ex) {
            Report("CommentaryPanel " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void itemStateChanged(ItemEvent e) {
        // retrieve the news body.
        try {
            Report("Loading..........");
            int indexL = newsSource.NumberOfHeaders - 1 - listHeader.getSelectedIndex();
            if (newsSource.newsObjects[indexL].Body == "") {
                commentaryEngine.GetBody(newsSource.newsObjects[indexL].ID, indexL);
                tnewsBody.setText(newsSource.newsObjects[indexL].Body);
            }
            this.tnewsBody.setText(newsSource.newsObjects[indexL].Body);
            Report("");
        } catch (Exception ex) {
            Report("CommentaryPanel::itemStateChanged " + ex.getMessage());
        }
    }

    void Report(String rp) {
        finetExpress.showStatus(rp);
    }

    public void SetLanguage(int tlanguage) throws Exception {
        language = tlanguage;

        if (language == FinetExpress.constEnglish) {
            this.lbstTitle.setText(lbArray[0][0]); //,{"Sell Brokers",""}};
        } else if (language == FinetExpress.constChinese) {
            this.lbstTitle.setText(lbArray[0][1]);   //Normial
        }
    }
}
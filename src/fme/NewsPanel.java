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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


public class NewsPanel extends Panel implements ItemListener {
    private final String lbArray[][] = {{"Finet News Express", "\u8ca1\u7d93\u65b0\u805e"}};
    private int language = 0;

    List listHeader = new List();
    TextArea tnewsBody = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
    Panel panel1 = new Panel();
    Label lbstTitle = new Label();
    NewsSource newsSource = null;
    NewsEngine newsEngine = null;
    FinetExpress finetexpress;

    public NewsPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SetSource(NewsSource ns) throws Exception {
        newsSource = ns;
    }

    public void SetMainApplet(FinetExpress fe) throws Exception {
        this.finetexpress = fe;
    }

    public void SetEngine(NewsEngine ne) throws Exception {
        newsEngine = ne;
    }

    public void setCaptionBackground(Color c) throws Exception {
        panel1.setBackground(c);
        lbstTitle.setBackground(c);
    }

    public void setInfoBackground(Color c) throws Exception {
        this.setBackground(c);
    }

    private void jbInit() throws Exception {
        tnewsBody.setBackground(Color.white);
        tnewsBody.setBounds(new Rectangle(0, 116, 498, 162));
        tnewsBody.setFont(new java.awt.Font("Serif", 0, 12));
        tnewsBody.setEditable(false);
        tnewsBody.setRows(20);
        this.setLayout(null);
        listHeader.setBackground(Color.white);
        listHeader.setBounds(new Rectangle(0, 33, 498, 81));
        listHeader.setFont(new java.awt.Font("Dialog", 0, 11));
        /*
        listHeader.addActionListener(new java.awt.event.ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            listHeader_actionPerformed(e);
          }
        });
        */
        listHeader.addItemListener(this);


        panel1.setBackground(Color.black);
        panel1.setBounds(new Rectangle(0, 0, 500, 33));
        panel1.setLayout(null);
        lbstTitle.setBounds(new Rectangle(0, 9, 218, 24));
        lbstTitle.setFont(new java.awt.Font("Dialog", 1, 20));
        lbstTitle.setForeground(Color.white);
        lbstTitle.setText("Finet News Express");

        this.add(panel1, null);

        panel1.add(lbstTitle, null);
        this.add(listHeader, null);
        this.add(tnewsBody, null);
    }

    public void UpdateInformation() throws Exception {
        try {
            listHeader.removeAll();
            for (int i = 0; i < newsSource.NumberOfHeaders; i++) {
                String HeaderDate = newsSource.newsObjects[newsSource.NumberOfHeaders - i - 1].Date.substring(0, newsSource.newsObjects[newsSource.NumberOfHeaders - i - 1].Date.lastIndexOf("-"));
                String HeaderString = HeaderDate.trim() + " " + newsSource.newsObjects[newsSource.NumberOfHeaders - i - 1].Time.trim() + " " + newsSource.newsObjects[newsSource.NumberOfHeaders - i - 1].Header;
                listHeader.add(HeaderString, newsSource.newsObjects[newsSource.NumberOfHeaders - i - 1].ID);
            }
        } catch (StringIndexOutOfBoundsException se) {
            System.out.println("StringIndexOutOfBoundsException");
        }
    }

    void Report(String rp) {
        finetexpress.showStatus(rp);
    }


    //do action when the user click a headline
    public void itemStateChanged(ItemEvent e) {
        try {
            //System.out.println(listHeader.getSelectedIndex());
            // retrieve the news body.
            Report("News Loading.................");
            int indexL = newsSource.NumberOfHeaders - 1 - listHeader.getSelectedIndex();
            if (newsSource.newsObjects[indexL].Body == "") {
                newsEngine.GetBody(newsSource.newsObjects[indexL].ID, indexL);
                tnewsBody.setText(newsSource.newsObjects[indexL].Body);
            }
            this.tnewsBody.setText(newsSource.newsObjects[indexL].Body);
            Report("News Done.");
        } catch (Exception ex) {
            Report("NewPanel::itemStateChanged " + ex.getMessage());
        }

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
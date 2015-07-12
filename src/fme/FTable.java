/**
 * Title:
 * <p>
 * Description:
 * <p>
 * Copyright: Copyright (c)
 * <p>
 * Company:
 * <p>
 * @author @version 1.0
 */
package fme;

import java.awt.*;

public class FTable
        extends Panel {

    //The item of each cell.

    public FLabel lbItem[][];
    public Color borderColor = Color.black;

    public int XCells, YCells; // there are XCells * YCells

    public void setFontColor(Color orgColor, Color spColor) {
        for (int i = 0; i < XCells; i++) {
            for (int j = 0; j < YCells; j++) {
                lbItem[i][j].spFgColor = spColor;
                lbItem[i][j].orgFgColor = orgColor;
            }
        }
    }

    public FTable(int cellx, int celly, int width[], int height, String sfont,
            int ft, int fontSize) {
        XCells = cellx;
        YCells = celly;

        try {
            // this.setBackground(Color.black);
            int QX[] = new int[cellx];
            QX[0] = 1;

            for (int i = 1; i < cellx; i++) {
                QX[i] = QX[i - 1] + width[i - 1] + 1;
            }
            lbItem = new FLabel[cellx][celly];

            for (int i = 0; i < cellx; i++) {
                for (int j = 0; j < celly; j++) {
                    int xx = QX[i];
                    int yy = j * (height + 1) + 1;
                    lbItem[i][j] = new FLabel("");
                    lbItem[i][j].setBackground(Color.white);
                    lbItem[i][j].setBounds(new Rectangle(xx, yy, width[i], height));
                    lbItem[i][j].setFont(new java.awt.Font(sfont, ft, fontSize));
                    lbItem[i][j].setAlignment(1);
                }
            }

            this.setLayout(null);

            for (int i = 0; i < cellx; i++) {
                for (int j = 0; j < celly; j++) {
                    this.add(lbItem[i][j], null);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setBorderColor(Color cColor, Color bColor) {
        this.setBackground(cColor);
        borderColor = bColor;
    }

    public void paint(Graphics g) {
        g.setColor(borderColor);
        g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
    }

    public FTable(int cellx, int celly, int width, int height, String sfont,
            int ft, int fontSize) {
        XCells = cellx;
        YCells = celly;

        try {
            this.setBackground(Color.black);
            lbItem = new FLabel[cellx][celly];
            for (int i = 0; i < cellx; i++) {
                for (int j = 0; j < celly; j++) {
                    int xx = i * (width + 1) + 1;
                    int yy = j * (height + 1) + 1;
                    lbItem[i][j] = new FLabel("");
                    lbItem[i][j].setBackground(Color.white);
                    lbItem[i][j].setBounds(new Rectangle(xx, yy, width, height));
                    lbItem[i][j].setFont(new java.awt.Font(sfont, ft, fontSize));
                    lbItem[i][j].setAlignment(1);
                }
            }

            this.setLayout(null);

            for (int i = 0; i < cellx; i++) {
                for (int j = 0; j < celly; j++) {
                    this.add(lbItem[i][j], null);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

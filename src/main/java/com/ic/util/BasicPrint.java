package com.ic.util;

import com.ic.core.ChartScreen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;


public class BasicPrint extends Component//  implements FilenameFilter
{
    private PrintJob pjob = null;
    private ChartScreen chartScreen1;
    private ChartScreen chartScreen2;
    private ChartScreen chartScreen3;

    private BufferedImage offimage = null;
    private int ww, hh; //the width and height of offimage

    public int pprint() {
        return 1;
    }

    public void startPrint() {
        SaveToFile();
    }

    private void createFullImage() {
        hh = 0;
        ww = chartScreen1.getSize().width;

        //calculate the height of the image
        if (chartScreen1.isVisible()) hh = hh + chartScreen1.getSize().height;
        if (chartScreen2.isVisible()) hh = hh + chartScreen2.getSize().height;
        if (chartScreen3.isVisible()) hh = hh + chartScreen3.getSize().height;


        offimage = new BufferedImage(ww, hh, BufferedImage.TYPE_INT_RGB);// chartScreen1.createImage(ww, hh);

        if (offimage == null) {
            System.out.println("NO Memory to print " + ww + " : " + hh);
            return;
        }

        Graphics g = offimage.getGraphics();

        // the real Height of chartScreen 1,2,3 in the page respectivitily.
        int realHeight1 = 0;
        int realHeight2 = 0;
        int realHeight3 = 0;
        if (chartScreen1.isVisible()) {
            realHeight1 = chartScreen1.getSize().height;
            g.drawImage(chartScreen1.getAllscreenImage(), 0, 0, this);
        }
        if (chartScreen2.isVisible()) {
            realHeight2 = chartScreen2.getSize().height;
            g.drawImage(chartScreen2.getAllscreenImage(), 0, realHeight1 + 1, this);
        }
        if (chartScreen3.isVisible()) {
            realHeight3 = chartScreen3.getSize().height;
            g.drawImage(chartScreen3.getAllscreenImage(), 0, realHeight1 + realHeight2 + 1, this);
        }

    }

    private void SaveToFile() {
        try {
            //BMPFile bmp = new BMPFile();
            FileDialog fd = new FileDialog(new Frame(), "Save Charts to file ", FileDialog.SAVE);
            fd.setFile("IC_" + new Date().getTime() + ".png");
            fd.show();
            if (fd.getDirectory() == null || fd.getFile() == null) return;
            System.out.println(fd.getDirectory());
            System.out.println(fd.getFile());
            String fullFileName = fd.getDirectory() + fd.getFile();
            File outputfile = new File(fullFileName);
            createFullImage();
            // construct the buffered image
            //BufferedImage bImage = new BufferedImage(offimage.getWidth(null), offimage.getHeight(null), BufferedImage.TYPE_INT_RGB);
            // retrieve image
            ImageIO.write(offimage, "png", outputfile);

        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    public boolean initPrinter(ChartScreen cs1, ChartScreen cs2, ChartScreen cs3) {
        chartScreen1 = cs1;
        chartScreen2 = cs2;
        chartScreen3 = cs3;
        return true;
    }
}


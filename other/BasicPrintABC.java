package fmechart;

import java.awt.*;
import java.awt.print.*;
//import javax.swing.JComponent;

/**
 * Title:        FME Chart Project for E-finet
 * Description:
 * Copyright:    Copyright (c)
 * Company:
 * @author
 * @version 1.0
 */

public class BasicPrint extends Component implements Printable
{

    private PrinterJob pjob = null;
    private PageFormat pf = null;
    private ChartScreen chartScreen1;
    private ChartScreen chartScreen2;
    private ChartScreen chartScreen3;

    public int print(Graphics g, PageFormat pf, int pageIndex) {

         System.out.println("start print");
         if (pageIndex > 0) {
           return Printable.NO_SUCH_PAGE;
         }
         //


         Graphics2D g2d = (Graphics2D)g;
        // System.out.println(pf.getImageableX()+ " : " + pf.getImageableY());
         g2d.translate(pf.getImageableX(),pf.getImageableY());

         int tSpace = 20;
         int realWidth = 400;
         // the real Height of chartScreen 1,2,3 in the page respectivitily.
         int realHeight1 =0;
         int realHeight2 =0;
         int realHeight3 =0;
         double ssa = 1f;

         if (chartScreen1.isVisible())
         {
           ssa = chartScreen1.allscreenImage.getWidth(this)/chartScreen1.allscreenImage.getHeight(this);
           realHeight1 = (int) (realWidth/ssa);
           g2d.drawImage(chartScreen1.allscreenImage,0,tSpace,realWidth,tSpace + realHeight1,0,0,chartScreen1.allscreenImage.getWidth(this),chartScreen1.allscreenImage.getHeight(this),this);
         }

         if (chartScreen2.isVisible())
         {
           ssa = chartScreen2.allscreenImage.getWidth(this)/chartScreen2.allscreenImage.getHeight(this);
           realHeight2 = (int) (realWidth/ssa);
           g2d.drawImage(chartScreen2.allscreenImage,0,tSpace+realHeight1,realWidth,tSpace+realHeight1+realHeight2,0,0,chartScreen2.allscreenImage.getWidth(this),chartScreen2.allscreenImage.getHeight(this),this);
         }//System.out.println(0 + " : " + realHeight1 + " : " + realWidth + " : " + realHeight2);

         if (chartScreen3.isVisible())
         {
           ssa = chartScreen3.allscreenImage.getWidth(this)/chartScreen3.allscreenImage.getHeight(this);
           realHeight3 = (int) (realWidth/ssa);
           g2d.drawImage(chartScreen3.allscreenImage,0,tSpace + realHeight1+realHeight2,realWidth,tSpace + realHeight1+realHeight2+realHeight3,0,0,chartScreen3.allscreenImage.getWidth(this),chartScreen3.allscreenImage.getHeight(this),this);
//        System.out.println(0 + " : " + (realHeight1+realHeight2) + " : " + realWidth + " : " + realHeight3);
         }
         return Printable.PAGE_EXISTS;
     }

     public void startPrint()
     {
        pjob.setPrintable(this, pf);
        try {
          pjob.print();
        } catch (PrinterException e) {
          e.printStackTrace();
        }
     }

     public boolean initPrinter(ChartScreen cs1, ChartScreen cs2, ChartScreen cs3) {
        chartScreen1 = cs1;
        chartScreen2 = cs2;
        chartScreen3 = cs3;
        pjob = PrinterJob.getPrinterJob();
        if (!pjob.printDialog())
        {
          System.out.println("Cancelled");
          return false;
        }
        pf = pjob.defaultPage();
        pjob.validatePage(pf);
        return true;
//        pjob.setCopies(1);
     }
 }


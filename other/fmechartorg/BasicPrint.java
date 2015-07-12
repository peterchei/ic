package fmechart;

import java.awt.*;
import java.awt.print.*;
import java.awt.Toolkit.*;
//import javax.swing.JComponent;

/**
 * Title:        FME Chart Project for E-finet
 * Description:
 * Copyright:    Copyright (c)
 * Company:
 * @author
 * @version 1.0
 */

public class BasicPrint extends Component// implements Printable
{
    private PrintJob pjob = null;
    //private PageFormat pf = null;
    private ChartScreen chartScreen1;
    private ChartScreen chartScreen2;
    private ChartScreen chartScreen3;

    public int pprint(Graphics g) {

        System.out.println("start print");

         int tSpace = 100;
         int lSpace = 50;
         int realWidth = 400;
         // the real Height of chartScreen 1,2,3 in the page respectivitily.
         int realHeight1 =0;
         int realHeight2 =0;
         int realHeight3 =0;
         double ssa = 1f;

         if (chartScreen1.isVisible())
         {
           ssa = chartScreen1.allscreenImage.getWidth(this)/chartScreen1.allscreenImage.getHeight(this);
           realHeight1 = (int) Math.round(realWidth/ssa);
           //g2d.drawImage(chartScreen1.allscreenImage,50,tSpace,50+realWidth,tSpace + realHeight1,0,0,chartScreen1.allscreenImage.getWidth(this),chartScreen1.allscreenImage.getHeight(this),this);
           g.drawImage(chartScreen1.allscreenImage,lSpace,tSpace,realWidth,realHeight1,this);
         }

         if (chartScreen2.isVisible())
         {
           ssa = chartScreen2.allscreenImage.getWidth(this)/chartScreen2.allscreenImage.getHeight(this);
           realHeight2 = (int) Math.round(realWidth/ssa)+1;
           //g2d.drawImage(chartScreen2.allscreenImage,50,tSpace+realHeight1,50+realWidth,tSpace+realHeight1+realHeight2,0,0,chartScreen2.allscreenImage.getWidth(this),chartScreen2.allscreenImage.getHeight(this),this);
           g.drawImage(chartScreen2.allscreenImage,lSpace,tSpace+realHeight1+1,realWidth,realHeight2,this);
         }//System.out.println(0 + " : " + realHeight1 + " : " + realWidth + " : " + realHeight2);

         if (chartScreen3.isVisible())
         {
           ssa = chartScreen3.allscreenImage.getWidth(this)/chartScreen3.allscreenImage.getHeight(this);
           realHeight3 = (int) Math.round(realWidth/ssa)+1;
           //g2d.drawImage(chartScreen3.allscreenImage,50,tSpace + realHeight1+realHeight2,50+realWidth,tSpace + realHeight1+realHeight2+realHeight3,0,0,chartScreen3.allscreenImage.getWidth(this),chartScreen3.allscreenImage.getHeight(this),this);
           g.drawImage(chartScreen3.allscreenImage,lSpace,tSpace+realHeight1+realHeight2,realWidth,realHeight3,this);
//        System.out.println(0 + " : " + (realHeight1+realHeight2) + " : " + realWidth + " : " + realHeight3);
         }


         //g.dispose();

//       pjob.finalize();
         pjob.end();
         return Printable.PAGE_EXISTS;
     }

     public void startPrint()
     {
        pjob = java.awt.Toolkit.getDefaultToolkit().getPrintJob(new Frame(),"FME chart",null);

        if (pjob!=null)
        {
          pprint(pjob.getGraphics());
        }
     }

     public boolean initPrinter(ChartScreen cs1, ChartScreen cs2, ChartScreen cs3) {
        chartScreen1 = cs1;
        chartScreen2 = cs2;
        chartScreen3 = cs3;
//        pjob = PrintJob.getPrinterJob();
        /*
        if (!pjob.printDialog())
        {
          System.out.println("Canceled");
          return false;
        }
        pf = pjob.defaultPage();
        pjob.validatePage(pf);
        return true;
//       pjob.setCopies(1);
*/
      return true;
     }
 }


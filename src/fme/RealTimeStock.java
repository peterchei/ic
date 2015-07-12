/**
 * Title:        <p>
 * Description:  <p>
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author
 * @version 1.0
 */
package fme;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class RealTimeStock {
    //Server Address
    String src;
    String pwd;
    final static String RealTimeURL = "script/stock.php";

    public RealTimeStock(String source, String passcode) {
        src = source;
        pwd = passcode;
    }

    public static BufferedReader getStream(String src, String pwd) throws
            Exception, RealTimeStockException {

        String parameter = "?src=" + src + "&passcode=" + pwd;
        String srcAddr = FinetExpress.getScriptBase() + RealTimeURL + parameter;
        System.out.println(srcAddr);

        URL Finet;
        Finet = new URL(srcAddr);
        URLConnection FinetConnection = Finet.openConnection();
        return new BufferedReader(new InputStreamReader(FinetConnection.
                getInputStream()));
    }

    public static String getRawString(String src, String pwd) throws Exception,
            RealTimeStockException {
        try {
            String parameter = "?src=" + src + "&passcode=" + pwd;
            String srcAddr = FinetExpress.getScriptBase() + RealTimeURL + parameter;
            //    System.out.println(srcAddr);

            URL Finet;
            Finet = new URL(srcAddr);
            URLConnection FinetConnection = Finet.openConnection();
            BufferedReader DS = new BufferedReader(new InputStreamReader(
                    FinetConnection.getInputStream()));
            String RawData = DS.readLine();
            //    System.out.println(RawData);
            DS.close();
            if (RawData.equals("") == false) {
                return RawData;
            } else {
                throw new RealTimeStockException("Passcode mismatch\n");
            }
        } catch (NullPointerException ne) {
            // do nth
            return "";
        }
    } // end getRawString
}
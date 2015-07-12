/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package teletext.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hung
 */
public class TestQuote1 {

    public static void main(String args[]) {
        try {
            URL url = new URL("http://www.aastocks.com/tc/LTP/RTQuote.aspx?&symbol=00001");
            System.out.println(url.getContent());
            InputStream in = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = reader.readLine();
            String strPrice = "N/A";
            while (line != null) {
                if (line.contains("<td>現價</td>"))  {
                    reader.readLine();//
                    reader.readLine();//
                    reader.readLine();//
                    line = reader.readLine();//

                    int startIndex = line.indexOf("\">");
                    int endIndex = line.indexOf("</span>");
                    strPrice = line.substring(startIndex+2, endIndex);

                    System.out.println(strPrice);
                    break;
                }
                line = reader.readLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(TestQuote1.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

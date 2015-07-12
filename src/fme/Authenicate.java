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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Authenicate
        extends Thread {

    final String authserver = "script/auth.php";
    final String loginserver = "script/AppletLogin.php";

    FinetExpress finetExpress;
    String sCompany, sUserid, sDigest, sSessionid;
    boolean status = true;

    public Authenicate(FinetExpress finet) {
        finetExpress = finet;
    }

    public void init() throws NullPointerException, AuthenicateException,
            Exception {
        URL Finet;
        sCompany = finetExpress.getParameter("COMPANY");
        sUserid = finetExpress.getParameter("USERID");
        sDigest = finetExpress.getParameter("DIGEST");
        sSessionid = finetExpress.getParameter("SESSIONID");
        String parameter = "?company=" + sCompany + "&userid=" + sUserid
                + "&sessionid=" + sSessionid + "&digest=" + sDigest;
        String srcAddr = finetExpress.getScriptBase() + loginserver + parameter;
        Finet = new URL(srcAddr);
        URLConnection FinetConnection = Finet.openConnection();
        BufferedReader DS = new BufferedReader(new InputStreamReader(
                FinetConnection.getInputStream()));
        String RawData = DS.readLine();
        System.out.println(RawData);
        String reason = DS.readLine();
        System.out.println(reason);
        DS.close();
        if (RawData.equals("Accepted")) {
            finetExpress.showStatus("Login Successfully");
        } else {
            throw new AuthenicateException(reason);
        }
    }

    public void run() {
        try {
            init();
            while (status) {
                finetExpress.showStatus("Getting sessionid");
                finetExpress.passcode = getPasscode();
                sleep(200000);
            }
        } catch (AuthenicateException ae) {
            try {
                status = false;
                finetExpress.passcode = "NULL";
                finetExpress.multilogin();
                finetExpress.showStatus(ae.getMessage());
            } catch (Exception ex) {
                finetExpress.showStatus(ex.getMessage());
                ex.printStackTrace();
            }
        } catch (NullPointerException ne) {
        } catch (InterruptedException iee) {
            status = false;
        } catch (Exception ex) {
            finetExpress.showStatus(ex.getMessage());
            ex.printStackTrace();
            status = false;
        }
    }

    public void Disable() throws Exception {
        status = false;
    }

    private String getPasscode() throws AuthenicateException, Exception {
        String parameter = "?company=" + sCompany + "&userid=" + sUserid
                + "&sessionid=" + sSessionid;
        System.out.println(parameter);
        String s = FinetExpress.getScriptBase() + authserver + parameter;
        URL AuthServerURL = new URL(s);
        System.out.println(s);
//      finetExpress.showStatus(s);
        URLConnection AuthServerConnection = AuthServerURL.openConnection();
        AuthServerConnection.setUseCaches(false);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                AuthServerConnection.getInputStream()));
        String message = reader.readLine();
        if (message.equals("Accepted")) {
            System.out.println("Accepted");
            String passcode = reader.readLine();
            System.out.println(passcode);
//        finetExpress.showStatus(passcode);
            return passcode;
        }
        if (message.equals("Deny")) {
            System.out.println("Deny");
            String reason = reader.readLine();
//        finetExpress.showStatus(reason);
            throw (new AuthenicateException(reason));
        }
        return "NULL";
    }
}

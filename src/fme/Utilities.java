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
import java.util.StringTokenizer;

public class Utilities {

    static private double factor = 1.0;

    public Utilities() {

    }

    public final static double GetFactor() {
        return factor;
    }

    public final static void SetFactor(double f) {
        factor = f;
    }

    public final static Font ScaleFont(Font f) {
        Font nf = new Font(f.getName(), f.getStyle(),
                (int) Math.round(f.getSize() * factor));
        return nf;
    }

    public final static int ScaleUp(int x) {
        return (int) Math.round(factor * x);
    }

    public final static Rectangle ScaleRectangle(Rectangle r) {
        Rectangle nr = new Rectangle();
        nr.width = (int) Math.round(r.width * factor);
        nr.height = (int) Math.round(r.height * factor);
        nr.x = (int) Math.round(r.x * factor);
        nr.y = (int) Math.round(r.y * factor);
        return nr;
    }

    public final static Color ColorExtractor(String c) throws Exception {
        StringTokenizer tokens = new StringTokenizer(c);
        int Red = (new Integer(tokens.nextToken(","))).intValue();
        int Green = (new Integer(tokens.nextToken(","))).intValue();
        int Blue = (new Integer(tokens.nextToken(","))).intValue();
        return new Color(Red, Green, Blue);
    }

}

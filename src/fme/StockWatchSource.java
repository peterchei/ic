/**
 * Title:        <p>
 * Description:  <p>
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author
 * @version 1.0
 */
package fme;

import java.util.Vector;

public class StockWatchSource {
    public int NumberOfStock;
    public final int MAXNumberOfStock = 20;

    // The StockTable.... it is a link list....
    public Vector MyStock = new Vector();

    public StockWatchSource() {
        NumberOfStock = 0;
    }

}

/*
 * Created by Dboy
 * Date: 2004年3月21日
 * Time: 下午06:09:54
 *
 */
package teletext.apps.ui;

import javax.swing.table.AbstractTableModel;

public class PortfolioModel extends AbstractTableModel {

    public static final String columnName[] = {
        "Code", "Stock Name", "Price", "Quantity", "Trade Date", "Sell Date", "Current Price", "P&L", "P&L(%)"};

    public PortfolioModel() {
    }

    public void clear() {
    }


    public void delete(int row) {

    }

    public void add(int row) {

    }

    public void add() {

    }

    public int getColumnCount() {
        return columnName.length;
    }

    public String getColumnName(int column) {
        return columnName[column];
    }

    public void getDataInfoAtRow(int rowIndex) {

    }

    public Object getValueAt(int rowIndex, int colIndex) {
        return null;
    }

    public int getRowCount() {
        return 10;

    }
}

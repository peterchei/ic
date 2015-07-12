package teletext.apps.ui;

import javax.swing.table.AbstractTableModel;

public class TopTenModel
        extends AbstractTableModel {

    public static final String columnName[] = {
        "Code", "Stock Name", "Last", "Change", "%Change"};

    public TopTenModel() {
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

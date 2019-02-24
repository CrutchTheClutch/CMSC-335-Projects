package SeaPortProject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class JobsTableModel extends DefaultTableModel implements TableModel {
    private static final String[] COLUMN_NAMES = {"Ship", "Name", "Status", "Progress", "Pause", "Cancel"};

    JobsTableModel() {
        super(COLUMN_NAMES, 0);
    }

    @Override
    public Class<?> getColumnClass(int col) {
        switch (col) {
            case 2:
            case 3:
            case 4:
            case 5:
                return JPanel.class;
        }
        return Object.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 4:
            case 5:
                return true;
            default:
                return false;
        }
    }
}

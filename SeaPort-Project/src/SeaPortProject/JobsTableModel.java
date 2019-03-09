package SeaPortProject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * <strong>Filename:</strong> &emsp;&emsp;&emsp; {@code JobsTableModel} <br/>
 * <strong>Author:</strong> &emsp;&emsp;&emsp;&emsp; William Crutchfield <br/>
 * <strong>Date Created:</strong> &emsp; February 21st, 2019 <br/>
 *
 * <br/>
 *
 * Defines the {@code JobsTableModel}.  Holds all GUI components for each {@link Job}.
 *
 * @author  William Crutchfield
 */
public class JobsTableModel extends DefaultTableModel implements TableModel {
    private static final String[] COLUMN_NAMES = {"Port", "Ship", "Name", "Status", "Progress", "Pause", "Cancel"};

    /**
     * Constructor for {@code JobsTableModel}.
     */
    JobsTableModel() {
        super(COLUMN_NAMES, 0);
    }

    @Override
    public Class<?> getColumnClass(int col) {
        switch (col) {
            case 3:
            case 4:
            case 5:
            case 6:
                return JPanel.class;
        }
        return Object.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 5:
            case 6:
                return true;
            default:
                return false;
        }
    }
}

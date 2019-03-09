package SeaPortProject;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * <strong>Filename:</strong> &emsp;&emsp;&emsp; {@code ResourcesTableModel} <br/>
 * <strong>Author:</strong> &emsp;&emsp;&emsp;&emsp; William Crutchfield <br/>
 * <strong>Date Created:</strong> &emsp; March 6th, 2019 <br/>
 *
 * <br/>
 *
 * Defines the {@code ResourcesTableModel}.  Holds all GUI components for each {@link Person}.
 *
 * @author  William Crutchfield
 */
public class ResourcesTableModel extends DefaultTableModel implements TableModel {
    private static final String[] COLUMN_NAMES = new String[]{"Port", "Name", "Skill", "Status", "Current Location"};

    /**
     * Constructor for {@code ResourcesTableModel}.
     */
    ResourcesTableModel() {
        super(COLUMN_NAMES, 0);
    }

    public Class<?> getColumnClass(int col) {
        if (col == 3) {
            return JPanel.class;
        }
        return Object.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
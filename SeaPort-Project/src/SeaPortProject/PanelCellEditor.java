package SeaPortProject;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

/**
 * <strong>Filename:</strong> &emsp;&emsp;&emsp; {@code PanelCellEditor} <br/>
 * <strong>Author:</strong> &emsp;&emsp;&emsp;&emsp; William Crutchfield <br/>
 * <strong>Date Created:</strong> &emsp; February 20th, 2019 <br/>
 *
 * <br/>
 *
 * Defines the {@code PanelCellEditor}.  Allows the user to interact with {@link JPanel} components in a JTable.
 *
 * @author  William Crutchfield
 */
public class PanelCellEditor extends AbstractCellEditor implements TableCellEditor {
    private Component editor;

    /**
     * Returns a {@link JPanel} component if the cell contains a {@link JPanel}.
     *
     * <br/><br/>
     *
     * {@inheritDoc}
     */
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (table.getModel().getColumnClass(column) == JPanel.class) {
            editor = (JPanel) value;
        }
        editor = (Component) value;
        return editor;
    }

    @Override
    public Object getCellEditorValue() {
        return editor;
    }
}

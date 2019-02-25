package SeaPortProject;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

/**
 * Filename :   PanelCellEditor
 * Author :     William Crutchfield
 * Date:        2/20/2019
 * Description: Custom CellEditor for JPanel Components
 */
public class PanelCellEditor extends AbstractCellEditor implements TableCellEditor {
    private Component editor;

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

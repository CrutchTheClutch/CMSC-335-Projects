package SeaPortProject;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

public class PanelCellEditor extends AbstractCellEditor implements TableCellEditor {

    private JPanel editor;

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        editor = (JPanel) value;
        return editor;
    }

    @Override
    public Object getCellEditorValue() {
        return editor;
    }
}

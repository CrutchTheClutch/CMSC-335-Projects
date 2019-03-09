package SeaPortProject;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * <strong>Filename:</strong> &emsp;&emsp;&emsp; {@code PanelCellRenderer} <br/>
 * <strong>Author:</strong> &emsp;&emsp;&emsp;&emsp; William Crutchfield <br/>
 * <strong>Date Created:</strong> &emsp; February 20th, 2019 <br/>
 *
 * <br/>
 *
 * Defines the {@code PanelCellRenderer}.  Allows the {@link JTable} to render {@link JPanel} components.
 *
 * @author  William Crutchfield
 */
public class PanelCellRenderer extends JPanel implements TableCellRenderer {

    /**
     * Returns a {@link JPanel} component if the cell contains a {@link JPanel}.
     *
     * <br/><br/>
     *
     * {@inheritDoc}
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (table.getModel().getColumnClass(column) == JPanel.class) {
            return (JPanel) value;
        }
        return (Component) value;
    }
}

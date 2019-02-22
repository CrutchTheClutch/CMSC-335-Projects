package SeaPortProject;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ProgressCellRenderer extends JProgressBar implements TableCellRenderer {


  public Component getTableCellRendererComponent(
      JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return (JProgressBar)value;
    }
}

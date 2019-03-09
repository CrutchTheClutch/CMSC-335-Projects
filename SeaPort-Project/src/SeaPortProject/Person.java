package SeaPortProject;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

/**
 * <strong>Filename:</strong> &emsp;&emsp;&emsp; {@code Person} <br/>
 * <strong>Author:</strong> &emsp;&emsp;&emsp;&emsp; William Crutchfield <br/>
 * <strong>Date Created:</strong> &emsp; January 26th, 2019 <br/>
 *
 * <br/>
 *
 * Defines the {@code Person} object.  Works on {@link Ship Ships}, and complete {@link Job Jobs}.
 *
 * @author  William Crutchfield
 */
class Person extends Thing {

    private String skill;
    private String location;
    private WorkerStatus status;

    // Create JPanels
    private JPanel statusPanel;
    private JLabel statusLabel;

    /**
     * Constructor for {@code Person}.
     *
     * @param sc The current text file data.
     */
    Person(Scanner sc) {
        super(sc);
        if (sc.hasNext()) skill = sc.next();
        buildGUI();
    }

    /**
     * Helper method for the {@link Person#Person(Scanner) Constructor}.  Is responsible for building the GUI.
     */
    private void buildGUI() {
        // Create JPanels
        statusPanel = new JPanel(new BorderLayout());

        // Set Panel Borders
        statusPanel.setBorder(null);

        // Create Components
        statusLabel = new JLabel();

        // Set Component Borders
        statusLabel.setBorder(null);

        // statusLabel Settings
        statusLabel.setForeground(Color.BLACK);
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        setStatus(WorkerStatus.AVAILABLE);

        // Add Components
        statusPanel.add(statusLabel, BorderLayout.CENTER);
    }

    /**
     * Getter method for {@code skill}.
     *
     * @return Current {@code skill}.
     */
    String getSkill() {
        return skill;
    }

    /**
     * Setter method for {@code skill}.
     *
     * @param skill New {@code skill}.
     */
    void setSkill(String skill) {
        this.skill = skill;
    }

    /**
     * Getter method for {@code location}.
     *
     * @return Current {@code location}.
     */
    String getLocation() {
        return location;
    }

    /**
     * Setter method for {@code location}.
     *
     * @param location New {@code location}.
     */
    void setLocation(String location) {
        this.location = location;
    }

    /**
     * Setter method for {@code status}.
     *
     * @return Current {@code location}.
     */
    WorkerStatus getStatus() {
        return status;
    }

    /**
     * Setter method for {@code status}.
     *
     * @param status New {@code status}.
     */
    void setStatus(WorkerStatus status) {
        switch (status) {
            case WORKING:
                statusPanel.setBackground(Color.GREEN);
                break;
            case AVAILABLE:
                statusPanel.setBackground(Color.ORANGE);
                break;
        }
        this.statusLabel.setText(status.toString());
        this.status = status;
    }

    /**
     * Getter method for {@code statusPanel}.  A {@link JPanel} that displays the {@link JobStatus}.
     *
     * @return Current {@code statusPanel}.
     */
    JPanel getStatusPanel() {
        return statusPanel;
    }

    /**
     * Returns a string representation of the {@code Person}.
     *
     * @return Formatted string of the {@code Person} object.
     */
    @Override
    public String toString() {
        return "Person: " + super.toString() + " " + skill;
    }
}

package SeaPortProject;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * <strong>Filename:</strong> &emsp;&emsp;&emsp; {@code Job} <br/>
 * <strong>Author:</strong> &emsp;&emsp;&emsp;&emsp; William Crutchfield <br/>
 * <strong>Date Created:</strong> &emsp; February 18th, 2019 <br/>
 *
 * <br/>
 *
 * <p>Defines the {@code Job} object.  Each {@code Job} is assigned to its own thread and each {@link Ship} can only run
 * a single {@code Job} at one time.  This class also defines {@link JPanel JPanels} for each {@code Job} that will be
 * added to the {@code jobsTable} in {@link SeaPortProgram}.
 *
 * <br/><br/>
 *
 * <p>Note that {@code Job} objects do not perform any actual logic.  {@code Job} objects are simulated tasks using
 * delays within the threads.
 *
 * @author  William Crutchfield
 */
class Job extends Thing implements Runnable {

    private final SeaPort port;
    private Ship ship;
    private JTable jobsTable;
    private DefaultTableModel jobsTableModel;

    private ArrayList<String> requirements = new ArrayList<>();
    private JobStatus status = JobStatus.SUSPENDED;
    private boolean isRunning = true;
    private boolean isCanceled = false;
    private boolean isFinished = false;
    private double duration;
    private Thread thread;

    // GUI Variables
    private SeaPortProgram program;
    private JPanel statusPanel;
    private JPanel progressPanel;
    private JPanel suspendPanel;
    private JPanel cancelPanel;
    private JLabel statusLabel;
    private JProgressBar progressBar;

    /**
     * Constructor for {@code Person}.
     *
     * @param sc            The current text file data.
     * @param program       Instance of {@link SeaPortProgram}.
     * @param shipsHashMap  HashMap containing all {@link Ship Ships} and their respective {@code index} values.
     */
    Job(Scanner sc, SeaPortProgram program, HashMap<Integer, Ship> shipsHashMap) {

        super(sc);
        if (sc.hasNextDouble()) duration = sc.nextDouble();
        while (sc.hasNext()) {
            requirements.add(sc.next());
        }

        this.program = program;
        jobsTable = program.getJobsTable();
        jobsTableModel = (DefaultTableModel) jobsTable.getModel();

        ship = shipsHashMap.get(this.getParent());
        port = ship.getPort();

        thread = new Thread(this, ship.getName() + "-" + this.getName());

        //region Job GUI
        statusPanel = new JPanel(new BorderLayout());
        progressPanel = new JPanel(new BorderLayout());
        suspendPanel = new JPanel(new BorderLayout());
        cancelPanel = new JPanel(new BorderLayout());

        statusPanel.setBorder(null);
        progressPanel.setBorder(null);
        suspendPanel.setBorder(null);
        cancelPanel.setBorder(null);

        statusLabel = new JLabel();
        progressBar = new JProgressBar (0, 100000);
        JButton suspendBtn = new JButton("Pause");
        JButton cancelBtn = new JButton("Cancel");

        statusLabel.setBorder(null);
        progressBar.setBorder(null);
        suspendBtn.setBorder(null);
        cancelBtn.setBorder(null);

        statusLabel.setForeground(Color.BLACK);
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        setStatus(status);

        progressBar.setStringPainted(true);

        statusPanel.add(statusLabel, BorderLayout.CENTER);
        progressPanel.add(progressBar, BorderLayout.CENTER);
        suspendPanel.add(suspendBtn, BorderLayout.CENTER);
        cancelPanel.add(cancelBtn, BorderLayout.CENTER);
        //endregion

        suspendBtn.addActionListener(e -> toggleIsRunning());
        cancelBtn.addActionListener(e -> cancelJob());
    }

    /**
     * Toggles the {@code isRunning} boolean.
     */
    private void toggleIsRunning() {
        isRunning = !isRunning;
    }

    /**
     * Cancels the current {@code Job}.
     */
    private void cancelJob() {
        isCanceled = true;
        isRunning = false;
    }

    /**
     * Sets the {@link JobStatus} for the current {@code Job}.  Depending on the status, the {@code statusPanel} color
     * will be changed, along with the boolean {@code isFinished}.
     *
     * @param status A predefined {@link JobStatus}, used to determine the status of the current {@code Job}.
     */
    private void setStatus(JobStatus status) {
        this.status = status;
        switch (status){
            case RUNNING:
                statusPanel.setBackground(Color.GREEN);
                break;
            case SUSPENDED:
                statusPanel.setBackground(Color.YELLOW);
                break;
            case WAITING:
                statusPanel.setBackground(Color.ORANGE);
                break;
            case DONE:
                statusPanel.setBackground(Color.RED);
                program.updateLog(this.getName() + " has Finished");
                isFinished = true;
                break;
            case CANCELLED:
                statusPanel.setBackground(Color.RED);
                program.updateLog(this.getName() + " has been Cancelled");
                isFinished = true;
                break;
        }
        statusLabel.setText(status.toString());
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
     * Getter method for {@code progressPanel}.  A {@link JPanel} that displays the progress.
     *
     * @return Current {@code progressPanel}.
     */
    JPanel getProgressPanel() {
        return progressPanel;
    }

    /**
     * Getter method for {@code suspendPanel}.  A {@link JPanel} that displays the Pause button.
     *
     * @return Current {@code suspendPanel}.
     */
    JPanel getSuspendPanel() {
        return suspendPanel;
    }

    /**
     * Getter method for {@code cancelPanel}.  A {@link JPanel} that displays the Cancel button.
     *
     * @return Current {@code cancelPanel}.
     */
    JPanel getCancelPanel() {
        return cancelPanel;
    }

    /**
     * Getter method for {@code thread}.
     *
     * @return Current {@code thread}.
     */
    Thread getThread() {
        return thread;
    }

    /**
     * Updates the {@code progressBar} as the {@code Job} is executed.  Depending on the {@link JobStatus}, the
     * {@code thread} will either run, wait, or end.
     */
    @Override
    public void run() {
        long currentTime = System.currentTimeMillis();
        long startTime = currentTime;
        long stopTime = (long) (startTime + duration * 1000);

        synchronized (port) {
            while (ship.getIsBusy() || ship.getDock() == null) {
                setStatus(JobStatus.WAITING);
                try{
                    port.wait();
                } catch (InterruptedException ignored) {}
            }
            ship.setIsBusy(true);
            program.updateLog(this.getName() + " has Started");
        }

        while (startTime < stopTime && !isCanceled) {
            try {
                Thread.sleep (100);
            } catch (InterruptedException ignored) {}

            if (isRunning) {
                setStatus(JobStatus.RUNNING);
                startTime += 100;
                progressBar.setValue((int)(((startTime - currentTime) / duration) * 100));
            } else {
                setStatus(JobStatus.SUSPENDED);
            }
            jobsTable.tableChanged(new TableModelEvent(jobsTableModel));
        }

        if (isCanceled && status != JobStatus.DONE) {
            setStatus(JobStatus.CANCELLED);
        } else {
            setStatus(JobStatus.DONE);
        }

        isFinished = true;

        synchronized (port) {
            ship.setIsBusy(false);
            for (Job jobs : ship.getJobs()) {
                if (!(jobs.isFinished)) {
                    port.notifyAll();
                    return;
                }
            }
            while (!port.getQueue().isEmpty()) {
                Ship newShip = port.getQueue().remove(0);
                if (!newShip.getJobs().isEmpty()) {
                    program.updateLog("Ship " + ship.getName() + " Departed from " + ship.getDock().getName());
                    Dock dock = ship.getDock();
                    dock.setShip(newShip);
                    newShip.setDock(dock);
                    program.updateLog("Ship " + newShip.getName() + " Arrived at " + dock.getName());
                    port.notifyAll();
                    return;
                }
            }
        }
    }

    /**
     * Returns a string representation of the {@code Job}.
     *
     * @return Formatted string of the {@code Job} object.
     */
    @Override
    public String toString() {
        return super.toString();
    }
}

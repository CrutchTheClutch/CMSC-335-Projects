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
 * Defines the {@code Job} object.  Each {@code Job} is assigned to its own thread and each {@link Ship} can only run
 * a single {@code Job} at one time.  This class also defines {@link JPanel JPanels} for each {@code Job} that will be
 * added to the {@code jobsTable} in {@link SeaPortProgram}.
 *
 * <br/><br/>
 *
 * Note that {@code Job} objects do not perform any actual logic.  {@code Job} objects are simulated tasks using
 * delays within their respective thread.
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

    // Create JPanels
    private JPanel statusPanel;
    private JPanel progressPanel;
    private JPanel suspendPanel;
    private JPanel cancelPanel;
    private JLabel statusLabel;

    private JProgressBar progressBar;

    /**
     * Constructor for {@code Job}.
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

        buildGUI();
    }

    /**
     * Helper method for the {@link Job#Job(Scanner, SeaPortProgram, HashMap) Constructor}.  Is responsible for
     * building the GUI and ActionListeners.
     */
    private void buildGUI() {

        // Create JPanels
        statusPanel = new JPanel(new BorderLayout());
        progressPanel = new JPanel(new BorderLayout());
        suspendPanel = new JPanel(new BorderLayout());
        cancelPanel = new JPanel(new BorderLayout());

        // Set Panel Borders
        statusPanel.setBorder(null);
        progressPanel.setBorder(null);
        suspendPanel.setBorder(null);
        cancelPanel.setBorder(null);

        // Create Components
        statusLabel = new JLabel();
        progressBar = new JProgressBar (0, 100000);
        JButton suspendBtn = new JButton("Pause");
        JButton cancelBtn = new JButton("Cancel");

        // Set Component Borders
        statusLabel.setBorder(null);
        progressBar.setBorder(null);
        suspendBtn.setBorder(null);
        cancelBtn.setBorder(null);

        // statusLabel Settings
        statusLabel.setForeground(Color.BLACK);
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        setStatus(status);

        // progressBar Settings
        progressBar.setStringPainted(true);

        // Add Components
        statusPanel.add(statusLabel, BorderLayout.CENTER);
        progressPanel.add(progressBar, BorderLayout.CENTER);
        suspendPanel.add(suspendBtn, BorderLayout.CENTER);
        cancelPanel.add(cancelBtn, BorderLayout.CENTER);

        // Action Listeners
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
     * will be changed, along with the boolean {@code isFinished}.  This method also updates the
     * {@link SeaPortProgram SeaPortProgram's} log.
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
                program.updateLog(JobLogMessage.FINISHED, this.getName(), ship.getName());
                isFinished = true;
                break;
            case CANCELED:
                statusPanel.setBackground(Color.RED);
                program.updateLog(JobLogMessage.CANCELED, this.getName(), ship.getName());
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
            canRun();
            while (isWaiting()) {
                setStatus(JobStatus.WAITING);
                try {
                    port.wait();
                } catch (InterruptedException ignored) {}
            }
            program.updateResourceDisplay();
            ship.setIsBusy(true);
        }

        program.updateLog(JobLogMessage.STARTED, this.getName(), ship.getName());

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
            setStatus(JobStatus.CANCELED);
        } else {
            setStatus(JobStatus.DONE);
        }

        isFinished = true;
        jobsTable.tableChanged(new TableModelEvent(jobsTableModel));

        synchronized (port) {
            deallocateWorkers();
            program.updateResourceDisplay();
            ship.setIsBusy(false);
            for (Job jobs : ship.getJobs()) {
                if (!(jobs.isFinished)) {
                    port.notifyAll();
                    return;
                }
            }

            Dock dock = ship.getDock();

            if (dock != null) {
                program.updateLog(JobLogMessage.DEPARTED, dock.getName(), ship.getName());
                dock.setShip(null);

                if (!port.getQueue().isEmpty()){
                    Ship newShip = port.getQueue().remove(0);
                    dock.setShip(newShip);
                    newShip.setDock(dock);
                    program.updateLog(JobLogMessage.ARRIVED, dock.getName(), newShip.getName());
                }
            } else {
                program.updateLog(JobLogMessage.DEPARTED, ship.getPort().getName(), ship.getName());
            }

            port.notifyAll();
        }
    }

    /**
     * Helper method for {@link #run() run}.  Checks to see if {@code Job} requirements can ever be filled.  If not,
     * the {@code Job} can never run, therefore it is canceled.
     */
    private void canRun() {
        boolean canRun = hasRequiredWorkers(port.getPersons());
        if (!canRun) {
            cancelJob();
        }
    }

    /**
     * Helper method for {@link #run() run}.  Each {@code Job} must wait for the proper conditions before executing.
     * This method checks to see if the {@code Job} needs to continue waiting.
     *
     * <br/><br/>
     *
     * If all of the following conditions are true:
     *
     * <ol>
     *     <li>
     *         The {@code Job}'s parent {@link Ship}:
     *         <ol type="a">
     *             <li>Is located at a {@link Dock}</li>
     *             <li>Is not busy</li>
     *         </ol>
     *     </li>
     *     <li>
     *         The {@code Job}'s parent {@link SeaPort#getResourcePool() resourcePool}:
     *         <ol type="a">
     *             <li>Has the required workers</li>
     *             <li>Can allocate the required workers</li>
     *         </ol>
     *     </li>
     * </ol>
     *
     * Then the {@code Job} should no longer wait to execute.
     *
     * @return {@code true} if the {@code Job} must continue to wait.  <br/>
     * {@code false} if the {@code Job} no longer needs to wait.
     */
    private synchronized boolean isWaiting() {
        if (!isRunning) {
            return false;
        }

        if (ship.getIsBusy() || ship.getDock() == null) {
            return true;
        } else {
            if (!requirements.isEmpty()) {
                // if Job has requirements, Job should NOT wait if workers CAN BE allocated
                boolean canAllocate = hasRequiredWorkers(port.getResourcePool());
                if (canAllocate) {
                    allocateWorkers();
                    notifyAll();
                }
                return !canAllocate;
            } else {
                return false;
            }
        }
    }

    /**
     * Helper method for both {@link #canRun() canRun} and {@link #isWaiting() isWaiting}.  This method searches an
     * {@link ArrayList} of {@link Person Persons} for the required workers.
     *
     * @param workers {@link ArrayList} of {@link Person Persons} to be searched.
     * @return {@code true} if all of the required workers are found.  <br/>
     * {@code false} if all of the required workers are NOT found.
     */
    private boolean hasRequiredWorkers(ArrayList<Person> workers) {
        ArrayList<Integer> indexes = new ArrayList<>();
        for (String requirement : requirements) {
            boolean workerFound = false;
            for (Person person : workers) {
                if (person.getSkill().equals(requirement) && !indexes.contains(person.getIndex())){
                    workerFound = true;
                    indexes.add(person.getIndex());
                    break;
                }
            }
            if (!workerFound) {
                program.updateLog(JobLogMessage.RESOURCES_REQUIRED, requirement, ship.getName());
                return false;
            }
        }
        return true;
    }

    /**
     * Helper method for {@link #isWaiting() isWaiting}.  Allocates the required workers to the appropriate ship.
     * Removes them from the {@link SeaPort#getResourcePool() resourcePool}.
     */
    private void allocateWorkers() {
        ArrayList<Person> workers = new ArrayList<>();
        for (String requirement : requirements) {
            for (Person worker : port.getResourcePool()) {
                if (worker.getSkill().equals(requirement) && worker.getStatus() == WorkerStatus.AVAILABLE){
                    worker.setLocation("Ship: " + ship.getName());
                    worker.setStatus(WorkerStatus.WORKING);
                    workers.add(worker);
                    break;
                }
            }
        }
        ship.setWorkers(workers);
        port.getResourcePool().removeAll(workers);
        program.updateResourceDisplay();
    }

    /**
     * Helper method for {@link #run() isWaiting}.  Deallocates all workers from the appropriate ship.  Returns
     * them to the {@link SeaPort#getResourcePool() resourcePool}.
     */
    private void deallocateWorkers() {
        ArrayList<Person> workers = new ArrayList<>();
        for (Person worker : ship.getWorkers()) {
            worker.setLocation("Port: " + port.getName());
            worker.setStatus(WorkerStatus.AVAILABLE);
            port.getResourcePool().add(worker);
            workers.add(worker);
        }
        ship.getWorkers().removeAll(workers);
        program.updateResourceDisplay();
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

package SeaPortProject;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Filename :   Job
 * Author :     William Crutchfield
 * Date:        2/18/2019
 * Description: Creates and Runs Jobs on separate Threads
 */
class Job extends Thing implements Runnable {

    private final SeaPort port;
    private Ship ship;
    private JTable jobsTable;
    private DefaultTableModel jobsTableModel;

    private Status status = Status.SUSPENDED;
    private boolean isRunning = true;
    private boolean isCanceled = false;
    private boolean isFinished = false;
    private double duration;
    private Thread thread;

    private enum Status {RUNNING, SUSPENDED, WAITING, DONE, CANCELLED}

    // GUI Variables
    private SeaPortProgram program;
    private JPanel statusPanel;
    private JPanel progressPanel;
    private JPanel suspendPanel;
    private JPanel cancelPanel;
    private JLabel statusLabel;
    private JProgressBar progressBar;

    Job(Scanner sc, SeaPortProgram program, HashMap<Integer, Ship> shipsHashMap) {

        super(sc);
        if (sc.hasNextDouble()) duration = sc.nextDouble();

        this.program = program;
        jobsTable = program.getjobsTable();
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

    private void toggleIsRunning() {
        isRunning = !isRunning;
    }

    private void cancelJob() {
        isCanceled = true;
        isRunning = false;
    }

    private void setStatus(Status status) {
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

    JPanel getStatusPanel() {
        return statusPanel;
    }

    JPanel getProgressPanel() {
        return progressPanel;
    }

    JPanel getSuspendPanel() {
        return suspendPanel;
    }

    JPanel getCancelPanel() {
        return cancelPanel;
    }

    Thread getThread() {
        return thread;
    }

    @Override
    public void run() {
        long currentTime = System.currentTimeMillis();
        long startTime = currentTime;
        long stopTime = (long) (startTime + duration * 1000);

        synchronized (port) {
            while (ship.getIsBusy() || ship.getDock() == null) {
                setStatus(Status.WAITING);
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
                setStatus(Status.RUNNING);
                startTime += 100;
                progressBar.setValue((int)(((startTime - currentTime) / duration) * 100));
            } else {
                setStatus(Status.SUSPENDED);
            }
            jobsTable.tableChanged(new TableModelEvent(jobsTableModel));
        }

        if (isCanceled && status != Status.DONE) {
            setStatus(Status.CANCELLED);
        } else {
            setStatus(Status.DONE);
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

    public String toString() {
        return super.toString();
    }
}

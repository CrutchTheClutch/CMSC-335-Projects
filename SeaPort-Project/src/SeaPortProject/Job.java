package SeaPortProject;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import java.util.Scanner;

/**
 * Filename :   Job
 * Author :     William Crutchfield
 * Date:        2/18/2019
 * Description:
 */
class Job extends Thing implements Runnable {

    private SeaPortProgram program;
    private JTable jobsTable;
    private JProgressBar progressBar = new JProgressBar (0, 100000);
    private TableModelEvent progressBarUpdateEvent;

    private double duration;
    private Status status = Status.SUSPENDED;

    private enum Status {RUNNING, SUSPENDED, WAITING, DONE}

    Job(Scanner sc, SeaPortProgram program) {
        super(sc);
        if (sc.hasNextDouble()) duration = sc.nextDouble();

        this.program = program;
        this.jobsTable = program.getJobsTable();
        progressBarUpdateEvent = new TableModelEvent(jobsTable.getModel());
    }

    JProgressBar getProgressBar() {
        return progressBar;
    }

    Status getStatus() {
        return status;
    }

    public void run() {
        long currentTime = System.currentTimeMillis();
        long startTime = currentTime;
        long stopTime = (long) (startTime + duration * 1000);
        int progress = 0;

        program.updateLog("Job " + this.getName() + " is RUNNING");

        while (startTime < stopTime) {
            try {
                Thread.sleep (100);
            } catch (InterruptedException ignored) {}


//            if (goFlag) {
//                showStatus (Status.RUNNING);
                startTime += 100;
                progress = ((int)(((startTime - currentTime) / duration) * 100));
                progressBar.setValue(progress);
                jobsTable.tableChanged(progressBarUpdateEvent);
//            } else {
//                showStatus (Status.SUSPENDED);
//            }
        }

        System.out.println(this.getName() + " is DONE ");

        program.updateLog("Job " + this.getName() + " is DONE");
    }

    public String toString() {
        return super.toString();
    }
}

package SeaPortProject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * <strong>Filename:</strong> &emsp;&emsp;&emsp; {@code Ship} <br/>
 * <strong>Author:</strong> &emsp;&emsp;&emsp;&emsp; William Crutchfield <br/>
 * <strong>Date Created:</strong> &emsp; January 26th, 2019 <br/>
 *
 * <br/>
 *
 * Defines the {@code Ship} object.
 *
 * @author  William Crutchfield
 * @see     CargoShip
 * @see     PassengerShip
 */
class Ship extends Thing {

    private SeaPort port;
    private Dock dock;
    private ArrayList <Job> jobs;
    private PortTime arrivalTime, dockTime;
    private double weight, length, width, draft;
    private boolean isBusy = false;

    /**
     * Constructor for {@code Ship}.
     *
     * @param sc            The current text file data.
     * @param portsHashMap  HashMap containing all {@link SeaPort SeaPorts} and their respective {@code index} values.
     * @param docksHashMap  HashMap containing all {@link Dock Docks} and their respective {@code index} values.
     */
    Ship(Scanner sc, HashMap<Integer, SeaPort> portsHashMap, HashMap<Integer, Dock> docksHashMap) {
        super(sc);
        if (sc.hasNextDouble()) weight = sc.nextDouble();
        if (sc.hasNextDouble()) length = sc.nextDouble();
        if (sc.hasNextDouble()) width = sc.nextDouble();
        if (sc.hasNextDouble()) draft = sc.nextDouble();
        jobs = new ArrayList<>();

        dock = docksHashMap.get(this.getParent());
        if (dock == null) {
            port = portsHashMap.get(this.getParent());
        } else {
            port = portsHashMap.get(dock.getParent());
        }
    }

    /**
     * Getter method for {@code jobs}.  All {@link Job Jobs} within the {@code Ship}.
     *
     * @return Current {@code jobs}.
     */
    ArrayList<Job> getJobs() {
        return jobs;
    }

    /**
     * Setter method for {@code jobs}.  All {@link Job Jobs} within the {@code Ship}.
     *
     * @param jobs New {@code jobs}.
     */
    void setJobs(ArrayList<Job> jobs) {
        this.jobs = jobs;
    }

    /**
     * Getter method for {@code arrivalTime}.  {@link PortTime} when the {@code Ship} arrived at the {@code Dock}.
     *
     * @return Current {@code arrivalTime}.
     */
    PortTime getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Setter method for {@code arrivalTime}.  {@link PortTime} when the {@code Ship} arrived at the {@code Dock}.
     *
     * @param arrivalTime New {@code arrivalTime}.
     */
    void setArrivalTime(PortTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * Getter method for {@code dockTime}.  {@link PortTime} of duration {@code Ship} spent at {@code Dock}.
     *
     * @return Current {@code dockTime}.
     */
    PortTime getDockTime() {
        return dockTime;
    }

    /**
     * Setter method for {@code dockTime}.  {@link PortTime} of duration {@code Ship} spent at {@code Dock}.
     *
     * @param dockTime New {@code dockTime}.
     */
    void setDockTime(PortTime dockTime) {
        this.dockTime = dockTime;
    }

    /**
     * Getter method for {@code weight}.
     *
     * @return Current {@code weight}.
     */
    double getWeight() {
        return weight;
    }

    /**
     * Setter method for {@code weight}.
     *
     * @param weight New {@code weight}.
     */
    void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Getter method for {@code length}.
     *
     * @return Current {@code length}.
     */
    double getLength() {
        return length;
    }

    /**
     * Setter method for {@code length}.
     *
     * @param length New {@code length}.
     */
    void setLength(double length) {
        this.length = length;
    }

    /**
     * Getter method for {@code width}.
     *
     * @return Current {@code width}.
     */
    double getWidth() {
        return width;
    }

    /**
     * Setter method for {@code width}.
     *
     * @param width New {@code width}.
     */
    void setWidth(double width) {
        this.width = width;
    }

    /**
     * Getter method for {@code draft}.
     *
     * @return Current {@code draft}.
     */
    double getDraft() {
        return draft;
    }

    /**
     * Setter method for {@code draft}.
     *
     * @param draft New {@code draft}.
     */
    void setDraft(double draft) {
        this.draft = draft;
    }

    /**
     * Getter method for {@code port}.
     *
     * @return Current {@code port}.
     */
    SeaPort getPort() {
        return port;
    }

    /**
     * Setter method for {@code port}.
     *
     * @param port New {@code port}.
     */
    void setPort(SeaPort port) {
        this.port = port;
    }

    /**
     * Getter method for {@code dock}.
     *
     * @return Current {@code dock}.
     */
    Dock getDock() {
        return dock;
    }

    /**
     * Setter method for {@code dock}.
     *
     * @param dock New {@code dock}.
     */
    void setDock(Dock dock) {
        this.dock = dock;
    }

    /**
     * Getter method for {@code isBusy}.
     *
     * @return Current {@code isBusy}.
     */
    boolean getIsBusy() {
        return isBusy;
    }

    /**
     * Setter method for {@code isBusy}.
     *
     * @param isBusy New {@code isBusy}.
     */
    void setIsBusy(boolean isBusy) {
        this.isBusy = isBusy;
    }

    /**
     * Returns a string representation of the {@code Ship}.
     *
     * @return Formatted string of the {@code Ship} object.
     */
    @Override
    public String toString() {
        return super.toString() + " "
                + this.getWeight() + " "
                + this.getLength() + " "
                + this.getWidth() + " "
                + this.getDraft();
    }
}

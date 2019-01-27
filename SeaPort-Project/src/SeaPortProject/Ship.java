package SeaPortProject;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Filename :   Ship
 * Author :     William Crutchfield
 * Date:        1/26/2019
 * Description: Defines the Ship Object
 */
class Ship extends Thing {

    private ArrayList <Job> jobs;
    private PortTime arrivalTime;
    private PortTime dockTime;
    private double weight;
    private double length;
    private double width;
    private double draft;

    /**
     * Constructs the Ship Object
     * @param sc a file Scanner of the current text file
     */
    Ship(Scanner sc) {
        super(sc);
        if (sc.hasNextDouble()) weight = sc.nextDouble();
        if (sc.hasNextDouble()) length = sc.nextDouble();
        if (sc.hasNextDouble()) width = sc.nextDouble();
        if (sc.hasNextDouble()) draft = sc.nextDouble();
    }

    /**
     * Getter method for the current Jobs in the Ship
     * @return ArrayList of Jobs in the Ship
     */
    ArrayList<Job> getJobs() {
        return jobs;
    }

    /**
     * Setter method for the current Jobs in the Ship
     * @param jobs ArrayList of Jobs in the Ship
     */
    void setJobs(ArrayList<Job> jobs) {
        this.jobs = jobs;
    }

    /**
     * Getter method for the Arrival Time of the Ship
     * @return PortTime of Ship Arrival Time
     */
    PortTime getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Setter method for the Arrival Time of the Ship
     * @param arrivalTime PortTime of Ship Arrival Time
     */
    void setArrivalTime(PortTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * Getter method for the Dock Time of the Ship
     * @return PortTime of Ship Dock Time
     */
    PortTime getDockTime() {
        return dockTime;
    }

    /**
     * Setter method for the Dock Time of the Ship
     * @param dockTime PortTime of Ship Dock Time
     */
    void setDockTime(PortTime dockTime) {
        this.dockTime = dockTime;
    }

    /**
     * Getter method for the Weight of the Ship
     * @return Weight of Ship
     */
    double getWeight() {
        return weight;
    }

    /**
     * Setter method for the Weight of the Ship
     * @param weight Weight of Ship
     */
    void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Getter method for the Length of the Ship
     * @return Length of Ship
     */
    double getLength() {
        return length;
    }

    /**
     * Setter method for the Length of the Ship
     * @param length Length of Ship
     */
    void setLength(double length) {
        this.length = length;
    }

    /**
     * Getter method for the Width of the Ship
     * @return Width of Ship
     */
    double getWidth() {
        return width;
    }

    /**
     * Setter method for the Width of the Ship
     * @param width Width of Ship
     */
    void setWidth(double width) {
        this.width = width;
    }

    /**
     * Getter method for the Draft of the Ship
     * @return Draft of Ship
     */
    double getDraft() {
        return draft;
    }

    /**
     * Setter method for the Draft of the Ship
     * @param draft Draft of Ship
     */
    void setDraft(double draft) {
        this.draft = draft;
    }

    /**
     * toString method
     * @return Formatted String of Ship
     */
    @Override
    public String toString() {
        return super.toString();
    }
}

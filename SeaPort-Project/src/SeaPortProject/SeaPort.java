package SeaPortProject;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Filename :   SeaPort
 * Author :     William Crutchfield
 * Date:        1/25/2019
 * Purpose:     Defines the SeaPort Object
 */
class SeaPort extends Thing {

    private ArrayList <Dock> docks;
    private ArrayList <Ship> queue;
    private ArrayList <Ship> ships;
    private ArrayList <Person> persons;

    /**
     * Constructs the SeaPort Object
     * @param sc a file Scanner of the current text file
     */
    SeaPort(Scanner sc) {
        super(sc);
        docks = new ArrayList<>();
        queue = new ArrayList<>();
        ships = new ArrayList<>();
        persons = new ArrayList<>();
    }

    /**
     * Getter method for the current Docks in the SeaPort
     * @return ArrayList of Docks
     */
    ArrayList<Dock> getDocks() {
        return docks;
    }

    /**
     * Setter method for the current Docks in the SeaPort
     * @param docks ArrayList of Docks
     */
    void setDocks(ArrayList<Dock> docks) {
        this.docks = docks;
    }

    /**
     * Getter method for the current Queue in the SeaPort
     * @return ArrayList of Ships in the Queue
     */
    ArrayList<Ship> getQueue() {
        return queue;
    }

    /**
     * Setter method for the current Queue in the SeaPort
     * @param queue ArrayList of Ships in the Queue
     */
    void setQueue(ArrayList<Ship> queue) {
        this.queue = queue;
    }

    /**
     * Getter method for the current Ships in the SeaPort
     * @return ArrayList of Ships in the SeaPort
     */
    ArrayList<Ship> getShips() {
        return ships;
    }

    /**
     * Setter method for the current Ships in the SeaPort
     * @param ships ArrayList of Ships in the SeaPort
     */
    void setShips(ArrayList<Ship> ships) {
        this.ships = ships;
    }

    /**
     * Getter method for the current Persons in the SeaPort
     * @return ArrayList of Persons in the SeaPort
     */
    ArrayList<Person> getPersons() {
        return persons;
    }

    /**
     * Setter method for the current Persons in the SeaPort
     * @param persons ArrayList of Persons in the SeaPort
     */
    void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

    /**
     * toString method
     * @return Formatted String of SeaPort
     */
    @Override
    public String toString () {
        StringBuilder out = new StringBuilder("\nSeaPort: " + super.toString());

        for (Dock dock: docks) {
            out.append("\n").append(dock);
        }

        out.append("\n\n --- List of all ships in que:");

        for (Ship ship: queue ) {
            out.append("\n   > ").append(ship);
        }

        out.append("\n\n --- List of all ships:");

        for (Ship ship: ships) {
            out.append("\n   > ").append(ship);
        }

        out.append("\n\n --- List of all persons:");

        for (Person person: persons) {
            out.append("\n   > ").append(person);
        }

        return out.toString();
    }
}

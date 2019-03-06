package SeaPortProject;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * <strong>Filename:</strong> &emsp;&emsp;&emsp; {@code SeaPort} <br/>
 * <strong>Author:</strong> &emsp;&emsp;&emsp;&emsp; William Crutchfield <br/>
 * <strong>Date Created:</strong> &emsp; January 25th, 2019 <br/>
 *
 * <br/>
 *
 * <p>Defines the {@code SeaPort} object. Contains {@link Dock Docks}, {@link Ship Ships}, and {@link Person Persons}.
 *
 * <br/><br/>
 *
 * <p>Note that each of the objects listed are contained in {@link ArrayList ArrayLists}, with {@link Ship Ships}
 * occupying two: one for {@link Ship Ships} in the {@code SeaPort}'s {@code Queue}, and the other for all
 * {@link Ship Ships} within the {@code SeaPort}.
 *
 * @author  William Crutchfield
 */
class SeaPort extends Thing {

    private ArrayList <Dock> docks;
    private ArrayList <Ship> queue;
    private ArrayList <Ship> ships;
    private ArrayList <Person> persons;

    /**
     * Constructor for {@code SeaPort}.
     *
     * @param sc The current text file data.
     */
    SeaPort(Scanner sc) {
        super(sc);
        docks = new ArrayList<>();
        queue = new ArrayList<>();
        ships = new ArrayList<>();
        persons = new ArrayList<>();
    }

    /**
     * Getter method for {@code docks}.  {@link Dock Docks} within the {@code SeaPort}.
     *
     * @return Current {@code docks}.
     */
    ArrayList<Dock> getDocks() {
        return docks;
    }

    /**
     * Setter method for {@code docks}.  {@link Dock Docks} within the {@code SeaPort}.
     *
     * @param docks New {@code docks}.
     */
    void setDocks(ArrayList<Dock> docks) {
        this.docks = docks;
    }

    /**
     * Getter method for {@code queue}.  {@link Ship} {@code queue} within the {@code SeaPort}.
     *
     * @return Current {@code queue} of {@link Ship Ships}.
     */
    ArrayList<Ship> getQueue() {
        return queue;
    }

    /**
     * Setter method for {@code queue}.  {@link Ship} {@code queue} within the {@code SeaPort}.
     *
     * @param queue New {@code queue} of {@link Ship Ships}.
     */
    void setQueue(ArrayList<Ship> queue) {
        this.queue = queue;
    }

    /**
     * Getter method for {@code ships}.  All {@link Ship Ships} within the {@code SeaPort}.
     *
     * @return Current {@code ships}.
     */
    ArrayList<Ship> getShips() {
        return ships;
    }

    /**
     * Setter method for {@code ships}.  All {@link Ship Ships} within the {@code SeaPort}.
     *
     * @param ships New {@code ships}.
     */
    void setShips(ArrayList<Ship> ships) {
        this.ships = ships;
    }

    /**
     * Getter method for {@code persons}.  All {@link Person Persons} within the {@code SeaPort}.
     *
     * @return Current {@code persons}.
     */
    ArrayList<Person> getPersons() {
        return persons;
    }

    /**
     * Setter method for {@code persons}.  All {@link Person Persons} within the {@code SeaPort}.
     *
     * @param persons New {@code persons}.
     */
    void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

    /**
     * Getter method for {@code resourcePool}.  All {@link Person Persons} within the {@code resourcePool}.
     *
     * @return Current {@code resourcePool}.
     */
    ArrayList<Person> getResourcePool() {
        return resourcePool;
    }

    /**
     * Setter method for {@code resourcePool}.  All {@link Person Persons} within the {@code resourcePool}.
     *
     * @param resourcePool New {@code resourcePool}.
     */
    void setResourcePool(ArrayList<Person> resourcePool) {
        this.resourcePool = resourcePool;
    }

    /**
     * Returns a string representation of the {@code SeaPort}.
     *
     * @return Formatted string of the {@code SeaPort} object.
     */
    @Override
    public String toString () {
        StringBuilder out = new StringBuilder("\nSeaPort: " + super.toString());

        out.append("\n\n --- List of all docks:");

        for (Dock dock: docks) {
            out.append("\n   > ").append(dock);
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

package SeaPortProject;

import java.util.HashMap;
import java.util.Scanner;

/**
 * <strong>Filename:</strong> &emsp;&emsp;&emsp; {@code PassengerShip} <br/>
 * <strong>Author:</strong> &emsp;&emsp;&emsp;&emsp; William Crutchfield <br/>
 * <strong>Date Created:</strong> &emsp; January 26th, 2019 <br/>
 *
 * <br/>
 *
 * <p>Defines the {@code PassengerShip} object.
 *
 * @author  William Crutchfield
 */
class PassengerShip extends Ship {

    private int numberOfPassengers;
    private int numberOfRooms;
    private int numberOfOccupiedRooms;

    /**
     * Constructor for {@code PassengerShip}.
     *
     * @param sc            The current text file data.
     * @param portsHashMap  HashMap containing all {@link SeaPort SeaPorts} and their respective {@code index} values.
     * @param docksHashMap  HashMap containing all {@link Dock Docks} and their respective {@code index} values.
     */
    PassengerShip(Scanner sc, HashMap<Integer, SeaPort> portsHashMap, HashMap<Integer, Dock> docksHashMap) {
        super(sc, portsHashMap, docksHashMap);
        if (sc.hasNextInt()) numberOfPassengers = sc.nextInt();
        if (sc.hasNextInt()) numberOfRooms = sc.nextInt();
        if (sc.hasNextInt()) numberOfOccupiedRooms = sc.nextInt();
    }

    /**
     * Getter method for {@code numberOfPassengers}.
     *
     * @return Current {@code numberOfPassengers}.
     */
    int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    /**
     * Setter method for {@code numberOfPassengers}.
     *
     * @param numberOfPassengers New {@code numberOfPassengers}.
     */
    void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    /**
     * Getter method for {@code numberOfRooms}.
     *
     * @return Current {@code numberOfRooms}.
     */
    int getNumberOfRooms() {
        return numberOfRooms;
    }

    /**
     * Setter method for {@code numberOfRooms}.
     *
     * @param numberOfRooms New {@code numberOfRooms}.
     */
    void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    /**
     * Getter method for {@code numberOfOccupiedRooms}.
     *
     * @return Current {@code numberOfOccupiedRooms}.
     */
    int getNumberOfOccupiedRooms() {
        return numberOfOccupiedRooms;
    }

    /**
     * Setter method for {@code numberOfOccupiedRooms}.
     *
     * @param numberOfOccupiedRooms New {@code numberOfOccupiedRooms}.
     */
    void setNumberOfOccupiedRooms(int numberOfOccupiedRooms) {
        this.numberOfOccupiedRooms = numberOfOccupiedRooms;
    }

    /**
     * Returns a string representation of the {@code PassengerShip}.
     *
     * @return Formatted string of the {@code PassengerShip} object.
     */
    @Override
    public String toString() {
        return "Passenger Ship: " + super.toString();
    }
}

package SeaPortProject;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Filename :   PassengerShip
 * Author :     William Crutchfield
 * Date:        1/26/2019
 * Description: Defines the PassengerShip Object
 */
class PassengerShip extends Ship {

    private int numberOfPassengers;
    private int numberOfRooms;
    private int numberOfOccupiedRooms;

    /**
     * Constructs the PassengerShip Object
     * @param sc a file Scanner of the current text file
     * @param portsHashMap
     * @param docksHashMap
     */
    PassengerShip(Scanner sc, HashMap<Integer, SeaPort> portsHashMap, HashMap<Integer, Dock> docksHashMap) {
        super(sc, portsHashMap, docksHashMap);
        if (sc.hasNextInt()) numberOfPassengers = sc.nextInt();
        if (sc.hasNextInt()) numberOfRooms = sc.nextInt();
        if (sc.hasNextInt()) numberOfOccupiedRooms = sc.nextInt();
    }

    /**
     * Getter method for numberOfPassengers in the PassengerShip
     * @return numberOfPassengers in the PassengerShip
     */
    int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    /**
     * Setter method for numberOfPassengers in the PassengerShip
     * @param numberOfPassengers numberOfPassengers in the PassengerShip
     */
    void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    /**
     * Getter method for numberOfRooms in the PassengerShip
     * @return numberOfRooms in the PassengerShip
     */
    int getNumberOfRooms() {
        return numberOfRooms;
    }

    /**
     * Setter method for numberOfRooms in the PassengerShip
     * @param numberOfRooms numberOfRooms in the PassengerShip
     */
    void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    /**
     * Getter method for numberOfOccupiedRooms in the PassengerShip
     * @return numberOfOccupiedRooms in the PassengerShip
     */
    int getNumberOfOccupiedRooms() {
        return numberOfOccupiedRooms;
    }

    /**
     * Setter method for numberOfOccupiedRooms in the PassengerShip
     * @param numberOfOccupiedRooms numberOfOccupiedRooms in the PassengerShip
     */
    void setNumberOfOccupiedRooms(int numberOfOccupiedRooms) {
        this.numberOfOccupiedRooms = numberOfOccupiedRooms;
    }

    /**
     * toString method
     * @return Formatted String of PassengerShip
     */
    @Override
    public String toString() {
        return "Passenger Ship: " + super.toString();
    }
}

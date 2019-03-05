package SeaPortProject;

import java.util.Scanner;
import java.util.HashMap;

/**
 * <strong>Filename:</strong> &emsp;&emsp;&emsp; {@code CargoShip} <br/>
 * <strong>Author:</strong> &emsp;&emsp;&emsp;&emsp; William Crutchfield <br/>
 * <strong>Date Created:</strong> &emsp; January 26th, 2019 <br/>
 *
 * <br/>
 *
 * <p>Defines the {@code CargoShip} object.
 *
 * @author  William Crutchfield
 */
class CargoShip extends Ship {

    private double cargoWeight;
    private double cargoVolume;
    private double cargoValue;

    /**
     * Constructor for {@code CargoShip}.
     *
     * @param sc            The current text file data.
     * @param portsHashMap  HashMap containing all {@link SeaPort SeaPorts} and their respective {@code index} values.
     * @param docksHashMap  HashMap containing all {@link Dock Docks} and their respective {@code index} values.
     */
    CargoShip(Scanner sc, HashMap<Integer, SeaPort> portsHashMap, HashMap<Integer, Dock> docksHashMap) {
        super(sc, portsHashMap, docksHashMap);
        if (sc.hasNextDouble()) cargoWeight = sc.nextDouble();
        if (sc.hasNextDouble()) cargoVolume = sc.nextDouble();
        if (sc.hasNextDouble()) cargoValue = sc.nextDouble();
    }

    /**
     * Getter method for {@code cargoWeight}.
     *
     * @return Current {@code cargoWeight}.
     */
    double getCargoWeight() {
        return cargoWeight;
    }

    /**
     * Setter method for {@code cargoWeight}.
     *
     * @param cargoWeight New {@code cargoWeight}.
     */
    void setCargoWeight(double cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    /**
     * Getter method for {@code cargoVolume}.
     *
     * @return Current {@code cargoVolume}.
     */
    double getCargoVolume() {
        return cargoVolume;
    }

    /**
     * Setter method for {@code cargoVolume}.
     *
     * @param cargoVolume New {@code cargoVolume}.
     */
    void setCargoVolume(double cargoVolume) {
        this.cargoVolume = cargoVolume;
    }

    /**
     * Getter method for {@code cargoValue}.
     *
     * @return Current {@code cargoValue}.
     */
    double getCargoValue() {
        return cargoValue;
    }

    /**
     * Setter method for {@code cargoValue}.
     *
     * @param cargoValue New {@code cargoValue}.
     */
    void setCargoValue(double cargoValue) {
        this.cargoValue = cargoValue;
    }

    /**
     * Returns a string representation of the {@code CargoShip}.
     *
     * @return Formatted string of the {@code CargoShip} object.
     */
    @Override
    public String toString() {
        return "Cargo Ship: " + super.toString();
    }
}

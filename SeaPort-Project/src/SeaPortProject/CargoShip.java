package SeaPortProject;

import java.util.Scanner;
import java.util.HashMap;

/**
 * Filename :   CargoShip
 * Author :     William Crutchfield
 * Date:        1/25/2019
 * Description: Defines the CargoShip Object
 */
class CargoShip extends Ship {

    private double cargoWeight;
    private double cargoVolume;
    private double cargoValue;

    /**
     * Constructs the CargoShip Object
     * @param sc a file Scanner of the current text file
     */
    CargoShip(Scanner sc, HashMap<Integer, SeaPort> portsHashMap, HashMap<Integer, Dock> docksHashMap) {
        super(sc, portsHashMap, docksHashMap);
        if (sc.hasNextDouble()) cargoWeight = sc.nextDouble();
        if (sc.hasNextDouble()) cargoVolume = sc.nextDouble();
        if (sc.hasNextDouble()) cargoValue = sc.nextDouble();
    }

    /**
     * Getter method for cargoWeight in the CargoShip
     * @return cargoWeight in the CargoShip
     */
    double getCargoWeight() {
        return cargoWeight;
    }

    /**
     * Setter method for cargoWeight in the CargoShip
     * @param cargoWeight cargoWeight in the CargoShip
     */
    void setCargoWeight(double cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    /**
     * Getter method for cargoVolume in the CargoShip
     * @return cargoVolume in the CargoShip
     */
    double getCargoVolume() {
        return cargoVolume;
    }

    /**
     * Setter method for cargoVolume in the CargoShip
     * @param cargoVolume cargoVolume in the CargoShip
     */
    void setCargoVolume(double cargoVolume) {
        this.cargoVolume = cargoVolume;
    }

    /**
     * Getter method for cargoValue in the CargoShip
     * @return cargoValue in the CargoShip
     */
    double getCargoValue() {
        return cargoValue;
    }

    /**
     * Setter method for cargoValue in the CargoShip
     * @param cargoValue cargoValue in the CargoShip
     */
    void setCargoValue(double cargoValue) {
        this.cargoValue = cargoValue;
    }

    /**
     * toString method
     * @return Formatted String of CargoShip
     */
    @Override
    public String toString() {
        return "Cargo Ship: " + super.toString();
    }
}

package SeaPortProject;

import java.util.Scanner;

/**
 * Filename :   Dock
 * Author :     William Crutchfield
 * Date:        1/26/2019
 * Description: Defines the Dock Object
 */
class Dock extends Thing {

    private Ship ship;

    /**
     * Constructs the Dock Object
     * @param sc a file Scanner of the current text file
     */
    Dock(Scanner sc) {
        super(sc);
    }

    /**
     * Getter method for the current Ship in the Dock
     * @return Ship in Dock
     */
    Ship getShip() {
        return ship;
    }

    /**
     * Setter method for the current Ship in the Dock
     * @param ship Ship in Dock
     */
    void setShip(Ship ship) {
        this.ship = ship;
    }

    /**
     * toString method
     * @return Formatted String of Dock
     */
    @Override
    public String toString() {
        return "Dock: " + super.toString() + "\n" + "        Ship: " + ship;
    }
}

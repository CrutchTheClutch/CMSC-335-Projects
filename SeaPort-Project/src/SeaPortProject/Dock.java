package SeaPortProject;

import java.util.Scanner;

/**
 * <strong>Filename:</strong> &emsp;&emsp;&emsp; {@code Dock} <br/>
 * <strong>Author:</strong> &emsp;&emsp;&emsp;&emsp; William Crutchfield <br/>
 * <strong>Date Created:</strong> &emsp; January 26th, 2019 <br/>
 *
 * <br/>
 *
 * <p>Defines the {@code Dock} object.  Contains a single {@link Ship}.
 *
 * @author  William Crutchfield
 */
class Dock extends Thing {

    private Ship ship;

    /**
     * Constructor for {@code Dock}.
     *
     * @param sc The current text file data.
     */
    Dock(Scanner sc) {
        super(sc);
    }

    /**
     * Getter method for {@code ship}.  The {@link Ship} within the {@code Dock}.
     *
     * @return Current {@code ship}.
     */
    Ship getShip() {
        return ship;
    }

    /**
     * Setter method for {@code ship}.  The {@link Ship} within the {@code Dock}.
     *
     * @param ship New {@code ship}.
     */
    void setShip(Ship ship) {
        this.ship = ship;
    }

    /**
     * Returns a string representation of the {@code Dock}.
     *
     * @return Formatted string of the {@code Dock} object.
     */
    @Override
    public String toString() {
        return "Dock: " + super.toString() + "\n" + "        Ship: " + ship;
    }
}

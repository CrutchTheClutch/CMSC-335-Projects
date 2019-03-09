package SeaPortProject;

import java.util.Comparator;

/**
 * <strong>Filename:</strong> &emsp;&emsp;&emsp; {@code ShipComparator} <br/>
 * <strong>Author:</strong> &emsp;&emsp;&emsp;&emsp; William Crutchfield <br/>
 * <strong>Date Created:</strong> &emsp; February 7th, 2019 <br/>
 *
 * <br/>
 *
 * Defines the {@code ShipComparator} object.  Is used to compare two {@link Ship} objects.  If two ships are compared, and
 * weight, length, width, and draft are all equal; then {@link ThingComparator} is called.  Technically extending
 * {@link ThingComparator}, but does not do so explicitly.
 *
 * @author  William Crutchfield
 */
public class ShipComparator implements Comparator<Ship> {

    private String target;
    private ThingComparator thingComparator;

    /**
     * ShipComparator Constructor
     * @param target {@code Ship} compare parameter
     */
    ShipComparator(String target) {
        this.target = target;
        thingComparator = new ThingComparator(target);
    }

    /**
     * Getter method for the current compare parameter
     * @return compare parameter
     */
    String getTarget() {
        return target;
    }

    /**
     * Setter method for the current compare parameter
     * @param target compare parameter
     */
    void setTarget(String target) {
        this.target = target;
    }

    /**
     * Ship Compare method, implemented from the Comparator Interface
     * @param ship1 first {@code Ship} to be compared
     * @param ship2 second {@code Ship} to be compared
     * @return 0 if ship1's target is equal to ship2's target;
     * a value less than 0 if ship1's target is less than ship2's target;
     * a value greater than 0 if ship1's target is greater than ship2's target.
     */
    @Override
    public int compare(Ship ship1, Ship ship2) {
        switch(target) {
            case "weight":
                return Double.compare(ship1.getWeight(), ship2.getWeight());
            case "length":
                return Double.compare(ship1.getLength(), ship2.getLength());
            case "width":
                return Double.compare(ship1.getWidth(), ship2.getWidth());
            case "draft":
                return Double.compare(ship1.getDraft(), ship2.getDraft());
        }

        return thingComparator.compare(ship1, ship2);
    }
}

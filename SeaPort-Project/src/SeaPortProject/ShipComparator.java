package SeaPortProject;

import java.util.Comparator;

public class ShipComparator implements Comparator<Ship> {

    private String target;
    private ThingComparator thingComparator;

    /**
     * ShipComparator Constructor
     * @param target <code>Ship</code> compare parameter
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
     * @param ship1 first <code>Ship</code> to be compared
     * @param ship2 second <code>Ship</code> to be compared
     * @return 0 if ship1's target is equal to ship2's target;
     * a value less than 0 if ship1's target is less than ship2's target;
     * a value greater than 0 if ship1's target is greater than ship2's target.
     */
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

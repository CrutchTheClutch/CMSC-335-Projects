package SeaPortProject;

import java.util.Comparator;

public class ThingComparator implements Comparator<Thing> {

    private String target;

    /**
     * ThingComparator Constructor
     * @param target <code>Thing</code> compare parameter
     */
    ThingComparator(String target) {
        this.target = target;
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
     * @param thing1 first <code>Thing</code> to be compared
     * @param thing2 second <code>Thing</code> to be compared
     * @return 0 if thing1's target is equal to thing2's target;
     * a value less than 0 if thing1's target is less than thing2's target;
     * a value greater than 0 if thing1's target is greater than thing2's target.
     */
    @Override
    public int compare(Thing thing1, Thing thing2) {
        switch(target) {
            case "index":
                return Integer.compare(thing1.getIndex(), thing2.getIndex());
            case "name":
                return thing1.getName().compareTo(thing2.getName());
        }
        return 0;
    }
}

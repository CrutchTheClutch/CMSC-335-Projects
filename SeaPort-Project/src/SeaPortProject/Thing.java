package SeaPortProject;

import java.util.Scanner;

/**
 * Filename :   Thing
 * Author :     William Crutchfield
 * Date:        1/25/2019
 * Purpose:     Defines the <code>Thing</code> Object, is the parent of all other objects in this package
 */
public class Thing implements Comparable <Thing> {

    private String name;
    private int index = 0;
    private int parent = 0;

    /**
     * Constructor for the <code>Thing</code> Object
     * @param sc parameters that are read in from a text file
     */
    Thing(Scanner sc) {
        if (sc.hasNext()) name = sc.next();
        if (sc.hasNextInt()) index = sc.nextInt();
        if (sc.hasNextInt()) parent = sc.nextInt();
    }

    /**
     * Getter method for the <code>name</code> of the <code>Thing</code>
     * @return the <code>Thing</code> Objects <code>name</code>
     */
    String getName() {
        return name;
    }

    /**
     * Setter method for the <code>name</code> of the <code>Thing</code>
     * @param name the <code>Thing</code> Objects <code>name</code>
     */
    void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for the <code>index</code> of the <code>Thing</code>
     * @return the <code>Thing</code> Objects <code>index</code>
     */
    int getIndex() {
        return index;
    }

    /**
     * Setter method for the <code>name</code> of the <code>Thing</code>
     * @param index the <code>Thing</code> Objects <code>index</code>
     */
    void setIndex(int index) {
        this.index = index;
    }

    /**
     * Getter method for the <code>name</code> of the <code>Thing</code>
     * @return the <code>Thing</code> Objects <code>parent</code>
     */
    int getParent() {
        return parent;
    }

    /**
     * Setter method for the <code>name</code> of the <code>Thing</code>
     * @param parent the <code>Thing</code> Objects <code>parent</code>
     */
    void setParent(int parent) {
        this.parent = parent;
    }

    /**
     * toString method
     * @return Formatted string of <code>Thing</code>
     */
    public String toString() {
        return name + " " + index;
    }

    /**
     * compareTo method
     * @param t <code>Thing</code> Object to be compared
     * @return 0 if <code>Thing</code> is equal to <code>t</code>;
     * a value less than 0 if <code>Thing</code> is less than <code>t</code>;
     * a value greater than 0 if <code>Thing</code> is greater than <code>t</code>.
     */
    @Override
    public int compareTo(Thing t) {
        int indexCompare = Integer.compare(this.index, t.index);
        if (indexCompare != 0) {
            return indexCompare;
        }

        return this.name.compareTo(t.name);
    }
}
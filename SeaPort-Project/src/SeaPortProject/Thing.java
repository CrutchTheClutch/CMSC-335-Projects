package SeaPortProject;

import java.util.Scanner;

/**
 * <strong>Filename:</strong> &emsp;&emsp;&emsp; {@code Thing} <br/>
 * <strong>Author:</strong> &emsp;&emsp;&emsp;&emsp; William Crutchfield <br/>
 * <strong>Date Created:</strong> &emsp; January 25th, 2019 <br/>
 *
 * <br/>
 *
 * Defines the {@code Thing} object.  Is the foundation for the {@link World} object, and all objects within
 * it.  Ensures that all objects in the {@link World} have a name, index, and parent.  Ensures the {@link World} has
 * a name and index.
 *
 * @author  William Crutchfield
 * @see     SeaPort
 * @see     Dock
 * @see     Ship
 * @see     Person
 * @see     Job
 */
public class Thing implements Comparable <Thing> {

    private String name;
    private int index = 0;
    private int parent = 0;

    /**
     * Constructor for {@code Thing}.
     *
     * @param sc The current text file data.
     */
    Thing(Scanner sc) {
        if (sc.hasNext()) name = sc.next();
        if (sc.hasNextInt()) index = sc.nextInt();
        if (sc.hasNextInt()) parent = sc.nextInt();
    }

    /**
     * Getter method for {@code name}.
     *
     * @return Current {@code name}.
     */
    String getName() {
        return name;
    }

    /**
     * Setter method for {@code name}.
     *
     * @param name New {@code name}.
     */
    void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for {@code index}.
     *
     * @return Current {@code index}.
     */
    int getIndex() {
        return index;
    }

    /**
     * Setter method for {@code index}.
     *
     * @param index New {@code index}.
     */
    void setIndex(int index) {
        this.index = index;
    }

    /**
     * Getter method for {@code parent}.  Index of parent object of {@code Thing}.
     *
     * @return Current {@code parent}.
     */
    int getParent() {
        return parent;
    }

    /**
     * Setter method for {@code parent}.  Index of parent object of {@code Thing}.
     *
     * @param parent New {@code parent}.
     */
    void setParent(int parent) {
        this.parent = parent;
    }

    /**
     * Returns a string representation of the {@code Thing}.
     *
     * @return Formatted string of the {@code Thing} object.
     */
    public String toString() {
        return name + " " + index;
    }

    /**
     * Compares the current {@code Thing} to {@code t}.
     *
     * @param t     {@code Thing} to be compared
     * @return      0 if current {@code Thing} is equal to {@code t}; a value less than 0 if current {@code Thing} is
     *              less than {@code t}; a value greater than 0 if current {@code Thing} is greater than {@code t}.
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
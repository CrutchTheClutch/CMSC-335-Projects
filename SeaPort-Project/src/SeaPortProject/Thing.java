package SeaPortProject;

import java.util.Scanner;

/**
 * Filename :   Thing
 * Author :     William Crutchfield
 * Date:        1/25/2019
 * Purpose:     Defines the Thing Object, is the parent of all other objects in this package
 */
public class Thing implements Comparable <Thing> {

    private String name;
    private int index = 0;
    private int parent = 0;

    Thing(Scanner sc) {
        if (sc.hasNext()) name = sc.next();
        if (sc.hasNextInt()) index = sc.nextInt();
        if (sc.hasNextInt()) parent = sc.nextInt();
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    int getIndex() {
        return index;
    }

    void setIndex(int index) {
        this.index = index;
    }

    int getParent() {
        return parent;
    }

    void setParent(int parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return name + " " + index;
    }

    @Override
    public int compareTo(Thing o) {
        return 0;
    }
}
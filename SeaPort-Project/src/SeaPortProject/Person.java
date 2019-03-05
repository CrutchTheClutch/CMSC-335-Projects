package SeaPortProject;

import java.util.Scanner;

/**
 * <strong>Filename:</strong> &emsp;&emsp;&emsp; {@code Person} <br/>
 * <strong>Author:</strong> &emsp;&emsp;&emsp;&emsp; William Crutchfield <br/>
 * <strong>Date Created:</strong> &emsp; January 26th, 2019 <br/>
 *
 * <br/>
 *
 * <p>Defines the {@code Person} object.
 *
 * @author  William Crutchfield
 */
class Person extends Thing {

    private String skill;

    /**
     * Constructor for {@code Person}.
     *
     * @param sc The current text file data.
     */
    Person(Scanner sc) {
        super(sc);
        if (sc.hasNext()) skill = sc.next();
    }

    /**
     * Getter method for {@code skill}.
     *
     * @return Current {@code skill}.
     */
    String getSkill() {
        return skill;
    }

    /**
     * Setter method for {@code skill}.
     *
     * @param skill New {@code skill}.
     */
    void setSkill(String skill) {
        this.skill = skill;
    }

    /**
     * Returns a string representation of the {@code Person}.
     *
     * @return Formatted string of the {@code Person} object.
     */
    @Override
    public String toString() {
        return "Person: " + super.toString() + " " + skill;
    }
}

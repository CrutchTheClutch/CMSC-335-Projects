package SeaPortProject;

import java.util.Scanner;

/**
 * Filename :   Person
 * Author :     William Crutchfield
 * Date:        1/26/2019
 * Description: Defines the Person Object
 */
class Person extends Thing {

    private String skill;

    /**
     * Constructs the Person Object
     * @param sc a file Scanner of the current text file
     */
    Person(Scanner sc) {
        super(sc);
        if (sc.hasNext()) skill = sc.next();
    }

    /**
     * Getter method for the Skill of the Person
     * @return Skill of the Person
     */
    String getSkill() {
        return skill;
    }

    /**
     * Setter method for the Skill of the Person
     * @param skill Skill of the Person
     */
    void setSkill(String skill) {
        this.skill = skill;
    }

    /**
     * toString method
     * @return Formatted String of Person
     */
    @Override
    public String toString() {
        return "Person: " + super.toString() + " " + skill;
    }
}

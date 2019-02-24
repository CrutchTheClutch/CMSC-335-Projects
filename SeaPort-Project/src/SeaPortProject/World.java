package SeaPortProject;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Filename :   World
 * Author :     William Crutchfield
 * Date:        1/25/2019
 * Purpose:     Defines the World Object
 */
public class World extends Thing {

    private SeaPortProgram program;
    private ArrayList <SeaPort> ports;
    private PortTime time;
    private JTable jobsTable;

    /**
     * Constructs the World Object
     * @param sc a file Scanner of the current text file
     */
    World(Scanner sc, SeaPortProgram program, JTable jobsTable) {
        super(sc);
        this.program = program;
        ports = new ArrayList<>();
        this.jobsTable = jobsTable;
    }

    /**
     * Creates the various objects from the text file
     * @param file text file that is processed
     */
    void process(Scanner file) {

        HashMap<Integer, SeaPort> portsHashMap = new HashMap<>();
        HashMap<Integer, Dock> docksHashMap = new HashMap<>();
        HashMap<Integer, Ship> shipsHashMap = new HashMap<>();
        HashMap<Integer, Person> personsHashMap = new HashMap<>();
        HashMap<Integer, Job> jobsHashMap = new HashMap<>();

        while (file.hasNextLine()) {

            String line = file.nextLine().trim();
            Scanner sc = new Scanner(line);

            if (sc.hasNext() && !line.startsWith("//")) {
                switch (sc.next()) {
                    case "port":
                        SeaPort seaPort = new SeaPort(sc);
                        addPort(portsHashMap, seaPort);
                        break;
                    case "dock":
                        Dock dock = new Dock(sc);
                        addDock(portsHashMap, docksHashMap, dock);
                        break;
                    case "pship":
                        PassengerShip pShip = new PassengerShip(sc, portsHashMap, docksHashMap);
                        addShip(portsHashMap, docksHashMap, shipsHashMap, pShip);
                        break;
                    case "cship":
                        CargoShip cShip = new CargoShip(sc, portsHashMap, docksHashMap);
                        addShip(portsHashMap, docksHashMap, shipsHashMap, cShip);
                        break;
                    case "person":
                        Person person = new Person(sc);
                        addPerson(portsHashMap, personsHashMap, person);
                        break;
                    case "job":
                        Job job = new Job(sc, program, shipsHashMap, jobsTable);
                        addJob(shipsHashMap, jobsHashMap, job);
                        break;
                }
            }
        }
    }

    /**
     * Adds a SeaPort to the World
     * @param port SeaPort that will be added
     */
    private void addPort(HashMap<Integer, SeaPort> portsHashMap, SeaPort port) {
        this.getPorts().add(port);
        portsHashMap.put(port.getIndex(), port);
    }

    /**
     * Adds a Dock to the World
     * @param portsHashMap HashMap containing <code>SeaPort</code> Objects and <code>index</code> values
     * @param dock Dock that will be added
     */
    private void addDock(HashMap<Integer, SeaPort> portsHashMap, HashMap<Integer, Dock> docksHashMap, Dock dock) {
        SeaPort port = portsHashMap.get(dock.getParent());
        port.getDocks().add(dock);
        docksHashMap.put(dock.getIndex(), dock);
    }

    /**
     * Adds a Ship to the World
     * @param portsHashMap HashMap containing <code>SeaPort</code> Objects and <code>index</code> values
     * @param docksHashMap HashMap containing <code>Dock</code> Objects and <code>index</code> values
     * @param ship Ship that will be added
     */
    private void addShip(HashMap<Integer, SeaPort> portsHashMap, HashMap<Integer, Dock> docksHashMap,
                         HashMap<Integer, Ship> shipsHashMap, Ship ship) {
        Dock dock = docksHashMap.get(ship.getParent());
        SeaPort port;

        if (dock == null) {
            port = portsHashMap.get(ship.getParent());
            port.getQueue().add(ship);
        } else {
            port = portsHashMap.get(dock.getParent());
            dock.setShip(ship);
        }
        port.getShips().add(ship);
        shipsHashMap.put(ship.getIndex(), ship);
    }

    /**
     * Adds a Person to the World
     * @param portsHashMap HashMap containing <code>SeaPort</code> Objects and <code>index</code> values
     * @param person Person that will be added
     */
    private void addPerson(HashMap<Integer, SeaPort> portsHashMap, HashMap<Integer, Person> personsHashMap, Person person) {
        SeaPort port = portsHashMap.get(person.getParent());
        port.getPersons().add(person);
        personsHashMap.put(person.getIndex(), person);
    }

    private void addJob(HashMap<Integer, Ship> shipsHashMap, HashMap<Integer, Job> jobsHashMap, Job job) {
        Ship ship = shipsHashMap.get(job.getParent());
        ship.getJobs().add(job);
        jobsHashMap.put(job.getIndex(), job);
    }

    /**
     * Getter method for the current <code>SeaPort</code> in the <code>World</code>
     * @return ArrayList of SeaPorts
     */
    ArrayList<SeaPort> getPorts() {
        return ports;
    }

    /**
     * Setter method for the current SeaPorts in the World
     * @param ports ArrayList of SeaPorts
     */
    void setPorts(ArrayList<SeaPort> ports) {
        this.ports = ports;
    }

    /**
     * Getter method for the current <code>PortTime</code>
     * @return time
     */
    PortTime getTime() {
        return time;
    }

    /**
     * Setter method for the current <code>PortTime</code>
     */
    void setTime(PortTime time) {
        this.time = time;
    }

    /**
     * Searches the World based on index
     * @param results ArrayList of Things matching the index
     * @param index index to be found
     * @return ArrayList of search results
     */
    ArrayList <Thing> indexSearch(ArrayList <Thing> results, int index) {
        for (SeaPort port : ports) {
            if (port.getIndex() == index) {
                results.add(port);
            }
            for (Dock dock : port.getDocks()) {
                if (dock.getIndex() == index) {
                    results.add(dock);
                }
            }
            for (Ship ship : port.getShips()) {
                if (ship.getIndex() == index) {
                    results.add(ship);
                }
                for (Job job : ship.getJobs()) {
                    if (job.getIndex() == index) {
                        results.add(job);
                    }
                }
            }
            for (Person person : port.getPersons()) {
                if (person.getIndex() == index) {
                    results.add(person);
                }
            }
        }

        return results;
    }

    /**
     * Searches the World based on name
     * @param results ArrayList of Things matching the name
     * @param name name to be found
     * @return ArrayList of search results
     */
    ArrayList <Thing> nameSearch(ArrayList <Thing> results, String name) {
        for (SeaPort port : ports) {
            if (port.getName().equalsIgnoreCase(name)) {
                results.add(port);
            }
            for (Dock dock : port.getDocks()) {
                if (dock.getName().equalsIgnoreCase(name)) {
                    results.add(dock);
                }
            }
            for (Ship ship : port.getShips()) {
                if (ship.getName().equalsIgnoreCase(name)) {
                    results.add(ship);
                }
                for (Job job : ship.getJobs()) {
                    if (job.getName().equalsIgnoreCase(name)) {
                        results.add(job);
                    }
                }
            }
            for (Person person : port.getPersons()) {
                if (person.getName().equalsIgnoreCase(name)) {
                    results.add(person);
                }
            }
        }
        return results;
    }

    /**
     * Searches the World based on skill
     * @param results ArrayList of Things matching the skill
     * @param skill skill to be found
     * @return ArrayList of search results
     */
    ArrayList<Thing> skillSearch(ArrayList<Thing> results, String skill) {
        for (SeaPort port : ports) {
            for (Person person : port.getPersons()) {
                if (person.getSkill().equalsIgnoreCase(skill)) {
                    results.add(person);
                }
            }
        }
        return results;
    }

    /**
     * Searches the World based on type
     * @param results ArrayList of Things matching the type
     * @param type type to be found
     * @return ArrayList of search results
     */
    ArrayList <Thing> typeSearch(ArrayList <Thing> results, String type) {
        switch (type.toLowerCase()) {
            case "port":
                results.addAll(ports);
                break;
            case "dock":
                for (SeaPort port : ports) {
                    results.addAll(port.getDocks());
                }
                break;
            case "ship":
                for (SeaPort port : ports) {
                    results.addAll(port.getShips());
                }
                break;
            case "person":
                for (SeaPort port : ports) {
                    results.addAll(port.getPersons());
                }
                break;
            case "job":
                for (SeaPort port : ports) {
                    for (Ship ship : port.getShips()) {
                        results.addAll(ship.getJobs());
                    }
                }
                break;
        }
        return results;
    }

    /**
     * toString method
     * @return Formatted String of World
     */
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("\n\n>>>>> World: ");

        for (SeaPort port: ports) {
            out.append("\n").append(port);
        }

        return out.toString();
    }
}

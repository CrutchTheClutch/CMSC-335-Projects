package SeaPortProject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * <strong>Filename:</strong> &emsp;&emsp;&emsp; {@code World} <br/>
 * <strong>Author:</strong> &emsp;&emsp;&emsp;&emsp; William Crutchfield <br/>
 * <strong>Date Created:</strong> &emsp; January 25th, 2019 <br/>
 *
 * <br/>
 *
 * Defines the {@code World} object.  Contains one or more {@link SeaPort SeaPorts}.  Is responsible for processing the
 * text file data, and creating the corresponding objects within it.  The {@code World} acts as the "parent" object for
 * all classes that extend {@link Thing}.
 *
 * <br/><br/>
 *
 * Note that it is necessary to pass an instance of {@link SeaPortProgram} to the {@code World} for {@link Job}
 * object creation.
 *
 * @author William Crutchfield
 */
public class World extends Thing {

    private SeaPortProgram program;
    private ArrayList <SeaPort> ports;
    private PortTime time;

    /**
     * Constructor for {@code World}.
     *
     * @param sc        The current text file data.
     * @param program   Instance of {@link SeaPortProgram}.
     */
    World(Scanner sc, SeaPortProgram program) {
        super(sc);
        this.program = program;
        ports = new ArrayList<>();
    }

    /**
     * Creates all {@link SeaPort SeaPorts}, {@link Dock Docks}, {@link Ship Ships}, {@link Person Persons}, and
     * {@link Job Jobs} from the text file data.
     *
     * @param file All text file data.
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
                        Job job = new Job(sc, program, shipsHashMap);
                        addJob(shipsHashMap, jobsHashMap, job);
                        break;
                }
            }
        }
    }

    /**
     * Adds a {@link SeaPort} to the {@code World}.
     *
     * @param port {@link SeaPort} that will be added
     */
    private void addPort(HashMap<Integer, SeaPort> portsHashMap, SeaPort port) {
        this.getPorts().add(port);
        portsHashMap.put(port.getIndex(), port);
    }

    /**
     * Adds a {@link Dock} to the {@code World}.
     *
     * @param portsHashMap  HashMap containing all {@link SeaPort SeaPorts} and their respective {@code index} values.
     * @param dock          {@link Dock} that will be added.
     */
    private void addDock(HashMap<Integer, SeaPort> portsHashMap, HashMap<Integer, Dock> docksHashMap, Dock dock) {
        SeaPort port = portsHashMap.get(dock.getParent());
        port.getDocks().add(dock);
        docksHashMap.put(dock.getIndex(), dock);
    }

    /**
     * Adds a {@link Ship} to the {@code World}.
     *
     * <br/><br/>
     *
     * Note, that the parent of the {@link Ship} determines how it is added to the {@code World}.  If the parent is a
     * {@link SeaPort}, it will be added to the respective {@link SeaPort#getQueue() queue}.
     * If the parent is a {@link Dock}, it will be added via the {@link Dock#setShip(Ship) setShip} method.
     *
     * @param portsHashMap  HashMap containing all {@link SeaPort Seaports} and their respective {@code index} values.
     * @param docksHashMap  HashMap containing all {@link Dock Docks} and their respective {@code index} values.
     * @param ship          {@link Ship} that will be added.
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
     * Adds a {@link Person} to the {@code World}.
     *
     * @param portsHashMap  HashMap containing all {@link SeaPort Seaports} and their respective {@code index} values.
     * @param person        {@link Person} that will be added.
     */
    private void addPerson(HashMap<Integer, SeaPort> portsHashMap, HashMap<Integer, Person> personsHashMap, Person person) {
        SeaPort port = portsHashMap.get(person.getParent());
        port.getPersons().add(person);
        personsHashMap.put(person.getIndex(), person);
    }

    /**
     * Adds a {@link Job} to the {@code World}.
     *
     * @param shipsHashMap  HashMap containing all {@link Ship Ships} and their respective {@code index} values.
     * @param jobsHashMap   HashMap containing all {@link Job Jobs} and their respective {@code index} values.
     * @param job           {@link Job} that will be added.
     */
    private void addJob(HashMap<Integer, Ship> shipsHashMap, HashMap<Integer, Job> jobsHashMap, Job job) {
        Ship ship = shipsHashMap.get(job.getParent());
        ship.getJobs().add(job);
        jobsHashMap.put(job.getIndex(), job);
    }

    /**
     * Getter method for {@code ports}.  An {@link ArrayList} of all {@link SeaPort SeaPorts} within the {@code World}.
     *
     * @return Current {@code ports}.
     */
    ArrayList<SeaPort> getPorts() {
        return ports;
    }

    /**
     * Setter method for {@code ports}.  An {@link ArrayList} of all {@link SeaPort SeaPorts} within the {@code World}.
     *
     * @param ports New {@code ports}.
     */
    void setPorts(ArrayList<SeaPort> ports) {
        this.ports = ports;
    }

    /**
     * Getter method {@code time}.  An instance of {@link PortTime}.
     *
     * @return Current {@code time}.
     */
    PortTime getTime() {
        return time;
    }

    /**
     * Setter method for {@code time}.  An instance of {@link PortTime}.
     *
     * @param time New {@code time}.
     */
    void setTime(PortTime time) {
        this.time = time;
    }

    /**
     * Searches the {@code World} based on {@code index}.  Adds results to the {@code results} variable as they are
     * found.
     *
     * @param results   {@link ArrayList} of {@link Thing Things} matching the index.  Empty upon initial method call.
     * @param index     {@code index} value to be found.
     * @return          {@code results} variable.
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
     * Searches the {@code World} based on {@code name}.  Adds results to the {@code results} variable as they are
     * found.
     *
     * @param results   {@link ArrayList} of {@link Thing Things} matching the index.  Empty upon initial method call.
     * @param name      {@code index} value to be found.
     * @return          {@code results} variable.
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
     * Searches the {@code World} based on {@code skill}.  Adds results to the {@code results} variable as they are
     * found.
     *
     * @param results   {@link ArrayList} of {@link Thing Things} matching the index.  Empty upon initial method call.
     * @param skill     {@code skill} value to be found.
     * @return          {@code results} variable.
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
     * Searches the {@code World} based on {@code type}.  Adds results to the {@code results} variable as they are
     * found.
     *
     * @param results   {@link ArrayList} of {@link Thing Things} matching the index.  Empty upon initial method call.
     * @param type      {@code type} value to be found.
     * @return          {@code results} variable.
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
     * Returns a string representation of the {@code World}.
     *
     * @return Formatted string of the {@code World} object.
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

package SeaPortProject;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Filename :   World
 * Author :     William Crutchfield
 * Date:        1/25/2019
 * Purpose:     Defines the World Object
 */
public class World extends Thing {

    private ArrayList <SeaPort> ports;
    private PortTime time;

    /**
     * Constructs the World Object
     * @param sc a file Scanner of the current text file
     */
    World(Scanner sc) {
        super(sc);
        ports = new ArrayList<>();
    }

    /**
     * Creates the various objects from the text file
     * @param line current line of the file Scanner
     */
    void process(String line) {
        Scanner sc = new Scanner(line);

        if (!sc.hasNext() || line.startsWith("//")) {
            return;
        }

        switch (sc.next()) {
            case "port":
                SeaPort port = new SeaPort(sc);
                addPort(port);
                break;
            case "dock":
                Dock dock = new Dock(sc);
                SeaPort dockPort = getSeaPortByIndex(dock.getParent());
                if (dockPort != null) {
                    addDock(dockPort, dock);
                }
                break;
            case "pship":
                PassengerShip pShip = new PassengerShip(sc);
                Dock pDock = getDockByIndex(pShip.getParent());
                SeaPort pPort = getSeaPortByIndex(pShip.getParent());
                SeaPort pDockPort = null;
                if (pDock != null) {
                    pDockPort = getSeaPortByIndex(pDock.getParent());
                }
                addShip(pPort, pDockPort, pDock, pShip);
                break;
            case "cship":
                CargoShip cShip = new CargoShip(sc);
                Dock cDock = getDockByIndex(cShip.getParent());
                SeaPort cPort = getSeaPortByIndex(cShip.getParent());
                SeaPort cDockPort = null;
                if (cDock != null) {
                    cDockPort = getSeaPortByIndex(cDock.getParent());
                }
                addShip(cPort, cDockPort, cDock, cShip);
                break;
            case "person":
                Person person = new Person(sc);
                SeaPort personPort = getSeaPortByIndex(person.getParent());
                if (personPort != null) {
                    addPerson(personPort, person);
                }
                break;
        }
    }

    /**
     * Adds a SeaPort to the World
     * @param port SeaPort that will be added
     */
    private void addPort(SeaPort port) {
        ports.add(port);
    }

    /**
     * Adds a Dock to the World
     * @param port SeaPort parent of the @param dock
     * @param dock Dock that will be added
     */
    private void addDock(SeaPort port, Dock dock) {
        port.getDocks().add(dock);
    }

    /**
     * Adds a Ship to the World
     * @param port SeaPort parent of the @param ship
     * @param dPort SeaPort parent of the @param dock
     * @param dock Dock parent of the @param ship
     * @param ship Ship that will be added
     */
    private void addShip(SeaPort port, SeaPort dPort, Dock dock, Ship ship) {
        if (dock == null) {
            port.getShips().add (ship);
            port.getQueue().add (ship);
            return;
        }
        dock.setShip(ship);
        dPort.getShips().add (ship);
    }

    /**
     * Adds a Person to the World
     * @param port SeaPort parent of the @param person
     * @param person Person that will be added
     */
    private void addPerson(SeaPort port, Person person) {
        port.getPersons().add(person);
    }

    /**
     * Retrieves a SeaPort using it's index
     * @param index index of SeaPort to be retrieved
     * @return SeaPort if one is found, otherwise null
     */
    private SeaPort getSeaPortByIndex(int index) {
        for (SeaPort port : ports) {
            if (port.getIndex() == index) {
                return port;
            }
        }
        return null;
    }

    /**
     * Retrieves a Dock using it's index
     * @param index index of SeaPort to be retrieved
     * @return Dock if one is found, otherwise null
     */
    private Dock getDockByIndex(int index) {
        for (SeaPort port : ports) {
            for (Dock dock : port.getDocks()) {
                if (dock.getIndex() == index) {
                    return dock;
                }
            }
        }
        return null;
    }

    /**
     * Retrieves a Ship using it's index
     * @param index index of SeaPort to be retrieved
     * @return Ship if one is found, otherwise null
     */
    private Ship getShipByIndex(int index) {
        for (SeaPort port : ports) {
            for (Ship ship : port.getShips()) {
                if (ship.getIndex() == index) {
                    return ship;
                }
            }
        }
        return null;
    }

    /**
     * Retrieves a Person using it's index
     * @param index index of SeaPort to be retrieved
     * @return Person if one is found, otherwise null
     */
    private Person getPersonByIndex(int index) {
        for (SeaPort port : ports) {
            for (Person person : port.getPersons()) {
                if (person.getIndex() == index) {
                    return person;
                }
            }
        }
        return null;
    }

    /**
     * Getter method for the current SeaPorts in the World
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

    PortTime getTime() {
        return time;
    }

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

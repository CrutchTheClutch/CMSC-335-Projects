package SeaPortProject;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultCaret;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * <strong>Filename:</strong> &emsp;&emsp;&emsp; {@code SeaPortProgram} <br>
 * <strong>Author:</strong> &emsp;&emsp;&emsp;&emsp; William Crutchfield <br>
 * <strong>Date Created:</strong> &emsp; January 25th, 2019 <br>
 * <br> <p>
 * {@code SeaPortProgram} is the base class for the entire {@link SeaPortProject}.  It's main responsibilities
 * involve constructing the GUI, ActionListeners, and building the {@link World} object.  The {@code SeaPortProgram}
 * will only build the {@link World} object from a text file, after it has been read in.
 *
 * @author William Crutchfield
 * @see World
 */
public class SeaPortProgram extends JFrame {

    private World world;

    // GUI Variables
    private JComboBox <String> searchCombo, sortCombo, sortTargetCombo;
    private JTextField searchField;
    private JTree worldTree;
    private JTextArea searchTextArea, logTextArea;
    private JTable jobsTable, resourcesTable;

    /**
     * Default Constructor for {@code SeaPortProgram}.
     */
    private SeaPortProgram() {

        // Combo Items
        final String[] searchComboItems = {"Index", "Name", "Skill", "Type"};
        final String[] sortComboItems = {"Ports", "Docks", "Ships", "Queue", "People", "Jobs"};

        // Sort Targets For Each sortComboItems
        final String[] seaPortSortTargets = {"name"};
        final String[] docksSortTargets = {"name"};
        final String[] allShipsSortTargets = {"name"};
        final String[] queuedShipsSortTargets = {"name", "weight", "length", "width", "draft"};
        final String[] peopleSortTargets = {"name"};
        final String[] jobsSortTargets = {"name"};

        // All Sort Targets
        final String[][] sortTargets = {
                seaPortSortTargets, docksSortTargets, allShipsSortTargets,
                queuedShipsSortTargets, peopleSortTargets, jobsSortTargets
        };

        // JTable Header Titles
        final String[] resourcesTableTitles = {"This", "Is", "A", "Placeholder", "", ""};

        // Preset Dimensions
        final Dimension frameDimension = new Dimension(1125,619);

        // Preset Fonts
        Font monospaced = new Font ("Monospaced", Font.PLAIN, 12);

        // Preset Insets
        Insets componentInset = new Insets(2,5,3,5);
        Insets panelInset = new Insets(0,0,0,0);

        // GridBagConstraints Initial Settings
        GridBagConstraints c = new GridBagConstraints();
        c.insets = componentInset;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 1;
        c.weighty = 1;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1;

        // Create JPanels
        JPanel optionsPanel = new JPanel();                     // UPPER UI - super Panel
        JPanel filePanel = new JPanel();                        // UPPER UI - File Options
        JPanel searchPanel = new JPanel();                      // UPPER UI - Search Options
        JPanel sortPanel = new JPanel();                        // UPPER UI - Sort Options
        JPanel viewPanel = new JPanel();                        // MAIN UI - super Panel
        JPanel treePanel = new JPanel();                        // MAIN UI - Tree View
        JPanel treeOptPanel = new JPanel();                     // MAIN UI - Tree View Options
        JPanel logPanel = new JPanel();                         // MAIN UI - Log/Job View

        // region UPPER UI Construction
        // Set Layouts
        optionsPanel.setLayout(new GridBagLayout());
        filePanel.setLayout(new GridBagLayout());
        searchPanel.setLayout(new GridBagLayout());
        sortPanel.setLayout(new GridBagLayout());

        // Set Panel Borders
        filePanel.setBorder(new TitledBorder("File"));
        searchPanel.setBorder(new TitledBorder("Search"));
        sortPanel.setBorder(new TitledBorder("Sort"));

        // Create Components
        JButton readBtn = new JButton("Read");             // File Options - Read Button
        JButton displayBtn = new JButton("Display");       // File Options - Display Button
        JButton clearBtn = new JButton("Clear");           // File Options - Clear Button
        searchField = new JTextField ();                        // Search Options - Search Field
        searchCombo = new JComboBox<>();                        // Search Options - Search Combo
        JButton searchBtn = new JButton("Search");         // Search Options - Search Button
        sortTargetCombo = new JComboBox<>();                    // Sort Options - Sort Target (Thing) Combo
        sortCombo = new JComboBox<>();                          // Sort Options - Sort Combo
        JButton sortBtn = new JButton("Sort");             // Sort Options - Sort Button

        // Set Fonts
        searchField.setFont(monospaced);

        // searchCombo Settings
        searchCombo.setEditable(false);
        for (String item : searchComboItems) {
            searchCombo.addItem (item);                         // Populates searchCombo ComboBox
        }
        searchCombo.setSelectedIndex(0);

        // sortCombo Settings
        sortCombo.setEditable(false);
        for (String item : sortComboItems) {
            sortCombo.addItem (item);                           // Populates sortCombo ComboBox
        }
        sortCombo.setSelectedIndex(0);

        // sortTargetCombo Settings
        sortTargetCombo.setEditable(false);
        for (String item : sortTargets[0]) {
            sortTargetCombo.addItem(item);                      // Populates sortParamCombo ComboBox
        }

        // Add filePanel Components
        c.gridx = 0;
        filePanel.add(readBtn, c);
        c.gridx = 1;
        filePanel.add(displayBtn, c);
        c.gridx = 2;
        filePanel.add(clearBtn, c);

        // Add searchPanel Components
        c.gridx = 0;
        c.gridwidth = 2;
        c.weightx = 2;
        searchPanel.add(searchField, c);
        c.gridx = 2;
        c.gridwidth = 1;
        c.weightx = 1;
        searchPanel.add(searchCombo, c);
        c.gridx = 3;
        searchPanel.add(searchBtn, c);

        // Add sortPanel Components
        c.gridx = 0;
        sortPanel.add(sortCombo, c);
        c.gridx = 1;
        sortPanel.add(sortTargetCombo, c);
        c.gridx = 2;
        sortPanel.add(sortBtn, c);

        // Add Panels to optionsPanel
        c.insets = panelInset;
        c.gridx = 0;
        optionsPanel.add(filePanel, c);
        c.gridx = 1;
        c.gridwidth = 2;
        c.weightx = 2;
        optionsPanel.add(searchPanel, c);
        c.gridx = 3;
        c.gridwidth = 1;
        c.weightx = 1;
        optionsPanel.add(sortPanel, c);
        //endregion

        // region MAIN UI Construction
        // Set Layouts
        viewPanel.setLayout(new BorderLayout(0,0));
        treePanel.setLayout(new BorderLayout(0,0));
        treeOptPanel.setLayout(new GridBagLayout());
        logPanel.setLayout(new BorderLayout(0,0));

        // Set Panel Borders
        viewPanel.setBorder(null);
        treePanel.setBorder(new EtchedBorder());
        logPanel.setBorder(new EtchedBorder());

        // Set Table Models
        DefaultTableModel jobsTableModel = new JobsTableModel();
        DefaultTableModel resourcesTableModel = new DefaultTableModel(resourcesTableTitles, 0);

        // Create Components
        worldTree = new JTree(new DefaultMutableTreeNode("SeaPorts"));    // Tree View - World Tree
        JButton expandBtn = new JButton("Expand All");                         // Tree View - Expand All Button
        JButton collapseBtn = new JButton("Collapse All");                     // Tree View - Collapse All Button
        logTextArea = new JTextArea();                                              // Log View - Log TextArea
        searchTextArea = new JTextArea();                                           // Log View - Search Results
        jobsTable = new JTable(jobsTableModel);                                     // Job View - Jobs Table
        resourcesTable = new JTable(resourcesTableModel);                           // Job View - Resources Table

        // Create Scroll Panes
        JScrollPane treeScrollPane = new JScrollPane(worldTree);                    // Tree View - Scroll Pane
        JScrollPane logScrollPane = new JScrollPane(logTextArea);                   // Log View - Log Scroll Pane
        JScrollPane searchScrollPane = new JScrollPane(searchTextArea);             // Log View - Search Scroll Pane
        JScrollPane jobsScrollPane = new JScrollPane(jobsTable);                    // Job View - Jobs Scroll Pane
        JScrollPane resourcesScrollPane = new JScrollPane(resourcesTable);          // Job View - Resources Scroll Pane

        // Create JTabbed Panes
        JTabbedPane logsTabbedPane = new JTabbedPane();                             // Log View - Log Tabbed Pane
        logsTabbedPane.add("Log", logScrollPane);                              // Log View - Log Tab
        logsTabbedPane.add("Search Results", searchScrollPane);                // Log View - Search Results Tab
        JTabbedPane tablesTabbedPane = new JTabbedPane();                           // Job View - Tables Tabbed Pane
        tablesTabbedPane.add("Jobs", jobsScrollPane);                          // Job View - Job Tab
        tablesTabbedPane.add("Resources", resourcesScrollPane);                // Job View - Resources Tab

        // Create JSplit Panes
        JSplitPane logsSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, logsTabbedPane, tablesTabbedPane);
        JSplitPane mainUISplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePanel, logPanel);

        // Create Carets
        DefaultCaret logCaret = (DefaultCaret) logTextArea.getCaret();
        DefaultCaret searchCaret = (DefaultCaret) searchTextArea.getCaret();

        // Set Fonts
        worldTree.setFont(monospaced);
        logTextArea.setFont(monospaced);
        searchTextArea.setFont(monospaced);
        jobsTable.setFont(monospaced);
        resourcesTable.setFont(monospaced);

        // Set Component Borders
        treeScrollPane.setBorder(new TitledBorder("World"));
        jobsTable.setBorder(null);
        resourcesTable.setBorder(null);
        logsSplitPane.setBorder(null);
        mainUISplitPane.setBorder(new EtchedBorder());

        // Set Carets
        logCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        searchCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        // jobsTable Settings
        jobsTable.setDefaultRenderer(Component.class, new PanelCellRenderer());
        jobsTable.setDefaultEditor(Component.class, new PanelCellEditor());
        jobsTable.getTableHeader().setReorderingAllowed(false);
        jobsTable.setRowHeight(25);

        // JSplit Pane Settings
        logsSplitPane.setResizeWeight(0.5);
        mainUISplitPane.setDividerSize(12);

        // TextArea Settings
        searchTextArea.setEditable(false);
        logTextArea.setEditable(false);

        // Add treeOptPanel Components
        c.insets = componentInset;
        c.gridx = 0;
        treeOptPanel.add(expandBtn, c);
        c.gridx = 1;
        treeOptPanel.add(collapseBtn, c);

        // Add treePanel Components
        treePanel.add(treeScrollPane, BorderLayout.CENTER);
        treePanel.add(treeOptPanel, BorderLayout.SOUTH);

        // Add logPanel Components
        logPanel.add(logsSplitPane, BorderLayout.CENTER);

        // Add Panels to viewPanel
        viewPanel.add(mainUISplitPane, BorderLayout.CENTER);
        //endregion

        // JFrame Settings
        setTitle("SeaPort Program");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(frameDimension);
        setMinimumSize(frameDimension);
        setLayout(new BorderLayout(0,0));
        setLocationRelativeTo(null);
        setVisible(true);

        // Add Panels to JFrame
        add(optionsPanel, BorderLayout.PAGE_START);
        add(viewPanel, BorderLayout.CENTER);

        validate ();

        // Action Listeners
        readBtn.addActionListener (e -> readFile());
        displayBtn.addActionListener (e -> display());
        clearBtn.addActionListener (e -> clear());
        searchBtn.addActionListener(e -> search());
        sortCombo.addActionListener(e -> {
            sortTargetCombo.removeAllItems();
            for (String item : sortTargets[sortCombo.getSelectedIndex()]) {
                sortTargetCombo.addItem(item);
            }
            validate();
        });
        sortBtn.addActionListener(e -> sort());
        expandBtn.addActionListener(e -> expandTree());
        collapseBtn.addActionListener(e -> collapseTree());
    }

    /**
     * Reads a text file into {@code SeaPortProgram}, then builds the {@link World} object with the data.
     */
    private void readFile() {
        JFileChooser jFileChooser = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt", "text");
        jFileChooser.setFileFilter(filter);
        jFileChooser.showOpenDialog(this);

        Scanner sc;

        try {
            sc = new Scanner(jFileChooser.getSelectedFile());
        } catch (FileNotFoundException | NullPointerException e) {
            displayError(ErrorType.NO_FILE);
            updateLog("Read File Failed - No File Found");
            return;
        }

        updateLog("Read File Success");

        world = new World(sc, this);
        world.process(sc);
        updateLog("World Process Success");

        startAllJobs();

        display();
    }

    /**
     * Gets all {@link Ship Ships} from the {@link World}.  Removes them from their respective {@link SeaPort}
     * if they do not contain any {@link Job Jobs}.  Then, starts all {@link Job} threads.
     */
    private void startAllJobs() {
        for (SeaPort port : world.getPorts()) {
            for (Dock dock : port.getDocks()) {
                if (dock.getShip().getJobs().isEmpty()) {
                    updateLog("Ship " + dock.getShip().getName() + " Departed from " + dock.getName());
                    dock.setShip(null);
                    while (!port.getQueue().isEmpty()) {
                        Ship ship = port.getQueue().remove(0);
                        if (!ship.getJobs().isEmpty()) {
                            dock.setShip(ship);
                            ship.setDock(dock);
                            updateLog("Ship " + ship.getName() + " Arrived at " + dock.getName());
                            break;
                        }
                    }
                }
            }
            for (Ship ship : port.getShips()) {
                for (Job job : ship.getJobs()) {
                    job.getThread().start();
                }
            }
        }
    }

    /**
     * Calls both {@link #updateWorldDisplay}, and {@link #updateJobDisplay}. Updates all of the GUI elements.
     */
    private void display() {
        if (world == null) {
            displayError(ErrorType.NO_WORLD);
            updateLog("Display Update Failed - No World Found");
            return;
        }

        updateWorldDisplay();
        updateJobDisplay();

        updateLog("Display Update Success");

        validate();
    }

    /**
     * Updates the {@code worldTree} GUI.
     */
    private void updateWorldDisplay() {
        DefaultTreeModel treeModel = (DefaultTreeModel) worldTree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel.getRoot();

        root.removeAllChildren();
        treeModel.reload();

        // Creates the World JTree
        for (SeaPort port : world.getPorts()) {

            // All SeaPort Nodes
            DefaultMutableTreeNode portNode = new DefaultMutableTreeNode(port.getName());
            root.add(portNode);

            // All Docks Node
            DefaultMutableTreeNode docks = new DefaultMutableTreeNode("Docks");
            portNode.add(docks);

            for (Dock dock : port.getDocks()) {
                // Adds Dock Nodes to All Docks
                DefaultMutableTreeNode dockNode = new DefaultMutableTreeNode(dock.getName());
                docks.add(dockNode);

                // Adds Ship Node to Dock
                DefaultMutableTreeNode shipNode;
                if (dock.getShip() == null) {
                    shipNode = new DefaultMutableTreeNode(null);
                } else {
                    shipNode = new DefaultMutableTreeNode("Ship: " + dock.getShip().getName());
                    // Adds Job Node to Ship
                    for (Job jobs : dock.getShip().getJobs()) {
                        DefaultMutableTreeNode jobNode = new DefaultMutableTreeNode("Job: " + jobs.getName());
                        shipNode.add(jobNode);
                    }
                }
                dockNode.add(shipNode);
            }

            // All Ships Node
            DefaultMutableTreeNode ships = new DefaultMutableTreeNode("Ships");
            portNode.add(ships);

            for (Ship ship : port.getShips()) {
                // Adds Ship Nodes to All Ships
                DefaultMutableTreeNode shipNode = new DefaultMutableTreeNode(ship.getName());
                ships.add(shipNode);
            }

            // All Nodes in Queue
            DefaultMutableTreeNode queue = new DefaultMutableTreeNode("Queue");
            portNode.add(queue);

            for (Ship ship : port.getQueue()) {
                // Adds Ship Nodes to Queue
                DefaultMutableTreeNode shipNode = new DefaultMutableTreeNode("Ship: " + ship.getName());
                queue.add(shipNode);
            }

            // All Persons Node
            DefaultMutableTreeNode persons = new DefaultMutableTreeNode("Persons");
            portNode.add(persons);

            for (Person person : port.getPersons()) {
                // Adds Person Nodes to All Persons
                DefaultMutableTreeNode personNode = new DefaultMutableTreeNode(person.getName());
                persons.add(personNode);
            }
        }

        worldTree.expandRow(0);
    }

    /**
     * Updates the {@code jobsTable} GUI.
     */
    private void updateJobDisplay() {
        DefaultTableModel jobsTableModel = (DefaultTableModel) jobsTable.getModel();
        jobsTableModel.setRowCount(0);

        for (SeaPort port : world.getPorts()) {
            for (Ship ship : port.getShips()) {
                for (Job job : ship.getJobs()) {
                    jobsTableModel.addRow(new Object[]{ship.getName(), job.getName(), job.getStatusPanel(),
                            job.getProgressPanel(), job.getSuspendPanel(), job.getCancelPanel()});
                }
            }
        }
    }

    /**
     * Clears both {@code logTextArea} and {@code searchTextArea} of all text.  Then, removes all finished
     * {@link Job Jobs} from the {@code jobsTable}.
     */
    private void clear() {
        logTextArea.setText("");
        searchTextArea.setText("");
        updateJobDisplay();
    }

    /**
     * Searches the {@link World} for the value defined in {@code fieldText}.  Search type is determined by the selected
     * item in {@code searchCombo}.  Both {@code fieldText} and {@code searchCombo} are located in the
     * {@code searchPanel} of the GUI.  Once the search is deemed valid, the helper method
     * {@link #searchType(String, String)} is called, passing the values of {@code fieldText} and {@code searchCombo}
     * as arguments.
     */
    private void search() {
        String comboSelectedItem = String.valueOf(searchCombo.getSelectedItem());
        String fieldText = searchField.getText();

        if (world == null) {
            displayError(ErrorType.NO_WORLD);
            updateLog("World Search Failed - No World Found");
            return;
        }

        if (comboSelectedItem == null || fieldText.equals("")) {
            displayError(ErrorType.MISSING_SEARCH_PARAM);
            updateLog("World Search Failed - Incorrect Search Parameters");
            return;
        }

        searchType(comboSelectedItem, fieldText);
    }

    /**
     * Helper method for {@link #search()}, determines the search type and calls the appropriate search method from the
     * {@link World} class.  Will call one of four methods: {@link World#indexSearch(ArrayList, int) indexSearch()},
     * {@link World#nameSearch(ArrayList, String) nameSearch()},
     * {@link World#skillSearch(ArrayList, String) skillSearch()}, or
     * {@link World#typeSearch(ArrayList, String) typeSearch()}.
     * @param type value from {@code searchCombo}, the type of search that will be preformed.
     * @param target value from {@code fieldText}, the value that will be searched for.
     */
    private void searchType(String type, String target) {

        ArrayList<Thing> results = new ArrayList<>();

        switch (type) {
            case "Index":
                results = world.indexSearch(results, Integer.parseInt(target));
                break;
            case "Name":
                results = world.nameSearch(results, target);
                break;
            case "Skill":
                results = world.skillSearch(results, target);
                break;
            case "Type":
                results = world.typeSearch(results, target);
                break;
        }

        updateLog("World Search - Type: " + type);
        updateLog("World Search - Target: " + target);

        searchTextArea.append(searchResultsToString(results, type + " - " + target));
    }

    /**
     * Helper method for {@link #search()}, builds a string of all search results.
     * @param results ArrayList of all search results.
     * @return Formatted String of all search results.
     */
    private String searchResultsToString(ArrayList<Thing> results, String params) {
        StringBuilder out = new StringBuilder("Search Results: " + params);
        if (results.isEmpty()) {
            out.append("\n    NO RESULTS FOUND");
            updateLog("World Search - NO RESULTS FOUND");
        } else {
            for (Thing thing : results) {
                out.append("\n    ").append(thing);
                updateLog("World Search - " + results.size() + " RESULTS FOUND");
            }
        }
        out.append("\n\n");
        return out.toString();
    }

    /**
     * Sorts the {@code sortCombo} objects in the {@link World}. Sorts the {@code sortCombo} objects based on the value
     * of {@code sortTargetCombo}.
     */
    private void sort(){

        String sortComboItem = String.valueOf(sortCombo.getSelectedItem());
        String target = String.valueOf(sortTargetCombo.getSelectedItem());

        if (world == null) {
            displayError(ErrorType.NO_WORLD);
            updateLog("World Sort Failed - No World Found");
            return;
        }

        switch (sortComboItem) {
            case "Ports":
                world.getPorts().sort(new ThingComparator(target));
                break;
            case "Docks":
                for (SeaPort port : world.getPorts()) {
                    port.getDocks().sort(new ThingComparator(target));
                }
                break;
            case "Ships":
                for (SeaPort port : world.getPorts()) {
                    port.getShips().sort(new ShipComparator(target));
                }
                break;
            case "Queue":
                for (SeaPort port : world.getPorts()) {
                    port.getQueue().sort(new ShipComparator(target));
                }
                break;
            case "People":
                for (SeaPort port : world.getPorts()) {
                    port.getPersons().sort(new ThingComparator(target));
                }
                break;
            case "Jobs":
                for (SeaPort port : world.getPorts()) {
                    for (Ship ship : port.getShips()) {
                        ship.getJobs().sort(new ThingComparator(target));
                    }
                }
                break;
        }

        updateLog("World Sort - Type: " + sortComboItem);
        updateLog("World Sort - Target: " + target);

        display();
    }

    /**
     * Expands all nodes in the {@code worldTree}.
     */
    private void expandTree() {
        if (world == null) {
            displayError(ErrorType.NO_WORLD);
            updateLog("Tree Expand Failed - No World Found");
            return;
        }

        for (int i = 1; i < worldTree.getRowCount(); i++) {
            worldTree.expandRow(i);
        }

        updateLog("Tree Expand Success");
    }

    /**
     * Collapses all nodes in the {@code worldTree}.
     */
    private void collapseTree() {
        if (world == null) {
            displayError(ErrorType.NO_WORLD);
            updateLog("Tree Collapse Failed - No World Found");
            return;
        }

        for (int i = 1; i < worldTree.getRowCount(); i++) {
            worldTree.collapseRow(i);
        }

        updateLog("Tree Collapse Success");
    }

    /**
     * Creates a {@code JOptionPane} with the appropriate error message.
     * @param type one of the predefined {@code ErrorType} values, used to determine error message.
     */
    private void displayError(ErrorType type) {
        String msg = "Error";
        switch(type) {
            case NO_FILE:
                msg = "A file was not selected.";
                break;
            case NO_WORLD:
                msg = "A World is not loaded.";
                break;
            case MISSING_SEARCH_PARAM:
                msg = "Ensure that all search fields are filled.";
                break;
        }
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Appends a log message to the {@code logTextArea}.  Messages are formatted to contain the current date and time
     * at the beginning of each log message.  Is called by various methods in the {@link SeaPortProgram}, {@link World},
     * and {@link Job} classes.
     * @param logMessage Message to be displayed in the {@code logTextArea}.
     */
    void updateLog(String logMessage) {
        String currentTime = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SSS").format(new Date().getTime());
        logTextArea.append(currentTime + " | " + logMessage + "\n");
    }

    /**
     * Getter method for the {@code jobsTable} variable.
     * @return Current {@code jobsTable}.
     */
    JTable getJobsTable() {
        return jobsTable;
    }

    /**
     * Creates an instance of {@link SeaPortProgram};
     * @param args Will not affect the program.
     */
    public static void main(String[] args) {
        new SeaPortProgram();
    }
}
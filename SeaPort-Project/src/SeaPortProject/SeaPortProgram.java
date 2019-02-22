package SeaPortProject;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
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
 * Filename :   SeaPortProgram
 * Author :     William Crutchfield
 * Date:        1/25/2019
 * Purpose:     Builds the GUI, and handles ActionListeners for the various GUI Components
 */
public class SeaPortProgram extends JFrame {

    private World world;

    // GUI Variables
    private JPanel mainPanel = new JPanel();
    private JComboBox <String> searchCombo = new JComboBox<>();
    private JComboBox <String> sortCombo = new JComboBox<>();
    private JComboBox <String> sortTargetCombo = new JComboBox<>();
    private JTextField searchField = new JTextField ();

    private JTextArea resultsTextArea = new JTextArea();
    private JTextArea logTextArea = new JTextArea();
    private JTree worldTree;

    private JTable jobsTable, resourcesTable;



    /**
     * Constructs the GUI and Action Listeners
     */
    private SeaPortProgram() {
        // Combo Items
        final String[] searchComboItems = {"Index", "Name", "Skill", "Type"};
        final String[] sortComboItems = {"Ports", "Docks", "Ships", "Queue", "People", "Jobs"};

        // Sort Targets For Each type of Thing
        final String[] seaPortSortTargets = {"name"};
        final String[] docksSortTargets = {"name"};
        final String[] allShipsSortTargets = {"name"};
        final String[] queuedShipsSortTargets = {"name", "weight", "length", "width", "draft"};
        final String[] peopleSortTargets = {"name"};
        final String[] jobsSortTargets = {"name"};

        // All Sort Targets
        final String[][] sortTargets = {seaPortSortTargets, docksSortTargets, allShipsSortTargets, queuedShipsSortTargets, peopleSortTargets, jobsSortTargets};

        final String[] jobsTableTitles = {"Ship", "Name", "Status", "Progress", "", ""};

        // Preset Dimensions for each Component
        final Dimension minimumFrameDimension = new Dimension(1125,350);
        final Dimension comboBoxDimension = new Dimension(120, 25);
        final Dimension txtFieldDimension = new Dimension(170, 25);
        final Dimension btnDimension = new Dimension(95, 25);
        final Dimension worldPaneDimension = new Dimension(239, 500);
        final Dimension tabbedPaneDimension = new Dimension(835, 209);

        // Preset Font
        final Font defaultFont = new Font ("Monospaced", Font.PLAIN, 12);

        // Create JPanels
        JPanel optionsPanel = new JPanel();
        JPanel filePanel = new JPanel();
        JPanel searchPanel = new JPanel();
        JPanel sortPanel = new JPanel();

        JPanel viewPanel = new JPanel();

        // Create TextAreas
        JTextArea worldTextArea = new JTextArea();

        // Create Buttons
        JButton readBtn = new JButton ("Read");
        JButton displayBtn = new JButton ("Display");
        JButton searchBtn = new JButton ("Search");
        JButton sortBtn = new JButton ("Sort");
        JButton clearBtn = new JButton("Clear");

        // Create JTree
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("SeaPorts");
        worldTree = new JTree(root);

        // Create JTables
        DefaultTableModel jobsTableModel = new DefaultTableModel(jobsTableTitles, 0);
        jobsTable = new JTable(jobsTableModel);
        TableCellRenderer ProgressBarRenderer = new ProgressCellRenderer();
        jobsTable.getColumn("Progress").setCellRenderer(ProgressBarRenderer);

        // Create Scroll Panes
        JScrollPane worldScrollPane = new JScrollPane(worldTree);
        JScrollPane resultsScrollPane = new JScrollPane(resultsTextArea);
        JScrollPane logScrollPane = new JScrollPane(logTextArea);
        JScrollPane jobsScrollPane = new JScrollPane(jobsTable);
        JScrollPane resourcesScrollPane = new JScrollPane(resourcesTable);

        // Create JTabbed Panes
        JTabbedPane textTabbedPane = new JTabbedPane();
        textTabbedPane.add("Log", logScrollPane);
        textTabbedPane.add("Search Results", resultsScrollPane);

        JTabbedPane tablesTabbedPane = new JTabbedPane();
        tablesTabbedPane.add("Jobs", jobsScrollPane);
        tablesTabbedPane.add("Resources", resourcesScrollPane);

        // Create JSplit Panes
        JSplitPane jobsSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, textTabbedPane, tablesTabbedPane);
        JSplitPane worldSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, worldScrollPane, jobsSplitPane);

        // Create Carets
        DefaultCaret logCaret = (DefaultCaret) logTextArea.getCaret();
        DefaultCaret searchCaret = (DefaultCaret) logTextArea.getCaret();

        // JFrame Settings
        setTitle("SeaPort Program");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1125, 619);
        setLocationRelativeTo(null);
        setMinimumSize(minimumFrameDimension);
        setVisible(true);
        setLayout(new BorderLayout(0,0));


        // Set Panel Borders
        filePanel.setBorder(new TitledBorder("File"));
        searchPanel.setBorder(new TitledBorder("Search"));
        sortPanel.setBorder(new TitledBorder("Sort"));

        // Set Panel Layouts
        optionsPanel.setLayout(new BorderLayout(0,0));
        viewPanel.setLayout(new BorderLayout(0,0));

        // Set Scroll Pane Borders
        worldScrollPane.setBorder(new TitledBorder("World"));

        // Set JTables Borders
        jobsTable.setBorder(null);
        resourcesTable.setBorder(null);

        // Set Button Sizes
        readBtn.setPreferredSize(btnDimension);
        displayBtn.setPreferredSize(btnDimension);
        clearBtn.setPreferredSize(btnDimension);
        searchBtn.setPreferredSize(btnDimension);
        sortBtn.setPreferredSize(btnDimension);

        // Set Text Field Sizes
        searchField.setPreferredSize(txtFieldDimension);

        // searchCombo Settings
        searchCombo.setEditable(false);
        searchCombo.setPreferredSize(comboBoxDimension);
        // Populates searchCombo
        for (String item : searchComboItems) {
            searchCombo.addItem (item);
        }
        searchCombo.setSelectedIndex(0);

        // sortCombo Settings
        sortCombo.setEditable(false);
        sortCombo.setPreferredSize(comboBoxDimension);
        // Populates sortCombo
        for (String item : sortComboItems) {
            sortCombo.addItem (item);
        }
        sortCombo.setSelectedIndex(0);

        // sortTargetCombo Settings
        sortTargetCombo.setEditable(false);
        sortTargetCombo.setPreferredSize(comboBoxDimension);
        // Populates sortParamCombo
        for (String item : sortTargets[0]) {
            sortTargetCombo.addItem(item);
        }

        // worldTextArea Settings
        worldTextArea.setFont(defaultFont);
        worldTextArea.setEditable(false);

        // searchTextArea Settings
        resultsTextArea.setFont(defaultFont);
        resultsTextArea.setEditable(false);
        searchCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        // logTextArea Settings
        logTextArea.setFont(defaultFont);
        logTextArea.setEditable(false);
        logCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        // jobsTable Settings
        jobsTable.setFont(defaultFont);

        // Set Scroll Panes Sizes
        worldScrollPane.setPreferredSize(worldPaneDimension);
        resultsScrollPane.setPreferredSize(tabbedPaneDimension);
        logScrollPane.setPreferredSize(tabbedPaneDimension);
        jobsScrollPane.setPreferredSize(tabbedPaneDimension);
        resourcesScrollPane.setPreferredSize(tabbedPaneDimension);

        // JSplit Pane Settings
        jobsSplitPane.setBorder(null);
        worldSplitPane.setBorder(new EtchedBorder());
        worldSplitPane.setDividerSize(12);

        // Add filePanel Components
        filePanel.add(readBtn);
        filePanel.add(displayBtn);
        filePanel.add(clearBtn);

        // Add searchPanel Components
        searchPanel.add(searchField);
        searchPanel.add(searchCombo);
        searchPanel.add(searchBtn);

        // Add sortPanel Components
        sortPanel.add(sortCombo);
        sortPanel.add(sortTargetCombo);
        sortPanel.add(sortBtn);

        // Add Panels to optionsPanel
        optionsPanel.add(filePanel, BorderLayout.WEST);
        optionsPanel.add(searchPanel, BorderLayout.CENTER);
        optionsPanel.add(sortPanel, BorderLayout.EAST);

        // Add Panels to viewPanel
        viewPanel.add(worldSplitPane, BorderLayout.CENTER);

        // Add Panels to mainPanel
        mainPanel.add(optionsPanel, BorderLayout.NORTH);
        mainPanel.add(viewPanel, BorderLayout.CENTER);

        // Add mainPanel to JFrame
        add(mainPanel);

        validate ();

        // Action Listeners
        readBtn.addActionListener (e -> readFile());
        displayBtn.addActionListener (e -> display());
        clearBtn.addActionListener (e -> {
            logTextArea.setText("");
            resultsTextArea.setText("");
        });
        searchBtn.addActionListener(e -> search());
        sortCombo.addActionListener(e -> {
            sortTargetCombo.removeAllItems();
            for (String item : sortTargets[sortCombo.getSelectedIndex()]) {
                sortTargetCombo.addItem(item);
            }
            validate();
        });
        sortBtn.addActionListener(e -> sort());
    }

    /**
     * Reads in the selected text file from the jFileChooser
     */
    private void readFile() {
        JFileChooser jFileChooser = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt", "text");
        jFileChooser.setFileFilter(filter);
        jFileChooser.showOpenDialog(this);

        Scanner sc;

        updateLog("Read File Initialized");

        try {
            sc = new Scanner(jFileChooser.getSelectedFile());
        } catch (FileNotFoundException | NullPointerException e) {
            noFileMessageDialog();
            updateLog("Read File Failed - No File Found");
            return;
        }

        updateLog("Read File Success");

        world = new World(sc, this);
        updateLog("World Processing");
        world.process(sc);
        updateLog("World Processed");

        display();
    }

    /**
     * Displays All Data from the currently loaded text file
     */
    private void display() {
        updateLog("Display Update Initialized");

        if (world == null) {
            noWorldMessageDialog();
            updateLog("Display Update Failed - No World Found");
            return;
        }

        updateWorldDisplay();
        updateJobDisplay();

        updateLog("Display Update Success");

        validate();
    }

    /**
     *
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
                DefaultMutableTreeNode shipNode = new DefaultMutableTreeNode("Ship: " + dock.getShip().getName());
                dockNode.add(shipNode);

                // Adds Job Nodes to Docked Ship
                for (Job job : dock.getShip().getJobs()) {
                    DefaultMutableTreeNode jobNode = new DefaultMutableTreeNode(job.getName());
                    shipNode.add(jobNode);
                }
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
                // Adds Job Nodes to Queued Ship
                for (Job job : ship.getJobs()) {
                    DefaultMutableTreeNode jobNode = new DefaultMutableTreeNode(job.getName());
                    shipNode.add(jobNode);
                }
            }

            // All Persons Node
            DefaultMutableTreeNode persons = new DefaultMutableTreeNode("Persons");
            portNode.add(persons);

            for (Person person : port.getPersons()) {
                // Adds Person Nodes to All Persons
                DefaultMutableTreeNode personNode = new DefaultMutableTreeNode(person.getName());
                persons.add(personNode);
            }

            // All Ships Node
            DefaultMutableTreeNode jobs = new DefaultMutableTreeNode("Jobs");
            portNode.add(jobs);

            for (Ship ship : port.getShips()) {
                for (Job job : ship.getJobs()) {
                    // Adds Ship Nodes to All Ships
                    DefaultMutableTreeNode jobNode = new DefaultMutableTreeNode(job.getName());
                    jobs.add(jobNode);
                }
            }
        }

        worldTree.expandRow(0);
    }

    /**
     *
     */
    private void updateJobDisplay() {
        DefaultTableModel jobsTableModel = (DefaultTableModel) jobsTable.getModel();
        jobsTableModel.setRowCount(0);

        for (SeaPort port : world.getPorts()) {
            for (Ship ship : port.getShips()) {
                for (Job job : ship.getJobs()) {
                    jobsTableModel.addRow(new Object[]{ship.getName(), job.getName(), job.getStatus(), job.getProgressBar()});
                }
            }
        }
    }

    /**
     * Searches the data structure for the <code>fieldText</code> of parameter type <code>comboSelectedItem</code>
     */
    private void search() {
        String comboSelectedItem = String.valueOf(searchCombo.getSelectedItem());
        String fieldText = searchField.getText();

        updateLog("World Search Initialized");

        if (world == null) {
            noWorldMessageDialog();
            updateLog("World Search Failed - No World Found");
            return;
        }

        if (comboSelectedItem == null || fieldText.equals("")) {
            searchErrorMessageDialog();
            updateLog("World Search Failed - Incorrect Search Parameters");
            return;
        }

        searchType(comboSelectedItem, fieldText);
    }

    /**
     * Search helper method, invokes the correct world search method based on the <code>type</code>
     * @param type parameter that will be searched
     * @param target term that will be searched
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

        resultsTextArea.append(searchResultsToString(results, type + " - " + target));

        updateLog("World Search Success");
    }

    /**
     * search helper method, is used to build the results string
     * @param results ArrayList of all results from a search
     * @return Formatted String of all results
     */
    private String searchResultsToString(ArrayList<Thing> results, String params) {
        StringBuilder out = new StringBuilder("Search Results: " + params);
        if (results.isEmpty()) {
            out.append("\n    NO RESULTS FOUND");
        } else {
            for (Thing thing : results) {
                out.append("\n    ").append(thing);
            }
        }
        out.append("\n\n");
        return out.toString();
    }

    /**
     * Sorts the data <code>sortComboItem</code> structure based on the <code>sortTarget</code>
     */
    private void sort(){

        String sortComboItem = String.valueOf(sortCombo.getSelectedItem());
        String target = String.valueOf(sortTargetCombo.getSelectedItem());

        updateLog("World Sort Initialized");

        if (world == null) {
            noWorldMessageDialog();
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

        updateLog("World Sort Success");

        display();
    }

    /**
     * Displays an Error message that no file was loaded
     */
    private void noFileMessageDialog() {
        JOptionPane.showMessageDialog(mainPanel,
                "A file was not selected.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Displays an Error message that no world was loaded
     */
    private void noWorldMessageDialog() {
        JOptionPane.showMessageDialog(mainPanel,
                "A world is not loaded.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Displays an Error message that search parameters are incorrect
     */
    private void searchErrorMessageDialog() {
        JOptionPane.showMessageDialog(mainPanel,
                "Please ensure that all search fields are filled out.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     *
     * @param logMessage
     */
    void updateLog(String logMessage) {
        String currentTime = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SSS").format(new Date().getTime());
        logTextArea.append(currentTime + " | " + logMessage + "\n");
    }

    /**
     *
     * @return
     */
    JTable getJobsTable() {
        return jobsTable;
    }

    /**
     * Main method, creates the SeaPortProgram();
     * @param args will not affect the program
     */
    public static void main(String[] args) {
        new SeaPortProgram();
    }
}

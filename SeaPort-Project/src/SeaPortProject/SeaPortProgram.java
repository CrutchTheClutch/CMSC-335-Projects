package SeaPortProject;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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

    private JComboBox <String> searchCombo = new JComboBox <> ();
    private JComboBox <String> sortCombo = new JComboBox <> ();
    private JComboBox <String> sortTargetCombo = new JComboBox <> ();

    private JTextField searchField = new JTextField ();

    private JTextArea worldTextArea = new JTextArea();
    private JTextArea searchTextArea = new JTextArea();

    /**
     * Constructs the GUI and Action Listeners
     */
    private SeaPortProgram() {

        final String[] searchComboItems = {"Index", "Name", "Skill", "Type"};
        final String[] sortComboItems = {"Ports", "Docks", "Ships", "Queue", "People", "Jobs"};

        // Sort Targets For Each type of Thing
        final String[] seaPortSortTargets = {"name"};
        final String[] docksSortTargets = {"name"};
        final String[] allShipsSortTargets = {"name"};
        final String[] queuedShipsSortTargets = {"name", "weight", "length", "width", "draft"};
        final String[] peopleSortTargets = {"name"};
        final String[] jobsSortTargets = {"name"};

        final String[][] sortTargets = {seaPortSortTargets, docksSortTargets, allShipsSortTargets, queuedShipsSortTargets, peopleSortTargets, jobsSortTargets};

        // Preset Dimensions for each Component
        final Dimension comboBoxDimension = new Dimension(120, 25);
        final Dimension txtFieldDimension = new Dimension(170, 25);
        final Dimension btnDimension = new Dimension(95, 25);
        final Dimension scrollPaneDimension = new Dimension(530, 400);

        // Create JPanels
        JPanel optionsPanel = new JPanel();
        JPanel filePanel = new JPanel();
        JPanel searchPanel = new JPanel();
        JPanel sortPanel = new JPanel();

        JPanel viewPanel = new JPanel();
        JPanel logPanel = new JPanel();
        JPanel resultsPanel = new JPanel();

        // Create Buttons
        JButton readBtn = new JButton ("Read");
        JButton displayBtn = new JButton ("Display");
        JButton searchBtn = new JButton ("Search");
        JButton sortBtn = new JButton ("Sort");
        JButton clearBtn = new JButton("Clear");

        // Create Scroll Panes
        JScrollPane worldScrollPane = new JScrollPane(worldTextArea);
        JScrollPane sortScrollPane = new JScrollPane(searchTextArea);

        // JFrame Settings
        setTitle("SeaPort Program");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1125, 556);
        setLocation(300, 400);
        setVisible(true);
        setLayout(new BorderLayout());

        // Set Panel Border Titles
        filePanel.setBorder(new TitledBorder("File"));
        searchPanel.setBorder(new TitledBorder("Search"));
        sortPanel.setBorder(new TitledBorder("Sort"));
        logPanel.setBorder(new TitledBorder("World Log"));
        resultsPanel.setBorder(new TitledBorder("Search Results"));

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
        worldTextArea.setFont(new Font ("Monospaced", Font.PLAIN, 12));
        worldTextArea.setEditable(false);

        // searchTextArea Settings
        searchTextArea.setFont(new Font ("Monospaced", Font.PLAIN, 12));
        searchTextArea.setEditable(false);

        // Set Scroll Panes Sizes
        worldScrollPane.setPreferredSize(scrollPaneDimension);
        sortScrollPane.setPreferredSize(scrollPaneDimension);

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

        // Add logPanel Components
        logPanel.add(worldScrollPane);

        // Add resultsPanel Components
        resultsPanel.add(sortScrollPane);

        // Add Panels to optionsPanel
        optionsPanel.add(filePanel, BorderLayout.WEST);
        optionsPanel.add(searchPanel, BorderLayout.CENTER);
        optionsPanel.add(sortPanel, BorderLayout.EAST);

        // Add Panels to viewPanel
        viewPanel.add(logPanel, BorderLayout.WEST);
        viewPanel.add(resultsPanel, BorderLayout.EAST);

        // Add Panels to mainPanel
        mainPanel.add(optionsPanel, BorderLayout.NORTH);
        mainPanel.add(viewPanel, BorderLayout.SOUTH);

        // Add mainPanel to JFrame
        add(mainPanel);

        validate ();

        // Action Listeners
        readBtn.addActionListener (e -> readFile());
        displayBtn.addActionListener (e -> display());
        clearBtn.addActionListener (e -> {
            worldTextArea.setText("");
            searchTextArea.setText("");
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

        try {
            sc = new Scanner(jFileChooser.getSelectedFile());
        } catch (FileNotFoundException | NullPointerException e1) {
            noFileMessageDialog();
            return;
        }

        world = new World(sc);
        world.process(sc);
    }

    /**
     * Displays All Data from the currently loaded text file
     */
    private void display() {
        if (world == null) {
            noWorldMessageDialog();
        } else {
            worldTextArea.append(world.toString());
        }
        validate();
    }

    /**
     * Searches the data structure for the <code>fieldText</code> of parameter type <code>comboSelectedItem</code>
     */
    private void search() {
        String comboSelectedItem = String.valueOf(searchCombo.getSelectedItem());
        String fieldText = searchField.getText();

        if (world == null) {
            noWorldMessageDialog();
            return;
        }

        if (comboSelectedItem == null || fieldText.equals("")) {
            searchErrorMessageDialog();
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
        final String resultType = "Search";

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

        searchTextArea.append(resultsToString(results, resultType));
    }

    /**
     * Sorts the data <code>sortComboItem</code> structure based on the <code>sortTarget</code>
     */
    private void sort(){
        final String resultType = "Sort";

        String sortComboItem = String.valueOf(sortCombo.getSelectedItem());
        String target = String.valueOf(sortTargetCombo.getSelectedItem());

        if (world == null) {
            noWorldMessageDialog();
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
                // class is not yet implemented
                break;
        }

        ArrayList<Thing> results = new ArrayList<>(world.getPorts());
        searchTextArea.append(resultsToString(results, resultType));
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
     * search helper method, is used to build the search results string
     * @param results ArrayList of all results from a search
     * @return Formatted String of all results
     */
    private String resultsToString(ArrayList<Thing> results, String type) {
        StringBuilder out = new StringBuilder("\n\n" + type +  " Results: ");
        if (results.isEmpty()) {
            out.append("\n    NO RESULTS FOUND");
        } else {
            for (Thing thing : results) {
                out.append("\n    ").append(thing);
            }
        }
        return out.toString();
    }

    /**
     * Main method, creates the SeaPortProgram();
     * @param args will not affect the program
     */
    public static void main(String[] args) {
        SeaPortProgram program = new SeaPortProgram();
    }
}

package SeaPortProject;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * Filename :   SeaPortProgram
 * Author :     William Crutchfield
 * Date:        1/25/2019
 * Purpose:     Builds the GUI, and handles ActionListeners for the various GUI Components
 */
public class SeaPortProgram extends JFrame {

    private World world;
    private Scanner sc;
    private JTextArea worldTextArea;

    /**
     * Constructs the GUI and Action Listeners
     */
    private SeaPortProgram() {

        // JFrame Settings
        setTitle("SeaPort Program");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(480, 490);
        setLocation(300, 400);
        setVisible(true);
        setLayout(new BorderLayout());

        // Create JPanels
        JPanel main = new JPanel();
        JPanel file = new JPanel();
        JPanel search = new JPanel();
        JPanel world = new JPanel();

        // JPanel Settings
        file.setBorder(new TitledBorder("File"));
        search.setBorder(new TitledBorder("Search"));
        world.setBorder(new TitledBorder("World"));

        // Create Components
        JButton readBtn = new JButton ("Read");
        JButton displayBtn = new JButton ("Display");
        JButton searchBtn = new JButton ("Search");

        JTextField searchField = new JTextField (10);

        JComboBox <String> searchCombo = new JComboBox <> ();
        searchCombo.addItem ("Index");
        searchCombo.addItem ("Name");
        searchCombo.addItem ("Skill");
        searchCombo.addItem ("Type");

        worldTextArea = new JTextArea(20,61);
        worldTextArea.setFont(new java.awt.Font ("Monospaced", Font.PLAIN, 12));
        JScrollPane worldScrollPane = new JScrollPane(worldTextArea);

        // Add Components
        file.add(readBtn);
        file.add(displayBtn);
        search.add(searchField);
        search.add(searchCombo);
        search.add(searchBtn);
        world.add(worldScrollPane, BorderLayout.CENTER);

        // Add Panels to main JPanel
        main.add(file, BorderLayout.NORTH);
        main.add(search, BorderLayout.NORTH);
        main.add(world, BorderLayout.CENTER);

        // Add main to JFrame
        add(main);

        validate ();

        // Action Listeners
        readBtn.addActionListener (e -> readFile ());
        displayBtn.addActionListener (e -> display ());
        searchBtn.addActionListener (e -> search ((String)(Objects.requireNonNull(searchCombo.getSelectedItem())), searchField.getText()));
    }

    /**
     * Reads a text file, builds the World Object
     */
    private void readFile() {
        JFileChooser jFileChooser = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt", "text");
        jFileChooser.setFileFilter(filter);
        jFileChooser.showOpenDialog(this);

        try {
            sc = new Scanner(jFileChooser.getSelectedFile());
        } catch (FileNotFoundException | NullPointerException e) {
            System.out.println(e);
        }

        world = new World(sc);

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            world.process(line);
        }
    }

    /**
     * Displays the World Object in the TextArea
     */
    private void display() {
        worldTextArea.append(world.toString());
    }

    /**
     * Main search method, utilizes helper methods to carry out various searches
     */
    private void search(String type, String target) {
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

        worldTextArea.append(resultsToString(results));
    }

    /**
     * search helper method, is used to build the search results string
     * @param results ArrayList of all results from a search
     * @return Formatted String of all results
     */
    private String resultsToString(ArrayList<Thing> results) {
        StringBuilder out = new StringBuilder("\n\nSearch Results: ");
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

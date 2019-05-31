import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * A class that creates a GUI for viewing and editing .VEC files
 */
public class VecGUI extends JFrame implements ActionListener, KeyListener {

    // Dimensions
    private static final int WIDTH = 700;
    private static final int HEIGHT = 600;

    // Constants
    private static final int BTN_WIDTH = 100;
    private static final int BTN_HEIGHT = 30;

    // Left Panel
    private JPanel leftPanel;
    private JButton Plot;
    private JButton Line;
    private JButton Rectangle;
    private JButton Ellipse;

    // Main Panel (canvas)
    private DrawingCanvas canvas;
    
    // Helper Frame
    private JFrame helpFrame;

    // Bottom Panel (colour picker)
    private JPanel bottomPanel;

    // Menu bar
    private JPanel menuPanel;

    private JButton Load;
    private JButton saveAs;
    private JButton Save;
    private JButton Undo;
    private JButton Help;

    // Drawing Canvas components
    private JButton penColourBtn;
    private JButton fillColourBtn;
    private JButton penOffBtn;
    private JButton fillOffBtn;



    /**
     * Responds to actions performed by the user
     * @param e An event
     */
    public void actionPerformed(ActionEvent e) {
        // The left panel buttons
        if (e.getSource() == Plot) {
            canvas.setActiveTool(1);
        } else if (e.getSource() == Line) {
            canvas.setActiveTool(2);
        } else if (e.getSource() == Rectangle) {
            canvas.setActiveTool(3);
        } else if (e.getSource() == Ellipse) {
            canvas.setActiveTool(4);
        }

        // The menu panel buttons
        else if (e.getSource() == Load) {
            ReadVec.readFile(canvas);
        } else if (e.getSource() == saveAs) {
            canvas.saveAs();
        } else if (e.getSource() == Save) {
            canvas.saveAs();
        } else if (e.getSource() == Undo) {
            canvas.Undo();
        } else if (e.getSource() == Help) {
            helperTab();
        }

        // The bottom panel (colours)
        else if (e.getSource() == penColourBtn) {
            Color current = canvas.getCurrentPenColour();
            Color newColour = JColorChooser.showDialog(null, "Choose a color", current);
            canvas.setCurrentPenColour(newColour);
        } else if (e.getSource() == fillColourBtn) {
            Color current = canvas.getCurrentFillColour();
            Color newColour = JColorChooser.showDialog(null, "Choose a color", current);
            canvas.setCurrentFillColour(newColour);
        } else if (e.getSource() == penOffBtn) {
            canvas.setPenColourOff();
        } else if (e.getSource() == fillOffBtn) {
            canvas.setFillColourOff();
        }
    }

    // Implement KeyListener methods to respond to keyboard input
    @Override
    public void keyTyped(KeyEvent e) {
        // Does nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {
//         Check if ctrl + z is pressed
        if (e.getKeyCode() == KeyEvent.VK_Z && e.isControlDown()) {
            canvas.Undo();
            System.out.println("Undo");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Does nothing
    }


    /**
     * Creates the left panel and lays out the buttons on it
     */
    private void createLeftPanel() {
        // Create the panel
        leftPanel = createPanel(Color.LIGHT_GRAY);

        // Create the buttons
        Plot = createButton("Plot", "Place a dot");
        Line = createButton("Line", "Draw a line between two points");
        Rectangle = createButton("Rectangle", "Draw a rectangle");
        Ellipse = createButton("Ellipse", "Draw an ellipse");
    }


    /**
     * Creates the canvas JPanel and populates it with buttons, helper function for VecGUI
     */
    private void createCanvas() {
        canvas = new DrawingCanvas();
        canvas.setLayout(new BorderLayout());
        canvas.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new LineBorder(Color.BLACK)));
        canvas.setBackground(Color.WHITE);
    }

    /**›
     * Creates the bottom panel
     */
    private void createBottomPanel() {
        // Create the bottom panel
        bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.DARK_GRAY);

        // Create the colour picker buttons
        penOffBtn = createButton("Pen Reset", "Clear the pen colour");
        fillOffBtn = createButton("Fill Off", "Clear the fill colour");
        penColourBtn = createButton("Line Colour", "Choose a line colour");
        fillColourBtn = createButton("Fill Colour", "Choose a fill colour");

        // Add the colour picker buttons to the bottom panel
        bottomPanel.add(penColourBtn);
        bottomPanel.add(penOffBtn);
        bottomPanel.add(fillColourBtn);
        bottomPanel.add(fillOffBtn);
    }


    /**
     * Creates a new button with specific text
     * @param buttonText Text to be displayed on the button
     * @param toolTipText Text to be displayed to the user upon hovering
     *                    mouse on the button
     * @return The created JButton object
     */
    private JButton createButton(String buttonText, String toolTipText) {
        JButton newButton = new JButton(buttonText);
        newButton.setToolTipText(toolTipText);
        newButton.setPreferredSize(new Dimension(BTN_WIDTH, BTN_HEIGHT));
        newButton.addActionListener(this);
        newButton.addKeyListener(this);
        newButton.setFocusable(true);
        return newButton;
    }


    /**
     * Creates a new panel with specified colour background
     * @param c The background colour of the panel
     * @return The created JPanel object
     */
    private JPanel createPanel(Color c) {
        JPanel newPanel = new JPanel();
        newPanel.setBackground(c);
        return newPanel;
    }


    /**
     * Method to add a component to given grid bag
     * layout locations. Code due to Cay Horstmann.
     * @param c The component to add
     * @param constraints The grid bag constraints to use
     * @param x The x grid position
     * @param y The y grid position
     * @param w The grid width of the component
     * @param h The grid height of the component
     */
    private void addToPanel(JPanel jp, Component c, GridBagConstraints
            constraints, int x, int y, int w, int h) {
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = w;
        constraints.gridheight = h;
        jp.add(c, constraints);
    }
    

     /**
     * Simple Helper Dialog Tab
     */
    private void helperTab() {
        JOptionPane.showMessageDialog(helpFrame,
                "\nVecSpectre 2019" +
                        "\n\nPlot - Adds a Dot" +
                        "\nLine - Drag to Draw a Line" +
                        "\nRectangle - Drag to draw a Rectangle" +
                        "\nEllipse - Drag to Draw an Ellipse" +
                        "\n\nLine Colour - Change the Colour of the Tools" +
                        "\nPen Off - Adds a Dot" +
                        "\nFill Colour - Fill Object Colour" +
                        "\nFill Off -Turn off Fill" +
                        "\n\nLoad - Load an Image (.vec)" +
                        "\nSave & Save As - Save your work" +
                        "\n\nUndo - Undo your last command",
                "Help - VecSpectre", JOptionPane.INFORMATION_MESSAGE);
    }


    /**
     * Lays out the buttons on all panels
     */
    private void layoutPanels() {
        GridBagLayout layout = new GridBagLayout();
        leftPanel.setLayout(layout);
        GridBagConstraints constraints = new GridBagConstraints();

        // Defaults
        constraints.fill = GridBagConstraints.NORTH;
        constraints.anchor = GridBagConstraints.NORTH;

        // Add buttons to the panel
        addToPanel(leftPanel, Plot, constraints, 0, 1, 1, 1);
        addToPanel(leftPanel, Line, constraints, 0, 2, 1, 1);
        addToPanel(leftPanel, Rectangle, constraints, 0, 3, 1, 1);
        addToPanel(leftPanel, Ellipse, constraints, 0, 4, 1, 1);

        // Add buttons to the panel
        addToPanel(menuPanel, Load, constraints, 0, 0, 1, 1);
        addToPanel(menuPanel, saveAs, constraints, 1, 0, 1, 1);
        addToPanel(menuPanel, Save, constraints, 2, 0, 1, 1);
        addToPanel(menuPanel, Undo, constraints, 3, 0, 1, 1);
        addToPanel(menuPanel, Help, constraints, 4, 0, 1, 1);

    }


    /**
     * Creates the menu panel
     */
    private void createMenuPanel() {
        // Create the panel
        menuPanel = createPanel(Color.DARK_GRAY);

        // Create the buttons
        Load = createButton("Load", "Load a .vec file");
        saveAs = createButton("Save As", "Save the file in a new instance");
        Save = createButton("Save", "Saves the file (ctrl + s)");
        Undo = createButton("Undo", "Undoes the last action (ctrl + z)");
        Help = createButton("Help", "Click for Help :)");

    }

    /**
     * Method to build the GUI
     */
    public VecGUI() {
        super();
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setTitle("VecSpectre"); // Never change
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the left Panel
        createLeftPanel();

        // Create the canvas
        createCanvas();

        // Create the bottom panel
        createBottomPanel();

        // Create the menu panel
        createMenuPanel();

        // Layout buttons on the panels
        layoutPanels();

        // Add keyboard listeners to the panels
        addKeyListener(this);
        this.setFocusable(true);

        // Place the panels onto the frame
        getContentPane().add(canvas, BorderLayout.CENTER);
        getContentPane().add(leftPanel, BorderLayout.WEST);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        getContentPane().add(menuPanel, BorderLayout.NORTH);

        // Some necessary defaults
        this.setResizable(true);
        repaint();
        this.setVisible(true);
    }
}
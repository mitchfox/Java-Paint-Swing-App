import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Point;


import javax.swing.*;

/**
 * A class for drawing on a JPanel based on user's input
 */
public class DrawingCanvas extends JComponent implements MouseListener {

    // BUTTONS (and their corresponding values)
    // 0 - Draw
    // 1 - Plot
    // 2 - Line
    // 3 - Rectangle
    // 4 - Ellipse
    // 5 - Polygon

    // Currently active functionality
    private int activeTool = 0;

    // Some globals
    private ArrayList<Shape> shapes;
    private ArrayList<Color> lineColours;
    private ArrayList<Color> fillColours;

    private ArrayList<Point> points;
    private Point startDrag, endDrag;
    private boolean clickStatus;

    // Image in which we're going to draw
    private BufferedImage image;

    // Graphics2D object ==> used to draw on
    private Graphics2D g2d;

    private Color currentPenColour;
    private Color currentFillColour;
    private ShapeCreator newShape;

    private WriteVec writeFile;

    /**
     * Sets the active tool
     * @param tool Which tool the user would like to be active
     */
    void setActiveTool(int tool) {
        this.activeTool = tool;
    }

    /**
     * Sets the current line colour
     * @param colour The chosen colour
     */
    void setCurrentPenColour(Color colour) {
        int colourIndex = shapes.size();
        lineColours.set(colourIndex, colour);
        currentPenColour = colour;
        writeFile.writePen(colour);
    }

    /**
     * Gets the current pen colour
     * @return The current pen colour
     */
    Color getCurrentPenColour() {
        return this.currentPenColour;
    }

    /**
     * Sets the current fill colour
     * @param colour The chosen colour
     */
    void setCurrentFillColour(Color colour) {
        int colourIndex = shapes.size();
        fillColours.set(colourIndex, colour);
        currentFillColour = colour;
        writeFile.writeFill(colour);
    }

    /**
     * Gets the current fill colour
     * @return The current fill colour
     */
    Color getCurrentFillColour() {
        return this.currentFillColour;
    }

    /**
     * Turns off the pen
     */
    void setPenColourOff() {
        currentPenColour = Color.BLACK;
        writeFile.writePenOff();
    }

    /**
     * Turns of fill
     */
    void setFillColourOff() {
        currentFillColour = null;
        int colourIndex = shapes.size();
        fillColours.set(colourIndex, null);
        writeFile.writeFillOff();
    }

    /**
     * Opens the file browser for the user to save their vec file in a new instance
     */
    void saveAs() {
        writeFile.Export();
    }

    /**
     * Undoes the previous action
     */
    void Undo() {
        if (shapes.size() > 0) {
            shapes.remove(shapes.size() - 1);
            lineColours.remove(lineColours.size() - 1);
            currentPenColour = lineColours.get(lineColours.size() - 1);
            fillColours.remove(fillColours.size() - 1);
            currentFillColour = fillColours.get(fillColours.size() - 1);
            writeFile.undoWrite();
            repaint();
        }
    }

    /**
     * Method used when opening a new file to add all that files
     * shapes to the current DrawingCanvas, removing the old shapes
     */
    void newLists(ArrayList<Shape> newShapes, ArrayList<Color> newLineColours, ArrayList<Color> newFillColours) {
        shapes.clear();
        lineColours.clear();
        fillColours.clear();
        shapes.addAll(newShapes);
        lineColours.addAll(newLineColours);
        fillColours.addAll(newFillColours);
    }


    /**
     * Invokes a new instance of the DrawingCanvas object, assigning values to various variables
     */
    DrawingCanvas() {
        super();

        // ArrayList to hold all shape objects
        shapes = new ArrayList<>();

        // ArrayList to hold all point objects
        points = new ArrayList<>();

        // Initialize colour arrays
        lineColours = new ArrayList<>();
        fillColours = new ArrayList<>();

        lineColours.add(Color.black);
        fillColours.add(null);

        clickStatus = true;
        activeTool = 1;
        this.addMouseListener(this);
        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                endDrag = new Point(e.getX(), e.getY());
                repaint();
            }
        });

        // Initialize the WriteVec object, so it can be written to
        writeFile = new WriteVec();

        // Initialize the ShapeCreator object
        newShape = new ShapeCreator();

        // Set colours
        currentFillColour = null;
        currentPenColour = Color.BLACK;
    }

    /**
     * Override of the class method
     * @param e A mouse event
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // Does nothing
    }

    /**
     * Override of the class method
     * @param e A mouse method
     */
    @Override
    public void mousePressed(MouseEvent e) {
        startDrag = new Point(e.getX(), e.getY());
        points.add(startDrag);
        repaint();
    }

    /**
     * What happens when the mouse is released
     * @param e A mouse event
     */
    @Override
    public void mouseReleased(MouseEvent e) {

        // Check if Plot is selected
        if (activeTool == 1) {
            endDrag = new Point (e.getX(), e.getY());
            Shape r = newShape.makeLine(startDrag.x, startDrag.y, startDrag.x, startDrag.y);

            // Write to data array
            writeFile.writePlot(startDrag.x, startDrag.y);

            shapes.add(r);
            lineColours.add(currentPenColour);
            fillColours.add(currentFillColour);
            points.add(endDrag);
            startDrag = null;
            endDrag = null;
        }

        // Check if line is selected
        else if (activeTool == 2) {
            endDrag = new Point (e.getX(), e.getY());
            Shape r = newShape.makeLine(startDrag.x, startDrag.y, endDrag.x, endDrag.y);

            // Write to data array
            writeFile.writeLine(startDrag.x, startDrag.y, endDrag.x, endDrag.y);

            shapes.add(r);
            lineColours.add(currentPenColour);
            fillColours.add(currentFillColour);
            points.add(endDrag);
            startDrag = null;
            endDrag = null;
        }

        // Check if rectangle is selected
        else if (activeTool == 3) {
            endDrag = new Point(e.getX(), e.getY());
            Shape r = newShape.makeRectangle(startDrag.x, startDrag.y, endDrag.x, endDrag.y);

            // Write to data array
            writeFile.writeRectangle(startDrag.x, startDrag.y, endDrag.x, endDrag.y);

            shapes.add(r);
            points.add(endDrag);
            lineColours.add(currentPenColour);
            fillColours.add(currentFillColour);
            startDrag = null;
            endDrag = null;
        }

        // Check if Ellipse is selected
        else if (activeTool == 4) {
            endDrag = new Point(e.getX(), e.getY());
            Shape r = newShape.makeEllipse(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
            shapes.add(r);

            // Write to data array
            writeFile.writeEllipse(startDrag.x, startDrag.y, endDrag.x, endDrag.y);

            lineColours.add(currentPenColour);
            fillColours.add(currentFillColour);


            points.add(endDrag);
            startDrag = null;
            endDrag = null;
        }

        repaint();
    }

    /**
     * Override of the class method
     * @param e A mouse event
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Override of the class method
     * @param e A mouse event
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }

    // GRID (currently turned off)
//    private void paintBackground(Graphics2D g2){
//        g2.setPaint(Color.RED);
//        for (int i = 0; i < getSize().width; i += 10) {
//            Shape line = new Line2D.Float(i, 0, i, getSize().height);
//            g2.draw(line);
//        }
//
//        for (int i = 0; i < getSize().height; i += 10) {
//            Shape line = new Line2D.Float(0, i, getSize().width, i);
//            g2.draw(line);
//        }
//    }

    /**
     * A method to paint the components and render them all onto the canvas
     * @param g A Graphics object
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        paintBackground(g2);

        g2.setStroke(new BasicStroke(2));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.50f));

        // Draw & paint all the shapes
        for (int i = 0; i < shapes.size(); i++) {
            // Fill the shape
            if (fillColours.get(i) != null) {
                g2.setPaint(fillColours.get(i));
                g2.fill(shapes.get(i));
            }
            // Paint the shape
            g2.setPaint(lineColours.get(i));
            g2.draw(shapes.get(i));

        }

        // Shadow background (as user draws)
        if (startDrag != null && endDrag != null) {
            g2.setPaint(Color.LIGHT_GRAY);
            Shape r;

            // Check if Plot is selected
            if (activeTool == 1) {
                r = newShape.makeLine(startDrag.x, startDrag.y, startDrag.x, startDrag.y);
            }

            // Check if Line is selected
            else if (activeTool == 2) {
                r = newShape.makeLine(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
            }

            // Check if Rectangle is selected
            else if (activeTool == 3) {
                r = newShape.makeRectangle(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
            }

            // Check if Ellipse is selected
            else if (activeTool == 4) {
                r = newShape.makeEllipse(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
            }

            else {
                r = null;
            }

            g2.draw(r);
        }
    }
}
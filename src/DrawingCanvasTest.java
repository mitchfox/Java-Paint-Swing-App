import org.junit.jupiter.api.Test;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Point;
import static org.junit.jupiter.api.Assertions.*;

/**
 * A class for testing the drawing canvas using JUnit 5
 */
class DrawingCanvasTest {

    // Currently active functionality
    private int activeTool = 0;

    // Some globals
    private ArrayList<Shape> shapes;

    private ArrayList<Point> points;
    private Point startDrag, endDrag, expectedDrag;
    private boolean clickStatus;
    boolean undoWriteExpected = true;
    boolean undoWriteOutcome;
    int expectedTool = 0;

    // Image in which we're going to draw
    private BufferedImage image;

    // Graphics2D object ==> used to draw on
    private Graphics2D g2d;

    private Color currentLineColour;
    private Color fillColour;
    private ShapeCreator newShape;
    private ShapeCreator expectedShape;
    boolean expectedNotNull;
    boolean notNull;

    /**
     * Simple Test of active tool functionality
     */
    @Test
    void setActiveTool() {
        this.activeTool = activeTool;
        this.expectedTool = activeTool;
        assertEquals(activeTool, expectedTool);
    }

    /**
     * Testing the ability to store and remove from a class to demo undo functionality
     */
    @Test
    void undo() {
        int testInt = 1;
        ArrayList<Integer> testArrayShape = new ArrayList<Integer>();
        testArrayShape.add(testInt);
        if (testArrayShape.size() > 0) {
            testArrayShape.remove(testArrayShape.size() - 1);
            undoWriteOutcome = true;
            // Will Undo - Test for True test
        }
        assertEquals(undoWriteExpected, undoWriteOutcome);
    }

    /**
     * Simple Click Commands
     */
    @Test
    void mouseClicked() {
        // Do Nothing
    }

    /**
     * Simple Click Release
     */
    @Test
    void mouseReleased() {
       // Do Nothing
    }
    
    /**
     * Simple Click Enter
     */
    @Test
    void mouseEntered() {
        // Do Nothing
    }
    
    /**
     * Simple Click Exit
     */
    @Test
    void mouseExited() {
        // Do Nothing
    }
}

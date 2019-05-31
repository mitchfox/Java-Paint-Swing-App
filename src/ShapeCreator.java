import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

/**
 * Helper class for creating shapes
 */
public class ShapeCreator {

    /**
     * Creates a Rectangle2D.Float object
     * @param x1 The first x coordinate
     * @param y1 The first y coordinate
     * @param x2 The second x coordinate
     * @param y2 The second y coordinate
     * @return a Rectangle2D.Float object
     */
    public Rectangle2D.Float makeRectangle(float x1, float y1, float x2, float y2) {
        return new Rectangle2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

    /**
     * Creates a Line2D.Float object
     * @param x1 The first x coordinate
     * @param y1 The first y coordinate
     * @param x2 The second x coordinate
     * @param y2 The second y coordinate
     * @return A Line2D.Float object
     */
    public Line2D.Float makeLine(float x1, float y1, float x2, float y2) {
        return new Line2D.Float(x1, y1, x2, y2);
    }

    /**
     * Creates a Ellipse2D.Float object
     * @param x1 The first x coordinate
     * @param y1 The first y coordinate
     * @param x2 The second x coordinate
     * @param y2 The second y coordinate
     * @return A Ellipse2D.Float object
     */
    public Ellipse2D.Float makeEllipse(float x1, float y1, float x2, float y2) {
        return new Ellipse2D.Float(x1, y1, x2, y2);
    }
}

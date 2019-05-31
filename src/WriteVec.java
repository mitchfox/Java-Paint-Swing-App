import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A class for writing to an array object, with the main purpose being to convert and save this object as a .vec file
 */
public class WriteVec {

    private ArrayList<String> data = new ArrayList<>();

    WriteVec() {

    }

    /**
     * Method to get the data ArrayList
     * @return Arraylist the data ArrayList
     */
    public ArrayList<String> getArray() {
        return this.data;
    }

    /**
     * Called when a draw command is undone from the canvas, removes the last element added to the arraylist
     */
    void undoWrite() {
        data.remove(data.size() - 1);
    }

    /**
     * Adds a PLOT command to the data ArrayList for conversion to a .vec file
     * @param x1 The x coordinate of the PLOT point
     * @param y1 The y coordinate of the PLOT point
     */
    void writePlot(float x1, float y1) {
        String command = "PLOT " + x1 / 1000 + " " + y1 / 1000 + " ";
        data.add(command);
    }

    /**
     * Adds a LINE command to the data ArrayList for conversion to a .vec file
     * @param x1 The first x coordinate of the LINE
     * @param y1 The first y coordinate of the LINE
     * @param x2 The second y coordinate of the LINE
     * @param y2 The second y coordinate of the LINE
     */
    void writeLine(float x1, float y1, float x2, float y2) {
        String command = "LINE " + x1 / 1000 + " " + y1 / 1000 + " " + x2 / 1000 + " " + y2 / 1000 + " ";
        data.add(command);
    }

    /**
     * Adds a RECTANGLE command to the data ArrayList for conversion to a .vec file
     * @param x1 The first x coordinate of the RECTANGLE
     * @param y1 The first y coordinate of the RECTANGLE
     * @param x2 The second x coordinate of the RECTANGLE
     * @param y2 The second y coordinate of the RECTANGLE
     */
    void writeRectangle(float x1, float y1, float x2, float y2) {
        String command = "RECTANGLE " + x1 / 1000 + " " + y1 / 1000 + " " + x2 / 1000 + " " + y2 / 1000 + " ";
        data.add(command);
    }

    /**
     * Adds an ELLIPSE command to the data ArrayList for conversion to a .vec file
     * @param x1 The first x coordinate of the ELLIPSE
     * @param y1 The first y coordinate of the ELLIPSE
     * @param x2 The second x coordinate of the ELLIPSE
     * @param y2 The second y coordinate of the ELLIPSE
     */
    void writeEllipse(float x1, float y1, float x2, float y2) {
        String command = "ELLIPSE " + x1 / 1000 + " " + y1 / 1000 + " " + x2 / 1000 + " " + y2 / 1000 + " ";
        data.add(command);
    }

    /**
     * Adds a PEN command to the data ArrayList for conversion to a .vec file
     * @param color The chosen PEN colour
     */
    void writePen(Color color) {
        String hex = "#" + Integer.toHexString(color.getRGB()).substring(2);
        String command = "PEN " + hex;
        data.add(command);
    }

    /**
     * Adds a FILL command to the data ArrayList for conversion to a .vec file
     * @param color The chosen FILL colour
     */
    void writeFill(Color color) {
        String hex = "#" + Integer.toHexString(color.getRGB()).substring(2);
        String command = "FILL " + hex;
        data.add(command);
    }

    /**
     * Adds a PEN OFF command to the data ArrayList for conversion to a .vec file
     */
    void writePenOff() {
        String command = "PEN OFF";
        data.add(command);
    }

    /**
     * Adds a FILL OFF command to the data ArrayList for conversion to a .vec file
     */
    void writeFillOff() {
        String command = "FILL OFF";
        data.add(command);
    }

    /**
     * Opens up a file explorer to choose a save location for the file
     */
    void Export() {
        JFileChooser FC = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        FC.setAcceptAllFileFilterUsed(false);
        FC.setDialogTitle("Please save as a .vec file");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".vec", "vec");
        FC.addChoosableFileFilter(filter);

        if (FC.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file;
            file = FC.getSelectedFile();
            if (!file.toString().endsWith(".vec")) {
                file = new File(file.toString() + ".vec");
            }

            try {
                FileWriter writer = new FileWriter(file);
                for (String command : data) {
                    writer.write(command);
                    writer.write(System.getProperty("line.separator"));
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

import java.io.*;
import java.util.ArrayList;

import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/**
 * Reading and Display .vec files
 */
public class ReadVec {

    /**
     * Reads a .vec file
     * @param canvas A DrawingCanvas object
     */
    public static void readFile(DrawingCanvas canvas) {

        // File Navigation and selection
        JFileChooser FC = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        FC.setAcceptAllFileFilterUsed(false);
        FC.setDialogTitle("Select a .vec file");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".vec", "vec");
        FC.addChoosableFileFilter(filter);

        JFileChooser newChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        newChooser.setAcceptAllFileFilterUsed(false);
        newChooser.setDialogTitle("Select a .vec file");
        FileNameExtensionFilter filterBy = new FileNameExtensionFilter(".vec", "vec");
        newChooser.addChoosableFileFilter(filterBy);

        // StringBuilder to hold the file's content
        StringBuilder sb = new StringBuilder();

        if (FC.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                File file;
                String line = null;
                file = FC.getSelectedFile();

                FileReader fileReader = new FileReader(file);

                // Wrap fileReader in BufferedReader
                BufferedReader br = new BufferedReader(fileReader);

                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }

                // Always close files.
                br.close();

            } catch (IOException e){
                e.printStackTrace();
            }
        }

        /**
         * Find first words on each line to determine which shapes /colours to apply to canvas
         */

        // Array to hold the new file shapes
        ArrayList<Shape> shapes = new ArrayList<>();

        // Array to hold the new file colours
        ArrayList<Color> fillColours = new ArrayList<>();
        ArrayList<Color> lineColours = new ArrayList<>();

        // Initialize a new shape creator object
        ShapeCreator newShape = new ShapeCreator();

        // Array to hold each individual string
        String[] lines = sb.toString().split("\\n");

        // Current pen & fill colour
        Color currentLineColour = Color.BLACK;
        Color currentFillColour = null;

        for (int i = 0; i < lines.length; i++) {

            // Break up the line
            String[] splitString = lines[i].split(" ");

            // Find the first word
            String firstWord = splitString[0];

            // Check for each case
            if (firstWord.equals("PLOT") || firstWord.equals("Plot")) {
                float x = Float.parseFloat(splitString[1]) * 1000;
                float y = Float.parseFloat(splitString[2]) * 1000;
                Shape plot = newShape.makeLine(x, y, x, y);
                shapes.add(plot);
            }

            else if (firstWord.equals("LINE") || firstWord.equals("Line")) {
                float x1 = Float.parseFloat(splitString[1]) * 1000;
                float y1 = Float.parseFloat(splitString[2]) * 1000;
                float x2 = Float.parseFloat(splitString[3]) * 1000;
                float y2 = Float.parseFloat(splitString[4]) * 1000;
                Shape line = newShape.makeLine(x1, y1, x2, y2);
                shapes.add(line);
            }

            else if (firstWord.equals("RECTANGLE")|| firstWord.equals("Rectangle")) {
                float x1 = Float.parseFloat(splitString[1]) * 1000;
                float y1 = Float.parseFloat(splitString[2]) * 1000;
                float x2 = Float.parseFloat(splitString[3]) * 1000;
                float y2 = Float.parseFloat(splitString[4]) * 1000;
                Shape rectangle = newShape.makeRectangle(x1, y1, x2, y2);
                shapes.add(rectangle);
            }

            else if (firstWord.equals("ELLIPSE") || firstWord.equals("Ellipse")) {
                float x1 = Float.parseFloat(splitString[1]) * 1000;
                float y1 = Float.parseFloat(splitString[2]) * 1000;
                float x2 = Float.parseFloat(splitString[3]) * 1000;
                float y2 = Float.parseFloat(splitString[4]) * 1000;
                Shape ellipse = newShape.makeEllipse(x1, y1, x2, y2);
                shapes.add(ellipse);
            }

            else if (firstWord.equals("PEN") || firstWord.equals("Pen")) {
                if (splitString[1].equals("OFF")) {
                    currentLineColour = Color.BLACK;
                }
                else {
                    currentLineColour = Color.decode(splitString[1]);
                }
            }

            else if (firstWord.equals("FILL") || firstWord.equals("Fill")) {
                if (splitString[1].equals("OFF")) {
                    currentFillColour = null;
                }
                else {
                    currentFillColour = Color.decode(splitString[1]);
                }
            }

            else {
                System.out.println("Polygon not implemented sorry");
            }

            // Paint as specified
            if (i != 0) {
                lineColours.add(currentLineColour);
            }
            if (i != 0) {
                fillColours.add(currentFillColour);
            }
        }

        // Return the new shapes array
        canvas.newLists(shapes, lineColours, fillColours);
        canvas.repaint();
    }
}

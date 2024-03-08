//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Main {
    private int rows;
    private int columns;

    public Main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            int rows = Integer.parseInt(JOptionPane.showInputDialog("Enter number of rows:"));
            int columns = Integer.parseInt(JOptionPane.showInputDialog("Enter number of columns:"));
            this.rows = rows;
            this.columns = columns;
            JFrame frame = new JFrame("Treasure Game");
            frame.setDefaultCloseOperation(3);
            Grid grid = new Grid(rows, columns);
            List<Location> motionlessLocations = grid.getAllMotionlessLocations();
            frame.add(grid);
            frame.setSize(1400, 1000);
            frame.setLocationRelativeTo((Component)null);
            frame.setVisible(true);
            List<Location> locations = new ArrayList();
            this.RandomDesign(grid, locations);
        });
    }

    private void RandomDesign(Grid grid, List<Location> occupiedLocations) {
        Random random = new Random();
        int numberOfIcons = 13;
        int numberOfIconsDynamic = 2;
        int[][] randomObject = new int[numberOfIcons][2];

        for(int j = 0; j < numberOfIcons - numberOfIconsDynamic; ++j) {
            int randomX = random.nextInt(this.rows);
            int randomY = random.nextInt(this.columns);
            if (!this.isinarray(randomObject, randomX, randomY)) {
                randomObject[j][0] = randomX;
                randomObject[j][1] = randomY;
                MotionlessObject motionless = new MotionlessObject(grid, this.rows, this.columns, occupiedLocations);
                grid.add(motionless);
            }

            grid.placeRandomIcons(40);
        }

    }

    private boolean isinarray(int[][] array, int x, int y) {
        boolean isin = false;

        for(int i = 0; i < array.length; ++i) {
            if (array[i][0] == x && array[i][1] == y) {
                isin = true;
            }
        }

        return isin;
    }

    public static void main(String[] args) {
        new Main(args);
    }
}

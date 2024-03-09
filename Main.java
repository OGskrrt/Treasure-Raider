import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    private int rows, columns;

    public Main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            int rows = Integer.parseInt(JOptionPane.showInputDialog("Enter number of rows:"));
            int columns = Integer.parseInt(JOptionPane.showInputDialog("Enter number of columns:"));
            this.rows = rows;
            this.columns = columns;

            JFrame frame = new JFrame("Treasure Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            System.out.println("Main içi\n");
            Grid grid = new Grid(rows, columns);
            // Add Null Grid as Frame, fill it later,
            frame.add(grid);
            frame.setSize(1400, 1000);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            // Null Grid added, now fill it...
            grid.RandomLocationsForIcons(20);

        });

    }



    public static void main(String[] args) {
        new Main(args);
    }

}

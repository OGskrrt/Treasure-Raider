import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Main {
    private int rows, columns;
    private JFrame frame;
    private Grid grid;


    public Main(String[] args) {
        frame = new JFrame("Treasure Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CustomPanel panel = new CustomPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JButton createMapButton = new JButton("YENİ HARİTA OLUŞTUR");
        createMapButton.setForeground(Color.BLACK);
        createMapButton.setBackground(Color.WHITE);
        createMapButton.setOpaque(true);
        createMapButton.setBorderPainted(false);

        JButton startButton = new JButton("BAŞLAT");
        startButton.setForeground(Color.BLACK);
        startButton.setBackground(Color.WHITE);
        startButton.setOpaque(true);
        startButton.setBorderPainted(false);

        createMapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start();
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start();
            }
        });


        gbc.insets = new Insets(10, 0, 0, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(createMapButton, gbc);

        gbc.gridy = 1;
        panel.add(startButton, gbc);

        frame.add(panel);
        frame.setSize(1400, 1000);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        /////////


    }



    public static void main(String[] args) {
        new Main(args);
    }

    private void start() {
        JTextField rowsField = new JTextField(5);
        JTextField columnsField = new JTextField(5);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Rows:"));
        myPanel.add(rowsField);
        myPanel.add(Box.createHorizontalStrut(15));
        myPanel.add(new JLabel("Columns:"));
        myPanel.add(columnsField);

        int result = JOptionPane.showOptionDialog(null, myPanel, "Please Enter Values",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

        if (result == JOptionPane.OK_OPTION) {
            int rows, columns;
            try {
                rows = Integer.parseInt(rowsField.getText());
                columns = Integer.parseInt(columnsField.getText());
                this.rows = rows;
                this.columns = columns;


                grid = new Grid(rows, columns);

                frame.getContentPane().removeAll();
                frame.add(grid);
                frame.revalidate();

                grid.RandomLocationsForIcons(20, 3);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter values.");
            }
        }
        grid.Animation();
    }

}

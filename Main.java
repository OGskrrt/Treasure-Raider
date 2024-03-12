import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class Main {
    private int rows, columns;
    private JFrame frame;
    private Grid grid;
    private Character character;


    private void createAndShowGUI() {
        frame = new JFrame("Treasure Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CustomPanel panel = new CustomPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JButton createMapButton = new JButton("CREATE A NEW MAP");
        styleButton(createMapButton);


        JButton startButton = new JButton("START");
        styleButton(startButton);

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
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void styleButton(JButton button) {

        button.setForeground(Color.BLACK);


        Color whiteBlue = new Color(240, 240, 255);
        button.setBackground(whiteBlue);

        button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        button.setOpaque(true);
        button.setBorderPainted(true);

        button.setFont(new Font("Arial", Font.BOLD, 14));

        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setVerticalAlignment(SwingConstants.CENTER);

        button.setMargin(new Insets(1, 15, 15, 25));

        button.setPreferredSize(new Dimension(220, 40));
        button.setMinimumSize(new Dimension(220, 40));
        button.setMaximumSize(new Dimension(220, 60));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Color whiteBlue = new Color(250, 240, 245);
                button.setBackground(whiteBlue);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(whiteBlue);
            }
        });
    }

    public Main(String[] args) {
        createAndShowGUI();
    }

    private void initializeCharacter() {
        Location initialLocation = grid.getInitialCharacterLocation();
        character = new Character(frame, grid, rows, columns, grid.getIconLocations(), initialLocation);
        grid.setCharacter(character);
    }

    public static void main(String[] args) {
        new Main(args);
    }

    private void start() {
        JTextField rowsField = new JTextField(5);
        JTextField columnsField = new JTextField(5);

        Dimension textFieldDimension = new Dimension(200, 30);
        Color textFieldBackground = new Color(240, 240, 255);
        rowsField.setPreferredSize(textFieldDimension);
        columnsField.setPreferredSize(textFieldDimension);
        rowsField.setBackground(textFieldBackground);
        columnsField.setBackground(textFieldBackground);

        JPanel myPanel = new JPanel(new GridLayout(0, 1));
        myPanel.add(new JLabel("Rows:"));
        myPanel.add(rowsField);
        myPanel.add(new JLabel("Columns:"));
        myPanel.add(columnsField);

        int result = JOptionPane.showOptionDialog(null, myPanel, "Please Enter Values",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

        if (result == JOptionPane.OK_OPTION) {
            try {
                rows = Integer.parseInt(rowsField.getText());
                columns = Integer.parseInt(columnsField.getText());

                frame.getContentPane().removeAll();
                grid = new Grid(rows, columns);
                adjustFrameSize(rows,columns);
                Location initialLocation = new Location(0, 0, 4, 4, null);
                character = new Character(frame, grid, rows, columns, new ArrayList<>(), initialLocation);
                CharacterMovement characterMovement = new CharacterMovement(character, grid, rows, columns);

                grid.setCharacter(character);

                frame.getContentPane().removeAll();
                frame.add(grid);
                frame.revalidate();
                frame.repaint();

                grid.RandomLocationsForIcons(20, 3);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter numeric values.");
            }
        }
    }
    private void adjustFrameSize(int rows, int columns) {
        int cellSize = 50;
        int width = Math.max(columns * cellSize, 500);
        int height = Math.max(rows * cellSize + 100, 450);
        frame.setSize(new Dimension(width, height));
        frame.setLocationRelativeTo(null);
    }

}

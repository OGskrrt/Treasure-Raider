import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main extends JFrame implements ActionListener {
    private Grid2 grid2;
    private Grid grid;

    private JTextField rowField, colField;
    private JButton createButton, startButton;

    private JFrame prevFrame; // Önceki pencereyi tutmak için kullanılacak değişken

    public Main() {
        setTitle("Map UI");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Pencereyi ekranın ortasına yerleştirir.

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    // Arka plan resminin yüklenmesi ve boyutlarının ayarlanması
                    BufferedImage image = ImageIO.read(new File("C:\\Users\\skrrrt\\Desktop\\prolab4\\background.jpg"));
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        mainPanel.setLayout(null);

        JPanel panel = new JPanel();
        panel.setOpaque(false); // Panelin arka planının şeffaf olmasını sağlar
        panel.setLayout(null);
        panel.setBounds(50, 200, 400, 150);
        mainPanel.add(panel);

        JLabel rowLabel = new JLabel("Row:");
        rowLabel.setBounds(0, 0, 80, 30);
        rowLabel.setForeground(new Color(204, 250, 255)); // Parlak mor-pembe renk
        rowLabel.setFont(rowLabel.getFont().deriveFont(Font.BOLD, 16f)); // Yazı kalın hale getirildi
        panel.add(rowLabel);

        rowField = new JTextField();
        rowField.setBounds(110, 0, 150, 30);
        rowField.setFont(rowField.getFont().deriveFont(Font.BOLD, 14f));
        rowField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(204, 0, 255))); // Alt çizgi eklendi
        rowField.setForeground(new Color(204, 0, 255)); // Metin rengi parlak mor-pembe yapıldı
        panel.add(rowField);

        JLabel colLabel = new JLabel("Column:");
        colLabel.setBounds(0, 50, 80, 30);
        colLabel.setForeground(new Color(204, 250, 255)); // Parlak mor-pembe renk
        colLabel.setFont(colLabel.getFont().deriveFont(Font.BOLD, 16f)); // Yazı kalın hale getirildi
        panel.add(colLabel);

        colField = new JTextField();
        colField.setBounds(110, 50, 150, 30);
        colField.setFont(colField.getFont().deriveFont(Font.BOLD, 14f));
        colField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(204, 0, 255))); // Alt çizgi eklendi
        colField.setForeground(new Color(204, 0, 255)); // Metin rengi parlak mor-pembe yapıldı
        panel.add(colField);

        createButton = new JButton("Create a New Map");
        createButton.setBounds(50, 110, 200, 40);
        createButton.setFont(createButton.getFont().deriveFont(Font.BOLD, 16f)); // Buton yazıları kalın hale getirildi
        createButton.setForeground(new Color(0, 0, 0)); // Metin rengi parlak mor-pembe yapıldı
        createButton.setBackground(Color.WHITE); // Arka plan rengi beyaz yapıldı
        createButton.addActionListener(this);
        panel.add(createButton);

        startButton = new JButton("Start");
        startButton.setBounds(260, 110, 150, 40);
        startButton.setFont(startButton.getFont().deriveFont(Font.BOLD, 16f)); // Buton yazıları kalın hale getirildi
        startButton.setForeground(new Color(0, 0, 0)); // Metin rengi parlak mor-pembe yapıldı
        startButton.setBackground(Color.WHITE); // Arka plan rengi beyaz yapıldı
        startButton.addActionListener(this);
        panel.add(startButton);

        add(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createButton) {
            if (prevFrame != null) {
                prevFrame.dispose(); // Önceki pencereyi kapat
            }
            start();
        }
        if (e.getSource() == startButton) {
            if (prevFrame != null) {
                prevFrame.dispose(); // Önceki pencereyi kapat
            }
            start2();
        }
    }

    private void start2() {
        int rows, columns;
        try {
            rows = Integer.parseInt(rowField.getText());
            columns = Integer.parseInt(colField.getText());

            grid2 = new Grid2(rows, columns);

            JFrame frame = new JFrame("Let's Play!");
            frame.setSize(1400, 1000);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().setBackground(Color.GREEN); // Arka plan rengi olarak koyu yeşil (Minecraft yeşili)
            frame.setLocationRelativeTo(null); // Pencereyi ekranın ortasına yerleştirir.
            frame.setVisible(true);
            prevFrame = frame; // Önceki pencereyi sakla

            frame.add(grid2);

            grid2.RandomLocationsForIcons(20, 3);
            grid2.Animationbee();
            grid2.Animationbird();
            grid2.MoveBitxh();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter valid values for rows and columns.");
        }
    }

    private void start() {
        int rows, columns;
        try {
            rows = Integer.parseInt(rowField.getText());
            columns = Integer.parseInt(colField.getText());

            grid = new Grid(rows, columns);

            JFrame frame = new JFrame("Let's Play!");
            frame.setSize(1400, 1000);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().setBackground(Color.GREEN); // Arka plan rengi olarak koyu yeşil (Minecraft yeşili)
            frame.setLocationRelativeTo(null); // Pencereyi ekranın ortasına yerleştirir.
            frame.setVisible(true);
            prevFrame = frame; // Önceki pencereyi sakla

            frame.add(grid);

            grid.RandomLocationsForIcons(20, 3);
            grid.Animationbee();
            grid.Animationbird();
            //grid.MoveBitxh();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter valid values for rows and columns.");
        }
    }
}

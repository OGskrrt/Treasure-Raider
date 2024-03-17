import javax.swing.*;
import java.awt.*;

public class CustomPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(30, 100, 30));
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
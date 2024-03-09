import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Grid extends JPanel {
    private int rows;
    private int columns;
    private List<Location> iconLocations;
    private MotionlessObject motionlessObject;
    int blockWidth;
    int blockHeight;

    public Grid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.iconLocations = new ArrayList<>();

        List<Location> occupiedLocations = new ArrayList<>();
        this.motionlessObject = new MotionlessObject(this, rows, columns, occupiedLocations);

    }
    public List<MotionlessObject> getAllMotionlessObjects() {
        List<MotionlessObject> motionlessObjects = new ArrayList<>();
        for (Location location : iconLocations) {
            MotionlessObject motionless = new MotionlessObject(this, location.getY(), location.getX(), null);
            motionlessObjects.add(motionless);
        }
        return motionlessObjects;
    }
    public List<Location> getAllMotionlessLocations() {
        List<Location> motionlessLocations = new ArrayList<>();
        List<MotionlessObject> motionlessObjects = getAllMotionlessObjects();
        for (MotionlessObject motionlessObject : motionlessObjects) {
            motionlessLocations.add(motionlessObject.gettLocation());
        }
        return motionlessLocations;
    }



    public void placeRandomIcons(int numberOfIcons) {
        Random random = new Random();
        int[][] randomObject = new int[numberOfIcons][2];

        for (int i = 0; i < numberOfIcons; i++) {
            int row = random.nextInt(rows-11)+5;
            int col = random.nextInt(columns-11)+5;


            int iconX = col * blockWidth;
            int iconY = row * blockHeight;
            int iconWidth = blockWidth;
            int iconHeight = blockHeight;


            int maxOffset = 10;
            int maxRow = Math.min(row + maxOffset, rows - 1);
            int maxCol = Math.min(col + maxOffset, columns - 1);


            int minRow = Math.max(row - maxOffset, 0);
            int minCol = Math.max(col - maxOffset, 0);

            boolean isOverlapping = false;
            for (Location existingLocation : iconLocations) {

                if (existingLocation.getX() >= minCol && existingLocation.getX() <= maxCol &&
                        existingLocation.getY() >= minRow && existingLocation.getY() <= maxRow) {
                    isOverlapping = true;
                    break;
                }
            }

            // Çakışma yoksa ekle
            if (!isOverlapping) {
                iconLocations.add(new Location(col, row, 1, 1));
            }
        }
        repaint();
    }
    private boolean areAreasOverlapping(Location location1, Location location2) {
        for (int i = 0; i < location1.getWidth(); i++) {
            for (int j = 0; j < location1.getHeight(); j++) {
                for (int k = 0; k < location2.getWidth(); k++) {
                    for (int l = 0; l < location2.getHeight(); l++) {
                        if (location1.getAreaX()[i][j] == location2.getAreaX()[k][l] ||
                                location1.getAreaY()[i][j] == location2.getAreaY()[k][l]) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        blockWidth = width / columns;
        blockHeight = height / rows;


        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                int x = column * blockWidth;
                int y = row * blockHeight;

                if (column < (columns / 2)) {
                    g.setColor(new Color(185, 232, 234));
                } else {
                    g.setColor(new Color(255, 223, 34));
                }
                g.fillRect(x, y, blockWidth, blockHeight);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, blockWidth, blockHeight);



            }
        }
        for (Location location : iconLocations) {
            for (int row = 0; row < rows; row++) {
                for (int column = 0; column < columns; column++) {
                    int x = column * blockWidth;
                    int y = row * blockHeight;
                    if (location.getX() == column && location.getY() == row) {

                        BufferedImage pickedIcon = getRandomIcon();

                        if (pickedIcon != null) {

                            int iconWidth = pickedIcon.getWidth();
                            int iconHeight = pickedIcon.getHeight();

                            int iconX = x + (blockWidth - iconWidth) / 2;
                            int iconY = y + (blockHeight - iconHeight) / 2;
                            Graphics2D g2d = (Graphics2D) g.create();
                            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                            g2d.drawImage(pickedIcon, iconX, iconY, iconWidth, iconHeight, null);
                            g2d.dispose();
                        }
                    }



                }
            }

        }
    }


    private BufferedImage getRandomIcon() {
        Random random = new Random();
        int what = random.nextInt(4) + 1;
        switch (what) {
            case 1:
                return resizeIcon(motionlessObject.getMountainIcon(), 150, 150);
            case 2:
                int size = random.nextInt(4) + 2;
                return resizeIcon(motionlessObject.getTreeIcon(), size * blockWidth, size * blockHeight);
            case 3:
                return resizeIcon(motionlessObject.getWallIcon(), 10 * blockWidth, blockHeight);
            case 4:
                size = random.nextInt(2) + 2;
                return resizeIcon(motionlessObject.getRockIcon(), size * blockWidth, size * blockHeight);
            default:
                return null;
        }
    }


    private BufferedImage resizeIcon(BufferedImage icon, int width, int height) {
        if (width > 0 && height > 0) {
            Image newImage = icon.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage buffered = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = buffered.createGraphics();
            g2d.drawImage(newImage, 0, 0, null);
            g2d.dispose();
            return buffered;
        } else {
            return icon;
        }
    }


}
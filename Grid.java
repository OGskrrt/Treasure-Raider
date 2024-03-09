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
    public int pickedIconWidth;
    public int pickedIconHeight;
    private int[] iconCount = new int[5];

    public Grid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.iconLocations = new ArrayList<>();
        System.out.println("Grid Constructor\n");
        List<Location> occupiedLocations = new ArrayList<>();
        this.motionlessObject = new MotionlessObject(this, rows, columns, occupiedLocations);

    }

    public void RandomLocationsForIcons(int numberOfIcons) {
        Random random = new Random();
        for (int i = 0; i < numberOfIcons; i++) {
            boolean isOverlapping = false;
            while(!isOverlapping){
                // Get random row and column
                int row = random.nextInt(rows-11)+5;
                int col = random.nextInt(columns-11)+5;
                // Get random icon;
                BufferedImage newimage = getRandomIcon();
                Location cmonman = new Location(col, row, pickedIconWidth, pickedIconHeight, newimage);
                for (Location existingLocation : iconLocations) {
                    //Look all locations if random row and col conflict with any other icons
                    if(areAreasOverlapping(existingLocation, cmonman)){
                        isOverlapping = true;
                        break;
                    }
                }
                //If there is no overlap, add it to icon locations list
                if (!isOverlapping) {
                    isOverlapping = true;
                    iconLocations.add(new Location(col, row, pickedIconWidth, pickedIconHeight, newimage));
                }
            }

        }
        // IDK what is this but its necessary, I think...
        repaint();
    }
    private boolean areAreasOverlapping(Location location1, Location location2) {

        // Get the corner points of Location 1
        int x1 = location1.getX();
        int y1 = location1.getY();
        int w1 = location1.getWidth();
        int h1 = location1.getHeight();

        // Get the corner points of Location 2
        int x2 = location2.getX();
        int y2 = location2.getY();
        int w2 = location2.getWidth();
        int h2 = location2.getHeight();

        // Check if they are adjacent
        if (x1 + w1 < x2 || x2 + w2 < x1 || y1 + h1 < y2 || y2 + h2 < y1) {
            return false;
        }

        // Check the corner points of Location 1
        for (int i = 0; i < w1; i++) {
            for (int j = 0; j < h1; j++) {
                int px = x1 + i;
                int py = y1 + j;

                // If it is inside Location 2, there is an intersection
                if (px >= x2 && px < x2 + w2 && py >= y2 && py < y2 + h2) {
                    return true;
                }
            }
        }

        // Check the corner points of Location 2
        for (int i = 0; i < w2; i++) {
            for (int j = 0; j < h2; j++) {
                int px = x2 + i;
                int py = y2 + j;

                // If it is inside Location 1, there is an intersection
                if (px >= x1 && px < x1 + w1 && py >= y1 && py < y1 + h1) {
                    return true;
                }
            }
        }

        // If there is no intersection, return false
        return false;
    }




    @Override
    protected void paintComponent(Graphics g) {
        System.out.println("Paint Component giriş \n");
        //Start with Grid same time
        super.paintComponent(g);

        // Draw Grid
        int width = getWidth();
        int height = getHeight();
        blockWidth = width / columns;
        blockHeight = height / rows;
        // Start to drawing grid
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
        // Grid Completed

        //This section for draw icons
        for (Location location : iconLocations){
            if(location.pickedIcon==null){
                System.out.println("OMG there is no icon! DAD, HELP! \n");
            }
            int a = location.x*blockWidth;
            int b = location.y*blockHeight;

            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawImage(location.pickedIcon, a, b, location.getWidth()*blockWidth, location.getHeight()*blockHeight, null);
            // And draw...
            g2d.dispose();


        }
        //Grid and motionless icons drawn.
    }


    private BufferedImage getRandomIcon() {
        System.out.println("random içi");
        // It's To get random icon, no icon should be taken more than 5.
        Random random = new Random();
        boolean ops = false;
        int what = 0;
        while (!ops) {
            what = random.nextInt(4) + 1;
            if (iconCount[what - 1] < 5) {
                ops = true;
            }
        }
        //increase counter
        iconCount[what - 1]++;

        switch (what) {
            case 1:
                pickedIconWidth = 15;
                pickedIconHeight = 15;
                return resizeIcon(motionlessObject.getMountainIcon(), 150, 150);
            case 2:
                int size = random.nextInt(4) + 2;
                pickedIconWidth = size;
                pickedIconHeight = size;
                return resizeIcon(motionlessObject.getTreeIcon(), size * blockWidth, size * blockHeight);
            case 3:
                pickedIconWidth = 10;
                pickedIconHeight = 1;
                return resizeIcon(motionlessObject.getWallIcon(), 10 * blockWidth, blockHeight);
            case 4:
                size = random.nextInt(2) + 2;
                pickedIconWidth = size;
                pickedIconHeight = size;
                return resizeIcon(motionlessObject.getRockIcon(), size * blockWidth, size * blockHeight);
            default:
                return null;
        }
    }


    private BufferedImage resizeIcon(BufferedImage icon, int width, int height) {
        // To resize as wished, don't touch here pls.
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
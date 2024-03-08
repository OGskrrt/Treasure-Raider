//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.swing.JPanel;

public class Grid extends JPanel {
    private int rows;
    private int columns;
    private List<Location> iconLocations;
    private MotionlessObject motionlessObject;
    private MovingObject movingObject;
    int blockWidth;
    int blockHeight;
    private int grid;

    public Grid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.iconLocations = new ArrayList();
        List<Location> occupiedLocations = new ArrayList();
        this.motionlessObject = new MotionlessObject(this, rows, columns, occupiedLocations);
        this.movingObject = new MovingObject(this, this, this.rows, this.columns, new ArrayList());
    }

    public List<MotionlessObject> getAllMotionlessObjects() {
        List<MotionlessObject> motionlessObjects = new ArrayList();
        Iterator var2 = this.iconLocations.iterator();

        while(var2.hasNext()) {
            Location location = (Location)var2.next();
            MotionlessObject motionless = new MotionlessObject(this, location.getY(), location.getX(), (List)null);
            motionlessObjects.add(motionless);
        }

        return motionlessObjects;
    }

    public List<Location> getAllMotionlessLocations() {
        List<Location> motionlessLocations = new ArrayList();
        List<MotionlessObject> motionlessObjects = this.getAllMotionlessObjects();
        Iterator var3 = motionlessObjects.iterator();

        while(var3.hasNext()) {
            MotionlessObject motionlessObject = (MotionlessObject)var3.next();
            motionlessLocations.add(motionlessObject.gettLocation());
        }

        return motionlessLocations;
    }

    public List<Location> getIconLocations() {
        return this.iconLocations;
    }

    public void placeRandomIcons(int numberOfIcons) {
        Random random = new Random();
        int[][] randomObject = new int[numberOfIcons][2];

        for(int i = 0; i < numberOfIcons; ++i) {
            int row = random.nextInt(this.rows - 11) + 5;
            int col = random.nextInt(this.columns - 11) + 5;
            int var10000 = col * this.blockWidth;
            var10000 = row * this.blockHeight;
            int iconWidth = this.blockWidth;
            int iconHeight = this.blockHeight;
            int maxOffset = 10;
            int maxRow = Math.min(row + maxOffset, this.rows - 1);
            int maxCol = Math.min(col + maxOffset, this.columns - 1);
            int minRow = Math.max(row - maxOffset, 0);
            int minCol = Math.max(col - maxOffset, 0);
            boolean isOverlapping = false;
            Iterator var17 = this.iconLocations.iterator();

            while(var17.hasNext()) {
                Location existingLocation = (Location)var17.next();
                if (existingLocation.getX() >= minCol && existingLocation.getX() <= maxCol && existingLocation.getY() >= minRow && existingLocation.getY() <= maxRow) {
                    isOverlapping = true;
                    break;
                }
            }

            if (!isOverlapping) {
                this.iconLocations.add(new Location(col, row, 1, 1));
            }
        }

        this.repaint();
    }

    private boolean areAreasOverlapping(Location location1, Location location2) {
        for(int i = 0; i < location1.getWidth(); ++i) {
            for(int j = 0; j < location1.getHeight(); ++j) {
                for(int k = 0; k < location2.getWidth(); ++k) {
                    for(int l = 0; l < location2.getHeight(); ++l) {
                        if (location1.getAreaX()[i][j] == location2.getAreaX()[k][l] || location1.getAreaY()[i][j] == location2.getAreaY()[k][l]) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = this.getWidth();
        int height = this.getHeight();
        this.blockWidth = width / this.columns;
        this.blockHeight = height / this.rows;

        int row;
        int column;
        for(row = 0; row < this.rows; ++row) {
            for(column = 0; column < this.columns; ++column) {
                row = column * this.blockWidth;
                column = row * this.blockHeight;
                if (column < this.columns / 2) {
                    g.setColor(new Color(185, 232, 234));
                } else {
                    g.setColor(new Color(255, 223, 34));
                }

                g.fillRect(row, column, this.blockWidth, this.blockHeight);
                g.setColor(Color.BLACK);
                g.drawRect(row, column, this.blockWidth, this.blockHeight);
            }
        }

        Iterator var16 = this.iconLocations.iterator();

        while(var16.hasNext()) {
            Location location = (Location)var16.next();

            for(row = 0; row < this.rows; ++row) {
                for(column = 0; column < this.columns; ++column) {
                    int x = column * this.blockWidth;
                    int y = row * this.blockHeight;
                    if (location.getX() == column && location.getY() == row) {
                        BufferedImage pickedIcon = this.getRandomIcon();
                        if (pickedIcon != null) {
                            int iconWidth = pickedIcon.getWidth();
                            int iconHeight = pickedIcon.getHeight();
                            int iconX = x + (this.blockWidth - iconWidth) / 2;
                            int iconY = y + (this.blockHeight - iconHeight) / 2;
                            Graphics2D g2d = (Graphics2D)g.create();
                            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                            g2d.drawImage(pickedIcon, iconX, iconY, iconWidth, iconHeight, (ImageObserver)null);
                            g2d.dispose();
                        }
                    }
                }
            }
        }

    }

    private BufferedImage getRandomIcon() {
        Random random = new Random();
        int what = random.nextInt(7) + 1;
        int size;
        switch (what) {
            case 1:
                return this.resizeIcon(this.motionlessObject.getMountainIcon(), 150, 150);
            case 2:
                size = random.nextInt(4) + 2;
                return this.resizeIcon(this.motionlessObject.getTreeIcon(), size * this.blockWidth, size * this.blockHeight);
            case 3:
                return this.resizeIcon(this.motionlessObject.getWallIcon(), 10 * this.blockWidth, this.blockHeight);
            case 4:
                size = random.nextInt(2) + 2;
                return this.resizeIcon(this.motionlessObject.getRockIcon(), size * this.blockWidth, size * this.blockHeight);
            case 5:
                size = random.nextInt(2) + 2;
                return this.resizeIcon(this.movingObject.getBirdIcon(), size * this.blockWidth, size * this.blockHeight);
            case 6:
                size = random.nextInt(2) + 2;
                return this.resizeIcon(this.movingObject.getBeeIcon(), size * this.blockWidth, size * this.blockHeight);
            case 7:
                size = random.nextInt(4) + 2;
                return this.resizeIcon(this.movingObject.getCatIcon(), size * this.blockWidth, size * this.blockHeight);
            default:
                return null;
        }
    }

    private BufferedImage resizeIcon(BufferedImage icon, int width, int height) {
        if (width > 0 && height > 0) {
            Image newImage = icon.getScaledInstance(width, height, 4);
            BufferedImage buffered = new BufferedImage(width, height, 2);
            Graphics2D g2d = buffered.createGraphics();
            g2d.drawImage(newImage, 0, 0, (ImageObserver)null);
            g2d.dispose();
            return buffered;
        } else {
            return icon;
        }
    }
}

package src;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Character {
    private BufferedImage characterIcon;
    private int rows;
    private int columns;
    private Grid2 grid;
    private int currX;
    private int currY;
    private List<Point> Path = new ArrayList<>();
    private List<Point> emeraldMemory = new ArrayList<>();
    private List<Point> goldMemory = new ArrayList<>();
    private List<Point> silverMemory = new ArrayList<>();
    private List<Point> copperMemory = new ArrayList<>();
    private List<Point> allTreasureMemory = new ArrayList<>();
    private List<Point> obstacleMemory = new ArrayList<>();
    private Point Destination;
    private List<Point> visitedPath = new ArrayList<>();


    public Character(Grid2 grid, int rows, int columns){
        //super();
        this.grid = grid;
        this.rows = rows;
        this.columns = columns;
        characterIcon = loadAndMakeTransparent("C:\\Users\\skrrrt\\Desktop\\prolab4\\character.png");
        //characterIcon = ImageIO.read(new File("C:\\Users\\merve\\OneDrive\\Masaüstü\\picture\\character.jpg"));
        Random random = new Random();
        int randomX;
        int randomY;
        while(true){
            randomX = random.nextInt(columns);
            randomY = random.nextInt(rows);
            boolean dorumu = grid.isCollision2(randomX, randomY);
            if(!dorumu){
                break;
            }
        }
        currX = randomX;
        currY = randomY;
    }


    // Getter, Setter, things go brrr
    public int getCurrX() {
        return currX;
    }

    public void setCurrX(int currX) {
        this.currX = currX;
    }

    public int getCurrY() {
        return currY;
    }

    public void setCurrY(int currY) {
        this.currY = currY;
    }

    public BufferedImage getCharacterIcon() {
        return characterIcon;
    }

    public List<Point> getPath() {
        return Path;
    }

    public int getRows() {
        return rows;
    }

    public List<Point> getEmeraldMemory() {
        return emeraldMemory;
    }

    public List<Point> getGoldMemory() {
        return goldMemory;
    }

    public List<Point> getSilverMemory() {
        return silverMemory;
    }

    public List<Point> getCopperMemory() {
        return copperMemory;
    }

    public List<Point> getAllTreasureMemory() {
        return allTreasureMemory;
    }

    public List<Point> getObstacleMemory() {
        return obstacleMemory;
    }

    // Make png icon transparent, complete. Please don't touch.
    public BufferedImage loadAndMakeTransparent(String path) {
        try {
            BufferedImage image = ImageIO.read(new File(path));
            return makeColorTransparent(image, new Color(255, 255, 255, 0)); // Beyaz rengi şeffaf yap
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BufferedImage makeColorTransparent(BufferedImage im, final Color color) {
        ImageFilter filter = new RGBImageFilter() {
            public int markerRGB = color.getRGB() | 0xFF000000;

            public final int filterRGB(int x, int y, int rgb) {
                if ((rgb | 0xFF000000) == markerRGB) {
                    return 0x00FFFFFF & rgb;
                } else {
                    return rgb;
                }
            }
        };

        ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        return toBufferedImage(Toolkit.getDefaultToolkit().createImage(ip));
    }

    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        return bimage;
    }

    // Transparent OK


    public Point getDestination() {
        return Destination;
    }

    public void setDestination(Point destination) {
        Destination = destination;
    }

    public List<Point> getVisitedPath() {
        return visitedPath;
    }
}

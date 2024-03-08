//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JLabel;

public class MotionlessObject extends JLabel {
    private BufferedImage MountainIcon;
    private BufferedImage TreeIcon;
    private BufferedImage WallIcon;
    private BufferedImage RockIcon;
    private int rows;
    private int columns;
    private List<Location> locations;
    private Container container;
    private List<Location> iconLocations;

    public MotionlessObject(Container container, int rows, int columns, List<Location> occupiedLocations) {
        this.container = container;
        this.rows = rows;
        this.columns = columns;
        this.locations = occupiedLocations;
        this.iconLocations = new ArrayList();
        this.MountainIcon = this.loadAndMakeTransparent("mountain.png");
        this.TreeIcon = this.loadAndMakeTransparent("tree.png");
        this.WallIcon = this.loadAndMakeTransparent("wall.jpg");
        this.RockIcon = this.loadAndMakeTransparent("rock.png");
    }

    public Location gettLocation() {
        return new Location(this.rows, this.columns, 1, 1);
    }

    private BufferedImage loadAndMakeTransparent(String path) {
        try {
            BufferedImage image = ImageIO.read(new File(path));
            return makeColorTransparent(image, new Color(255, 255, 255, 0));
        } catch (IOException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static BufferedImage makeColorTransparent(BufferedImage im, final Color color) {
        ImageFilter filter = new RGBImageFilter() {
            public int markerRGB = color.getRGB() | -16777216;

            public final int filterRGB(int x, int y, int rgb) {
                return (rgb | -16777216) == this.markerRGB ? 16777215 & rgb : rgb;
            }
        };
        ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        return toBufferedImage(Toolkit.getDefaultToolkit().createImage(ip));
    }

    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage)img;
        } else {
            BufferedImage bimage = new BufferedImage(img.getWidth((ImageObserver)null), img.getHeight((ImageObserver)null), 2);
            Graphics2D bGr = bimage.createGraphics();
            bGr.drawImage(img, 0, 0, (ImageObserver)null);
            bGr.dispose();
            return bimage;
        }
    }

    public BufferedImage getMountainIcon() {
        return this.MountainIcon;
    }

    public BufferedImage getTreeIcon() {
        return this.TreeIcon;
    }

    public BufferedImage getWallIcon() {
        return this.WallIcon;
    }

    public BufferedImage getRockIcon() {
        return this.RockIcon;
    }
}

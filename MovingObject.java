//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.Timer;

public class MovingObject extends JLabel {
    private BufferedImage BeeIcon;
    private BufferedImage BirdIcon;
    private BufferedImage CatIcon;
    private int rows;
    private int columns;
    private List<Location> iconLocations;
    private Container container;
    private Timer timer;
    private Grid grid;
    private int step = 1;

    public MovingObject(Container container, Grid grid, int rows, int columns, List<Location> iconLocations) {
        this.container = container;
        this.grid = grid;
        this.rows = rows;
        this.columns = columns;
        this.iconLocations = iconLocations;
        this.timer = new Timer(500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MovingObject.this.newPosition();
            }
        });
        this.timer.setRepeats(true);
        this.timer.start();
        this.BeeIcon = this.loadAndMakeTransparent("C:\\Users\\skrrrt\\Desktop\\prolab4\\rock.png");
        this.BirdIcon = this.loadAndMakeTransparent("C:\\Users\\skrrrt\\Desktop\\prolab4\\rock.png");
        this.CatIcon = this.loadAndMakeTransparent("C:\\Users\\skrrrt\\Desktop\\prolab4\\rock.png");
    }

    public Location gettLocation() {
        //List<Location> iconLocations = this.grid.getIconLocations();
        return !iconLocations.isEmpty() ? (Location)iconLocations.get(iconLocations.size() - 1) : new Location(0, 0, 1, 1);
    }

    public void newPosition() {
        Location location;
        int newX;
        for(Iterator var1 = this.iconLocations.iterator(); var1.hasNext(); location.setX(newX)) {
            location = (Location)var1.next();
            newX = location.getX() + 2 * this.step;
            if (newX >= this.columns || newX < 0) {
                this.step *= -1;
                newX = location.getX() + 2 * this.step;
            }
        }

        this.container.repaint();
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

    public BufferedImage getBeeIcon() {
        return this.BeeIcon;
    }

    public BufferedImage getBirdIcon() {
        return this.BirdIcon;
    }

    public BufferedImage getCatIcon() {
        return this.CatIcon;
    }
}

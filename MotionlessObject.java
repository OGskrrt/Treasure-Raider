import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageFilter;
import java.awt.image.RGBImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MotionlessObject extends JLabel {
    private BufferedImage MountainIcon;
    private BufferedImage TreeIcon;
    private BufferedImage WallIcon;
    private BufferedImage RockIcon;
    private int rows;
    private int columns;


    // Where begins in GRID section
    public MotionlessObject(Container container, int rows, int columns, List<Location> occupiedLocations) {
        super();
        this.rows = rows;
        this.columns = columns;

        // Load icons
        MountainIcon = loadAndMakeTransparent("C:\\Users\\skrrrt\\Desktop\\prolab4\\mountain.png");
        TreeIcon = loadAndMakeTransparent("C:\\Users\\skrrrt\\Desktop\\prolab4\\tree.png");
        WallIcon = loadAndMakeTransparent("C:\\Users\\skrrrt\\Desktop\\prolab4\\wall.jpg");
        RockIcon = loadAndMakeTransparent("C:\\Users\\skrrrt\\Desktop\\prolab4\\rock.png");
    }
    // This and Transparent section created in Grid.


    // Make png icon transparent, complete. Please don't touch.
    private BufferedImage loadAndMakeTransparent(String path) {
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



    // GET methods for icons, required for placeRandomIcon
    public BufferedImage getMountainIcon() {
        return MountainIcon;
    }

    public BufferedImage getTreeIcon() {
        return TreeIcon;
    }

    public BufferedImage getWallIcon() {
        return WallIcon;
    }

    public BufferedImage getRockIcon() {
        return RockIcon;
    }

}

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MotionlessObject extends JLabel {
    private BufferedImage MountainSummer;
    private BufferedImage MountainWinter;
    private BufferedImage TreeSummer;
    private BufferedImage TreeWinter;
    private BufferedImage WallIcon;
    private BufferedImage RockIcon;
    private List<Location> MotionlessLocations;
    private int rows;
    private int columns;


    // Where begins in GRID section
    public MotionlessObject(Container container, int rows, int columns) {
        super();
        this.rows = rows;
        this.columns = columns;

        // Load icons

        MountainSummer = loadAndMakeTransparent("C:\\Users\\skrrrt\\Desktop\\prolab4\\mountainsummer.jpg");
        MountainWinter = loadAndMakeTransparent("C:\\Users\\skrrrt\\Desktop\\prolab4\\mountain.jpg");
        TreeSummer = loadAndMakeTransparent("C:\\Users\\skrrrt\\Desktop\\prolab4\\tree.png");
        TreeWinter = loadAndMakeTransparent("C:\\Users\\skrrrt\\Desktop\\prolab4\\treewinter.png");
        WallIcon = loadAndMakeTransparent("C:\\Users\\skrrrt\\Desktop\\prolab4\\wall.jpg");
        RockIcon = loadAndMakeTransparent("C:\\Users\\skrrrt\\Desktop\\prolab4\\rock.png");
        /*
        MountainSummer = loadAndMakeTransparent("C:\\Users\\merve\\OneDrive\\Masaüstü\\picture\\mountainsummer.jpg");
        MountainWinter = loadAndMakeTransparent("C:\\Users\\merve\\OneDrive\\Masaüstü\\picture\\mountain.jpg");
        TreeSummer = loadAndMakeTransparent("C:\\Users\\merve\\OneDrive\\Masaüstü\\picture\\tree.png");
        TreeWinter = loadAndMakeTransparent("C:\\Users\\merve\\OneDrive\\Masaüstü\\picture\\treewinter.png");
        WallIcon = loadAndMakeTransparent("C:\\Users\\merve\\OneDrive\\Masaüstü\\picture\\wall.jpg");
        RockIcon = loadAndMakeTransparent("C:\\Users\\merve\\OneDrive\\Masaüstü\\picture\\rock.png");
*/
    }
    // This and Transparent section created in Grid.


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



    // GET methods for icons, required for placeRandomIcon
    public BufferedImage getMountainSummer() {
        return MountainSummer;
    }
    public BufferedImage getMountainWinter() {
        return MountainWinter;
    }

    public BufferedImage getTreeSummer() {
        return TreeSummer;
    }
    public BufferedImage getTreeWinter() {
        return TreeWinter;
    }

    public BufferedImage getWallIcon() {
        return WallIcon;
    }

    public BufferedImage getRockIcon() {
        return RockIcon;
    }

    public void setMotionlessLocations(List<Location> motionlessLocations) {
        MotionlessLocations = motionlessLocations;
    }
}

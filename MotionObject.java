import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MotionObject {
    private static BufferedImage bee;
    private static BufferedImage bird;
    private static BufferedImage beeareaicon;
    private static BufferedImage birdareaicon;
    private List<Location> MotionLocations;
    public MotionObject() {
        bee = loadAndMakeTransparent("C:\\Users\\skrrrt\\Desktop\\prolab4\\bee.png");
        bird = loadAndMakeTransparent("C:\\Users\\skrrrt\\Desktop\\prolab4\\bird.png");
        beeareaicon = loadAndMakeTransparent("C:\\Users\\skrrrt\\Desktop\\prolab4\\beearea.png");
        birdareaicon = loadAndMakeTransparent("C:\\Users\\skrrrt\\Desktop\\prolab4\\birdarea.png");
    }
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

    public static BufferedImage getBee() {
        return bee;
    }

    public static BufferedImage getBird() {
        return bird;
    }

    public static BufferedImage getBeeareaicon() {
        return beeareaicon;
    }

    public static BufferedImage getBirdareaicon() {
        return birdareaicon;
    }

    public void setMotionLocations(List<Location> motionLocations) {
        MotionLocations = motionLocations;
    }
}

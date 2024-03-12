import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.List;

public class Treasures extends MotionlessObject{
    private List<Location> TreasureLocations;
    private BufferedImage Gold;
    private BufferedImage Silver;
    private BufferedImage Copper;
    private BufferedImage Emerald;
    public Treasures(Container container, int rows, int columns) {
        super(container, rows, columns);
        this.Gold= loadAndMakeTransparent("C:\\Users\\skrrrt\\Desktop\\prolab4\\gold.png");
        this.Silver= loadAndMakeTransparent("C:\\Users\\skrrrt\\Desktop\\prolab4\\silver.png");
        this.Copper= loadAndMakeTransparent("C:\\Users\\skrrrt\\Desktop\\prolab4\\bronze.png");
        this.Emerald= loadAndMakeTransparent("C:\\Users\\skrrrt\\Desktop\\prolab4\\emerald.png");

    }

    public void setTreasureLocations(List<Location> treasureLocations) {
        TreasureLocations = treasureLocations;
    }

    public BufferedImage getGold() {
        return Gold;
    }

    public BufferedImage getSilver() {
        return Silver;
    }

    public BufferedImage getCopper() {
        return Copper;
    }

    public BufferedImage getEmerald() {
        return Emerald;
    }
}

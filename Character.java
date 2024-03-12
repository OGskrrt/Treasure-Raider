import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Character {
    private BufferedImage characterIcon;
    private int rows;
    private int columns;
    private List<Location> iconLocations;
    private Container container;
    private Grid grid;
    private Location currentPosition;
    BufferedImage newimage = null;

    public Character(Container container,Grid grid, int rows, int columns,List<Location> iconLocations, Location initialLocation){
        super();
        this.container = container;
        this.grid = grid;
        this.rows = rows;
        this.columns = columns;
        this.iconLocations = iconLocations;
        this.currentPosition = new Location(0, 0,4,4,newimage);

        try {
            characterIcon = ImageIO.read(new File("C:\\Users\\merve\\OneDrive\\Masaüstü\\picture\\character.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Location getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Location newPosition) {
        this.currentPosition = newPosition;
    }

    public BufferedImage getCharacterIcon() {
        return characterIcon;
    }
}

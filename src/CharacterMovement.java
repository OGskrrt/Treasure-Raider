import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.List;
/*
  --------------------------------- Movement Flow--------------------------------
                              Character Movement draft

drawCharacter();                // Draw the icon
removeSmoke();                  // remove smoke
removeTreasure();               // If there is treasure, remove it
recordWayCharacterCome();       // save previous rectangles and paint
drawWay();                      // draw the way character come
browseAndRecord();              // record seen treasures into character memory
                                                    chooseBehavior(); // choose character behavior
                                                    chosenPath(); // make some math with chosen path and change icon's X, Y
*/
public class CharacterMovement extends JPanel{
    //private final Graphics g;
    private Timer moveTimer;
    private Random random = new Random();
    private Character character;
    private int gridRows;
    private int gridColumns;
    private Grid2 grid;
    private double blockWidth;
    private double blockHeight;
    private smoke smoke;
    private Location.TreasureType type;
    //private Graphics g;

    public CharacterMovement(Grid2 grid, int gridRows, int gridColumns, smoke smoke, Character character) {
        //this.g = g;
        this.smoke = smoke;
        this.grid=grid;
        this.character = character;
        this.gridRows = gridRows;
        //this.grid = grid;
        this.gridColumns = gridColumns;
        int width = getWidth();
        int height = getHeight();
        blockWidth = (double) width / gridColumns;
        blockHeight = (double) height / gridRows;

    }



    public void paint(Graphics g){
        /*if(g!=null){
            System.out.println("first smoke inside");
            smoke.smokeit(g);
        }*/
        //super.paintComponent(g);
        //this.g=g;
        System.out.println("CH mov paint inside");
        //super.paintComponent(g);
        // Draw character icon
        // drawCharacter()
        int x = (int) (character.getCurrX());
        int y = (int) (character.getCurrY());
        if (true) {
            BufferedImage characterIcon = character.getCharacterIcon();
            if (characterIcon != null) {
                int a = (int) (character.getCurrX() * blockWidth);
                int b = (int) (character.getCurrY() * blockHeight);
                int characterWidth = (int) (1 * blockWidth);
                int characterHeight = (int) (1 * blockHeight);

                if(g!=null){
                    System.out.println("first g.draw");
                    g.drawImage(characterIcon, a, b, characterWidth, characterHeight, this);
                }

            }
        }
        // Remove Smoke
        // removeSmoke()
        smoke.smokeAreaRemove(x, y,3);
        System.out.println("smoke remove");
        // Remove if there is treasure
        // removeTreasure()
        /*Iterator<Location> iterator = grid.getIconLocationsTreasure().iterator();
        while (iterator.hasNext()) {
            System.out.println("Inside iterator room");
            Location location = iterator.next();
            if (location.getX() == x || location.getY() == y) {
                iterator.remove();
            }
        }*/

        // Record the way character comes
        // recordWayCharacterCome()
        character.getPath().add(new Point(x, y));

        // Draw Character way
        // drawWay()
        System.out.println("draw way");
        if (character.getPath().size() > 2) {
            System.out.println("Character Path draw");
            if(g!=null){
                g.setColor(Color.RED);
            }
            for (int k = 1; k < character.getPath().size(); k++) {
                Point lastPath = character.getPath().get(k - 1);
                Point exactPath = character.getPath().get(k);
                if(g!=null){
                    g.drawLine((int) (lastPath.getX() * blockWidth + blockWidth / 2),
                            (int) (lastPath.getY() * blockHeight + blockHeight / 2),
                            (int) (exactPath.getX() * blockWidth + blockWidth / 2),
                            (int) (exactPath.getY() * blockHeight + blockHeight / 2));
                }

            }
        }
        System.out.println("Browse and record");
        // Browse and record
        // browseAndRecord()
        // Look for treasures first
        browseAndRecordTreasure(x, y, 3, grid.getIconLocationsTreasure());
        // Look for obstacles
        browseAndRecordObstacles(x, y, 3, grid.getIconLocationsObstacle());
        // ChooseBehavior()
        // Go random for just now
        // 1 for random
        System.out.println("Behaivor");
        int behavior = ChooseBehavior();
        // calculate X and Y as chosen behavior previous
        switch(behavior){
            case 1:
                System.out.println("Go random");
                //goRandom(x,y);
                break;
        }

        if (moveTimer == null) {
            moveTimer = new Timer(100, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Override seciton with timer");
                    repaint();  // Trigger repaint on timer tick
                }
            });
            moveTimer.start();
        }
    }
    public void goRandom(int x, int y){
        boolean moved = false;
        while (!moved) {
            System.out.println("Random while ");
            int movementt = random.nextInt(4);
            switch (movementt) {
                case 0: // Up
                    if (y > 0 && grid.isCollision(x, y - 1)) {
                        y--;
                        moved = true;
                    }
                    break;
                case 1: // Down
                    if (y < gridRows - 1 && grid.isCollision(x, y + 1)) {
                        y++;
                        moved = true;
                    }
                    break;
                case 2: // Right
                    if (x < gridColumns - 1 && grid.isCollision(x + 1, y)) {
                        x++;
                        moved = true;
                    }
                    break;
                case 3: // Left
                    if (x > 0 && grid.isCollision(x - 1, y)) {
                        x--;
                        moved = true;
                    }
                    break;
            }
        }
        character.setCurrX(x);
        character.setCurrX(y);
    }
    public int ChooseBehavior(){
        if (character.getDestination() != null){
            return 2;
        }
        return 1;
    }
    public void browseAndRecordObstacles(int x, int y, int size, List<Location> listForSearch){
        // Please make Size odd
        // Check for boundaries
        // Check matrix boundaries so that the boundaries to be checked to do not go beyond the grid boundaries
        int xsize = x+size;
        int xstart = x-size;
        if(x+size>gridRows){
            xsize=gridRows;
        }
        if(x-size<0){
            xstart = 0;
        }
        int ysize = y+size;
        int ystart = y-size;
        if(y+size>gridColumns){
            ysize=gridColumns;
        }
        if(y-size<0){
            ystart = 0;
        }

        // Browse and save
        // Look for 7x7 area where character middle of it.
        // Check all cells coordinate if it's saved in any obstacle location
        // if that coordinate is obstacle coordinate, save it to character memory as obstacle coordinate.
        for (int i = xstart; i < xsize; i++) {
            for (int j = ystart; j < ysize; j++) {
                if(isThisExistInListObstacle(i, j, listForSearch, null)){
                    if(!isThisExistInListObstacle(i, j, null, character.getObstacleMemory())){
                        character.getObstacleMemory().add(new Point(i,j));
                    }
                }
            }
        }
    }
    private boolean isThisExistInListObstacle(int x, int y, List<Location> listForSearch, List<Point> pointList){
        if(listForSearch != null){
            for (Location location : listForSearch) {
                int[][] areaX = location.getAreaX();
                int[][] areaY = location.getAreaY();
                for (int i = 0; i < areaX.length; i++) {
                    for (int j = 0; j < areaX[i].length; j++) {
                        if (areaX[i][j] == x && areaY[i][j] == y) {
                            return true;
                        }
                    }
                }
            }
        }
        if (pointList != null){
            for (Point point : pointList) {
                if (point.getX() == x && point.getY() == y) {
                    return true;
                }
            }
        }
        return false;

    }

    public void browseAndRecordTreasure(int x, int y, int size, List<Location> listForSearch){
        // Please make Size odd
        // Check for boundaries
        // Check matrix boundaries so that the boundaries to be checked to do not go beyond the grid boundaries
        int xsize = x+size;
        int xstart = x-size;
        if(x+size>gridRows){
            xsize=gridRows;
        }
        if(x-size<0){
            xstart = 0;
        }
        int ysize = y+size;
        int ystart = y-size;
        if(y+size>gridColumns){
            ysize=gridColumns;
        }
        if(y-size<0){
            ystart = 0;
        }
        // Browse and save
        // Look for 7x7 area where character middle of it.
        // Check all cells coordinate if it's saved in any treasure location
        // if that coordinate is treasure coordinate, save it to character memory as treasure coordinate.
        // Save that coordinate to all treasure locations and discrete locations according to the type of treasure
        for (int i = xstart; i < xsize; i++) {
            for (int j = ystart; j < ysize; j++) {
                if(isThisExistInListTreasure(i, j, listForSearch, null)){
                    if(!isThisExistInListTreasure(i, j, null, character.getAllTreasureMemory())){
                        switch(type){
                            case Location.TreasureType.EMERALD:
                                character.getEmeraldMemory().add(new Point(i,j));
                                break;
                            case Location.TreasureType.GOLD:
                                character.getGoldMemory().add(new Point(i,j));
                                break;
                            case Location.TreasureType.SILVER:
                                character.getSilverMemory().add(new Point(i,j));
                                break;
                            case Location.TreasureType.COPPER:
                                character.getCopperMemory().add(new Point(i,j));
                                break;
                        }
                        character.getAllTreasureMemory().add(new Point(i, j));
                        character.setDestination(new Point(i, j));
                    }
                }
            }
        }
    }

    private boolean isThisExistInListTreasure(int x, int y, List<Location> listForSearch, List<Point> pointList){
        if(listForSearch != null){
            for (Location location : listForSearch) {
                if (location.getX() == x && location.getY() == y) {
                    type = location.getTreasureType();
                    return true;
                }
                int[][] areaX = location.getAreaX();
                int[][] areaY = location.getAreaY();
                for (int i = 0; i < areaX.length; i++) {
                    for (int j = 0; j < areaX[i].length; j++) {
                        if (areaX[i][j] == x && areaY[i][j] == y) {
                            type = location.getTreasureType();
                            return true;
                        }
                    }
                }
            }
        }
        if (pointList != null){
            for (Point point : pointList) {

                if (point.getX() == x && point.getY() == y) {
                    return true;
                }
            }
        }
        return false;
    }
}



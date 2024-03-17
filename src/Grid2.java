package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Grid2 extends JPanel {
    private int rows;
    private int columns;
    private List<Location> iconBarrier;
    private List<Location> AllIconLocations;
    private List<Location> iconLocationsObstacle;
    private List<Location> iconLocationsstatic;
    private List<Location> iconLocationsdynamic;
    private List<Location> iconLocationsTreasure;
    private MotionlessObject motionlessObject;
    private MotionObject motionObject;
    private Treasures treasures;
    double blockWidth;
    double blockHeight;
    private int stepscount;
    public int pickedIconWidth = 15;
    public int pickedIconHeight = 15;

    public int pickedDYNWidth = 2;
    public int pickedDYNHeight = 2;
    private int attemp=0;
    private Timer moveTimer;
    private double distancebee = 0;
    private double distancebird = 0;
    private boolean directionbee = true;
    private boolean directionbird = true;
    private Location cmonman;
    public Graphics g;
    private CharacterMovement move;
    private Character character;
    private smoke smoke;
    private Random random = new Random();
    private String treasureMessage = "";
    private Font messageFont = new Font("Arial", Font.BOLD, 18);

    public Grid2(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.iconBarrier = new ArrayList<>();
        this.AllIconLocations = new ArrayList<>();
        this.iconLocationsstatic = new ArrayList<>();
        this.iconLocationsdynamic = new ArrayList<>();
        this.iconLocationsObstacle = new ArrayList<>();
        this.iconLocationsTreasure = new ArrayList<>();
        this.motionlessObject = new MotionlessObject(this, rows, columns);
        this.motionObject = new MotionObject();
        this.treasures = new Treasures(this, rows, columns);
        smoke = new smoke(rows, columns);
        character = new Character(this, rows, columns);
        move = new CharacterMovement(this, rows, columns, smoke, character);

    }
    // Choose random location and save
    public void RandomLocationsForIcons(int numberOfIcons, int numberofdynamic) {
        randomstatic(numberOfIcons);
        randomdynamic(numberofdynamic);
        randomTreasure(numberOfIcons);
        // IDK what is this but its necessary, I think...
        repaint();
        // After that, PaintComponent section will start.
    }
    private void randomTreasure(int numberOfIcons){
        Random random = new Random();
        int col = 0, row=0;
        BufferedImage newimage = null;
        for (int i = 0; i < numberOfIcons; i++) {
            boolean isOverlapping = true;
            while(isOverlapping){
                isOverlapping = false;
                // Get random row and column
                col = random.nextInt(columns);
                // Get random icon;
                newimage = getRandomTreasure(i);
                row = random.nextInt(rows);
                // Save it to location object
                Location cmonman = new Location(col, row, 1, 1, newimage);
                for (Location existingLocation : AllIconLocations) {
                    //Look all locations if random row and col conflict with any other icons
                    if(areAreasOverlapping(existingLocation, cmonman)){
                        isOverlapping = true;
                        break;
                    }
                }
                //If there is no overlap, add it to icon locations list
            }
            AllIconLocations.add(new Location(col, row, 1, 1, newimage));
            iconLocationsTreasure.add(new Location(col, row, 1, 1, newimage, i));

        }
        treasures.setTreasureLocations(iconLocationsTreasure);
    }
    private void randomdynamic(int numberofdynamic){
        BufferedImage picdynamicicon;
        Random random = new Random();
        int col = 0;
        int row=0;
        BufferedImage newimagedyn = null;
        for (int i = 0; i < numberofdynamic; i++) {
            boolean isOverlapping = true;
            while(isOverlapping){
                isOverlapping = false;
                // Get random row and column
                col = random.nextInt(columns-20)+10;
                // Get random icon;
                newimagedyn = getRandomIconDynamic(i);
                row = random.nextInt(rows-20)+10;
                picdynamicicon = getRandomIconDynamic2(i);
                // Save it to location object
                cmonman = new Location(col, row, pickedDYNWidth, pickedDYNHeight, newimagedyn);
                cmonman.setDynamicicon(picdynamicicon, i);
                for (Location existingLocation : AllIconLocations) {
                    //Look all locations if random row and col conflict with any other icons
                    if(areAreasOverlapping(existingLocation, cmonman)){
                        isOverlapping = true;
                        break;
                    }
                }
                //If there is no overlap, add it to icon locations list
            }
            AllIconLocations.add(cmonman);
            iconLocationsObstacle.add(cmonman);
            iconLocationsdynamic.add(cmonman);
        }
        motionObject.setMotionLocations(iconLocationsdynamic);
    }
    private void randomstatic(int numberOfIcons){
        Random random = new Random();
        int col = 0, row=0;
        BufferedImage newimage = null;
        for (int i = 0; i < numberOfIcons; i++) {
            boolean isOverlapping = true;
            while(isOverlapping){
                isOverlapping = false;
                // Get random row and column
                col = random.nextInt(columns);
                // Get random icon;
                newimage = getRandomIcon(col, i);
                row = random.nextInt(rows);
                if(col+pickedIconWidth > columns || row + pickedIconHeight >rows || col-pickedIconWidth < 0 || row - pickedIconHeight <0){
                    isOverlapping = true;
                    continue;
                }
                // Save it to location object
                Location cmonman = new Location(col, row, pickedIconWidth, pickedIconHeight, newimage);
                for (Location existingLocation : AllIconLocations) {
                    //Look all locations if random row and col conflict with any other icons
                    if(areAreasOverlapping(existingLocation, cmonman)){
                        isOverlapping = true;
                        break;
                    }
                }
                //If there is no overlap, add it to icon locations list
            }
            AllIconLocations.add(new Location(col, row, pickedIconWidth, pickedIconHeight, newimage));
            iconLocationsObstacle.add(new Location(col, row, pickedIconWidth, pickedIconHeight, newimage));
            iconLocationsstatic.add(new Location(col, row, pickedIconWidth, pickedIconHeight, newimage));

        }
        motionlessObject.setMotionlessLocations(iconLocationsstatic);
    }
    // Animation and overlap check
    private boolean areAreasOverlapping(Location location1, Location location2) {

        // Get the corner points of Location 1
        int x1 = location1.getX();
        int y1 = location1.getY();
        int w1 = location1.getWidth();
        int h1 = location1.getHeight();

        // Get the corner points of Location 2
        int x2 = location2.getX();
        int y2 = location2.getY();
        int w2 = location2.getWidth();
        int h2 = location2.getHeight();

        // Check if they are adjacent
        if (x1 + w1 < x2 || x2 + w2 < x1 || y1 + h1 < y2 || y2 + h2 < y1) {
            return false;
        }

        // Check the corner points of Location 1
        for (int i = 0; i < w1; i++) {
            for (int j = 0; j < h1; j++) {
                int px = x1 + i;
                int py = y1 + j;

                // If it is inside Location 2, there is an intersection
                if (px >= x2 && px < x2 + w2 && py >= y2 && py < y2 + h2) {
                    return true;
                }
            }
        }

        // Check the corner points of Location 2
        for (int i = 0; i < w2; i++) {
            for (int j = 0; j < h2; j++) {
                int px = x2 + i;
                int py = y2 + j;

                // If it is inside Location 1, there is an intersection
                if (px >= x1 && px < x1 + w1 && py >= y1 && py < y1 + h1) {
                    return true;
                }
            }
        }

        // If there is no intersection, return false
        return false;
    }
    public void Animationbee(){
        this.moveTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for (Location location : iconLocationsdynamic) {
                    if(location.DYNiconID.equals("bee")){
                        if(directionbee){
                            double x =location.dynamicX;
                            x = x + 0.03;
                            distancebee = distancebee + 0.03;
                            if(distancebee >= (double) 8){
                                directionbee = false;
                            }
                            location.setDynamicX(x);
                        }
                        if(!directionbee){
                            double x =location.dynamicX;
                            x = x - 0.03;
                            distancebee = distancebee - 0.03;
                            if(distancebee <= (double) 0){
                                directionbee = true;
                            }
                            location.setDynamicX(x);
                        }
                    }
                }
                repaint();
            }
        });
        moveTimer.start();
    }
    public void Animationbird(){
        this.moveTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for (Location location : iconLocationsdynamic) {
                    if(location.DYNiconID.equals("bird")){
                        if(directionbird){
                            double y =location.dynamicY;
                            y = y + 0.03;
                            distancebird = distancebird + 0.03;
                            if(distancebird >= (double) 8){
                                directionbird = false;
                            }
                            location.setDynamicY(y);
                        }
                        if(!directionbird){
                            double y =location.dynamicY;
                            y = y - 0.03;
                            distancebird = distancebird - 0.03;
                            if(distancebird <= (double) 0){
                                directionbird = true;
                            }
                            location.setDynamicY(y);
                        }
                    }
                }
                repaint();
            }
        });
        moveTimer.start();
    }
    public void MoveBitxh(){
        this.moveTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int x = (int) (character.getCurrX());
                int y = (int) (character.getCurrY());
                checkTreasure(x, y);
                character.getPath().add(new Point(x, y));
                move.browseAndRecordTreasure(x, y, 3, getIconLocationsTreasure());
                // Look for obstacles
                move.browseAndRecordObstacles(x, y, 3, getIconLocationsObstacle());

                int behavior = move.ChooseBehavior();
                // calculate X and Y as chosen behavior previous
                switch(behavior){
                    case 1:
                        goRandom(x,y);
                        break;
                    case 2:
                        int destX = (int) character.getDestination().x;
                        int destY = (int) character.getDestination().y;

                        if (x < destX && !isCollision(x + 1, y)) {
                            x++;
                        } else if (x > destX && !isCollision(x - 1, y)) {
                            x--;
                        } else if (y < destY && !isCollision(x, y + 1)) {
                            y++;
                        } else if (y > destY && !isCollision(x, y - 1)) {
                            y--;
                        }

                        character.setCurrX(x);
                        character.setCurrY(y);

                        if (x == destX && y == destY) {
                            character.setDestination(null);
                        }
                }
            }
        });
        moveTimer.start();
    }

    @Override
    //Start with Grid Constructor same time
    protected void paintComponent(Graphics g) {
        //this.g=g;
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        blockWidth = (double) width / columns;
        blockHeight = (double) height / rows;
        // Start to drawing grid
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                int x = (int) (column * blockWidth);
                int y = (int) (row * blockHeight);

                if (column < (columns / 2)) {
                    g.setColor(new Color(185, 232, 234));
                } else {
                    g.setColor(new Color(255, 223, 34));
                }
                g.fillRect(x, y, (int)blockWidth, (int)blockHeight);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, (int)blockWidth, (int)blockHeight);
            }
        }
        // Grid Completed

        //This section for draw icons
        for (Location location : AllIconLocations){
            if(location.pickedIcon==null){
                System.out.println("OMG there is no icon! DAD, HELP! \n");
            }
            int a = (int) (location.x*blockWidth);
            int b = (int) (location.y*blockHeight);

            //Graphics2D g2d = (Graphics2D) g.create();
            //g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawImage(location.pickedIcon, a, b, (int) (location.getWidth()*blockWidth), (int) (location.getHeight()*blockHeight), null);
            // And draw...
            //g2d.dispose();
            if(location.dynamicicon!=null){
                g.drawImage(location.dynamicicon, (int) (location.dynamicX*blockWidth), (int) (location.dynamicY*blockHeight), (int) (2*blockWidth), (int) (2*blockHeight), null);
            }
        }


        //Grid and motionless icons drawn.


        // Draw rectangles as smoke, where is 1 in matrix
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if(smoke.getSmokematrix()[i][j]==1){
                    g.fillRect((int) (i*blockWidth), (int) (j*blockHeight), (int) blockWidth, (int) blockHeight);
                    g.setColor(Color.GRAY);
                    g.drawRect((int) (i*blockWidth), (int) (j*blockHeight), (int) blockWidth, (int) blockHeight);
                }

            }
        }

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
                    g.drawImage(characterIcon, a, b, characterWidth, characterHeight, this);
                }

            }
        }
        // Remove smoke around character
        smoke.smokeAreaRemove(x, y,3);
        // add path to character memory;


        if (character.getPath().size() > 2) {
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
        if (!treasureMessage.isEmpty()) {
            g.setColor(Color.BLUE);
            g.setFont(messageFont);
            g.drawString(treasureMessage, getWidth() - 420, 20); // Mesajı sağ üst köşeye yazdır
        }

        repaint();
    }

    // Random image selection and resize section
    private BufferedImage getRandomIconDynamic2(int i){
        if(i==0){
            return resizeIcon(MotionObject.getBee(), (int) (2*blockWidth), (int)(2*blockHeight));
        }
        if(i==1){
            return resizeIcon(MotionObject.getBird(), (int) (2*blockWidth), (int)(2*blockHeight));
        }
        if(i==2){
            return resizeIcon(MotionObject.getBee(), (int) (2*blockWidth), (int)(2*blockHeight));
        }
        else{
            return null;
        }
    }

    private BufferedImage getRandomIconDynamic(int i){
        if(i==0){
            pickedDYNWidth = 6;
            pickedDYNHeight = 2;

            return MotionObject.getBeeareaicon();
        }
        if(i==1){
            pickedDYNWidth = 2;
            pickedDYNHeight = 10;
            return MotionObject.getBirdareaicon();
        }
        if(i==2){
            pickedDYNWidth = 6;
            pickedDYNHeight = 2;
            return MotionObject.getBeeareaicon();
        }
        else{
            return null;
        }
    }

    private BufferedImage getRandomIcon(int col, int i) {
        Random random = new Random();

        if(0 <= i && i<5){
            pickedIconWidth = 15;
            pickedIconHeight = 15;
            if (col > columns / 2.2) {
                return resizeIcon(motionlessObject.getMountainSummer(), 150, 150);
            } else {
                return resizeIcon(motionlessObject.getMountainWinter(), 150, 150);
            }
        }
        if(5<=i && i<10){
            int size = random.nextInt(4) + 2;
            pickedIconWidth = size;
            pickedIconHeight = size;
            if (col > columns / 2.2) {
                return resizeIcon(motionlessObject.getTreeSummer(), (int) (size * blockWidth), (int) (size * blockHeight));
            } else {
                return resizeIcon(motionlessObject.getTreeWinter(), (int) (size * blockWidth), (int) (size * blockHeight));
            }
        }
        if(10<=i && i<15){
            pickedIconWidth = 10;
            pickedIconHeight = 1;
            return resizeIcon(motionlessObject.getWallIcon(), (int) (10 * blockWidth), (int) (blockHeight));
        }
        if(15<=i && i<20){
            int size = random.nextInt(2) + 2;
            pickedIconWidth = size;
            pickedIconHeight = size;
            return resizeIcon(motionlessObject.getRockIcon(), (int) (size * blockWidth), (int) (size * blockHeight));
        }
        else{
            return null;
        }

    }
    private BufferedImage getRandomTreasure(int i) {
        if(0 <= i && i<5){
            return resizeIcon(treasures.getGold(), 1, 1);
        }
        if(5<=i && i<10){
            return resizeIcon(treasures.getSilver(), 1, 1);
        }
        if(10<=i && i<15){
            return resizeIcon(treasures.getCopper(), 1, 1);
        }
        if(15<=i && i<20){
            return resizeIcon(treasures.getEmerald(), 1, 1);
        }
        else{
            return null;
        }

    }

    private BufferedImage resizeIcon(BufferedImage icon, int width, int height) {
        // To resize images as wished, don't touch here pls.
        if (width > 0 && height > 0) {
            Image newImage = icon.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage buffered = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = buffered.createGraphics();
            g2d.drawImage(newImage, 0, 0, null);
            g2d.dispose();
            return buffered;
        } else {
            return icon;
        }
    }

    ///
    public boolean isCollision(int x, int y) {
        for (Location barrier : iconLocationsObstacle) {
            if (x >= barrier.getX() && x < barrier.getX() + barrier.getWidth() &&
                    y >= barrier.getY() && y < barrier.getY() + barrier.getHeight()) {
                // Collusion Detected
                return true;
            }
        }
        // Ain't no complaints
        return false;
    }

    public boolean isCollision2(int x, int y) {
        for(Location location: iconLocationsObstacle){
            for (int i = 0; i < location.areaX.length; i++) {
                for (int j = 0; j < location.areaY[i].length; j++) {
                    if(location.areaX[i][j]==x || location.areaY[i][j]==y){
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public List<Location> getIconLocationsTreasure() {
        return iconLocationsTreasure;
    }

    public List<Location> getIconLocationsObstacle() {
        return iconLocationsObstacle;
    }
    public void goRandom(int x, int y) {
        boolean moved = false;
        stepscount = 0;

        while (!moved) {
            int movement = random.nextInt(4);
            int newX = character.getCurrX();
            int newY = character.getCurrY();

            switch (movement) {
                case 0: // Yukarı
                    newY--;
                    break;
                case 1: // Aşağı
                    newY++;
                    break;
                case 2: // Sağa
                    newX++;
                    break;
                case 3: // Sola
                    newX--;
                    break;
            }

            // Yeni konum engellerle çakışmıyorsa, karakteri hareket ettir
            if (isValidMove(newX, newY) && !isCollision(newX, newY)) {
                if(!passedbefore(newX, newY)){
                    character.setCurrX(newX);
                    character.setCurrY(newY);
                    moved = true;
                    attemp=0;
                    stepscount++;
                }
                else{
                    attemp++;
                    if(attemp==10000){
                        attemp=0;
                        character.setCurrX(newX);
                        character.setCurrY(newY);
                        moved = true;
                        stepscount++;
                    }


                }
            }
        }
    }

    private boolean isValidMove(int x, int y) {
        return x >= 0 && x < columns && y >= 0 && y < rows;
    }

    private boolean passedbefore(int x, int y){
        if(character.getPath().size()>1){
            for (Point point : character.getPath()) {
                if (point.x == x && point.y == y) {
                    return true;
                }
            }
        }
        return false;

    }
    private void checkTreasure(int x, int y) {
        Location foundTreasure = null;
        for (Location treasureLocation : iconLocationsTreasure) {
            if (treasureLocation.getX() == x && treasureLocation.getY() == y) {
                treasureMessage = "Treasure found at X: " + x + ", Y: " + y + ". Type: " + treasureLocation.getTreasureType();
                foundTreasure = treasureLocation;
                break;
            }
        }
        findinalliconandremove(foundTreasure);

    }
    private void findinalliconandremove(Location foundTreasure) {
        if (foundTreasure != null) {
            for (int i = 0; i < AllIconLocations.size(); i++) {
                Location location = AllIconLocations.get(i);
                if (location.getX() == foundTreasure.getX() && location.getY() == foundTreasure.getY()) {
                    AllIconLocations.remove(i);
                    break;
                }
            }
        }
    }




}
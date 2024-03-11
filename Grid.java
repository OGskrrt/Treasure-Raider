import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Grid extends JPanel {
    private int rows;
    private int columns;
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
    public int pickedIconWidth = 15;
    public int pickedIconHeight = 15;

    public int pickedDYNWidth = 2;
    public int pickedDYNHeight = 2;
    private int[] iconCount = new int[5];
    private int mountainIconCount = 0;
    private int treeIconCount = 0;
    private int wallIconCount = 0;
    private int rockIconCount = 0;
    private Timer moveTimer;
    private Location cmonman;

    public Grid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.AllIconLocations = new ArrayList<>();
        this.iconLocationsstatic = new ArrayList<>();
        this.iconLocationsdynamic = new ArrayList<>();
        this.iconLocationsObstacle = new ArrayList<>();
        this.iconLocationsTreasure = new ArrayList<>();
        this.motionlessObject = new MotionlessObject(this, rows, columns);
        this.motionObject = new MotionObject();
        this.treasures = new Treasures(this, rows, columns);
    }

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
            System.out.println("ilk for içi\n" +i);
            boolean isOverlapping = true;
            while(isOverlapping){
                System.out.println("while giriş\n");
                isOverlapping = false;
                // Get random row and column
                col = random.nextInt(columns);
                // Get random icon;
                newimage = getRandomTreasure(i);
                row = random.nextInt(rows);
                // Save it to location object
                Location cmonman = new Location(col, row, 1, 1, newimage);
                for (Location existingLocation : AllIconLocations) {
                    System.out.println("ikinci for içi\n");
                    //Look all locations if random row and col conflict with any other icons
                    if(areAreasOverlapping(existingLocation, cmonman)){
                        System.out.println("overlap varrr\n");
                        isOverlapping = true;
                        break;
                    }
                }
                //If there is no overlap, add it to icon locations list
            }
            System.out.println("overlap giderildi, eklenme yapıldı \n");
            AllIconLocations.add(new Location(col, row, 1, 1, newimage));
            iconLocationsTreasure.add(new Location(col, row, 1, 1, newimage));

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
            System.out.println("ilk for içi\n" +i);
            boolean isOverlapping = true;
            while(isOverlapping){
                System.out.println("while giriş\n");
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
                    System.out.println("ikinci for içi\n");
                    //Look all locations if random row and col conflict with any other icons
                    if(areAreasOverlapping(existingLocation, cmonman)){
                        System.out.println("overlap varrr\n");
                        isOverlapping = true;
                        break;
                    }
                }

                //If there is no overlap, add it to icon locations list
            }
            System.out.println("overlap giderildi, eklenme yapıldı \n");
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
            System.out.println("ilk for içi\n" +i);
            boolean isOverlapping = true;
            while(isOverlapping){
                System.out.println("while giriş\n");
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
                    System.out.println("ikinci for içi\n");
                    //Look all locations if random row and col conflict with any other icons
                    if(areAreasOverlapping(existingLocation, cmonman)){
                        System.out.println("overlap varrr\n");
                        isOverlapping = true;
                        break;
                    }
                }
                //If there is no overlap, add it to icon locations list
            }
            System.out.println("overlap giderildi, eklenme yapıldı \n");
            AllIconLocations.add(new Location(col, row, pickedIconWidth, pickedIconHeight, newimage));
            iconLocationsObstacle.add(new Location(col, row, pickedIconWidth, pickedIconHeight, newimage));
            iconLocationsstatic.add(new Location(col, row, pickedIconWidth, pickedIconHeight, newimage));

        }
        motionlessObject.setMotionlessLocations(iconLocationsstatic);
    }

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

    @Override
    //Start with Grid Constructor same time
    protected void paintComponent(Graphics g) {
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

            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawImage(location.pickedIcon, a, b, (int) (location.getWidth()*blockWidth), (int) (location.getHeight()*blockHeight), null);
            // And draw...
            g2d.dispose();


        }
        moveTimer = new Timer(1000, new ActionListener() {
            int a, b;
            int x=0, y=0;
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Location location : iconLocationsdynamic){
                    x++;
                    y++;
                    a = (int) (location.x*blockWidth);
                    b = (int) (location.y*blockHeight);

                    g.drawImage(location.pickedIcon, a+x, b+y, (int) (location.getWidth()*blockWidth), (int) (location.getHeight()*blockHeight), null);
                }

            }
        });
        moveTimer.start();
        //Grid and motionless icons drawn.
    }
    private BufferedImage getRandomIconDynamic2(int i){
        if(i==0){
            return resizeIcon(MotionObject.getBee(),2,2);
        }
        if(i==1){
            return resizeIcon(MotionObject.getBird(),2,2);
        }
        if(i==2){
            return resizeIcon(MotionObject.getBee(),2, 2);
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


}
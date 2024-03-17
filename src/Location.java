package src;

import java.awt.image.BufferedImage;

public class Location {
    public int x;
    public int y;
    private int width;
    private int height;
    public int[][] areaX;
    public int[][] areaY;
    public BufferedImage pickedIcon;
    public BufferedImage dynamicicon;
    public double dynamicX;
    public double dynamicY;
    public int dynamicXWidth = 2;
    public int dynamicYHeigh = 2;
    public String DYNiconID;
    private TreasureType type;
    private boolean isThisTreasure = false;


    public Location(int x, int y, int width, int height, BufferedImage pickedIcon) {
        this.x = x;
        this.y = y;
        this.dynamicX = x;
        this.dynamicY = y;
        this.pickedIcon = pickedIcon;
        this.width = width;
        this.height = height;
        this.areaX = new int[width][height];
        this.areaY = new int[width][height];
        AreaCalculate();
    }
    public Location(int x,int y){
        this.x=x;
        this.y=y;
    }
    // For Treasures
    public enum TreasureType {
        EMERALD,
        COPPER,
        SILVER,
        GOLD;
    }

    public Location(int x, int y, int width, int height, BufferedImage pickedIcon, int ID) {
        this.isThisTreasure=true;
        this.x = x;
        this.y = y;
        this.pickedIcon = pickedIcon;
        this.width = width;
        this.height = height;
        this.areaX = new int[width][height];
        this.areaY = new int[width][height];
        AreaCalculate();


        if(0 <= ID && ID<5){
            this.type = TreasureType.EMERALD;
        }
        if(5<=ID && ID<10){
            this.type = TreasureType.GOLD;
        }
        if(10<=ID && ID<15){
            this.type = TreasureType.SILVER;
        }
        if(15<=ID && ID<20){
            this.type = TreasureType.COPPER;
        }


    }


    // To save all X and Y coordinate
    public void AreaCalculate(){
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                areaX[i][j]=x+i;
                areaY[i][j]=y+j;
            }
        }
    }

    public void setDynamicicon(BufferedImage dynamicicon, int id) {
        this.dynamicicon = dynamicicon;
        if(id%2==0){
            DYNiconID = "bee";
        }
        else{
            DYNiconID = "bird";
        }
    }

    public int getX() {
        return x;
    }


    public void setX(int x) {
        this.x = x;
    }


    public int getY() {
        return y;
    }


    public void setY(int y) {
        this.y = y;
    }


    public int getWidth() {
        return width;
    }


    public void setWidth(int width) {
        this.width = width;
    }


    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int[][] getAreaX() {
        return areaX;
    }
    public int[][] getAreaY() {
        return areaY;
    }

    public int getDynamicXWidth() {
        return dynamicXWidth;
    }

    public int getDynamicYHeigh() {
        return dynamicYHeigh;
    }

    public void setDynamicX(double dynamicX) {
        this.dynamicX = dynamicX;
    }

    public void setDynamicY(double dynamicY) {
        this.dynamicY = dynamicY;
    }

    public BufferedImage getIcon() {
        return pickedIcon;
    }

    public TreasureType getTreasureType() {
        return type;
    }
}

import java.awt.image.BufferedImage;

public class Location {
    public int x;
    public int y;
    private int width;
    private int height;
    public int[][] areaX;
    public int[][] areaY;
    public BufferedImage pickedIcon;


    public Location(int x, int y, int width, int height, BufferedImage pickedIcon) {
        this.x = x;
        this.y = y;
        this.pickedIcon = pickedIcon;
        this.width = width;
        this.height = height;
        this.areaX = new int[width][height];
        this.areaY = new int[width][height];
        AreaCalculate();
    }

    public void AreaCalculate(){
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                areaX[i][j] = x - (width / 2) + i;
                areaY[i][j] = y - (height / 2) + j;
            }
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
}

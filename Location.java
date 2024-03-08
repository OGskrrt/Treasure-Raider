//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

public class Location {
    private int x;
    private int y;
    private int width;
    private int height;
    public int[][] areaX;
    public int[][] areaY;

    public Location(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.areaX = new int[width][height];
        this.areaY = new int[width][height];
        this.AreaCalculate();
    }

    public void AreaCalculate() {
        for(int i = 0; i < this.width; ++i) {
            for(int j = 0; j < this.height; ++j) {
                this.areaX[i][j] = this.x - this.width / 2 + i;
                this.areaY[i][j] = this.y - this.height / 2 + j;
            }
        }

    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int[][] getAreaX() {
        return this.areaX;
    }

    public int[][] getAreaY() {
        return this.areaY;
    }
}

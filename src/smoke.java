import javax.swing.*;

public class smoke extends JPanel {
    double blockWidth;
    double blockHeight;
    private int[][] smokematrix;
    private int rows;
    private int columns;
    private int width;
    private int height;

    public smoke(int rows, int columns) {
        this.rows=rows;
        this.columns=columns;
        smokematrix = new int[rows][columns];
        // Initialize matrix as all 1
        initMatrix();
        width = getWidth();
        height = getHeight();
        blockWidth = (double) width / columns;
        blockHeight = (double) height / rows;
    }

    private void initMatrix(){
        for (int i = 0; i < smokematrix.length; i++) {
            for (int j = 0; j < smokematrix[i].length; j++) {
                smokematrix[i][j]=1;
            }
        }
    }
    public void smokeAreaRemove(int x, int y, int size){
        // Clear smoke as making 0 in matrix actual location
        // Please make Size odd

        // Check for boundaries
        int xsize = x+size;
        int xstart = x-size;
        if(x+size>rows){
            xsize=rows;
        }
        if(x-size<0){
            xstart = 0;
        }

        int ysize = y+size;
        int ystart = y-size;
        if(y+size>columns){
            ysize=columns;
        }
        if(y-size<0){
            ystart = 0;
        }
        //Remove smoke
        for (int i = xstart; i < xsize; i++) {
            for (int j = ystart; j < ysize; j++) {
                smokematrix[i][j] = 0;
            }
        }
    }

    public int[][] getSmokematrix() {
        return smokematrix;
    }

}

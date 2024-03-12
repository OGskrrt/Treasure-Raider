import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class CharacterMovement {
    private Timer moveTimer;
    private Random random = new Random();
    private Character character;
    private int gridRows;
    private int gridColumns;
    private Grid grid;

    public CharacterMovement(Character character, Grid grid, int gridRows, int gridColumns) {
        this.character = character;
        this.gridRows = gridRows;
        this.grid = grid;
        this.gridColumns = gridColumns;
        initMovement();
    }

    private void initMovement() {
        moveTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveCharacterRandomly();
                grid.repaint();
            }
        });
        moveTimer.start();
    }

    private void moveCharacterRandomly() {
        int movement = random.nextInt(3);
        Location currentLocation = character.getCurrentPosition();

        switch (movement) {
            case 0: // Up
                if (currentLocation.getY() > 0) {
                    currentLocation.setY(currentLocation.getY() - 1);
                }
                break;
            case 1: // Down
                if (currentLocation.getY() < gridRows - 1) {
                    currentLocation.setY(currentLocation.getY() + 1);
                }
                break;
            case 2: // Right
                if (currentLocation.getX() < gridColumns - 1) {
                    currentLocation.setX(currentLocation.getX() + 1);
                }
                break;
        }
        character.setCurrentPosition(currentLocation);
    }
}



package newEnemy;

import MainPackage.Enemy;
import MainPackage.IMage;
import MainPackage.Player;

import java.awt.*;
import java.util.Random;

public class NewEnemy extends Enemy {
    public NewEnemy(int width, Point upprleft, int distance, int moveInBlock, int moves, Player player) {
        super(width, upprleft, distance, moveInBlock, moves, player);
        this.speed = 1;


        enemyForward1 = new IMage("src/newEnemy/Picture1.png", 0);
        enemyForward2 = new IMage("src/newEnemy/Picture2.png", 0);
        enemyBackward1 = new IMage("src/newEnemy/Picture7.png", 0);
        enemyBackward2 = new IMage("src/newEnemy/Picture8.png", 0);
        enemyLeft1 = new IMage("src/newEnemy/Picture5.png", 0);
        enemyLeft2 = new IMage("src/newEnemy/Picture6.png", 0);
        enemyRight1 = new IMage("src/newEnemy/Picture3.png", 0);
        enemyRight2 = new IMage("src/newEnemy/Picture4.png", 0);

        this.stance = enemyForward1;
        classesNotAllowed.remove("MainPackage.Rock");




    }

    @Override
    public void moveChooser(long currentTime) {
        int r = -1;
        if (currentTime >= nextMovechooser) {
            Random random = new Random();
            r = random.nextInt(4);

        }
        enemyMove(r);
    }

    @Override
    public void render(Graphics2D G) {
        G.drawImage(stance.img, (int) this.getUpprleft().getX(), (int) this.getUpprleft().getY() - getBlockSize() / 10, getBlockSize(), getBlockSize() * 11 / 10, null);
    }


}
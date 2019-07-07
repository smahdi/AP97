package MainPackage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

;

public class Enemy1 extends Enemy {

    public Enemy1(int width, Point upprleft, int distance, int moveInBlock, int moves, Player player) {
        super(width, upprleft, distance, moveInBlock, moves, player);
        this.speed = 0.5;
        kind=1;

            enemyForward1 = new IMage("Images/Enemy1Forward1.png",1);
            enemyForward2 = new IMage("Images/Enemy1Forward2.png",2);
            enemyBackward1 = new IMage("Images/Enemy1Backward1.png",3);
            enemyBackward2 = new IMage("Images/Enemy1Backward2.png",4);
            enemyLeft1 = new IMage("Images/Enemy1Left1.png",5);
            enemyLeft2 = new IMage("Images/Enemy1Left2.png",6);
            enemyRight1 = new IMage("Images/Enemy1Right1.png",7);
            enemyRight2 = new IMage("Images/Enemy1Right2.png",8);



    }

    @Override
    public synchronized void moveChooser(long currentTime) {
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

package MainPackage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Enemy2 extends Enemy {
    public Enemy2(int width, Point upprleft, int distance, int moveInBlock, int moves, Player player) {
        super(width, upprleft, distance, moveInBlock, moves, player);
        this.speed = 0.5;
        kind=2;

            enemyForward1 = new IMage("Images/Enemi2Forward1.png",9);
            enemyForward2 = new IMage("Images/Enemi2Forward2.png",10);
            enemyBackward1 = new IMage("Images/Enemi2Backward1.png",11);
            enemyBackward2 = new IMage("Images/Enemi2Backward2.png",12);
            enemyLeft1 = new IMage("Images/Enemi2Left1.png",13);
            enemyLeft2 = new IMage("Images/Enemi2Left2.png",14);
            enemyRight1 = new IMage("Images/Enemi2Right1.png",15);
            enemyRight2 = new IMage("Images/Enemi2Right2.png",16);




    }

    @Override
    public void moveChooser(long currentTime) {
        if (currentTime >= nextMovechooser) {
            ArrayList<Integer> rs = new ArrayList<>();
            int r = -1;
            if (this.getBlockX() < player.getBlockX() ) {
                rs.add(0);

            }
            if (this.getBlockX() > player.getBlockX()) {
                rs.add(1);
            }
            if (this.getBlockY() > player.getBlockY()) {
                rs.add(2);
            }
            if (this.getBlockY() < player.getBlockY()) {
                rs.add(3);
            }
            Random random = new Random();
            if (!rs.isEmpty()) {
                int x = random.nextInt(rs.size());
                r = rs.get(x);
            }


            enemyMove(r);
        }

    }

    @Override
    public void render(Graphics2D G) {
        G.drawImage(stance.img, (int) this.getUpprleft().getX(), (int) this.getUpprleft().getY() - getBlockSize() / 10, getBlockSize(), getBlockSize() * 11 / 10, null);
    }
}
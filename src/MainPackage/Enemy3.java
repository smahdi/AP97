package MainPackage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Enemy3 extends Enemy {
    Enemy3(int width, Point upprleft, int distance, int moveInBlock, int moves,Player player) {
        super(width, upprleft, distance, moveInBlock, moves,player);
        this.speed=1;
        kind=3;

            enemyForward1= new IMage("Images/Enemi3Forward1.png",17);
            enemyForward2= new IMage("Images/Enemi3Forward2.png",18);
            enemyBackward1= new IMage("Images/Enemi3Backward1.png",19);
            enemyBackward2= new IMage("Images/Enemi3Backward2.png",20);
            enemyLeft1= new IMage("Images/Enemi3Left1.png",21);
            enemyLeft2= new IMage("Images/Enemi3Left2.png",22);
            enemyRight1= new IMage("Images/Enemi3Right1.png",23);
            enemyRight2= new IMage("Images/Enemi3Right2.png",24);


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
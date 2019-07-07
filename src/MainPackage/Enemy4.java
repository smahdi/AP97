package MainPackage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Enemy4 extends Enemy {
    Enemy4(int width, Point upprleft, int distance, int moveInBlock, int moves, Player player) {
        super(width, upprleft, distance, moveInBlock, moves, player);
        this.speed = 1;
        kind=4;

            enemyForward1 = new IMage("Images/Enemi4Forward1.png",25);
            enemyForward2 = new IMage("Images/Enemi4Forward2.png",26);
            enemyBackward1 = new IMage("Images/Enemi4Backward1.png",27);
            enemyBackward2 = new IMage("Images/Enemi4Backward2.png",28);
            enemyLeft1 = new IMage("Images/Enemi4Left1.png",29);
            enemyLeft2 = new IMage("Images/Enemi4Left2.png",30);
            enemyRight1 = new IMage("Images/Enemi4Right1.png",31);
            enemyRight2 = new IMage("Images/Enemi4Right2.png",32);


    }

    @Override
    public void moveChooser(long currentTime) {
        this.classesNotAllowed.clear();
        if (currentTime >= nextMovechooser) {
            int minNeiboorBlock = 1000000;
            int r = -1;
            if (Math.abs(this.getBlockX() - player.getBlockX() + moveInBlock * distance) + Math.abs(this.getBlockY() - player.getBlockY()) < minNeiboorBlock) {
                minNeiboorBlock =(int)( Math.abs(this.getBlockX() - player.getBlockX() + moveInBlock * distance) + Math.abs(this.getBlockY() - player.getBlockY()));
                r = 0;
            }
            if (Math.abs(this.getBlockX() - player.getBlockX() - moveInBlock * distance) + Math.abs(this.getBlockY() - player.getBlockY()) < minNeiboorBlock) {
                minNeiboorBlock =(int)(Math.abs(this.getBlockX() - player.getBlockX() - moveInBlock * distance) + Math.abs(this.getBlockY() - player.getBlockY()) );
                r = 1;
            }
            if (Math.abs(this.getBlockX() - player.getBlockX()) + Math.abs(this.getBlockY() - player.getBlockY() - moveInBlock * distance) < minNeiboorBlock) {
                minNeiboorBlock = (int)(Math.abs(this.getBlockX() - player.getBlockX()) + Math.abs(this.getBlockY() - player.getBlockY() - moveInBlock * distance));
                r = 2;
            }
            if (Math.abs(this.getBlockX() - player.getBlockX()) + Math.abs(this.getBlockY() - player.getBlockY() + moveInBlock * distance) < minNeiboorBlock) {
                minNeiboorBlock = (int)(Math.abs(this.getBlockX() - player.getBlockX()) + Math.abs(this.getBlockY() - player.getBlockY() + moveInBlock * distance));
                r = 3;
            }


            enemyMove(r);
        }

    }

    @Override
    public void render(Graphics2D G) {
        G.drawImage(stance.img, (int) this.getUpprleft().getX(), (int) this.getUpprleft().getY() - getBlockSize() / 10, getBlockSize(), getBlockSize() * 11 / 10, null);
    }
}
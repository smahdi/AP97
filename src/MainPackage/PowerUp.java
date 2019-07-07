package MainPackage;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PowerUp extends Shape {
    PoweUpKind poweUpKind;
    BufferedImage img;
    BombermanFrame bombermanFrame;

    PowerUp(int blockSize, Point upprleft, PoweUpKind poweUpKind, BufferedImage img, BombermanFrame bombermanFrame) {
        super(blockSize, upprleft);
        this.poweUpKind = poweUpKind;
        this.img = img;
        this.bombermanFrame = bombermanFrame;
    }

    @Override
    public void step() {

    }


    @Override
    public void render(Graphics2D G) {
        if(poweUpKind!=PoweUpKind.Nothing)
        G.drawImage(img, (int) this.getUpprleft().getX(), (int) this.getUpprleft().getY(), getBlockSize(), getBlockSize(), null);
    }

    void init(Player player) {
        synchronized (bombermanFrame.powerUps){bombermanFrame.powerUps.remove(this);}
        System.out.println(this.poweUpKind.toString());
        switch (poweUpKind) {
            case DecreaseBombs:
                if (player.bombCapacity > 1)
                    player.bombCapacity--;
                break;
            case DecreasePoint:
                if (player.points > 1)
                    player.points -= 100;
                break;
            case DecreaseRadius:
                if (player.bombRange > 1)
                    player.bombRange--;
                break;
            case DecreaseSpeed:
                if (player.speed > 1)
                    player.speed--;
                break;
            case IncreaseBombs:
                player.bombCapacity++;
                break;
            case IncreasePoint:
                player.points += 100;
                break;
            case IncreaseRadius:
                player.bombRange++;
                break;
            case Goastmode:
                player.classesNotAllowed.clear();
                player.classesNotAllowed.add("MainPackage.Wall2");
                player.ghostmode=true;
                break;
            case Bombcontrol:
                player.bombcontrol = true;
                break;
            case NextLevelPortal:
                BombermanFrame bombermanFrame1=new BombermanFrame(bombermanFrame.level+1,player);
                bombermanFrame.dispose();
                break;


        }
    }
}

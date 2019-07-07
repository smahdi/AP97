package MainPackage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;


public class Rock extends Shape {
    static BufferedImage img,pimg,dimg;
    static Random random = new Random();
    PoweUpKind poweUpKind ;
    Block block;
    BombermanFrame bombermanFrame;
    int randompowerup;

    Rock(int width, Point upprleft,Block block,BombermanFrame bombermanFrame) {
        super(width, upprleft);
        this.block=block;
        randompowerup=random.nextInt(12);
        this.bombermanFrame=bombermanFrame;
        switch ( randompowerup){
            case 0:
                poweUpKind=PoweUpKind.Nothing;
                break;
            case 1:
                poweUpKind=PoweUpKind.Nothing;
                break;
            case 2:
                poweUpKind=PoweUpKind.DecreaseBombs;
                break;
            case 3:
                poweUpKind=PoweUpKind.DecreasePoint;
                break;
            case 4:
                poweUpKind=PoweUpKind.DecreaseRadius;
                break;
            case 5:
                poweUpKind=PoweUpKind.DecreaseSpeed;
                break;
            case 6:
                poweUpKind=PoweUpKind.IncreaseBombs;
                break;
            case 7:
                poweUpKind=PoweUpKind.IncreasePoint;
                break;
            case 8:
                poweUpKind=PoweUpKind.IncreaseRadius;
                break;
            case 9:
                poweUpKind=PoweUpKind.IncreaseSpeed;
                break;
            case 10:
                poweUpKind=PoweUpKind.Goastmode;
                break;
            case 11:
                poweUpKind=PoweUpKind.Bombcontrol;



        }

    }



    @Override
    public void render(Graphics2D G) {
        G.drawImage(img, (int) this.getUpprleft().getX(), (int) this.getUpprleft().getY(), getBlockSize(), getBlockSize(), null);
    }

    @Override
    public Shape die() {
        //super.die();
        PowerUp powerUp;
        if (poweUpKind == PoweUpKind.NextLevelPortal) {
             powerUp= new PowerUp(this.getBlockSize(), this.getUpprleft(), this.poweUpKind, dimg,bombermanFrame);
            synchronized (bombermanFrame.powerUps){bombermanFrame.powerUps.add(powerUp);}
        }
        else{
            powerUp= new PowerUp(this.getBlockSize(), this.getUpprleft(), this.poweUpKind, pimg,bombermanFrame);
            synchronized (bombermanFrame.powerUps){bombermanFrame.powerUps.add(powerUp);}
        }
        block.paintPanel.addShape(powerUp);
        return powerUp;
    }

}

package MainPackage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by user on 18/04/11.
 */
public class Bomb extends Shape{

    int range;
    Date blowUpTime=new Date();
    Date beginTime;
    Player player;
    BufferedImage Img;



    Bomb(int width, Point position,int blockX,int blockY,long delayTime,int range,Player player) {
        super(width, position);
        this.setBlockXY(blockX,blockY);
        beginTime=new Date();
        this.blowUpTime.setTime(delayTime + beginTime.getTime());
        System.out.println(beginTime+" "+blowUpTime);
        this.range=range;
        this.player=player;
        try {
            Img=ImageIO.read(new File("Images/Bomb.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void step() {

    }

    @Override
    public void render(Graphics2D G) {
        G.drawImage(Img, (int) this.getUpprleft().getX(), (int) this.getUpprleft().getY() - getBlockSize() / 10, getBlockSize(), getBlockSize() * 11 / 10, null);
    }


    public MainPackage.Shape die() {
        Date currentDate = new Date();
        blowUpTime=currentDate;
        return  null;
    }
}

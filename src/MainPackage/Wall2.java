package MainPackage;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by user on 18/04/27.
 */
public class Wall2 extends Shape{

    static BufferedImage img;

    Wall2(int width, Point upprleft) {
        super(width, upprleft);


    }

    @Override
    public void step() {

    }


    @Override
    public void render(Graphics2D G) {
        G.drawImage(img, (int) this.getUpprleft().getX(), (int) this.getUpprleft().getY(), getBlockSize(), getBlockSize(), null);

    }
}

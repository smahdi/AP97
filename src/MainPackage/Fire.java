package MainPackage;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Fire extends Shape {
    BufferedImage img;
    Fire(int blockSize, Point upprleft,BufferedImage img) {
        super(blockSize, upprleft);
        this.img=img;

    }

    @Override
    public void render(Graphics2D G) {
        G.drawImage(img, (int) this.getUpprleft().getX(), (int) this.getUpprleft().getY(), getBlockSize(), getBlockSize(), null);

    }
}

package Client;

import java.awt.*;
import java.awt.image.BufferedImage;

class Sshape{
    BufferedImage img;
    int x,y;
    int a,b;

    public Sshape(BufferedImage img,int x,int y,int a,int b){
        this.img=img;
        this.x=x;
        this.y=y;
        this.a=a;
        this.b=b;
    }
    void render(Graphics2D G){
        G.drawImage(img, (int) x, (int)y, a, b, null);
    }
}
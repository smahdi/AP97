package MainPackage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class IMage {
    public BufferedImage img;
    public String address;
    public int imagecode=-1;
    IMage(String address){
        this.address=address;
        try {
            img=ImageIO.read(new File(address));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public IMage(String address, int imagecode){
        this.address=address;
        try {
            img=ImageIO.read(new File(address));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.imagecode=imagecode;
    }

}

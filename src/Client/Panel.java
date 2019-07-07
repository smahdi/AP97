package Client;

import MainPackage.Shape;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

public class Panel extends JPanel {

    public Panel(Dimension dimension) {
        this.setSize(dimension);
        this.setBackground(new Color(239,228,176));

    }
    ArrayList<Sshape> walls=new ArrayList<>();
    ArrayList<Sshape> sshapes=new ArrayList<>();
    @Override
    protected void paintComponent(Graphics G) {
        synchronized (this) {
            super.paintComponent(G);
            Iterator<Sshape> iterator = sshapes.iterator();
            for (Sshape sshape:walls){
                sshape.render((Graphics2D) G);
            }
            while (iterator.hasNext()) {
                Sshape cur = iterator.next();
                cur.render((Graphics2D) G);
            }

        }
    }
}

package MainPackage;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class PaintPanel extends JPanel {
    ArrayList<Shape> shapes = new ArrayList<>();
    private ArrayList<Shape> animatableShapes = new ArrayList<>();
    private ArrayList<Shape> toAddanimatableShapes = new ArrayList<>();
    private ArrayList<Enemy> enemys =new ArrayList<>();
    Thread animationThread;
    boolean animate=false;
    BombermanFrame bombermanFrame;

    public PaintPanel(Dimension dimension,BombermanFrame bombermanFrame) {
        this.setSize(dimension);
        this.setBackground(new Color(239,228,176));
        initialize();
        this.bombermanFrame=bombermanFrame;
    }

    void initialize() {
        setBorder(new BevelBorder(BevelBorder.LOWERED));
        this.setPreferredSize(new Dimension(2000, 2000));



        animationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (animatableShapes) {
                        Iterator<Shape> iterator = animatableShapes.iterator();
                        while (iterator.hasNext()) {
                            Shape cur = iterator.next();
                            cur.step();
                            if (cur.isDead) {
                                iterator.remove();
                                removeShape(cur);
                            }

                        }
                       synchronized (toAddanimatableShapes) {
                           Iterator<Shape> iterator1 = toAddanimatableShapes.iterator();
                           while (iterator1.hasNext()) {
                               Shape cur = iterator1.next();
                               animatableShapes.add(cur);
                               iterator1.remove();

                           }
                       }
                        try {
                            repaint();

                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        animationThread.start();

        Thread enemiAnimationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (enemys) {
                        Iterator<Enemy> iterator = enemys.iterator();
                        while (iterator.hasNext()) {
                            Enemy cur = iterator.next();
                            cur.step();
                            if (cur.isDead) {
                                iterator.remove();
                                removeShape(cur);
                            }
                        }

                        try {
                            repaint();

                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        enemiAnimationThread.start();
    }

    @Override
    protected void paintComponent(Graphics G) {
        synchronized (this) {
            super.paintComponent(G);
            Iterator<Shape> iterator = shapes.iterator();
            while (iterator.hasNext()) {
                Shape cur = iterator.next();
                cur.render((Graphics2D) G);
            }
        }
    }


    public synchronized void addShape(Shape shape) {

            shapes.add(shape);

    }
    public synchronized void removeShape(Shape shape){

            shapes.remove(shape);

    }
    public synchronized void addAnimatableShape(Shape shape) {
        synchronized (toAddanimatableShapes){
        toAddanimatableShapes.add(shape);}
            addShape(shape);


    }
    public synchronized void addEnemy(Enemy enemy) {
        synchronized (enemys){enemys.add(enemy);}
        addShape(enemy);

    }
    public synchronized void removeAnimatableShape(Shape shape){

            animatableShapes.remove(shape);
            removeShape(shape);

    }
    ArrayList getAnimatebleShapes(){
        return animatableShapes;
    }

}

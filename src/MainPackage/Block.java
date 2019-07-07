package MainPackage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class Block implements Serializable {
    ArrayList<Shape> shapesIn = new ArrayList<>();
    Block up, down, right, left;
    int x, y;
    PaintPanel paintPanel;
    Date boomDate = new Date();
    public long finalboomdate;
    Player boomPlayer;

    Block(PaintPanel paintPanel, int x, int y) {
        this.paintPanel = paintPanel;
        this.x = x;
        this.y = y;
    }

    Block() {
    }


    public void add(Shape a) {
        synchronized (shapesIn){   shapesIn.add(a);}
    }

    public void remove(Shape a) {
        synchronized (shapesIn){shapesIn.remove(a);}
    }

    public void clear() {
        Iterator<Shape> iterator = shapesIn.iterator();
        while (iterator.hasNext()) {
            paintPanel.removeShape(iterator.next());
        }
        shapesIn.clear();
    }

    public void clearNW() {
        Iterator<Shape> iterator = shapesIn.iterator();
        while (iterator.hasNext()) {
            Shape a = iterator.next();
            if (!(a instanceof Wall)) {
                a.isDead = true;
                paintPanel.removeShape(a);
                paintPanel.bombermanFrame.rocks.remove(a);
                iterator.remove();
            }

        }

    }


    public boolean containsWallRock() {
        Iterator<Shape> iterator = shapesIn.iterator();
        while (iterator.hasNext()) {
            Shape a = iterator.next();
            if (a instanceof Rock || a instanceof Wall)
                return true;
        }
        return false;
    }

    void boom() {
        synchronized (shapesIn) {

            Iterator<Shape> iterator1 = shapesIn.iterator();
            ArrayList<Shape> toaddShapes = new ArrayList<>();
            while (iterator1.hasNext()) {

                Shape currentShape = iterator1.next();
                if (!(currentShape instanceof PowerUp || currentShape instanceof Wall || currentShape instanceof Wall2))
                    System.out.println(currentShape.getClass());
                if (currentShape instanceof Rock) {
                    Rock rock = (Rock) currentShape;
                    toaddShapes.add(rock.die());
                    //paintPanel.removeAnimatableShape(currentShape);
                    rock.isDead = true;
                    boomPlayer.points += 50;
                    paintPanel.bombermanFrame.rocks.remove(rock);
                    iterator1.remove();

                }
                if (currentShape instanceof Bomb) {
                    Bomb bomb = (Bomb) currentShape;
                    bomb.die();
                    bomb.isDead = true;
                    //paintPanel.removeAnimatableShape(currentShape);
                    iterator1.remove();

                }
                if (currentShape instanceof Player) {
                    Player p = (Player) currentShape;
                    p.die();
                    p.isDead = true;
                    // paintPanel.removeAnimatableShape(currentShape);
                    iterator1.remove();
                }
                if (currentShape instanceof Enemy) {

                    Enemy p = (Enemy) currentShape;
                    p.die();
                    p.isDead = true;
                    // paintPanel.removeAnimatableShape(currentShape);
                    iterator1.remove();
                    boomPlayer.points += 75;


                }
            }
            for (Shape shape : toaddShapes)
                shapesIn.add(shape);


        }
    }
}
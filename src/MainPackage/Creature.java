package MainPackage;

import java.awt.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public abstract class Creature extends Shape {
    int health;
    protected double speed = 1;
    int distance = 2;
    int moveInBlock = 5;
    int movesPerPress = 10;
    int delay = 20;
    public ArrayList<Block> blocksIn = new ArrayList<>();
    public ArrayList<String> classesNotAllowed = new ArrayList<>();
    protected IMage stance;


    Creature(int width, Point upprleft, int distance, int moveInBlock, int movesPerPress) {
        super(width, upprleft);
        this.distance = distance;
        this.movesPerPress = movesPerPress;
        this.moveInBlock = moveInBlock;
        classesNotAllowed.add("MainPackage.Rock");
        classesNotAllowed.add("MainPackage.Wall");
        classesNotAllowed.add("MainPackage.Wall2");
        classesNotAllowed.add("MainPackage.Bomb");
    }

    boolean search(Block block) {
        boolean c = false;
        //System.out.println(block.x + " " + block.y);
        synchronized (block.shapesIn) {
            Iterator<Shape> iterator1 = block.shapesIn.iterator();
            while (iterator1.hasNext()) {

                Shape currentShape = iterator1.next();
                Iterator<String> iterator2 = classesNotAllowed.iterator();
                while (iterator2.hasNext()) {
                    String currentClass = iterator2.next();
                    if (currentShape.getClass().getName().equals(currentClass)) {
                        c = true;
                    }
                    //System.out.println(currentShape.getClass().getName() + " " + currentClass);
                }
            }
            return c;

        }
    }

    void moveRight(long d) {
        Creature p = this;
        boolean b = false;
        Iterator<Block> iterator = blocksIn.iterator();
        while (iterator.hasNext()) {
            Block currentBlock = iterator.next().right;
            b = b || !search(currentBlock);
        }


        if (p.canMoveHorizontal() && b ) {
            p.addAnimation(new Animation(delay, movesPerPress,d) {
                @Override
                public void step() {
                    p.setUpprleft(new Point((int) (p.getX() + speed * distance), (int) p.getY()));
                }
            });
            p.plusBlockXY((int) (speed * distance), 0);

            if (this.getBlockX() % (moveInBlock * distance) == 0) {
                Block block = new Block();
                Iterator<Block> iterator3 = blocksIn.iterator();
                while (iterator3.hasNext()) {
                    Block currentBlock = iterator3.next();
                    if (currentBlock.x == this.getBlockX() / (moveInBlock * distance) - 1) {
                        block = currentBlock;
                    }
                }
                block.remove(this);
                this.blocksIn.remove(block);
            }
            if (this.getBlockX() % (moveInBlock * distance) == distance) {
                Block block = blocksIn.get(0).right;
                block.add(this);
                this.blocksIn.add(block);
            }
        }
    }


    void moveLeft(long d) {
        Creature p = this;
        boolean b = false;
        Iterator<Block> iterator = blocksIn.iterator();
        while (iterator.hasNext()) {
            Block currentBlock = iterator.next().left;
            b = b || !search(currentBlock);
        }


        if (p.canMoveHorizontal() && b) {
            p.addAnimation(new Animation(delay, movesPerPress,d) {
                @Override
                public void step() {
                    p.setUpprleft(new Point((int) (p.getX() - speed * distance), (int) p.getY()));
                }
            });
            p.plusBlockXY(-(int) (speed * distance), 0);

            if (this.getBlockX() % (moveInBlock * distance) == 0) {
                Block block = new Block();
                Iterator<Block> iterator3 = blocksIn.iterator();
                while (iterator3.hasNext()) {
                    Block currentBlock = iterator3.next();
                    if (currentBlock.x == this.getBlockX() / (moveInBlock * distance) + 1) {
                        block = currentBlock;
                    }
                }
                block.remove(this);
                this.blocksIn.remove(block);
            }
            if (this.getBlockX() % (moveInBlock * distance) == (distance * moveInBlock - distance)) {
                Block block = blocksIn.get(0).left;
                block.add(this);
                this.blocksIn.add(block);
            }
        }


    }


    void moveUp(long d) {
        Creature p = this;
        boolean b = false;
        Iterator<Block> iterator = blocksIn.iterator();
        while (iterator.hasNext()) {
            Block currentBlock = iterator.next().up;
            b = b || !search(currentBlock);
        }


        if (p.canMoveVertical() && b) {
            p.addAnimation(new Animation(delay, movesPerPress,d) {
                @Override
                public void step() {
                    p.setUpprleft(new Point((int) p.getX(), (int) (p.getY() - speed * distance)));
                }
            });
            p.plusBlockXY(0, -(int) (speed * distance));

            if (this.getBlockY() % (moveInBlock * distance) == 0) {
                Block block = new Block();
                Iterator<Block> iterator3 = blocksIn.iterator();
                while (iterator3.hasNext()) {
                    Block currentBlock = iterator3.next();
                    if (currentBlock.y == this.getBlockY() / (moveInBlock * distance) + 1) {
                        block = currentBlock;
                    }
                }
                block.remove(this);
                this.blocksIn.remove(block);
            }
            if (this.getBlockY() % (moveInBlock * distance) == (distance * moveInBlock - distance)) {
                Block block = blocksIn.get(0).up;
                block.add(this);
                this.blocksIn.add(block);
            }
        }




    }


    void moveDown(long d) {
        Creature p = this;
        boolean b = false;
        Iterator<Block> iterator = blocksIn.iterator();
        while (iterator.hasNext()) {
            Block currentBlock = iterator.next().down;
            b = b || !search(currentBlock);
        }


        if (p.canMoveVertical() && b) {
            p.addAnimation(new Animation(delay, movesPerPress,d) {
                @Override
                public void step() {
                    p.setUpprleft(new Point((int) p.getX(), (int) (p.getY() + speed * distance)));
                }
            });
            p.plusBlockXY(0, (int) (speed * distance));

            if (this.getBlockY() % (moveInBlock * distance) == 0) {
                Block block = new Block();
                Iterator<Block> iterator3 = blocksIn.iterator();
                while (iterator3.hasNext()) {
                    Block currentBlock = iterator3.next();
                    if (currentBlock.y == this.getBlockY() / (moveInBlock * distance) - 1) {
                        block = currentBlock;
                    }
                }
                block.remove(this);
                this.blocksIn.remove(block);
            }
            if (this.getBlockY() % (moveInBlock * distance) == distance) {
                Block block = blocksIn.get(0).down;
                block.add(this);
                this.blocksIn.add(block);
            }
        }

    }



    boolean canMoveVertical() {
        if (this.getBlockX() % (moveInBlock * distance) == 0) {
            return true;
        }
        return false;
    }

    boolean canMoveHorizontal() {
        if (this.getBlockY() % (moveInBlock * distance) == 0) {
            return true;
        }
        return false;
    }
    void animatedmove(IMage img1,IMage img2){
        Date date = new Date();

        this.addAnimation(new Animation(0, 1, date.getTime()) {
            @Override
            public void step() {

                stance = img1;
            }
        });

        date.setTime(date.getTime() + 1);
        this.addAnimation(new Animation(0, 1, date.getTime()) {
            @Override
            public void step() {

                stance = img2;
            }
        });
        date.setTime(date.getTime() +  2*delay * movesPerPress -1);

        this.addAnimation(new Animation(0, 1, date.getTime()) {
            @Override
            public void step() {

                stance = img1;
            }
        });
    }


}

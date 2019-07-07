package MainPackage;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class Shape implements Drawable,Animatable,Serializable {

    private Point upprleft= new Point();
    private int blockSize;
    protected ArrayList<Animation> animations=new ArrayList<>();
    private int blockX;
    private int blockY;
    boolean isDead=false;





    Shape(int blockSize,Point upprleft){
        this.blockSize=blockSize;
        this.upprleft=upprleft;
    }


    synchronized public void addAnimation(Animation animation) {
        animations.add(animation);
    }
    public void removeAnimation(Animation animation){
        animations.remove(animation);
    }
    @Override
    synchronized public void step() {
        animations.removeIf(anm -> !anm.animate());





    }

    public Shape die(){
        //System.out.println("NNN");
        isDead=true;
        return null;
    }






































    public int getBlockY() {
        return blockY;
    }

    public int getBlockX() {
        return blockX;
    }

    public void setBlockXY(int blockX,int blockY) {
        this.blockY = blockY;
        this.blockX = blockX;
    }
    public void plusBlockXY(int X,int Y) {
        this.blockY += Y;
        this.blockX += X;
        //System.out.println(getBlockX()+"**"+getBlockY());
    }


    public Point getUpprleft() { return upprleft; }
    public void setUpprleft(Point upprleft) { this.upprleft = upprleft; }
    public double getX() {
        return upprleft.x;
    }
    public double getY() { return upprleft.y; }


    public int getBlockSize() { return blockSize; }
    public void setBlockSize(int blockSize) { this.blockSize = blockSize; }
}

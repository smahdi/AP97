package MainPackage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by user on 18/04/11.
 */
public class Player extends Creature {

    public boolean isoddturn =false;
    public int bombCapacity = 1;
    public int bombRange = 1;
    public int placedBombs=0;
    public boolean bombcontrol=false;
    public boolean ghostmode=false;
    public int bombdelayTime=5000;
    public int points=0;
    public PaintPanel paintPanel;
    public ArrayList<Bomb> bombs=new ArrayList<>();

    IMage BombermanForward1;
    IMage BombermanForward2;
    IMage BombermanForward3;
    IMage BombermanBackward1;
    IMage BombermanBackward2;
    IMage BombermanBackward3;
    IMage BombermanLeft1;
    IMage BombermanLeft2;
    IMage BombermanLeft3;
    IMage BombermanRight1;
    IMage BombermanRight2;
    IMage BombermanRight3;







    public  Player(int width, Point upprleft, int distance, int moveInBlock, int moves,PaintPanel paintPanel) {
        super(width, upprleft, distance, moveInBlock, moves);
        this.paintPanel=paintPanel;
        this.speed=1;

            BombermanForward1=new IMage("Images/BombermanForward1.png",33);
            BombermanForward2=new IMage("Images/BombermanForward2.png",34);
            BombermanForward3=new IMage("Images/BombermanForward3.png",35);
            BombermanBackward1=new IMage("Images/BombermanBackward1.png",36);
            BombermanBackward2=new IMage("Images/BombermanBackward2.png",37);
            BombermanBackward3=new IMage("Images/BombermanBackward3.png",38);
            BombermanRight1=new IMage("Images/BombermanRight1.png",39);
            BombermanRight2=new IMage("Images/BombermanRight2.png",40);
            BombermanRight3=new IMage("Images/BombermanRight3.png",41);
            BombermanLeft1=new IMage("Images/BombermanLeft1.png",42);
            BombermanLeft2=new IMage("Images/BombermanLeft2.png",43);
            BombermanLeft3=new IMage("Images/BombermanLeft3.png",44);
            stance=BombermanForward1;





        
    }

    @Override
    public void render(Graphics2D G) {

        G.drawImage(stance.img, (int) this.getUpprleft().getX(), (int) this.getUpprleft().getY() - getBlockSize() / 10, getBlockSize(), getBlockSize() * 11 / 10, null);
    }









    @Override
    public void moveRight(long d) {
        super.moveRight(d);
        playerAnimatableMove(BombermanRight1, BombermanRight2, BombermanRight3);
        powerUpChecker();

    }
    @Override
    public void moveLeft(long d) {
        super.moveLeft(d);
        playerAnimatableMove(BombermanLeft1, BombermanLeft2, BombermanLeft3);
        powerUpChecker();

    }

    private void playerAnimatableMove(IMage bombermanLeft1, IMage bombermanLeft2, IMage bombermanLeft3) {
        if(isoddturn) {
            isoddturn=!isoddturn;
            this.animatedmove(bombermanLeft1, bombermanLeft2);
        }
        else{
            isoddturn=!isoddturn;
            this.animatedmove(bombermanLeft1, bombermanLeft3);
        }
    }

    @Override
    public void moveDown(long d) {
        super.moveDown(d);
        playerAnimatableMove(BombermanForward1, BombermanForward2, BombermanForward3);
        powerUpChecker();

    }
    @Override
    public void moveUp(long d) {
        super.moveUp(d);
        playerAnimatableMove(BombermanBackward1, BombermanBackward2, BombermanBackward3);
        powerUpChecker();

    }


    public Bomb bombSet(){
        placedBombs++;
        Bomb bomb=new Bomb(getBlockSize(),getUpprleft(),getBlockX(),getBlockY(),bombdelayTime,bombRange,this);
        bombs.add(bomb);
        return bomb;
    }

    void powerUpChecker(){
        synchronized (blocksIn) {
            for (Block block : blocksIn) {
                synchronized (block.shapesIn) {
                    for (Shape shape : block.shapesIn)
                        if (shape instanceof PowerUp) {
                            ((PowerUp) shape).init(this);
                            block.remove(shape);
                            paintPanel.removeShape(shape);
                        }
                }
            }
        }
    }

    @Override
    public Shape die() {
        super.die();
        bombCapacity=0;
        //for(MainPackage.Block block:blocksIn)
         //   block.remove(this);
        //this.isDead=true;
        return  null;
        //this.setBlockXY(0,0);

        //return this;
    }
}

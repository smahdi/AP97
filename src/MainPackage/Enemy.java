package MainPackage;

import java.awt.*;
import java.util.Date;
import java.util.Iterator;

public abstract class Enemy extends Creature {



    protected IMage enemyForward1;
    protected IMage enemyForward2;
    protected IMage enemyBackward1;
    protected IMage enemyBackward2;
    protected IMage enemyLeft1;
    protected IMage enemyLeft2;
    protected IMage enemyRight1;
    protected IMage enemyRight2;
    protected Player player;
    protected long nextMovechooser=0;
    protected int kind;

    protected Enemy(int width, Point upprleft, int distance, int moveInBlock, int moves, Player player) {
        super(width, upprleft, distance, moveInBlock, moves);
        this.player=player;
    }
    public abstract void moveChooser(long currentTime);
    @Override
    void moveRight(long d) {
        super.moveRight(d);
        animatedmove(enemyRight1, enemyRight2);

    }
    @Override
    void moveLeft(long d) {
        super.moveLeft(d);
        animatedmove(enemyLeft1, enemyLeft2);

    }
    

    @Override
    void moveDown(long d) {
        super.moveDown(d);
        animatedmove(enemyForward1, enemyForward2);

    }
    @Override
    void moveUp(long d) {
        super.moveUp(d);
        animatedmove(enemyBackward1, enemyBackward2);

    }
    protected void enemyMove(int r){
        boolean b = false;
        Iterator<Block> iterator = blocksIn.iterator();
        switch (r) {
            case 0://right
                while (iterator.hasNext()) {
                    Block currentBlock = iterator.next().right;
                    b = b || !search(currentBlock);
                }
                if (b)
                    nextMovechooser = (new Date()).getTime() + (int) (moveInBlock / speed) * delay * movesPerPress;
                for (int i = 0; i < (int) (moveInBlock / speed); i++)
                    this.moveRight((new Date()).getTime() + i * delay * movesPerPress);
                break;

            case 1://left
                while (iterator.hasNext()) {
                    Block currentBlock = iterator.next().left;
                    b = b || !search(currentBlock);
                }
                if (b)
                    nextMovechooser = (new Date()).getTime() + (int) (moveInBlock / speed) * delay * movesPerPress;
                for (int i = 0; i < (int) (moveInBlock / speed); i++)
                    this.moveLeft((new Date()).getTime() + i * delay * movesPerPress);
                break;

            case 2://up
                while (iterator.hasNext()) {
                    Block currentBlock = iterator.next().up;
                    b = b || !search(currentBlock);
                }
                if (b)
                    nextMovechooser = (new Date()).getTime() + (int) (moveInBlock / speed) * delay * movesPerPress;
                for (int i = 0; i < (int) (moveInBlock / speed); i++)
                    this.moveUp((new Date()).getTime() + i * delay * movesPerPress);
                break;
            case 3://down
                while (iterator.hasNext()) {
                    Block currentBlock = iterator.next().down;
                    b = b || !search(currentBlock);
                }
                if (b)
                    nextMovechooser = (new Date()).getTime() + (int) (moveInBlock / speed) * delay * movesPerPress;
                for (int i = 0; i < (int) (moveInBlock / speed); i++)
                    this.moveDown((new Date()).getTime() + i * delay * movesPerPress);
                break;

        }

    }


}

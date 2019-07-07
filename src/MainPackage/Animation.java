package MainPackage;

import java.util.Date;

public abstract class Animation implements Animatable {
    long initializeDate ;
    Date nextAnimation ;
    int stepDelay;
    int moves ;
    //public static final long serialVersionUID=10L;



    public int getStepDelay() {
        return stepDelay;
    }

    public void setStepDelay(int stepDelay) {
        this.stepDelay = stepDelay;
    }




    public Animation(int stepDelay,int moves,long d) {
        this.stepDelay = stepDelay;
        nextAnimation = new Date();
        this.moves=moves;
        this.initializeDate=d;

    }

    public synchronized boolean animate() {
        if (moves==0)
            return false;
        long currentDate=(new Date()).getTime();

        if (this.initializeDate<=currentDate) {

            if (nextAnimation.before(new Date())) {
                step();
                nextAnimation.setTime(new Date().getTime() + stepDelay);
                moves--;
            }
        }

        return true;
    }
}

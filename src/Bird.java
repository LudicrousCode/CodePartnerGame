import java.awt.*;

/**
 * Created by rachel_chau on 3/23/17.
 */
public class Bird extends Sprite {

    private int direction;

    public Bird(int x, int y, int dir, int type){
        super(x, y, NORTH);
        if(type == 1) {
            if (dir == 0) { //going to the right
                setPic("FireBatRight.png", EAST);
                setDir(EAST);

            } else {//going to the left
                setPic("FireBatLeft.png", WEST);
                setDir(WEST);
            }
        }
        if(type == 2) {
            if (dir == 0) { //going to the right
                setPic("CaveBatRight.png", EAST);
                setDir(EAST);

            } else {//going to the left
                setPic("CaveBatLeft.png", WEST);
                setDir(WEST);
            }
        }
        if(type == 3) {
            if (dir == 0) { //going to the right
                setPic("BeeSpriteRight.png", EAST);
                setDir(EAST);

            } else {//going to the left
                setPic("BeeSpriteLeft.png", WEST);
                setDir(WEST);
            }
        }
        if(type == 4) {
            if (dir == 0) { //going to the right
                setPic("birdright.png", EAST);
                setDir(EAST);

            } else {//going to the left
                setPic("birdleft.png", WEST);
                setDir(WEST);
            }
        }

        direction = dir;
        setSpeed(5);
    }

    @Override
    public void update(){
        super.update();

        if(direction == 0) {
            if (getLoc().x >= Main.FRAMEWIDTH + getBoundingRectangle().width) {
                setLoc(new Point(-1 * getBoundingRectangle().width, getLoc().y));
            }
        }

        if(direction == 1) {
            if (getLoc().x <= -1 * getBoundingRectangle().width) {
                setLoc(new Point(Main.FRAMEWIDTH + getBoundingRectangle().width, getLoc().y));
            }
        }


    }



}

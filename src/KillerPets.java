import java.awt.*;

/**
 * Created by rachel_chau on 3/31/17.
 */
public class KillerPets extends Sprite {

    int rand = (int)(Math.random()*6) + 2;

    public KillerPets(int x, int y) {
        super(x, y, NORTH);
        int r = (int) (Math.random() * 4);
        if (r == 0) {
            setPic("cat1.png", NORTH);
        } else if (r == 1) {
            setPic("cat2.png", NORTH);
        } else if (r == 2) {
            setPic("dog1.png", NORTH);
        } else if(r == 3){
            setPic("dog2.png", NORTH);
        }
    }

    @Override
    public void update(){
        super.update();

        setLoc(new Point(getLoc().x, getLoc().y + rand));
        rotateBy(rand);

    }
}

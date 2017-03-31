import java.awt.*;

/**
 * Created by rachel_chau on 3/31/17.
 */
public class KillerPets extends Sprite {

    int rand = (int)(Math.random()*8) + 2;

    public KillerPets(int x, int y){
        super(x, y, NORTH);
        int r = (int)(Math.random()*2);
        if(r == 0){
            setPic("cat1.png", NORTH);
        } else if(r == 1){
            setPic("dog.png", NORTH);
        }
    }

    @Override
    public void update(){
        super.update();

        setLoc(new Point(getLoc().x, getLoc().y + rand));
        rotateBy(rand);

    }
}

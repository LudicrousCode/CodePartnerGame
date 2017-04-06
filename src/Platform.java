import java.awt.*;

/**
 * Created by rachel_chau on 3/16/17.
 */
public class Platform extends Sprite{
    //need to have a picture for the platforms, not a rectangle that is drawn

    public Platform(int x, int y, int type){

        super(x, y, NORTH);
        if(type == 1)
            this.setPic("LavaPlatform.png", NORTH);
        if(type == 2)
            this.setPic("StonePlatform.png", NORTH);
        if(type == 3)
            this.setPic("DirtPlatform.png", NORTH);
        if(type == 4)
            this.setPic("CloudPlatform.png", NORTH);
        if(type == 5)
            this.setPic("EggPlatform.png", NORTH);

        this.setSpeed(0);
//        g2.fillRect(x, y, w, h);

    }
//    public void draw(Graphics2D g2){
//        g2.drawRect();
//    }
//    public void update(){
//
//    }





}

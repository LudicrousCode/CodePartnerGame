/**
 * Created by rachel_chau on 3/15/17.
 */
public class Player extends Sprite{

    private boolean isJumping = false;
    private boolean onPlatform = false;



    public Player(){

        super(Main.FRAMEWIDTH / 2 - 25, Main.FRAMEHEIGHT * 8 / 9, NORTH);
        setPic("player.png", NORTH);

    }

    @Override
    public void update(){
        if(isJumping){
            setSpeed(40);
            isJumping = false;
        }
        super.update();
    }

    public void jump(){
        isJumping = true;

    }

    public void setOnPlatform(boolean onPlatform) {
        this.onPlatform = onPlatform;
    }

    public boolean isOnPlatform() {
        return onPlatform;
    }


}

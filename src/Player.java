/**
 * Created by rachel_chau on 3/15/17.
 */
public class Player extends Sprite{

    private boolean isJumping = false;
    private boolean onPlatform = false;

    public int getSide() {
        return side;
    }

    public void setSide(int side) {
        this.side = side;
    }

    private int side;

    public Player(){

        super(Main.FRAMEWIDTH / 2 - 25, Main.FRAMEHEIGHT * 8 / 9, NORTH);
        setPic("player.png", NORTH);

    }

    public void update(){
        if(isJumping){
            isJumping = false;
        }
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

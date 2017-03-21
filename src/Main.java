import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Created by andrew_briasco on 3/15/17.
 */

/**
 * to do: shift the screen based on the position of the player, (use shift commands ->
 * when player is above or below certain y values)
 * Create platforms on screen and above screen (randomly?)
 * balance gravity and screen shifting
 * add different sized platforms? different types?
 * add other things into game: flying obstacles/creatures, background, music?, sounds?, powerups?, lives?, win condition,
 * add even more things into game: starting screen?, levels?, difficulties?, different players?, different types of levels?
 * ???
 * profit.
 */

public class Main extends JPanel {
    public static final int FRAMEWIDTH = 500, FRAMEHEIGHT = 700;
    private Timer timer;
    private int gravity;
    private boolean[] keys;
    private boolean gameOver;

    private ArrayList<Platform> platform;

    Player player = new Player();


    public Main() {
        gravity = 2;
        keys = new boolean[512];
        platform = new ArrayList<Platform>();
        gameOver = false;

        for(int i = 0; i < 8; i++){
            platform.add(new Platform(i * 70, 660));
        }

        platform.add(new Platform(200, 540));
        platform.add(new Platform(300, 510));

//        home = new Point(FRAMEWIDTH / 2 - 25, FRAMEHEIGHT * 8 / 9 - 10);




        timer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                player.setOnPlatform(false);
                for(Platform a: platform){
                    if(player.intersects(a) && player.getSpeed()< 1 && player.getLoc().y+39 < a.getLoc().y+20) {
                        player.setOnPlatform(true);
//                        player.setLoc(new Point(player.getLoc().x, a.getLoc().y-38));
                    }
                }

                if(!player.isOnPlatform()&& gravity <0) {
                    if (player.getSpeed() > -5) {
                        gravity = 2;
                        player.setSpeed(player.getSpeed() - 1);
                    }
                }
                else if (player.isOnPlatform())
                    player.setSpeed(0);
                else {
                    gravity --;
                }
                //bug testing for gravity
//                System.out.println(player.getSpeed());

                if(keys[KeyEvent.VK_W]){
//                    System.out.println("w");
                    if(player.isOnPlatform()) {
                        player.jump();
                        player.setDir(Sprite.NORTH);
                        player.update();
                        keys[KeyEvent.VK_W] = false; //probably.
                        player.setOnPlatform(false);
                    }
                }
                if(keys[KeyEvent.VK_D]){
//                    System.out.println("d");
                    player.setLoc(new Point(player.getLoc().x+5, player.getLoc().y));
                    player.update();
                }
                else if(keys[KeyEvent.VK_A]){
//                    System.out.println("a");
                    player.setLoc(new Point(player.getLoc().x-5, player.getLoc().y));
                    player.update();
                }


                //bounds for game
                if(player.getLoc().x < -40)
                    player.setLoc(new Point(500, player.getLoc().y));
                if(player.getLoc().x>500)
                    player.setLoc(new Point(-40, player.getLoc().y));

                //if player falls out of screen
                if(player.getLoc().y>700) {
                    timer.stop();
                    gameOver = true;
                }
//                    player.setLoc(home);
                //move screen based on player position
                if(player.getLoc().y<400&&player.getSpeed()>0)
                    shift(player.getSpeed());
                if(player.getLoc().y>600 && player.getSpeed()<0)
                    shift(player.getSpeed());



                for(Platform s: platform)
                    s.update();

                player.update();

                repaint();
            }
        });
        timer.start();
        //outside timer but still in constructor
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                keys[keyEvent.getKeyCode()] = true;
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                keys[keyEvent.getKeyCode()] = false;
            }
        });
    }

    public void shift(int num){
        for(Platform a: platform) {
            a.setLoc(new Point(a.getLoc().x, a.getLoc().y + num));
//            player.setLoc(new Point(player.getLoc().x, player.getLoc().y + num));
        }
    }
//    public void shiftDown(int num){
//        for(Platform a: platform) {
//            a.setLoc(new Point(a.getLoc().x, a.getLoc().y - num));
////            player.setLoc(new Point(player.getLoc().x, player.getLoc().y - num));
//        }
//    }




    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        Color sky = new Color(107, 163, 255);
        g2.setColor(sky);
        g2.fillRect(0, 0, FRAMEWIDTH, FRAMEHEIGHT);

//        g2.drawRect(FRAMEWIDTH-4, FRAMEHEIGHT-4, 2, 1);
        for (Platform a : platform) {
            a.draw(g2);
        }
        player.draw(g2);

        if(gameOver){
            Color color = new Color(0, 0, 0, 210);
            g2.setColor(color);
            g2.fillRect(0, 0, FRAMEWIDTH, getHeight());
            g2.setColor(Color.WHITE);
            g2.drawString("Game Over", FRAMEWIDTH / 2 - 50, FRAMEHEIGHT / 2);
        }


    }



    public static void main(String[] args) {
        JFrame window = new JFrame("Jumper");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0, 0, FRAMEWIDTH, FRAMEHEIGHT + 22); //(x, y, w, h) 22 due to title bar.

        Main panel = new Main();
        panel.setSize(FRAMEWIDTH, FRAMEHEIGHT);

        panel.setFocusable(true);
        panel.grabFocus();
        window.setLocationRelativeTo(null);
        window.add(panel);
        window.setVisible(true);
        window.setResizable(false);

    }
}


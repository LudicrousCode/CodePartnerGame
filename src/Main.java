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
    private int gravity, spawn, not, level, time;
    private boolean[] keys;
    private boolean gameOver;


    private ArrayList<Platform> platform;
    private ArrayList<Bird> bird;

    Player player = new Player();


    public Main() {
        gravity = 2;
        not = 0;
        keys = new boolean[512];
        platform = new ArrayList<Platform>();
        bird = new ArrayList<Bird>();
        gameOver = false;
        spawn = 0;
        level = 0;
        time = 0;

        //ground thingy
        for(int i = 0; i < 8; i++){
            platform.add(new Platform(i * 70, 660));
        }

        //beginning set platforms
        platform.add(new Platform(400, 550));
        platform.add(new Platform(220, 500));
        platform.add(new Platform(300, 410));
        platform.add(new Platform(100, 300));
        platform.add(new Platform(150, 250));
        platform.add(new Platform(300, 150));
        platform.add(new Platform(200, 20));
        platform.add(new Platform(100, -20));
        platform.add(new Platform(50, 550));


        //birdz
        bird.add(new Bird(100, 100, 0));
        bird.add(new Bird(200, 300, 0));
        bird.add(new Bird(300, 200, 1));


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
                for(Bird b: bird){
                    if(player.intersects(b))
                        player.jump();
                }
                //test

                if(!player.isOnPlatform()&& gravity <0) {
                    if (player.getSpeed() > -5) {
                        //changed gravity from 2 to 1
                        gravity = 1;
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
                        keys[KeyEvent.VK_W] = false; //probably.
                        player.setOnPlatform(false);
                    }
                }
                if(keys[KeyEvent.VK_D]){
//                    System.out.println("d");
                    player.setLoc(new Point(player.getLoc().x+5, player.getLoc().y));
                }
                else if(keys[KeyEvent.VK_A]){
//                    System.out.println("a");
                    player.setLoc(new Point(player.getLoc().x-5, player.getLoc().y));
                }
                //spawn clouds off the screen
                if(spawn == 5) {
                    spawn = 0;
                    int rand = (int) (Math.random() * 6);
                    if(rand == 0) {
                        platform.add(new Platform((int) (Math.random() * 430), -50));
                        not = 0;
                    }
                    else if(rand == 1)
                        bird.add(new Bird((int) (Math.random() * 430), -50, (int) (Math.random()*2)));
                    else
                        not ++;
                }
                if(not == 4){
                    not= 0;
                    platform.add(new Platform((int)(Math.random()*430), -50));
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
//                if(player.getLoc().y<400&&player.getSpeed()>0)
//                    shift(player.getSpeed());
                if(player.getLoc().y>600 && player.getSpeed()<0)
                    shift(player.getSpeed());
                //alternate method of shifting screen still testing
                if(player.getLoc().y<350&&player.isOnPlatform()) {
                    testShift(4);
                }



                for(int i = 0; i < platform.size(); i++) {
                    if (platform.get(i).getLoc().y > 750) {
                        platform.remove(i);
                        i--;
                    }
                }

                for(Platform s: platform) {
                    s.update();
                }

                for(Bird b: bird) {
                    b.update();
                }

                player.update();
                repaint();
                time++;
                System.out.println(time);

                if(time <= 50){
                    level = 1;
                } else if(time <= 100){
                    level = 2;
                } else if(time <= 150){
                    level = 3;
                } else if(time <= 200){
                    level = 4;
                } else if(time <= 250){
                    level = 5;
                }


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
//            player.setLoc(new Point(player.getLoc().x, (player.getLoc().y-39) + num));
        }
        for(Bird b: bird){
            b.setLoc(new Point(b.getLoc().x, b.getLoc().y + num));
        }
//        player.setLoc(new Point(player.getLoc().x, (player.getLoc().y) + num));
//        spawn ++;
    }
    public void testShift(int num){
//        int temp = player.getLoc().y-39;
        for(Platform a: platform) {
            a.setLoc(new Point(a.getLoc().x, a.getLoc().y + num));
        }
        for(Bird b: bird){
            b.setLoc(new Point(b.getLoc().x, b.getLoc().y + num));
        }
        player.setLoc(new Point(player.getLoc().x, (player.getLoc().y) + num));
        spawn++;
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
        GradientPaint sky = new GradientPaint(0, 0, Color.WHITE, 0, 0, Color.WHITE);


        //level 1: clear-blue sky
        if(level == 1) {
            Color skytop = new Color(50, 88, 233);
            Color skybot = new Color(178, 229, 255);
            sky = new GradientPaint(FRAMEWIDTH, 0, skybot, 0, FRAMEHEIGHT - FRAMEHEIGHT/22, skytop);
        }

        //level 2: blizzard
        if(level == 2) {
            Color skytop = new Color(46, 64, 121);
            Color skybot = new Color(51, 51, 51);
            sky = new GradientPaint(FRAMEWIDTH, 0, skybot, 0, FRAMEHEIGHT - FRAMEHEIGHT/22, skytop);
        }
        
        //level 3: purple sky
        if(level == 3) {
            Color skytop = new Color(96, 17, 233);
            Color skybot = new Color(226, 163, 233);
            sky = new GradientPaint(FRAMEWIDTH, 0, skybot, 0, FRAMEHEIGHT - FRAMEHEIGHT/22, skytop);
        }

        //level 4: night
        if(level == 4) {
            Color skytop = new Color(6, 0, 38);
            Color skybot = new Color(53, 14, 96);
            sky = new GradientPaint(FRAMEWIDTH, 0, skybot, 0, FRAMEHEIGHT - FRAMEHEIGHT/22, skytop);

        }

        //level 5: sunset
        if(level == 5) {
            Color skytop = new Color(255, 133, 0);
            Color skybot = new Color(250, 255, 203);
            sky = new GradientPaint(FRAMEWIDTH, 0, skybot, 0, FRAMEHEIGHT - FRAMEHEIGHT/22, skytop);

        }

        g2.setPaint(sky);
        g2.fillRect(0, 0, FRAMEWIDTH, FRAMEHEIGHT);

        //starz / snow
        if(level == 4) {
            for (int i = 0; i < 20; i++) {
                g2.setColor(Color.YELLOW);
                g2.fillOval((int) (Math.random() * FRAMEWIDTH), (int) (Math.random() * FRAMEHEIGHT), 3, 3);
            }
        }
        if(level == 2){
            for (int i = 0; i < 500; i++) {
                g2.setColor(Color.WHITE);
                g2.fillOval((int) (Math.random() * FRAMEWIDTH), (int) (Math.random() * FRAMEHEIGHT), 3, 3);
            }
        }



        for (Platform a : platform) {
            a.draw(g2);
        }

        for(Bird b : bird){
            b.draw(g2);
        }

        player.draw(g2);

        if(gameOver){
            Color color = new Color(0, 0, 0, 210);
            g2.setColor(color);
            g2.fillRect(0, 0, FRAMEWIDTH, FRAMEHEIGHT);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
            g2.drawString("Game Over", FRAMEWIDTH / 2 - 125, FRAMEHEIGHT / 2);
        }

        if(level == 4 || level == 2){
            Color color = new Color(0, 0, 0, 152);
            g2.setColor(color);
            g2.fillRect(0, 0, FRAMEWIDTH, FRAMEHEIGHT);
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


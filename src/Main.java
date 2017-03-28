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
    private int gravity, spawn, not, fly;
    private boolean[] keys;
    private boolean gameOver;

    private ArrayList<Platform> platform;
    private ArrayList<Sprite> powerups;
    private ArrayList<Bird> bird;

    Player player = new Player();


    public Main() {
        gravity = 2;
        not = 0;
        keys = new boolean[512];
        platform = new ArrayList<Platform>();
        bird = new ArrayList<Bird>();
        powerups = new ArrayList<Sprite>();
        gameOver = false;
        spawn = 0;
        fly = 0;

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

        powerups.add(new Jetpack(200, 600));

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
                for (int i = 0; i < powerups.size(); i++) {
                    if(player.intersects(powerups.get(i))){
                        powerups.remove(i);
                        fly = 400;
                        break;
                    }
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
                else if(fly > 0)
                    player.setOnPlatform(true);
                else {
                    gravity --;
                }
                //bug testing for gravity
//                System.out.println(player.getSpeed());

                if(keys[KeyEvent.VK_W]){
//                    System.out.println("w");
                    if(player.isOnPlatform() && fly <1) {
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
                if(spawn >= 5) {
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
                if(fly > 0) {
                    fly--;
                    player.jump();
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
                if(player.getLoc().y<350&&player.isOnPlatform()&& fly < 1) {
                    testShift(4);
                }
                else if(player.getLoc().y<350 && fly >0)
                    testShift(player.getSpeed());



                for(int i = 0; i < platform.size(); i++) {
                    if (platform.get(i).getLoc().y > 750) {
                        platform.remove(i);
                        i--;
//                        System.out.println("removed cloud");
                    }
                }
                for (int j = 0; j < bird.size(); j++) {
                    if(bird.get(j).getLoc().y > 750){
                        bird.remove(j);
                        j--;
//                        System.out.println("removed bird");
                    }

                }
                for (int i = 0; i < powerups.size(); i++) {
                    if(powerups.get(i).getLoc().y>750)
                        powerups.remove(i);
                }

                for(Platform s: platform) {
                    s.update();
                }

                for(Bird b: bird) {
                    b.update();
                }
                for(Sprite s: platform)
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
//            player.setLoc(new Point(player.getLoc().x, (player.getLoc().y-39) + num));
        }
        for(Bird b: bird){
            b.setLoc(new Point(b.getLoc().x, b.getLoc().y + num));
        }
//        player.setLoc(new Point(player.getLoc().x, (player.getLoc().y) + num));
//        spawn ++;
    }
    public void testShift(int num){
        int temp = player.getLoc().y-39;
        for(Platform a: platform) {
            a.setLoc(new Point(a.getLoc().x, a.getLoc().y + num));
        }
        for(Bird b: bird){
            b.setLoc(new Point(b.getLoc().x, b.getLoc().y + num));
        }
        player.setLoc(new Point(player.getLoc().x, (player.getLoc().y) + num));
        if (fly>0)
            spawn+=2;
        else
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

        Color sky = new Color(173, 209, 255);
        g2.setColor(sky);
        g2.fillRect(0, 0, FRAMEWIDTH, FRAMEHEIGHT);

//        g2.drawRect(FRAMEWIDTH-4, FRAMEHEIGHT-4, 2, 1);
        for (Platform a : platform) {
            a.draw(g2);
        }

        for(Bird b : bird){
            b.draw(g2);
        }
        for(Sprite s: powerups)
            s.draw(g2);

        player.draw(g2);

        if(gameOver){
            Color color = new Color(0, 0, 0, 210);
            g2.setColor(color);
            g2.fillRect(0, 0, FRAMEWIDTH, getHeight());
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
            g2.drawString("Game Over", FRAMEWIDTH / 2 - 125, FRAMEHEIGHT / 2);
        }
        if(fly>0){
            g2.setColor(Color.GRAY);
            for (int i = 0; i < 30; i++) {
                g2.fillOval((int)(Math.random()*44+player.getLoc().x),(int)(Math.random()*40+player.getLoc().y+39),5,5);
            }
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


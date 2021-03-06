import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.NoRouteToHostException;
import java.util.ArrayList;

/**
 * Created by andrew_briasco on 3/15/17.
 */

/**
 * finished
 * to do: shift the screen based on the position of the player, (use shift commands ->
 * when player is above or below certain y values) DONE
 * Create platforms on screen and above screen (randomly?) DONE
 * balance gravity and screen shifting DONE
 * add different sized platforms? different types? DONE
 * add other things into game: flying obstacles/creaturesDONE, backgroundDONE, music?, sounds?, powerupsDONE?, livesDOONE?, win condition,
 * add even more things into game: starting screenDONE?, levelsDONE?, difficulties?, different players?, different types of levelsSORTA?
 * ???
 * profit.
 *
 * add birds and platforms for level 5
 * make it so more platforms/birds spawn so it's actually possible
 * ending/victory screen
 */

public class Main extends JPanel {
    public static final int FRAMEWIDTH = 500, FRAMEHEIGHT = 700;
    private Timer timer;
    private int gravity, spawn, not, fly, sboots, level, points, lives, leveled;
    private boolean[] keys;
    private boolean gameOver, cave, grass, sky, start, sunset, win, levelUp;

    private ArrayList<Platform> platform;
    private ArrayList<Sprite> powerups;
    private ArrayList<Bird> bird;
    private ArrayList<KillerPets> killerPets;
    private ArrayList<Sprite> titleDummies;

    private Sprite cake;


    Player player = new Player();


    public Main() {
        gravity = 2;
        not = 0;
        keys = new boolean[512];
        platform = new ArrayList<Platform>();
        bird = new ArrayList<Bird>();
        powerups = new ArrayList<Sprite>();
        killerPets = new ArrayList<KillerPets>();
        titleDummies = new ArrayList<Sprite>();
        gameOver = false;
        spawn = 0;
        fly = 0;
        sboots = 0;
        level = 1;
        points = 0;
        lives = 8;

        titleDummies.add(new Sprite(100, 145, "gudetamaTitle.png"));


//        powerups.add(new Jetpack(100, FRAMEHEIGHT - 100));
//        powerups.add(new SuperBoots(200, FRAMEHEIGHT - 100));
//        powerups.add(new Heart(300, FRAMEHEIGHT - 100));


        //ground thingy
        for(int i = 0; i < 8; i++){
            platform.add(new Platform(i * 70, 660, level));
        }

        //beginning set platforms
        platform.add(new Platform(400, 600, level));
        platform.add(new Platform(220, 500, level));
        platform.add(new Platform(300, 410, level));
        platform.add(new Platform(100, 300, level));
        platform.add(new Platform(150, 250, level));
        platform.add(new Platform(300, 150, level));
        platform.add(new Platform(200, 20, level));
        platform.add(new Platform(100, -20, level));

        platform.add(new Platform(50, 550, level));


        //birdz
        bird.add(new Bird(100, 100, 0, level));
        bird.add(new Bird(200, 300, 0, level));
        bird.add(new Bird(300, 200, 1, level));

        //cats and dogs
        for(int i = 0; i < 3; i++) {
            killerPets.add(new KillerPets(i*150, -25 - i));
        }

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
                        if(powerups.get(i) instanceof Jetpack) {
                            powerups.remove(i);
                            fly = 150;
                            break;
                        }
                        if(powerups.get(i) instanceof SuperBoots){
                            powerups.remove(i);
                            sboots += 3;
                            break;
                        }
                        if(powerups.get(i) instanceof Heart){
                            powerups.remove(i);
                            lives++;
                            break;
                        }
                    }
                }
                for(int kp = 0; kp < killerPets.size(); kp++){
                    if(player.intersects(killerPets.get(kp))){
                        killerPets.remove(kp);
                        lives--;

                        killerPets.add(new KillerPets((int)(Math.random()*FRAMEWIDTH), -100));

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
                    if(player.isOnPlatform() && sboots > 0) {
                        sboots--;
                        player.superJump();
                        keys[KeyEvent.VK_W] = false; //probably.
                        player.setOnPlatform(false);
                    }
                    if(player.isOnPlatform() && fly <1) {
                        player.jump();
                        keys[KeyEvent.VK_W] = false; //probably.
                        player.setOnPlatform(false);
                    }
                }
                if(keys[KeyEvent.VK_D]){
//                    System.out.println("d");
                    player.setLoc(new Point(player.getLoc().x+5, player.getLoc().y));
                }
                if(keys[KeyEvent.VK_B]){
                    player.jump();
                }
                else if(keys[KeyEvent.VK_A]){
//                    System.out.println("a");
                    player.setLoc(new Point(player.getLoc().x-5, player.getLoc().y));
                }
                //spawn clouds off the screen
                if(spawn >= 5) {
                    spawn = 0;
                    int rand = (int) (Math.random() * 8);
                    if(rand == 0) {
                        platform.add(new Platform((int) (Math.random() * 430), -50, level));
                        not = 0;
                    }
                    if(rand == 1) {
                        platform.add(new Platform((int) (Math.random() * 430), -50, level));
                        not = 0;
                    }
                    else if(rand == 3)
                        bird.add(new Bird((int) (Math.random() * 430), -50, (int) (Math.random()*2), level));
                    // add a not = 0 here?
                    else
                        not ++;
                    int power = (int)(Math.random()*100);
                    if (power == 0)
                        powerups.add(new Jetpack((int) (Math.random() * 430), -50));
                    if(power == 1)
                        powerups.add(new SuperBoots((int) (Math.random() * 430), -50));
                    if(power == 2)
                        powerups.add(new Heart((int) (Math.random() * 430), -50));
                }
                if(not == 3){
                    not= 0;
                    platform.add(new Platform((int)(Math.random()*430), -50, level));
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

                //if player falls out of screen or no lives
                if(player.getLoc().y>700 || lives == 0) {
                    timer.stop();
                    gameOver = true;
                }
//                    player.setLoc(home);
                //move screen based on player position
//                if(player.getLoc().y<400&&player.getSpeed()>0)
//                    shift(player.getSpeed());

                //shift when at very top of screen
                if(player.getLoc().y<150 && player.getSpeed()>0 && !player.isOnPlatform())
                    shiftUp(4);

                //shift when on platform
                else if(player.getLoc().y<350&&player.isOnPlatform()&& fly < 1) {
                    testShift(4);
                }

                if(player.getLoc().y<350 && fly >0 &&player.getSpeed()>=0|| player.getLoc().y<350 && sboots>0 && player.getSpeed()>=0)
                    testShift(player.getSpeed());
                //falling
                if(player.getLoc().y>600 && player.getSpeed()<0)
                    shift(player.getSpeed());



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
                for (int i = 0; i < killerPets.size(); i++) {
                    if(killerPets.get(i).getLoc().y > 750) {
                        killerPets.remove(i);
                        killerPets.add(new KillerPets((int)((Math.random()*FRAMEWIDTH)), -100));
                    }
                }

                for(Platform s: platform) {
                    s.update();
                }

                for(Bird b: bird) {
                    b.update();
                }
                for(Sprite s: platform) {
                    s.update();
                }
                for(KillerPets kp: killerPets){
                    kp.update();
                }

//                if(time <= 50){
//                    level = 1;
//                } else if(time <= 100){
//                    level = 2;
//                } else if(time <= 150){
//                    level = 3;
//                } else if(time <= 200){
//                    level = 4;
//                } else if(time <= 250){
//                    level = 5;
//                } else if(time <= 300){
//                    time = 0;
//                }
                if(points >= 2500 && !cave) {
                    cave  = true;
                    level++;
                    levelUp = true;
//                    System.out.println("level ++");
                }
                if(points >= 5000 && !grass){
                    grass = true;
                    level++;
                    levelUp = true;
//                    System.out.println("level ++");
                }
                if(points >= 7500 && !sky){
                    sky = true;
                    level++;
                    levelUp = true;
//                    System.out.println("level ++");
                }

                if(points >=10000 && !sunset){
                    sunset = true;
                    level++;
                    levelUp = true;
//                    System.out.println("level ++");
                }
                if(points >= 12500){
                    start = false;
                    win = true;
                    cake = new Sprite(FRAMEWIDTH/2 - 150, 100, "cake.png");
                    timer.stop();
                }

                if(levelUp){
                    levelUp = false;
                    leveled = 20;
                }

                player.update();
                repaint();
//                System.out.println(time);
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

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if(mouseEvent.getX() >  FRAMEWIDTH / 2 - 105 && mouseEvent.getX() < FRAMEWIDTH / 2 + 105 && mouseEvent.getY() > FRAMEHEIGHT/2 + 60 && mouseEvent.getY() < FRAMEHEIGHT/2 + 110){
                    start = true;
                }
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
    }

    private void shift(int num){
        for(Platform a: platform) {
            a.setLoc(new Point(a.getLoc().x, a.getLoc().y + num));
//            player.setLoc(new Point(player.getLoc().x, (player.getLoc().y-39) + num));
        }
        for(Bird b: bird){
            b.setLoc(new Point(b.getLoc().x, b.getLoc().y + num));
        }
        for(Sprite s: powerups) {
            s.setLoc(new Point(s.getLoc().x, s.getLoc().y + num));
        }
        for(KillerPets kp: killerPets) {
            kp.setLoc(new Point(kp.getLoc().x, kp.getLoc().y + num));
        }


//        player.setLoc(new Point(player.getLoc().x, (player.getLoc().y) + num));
//        spawn ++;
    }
    private void shiftUp(int num){
        if(num > 0)
            points+=num;
        for(Platform a: platform) {
            a.setLoc(new Point(a.getLoc().x, a.getLoc().y + num));
        }
        for(Bird b: bird){
            b.setLoc(new Point(b.getLoc().x, b.getLoc().y + num));
        }
        for(Sprite s: powerups)
            s.setLoc(new Point(s.getLoc().x, s.getLoc().y + num));
        player.setLoc(new Point(player.getLoc().x, (player.getLoc().y) + num));
            spawn++;
        for(KillerPets kp: killerPets) {
            kp.setLoc(new Point(kp.getLoc().x, kp.getLoc().y + num));
        }
        spawn +=2;
    }
    private void testShift(int num){
        if(num > 0)
            points+=num;
        for(Platform a: platform) {
            a.setLoc(new Point(a.getLoc().x, a.getLoc().y + num));
        }
        for(Bird b: bird){
            b.setLoc(new Point(b.getLoc().x, b.getLoc().y + num));
        }
        for(Sprite s: powerups)
            s.setLoc(new Point(s.getLoc().x, s.getLoc().y + num));
        player.setLoc(new Point(player.getLoc().x, (player.getLoc().y) + num));
        if (fly>0)
            spawn += 2;
        else
            spawn++;
        for(KillerPets kp: killerPets) {
            kp.setLoc(new Point(kp.getLoc().x, kp.getLoc().y + num));
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
        GradientPaint sky = new GradientPaint(0, 0, Color.WHITE, 0, 0, Color.WHITE);

        if(start) {

            //level 3: clear-blue sky
            if (level == 1) {
                Color skytop = new Color(233, 25, 19);
                Color skybot = new Color(255, 169, 68);
                sky = new GradientPaint(FRAMEWIDTH, 0, skybot, 0, FRAMEHEIGHT - FRAMEHEIGHT / 22, skytop);
            }

            //level 2: blizzard
            if (level == 2) {
                Color skytop = new Color(0, 43, 21);
                Color skybot = new Color(45, 13, 97);
                sky = new GradientPaint(FRAMEWIDTH, 0, skybot, 0, FRAMEHEIGHT - FRAMEHEIGHT / 22, skytop);
            }

            //level 3: purple sky
            if (level == 3) {
                Color skytop = new Color(96, 17, 233);
                Color skybot = new Color(226, 163, 233);
                sky = new GradientPaint(FRAMEWIDTH, 0, skybot, 0, FRAMEHEIGHT - FRAMEHEIGHT / 22, skytop);
            }

            //level 4: night
            if (level == 4) {
                Color skytop = new Color(0, 27, 87);
                Color skybot = new Color(53, 0, 96);
                sky = new GradientPaint(FRAMEWIDTH, 0, skybot, 0, FRAMEHEIGHT - FRAMEHEIGHT / 22, skytop);

            }

            //level 5: sunset
            if (level == 5) {
                Color top = new Color(132, 255, 221);
                Color bot = new Color(14, 226, 220);
                sky = new GradientPaint(FRAMEWIDTH, 0, bot, 0, FRAMEHEIGHT - FRAMEHEIGHT / 22, top);

            }

            g2.setPaint(sky);
            g2.fillRect(0, 0, FRAMEWIDTH, FRAMEHEIGHT);

            //starz / snow
            if (level == 4) {
                for (int i = 0; i < 50; i++) {
                    g2.setColor(Color.YELLOW);
                    g2.fillOval((int) (Math.random() * FRAMEWIDTH), (int) (Math.random() * FRAMEHEIGHT), 3, 3);
                }
            }
            if (level == 2) {
                for (int i = 0; i < 500; i++) {
                    g2.setColor(Color.WHITE);
                    g2.fillOval((int) (Math.random() * FRAMEWIDTH), (int) (Math.random() * FRAMEHEIGHT), 3, 3);
                }
            }

//        g2.drawRect(FRAMEWIDTH-4, FRAMEHEIGHT-4, 2, 1);
            for (Platform a : platform) {
                a.draw(g2);
            }

            for (Bird b : bird) {
                b.draw(g2);
            }
            for (Sprite s : powerups) {
                s.draw(g2);
            }
            for (KillerPets kp : killerPets) {
                kp.draw(g2);
            }


            player.draw(g2);


            if (level == 4 || level == 2) {
                Color color = new Color(0, 0, 0, 133);
                g2.setColor(color);
                g2.fillRect(0, 0, FRAMEWIDTH, FRAMEHEIGHT);
            }
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
            g2.drawString("Points: " + points, 100, 695);
            g2.drawString("Lives: " + lives, 10, 695);

            //jetpack fuel + num superboot jumps
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
            g2.drawString("Jetpack Fuel: " + fly, 200, 695);
            g2.drawString("Number of Super Jumps: " + sboots, 325, 695);


            if (fly > 0) {
                g2.setColor(Color.GRAY);
                for (int i = 0; i < 30; i++) {
                    g2.fillOval((int) (Math.random() * 44 + player.getLoc().x), (int) (Math.random() * 40 + player.getLoc().y + 39), 5, 5);
                }
            }

//        if(sboots>0){
//            g2.setColor(Color.WHITE);
//            g2.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
//            g2.drawString("Jumps left: " + sboots, 200, 695);
//        }
            if(leveled > 0){
                leveled --;
                g2.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
                g2.drawString("Level Up", 150, 300);
            }
        }



        if(gameOver){
            Color color = new Color(0, 0, 0, 210);
            g2.setColor(color);
            g2.fillRect(0, 0, FRAMEWIDTH, FRAMEHEIGHT);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
            g2.drawString("Game Over", FRAMEWIDTH / 2 - 125, FRAMEHEIGHT / 2);
        }

        if(!start){
//            g2.setColor(new Color(52, 132, 213));
            Color top = new Color(255, 205, 42);
            Color bot = new Color(174, 71, 226);

//            Color bot = new Color(233, 121, 218);
            sky = new GradientPaint(FRAMEWIDTH, 0, bot, 0, FRAMEHEIGHT, top);
            g2.setPaint(sky);

            g2.fillRect(0, 0, FRAMEWIDTH, FRAMEHEIGHT);

            if(!win) {
                g2.setColor(new Color(244, 237, 231));
                g2.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
                g2.drawString("Gudetama Jump", FRAMEWIDTH / 2 - 190, FRAMEHEIGHT / 8);

//            g2.setColor(new Color(244, 237, 231));
                g2.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
                g2.drawString("By: Andrew Briasco-Stewart & Rachel Chau", 45, 130);

                g2.setFont(new Font("Comic Sans MS", Font.ITALIC, 15));
                g2.drawString("Gudetama, the egg yoke, has lost his friends, the cake ingredients.", 20, FRAMEHEIGHT / 4 + 100);
                g2.drawString("Gudetama wants to become a part of the cake with his friends.", 30, FRAMEHEIGHT / 4 + 125);
                g2.drawString("Help Gudetama become a part of the cake", 100, FRAMEHEIGHT / 4 + 150);
                g2.drawString("(which is like 403847182374m in the air).", 100, FRAMEHEIGHT / 4 + 175);

                g2.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
                g2.drawString("Do you accept this mission?", 150, FRAMEHEIGHT / 2 + 50);

                g2.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
                g2.drawString("Instructions:", 190, FRAMEHEIGHT / 2 + 160);

                g2.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
                g2.drawString("w = jump     a = left     d = right", 145, FRAMEHEIGHT / 2 + 185);
                g2.drawString("Collect jetpacks and superboots to soar your way to the top!", 40, FRAMEHEIGHT / 2 + 210);
                g2.drawString("Hop on birds (they'll give you a boost)", 120, FRAMEHEIGHT / 2 + 235);
                g2.drawString("BEWARE! Avoid the raining cats and dogs!", 105, FRAMEHEIGHT / 2 + 260);
                g2.drawString("Collect hearts to regain lives <3", 140, FRAMEHEIGHT / 2 + 285);

                Rectangle startButton = new Rectangle(FRAMEWIDTH / 2 - 105, FRAMEHEIGHT / 2 + 60, 105 * 2, 50);
                g2.fill(startButton);
                g2.setColor(new Color(250, 171, 29));
                g2.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
                g2.drawString("YES", FRAMEWIDTH / 2 - 40, FRAMEHEIGHT / 2 + 100);

                for (Sprite td : titleDummies) {
                    td.draw(g2);
                }
            } else {

                cake.draw(g2);
                g2.setColor(new Color(244, 237, 231));
                g2.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
                g2.drawString("YOU WON", 115, FRAMEHEIGHT/2 + 100);

            }

        }



    }

    public static void main(String[] args) {
        JFrame window = new JFrame("Gudetama Jump");
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


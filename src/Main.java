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

public class Main extends JPanel {
    public static final int FRAMEWIDTH = 500, FRAMEHEIGHT = 700;
    private Timer timer;
    private int gravity;
    private boolean[] keys;

    private ArrayList<Platform> platform;

    Player player = new Player();


    public Main() {
        gravity = 2;
        keys = new boolean[512];
        platform = new ArrayList<Platform>();
        platform.add(new Platform(250, 660));




        timer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                player.setOnPlatform(false);
                for(Platform a: platform){
                    if(player.intersects(a))
                        player.setOnPlatform(true);
                }

                if(keys[KeyEvent.VK_W]){
//                    System.out.println("w");
                    if(player.isOnPlatform()) {
                        player.jump();
                        player.setDir(Sprite.NORTH);
                        player.update();
                        keys[KeyEvent.VK_W] = false; //probably.
                    }
                }
                if(keys[KeyEvent.VK_D]){
//                    System.out.println("d");
                    player.setLoc(new Point(player.getLoc().x+6, player.getLoc().y));
                    player.update();
                }
                else if(keys[KeyEvent.VK_A]){
//                    System.out.println("a");
                    player.setLoc(new Point(player.getLoc().x-6, player.getLoc().y));
                    player.update();
                }

                if(!player.isOnPlatform()&& gravity <1) {
                    if (player.getSpeed() > -4) {
                        gravity = 2;
                        player.setSpeed(player.getSpeed() - 1);
                    }
                }
                else {
                    gravity --;
                    player.setSpeed(0);
                }



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




    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

//        g2.drawRect(FRAMEWIDTH-4, FRAMEHEIGHT-4, 2, 1);
        for (Platform a : platform) {
            a.draw(g2);
        }
        player.draw(g2);
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


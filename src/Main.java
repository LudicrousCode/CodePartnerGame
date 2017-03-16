import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by andrew_briasco on 3/15/17.
 */

public class Main extends JPanel {
    public static final int FRAMEWIDTH = 500, FRAMEHEIGHT = 700;
    private Timer timer;
    private boolean[] keys;

    private ArrayList<Platform> platform;

    Player player = new Player();


    public Main() {
        keys = new boolean[512];






        timer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                for(Platform a: platform){
                    if(player.intersects(a))
                        player.setOnPlatform(true);
                }

                if(keys[KeyEvent.VK_W]){
                    if(player.isOnPlatform()) {
                        player.jump();
                        player.setDir(Sprite.NORTH);
                        player.update();
                        keys[KeyEvent.VK_W] = false; //probably.
                    }
                }
                if(keys[KeyEvent.VK_D]){
                    player.setSide(1);
                    player.update();
                }
                else if(keys[KeyEvent.VK_A]){
                    player.setSide(-1);
                    player.setDir(Sprite.WEST);
                    player.update();
                }
                else
                    player.setSide(0);














            }
        });
        //outside timer but still in constructor
    }




    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.drawRect(FRAMEWIDTH-4, FRAMEHEIGHT-4, 2, 1);

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


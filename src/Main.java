import javax.swing.*;
import java.awt.*;

/**
 * Created by andrew_briasco on 3/15/17.
 */
public class Main extends JPanel {
    public static final int FRAMEWIDTH = 500, FRAMEHEIGHT = 700;

    public Main() {


    }




    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.drawRect(FRAMEWIDTH-4, FRAMEHEIGHT-4, 2, 1);
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


package board;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class snake_board extends JPanel implements ActionListener {
    private final int b_width = 300;
    private final int b_height = 300;
    private final int dot_size = 10;
    private final int all_dots = 900;
    private final int rand_pos = 29;
    private final int DELAY = 140;
    private final int x[] = new int[all_dots];
    private final int y[] = new int[all_dots];
    private int dots;
    private int apple_x;
    private int apple_y;
    private boolean left_direction = false;
    private boolean right_direction = true;
    private boolean up_direction = false;
    private boolean down_direction = false;
    private boolean inGame = true;
    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;

    public snake_board() {

        addKeyListener(new TAdapter());
        setBackground(Color.WHITE);
        setFocusable(true);
        setPreferredSize(new Dimension(b_width, b_height));
        load_images();
        init_game();
    }

    private void load_images() {

        ImageIcon iid = new ImageIcon("E:\\NetBeans App pics\\dot.png");
        ball = iid.getImage();

        ImageIcon iia = new ImageIcon("E:\\NetBeans App pics\\apple.jpg");
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon("E:\\NetBeans App pics\\dot.png");
        head = iih.getImage();
    }

    private void init_game() {
        dots = 5;
        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }
        locate_apple();
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        do_drawing(g);
    }
    
    private void do_drawing(Graphics g) {
        if (inGame) {
            g.drawImage(apple, apple_x, apple_y, this);
            for (int z = 0; z < dots; z++) {
                if (z == 0)
                    g.drawImage(head, x[z], y[z], this);
                else
                    g.drawImage(ball, x[z], y[z], this);
            }
            Toolkit.getDefaultToolkit().sync();

        }
        else
            game_over(g);     
    }

    private void game_over(Graphics g) {
        String msg = "Game Over!! Your Score is "+""+(dots-5);
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);
        g.setColor(Color.BLACK);
        g.setFont(small);
        g.drawString(msg, (b_width - metr.stringWidth(msg)) / 2, b_height / 2);
    }

    private void check_apple() {
        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            dots++;
            locate_apple();
        }
    }

    private void move() {
        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }
        if(left_direction)
            x[0] -= dot_size;
        else if(right_direction)
            x[0] += dot_size;
        else if(up_direction)
            y[0] -= dot_size;
        else if(down_direction)
            y[0] += dot_size;
    }

    private void check_collision() {
        for (int z = dots; z > 0; z--) {
            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z]))
                inGame = false;
        }
        if (y[0] >= b_height)
            inGame = false;
        if (y[0] < 0)
            inGame = false;
        if (x[0] >= b_width)
            inGame = false;
        if (x[0] < 0)
            inGame = false;
        if(!inGame)
            timer.stop();
    }

    private void locate_apple() {
        int r = (int) (Math.random() * rand_pos);
        apple_x = ((r * dot_size));
        r = (int) (Math.random() * rand_pos);
        apple_y = ((r * dot_size));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            check_apple();
            check_collision();
            move();
        }
        repaint();
    }
    
    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if ((key == KeyEvent.VK_LEFT) && (!right_direction)) {
                left_direction = true;
                up_direction = false;
                down_direction = false;
            }
            else if ((key == KeyEvent.VK_RIGHT) && (!left_direction)) {
                right_direction = true;
                up_direction = false;
                down_direction = false;
            }
            else if ((key == KeyEvent.VK_UP) && (!down_direction)) {
                up_direction = true;
                right_direction = false;
                left_direction = false;
            }
            else if ((key == KeyEvent.VK_DOWN) && (!up_direction)) {
                down_direction = true;
                right_direction = false;
                left_direction = false;
            }
            else if(key == 'r' || key == 'R')
            {
                timer.stop();
                inGame=true;
                init_game();
                left_direction=false;
                up_direction=false;
                down_direction=false;
                right_direction=true;
            }
            else if(key == KeyEvent.VK_ESCAPE)
                inGame=false;
        }
    }
}
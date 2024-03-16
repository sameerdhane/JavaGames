import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PongGame extends JPanel implements ActionListener, KeyListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int BALL_DIAMETER = 20;
    private static final int PADDLE_WIDTH = 20;
    private static final int PADDLE_HEIGHT = 100;
    private static final int PADDLE_SPEED = 10;
    private static final int BALL_SPEED = 5;

    private int ballX = WIDTH / 2 - BALL_DIAMETER / 2;
    private int ballY = HEIGHT / 2 - BALL_DIAMETER / 2;
    private int ballXSpeed = BALL_SPEED;
    private int ballYSpeed = BALL_SPEED;

    private int paddle1Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
    private int paddle2Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;

    private boolean upKeyPressed = false;
    private boolean downKeyPressed = false;
    private boolean upKeyPressed2 = false;
    private boolean downKeyPressed2 = false;

    public PongGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        Timer timer = new Timer(1000 / 60, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(ballX, ballY, BALL_DIAMETER, BALL_DIAMETER);
        g.fillRect(0, paddle1Y, PADDLE_WIDTH, PADDLE_HEIGHT);
        g.fillRect(WIDTH - PADDLE_WIDTH, paddle2Y, PADDLE_WIDTH, PADDLE_HEIGHT);
    }

    public void move() {
        ballX += ballXSpeed;
        ballY += ballYSpeed;

        if (ballY <= 0 || ballY + BALL_DIAMETER >= HEIGHT) {
            ballYSpeed = -ballYSpeed;
        }

        if (ballX <= PADDLE_WIDTH && ballY + BALL_DIAMETER / 2 >= paddle1Y && ballY + BALL_DIAMETER / 2 <= paddle1Y + PADDLE_HEIGHT) {
            ballXSpeed = -ballXSpeed;
        }

        if (ballX + BALL_DIAMETER >= WIDTH - PADDLE_WIDTH && ballY + BALL_DIAMETER / 2 >= paddle2Y && ballY + BALL_DIAMETER / 2 <= paddle2Y + PADDLE_HEIGHT) {
            ballXSpeed = -ballXSpeed;
        }

        if (ballX < 0 || ballX > WIDTH) {
            ballX = WIDTH / 2 - BALL_DIAMETER / 2;
            ballY = HEIGHT / 2 - BALL_DIAMETER / 2;
            ballXSpeed = BALL_SPEED;
            ballYSpeed = BALL_SPEED;
        }

        if (upKeyPressed && paddle1Y > 0) {
            paddle1Y -= PADDLE_SPEED;
        }

        if (downKeyPressed && paddle1Y + PADDLE_HEIGHT < HEIGHT) {
            paddle1Y += PADDLE_SPEED;
        }
        
        if (upKeyPressed2 && paddle2Y > 0) {
            paddle2Y -= PADDLE_SPEED;
        }

        if (downKeyPressed2 && paddle2Y + PADDLE_HEIGHT < HEIGHT) {
            paddle2Y += PADDLE_SPEED;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                upKeyPressed = true;
                break;
            case KeyEvent.VK_DOWN:
                downKeyPressed = true;
                break;
            case KeyEvent.VK_W:
                upKeyPressed2 = true;
                break;
            case KeyEvent.VK_S:
                downKeyPressed2 = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                upKeyPressed = false;
                break;
            case KeyEvent.VK_DOWN:
                downKeyPressed = false;
                break;
            case KeyEvent.VK_W:
                upKeyPressed2 = false;
                break;
            case KeyEvent.VK_S:
                downKeyPressed2 = false;
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not needed for this game
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pong Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new PongGame());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}


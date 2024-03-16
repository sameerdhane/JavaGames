import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class SpaceInvaders extends JPanel implements ActionListener, KeyListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int PLAYER_WIDTH = 50;
    private static final int PLAYER_HEIGHT = 30;
    private static final int ENEMY_WIDTH = 30;
    private static final int ENEMY_HEIGHT = 30;
    private static final int BULLET_WIDTH = 5;
    private static final int BULLET_HEIGHT = 15;
    private static final int ENEMY_ROWS = 5;
    private static final int ENEMY_COLS = 10;
    private static final int ENEMY_SPACING = 40;
    private static int ENEMY_SPEED = 2;
    private static final int PLAYER_SPEED = 5;
    private static final int BULLET_SPEED = 5;

    private boolean[] keysPressed = new boolean[256];
    private boolean gameRunning = true;
    private Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<Bullet> bullets;

    public SpaceInvaders() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        initializeGame();
        Timer timer = new Timer(1000 / 60, this);
        timer.start();
    }

    public void initializeGame() {
        player = new Player(WIDTH / 2 - PLAYER_WIDTH / 2, HEIGHT - PLAYER_HEIGHT - 20, PLAYER_WIDTH, PLAYER_HEIGHT);
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        for (int i = 0; i < ENEMY_ROWS; i++) {
            for (int j = 0; j < ENEMY_COLS; j++) {
                int x = j * (ENEMY_WIDTH + ENEMY_SPACING);
                int y = i * (ENEMY_HEIGHT + ENEMY_SPACING);
                enemies.add(new Enemy(x, y, ENEMY_WIDTH, ENEMY_HEIGHT));
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (gameRunning) {
            player.draw(g);
            for (Enemy enemy : enemies) {
                enemy.draw(g);
            }
            for (Bullet bullet : bullets) {
                bullet.draw(g);
            }
        } else {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("Game Over", WIDTH / 2 - 100, HEIGHT / 2);
        }
    }

    public void update() {
        if (gameRunning) {
            player.update();
            for (Bullet bullet : bullets) {
                bullet.update();
            }
            for (int i = bullets.size() - 1; i >= 0; i--) {
                Bullet bullet = bullets.get(i);
                if (bullet.getY() < 0) {
                    bullets.remove(i);
                }
            }
            for (int i = enemies.size() - 1; i >= 0; i--) {
                Enemy enemy = enemies.get(i);
                enemy.update();
                if (enemy.getY() + enemy.getHeight() >= HEIGHT) {
                    gameRunning = false;
                }
                for (int j = bullets.size() - 1; j >= 0; j--) {
                    Bullet bullet = bullets.get(j);
                    if (enemy.intersects(bullet)) {
                        enemies.remove(i);
                        bullets.remove(j);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        update();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        keysPressed[keyCode] = true;
        if (keyCode == KeyEvent.VK_SPACE) {
            bullets.add(new Bullet(player.getX() + player.getWidth() / 2 - BULLET_WIDTH / 2, player.getY(), BULLET_WIDTH, BULLET_HEIGHT));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keysPressed[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not needed for this game
    }

    private class Player {
        private int x;
        private int y;
        private int width;
        private int height;

        public Player(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public void draw(Graphics g) {
            g.setColor(Color.GREEN);
            g.fillRect(x, y, width, height);
        }

        public void update() {
            if (keysPressed[KeyEvent.VK_LEFT] && x > 0) {
                x -= PLAYER_SPEED;
            }
            if (keysPressed[KeyEvent.VK_RIGHT] && x < WIDTH - width) {
                x += PLAYER_SPEED;
            }
        }
    }

    private class Enemy {
        private int x;
        private int y;
        private int width;
        private int height;

        public Enemy(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public void draw(Graphics g) {
            g.setColor(Color.RED);
            g.fillRect(x, y, width, height);
        }

        public void update() {
            x += ENEMY_SPEED;
            if (x <= 0 || x + width >= WIDTH) {
                ENEMY_SPEED = -ENEMY_SPEED;
                y += 10;
            }
        }

        public boolean intersects(Bullet bullet) {
            return bullet.getX() < x + width && bullet.getX() + bullet.getWidth() > x &&
                    bullet.getY() < y + height && bullet.getY() + bullet.getHeight() > y;
        }
    }

    private class Bullet {
        private int x;
        private int y;
        private int width;
        private int height;

        public Bullet(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public void draw(Graphics g) {
            g.setColor(Color.YELLOW);
            g.fillRect(x, y, width, height);
        }

        public void update() {
            y -= BULLET_SPEED;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Space Invaders");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new SpaceInvaders());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

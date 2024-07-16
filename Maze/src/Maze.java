import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
 
public class Maze extends JPanel {
    private static final int Cell_size = 20;
    private static final int Maze_width = 15;
    private static final int Maze_height = 15;
    private int[][] maze;
    private Random random;
 
    public Maze() {
        this.setPreferredSize(new Dimension(Maze_width * Cell_size, Maze_height * Cell_size));
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    generateMaze();
                    repaint();
                }
            }
        });
        random = new Random();
        generateMaze();
    }
 
    private void generateMaze() {
        maze = new int[Maze_height][Maze_width];
        for (int y = 0; y < Maze_height; y++) {
            for (int x = 0; x < Maze_width; x++) {
                maze[y][x] = 1;
            }
        }
        carvePath(1, 1);
        maze[0][1] = 2; // スタート地点
        maze[Maze_height - 1][Maze_width - 2] = 3; // ゴール地点
    }
 
    private void carvePath(int x, int y) {
        int[] directions = {1, 2, 3, 4};
        shuffleArray(directions);
 
        for (int direction : directions) {
            int nx = x, ny = y;
            switch (direction) {
                case 1: nx = x + 2; break; // 下
                case 2: nx = x - 2; break; // 上
                case 3: ny = y + 2; break; // 右
                case 4: ny = y - 2; break; // 左
            }
 
            if (isInBounds(nx, ny) && maze[ny][nx] == 1) {
                maze[ny][nx] = 0;
                maze[ny + (y - ny) / 2][nx + (x - nx) / 2] = 0;
                carvePath(nx, ny);
            }
        }
    }
 
    private boolean isInBounds(int x, int y) {
        return x > 0 && y > 0 && x < Maze_width - 1 && y < Maze_height - 1;
    }
 
    private void shuffleArray(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
 
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int y = 0; y < Maze_height; y++) {
            for (int x = 0; x < Maze_width; x++) {
                if (maze[y][x] == 1) {
                    g.setColor(Color.BLACK);
                } else if (maze[y][x] == 0) {
                    g.setColor(Color.WHITE);
                } else if (maze[y][x] == 2) {
                    g.setColor(Color.BLUE);
                } else if (maze[y][x] == 3) {
                    g.setColor(Color.RED);
                }
                g.fillRect(x * Cell_size, y * Cell_size, Cell_size, Cell_size);
            }
        }
    }
 
    public static void main(String[] args) {
        JFrame frame = new JFrame("Maze Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Maze());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
 
 

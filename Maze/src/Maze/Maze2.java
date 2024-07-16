package Maze;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
 
public class Maze2 extends MyFrame {
	Reroll reroll = new Reroll();
 
	public void run() {
		addKeyListener(reroll);
		make();
	}
 
	private int pointX; //ブロックを置いたり消したりする目印。
	private int pointY;
	private int width; //横幅と高さ。
	private int height;
	private byte[][] map; //マップを格納する配列
 
	//コンストラクター
	public Maze2(int w, int h) {
		width = w;
		height = h;
		if (w % 2 != 0 && h % 2 != 0 && 5 <= w && 5 <= h) {
			map = new byte[width][height];
			make();
 
		}
	}
 
	//x,y座標共に奇数なランダムな座標を返す
	int randomPos(int muki) {
		int result = 1 + 2 * (int) Math.floor((Math.random() * (muki - 1)) / 2);
		return result;
	}
 
	//マップを作成する
	private void make() {
 
		pointX = randomPos(width);
		pointY = randomPos(height);
 
		//すべて壁で埋める。
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				map[x][y] = 1;
			}
		}
		map[pointX][pointY] = 0;
		dig();
		for (int y = 0; y < 15; y++) {
			for (int x = 0; x < 15; x++) {
				if (map[x][y] == 1) {
					if (y == 0 && x == 1) {
						setColor(0, 0, 255);
						drawString("■", 40 + (x * 19), 70 + (y * 19), 20);
						sleep(0.001);
					} else if ((y == 14) && (x == 13)) {
						setColor(255, 0, 0);
						drawString("■", 40 + (x * 19), 70 + (y * 19), 20);
						sleep(0.001);
					} else {
						setColor(189, 93, 226);
						drawString("■", 40 + (x * 19), 70 + (y * 19), 20);
						sleep(0.001);
					}
				}
			}
 
		}
	}
 
	private void dig() {
		if (isAbleContinueDig() && map[pointX][pointY] == 0) {
			map[pointX][pointY] = 0;
			int direction = (int) Math.floor(Math.random() * 4);
			switch (direction) {
			case 0:
				if (pointY != 1) {
					if (map[pointX][pointY - 2] == 1) {
						map[pointX][pointY - 1] = 0;
						pointY -= 2;
						break;//u
						
					}
				}
			case 1:
				if (pointY != height - 2) {
					if (map[pointX][pointY + 2] == 1) {
						map[pointX][pointY + 1] = 0;
						pointY += 2;
						break;//d
					}
				}
			case 2:
				if (pointX != 1) {
					if (map[pointX - 2][pointY] == 1) {
						map[pointX - 1][pointY] = 0;
						pointX -= 2;
						break;//l
					}
				}
			case 3:
				if (pointX != width - 2) {
					if (map[pointX + 2][pointY] == 1) {
						map[pointX + 1][pointY] = 0;
						pointX += 2;
						break;//r
					}
				}
			}
			map[pointX][pointY] = 0;
			dig();
 
		} else if (isAbleDig()) {
			pointX = randomPos(width);
			pointY = randomPos(height);
			dig();
		}
 
	}
 
	//まだ掘るところがあるか確かめる
	private boolean isAbleDig() {
		boolean result;
		int cnt = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (x % 2 != 0 && y % 2 != 0) {
 
					if (map[x][y] != 0) {
						cnt++;
					}
				}
			}
		}
		if (cnt == 0) {
			result = false;
		} else {
			result = true;
		}
		return result;
	}
 
	private boolean isAbleContinueDig() {//四方に掘れるところが残っているかどうか判断する
 
		if (pointY != 1) {
			if (map[pointX][pointY - 2] == 1) {
				return true;
			}
		}
		if (pointY != height - 2) {
			if (map[pointX][pointY + 2] == 1) {
				return true;
			}
		}
		if (pointX != 1) {
			if (map[pointX - 2][pointY] == 1) {
				return true;
			}
		}
		if (pointX != width - 2) {
			if (map[pointX + 2][pointY] == 1) {
				return true;
			}
		}
		return false;
	}
 
	public byte[][] getMaze() {
		return map;
	}
 
	public class Reroll implements KeyListener {
 
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO 自動生成されたメソッド・スタブ
 
		}
 
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO 自動生成されたメソッド・スタブ
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				clear();
				make();
			}
		}
 
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO 自動生成されたメソッド・スタブ
 
		}
 
	}
}
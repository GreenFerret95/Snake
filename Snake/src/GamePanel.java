import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

	Player player;
	Timer timer;
	GameOver gameOverHandle;
	Paused pauseHandle;
	Score score;

	private static ArrayList<Fruit> fruitList = new ArrayList<Fruit>();

	private boolean wPressed = false;
	private boolean sPressed = false;
	private boolean aPressed = false;
	private boolean dPressed = false;

	private boolean playing = true;
	private boolean gameOver = false;
	private boolean paused = false;

	boolean debugInfo = false;

	private int frameRate = 1000 / 7;

	public GamePanel() {

		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);

	}

	public void init() {

		player = new Player(this, ((getWidth() / 15) / 2) * 15, ((getHeight() / 15) / 2) * 15);
		gameOverHandle = new GameOver(this);
		pauseHandle = new Paused(this);
		Score score = new Score(this);
		

		timer = new Timer(frameRate, this);
		timer.start();

		fruitList.add(new Fruit(this));
		fruitList.add(new Fruit(this));

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (playing) {
			if (!gameOver) {
				for (int i = 0; i < fruitList.size(); i++) {
					fruitList.get(i).draw(g);
				}
				g.setColor(Color.white);

				player.draw(g);
				g.setColor(Color.WHITE);
				g.setFont(new Font("TimesRoman", Font.BOLD, 15));
				g.drawString("Total Length: " + (player.getBodyCount() + 1), (getWidth() / 2) - 40, 15);

			} else if (gameOver) {
				gameOverHandle.draw(g);
			}

			if (paused) {
				pauseHandle.draw(g);
			}

			drawDebugInfo(g);

		}

	}

	public void drawDebugInfo(Graphics g) {
		if (debugInfo) {

			g.setColor(Color.yellow);
			g.drawString("Total Fruits: " + getFruitList().size(), getWidth() - 250, 25);
			g.setColor(Color.white);
			g.drawString("x: " + player.getHeadX() + " y: " + player.getHeadY(), 15, getHeight() - 10);
			g.drawString("Body Count: " + player.getBodyCount(), 15, getHeight() - 20);

			int screenLength = 1;
			int height = 15;

			int width = 6;
			int inc = 1;

			g.setFont(new Font("TimesRoman", Font.PLAIN, 10));
			for (int i = 1; i <= fruitList.size(); i++) {

				g.setColor(fruitList.get(i - 1).getColor());
				g.drawString("[ " + getFruitList().get(i - 1).getX() + "][ " + getFruitList().get(i - 1).getY()
						+ "] at: " + (i - 1), width * inc, height * screenLength);
				screenLength++;

				if (height * screenLength > getHeight()) {
					screenLength = 1;
					inc += 13;

				}

			}

			for (int i = 1; i <= player.getxCoordList().size(); i++) {

				int x = player.getxCoordList().get(i - 1);
				int dx = player.getxCoordList().get(i - 1) + player.getWidth();

				int y = player.getyCoordList().get(i - 1);
				int dy = player.getyCoordList().get(i - 1) + player.getHeight();

				if (i <= 14) {
					g.setColor(Color.gray);
					g.drawString("[" + (i - 1) + "] x: " + x + " dx: " + dx + " y: " + y + " dy: " + dy,
							getWidth() - 150, 25 * i - 1);
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (playing) {

			player.step();
			repaint();

			if (gameOver) {
				gameOverHandle.step();
				repaint();
			}
			if (paused) {
				pauseHandle.step();
				repaint();
			}
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {

		/*
		 * MOVE UP
		 */
		if (e.getKeyCode() == KeyEvent.VK_W && !sPressed) {
			wPressed = true;
			sPressed = false;
			aPressed = false;
			dPressed = false;

		}
		/*
		 * MOVE DOWN
		 */
		if (e.getKeyCode() == KeyEvent.VK_S && !wPressed) {
			wPressed = false;
			sPressed = true;
			aPressed = false;
			dPressed = false;

		}
		/*
		 * MOVE LEFT
		 */
		if (e.getKeyCode() == KeyEvent.VK_A && !dPressed) {
			wPressed = false;
			sPressed = false;
			aPressed = true;
			dPressed = false;

		}
		/*
		 * MOVE RIGHT
		 */
		if (e.getKeyCode() == KeyEvent.VK_D && !aPressed) {
			wPressed = false;
			sPressed = false;
			aPressed = false;
			dPressed = true;

		}

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

			System.exit(0);
		}

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			for (int i = 0; i < 50; i++) {
				fruitList.add(new Fruit(this));
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_DELETE) {
			if (fruitList.size() > 0) {
				for (int i = 0; i < fruitList.size(); i++) {
					fruitList.remove(fruitList.size() - 1);
				}

			}
		}

		if (e.getKeyCode() == KeyEvent.VK_F3) {
			if (!debugInfo) {
				debugInfo = true;
			} else {
				debugInfo = false;
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_P) {
			if (paused) {
				paused = false;
				player.setDelta(player.getPermanentSpeed());
				player.setUpdate(true);

			} else {
				paused = true;
				player.setDelta(0);
				player.setUpdate(false);
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// Nothing

	}

	public boolean iswPressed() {
		return wPressed;
	}

	public boolean issPressed() {
		return sPressed;
	}

	public boolean isaPressed() {
		return aPressed;
	}

	public boolean isdPressed() {
		return dPressed;
	}

	public ArrayList<Fruit> getFruitList() {
		return fruitList;
	}

	public int getCenterX() {
		return getWidth() / 2;
	}

	public int getCenterY() {
		return getHeight() / 2;
	}

	public boolean isPlaying() {
		return playing;
	}

	public void setPlaying(boolean playing) {
		this.playing = playing;
	}

	public void setwPressed(boolean wPressed) {
		this.wPressed = wPressed;
	}

	public void setsPressed(boolean sPressed) {
		this.sPressed = sPressed;
	}

	public void setaPressed(boolean aPressed) {
		this.aPressed = aPressed;
	}

	public void setdPressed(boolean dPressed) {
		this.dPressed = dPressed;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}

}

import java.awt.Color;
import java.awt.Graphics;

public class Fruit {
	GamePanel panel;

	Color color;

	private int x;
	private int y;
	private int size = 15;

	public Fruit(GamePanel panel) {
		this.panel = panel;

		this.x = pickRandomX();
		this.y = pickRandomY();
		checkValidFruitLocations();
		pickRandomColor();

	}

	public int pickRandomX() {
		// random x coordinate in increments of fruit width: 15,30,45 etc
		int xCoord = (int) (Math.random() * (panel.getWidth() / size)) * size;
		// if x is less than window size
		if (xCoord < 0) {
			// position it just to the right of the left window bound
			xCoord += size;
		}
		// if x is greater than window size
		if (xCoord + size > panel.getWidth()) {
			// position it just to the left of the right bound
			xCoord -= size;
		}

		return xCoord;
	}

	public int pickRandomY() {
		// random y coordinate in increments of fruit width: 15,30,45 etc
		int yCoord = (int) (Math.random() * (panel.getHeight() / size)) * size;
		// if less than window top bound
		if (yCoord < 0) {
			// position just below the top bound
			yCoord += size;
		}
		// if greather than the height of window
		if (yCoord + size > panel.getHeight()) {
			// position just above the bottom bound
			yCoord -= size;
		}

		return yCoord;
	}

	public void checkValidFruitLocations() {
		// if the player has atleast one body segment
		if (panel.player.getxCoordList().size() > 0) {
			// loop through body segments
			for (int i = 0; i < panel.player.getxCoordList().size(); i++) {
				// variable to hold segment x coord
				int playerBodyX = panel.player.getxCoordList().get(i);
				// variable to hold segment y coord
				int playerBodyY = panel.player.getyCoordList().get(i);

				// if the fruit xy coordinates are the same as the body segments
				while (x == playerBodyX && y == playerBodyY) {
					// pick new x y coordinates
					int newX = pickRandomX();
					int newY = pickRandomY();

					// System.out.println("existing player segment: old X: " + x
					// + " new X: " + newX + " old y: " + y
					// + " new y: " + newY);
					x = newX;
					y = newY;
				}

			}
		}

		if (panel.getFruitList().size() > 0) {
			for (int i = 0; i < panel.getFruitList().size(); i++) {
				int fruitX = panel.getFruitList().get(i).getX();
				int fruitY = panel.getFruitList().get(i).getY();

				while (this.x == fruitX && this.y == fruitY) {
					int newFruitX = this.pickRandomX();
					int newFruitY = this.pickRandomY();
					// System.out.println(i + " Existing Fruit: X: " + x + " Y:
					// " + y + " newX: " + newFruitX + " newY: "
					// + newFruitY + "fruit: " + panel.getFruitList().size());
					x = newFruitX;
					y = newFruitY;

				}
			}
		}

	}

	public void pickRandomColor() {
		int r = (int) (Math.random() * 255);
		int g = (int) (Math.random() * 255);
		int b = (int) (Math.random() * 255);

		// while color = white
		while (r == 255 && g == 255 && b == 255) {
			System.err.println("Color was white, picking new color");
			pickRandomColor();
		}
		// while color = black
		while (r == 0 && g == 0 && b == 0) {
			System.err.println("Color was black picking new color");
			pickRandomColor();
		}

		color = new Color(r, g, b);

		if (panel.player.getColorList().size() > 0) {
			for (int i = 0; i < panel.player.getColorList().size(); i++) {
				Color iFruitColor = panel.player.getColorList().get(i);
				// if the color already exists on screen
				if (color == iFruitColor) {

					System.out.println("Color already exists, picking new color");
					pickRandomColor();
				}
			}
		}

	}

	public void draw(Graphics g) {

		g.setColor(color);
		g.fillOval(x, y, size, size);

	}

	public int getWidth() {
		return size;
	}

	public int getHeight() {
		return size;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Color getColor() {
		return color;
	}
}

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Player {
	GamePanel panel;
	private int headX;
	private int headY;
	private boolean update = true;

	private int currentBodyCount = 0;

	private ArrayList<Integer> xCoordList = new ArrayList<Integer>();
	private ArrayList<Integer> yCoordList = new ArrayList<Integer>();
	private ArrayList<Color> colorList = new ArrayList<Color>();

	private int width = 15;
	private int height = 15;

	private int permanentSpeed = 15;
	private int delta = 15;

	public Player(GamePanel panel, int xPos, int yPos) {
		this.panel = panel;
		this.headX = xPos;
		this.headY = yPos;

	}

	public void draw(Graphics g) {
		// sort colors

		g.setColor(Color.white);
		g.fillRect(headX, headY, width, height);

		sortColors();

		for (int i = 0; i < xCoordList.size(); i++) {

			g.setColor(colorList.get(i));
			g.fillRect(xCoordList.get(i), yCoordList.get(i), width, height);

		}

	}

	public void sortColors() {
		if (colorList.size() > 0) {
			for (int i = colorList.size() - 1; i > 0; i--) {

				Color currentColor = colorList.get(i);
				Color previousColor = colorList.get(i - 1);

				// if current color is less than previous color
				if (compareColors(currentColor, previousColor) == -1) {

					colorList.set(i, previousColor);
					colorList.set(i - 1, currentColor);

				}

				if (compareColors(currentColor, previousColor) == 1) {
					colorList.set(i, currentColor);
					colorList.set(i - 1, previousColor);
				}
			}
		}
	}

	public int compareColors(Color c1, Color c2) {
		float[] hsb1 = Color.RGBtoHSB(c1.getRed(), c1.getGreen(), c1.getBlue(), null);
		float[] hsb2 = Color.RGBtoHSB(c2.getRed(), c2.getGreen(), c2.getBlue(), null);
		// index 0 = hue
		// index 1 = saturation
		// index 2 = brightness

		// if c1 hue < c2 hue
		if (hsb1[0] < hsb2[0])
			// return less than
			return -1;
		// if c1 hue > c2 hue
		if (hsb1[0] > hsb2[0])
			// return greater than
			return 1;

		// if c1 saturation < c2 saturation
		if (hsb1[1] < hsb2[1])
			// return less than
			return -1;
		// if c1 saturation > c2 saturation
		if (hsb1[1] > hsb2[1])
			// return greater than
			return 1;
		// if c1 brightness < c2 brightness
		if (hsb1[2] < hsb2[2])
			// return less than
			return -1;
		// if c1 brightness > c2 brightness
		if (hsb1[2] > hsb2[2])
			// return greater than
			return 1;

		return 0;
	}

	public void step() {

		if (panel.iswPressed()) {
			if (xCoordList.size() > 0 && yCoordList.size() > 0) {
				if (headX == this.xCoordList.get(0) && (headY - delta == this.yCoordList.get(0))) {
					panel.setwPressed(false);
					panel.setsPressed(true);
					return;
				} else {
					headY -= delta;
				}

			} else {
				headY -= delta;
			}
		}

		else if (panel.issPressed()) {
			if (xCoordList.size() > 0 && yCoordList.size() > 0) {
				if (headX == this.xCoordList.get(0) && (headY + delta == this.yCoordList.get(0))) {
					panel.setsPressed(false);
					panel.setwPressed(true);
					return;
				} else {
					headY += delta;
				}

			} else {
				headY += delta;
			}
		}

		else if (panel.isaPressed()) {
			if (xCoordList.size() > 0 && yCoordList.size() > 0) {
				if (headX - delta == this.xCoordList.get(0) && (headY == this.yCoordList.get(0))) {
					panel.setaPressed(false);
					panel.setdPressed(true);
					return;
				} else {
					headX -= delta;

				}

			} else {
				headX -= delta;
			}
		}

		else if (panel.isdPressed()) {
			if (xCoordList.size() > 0 && yCoordList.size() > 0) {

				if (headX + delta == this.xCoordList.get(0) && (headY == this.yCoordList.get(0))) {
					panel.setdPressed(false);
					panel.setaPressed(true);
					return;
				} else {
					headX += delta;
				}

			} else {
				headX += delta;
			}
		}

		// check for collisions
		checkFruitCollision();
		checkScreenCollision();
		checkSelfCollision();

		if (update) {
			updateCoordList();
		}

	}

	public void grow() {
		currentBodyCount += 1;

	}

	public void updateCoordList() {

		if (currentBodyCount > 0) {
			if (xCoordList.size() < currentBodyCount || yCoordList.size() < currentBodyCount) {

				if (panel.iswPressed()) {
					xCoordList.add(0, headX);
					yCoordList.add(0, headY + delta);
				}
				if (panel.issPressed()) {
					xCoordList.add(0, headX);
					yCoordList.add(0, headY - delta);
				}
				if (panel.isaPressed()) {
					xCoordList.add(0, headX + delta);
					yCoordList.add(0, headY);
				}
				if (panel.isdPressed()) {
					xCoordList.add(0, headX - delta);
					yCoordList.add(0, headY);
				}

			}

			else if (xCoordList.size() == currentBodyCount && yCoordList.size() == currentBodyCount) {

				if (panel.iswPressed()) {
					xCoordList.add(0, headX);
					yCoordList.add(0, headY + delta);
				}
				if (panel.issPressed()) {
					xCoordList.add(0, headX);
					yCoordList.add(0, headY - delta);
				}
				if (panel.isaPressed()) {
					xCoordList.add(0, headX + delta);
					yCoordList.add(0, headY);
				}
				if (panel.isdPressed()) {
					xCoordList.add(0, headX - delta);
					yCoordList.add(0, headY);
				}

				xCoordList.remove(currentBodyCount);
				yCoordList.remove(currentBodyCount);

			}

		}

	}

	public void checkSelfCollision() {

		int headDx = headX + width;
		int headDy = headY + height;

		int hCenterX = (headX + headDx) / 2;
		int hCenterY = (headY + headDy) / 2;

		for (int i = 0; i < xCoordList.size(); i++) {
			int bodyX = xCoordList.get(i);
			int bodyDx = xCoordList.get(i) + width;
			int bodyY = yCoordList.get(i);
			int bodyDy = yCoordList.get(i) + height;

			if (bodyX <= hCenterX && hCenterX <= bodyDx && bodyY <= hCenterY && hCenterY <= bodyDy) {

				panel.setGameOver(true);
			}
		}

	}

	public void checkScreenCollision() {

		int playerTop = headY;
		int playerBottom = headY + height;
		int playerLeft = headX;
		int playerRight = headX + width;
		// if the player hits the edge of the screen
		if (playerTop < 0 || playerBottom > panel.getHeight() || playerLeft < 0 || playerRight > panel.getWidth()) {

			panel.setGameOver(true);
		}
	}

	public void checkFruitCollision() {

		int playerTop = headY;
		int playerBottom = headY + height;
		int playerLeft = headX;
		int playerRight = headX + width;

		if (panel.getFruitList().size() > 0) {
			for (int i = 0; i < panel.getFruitList().size(); i++) {
				int fruitLeft = panel.getFruitList().get(i).getX();
				int fruitRight = panel.getFruitList().get(i).getX() + panel.getFruitList().get(i).getWidth();

				int fruitTop = panel.getFruitList().get(i).getY();
				int fruitBottom = panel.getFruitList().get(i).getY() + panel.getFruitList().get(i).getHeight();

				int fruitCenterX = (fruitLeft + fruitRight) / 2;
				int fruitCenterY = (fruitTop + fruitBottom) / 2;

				// if the player collides with fruit
				if (fruitCenterX <= playerRight && fruitCenterY >= playerTop && fruitCenterY <= playerBottom
						&& fruitCenterX >= playerLeft) {

					// remove that fruit
					colorList.add(panel.getFruitList().get(i).getColor());
					panel.getFruitList().remove(i);
					// grow the tail of the palyer
					grow();
					// add new fruit to the screen
					panel.getFruitList().add(new Fruit(panel));
				}

			}
		}

	}

	public int getHeadX() {
		return headX;
	}

	public int getHeadY() {
		return headY;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeadX(int x) {
		headX = x;
	}

	public void setHeadY(int y) {
		headY = y;
	}

	public int getBodyCount() {
		return currentBodyCount;
	}

	public void setCurrentBodyCount(int x) {
		currentBodyCount = x;
	}

	public ArrayList<Integer> getxCoordList() {
		return xCoordList;
	}

	public ArrayList<Integer> getyCoordList() {
		return yCoordList;
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	public int getPermanentSpeed() {
		return permanentSpeed;
	}

	public void setPermanentSpeed(int permanentSpeed) {
		this.permanentSpeed = permanentSpeed;
	}

	public int getDelta() {
		return delta;
	}

	public void setDelta(int delta) {
		this.delta = delta;
	}

	public ArrayList<Color> getColorList() {
		return colorList;
	}

	public void setColorList(ArrayList<Color> colorList) {
		this.colorList = colorList;
	}

}

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Paused {
	GamePanel panel;
	Color textColor = new Color(221, 191, 0);

	int count = 0;
	int min = 5;
	int max = 10;

	public Paused(GamePanel panel) {
		this.panel = panel;

	}

	public void draw(Graphics g) {
		g.setColor(textColor);
		g.setFont(new Font("TimesRoman", Font.BOLD, 13));
		g.drawString("PAUSED", panel.getWidth() - 75, 15);
		g.fillRect(panel.getWidth() - 20, 6, 3, 9);
		g.fillRect(panel.getWidth() - 14, 6, 3, 9);

		g.fillRect(0, 0, 2, panel.getHeight());
		g.fillRect(0, 0, panel.getWidth(), 2);
		g.fillRect(0, panel.getHeight() - 2, panel.getWidth(), 2);
		g.fillRect(panel.getWidth() - 2, 0, 2, panel.getHeight());
	}

	public void step() {
		count++;
		if (count < min) {
			textColor = new Color(221, 191, 0);

		}

		else if (count > min && count <= max) {
			textColor = Color.BLACK;
			if (count >= max) {
				count = 0;
			}
		}

	}

}
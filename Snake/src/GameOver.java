import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GameOver implements MouseListener{
	GamePanel panel;
	BufferedImage exitButton;
	BufferedImage retryButton;
	int gameOverX;
	int gameOverY;
	int finalScoreX;
	int finalScoreY;
	int exitButtonX;
	int exitButtonY;
	int retryButtonX;
	int retryButtonY;
	int count = 0;
	int min = 5;
	int max = 10;

	Color gameOverTextColor = new Color(181, 0, 0);
	Color finalScoreTextColor = new Color(181, 0, 0);

	Color backgroundColor = Color.black;

	public GameOver(GamePanel panel) {
		this.panel = panel;
		
		gameOverX = (panel.getWidth() / 2) - 100;
		gameOverY = panel.getHeight() / 2;
		finalScoreX = (panel.getWidth() / 2) - 40;
		finalScoreY = (panel.getHeight() / 2) + 40;

		exitButtonX = (panel.getWidth() / 2) - 95;
		exitButtonY = (panel.getHeight() / 2) + 70;

		retryButtonX = (panel.getWidth() / 2) + 35;
		retryButtonY = (panel.getHeight() / 2) + 70;

		try {

			exitButton = ImageIO.read(new File("./gui/EXIT_BUTTON_65x25.png"));
			retryButton = ImageIO.read(new File("./gui/RETRY_BUTTON_65x25.png"));
		} catch (IOException e) {
		}

	}

	public void draw(Graphics g) {
		panel.setBackground(backgroundColor);
		g.setColor(gameOverTextColor);

		g.setFont(new Font("TimesRoman", Font.BOLD, 32));
		g.drawString("GAME OVER", gameOverX, gameOverY);

		g.setColor(finalScoreTextColor);
		g.setFont(new Font("TimesRoman", Font.BOLD, 14));
		g.drawString("Final Score: " + (panel.player.getBodyCount() + 1), finalScoreX, finalScoreY);

		// EXIT

		g.drawImage(exitButton, exitButtonX, exitButtonY, null, null);

		// RESTART

		g.drawImage(retryButton, retryButtonX, retryButtonY, null, null);

	}

	public void step() {
		applyAnimation();
		handleButtonPress();
		//addMouseListener();

	}

	public void updateTextFile() {
		panel.getScore().writeTextFile(Integer.toString(panel.player.getBodyCount() + 1));
	}
	
	
	public void applyAnimation(){
		count++;
		if (count < min) {

			finalScoreTextColor = new Color(181, 0, 0);
		}

		else if (count > min && count <= max) {
			finalScoreTextColor = backgroundColor;
			if (count >= max) {
				count = 0;
			}
		}
	}
	public void handleButtonPress(){
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println(e.getX());
		System.out.println(e.getY());
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}

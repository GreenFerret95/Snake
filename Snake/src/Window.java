
/*
 * TODO:
 * [X ] if fruit already exist at those coordinates pick new coordinate
 * [ ] after game ends offer an option to restart the game
 * [ ] save scores to a text file
 * [ ] read high scores from a text file and paint it to screen next to current length
 * [ ] when the game is over all the user to type a name for the score
 * [ ] display scores from highest to lowest showing names in a end credit type fashion
 * 
 * [ ] give functionality to exit and retry button
 * [ ] when user presses exit update the highscores text file
 * [ ] when user presses retry update the highscores text file
 */

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends JFrame {

	private static final int FACTOR = 23;
	private static final int WIDTH = 15 * FACTOR;
	private static final int HEIGHT = 15 * FACTOR;

	public static void main(String[] args) {
		Window w = new Window();

		GamePanel panel = new GamePanel();

		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));

		w.setTitle("Snake Game");
		w.setResizable(false);
		w.setDefaultCloseOperation(EXIT_ON_CLOSE);
		w.setLayout(new BorderLayout());
		w.add(panel, BorderLayout.CENTER);

		w.pack();
		w.setLocationRelativeTo(null);
		w.setVisible(true);

		panel.init();

	}
}

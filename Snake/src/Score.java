import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class Score {
	private GamePanel panel;
	BufferedWriter writer = null;
	String filePath = "./misc/score.txt";

	ArrayList<String> stringList = new ArrayList<String>();

	public Score(GamePanel panel) {
		this.panel = panel;
	}

	public void writeTextFile(String s) {
		try {
			File textFile = new File(filePath);
			System.out.println(textFile.getCanonicalPath());

			writer = new BufferedWriter(new FileWriter(textFile,true));
			

			writer.append(s + "\n");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (Exception e) {

			}
		}
	}

	public void readTextFile() {

	}
}

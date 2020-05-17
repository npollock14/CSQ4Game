import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Misc {
	
	static Font f = new Font("Press Start", 0, 48);
	static Font fBig = new Font("Press Start", 0, 82);
	static Font font = new Font("Courier New", 1, 25);
	static Font titleFont = new Font("Dialog", 3, 50);
	static Font smallTitleFont = new Font("Dialog", 3, 30);
	static Font smallestTitleFont = new Font("Dialog", 1, 17);
	static Font fpsFont = new Font("Impact", 1, 25);
	static Font fancyTitleFont = new Font("TimesRoman", 3, 82);
	static Color darkGreen = new Color(0,102,0);

	public static BufferedImage loadImage(String path) {
		try {
			return ImageIO.read(Misc.class.getResource(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1); // if image is not loaded dont run game
		}
		return null;
	}
	public static String[] read(String location) {
		String[] text = null;
		String line = null;
		int i = 0;
		// The name of the file to open.
		String fileLocation = location;
		// This will reference one line at a time
		try {
			// FileReader reads text files in the default encoding.
			FileReader linesCounter = new FileReader(fileLocation);

			// Always wrap FileReader in BufferedReader.
			BufferedReader lineCounter = new BufferedReader(linesCounter);
			while ((line = lineCounter.readLine()) != null) {
				i++;
			}
			lineCounter.close();
			FileReader fileReader = new FileReader(fileLocation);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			text = new String[i];
			i = 0;
			while ((line = bufferedReader.readLine()) != null) {
				text[i] = line;
				i++;
			}
			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to find file '" + fileLocation + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileLocation + "'");
			// Or we could just do this:
			// ex.printStackTrace();
		}
		// String fullText = String.join(" ", text);
		return (text);
	}
	public static void writeToFile(String path, String text) {
		// writes directly to file & will replace all previous text there
		try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))) {
			bufferedWriter.write(text);
		} catch (IOException e) {
			System.out.println("Error: IO Exception");
		}
	}
	public static int randSign() {
		return (Math.random() > .5 ? 1 : -1);
	}
	public static int rBt(int min, int max) {
		return ((int) (Math.random() * (max - min + 1) + min));
	}
	
	public static void drawDashedLine(Graphics g, int x1, int y1, int x2, int y2){

        //creates a copy of the Graphics instance
        Graphics2D g2d = (Graphics2D) g.create();

        //set the stroke of the copy, not the original 
        Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        g2d.setStroke(dashed);
        g2d.drawLine(x1, y1, x2, y2);

        //gets rid of the copy
        g2d.dispose();
}
}

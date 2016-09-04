package cave.fighter.utilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public final class Assets {

	public static void init() {

		try {
			// Menu images setup
			menu = ImageIO.read(MENU);
			selection = ImageIO.read(SELECTION);
			htp = ImageIO.read(HTP);
			pointer1 = ImageIO.read(POINTER_1);
			pointer2 = ImageIO.read(POINTER_2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static BufferedImage menu;
	public static BufferedImage selection;
	public static BufferedImage htp;
	public static BufferedImage pointer1;
	public static BufferedImage pointer2;

	// Menu constants
	private static final String MENU_URL = "./resources/menu1.png";
	private static final File MENU = new File(MENU_URL);
	private static final String SELECTION_URL = "./resources/menu2.png";
	private static final File SELECTION = new File(SELECTION_URL);
	private static final String HTP_URL = "./resources/howtoplay.png";
	private static final File HTP = new File(HTP_URL);
	private static final String POINTER_1_URL = "./resources/pointer0.png";
	private static final File POINTER_1 = new File(POINTER_1_URL);
	private static final String POINTER_2_URL = "./resources/pointer1.png";
	private static final File POINTER_2 = new File(POINTER_2_URL);
	
	private Assets() 
	{/* Utility Class */}

}

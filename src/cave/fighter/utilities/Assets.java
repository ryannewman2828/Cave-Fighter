package cave.fighter.utilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import animation.framework.Animation;

public final class Assets {

	public static void init() {

		bodyAnimation = new Animation[4];
		headAnimation = new Animation[4];
		damageBuffer = new Animation();

		for (int i = 0; i < 4; i++) {
			bodyAnimation[i] = new Animation();
			headAnimation[i] = new Animation();
		}

		try {

			// Menu images setup
			menu = ImageIO.read(new File(MENU_URL));
			selection = ImageIO.read(new File(SELECTION_URL));
			htp = ImageIO.read(new File(HTP_URL));
			pointer1 = ImageIO.read(new File(POINTER_1_URL));
			pointer2 = ImageIO.read(new File(POINTER_2_URL));

			// Character image setup
			damageBuffer.addFrame(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), 1);
			for (int i = 0; i < 3; i++) {
				bodyAnimation[0].addFrame(ImageIO.read(new File(String.format(BODY_ANIMATION_FRONT_URL, i))), 100);
				bodyAnimation[1].addFrame(ImageIO.read(new File(String.format(BODY_ANIMATION_LEFT_URL, i))), 100);
				bodyAnimation[2].addFrame(ImageIO.read(new File(String.format(BODY_ANIMATION_RIGHT_URL, i))), 100);
				bodyAnimation[3].addFrame(ImageIO.read(new File(String.format(BODY_ANIMATION_BACK_URL, i))), 100);
			}
			bodyAnimation[0].addFrame(ImageIO.read(new File(String.format(BODY_ANIMATION_FRONT_URL, 1))), 100);
			bodyAnimation[1].addFrame(ImageIO.read(new File(String.format(BODY_ANIMATION_LEFT_URL, 1))), 100);
			bodyAnimation[2].addFrame(ImageIO.read(new File(String.format(BODY_ANIMATION_RIGHT_URL, 1))), 100);
			bodyAnimation[3].addFrame(ImageIO.read(new File(String.format(BODY_ANIMATION_BACK_URL, 1))),100);
			bodyImage = ImageIO.read(new File(String.format(BODY_ANIMATION_FRONT_URL, 1)));
			for (int i = 0; i < 8; i += 2) {
				headAnimation[i / 2].addFrame(ImageIO.read(new File(String.format(FACE_ANIMATION_URL, i))), 100);
				headAnimation[i / 2].addFrame(ImageIO.read(new File(String.format(FACE_ANIMATION_URL, i + 1))), 100);
			}
			headImage = ImageIO.read(new File(String.format(FACE_ANIMATION_URL, 0)));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static BufferedImage menu;
	public static BufferedImage selection;
	public static BufferedImage htp;
	public static BufferedImage pointer1;
	public static BufferedImage pointer2;

	public static BufferedImage headImage; 
	public static BufferedImage bodyImage;
	public static Animation[] bodyAnimation;
	public static Animation[] headAnimation;
	public static Animation damageBuffer;


	// Menu constants
	private static final String MENU_URL = "./resources/menu1.png";
	private static final String SELECTION_URL = "./resources/menu2.png";
	private static final String HTP_URL = "./resources/howtoplay.png";
	private static final String POINTER_1_URL = "./resources/pointer0.png";
	private static final String POINTER_2_URL = "./resources/pointer1.png";
	
	// Character constants
	private static final String BODY_ANIMATION_FRONT_URL = "./resources/front%d.png";
	private static final String BODY_ANIMATION_LEFT_URL = "./resources/left%d.png";
	private static final String BODY_ANIMATION_RIGHT_URL = "./resources/right%d.png";
	private static final String BODY_ANIMATION_BACK_URL = "./resources/back%d.png";
	private static final String FACE_ANIMATION_URL = "./resources/face%d.png";

	private Assets() {/* Utility Class */
	}

}

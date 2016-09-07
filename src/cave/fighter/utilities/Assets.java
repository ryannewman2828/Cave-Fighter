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

			// GamePanel image setup
			walls = ImageIO.read(new File(WALLS_URL));
			fullHeart = ImageIO.read(new File(FULL_HEART_URL));
			halfHeart = ImageIO.read(new File(HALF_HEART_URL));
			emptyHeart = ImageIO.read(new File(EMPTY_HEART_URL));
			dead = ImageIO.read(new File(DEAD_URL));
			gameOver = ImageIO.read(new File(GAME_OVER_URL));
			
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
			
			// Room image setup
			roomImage = new BufferedImage(800, 480, BufferedImage.TYPE_INT_ARGB);
			background = ImageIO.read(new File(BACKGROUND_URL));
			rightDoor = ImageIO.read(new File(RIGHT_DOOR_URL));
			leftDoor = ImageIO.read(new File(LEFT_DOOR_URL));
			downDoor = ImageIO.read(new File(DOWN_DOOR_URL));
			upDoorOn = ImageIO.read(new File(UP_DOOR_ON_URL));
			upDoorOff = ImageIO.read(new File(UP_DOOR_OFF_URL));
			bossDoor = ImageIO.read(new File(BOSS_DOOR_URL));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static BufferedImage menu;
	public static BufferedImage selection;
	public static BufferedImage htp;
	public static BufferedImage pointer1;
	public static BufferedImage pointer2;
	
	public static BufferedImage walls;
	public static BufferedImage fullHeart;
	public static BufferedImage halfHeart;
	public static BufferedImage emptyHeart;
	public static BufferedImage dead;
	public static BufferedImage gameOver;

	public static BufferedImage headImage; 
	public static BufferedImage bodyImage;
	public static Animation[] bodyAnimation;
	public static Animation[] headAnimation;
	public static Animation damageBuffer;
	
	public static BufferedImage roomImage;
	public static BufferedImage background;
	public static BufferedImage rightDoor;
	public static BufferedImage leftDoor;
	public static BufferedImage downDoor;
	public static BufferedImage upDoorOn;
	public static BufferedImage upDoorOff;
	public static BufferedImage bossDoor;
	
	

	// Menu constants
	private static final String MENU_URL = "./resources/menu1.png";
	private static final String SELECTION_URL = "./resources/menu2.png";
	private static final String HTP_URL = "./resources/howtoplay.png";
	private static final String POINTER_1_URL = "./resources/pointer0.png";
	private static final String POINTER_2_URL = "./resources/pointer1.png";
	
	// Game constants
	private static final String WALLS_URL = "./resources/walls.png";
	private static final String FULL_HEART_URL = "./resources/heart2.png";
	private static final String HALF_HEART_URL = "./resources/heart1.png";
	private static final String EMPTY_HEART_URL = "./resources/heart0.png";
	private static final String DEAD_URL = "./resources/defeat.png";
	private static final String GAME_OVER_URL = "./resources/victory.png";
	
	// Character constants
	private static final String BODY_ANIMATION_FRONT_URL = "./resources/front%d.png";
	private static final String BODY_ANIMATION_LEFT_URL = "./resources/left%d.png";
	private static final String BODY_ANIMATION_RIGHT_URL = "./resources/right%d.png";
	private static final String BODY_ANIMATION_BACK_URL = "./resources/back%d.png";
	private static final String FACE_ANIMATION_URL = "./resources/face%d.png";
	
	// Room constants
	private static final String BACKGROUND_URL = "./resources/background.png";
	private static final String RIGHT_DOOR_URL = "./resources/rightDoor.png";
	private static final String LEFT_DOOR_URL = "./resources/leftDoor.png";
	private static final String DOWN_DOOR_URL = "./resources/downDoor.png";
	private static final String UP_DOOR_ON_URL = "./resources/upDoorOn.png";
	private static final String UP_DOOR_OFF_URL = "./resources/upDoorOff.png";
	private static final String BOSS_DOOR_URL = "./resources/bossDoor.png";

	private Assets() 
	{/* Utility Class */}

}

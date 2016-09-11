package cave.fighter.utilities;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import cave.fighter.animation.framework.Animation;

public final class Assets {

	public static void init() {

		bodyAnimation = new Animation[4];
		headAnimation = new Animation[4];
		damageBuffer = new Animation();
		powerUp = new BufferedImage[5];
		bulletAnimation = new Animation[4];
		greenSlimeAnim = new Animation();
		blueSlimeAnim = new Animation();
		redSlimeAnim = new Animation();
		yellowSlimeAnim = new Animation();
		mageBossAnim = new Animation();
		archMageBossAnim = new Animation();
		shadeAnim = new Animation();
		shadeSpawn = new Animation();
		shadeHide = new Animation();

		for (int i = 0; i < 4; i++) {
			bodyAnimation[i] = new Animation();
			headAnimation[i] = new Animation();
			bulletAnimation[i] = new Animation();
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
			damageBuffer.addFrame(new BufferedImage(1, 1,
					BufferedImage.TYPE_INT_ARGB), 1);
			for (int i = 0; i < 3; i++) {
				bodyAnimation[0].addFrame(ImageIO.read(new File(String.format(
						BODY_ANIMATION_FRONT_URL, i))), 100);
				bodyAnimation[1].addFrame(ImageIO.read(new File(String.format(
						BODY_ANIMATION_LEFT_URL, i))), 100);
				bodyAnimation[2].addFrame(ImageIO.read(new File(String.format(
						BODY_ANIMATION_RIGHT_URL, i))), 100);
				bodyAnimation[3].addFrame(ImageIO.read(new File(String.format(
						BODY_ANIMATION_BACK_URL, i))), 100);
			}
			bodyAnimation[0].addFrame(ImageIO.read(new File(String.format(
					BODY_ANIMATION_FRONT_URL, 1))), 100);
			bodyAnimation[1].addFrame(ImageIO.read(new File(String.format(
					BODY_ANIMATION_LEFT_URL, 1))), 100);
			bodyAnimation[2].addFrame(ImageIO.read(new File(String.format(
					BODY_ANIMATION_RIGHT_URL, 1))), 100);
			bodyAnimation[3].addFrame(ImageIO.read(new File(String.format(
					BODY_ANIMATION_BACK_URL, 1))), 100);
			bodyImage = ImageIO.read(new File(String.format(
					BODY_ANIMATION_FRONT_URL, 1)));
			for (int i = 0; i < 8; i += 2) {
				headAnimation[i / 2].addFrame(ImageIO.read(new File(String
						.format(FACE_ANIMATION_URL, i))), 100);
				headAnimation[i / 2].addFrame(ImageIO.read(new File(String
						.format(FACE_ANIMATION_URL, i + 1))), 100);
			}
			headImage = ImageIO.read(new File(String.format(FACE_ANIMATION_URL,
					0)));

			// Room image setup
			roomImage = new BufferedImage(800, 480, BufferedImage.TYPE_INT_ARGB);
			background = ImageIO.read(new File(BACKGROUND_URL));
			rightDoor = ImageIO.read(new File(RIGHT_DOOR_URL));
			leftDoor = ImageIO.read(new File(LEFT_DOOR_URL));
			downDoor = ImageIO.read(new File(DOWN_DOOR_URL));
			upDoorOn = ImageIO.read(new File(UP_DOOR_ON_URL));
			upDoorOff = ImageIO.read(new File(UP_DOOR_OFF_URL));
			bossDoor = ImageIO.read(new File(BOSS_DOOR_URL));
			for (int i = 0; i < powerUp.length; i++) {
				powerUp[i] = ImageIO.read(new File(String.format(POWER_UP_URL,
						i)));
			}

			// Projectile image setup
			for (int i = 0; i < 4; i++) {
				bulletAnimation[0].addFrame(
						ImageIO.read(new File(String.format(BULLET_URL, i))),
						100);
				image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
				g2d = (Graphics2D) image.getGraphics();
				g2d.drawImage(
						ImageIO.read(new File(String.format(BULLET_URL, i))),
						AffineTransform.getRotateInstance(Math.PI, 16, 16),
						null);
				bulletAnimation[1].addFrame(image, 100);
				image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
				g2d = (Graphics2D) image.getGraphics();
				g2d.drawImage(
						ImageIO.read(new File(String.format(BULLET_URL, i))),
						AffineTransform.getRotateInstance(Math.PI / 2, 16, 16),
						null);
				bulletAnimation[2].addFrame(image, 100);
				image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
				g2d = (Graphics2D) image.getGraphics();
				g2d.drawImage(ImageIO.read(new File(String
						.format(BULLET_URL, i))), AffineTransform
						.getRotateInstance(3 * Math.PI / 2, 16, 16), null);
				bulletAnimation[3].addFrame(image, 100);
			}

			// Enemy image setup
			for (int i = 0; i < 3; i++) {
				greenSlimeAnim.addFrame(ImageIO.read(new File(String.format(
						GREEN_SLIME_URL, i))), 100);
				blueSlimeAnim.addFrame(ImageIO.read(new File(String.format(
						BLUE_SLIME_URL, i))), 100);
				redSlimeAnim
						.addFrame(ImageIO.read(new File(String.format(
								RED_SLIME_URL, i))), 100);
				yellowSlimeAnim.addFrame(ImageIO.read(new File(String.format(
						YELLOW_SLIME_URL, i))), 100);
			}
			greenSlimeAnim.addFrame(
					ImageIO.read(new File(String.format(GREEN_SLIME_URL, 1))),
					100);
			greenSlime = ImageIO.read(new File(String
					.format(GREEN_SLIME_URL, 0)));
			blueSlimeAnim.addFrame(
					ImageIO.read(new File(String.format(BLUE_SLIME_URL, 1))),
					100);
			blueSlime = ImageIO
					.read(new File(String.format(BLUE_SLIME_URL, 0)));
			redSlimeAnim.addFrame(
					ImageIO.read(new File(String.format(RED_SLIME_URL, 1))),
					100);
			redSlime = ImageIO.read(new File(String.format(RED_SLIME_URL, 0)));
			yellowSlimeAnim.addFrame(
					ImageIO.read(new File(String.format(YELLOW_SLIME_URL, 1))),
					100);
			yellowSlime = ImageIO.read(new File(String.format(YELLOW_SLIME_URL,
					0)));

			// Boss image setup
			for (int i = 0; i < 8; i++) {
				mageBossAnim
						.addFrame(ImageIO.read(new File(String.format(
								MAGE_BOSS_URL, i))), 100);
			}
			for (int i = 6; i >= 0; i--) {
				mageBossAnim
						.addFrame(ImageIO.read(new File(String.format(
								MAGE_BOSS_URL, i))), 100);
			}
			mageBoss = ImageIO.read(new File(String.format(MAGE_BOSS_URL, 0)));
			mageBossAnim.addFrame(mageBoss, 100);
			for (int i = 0; i < 8; i++) {
				archMageBossAnim.addFrame(ImageIO.read(new File(String.format(ARCH_MAGE_BOSS_URL, i))), 100);
			}
			for (int i = 6; i >= 0; i--) {
				archMageBossAnim.addFrame(ImageIO.read(new File(String.format(ARCH_MAGE_BOSS_URL, i))), 100);
			}
			archMageBoss = ImageIO.read(new File(String.format(ARCH_MAGE_BOSS_URL, 0)));
			archMageBossAnim.addFrame(archMageBoss, 100);
			for (int i = 0; i < 4; i++) {
				shadeAnim.addFrame(ImageIO.read(new File(String.format(SHADE_BOSS_URL, i))), 100);
			}
			for (int i = 2; i >= 0; i--) {
				shadeAnim.addFrame(ImageIO.read(new File(String.format(SHADE_BOSS_URL, i))), 100);
			}
			for (int i = 0; i < 10; i++) {
				shadeSpawn.addFrame(ImageIO.read(new File(String.format(SHADE_SPAWN_URL, i))), 100);
			}
			for (int i = 9; i >= 0; i--) {
				shadeHide.addFrame(ImageIO.read(new File(String.format(SHADE_SPAWN_URL, i))), 100);
			}
			shade = ImageIO.read(new File(String.format(SHADE_SPAWN_URL, 0)));
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
	public static BufferedImage[] powerUp;

	public static Animation[] bulletAnimation;

	public static BufferedImage greenSlime;
	public static Animation greenSlimeAnim;
	public static BufferedImage blueSlime;
	public static Animation blueSlimeAnim;
	public static BufferedImage redSlime;
	public static Animation redSlimeAnim;
	public static BufferedImage yellowSlime;
	public static Animation yellowSlimeAnim;

	public static BufferedImage mageBoss;
	public static Animation mageBossAnim;
	public static BufferedImage archMageBoss;
	public static Animation archMageBossAnim;
	public static BufferedImage shade;
	public static Animation shadeAnim;
	public static Animation shadeSpawn;
	public static Animation shadeHide;

	private static Graphics2D g2d;
	private static BufferedImage image;

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
	private static final String POWER_UP_URL = "./resources/icon%d.png";

	// Bullets constants
	private static final String BULLET_URL = "./resources/fire%d.png";

	// Enemy constants
	private static final String GREEN_SLIME_URL = "./resources/slime%d.png";
	private static final String BLUE_SLIME_URL = "./resources/bslime%d.png";
	private static final String RED_SLIME_URL = "./resources/rslime%d.png";
	private static final String YELLOW_SLIME_URL = "./resources/yslime%d.png";
	private static final String TENTACLE_URL = "./resources/tentacle%d.png";
	private static final String TENTACLE_ENTRANCE_URL = "./resources/tentacle%d.png";

	// Boss constants
	private static final String MAGE_BOSS_URL = "./resources/mage%d.png";
	private static final String ARCH_MAGE_BOSS_URL = "./resources/amage%d.png";
	private static final String SHADE_BOSS_URL = "./resources/shade%d.png";
	private static final String SHADE_SPAWN_URL = "./resources/spawn%d.png";

	private Assets() 
	{/* Utility Class */}

}

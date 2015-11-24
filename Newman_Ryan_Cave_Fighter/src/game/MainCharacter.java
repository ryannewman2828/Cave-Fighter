/**
 * Ryan Newman
 * ICS4U - 1
 * Cave Fighter
 * June 10 2015 
*/
package game;

import game.GamePanel.attackStates;
import game.GamePanel.gameStates;
import game.GamePanel.moveStates;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import animation.framework.Animation;

public class MainCharacter {

	// health the character has when health is full
	private int health = 6;

	// the current health
	private int curHealth = health;

	// 5 hearts at maximum
	private final int MAX_HEALTH = 10;

	// pixels / 60th of a second
	private int speed = 3;

	// The maximum speed the character can reach
	private final int MAX_SPEED = 6;

	// The maximum rate the character can shoot at
	private final int MAX_FIRE_RATE = 10;

	// The current rate the character can shot at
	private int fireRate = 25;

	// The speed of the fired bullet
	private int bulletSpeed = 4;

	// holds the co-ordinates of the characters
	// initialized to middle of map
	private int x = 400;
	private int y = 240;

	// the current time since the last projectile was fired
	private int fireCounter = 0;

	//When can I take damage again?
	private int damageCounter = 0;

	//Am i reasy to fire a bulet?
	private boolean fireReady = false;

	// controls how long the head will stay looking in the attack direction
	private int headCounter = 0;

	// the damage of the character
	private int damage = 1;

	// the max damage the player can do
	private final int MAX_DAMAGE = 5;

	//The index for what head/body to draw
	private int animationIndexHead = 0;
	private int animationIndexBody = 0;

	//Holds the projectiles
	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

	// The current attack state
	public static attackStates attack = attackStates.STATIC;

	// HitBoxes
	public static Rectangle rect = new Rectangle(0, 0, 0, 0);
	public static Rectangle rect2 = new Rectangle(0, 0, 0, 0);

	//Images/Animations
	private Image headImage, bodyImage;
	private Animation[] bodyAnimation = new Animation[4];
	private Animation[] headAnimation = new Animation[4];
	private Animation damageBuffer;

	public MainCharacter() {

		// Animation Setups
		for (int i = 0; i < 4; i++) {
			bodyAnimation[i] = new Animation();
			headAnimation[i] = new Animation();
		}
		
		//The animation for the damage buffer
		damageBuffer = new Animation();
		damageBuffer.addFrame(new BufferedImage(1, 1,
				BufferedImage.TYPE_INT_ARGB), 1);
		
		//Image setups
		try {
			for (int i = 0; i < 3; i++) {
				headImage = ImageIO.read(new File("data/front" + i + ".png"));
				bodyAnimation[0].addFrame(headImage, 100);
				headImage = ImageIO.read(new File("data/left" + i + ".png"));
				bodyAnimation[1].addFrame(headImage, 100);
				headImage = ImageIO.read(new File("data/right" + i + ".png"));
				bodyAnimation[2].addFrame(headImage, 100);
				headImage = ImageIO.read(new File("data/back" + i + ".png"));
				bodyAnimation[3].addFrame(headImage, 100);
			}
			bodyAnimation[0].addFrame(
					ImageIO.read(new File("data/front1.png")), 100);
			bodyAnimation[1].addFrame(ImageIO.read(new File("data/left1.png")),
					100);
			bodyAnimation[2].addFrame(
					ImageIO.read(new File("data/right1.png")), 100);
			bodyAnimation[3].addFrame(ImageIO.read(new File("data/back1.png")),
					100);
			bodyImage = ImageIO.read(new File("data/front1.png"));
			for (int i = 0; i < 8; i += 2) {
				headImage = ImageIO.read(new File("data/face" + i + ".png"));
				headAnimation[i / 2].addFrame(headImage, 100);
				headImage = ImageIO.read(new File("data/face" + (i + 1)
						+ ".png"));
				headAnimation[i / 2].addFrame(headImage, 100);
			}
			headImage = ImageIO.read(new File("data/face0.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update() {

		// Updates position of the hitBoxes
		rect.setRect(x - 15, y - 9, 29, 18);
		rect2.setRect(x - 12, y - 23, 24, 22);

		// Handles the delay between shots from the character
		if (!fireReady) {
			fireCounter++;
			if (fireCounter == fireRate) {
				fireReady = true;
				fireCounter = 0;
			}
		}

		// Decrements the counter for the head
		if (headCounter > 0) {
			headCounter--;
		} else {
			
			// if counter == 0 then no attack state
			attack = attackStates.STATIC;
		}

		//Decrements damage counter
		if (damageCounter > 0) {
			damageCounter--;
		}

		//Fires depending on attack state and if fire ready
		switch (attack) {
		case DOWN:
			animationIndexHead = 0;
			if (fireReady)
				shoot(0, bulletSpeed);
			break;
		case LEFT:
			animationIndexHead = 1;
			if (fireReady)
				shoot(-bulletSpeed, 0);
			break;
		case RIGHT:
			animationIndexHead = 2;
			if (fireReady)
				shoot(bulletSpeed, 0);
			break;
		case STATIC:
			break;
		case UP:
			animationIndexHead = 3;
			if (fireReady)
				shoot(0, -bulletSpeed);
			break;
		default:
			break;
		}
		
		
		//Updates/removes projectiles
		for (int i = 0; i < projectiles.size(); i++) {
			if (projectiles.get(i).isActive()) {
				projectiles.get(i).update();
			} else {
				projectiles.remove(i);
			}
		}
	}

	// method for shooting the bullets
	private void shoot(int speedX, int speedY) {
		Projectile p = new Projectile(x, y - 12, speedX, speedY);
		projectiles.add(p);
		fireReady = false;
	}

	//Character gets damaged
	public void getHurt(int damage) {
		if (damageCounter == 0) {
			damageCounter = 120;
			hurt(damage);
		}
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getCurHealth() {
		return curHealth;
	}

	public void setCurHealth(int curHealth) {
		this.curHealth = curHealth;
	}

	public int getMaxHealth() {
		return MAX_HEALTH;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	//Character moves
	public void move(int xSpeed, int ySpeed) {
		x += xSpeed;
		y += ySpeed;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

	public void increaseSpeed() {
		if (speed < MAX_SPEED) {
			speed++;
			bulletSpeed++;
		}
	}

	public void increaseDamage() {
		if (damage < MAX_DAMAGE) {
			damage++;
		}
	}

	public void increaseHealth() {
		if (health < MAX_HEALTH) {
			health += 2;
		}
		heal();
	}

	public void heal() {
		// Heals the player for 1 heart or 2 half hearts
		if (curHealth + 2 <= health) {
			curHealth += 2;
		} else {
			curHealth = health;
		}
	}

	public void hurt(int damage) {
		if (curHealth - damage > 0) {
			curHealth -= damage;
		} else {
			curHealth = 0;
			GamePanel.setGameState(gameStates.DEAD);
		}
	}

	public void decreaseFireRate() {
		if (fireRate > MAX_FIRE_RATE) {
			fireRate -= 5;
		}
	}

	public int getFireRate() {
		return fireRate;
	}

	public void setFireRate(int fireRate) {
		this.fireRate = fireRate;
	}

	public int getHeadCounter() {
		return headCounter;
	}

	public void setHeadCounter(int headCounter) {
		this.headCounter = headCounter;
	}

	public Image getHeadImage() {
		if (damageCounter / 10 % 2 == 1) {
			return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		}
		return headImage;
	}

	public Image getBodyImage() {
		if (damageCounter / 10 % 2 == 1) {
			return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		}
		return bodyImage;
	}

	public Animation getHeadAnimation() {
		if (damageCounter / 10 % 2 == 1) {
			return damageBuffer;
		}
		return headAnimation[animationIndexHead];
	}

	public Animation getBodyAnimation() {
		if (damageCounter / 10 % 2 == 1) {
			return damageBuffer;
		}
		return bodyAnimation[animationIndexBody];
	}

	/**
	 * @return the animationIndexHead
	 */
	public int getAnimationIndexHead() {
		return animationIndexHead;
	}

	/**
	 * @param animationIndexHead
	 *            the animationIndexHead to set
	 */
	public void setAnimationIndexHead(int animationIndexHead) {
		this.animationIndexHead = animationIndexHead;
	}

	/**
	 * @return the animationIndexBody
	 */
	public int getAnimationIndexBody() {
		return animationIndexBody;
	}

	/**
	 * @param animationIndexBody
	 *            the animationIndexBody to set
	 */
	public void setAnimationIndexBody(int animationIndexBody) {
		this.animationIndexBody = animationIndexBody;
	}
}
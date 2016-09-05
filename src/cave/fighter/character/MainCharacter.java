package cave.fighter.character;

import game.GamePanel;
import game.Projectile;
import game.GamePanel.attackStates;
import game.GamePanel.gameStates;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import cave.fighter.utilities.Assets;
import cave.fighter.utilities.Constants;
import animation.framework.Animation;

public class MainCharacter {

	//Uses the singleton pattern
	private static MainCharacter instance = new MainCharacter();
	
	// health the character has when health is full
	private int health = 6;

	// the current health
	private int curHealth = health;

	// pixels / 60th of a second
	private int speed = 3;

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

	//Am i ready to fire a bullet?
	private boolean fireReady = false;

	// controls how long the head will stay looking in the attack direction
	private int headCounter = 0;

	// the damage of the character
	private int damage = 1;

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

	private MainCharacter() {
		
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
		if (speed < Constants.CHAR_MAX_SPEED) {
			speed++;
			bulletSpeed++;
		}
	}

	public void increaseDamage() {
		if (damage < Constants.CHAR_MAX_DAMAGE) {
			damage++;
		}
	}

	public void increaseHealth() {
		if (health < Constants.CHAR_MAX_HEALTH) {
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
		if (fireRate > Constants.CHAR_MAX_FIRE_RATE) {
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

	public BufferedImage getHeadImage() {
		if (damageCounter / 10 % 2 == 1) {
			return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		}
		return Assets.headImage;
	}

	public Image getBodyImage() {
		if (damageCounter / 10 % 2 == 1) {
			return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		}
		return Assets.bodyImage;
	}

	public Animation getHeadAnimation() {
		if (damageCounter / 10 % 2 == 1) {
			return Assets.damageBuffer;
		}
		return Assets.headAnimation[animationIndexHead];
	}

	public Animation getBodyAnimation() {
		if (damageCounter / 10 % 2 == 1) {
			return Assets.damageBuffer;
		}
		return Assets.bodyAnimation[animationIndexBody];
	}

	public int getAnimationIndexHead() {
		return animationIndexHead;
	}

	public void setAnimationIndexHead(int animationIndexHead) {
		this.animationIndexHead = animationIndexHead;
	}

	public int getAnimationIndexBody() {
		return animationIndexBody;
	}

	public void setAnimationIndexBody(int animationIndexBody) {
		this.animationIndexBody = animationIndexBody;
	}
}
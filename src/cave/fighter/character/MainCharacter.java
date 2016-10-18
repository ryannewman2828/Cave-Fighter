package cave.fighter.character;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import cave.fighter.animation.framework.Animation;
import cave.fighter.enums.AttackStates;
import cave.fighter.utilities.Assets;
import cave.fighter.utilities.Constants;

public class MainCharacter {

	// Uses the singleton design pattern
	private static MainCharacter instance = new MainCharacter();

	private int health;
	private int curHealth;
	private int speed;
	private int fireRate;
	private int bulletSpeed;
	private int x;
	private int y;
	private int fireCounter;
	private int damageCounter;
	private int headCounter;
	private int damage;
	private int animationIndexHead;
	private int animationIndexBody;
	private boolean fireReady;
	private boolean alive;

	private ArrayList<Projectile> projectiles;

	public AttackStates attack;

	private Rectangle rect;
	private Rectangle rect2;

	private MainCharacter() {
		health = Constants.CHAR_START_HEALTH;
		curHealth = Constants.CHAR_START_HEALTH;
		speed = Constants.CHAR_START_SPEED;
		fireRate = Constants.CHAR_START_FIRE_RATE;
		bulletSpeed = Constants.CHAR_START_BULLET_SPEED;
		x = Constants.CHAR_START_X;
		y = Constants.CHAR_START_Y;
		fireCounter = 0;
		damageCounter = 0;
		headCounter = 0;
		damage = Constants.CHAR_START_DAMAGE;
		animationIndexHead = 0;
		animationIndexBody = 0;
		fireReady = false;
		alive = true;

		projectiles = new ArrayList<Projectile>();
		attack = AttackStates.STATIC;
		rect = new Rectangle(0, 0, 0, 0);
		rect2 = new Rectangle(0, 0, 0, 0);
	}

	public static MainCharacter getCharacterInstance(){
		return instance;
	}
	
	public void resetCharacter(){
		health = Constants.CHAR_START_HEALTH;
		curHealth = Constants.CHAR_START_HEALTH;
		speed = Constants.CHAR_START_SPEED;
		fireRate = Constants.CHAR_START_FIRE_RATE;
		bulletSpeed = Constants.CHAR_START_BULLET_SPEED;
		x = Constants.CHAR_START_X;
		y = Constants.CHAR_START_Y;
		fireCounter = 0;
		damageCounter = 0;
		headCounter = 0;
		damage = Constants.CHAR_START_DAMAGE;
		animationIndexHead = 0;
		animationIndexBody = 0;
		fireReady = false;
		alive = true;

		projectiles = new ArrayList<Projectile>();
		attack = AttackStates.STATIC;
		rect = new Rectangle(0, 0, 0, 0);
		rect2 = new Rectangle(0, 0, 0, 0);
	}
	
	public void update() {
		rect.setRect(x - Constants.CHAR_HITBOX_1_X_DISPLACE, y - Constants.CHAR_HITBOX_1_Y_DISPLACE, Constants.CHAR_HITBOX_1_WIDTH, Constants.CHAR_HITBOX_1_HEIGHT);
		rect2.setRect(x - Constants.CHAR_HITBOX_2_X_DISPLACE, y - Constants.CHAR_HITBOX_2_Y_DISPLACE, Constants.CHAR_HITBOX_2_WIDTH, Constants.CHAR_HITBOX_2_HEIGHT);

		if (!fireReady) {
			fireCounter++;
			if (fireCounter == fireRate) {
				fireReady = true;
				fireCounter = 0;
			}
		}

		if (headCounter > 0) {
			headCounter--;
		} else {
			attack = AttackStates.STATIC;
		}
		
		if (damageCounter > 0) {
			damageCounter--;
		}
		
		if (health <= 0){
			alive = false;
		}

		switch (attack) {
		case DOWN:
			animationIndexHead = 0;
			if (fireReady){
				shoot(0, bulletSpeed);
			}
			break;
		case LEFT:
			animationIndexHead = 1;
			if (fireReady){
				shoot(-bulletSpeed, 0);
			}
			break;
		case RIGHT:
			animationIndexHead = 2;
			if (fireReady){
				shoot(bulletSpeed, 0);
			}
			break;
		case STATIC:
			break;
		case UP:
			animationIndexHead = 3;
			if (fireReady){
				shoot(0, -bulletSpeed);
			}
			break;
		default:
			break;
		}

		for (int i = 0; i < projectiles.size(); i++) {
			if (projectiles.get(i).isActive()) {
				projectiles.get(i).update();
			} else {
				projectiles.remove(i);
			}
		}
	}

	private void shoot(int speedX, int speedY) {
		Projectile p = new Projectile(x, y - Constants.CHAR_BULLET_Y_DISPLACE, speedX, speedY);
		projectiles.add(p);
		fireReady = false;
	}

	public void getHurt(int damage) {
		if (damageCounter == 0) {
			damageCounter = Constants.CHAR_BULLET_COOLDOWN;
			hurt(damage);
		}
	}

	public int getHealth() {
		return health;
	}

	public int getCurHealth() {
		return curHealth;
	}

	public int getSpeed() {
		return speed;
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

	public void move(int xSpeed, int ySpeed) {
		x += xSpeed;
		y += ySpeed;
	}

	public int getDamage() {
		return damage;
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
			alive = false;
		}
	}

	public void decreaseFireRate() {
		if (fireRate > Constants.CHAR_MAX_FIRE_RATE) {
			fireRate -= Constants.CHAR_DECREASE_FIRE_RATE;
		}
	}

	public int getHeadCounter() {
		return headCounter;
	}

	public void setHeadCounter(int headCounter) {
		this.headCounter = headCounter;
	}
	
	public boolean getAlive(){
		return alive;
	}

	public BufferedImage getHeadImage() {
		if (damageCounter / 10 % 2 == 1) {
			return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		}
		return Assets.headImage;
	}

	public BufferedImage getBodyImage() {
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

	public void setAnimationIndexHead(int animationIndexHead) {
		this.animationIndexHead = animationIndexHead;
	}

	public void setAnimationIndexBody(int animationIndexBody) {
		this.animationIndexBody = animationIndexBody;
	}

	public Rectangle getRect() {
		return rect;
	}

	public Rectangle getRect2() {
		return rect2;
	}
}
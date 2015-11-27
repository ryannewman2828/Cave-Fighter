/**
 * Ryan Newman
 * Cave Fighter
 * Version 1.1
 * November 26 2015 
*/
package game;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import animation.framework.Animation;

public abstract class Boss {

	//Boss variables
	private int speed;
	private boolean spawnEnemy;
	private boolean spawning = true;
	private int x;
	private int y;
	private int health;
	private int damage;
	private int counter = 0;
	private float alpha = 0.04f;
	private Animation bossAnimation;
	private Image image;
	private Image canvasImage;
	private MainCharacter character;
	private BossHealthBar hpBar;
	public ArrayList<Rectangle> bossHitBoxes;

	public Boss(int x, int y, int speed, int health, int damage,
			MainCharacter character, int width, int height) {
		
		//Constructs the boss
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.health = health;
		this.damage = damage;
		this.character = character;
		canvasImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		bossAnimation = new Animation();
		hpBar = new BossHealthBar(health);
		bossHitBoxes = new ArrayList<Rectangle>();
	}

	//Every boss needs to be updated
	public abstract void update();

	//Controls the fade and HP bar
	public void spawn() {
		Graphics2D g2d = (Graphics2D) canvasImage.getGraphics();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				alpha));
		g2d.drawImage(image, 0, 0, null);
		bossAnimation.setFrame(canvasImage, 100, 0);
		counter++;
		hpBar.incrementBar();
	}

	/**
	 * @return the speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * @param speed
	 *            the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the health
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * @param health
	 *            the health to set
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	//
	public void damageBoss(int dmg) {
		if (health - dmg >= 0) {
			health -= dmg;
		} else {
			health = 0;
		}
		hpBar.update(health);
	}

	/**
	 * @return the damage
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * @param damage
	 *            the damage to set
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}

	/**
	 * @return the enemyAnimation
	 */
	public Animation getBossAnimation() {
		return bossAnimation;
	}

	/**
	 * @param enemyAnimation
	 *            the enemyAnimation to set
	 */
	public void setBossAnimation(Animation enemyAnimation) {
		this.bossAnimation = enemyAnimation;
	}

	/**
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * @return the character
	 */
	public MainCharacter getCharacter() {
		return character;
	}

	/**
	 * @param character
	 *            the character to set
	 */
	public void setCharacter(MainCharacter character) {
		this.character = character;
	}

	/**
	 * @return the hpBar
	 */
	public BossHealthBar getHpBar() {
		return hpBar;
	}

	/**
	 * @param hpBar
	 *            the hpBar to set
	 */
	public void setHpBar(BossHealthBar hpBar) {
		this.hpBar = hpBar;
	}

	/**
	 * @return the spawnEnemy
	 */
	public boolean isSpawnEnemy() {
		return spawnEnemy;
	}

	/**
	 * @param spawnEnemy
	 *            the spawnEnemy to set
	 */
	public void setSpawnEnemy(boolean spawnEnemy) {
		this.spawnEnemy = spawnEnemy;
	}

	/**
	 * @return the spawning
	 */
	public boolean isSpawning() {
		return spawning;
	}

	/**
	 * @param spawning the spawning to set
	 */
	public void setSpawning(boolean spawning) {
		this.spawning = spawning;
	}

	/**
	 * @return the counter
	 */
	public int getCounter() {
		return counter;
	}

	/**
	 * @param counter the counter to set
	 */
	public void setCounter(int counter) {
		this.counter = counter;
	}

	/**
	 * @return the canvasImage
	 */
	public Image getCanvasImage() {
		return canvasImage;
	}

	/**
	 * @param canvasImage the canvasImage to set
	 */
	public void setCanvasImage(Image canvasImage) {
		this.canvasImage = canvasImage;
	}
}
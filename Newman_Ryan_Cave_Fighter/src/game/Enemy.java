/**
 * Ryan Newman
 * Cave Fighter
 * Version 1.1
 * November 26 2015 
*/
package game;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.net.URL;

import javax.swing.JFrame;

import animation.framework.Animation;

public abstract class Enemy {

	//Enemy variables
	private int speed;
	private boolean alive = true;
	private boolean spawning;
	private int x;
	private int y;
	private int health;
	private int damage;
	private float alpha = .01f;
	private int counter = 0;
	private Animation enemyAnimation;
	private Image image;
	private Image canvasImage;
	public Rectangle enemyHitBox;
	private MainCharacter character;
	
	public Enemy(int x, int y, int speed, int health, int damage, boolean spawning, MainCharacter character) {
		
		//Constructs the enemy
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.health = health;
		this.damage = damage;
		this.setSpawning(spawning);
		this.character = character;
		canvasImage = new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB);
		enemyAnimation = new Animation();
		enemyHitBox = new Rectangle(0,0,0,0);
	}
	
	//Every enemy needs to be updated
	public abstract void update();

	//Responsible for spawning in the enemy
	public void fade() {
		Graphics2D g2d = (Graphics2D) canvasImage.getGraphics();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				alpha));
		g2d.drawImage(image, 0, 0, null);
		enemyAnimation.setFrame(canvasImage, 100, 0);
		counter++;
	}
	
	/**
	 * @return the speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * @return the alive
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * @param alive the alive to set
	 */
	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
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
	 * @param y the y to set
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
	 * @param health the health to set
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	//
	public void damageEnemy(int dmg){
		health -= dmg;
	}
	
	/**
	 * @return the damage
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * @param damage the damage to set
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}

	/**
	 * @return the enemyAnimation
	 */
	public Animation getEnemyAnimation() {
		return enemyAnimation;
	}

	/**
	 * @param enemyAnimation the enemyAnimation to set
	 */
	public void setEnemyAnimation(Animation enemyAnimation) {
		this.enemyAnimation = enemyAnimation;
	}

	/**
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * @param image the image to set
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
	 * @param character the character to set
	 */
	public void setCharacter(MainCharacter character) {
		this.character = character;
	}

	public boolean isSpawning() {
		return spawning;
	}

	public void setSpawning(boolean spawning) {
		this.spawning = spawning;
	}

	/**
	 * @return the alpha
	 */
	public float getAlpha() {
		return alpha;
	}

	/**
	 * @param alpha the alpha to set
	 */
	public void setAlpha(float alpha) {
		this.alpha = alpha;
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
}
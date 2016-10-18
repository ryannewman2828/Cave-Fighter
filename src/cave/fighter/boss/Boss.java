package cave.fighter.boss;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import cave.fighter.animation.framework.Animation;
import cave.fighter.utilities.Constants;

public abstract class Boss {

	private int speed;
	private boolean spawnEnemy;
	private boolean spawning;
	private int x;
	private int y;
	private int health;
	private int damage;
	private int counter;
	private float alpha;
	private Animation bossAnimation;
	private Image image;
	private Image canvasImage;
	private BossHealthBar hpBar;
	public ArrayList<Rectangle> bossHitBoxes;

	public Boss(int x, int y, int speed, int health, int damage, int width, int height) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.health = health;
		this.damage = damage;
		canvasImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		bossAnimation = new Animation();
		hpBar = new BossHealthBar(health);
		bossHitBoxes = new ArrayList<Rectangle>();
		spawning = true;
		counter = 0;
		alpha = Constants.ALPHA;
	}

	public abstract void update();

	public void spawn() {
		Graphics2D g2d = (Graphics2D) canvasImage.getGraphics();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				alpha));
		g2d.drawImage(image, 0, 0, null);
		bossAnimation.setFrame(canvasImage, 100, 0);
		counter++;
		hpBar.incrementBar();
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

	public int getHealth() {
		return health;
	}

	public void damageBoss(int dmg) {
		if (health - dmg >= 0) {
			health -= dmg;
		} else {
			health = 0;
		}
		hpBar.update(health);
	}

	public int getDamage() {
		return damage;
	}

	public Animation getBossAnimation() {
		return bossAnimation;
	}

	public void setBossAnimation(Animation animation) {
		this.bossAnimation = animation;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}


	public BossHealthBar getHpBar() {
		return hpBar;
	}

	public boolean isSpawnEnemy() {
		return spawnEnemy;
	}

	public void setSpawnEnemy(boolean spawnEnemy) {
		this.spawnEnemy = spawnEnemy;
	}

	public boolean isSpawning() {
		return spawning;
	}

	public void setSpawning(boolean spawning) {
		this.spawning = spawning;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}
}
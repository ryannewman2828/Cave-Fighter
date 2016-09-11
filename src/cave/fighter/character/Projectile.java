package cave.fighter.character;

import java.awt.Rectangle;

import cave.fighter.animation.framework.Animation;
import cave.fighter.utilities.Assets;

public class Projectile {

	private int x;
	private int y;
	private int speedX;
	private int speedY;
	private int height;
	private int width;
	int animIndex;
	private boolean active;

	public Rectangle bulletHitBox = new Rectangle(0, 0, 0, 0);

	public Projectile(int x, int y, int speedX, int speedY) {
		this.x = x;
		this.y = y;
		this.speedX = speedX;
		this.speedY = speedY;
		active = true;

		if (speedY < 0) {
			width = 16;
			height = 32;
			animIndex = 1;
		} else if (speedX < 0) {
			width = 32;
			height = 16;
			animIndex = 2;
		} else if (speedX > 0) {
			width = 32;
			height = 16;
			animIndex = 3;
		} else {
			width = 16;
			height = 32;
			animIndex = 0;
		}

	}

	public void update() {

		x += speedX;
		y += speedY;

		bulletHitBox.setRect(x - width / 2, y - height / 2, width, height);

		if (x <= 10 || x >= 780 || y <= 80 || y >= 460) {
			active = false;
		}
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

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Animation getBulletAnimation() {
		return Assets.bulletAnimation[animIndex];
	}
}
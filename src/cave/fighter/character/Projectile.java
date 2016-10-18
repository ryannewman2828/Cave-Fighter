package cave.fighter.character;

import java.awt.Rectangle;

import cave.fighter.animation.framework.Animation;
import cave.fighter.utilities.Assets;
import cave.fighter.utilities.Constants;

public class Projectile {

	private int x;
	private int y;
	private int speedX;
	private int speedY;
	private int height;
	private int width;
	private boolean active;
	private Animation anim;

	public Rectangle bulletHitBox = new Rectangle(0, 0, 0, 0);

	public Projectile(int x, int y, int speedX, int speedY) {
		this.x = x;
		this.y = y;
		this.speedX = speedX;
		this.speedY = speedY;
		active = true;

		if (speedY < 0) {
			width = Constants.BULLET_HALF_SIZE;
			height = Constants.BULLET_FULL_SIZE;
			anim = Assets.bulletAnimation[1].clone();
		} else if (speedX < 0) {
			width = Constants.BULLET_FULL_SIZE;
			height = Constants.BULLET_HALF_SIZE;
			anim = Assets.bulletAnimation[2].clone();
		} else if (speedX > 0) {
			width = Constants.BULLET_FULL_SIZE;
			height = Constants.BULLET_HALF_SIZE;
			anim = Assets.bulletAnimation[3].clone();
		} else {
			width = Constants.BULLET_HALF_SIZE;
			height = Constants.BULLET_FULL_SIZE;
			anim = Assets.bulletAnimation[0].clone();
		}

	}

	public void update() {

		x += speedX;
		y += speedY;

		bulletHitBox.setRect(x - width / 2, y - height / 2, width, height);

		if (x <= Constants.MAP_LEFT_BOUND || x >= Constants.MAP_RIGHT_BOUND || y <= Constants.MAP_TOP_BOUND || y >= Constants.MAP_BOTTOM_BOUND) {
			active = false;
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Animation getBulletAnimation() {
		return anim;
	}
}
/**
 * Ryan Newman
 * Cave Fighter
 * Version 1.1
 * November 26 2015
 */
package game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import animation.framework.Animation;

public class Projectile {

	// co-ordinates
	private int x;
	private int y;

	// The animation and the images for the bullets
	private Animation bulletAnimation;
	private Image image;
	private Image originalImage;

	// Transformation for the bullet
	private static AffineTransform at = new AffineTransform();
	private Graphics2D g2d;

	// Holds the pixels / 60th of a second of the projectile
	// X and y for diagonal movement
	private int speedX;
	private int speedY;

	// The dimensions of the bullet
	private int height;
	private int width;

	// Is it active?
	private boolean active = true;

	//Hitbox
	public Rectangle bulletHitBox = new Rectangle(0, 0, 0, 0);

	public Projectile(int x, int y, int speedX, int speedY) {
		this.x = x;
		this.y = y;
		this.speedX = speedX;
		this.speedY = speedY;

		bulletAnimation = new Animation();

		//Image setups
		try {
			for (int i = 0; i < 4; i++) {
				image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
				g2d = (Graphics2D) image.getGraphics();
				originalImage = ImageIO
						.read(new File("data/MFire" + i + ".png"));
				if (speedY < 0) {
					width = 16;
					height = 32;
					g2d.drawImage(originalImage,
							at.getRotateInstance(Math.PI, 16, 16), null);
				} else if (speedX < 0) {
					width = 32;
					height = 16;
					g2d.drawImage(originalImage,
							at.getRotateInstance(Math.PI / 2, 16, 16), null);
				} else if (speedX > 0) {
					width = 32;
					height = 16;
					g2d.drawImage(originalImage,
							at.getRotateInstance(3 * Math.PI / 2, 16, 16), null);
				} else {
					width = 16;
					height = 32;
					image = originalImage;
				}
				bulletAnimation.addFrame(image, 100);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		
		// Updates the speed of the projectile
		x += speedX;
		y += speedY;

		// updates hitboxes
		bulletHitBox.setRect(x - width / 2, y - height / 2, width, height);

		//Checks for inactive bullets
		if (x <= 10 || x >= 780 || y <= 80 || y >= 460) {
			active = false;
		}
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
	 * @return the speedX
	 */
	public int getSpeedX() {
		return speedX;
	}

	/**
	 * @param speedX
	 *            the speedX to set
	 */
	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	/**
	 * @return the speedY
	 */
	public int getSpeedY() {
		return speedY;
	}

	/**
	 * @param speedY
	 *            the speedY to set
	 */
	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @return the bulletAnimation
	 */
	public Animation getBulletAnimation() {
		return bulletAnimation;
	}

	/**
	 * @param bulletAnimation
	 *            the bulletAnimation to set
	 */
	public void setBulletAnimation(Animation bulletAnimation) {
		this.bulletAnimation = bulletAnimation;
	}
}
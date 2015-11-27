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
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import animation.framework.Animation;

public class GreenSlime extends Enemy {

	// movement delays and move cycles
	private int a = (int) (Math.random() * 4) * 30 + 30, b = (int) (Math
			.random() * 4) * 30 + 30;
	private boolean c = true, d = false;

	// Animation
	private Animation enemyAnimation = new Animation();

	public GreenSlime(int x, int y, int speed, int health, int damage,
			boolean spawning, MainCharacter character) {
		super(x, y, speed, health, damage, spawning, character);

		// Image setups
		try {
			for (int i = 0; i < 3; i++) {
				setImage(ImageIO.read(new File("data/Slime" + i + ".png")));
				enemyAnimation.addFrame(getImage(), 100);
			}

			// Spawning purposes only
			setImage(ImageIO.read(new File("data/Slime1.png")));
			enemyAnimation.addFrame(getImage(), 100);
			setImage(ImageIO.read(new File("data/Slime0.png")));
			getEnemyAnimation().addFrame(getImage(), 100);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Sets animation
		if (!spawning) {
			setEnemyAnimation(enemyAnimation);
		}
	}

	@Override
	public void update() {
		if (isSpawning()) {

			//Spawning
			fade();

			//The after spawn
			if (getCounter() >= 120) {
				setSpawning(false);
				setEnemyAnimation(enemyAnimation);
			}
		} else {
			// Sets slime to dead when it has no health
			if (getHealth() <= 0) {
				setAlive(false);
			}

			//Movement AI
			if (c) {
				if (getCharacter().getX() > getX()) {
					setX(getX() + getSpeed());
				} else if (getCharacter().getX() < getX()) {
					setX(getX() - getSpeed());
				}
				if (getCharacter().getY() > getY()) {
					setY(getY() + getSpeed());
				} else if (getCharacter().getY() < getY()) {
					setY(getY() - getSpeed());
				}
			}
			if (a > 0 && !d) {
				a--;
				if (a == 0) {
					d = true;
					c = false;
					b = (int) (Math.random() * 4) * 30 + 30;
				}
			}
			if (b > 0 && !c) {
				b--;
				if (b == 0) {
					c = true;
					d = false;
					a = (int) (Math.random() * 6) * 30 + 30;
				}
			}

			// Updates HitBoxes
			enemyHitBox.setRect(getX() - 14, getY() - 10, 27, 21);
		}
	}
}
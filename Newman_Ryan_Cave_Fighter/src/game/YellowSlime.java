/**
 * Ryan Newman
 * Cave Fighter
 * Version 1.1
 * November 26 2015 
*/
package game;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import animation.framework.Animation;

public class YellowSlime extends Enemy {

	//Charge booleans
	private boolean chargeLeft = false;
	private boolean chargeRight = false;

	//Aniamtion
	private Animation enemyAnimation = new Animation();

	public YellowSlime(int x, int y, int speed, int health, int damage,
			boolean spawning, MainCharacter character) {
		super(x, y, speed, health, damage, spawning, character);
		
		//Image setups
		try {
			for (int i = 0; i < 3; i++) {
				setImage(ImageIO.read(new File("data/YSlime" + i + ".png")));
				enemyAnimation.addFrame(getImage(), 100);
			}
			
			//Spawning purposes only
			setImage(ImageIO.read(new File("data/YSlime1.png")));
			enemyAnimation.addFrame(getImage(), 100);
			setImage(ImageIO.read(new File("data/YSlime0.png")));
			getEnemyAnimation().addFrame(getImage(), 100);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Sets enemy Animation
		if (!spawning) {
			setEnemyAnimation(enemyAnimation);
		}
	}

	@Override
	public void update() {
		if(isSpawning()){
			
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
		
		// Movement AI
		if (getCharacter().getY() > getY() + 5 && (!chargeLeft && !chargeRight)) {
			setY(getY() + getSpeed());
		} else if (getCharacter().getY() < getY() - 5
				&& (!chargeLeft && !chargeRight)) {
			setY(getY() - getSpeed());
		} else {
			if (getCharacter().getX() >= getX() && !chargeLeft) {
				chargeRight = true;
			}
			if (getCharacter().getX() < getX() && !chargeRight) {
				chargeLeft = true;
			}
		}
		if (chargeLeft) {
			setX(getX() - getSpeed() - 3);
			if (getX() <= 100) {
				chargeLeft = false;
			}
		} else if (chargeRight) {
			setX(getX() + (getSpeed() + 3));
			if (getX() >= 700) {
				chargeRight = false;
			}
		}

		// Updates HitBoxes
		enemyHitBox.setRect(getX() - 17, getY() - 10, 33, 21);
		}
	}
}
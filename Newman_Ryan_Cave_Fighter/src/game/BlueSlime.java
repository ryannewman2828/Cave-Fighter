/**
 * Ryan Newman
 * Cave Fighter
 * Version 1.1
 * November 26 2015 
*/
package game;

import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import animation.framework.Animation;

public class BlueSlime extends Enemy {

	//Animation
	private Animation enemyAnimation = new Animation();

	public BlueSlime(int x, int y, int speed, int health, int damage,
			boolean spawning, MainCharacter character) {
		super(x, y, speed, health, damage, spawning, character);
		
		//Sets up images
		try {
			for (int i = 0; i < 3; i++) {
				setImage(ImageIO.read(new File("data/BSlime" + i + ".png")));
				enemyAnimation.addFrame(getImage(), 100);
			}
			
			//Spawning purposes only
			setImage(ImageIO.read(new File("data/BSlime1.png")));
			enemyAnimation.addFrame(getImage(), 100);
			setImage(ImageIO.read(new File("data/BSlime0.png")));
			getEnemyAnimation().addFrame(getImage(), 100);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (!spawning) {
			
			//Sets animation
			setEnemyAnimation(enemyAnimation);
		}
	}

	@Override
	public void update() {
		if (isSpawning()) {

			//Spawns
			fade();

			//The after spawn
			if (getCounter() >= 120) {
				setSpawning(false);
				setEnemyAnimation(enemyAnimation);
			}
		} else {

			//Die
			if (getHealth() <= 0) {
				setAlive(false);
			}

			// Movement
			//None they act as pylons
			
			// Updates HitBoxes
			enemyHitBox.setRect(getX() - 16, getY() - 12, 32, 24);
		}
	}
}
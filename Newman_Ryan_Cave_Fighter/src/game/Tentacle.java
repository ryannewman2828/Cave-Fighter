/**
 * Ryan Newman
 * ICS4U - 1
 * Cave Fighter
 * June 10 2015 
*/
package game;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import animation.framework.Animation;

public class Tentacle extends Enemy {

	//Animation
	private Animation enemyAnimation = new Animation();
	private Animation spawnAnimation = new Animation();
	
	public Tentacle(int x, int y, int speed, int health, int damage,
			boolean spawning, MainCharacter character) {
		super(x, y, speed, health, damage, spawning, character);
		
		//Image setups
		try {
			for (int i = 0; i < 8; i++) {
				setImage(ImageIO.read(new File("data/Tentacle" + i + ".png")));
				enemyAnimation.addFrame(getImage(), 100);
			}
			
			//Spawning purposes only
			for (int i = 0; i < 12; i++) {
				setImage(ImageIO.read(new File("data/TE" + i + ".png")));
				spawnAnimation.addFrame(getImage(), 100);
			}
			setEnemyAnimation(spawnAnimation);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Sets enemy animation
		if (!spawning) {
			setEnemyAnimation(enemyAnimation);
		}
	}

	@Override
	public void update() {
		if (isSpawning()) {

			//Sets spawn counter
			setCounter(getCounter() + 1);

			//The after spawn
			if (getCounter() >= 80) {
				setSpawning(false);
				setEnemyAnimation(enemyAnimation);
			}
		} else {

			//Sets false if its dead
			if (getHealth() <= 0) {
				setAlive(false);
			}

			// Movement
			//Acts as a pylon
			
			// Updates HitBoxes
			enemyHitBox.setRect(getX() - 12, getY() - 45, 25, 90);
		}
	}
}
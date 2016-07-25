/**
 * Ryan Newman
 * Cave Fighter
 * Version 1.1
 * November 26 2015 
*/
package game;

import game.GamePanel.gameStates;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import animation.framework.Animation;

public class MageBoss extends Boss {

	//Boss variables
	private int counter = 0;
	private int index = 1;
	private boolean moving = false;
	private int delay;

	//Animation
	private Animation bossAnimation = new Animation();

	public MageBoss(MainCharacter character) {
		super(420, 240, 1, 100, 1, character, 85, 94);
		
		//Image setup
		try {
			for (int i = 0; i < 8; i++) {
				setImage(ImageIO.read(new File("data/mage" + i + ".png")));
				bossAnimation.addFrame(getImage(), 100);
			}
			for (int i = 6; i >= 0; i--) {
				setImage(ImageIO.read(new File("data/mage" + i + ".png")));
				bossAnimation.addFrame(getImage(), 100);
			}

			//Spawning purposes only
			setImage(ImageIO.read(new File("data/mage0.png")));
			getBossAnimation().addFrame(getImage(), 100);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Declares hitboxes
		for (int i = 0; i < 4; i++) {
			bossHitBoxes.add(new Rectangle(0, 0, 0, 0));
		}
	}

	@Override
	public void update() {

		if (isSpawning()) {

			//Spawning
			spawn();

			//The after spawn
			if (getCounter() >= 140) {
				setSpawning(false);
				setBossAnimation(bossAnimation);
				GamePanel.setGameState(gameStates.BOSS_BATTLE);
			}
		} else {

			// Sets Mage to dead when it has no health
			if (getHealth() <= 0) {
				GamePanel.setGameState(gameStates.GAME_OVER);
			}

			// Movement AI
			if (moving) {
				switch (index) {
				case 0:
					if (counter <= delay + 20) {
						setX(getX() - getSpeed());
					} else if (counter <= 40 + delay * 2) {
						setX(getX() + getSpeed());
					} else {
						moving = false;
						counter = 0;
					}
					break;
				case 1:
					if (counter <= delay + 20) {
						setX(getX() + getSpeed());
					} else if (counter <= 40 + delay * 2) {
						setX(getX() - getSpeed());
					} else {
						moving = false;
						counter = 0;
					}
					break;
				case 2:
					if (counter <= delay) {
						setY(getY() - getSpeed());
					} else if (counter <= delay * 2) {
						setY(getY() + getSpeed());
					} else {
						moving = false;
						counter = 0;
					}
					break;
				case 3:
					if (counter <= delay) {
						setY(getY() + getSpeed());
					} else if (counter <= delay * 2) {
						setY(getY() - getSpeed());
					} else {
						moving = false;
						counter = 0;
					}
					break;
				case 4:
					setSpawnEnemy(true);
					moving = false;
					counter = 0;
					delay += 30;
				}
			} else {

				//Sets spawn enemy to false
				setSpawnEnemy(false);
				
				//Exits the delay
				if (counter == delay) {
					moving = true;
					index = (int) (Math.random() * 5);
					counter = 0;

					//Changes speed
					setSpeed(5 - getHealth() / 25);

					//Change delay to match speed
					delay = (10 * (5 - getSpeed()) + 30);
				}
			}

			// Updates hitboxes
			bossHitBoxes.get(0).setRect(getX() - 9, getY() - 40, 15, 85);
			bossHitBoxes.get(1).setRect(getX() - 17, getY() - 20, 33, 20);
			bossHitBoxes.get(2).setRect(getX() - 33, getY() + 32, 61, 14);
			bossHitBoxes.get(3).setRect(getX() - 23, getY() + 18, 41, 14);
			counter++;
		}
	}
}
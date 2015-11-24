/**
 * Ryan Newman
 * ICS4U - 1
 * Cave Fighter
 * June 10 2015 
*/
package game;

import game.GamePanel.gameStates;

import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import animation.framework.Animation;

public class ArchMageBoss extends Boss {

	//Boss variables
	private int counter = 0;
	private int index = 1;
	private boolean moving = false;
	private int delay;

	//Animation
	private Animation bossAnimation = new Animation();

	public ArchMageBoss(MainCharacter character) {
		super(420, 240, 1, 200, 2, character, 122, 110);
		try {
			//Setup images
			for (int i = 0; i < 8; i++) {
				setImage(ImageIO.read(new File("data/SMage" + i + ".png")));
				bossAnimation.addFrame(getImage(), 100);
			}
			for (int i = 6; i >= 0; i--) {
				setImage(ImageIO.read(new File("data/SMage" + i + ".png")));
				bossAnimation.addFrame(getImage(), 100);
			}

			//For spawning purposes only
			setImage(ImageIO.read(new File("data/SMage0.png")));
			getBossAnimation().addFrame(getImage(), 100);

		} catch (IOException e) {
			e.printStackTrace();
		}

		//hitboxes
		for (int i = 0; i < 3; i++) {
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

				//No longer spawning enemies
				setSpawnEnemy(false);
				
				//No longer being delayed
				if (counter == delay) {
					moving = true;
					index = (int) (Math.random() * 5);
					counter = 0;

					//Changes the speed
					setSpeed(5 - getHealth() / 50);

					//Changes the delay to match speed
					delay = (10 * (5 - getSpeed()) + 30);
				}
			}

			// Updates hitboxes
			bossHitBoxes.get(0).setRect(getX() - 1, getY() - 34, 14, 83);
			bossHitBoxes.get(1).setRect(getX() - 23, getY() - 20, 58, 36);
			bossHitBoxes.get(2).setRect(getX() - 16, getY() + 34, 43, 14);
			counter++;
		}
	}
}
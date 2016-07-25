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

public class Shade extends Boss {

	//Animation
	private Animation bossAnimation = new Animation();
	private Animation spawnAnimation = new Animation();
	private Animation hideAnimation = new Animation();

	//Boss variables
	private int counterSpawn = 0;
	private int counter = 0;
	private int index = 1;
	private boolean moving = false;
	private int delay;

	public Shade(MainCharacter character) {
		super(420, 240, 1, 500, 3, character, 80, 70);
		
		//Image setups
		try {
			for (int i = 0; i < 4; i++) {
				setImage(ImageIO.read(new File("data/shade" + i + ".png")));
				bossAnimation.addFrame(getImage(), 100);
			}
			for (int i = 2; i >= 0; i--) {
				setImage(ImageIO.read(new File("data/shade" + i + ".png")));
				bossAnimation.addFrame(getImage(), 100);
			}

			for (int i = 0; i < 10; i++) {
				setImage(ImageIO.read(new File("data/spawn" + i + ".png")));
				spawnAnimation.addFrame(getImage(), 100);
			}
			for (int i = 9; i >= 0; i--) {
				setImage(ImageIO.read(new File("data/spawn" + i + ".png")));
				hideAnimation.addFrame(getImage(), 100);
			}

			//Spawning purposes only
			setImage(ImageIO.read(new File("data/spawn0.png")));
			getBossAnimation().addFrame(getImage(), 100);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Hitboxes
		for (int i = 0; i < 4; i++) {
			bossHitBoxes.add(new Rectangle(0, 0, 0, 0));
		}
		
		//Sets delay
		delay = (10 * (5 - getSpeed()) + 30);
	}

	@Override
	public void update() {
		if (isSpawning()) {

			//Spawning
			spawn();

			//The after spawn
			if (getCounter() >= 120) {
				setSpawning(false);
				setBossAnimation(bossAnimation);
				GamePanel.setGameState(gameStates.BOSS_BATTLE);
			}
		} else {

			// Sets Boss to dead when it has no health
			if (getHealth() <= 0) {
				GamePanel.setGameState(gameStates.GAME_OVER);
			}

			// spawn Tentacle
			setSpawnEnemy(false);
			if (counterSpawn % 180 == 0)
				setSpawnEnemy(true);

			counterSpawn++;

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
				}
			} else {
				//Sets enemy spawn to false
				setSpawnEnemy(false);
				
				//Exits the delay
				if (counter == delay) {
					moving = true;
					index = (int) (Math.random() * 4);
					counter = 0;
				}
			}

			// Updates hitboxes
			bossHitBoxes.get(0).setRect(getX() - 6, getY() - 33, 14, 65);
			bossHitBoxes.get(1).setRect(getX() + 8, getY() - 13, 12, 45);
			bossHitBoxes.get(2).setRect(getX() - 24, getY() + 26, 62, 6);
			bossHitBoxes.get(3).setRect(getX() - 40, getY() - 22, 28, 15);
			counter++;
		}
	}

	@Override
	public void spawn() {

		//Increments health bar
		setCounter(getCounter() + 1);
		getHpBar().incrementBar();

		//starts spawning at counter = 40
		if (getCounter() == 40) {
			setBossAnimation(spawnAnimation);
		}
	}
}
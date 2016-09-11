package cave.fighter.boss;

import java.awt.Rectangle;

import cave.fighter.GamePanel;
import cave.fighter.enums.GameStates;
import cave.fighter.utilities.Assets;

public class ArchMageBoss extends Boss {

	private int counter;
	private int index;
	private boolean moving;
	private int delay;

	public ArchMageBoss() {
		super(420, 240, 1, 200, 2, 122, 110);
		
		counter = 0;
		index = 1;
		moving = false;
		
		setImage(Assets.archMageBoss);
		getBossAnimation().addFrame(getImage(), 100);

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
				setBossAnimation(Assets.archMageBossAnim);
				GamePanel.gameState = GameStates.BOSS_BATTLE;
			}
		} else {

			if (getHealth() <= 0) {
				GamePanel.gameState = GameStates.GAME_OVER;
			}

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

				setSpawnEnemy(false);
				
				if (counter == delay) {
					moving = true;
					index = (int) (Math.random() * 5);
					counter = 0;
					setSpeed(5 - getHealth() / 50);
					delay = (10 * (5 - getSpeed()) + 30);
				}
			}

			bossHitBoxes.get(0).setRect(getX() - 1, getY() - 34, 14, 83);
			bossHitBoxes.get(1).setRect(getX() - 23, getY() - 20, 58, 36);
			bossHitBoxes.get(2).setRect(getX() - 16, getY() + 34, 43, 14);
			counter++;
		}
	}
}
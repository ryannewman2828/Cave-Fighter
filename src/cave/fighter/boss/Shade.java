package cave.fighter.boss;

import java.awt.Rectangle;

import cave.fighter.GamePanel;
import cave.fighter.enums.GameStates;
import cave.fighter.utilities.Assets;

public class Shade extends Boss {

	private int counterSpawn;
	private int counter;
	private int index;
	private boolean moving;
	private int delay;

	public Shade() {
		super(420, 240, 1, 500, 3, 80, 70);

		counterSpawn = 0;
		counter = 0;
		index = 1;
		moving = false;
		
		setImage(Assets.shade);
		getBossAnimation().addFrame(getImage(), 100);

		for (int i = 0; i < 4; i++) {
			bossHitBoxes.add(new Rectangle(0, 0, 0, 0));
		}

		delay = (10 * (5 - getSpeed()) + 30);
	}

	@Override
	public void update() {
		if (isSpawning()) {

			// Spawning
			spawn();

			// The after spawn
			if (getCounter() >= 120) {
				setSpawning(false);
				setBossAnimation(Assets.shadeAnim);
				GamePanel.gameState = GameStates.BOSS_BATTLE;
			}
		} else {

			if (getHealth() <= 0) {
				GamePanel.gameState = GameStates.GAME_OVER;
			}

			// spawn Tentacle
			setSpawnEnemy(false);
			if (counterSpawn % 180 == 0){
				setSpawnEnemy(true);
			}

			counterSpawn++;

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
				setSpawnEnemy(false);

				if (counter == delay) {
					moving = true;
					index = (int) (Math.random() * 4);
					counter = 0;
				}
			}

			bossHitBoxes.get(0).setRect(getX() - 6, getY() - 33, 14, 65);
			bossHitBoxes.get(1).setRect(getX() + 8, getY() - 13, 12, 45);
			bossHitBoxes.get(2).setRect(getX() - 24, getY() + 26, 62, 6);
			bossHitBoxes.get(3).setRect(getX() - 40, getY() - 22, 28, 15);
			counter++;
		}
	}

	@Override
	public void spawn() {

		setCounter(getCounter() + 1);
		getHpBar().incrementBar();

		if (getCounter() == 40) {
			setBossAnimation(Assets.shadeSpawn);
		}
	}
}
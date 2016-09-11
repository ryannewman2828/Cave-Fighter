package cave.fighter.boss;

import java.awt.Rectangle;

import cave.fighter.GamePanel;
import cave.fighter.enums.GameStates;
import cave.fighter.utilities.Assets;
import cave.fighter.utilities.Constants;

public class Shade extends Boss {

	private int counterSpawn;
	private int counter;
	private int index;
	private boolean moving;
	private int delay;

	public Shade() {
		super(Constants.BOSS_X, Constants.BOSS_Y, Constants.SHADE_SPEED,
				Constants.SHADE_HEALTH, Constants.SHADE_DAMAGE,
				Constants.SHADE_WIDTH, Constants.SHADE_HEIGHT);

		counterSpawn = 0;
		counter = 0;
		index = 1;
		moving = false;

		setImage(Assets.shade);
		getBossAnimation().addFrame(getImage(), 100);

		for (int i = 0; i < 4; i++) {
			bossHitBoxes.add(new Rectangle(0, 0, 0, 0));
		}

		delay = (Constants.DELAY_MULTIPLIER
				* (Constants.SPEED_DEFLATER - getSpeed()) + Constants.DELAY_INCREMENT);
	}

	@Override
	public void update() {
		if (isSpawning()) {

			spawn();

			// The after spawn
			if (getCounter() >= Constants.SHADE_SPAWN_COUNT) {
				setSpawning(false);
				setBossAnimation(Assets.shadeAnim);
				GamePanel.gameState = GameStates.BOSS_BATTLE;
			}
		} else {

			if (getHealth() <= 0) {
				GamePanel.gameState = GameStates.GAME_OVER;
			}

			setSpawnEnemy(false);
			if (counterSpawn % Constants.SHADE_SPAWNER_COUNT == 0) {
				setSpawnEnemy(true);
			}

			counterSpawn++;

			if (moving) {
				switch (index) {
				case 0:
					if (counter <= delay + Constants.BOSS_DELAY_INCREMENT) {
						setX(getX() - getSpeed());
					} else if (counter <= Constants.SECONDARY_DELAY_INCREMENT
							+ delay * 2) {
						setX(getX() + getSpeed());
					} else {
						moving = false;
						counter = 0;
					}
					break;
				case 1:
					if (counter <= delay + Constants.BOSS_DELAY_INCREMENT) {
						setX(getX() + getSpeed());
					} else if (counter <= Constants.SECONDARY_DELAY_INCREMENT
							+ delay * 2) {
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
					index = (int) (Math.random() * Constants.SHADE_MOVE_POSSIBILITIES);
					counter = 0;
				}
			}

			bossHitBoxes.get(0).setRect(
					getX() + Constants.SHADE_HITBOX_X_DISPLACE_1,
					getY() + Constants.SHADE_HITBOX_Y_DISPLACE_1,
					Constants.SHADE_WIDTH_1, Constants.MAGE_HEIGHT_1);
			bossHitBoxes.get(1).setRect(
					getX() + Constants.SHADE_HITBOX_X_DISPLACE_2,
					getY() + Constants.SHADE_HITBOX_Y_DISPLACE_2,
					Constants.SHADE_WIDTH_2, Constants.MAGE_HEIGHT_2);
			bossHitBoxes.get(2).setRect(
					getX() + Constants.SHADE_HITBOX_X_DISPLACE_3,
					getY() + Constants.SHADE_HITBOX_Y_DISPLACE_3,
					Constants.SHADE_WIDTH_3, Constants.MAGE_HEIGHT_3);
			bossHitBoxes.get(3).setRect(
					getX() + Constants.SHADE_HITBOX_X_DISPLACE_4,
					getY() + Constants.SHADE_HITBOX_Y_DISPLACE_4,
					Constants.MAGE_WIDTH_4, Constants.MAGE_HEIGHT_4);
			counter++;
		}
	}

	@Override
	public void spawn() {

		setCounter(getCounter() + 1);
		getHpBar().incrementBar();

		if (getCounter() == Constants.SHADE_START_SPAWN_COUNT) {
			setBossAnimation(Assets.shadeSpawn);
		}
	}
}
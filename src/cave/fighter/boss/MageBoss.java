package cave.fighter.boss;

import java.awt.Rectangle;

import cave.fighter.GamePanel;
import cave.fighter.enums.GameStates;
import cave.fighter.utilities.Assets;
import cave.fighter.utilities.Constants;

public class MageBoss extends Boss {

	private int counter;
	private int index;
	private boolean moving;
	private int delay;

	public MageBoss() {
		super(Constants.BOSS_X, Constants.BOSS_Y, Constants.MAGE_SPEED,
				Constants.MAGE_HEALTH, Constants.MAGE_DAMAGE,
				Constants.MAGE_WIDTH, Constants.MAGE_HEIGHT);

		counter = 0;
		index = 1;
		moving = false;

		setImage(Assets.mageBoss);
		getBossAnimation().addFrame(getImage(), 100);

		for (int i = 0; i < 4; i++) {
			bossHitBoxes.add(new Rectangle(0, 0, 0, 0));
		}
	}

	@Override
	public void update() {
		if (isSpawning()) {

			spawn();

			// The after spawn
			if (getCounter() >= Constants.MAGE_SPAWN_COUNT) {
				setSpawning(false);
				setBossAnimation(Assets.mageBossAnim.clone());
				GamePanel.gameState = GameStates.BOSS_BATTLE;
			}
		} else {

			if (getHealth() <= 0) {
				GamePanel.gameState = GameStates.GAME_OVER;
			}

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
				case 4:
					setSpawnEnemy(true);
					moving = false;
					counter = 0;
					delay += Constants.DELAY_INCREMENT;
				}
			} else {

				setSpawnEnemy(false);

				if (counter == delay) {
					moving = true;
					index = (int) (Math.random() * Constants.MOVE_POSSIBILITIES);
					counter = 0;
					setSpeed(Constants.SPEED_DEFLATER - getHealth()
							/ Constants.MAGE_DEFLATER);
					delay = (Constants.DELAY_MULTIPLIER
							* (Constants.SPEED_DEFLATER - getSpeed()) + Constants.DELAY_INCREMENT);
				}
			}

			bossHitBoxes.get(0).setRect(
					getX() + Constants.MAGE_HITBOX_X_DISPLACE_1,
					getY() + Constants.MAGE_HITBOX_Y_DISPLACE_1,
					Constants.MAGE_WIDTH_1, Constants.MAGE_HEIGHT_1);
			bossHitBoxes.get(1).setRect(
					getX() + Constants.MAGE_HITBOX_X_DISPLACE_2,
					getY() + Constants.MAGE_HITBOX_Y_DISPLACE_2,
					Constants.MAGE_WIDTH_2, Constants.MAGE_HEIGHT_2);
			bossHitBoxes.get(2).setRect(
					getX() + Constants.MAGE_HITBOX_X_DISPLACE_3,
					getY() + Constants.MAGE_HITBOX_Y_DISPLACE_3,
					Constants.MAGE_WIDTH_3, Constants.MAGE_HEIGHT_3);
			bossHitBoxes.get(3).setRect(
					getX() + Constants.MAGE_HITBOX_X_DISPLACE_4,
					getY() + Constants.MAGE_HITBOX_Y_DISPLACE_4,
					Constants.MAGE_WIDTH_4, Constants.MAGE_HEIGHT_4);
			counter++;
		}
	}
}
package cave.fighter.boss;

import java.awt.Rectangle;

import cave.fighter.GamePanel;
import cave.fighter.enums.GameStates;
import cave.fighter.utilities.Assets;
import cave.fighter.utilities.Constants;

public class ArchMageBoss extends Boss {

	private int counter;
	private int index;
	private boolean moving;
	private int delay;

	public ArchMageBoss() {
		super(Constants.BOSS_X, Constants.BOSS_Y, Constants.ARCH_MAGE_SPEED,
				Constants.ARCH_MAGE_HEALTH, Constants.ARCH_MAGE_DAMAGE,
				Constants.ARCH_MAGE_WIDTH, Constants.ARCH_MAGE_HEIGHT);

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

			spawn();

			// The after spawn
			if (getCounter() >= Constants.ARCH_MAGE_SPAWN_COUNT) {
				setSpawning(false);
				setBossAnimation(Assets.archMageBossAnim.clone());
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
							/ Constants.ARCH_MAGE_DEFLATER);
					delay = (Constants.DELAY_MULTIPLIER
							* (Constants.SPEED_DEFLATER - getSpeed()) + Constants.DELAY_INCREMENT);
				}
			}

			bossHitBoxes.get(0).setRect(
					getX() + Constants.ARCH_MAGE_HITBOX_X_DISPLACE_1,
					getY() + Constants.ARCH_MAGE_HITBOX_Y_DISPLACE_1, Constants.ARCH_MAGE_WIDTH_1, Constants.ARCH_MAGE_HEIGHT_1);
			bossHitBoxes.get(1).setRect(
					getX() + Constants.ARCH_MAGE_HITBOX_X_DISPLACE_2,
					getY() + Constants.ARCH_MAGE_HITBOX_Y_DISPLACE_2, Constants.ARCH_MAGE_WIDTH_2, Constants.ARCH_MAGE_HEIGHT_2);
			bossHitBoxes.get(2).setRect(
					getX() + Constants.ARCH_MAGE_HITBOX_X_DISPLACE_3,
					getY() + Constants.ARCH_MAGE_HITBOX_Y_DISPLACE_3, Constants.ARCH_MAGE_WIDTH_3, Constants.ARCH_MAGE_HEIGHT_3);
			counter++;
		}
	}
}
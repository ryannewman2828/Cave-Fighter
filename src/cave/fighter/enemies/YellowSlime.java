package cave.fighter.enemies;

import cave.fighter.character.MainCharacter;
import cave.fighter.utilities.Assets;
import cave.fighter.utilities.Constants;

public class YellowSlime extends Enemy {

	private boolean chargeLeft = false;
	private boolean chargeRight = false;

	public YellowSlime(int x, int y, int speed, int health, int damage) {
		super(x, y, speed, health, damage);

		setEnemyAnimation(Assets.yellowSlimeAnim.clone());
		setImage(Assets.yellowSlime);
	}

	@Override
	public void update() {
		if (isSpawning()) {

			fade();

			// The after spawn
			if (getCounter() >= Constants.ENEMY_SPAWN_TIME) {
				setSpawning(false);
				setEnemyAnimation(Assets.yellowSlimeAnim.clone());
			}

		} else {
			if (getHealth() <= 0) {
				setAlive(false);
			}

			if (MainCharacter.getCharacterInstance().getY() > getY()
					+ Constants.YELLOW_SLIME_CHARGE_OFFSET
					&& (!chargeLeft && !chargeRight)) {
				setY(getY() + getSpeed());
			} else if (MainCharacter.getCharacterInstance().getY() < getY()
					- Constants.YELLOW_SLIME_CHARGE_OFFSET
					&& (!chargeLeft && !chargeRight)) {
				setY(getY() - getSpeed());
			} else {
				if (MainCharacter.getCharacterInstance().getX() >= getX()
						&& !chargeLeft) {
					chargeRight = true;
				}
				if (MainCharacter.getCharacterInstance().getX() < getX()
						&& !chargeRight) {
					chargeLeft = true;
				}
			}
			if (chargeLeft) {
				setX(getX() - getSpeed() - Constants.YELLOW_SLIME_EXTRA_SPEED);
				if (getX() <= Constants.YELLOW_SLIME_CHARGE_STOP_LEFT) {
					chargeLeft = false;
				}
			} else if (chargeRight) {
				setX(getX() + getSpeed() + Constants.YELLOW_SLIME_EXTRA_SPEED);
				if (getX() >= Constants.YELLOW_SLIME_CHARGE_STOP_RIGHT) {
					chargeRight = false;
				}
			}

			enemyHitBox
					.setRect(getX() + Constants.YELLOW_SLIME_X_DISPLACE, getY()
							+ Constants.YELLOW_SLIME_Y_DISPLACE,
							Constants.YELLOW_SLIME_WIDTH,
							Constants.YELLOW_SLIME_HEIGHT);
		}
	}
}
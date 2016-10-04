package cave.fighter.enemies;

import cave.fighter.character.MainCharacter;
import cave.fighter.utilities.Assets;
import cave.fighter.utilities.Constants;

public class GreenSlime extends Enemy {

	private int a = (int) ((Math.random() * Constants.GREEN_SLIME_MOVE_MULTI_1) + 1)
			* Constants.GREEN_SLIME_COOLDOWN_MULT;
	private int b = (int) ((Math.random() * Constants.GREEN_SLIME_MOVE_MULTI_1) + 1)
			* Constants.GREEN_SLIME_COOLDOWN_MULT;
	private boolean c = true;
	private boolean d = false;

	public GreenSlime(int x, int y, int speed, int health, int damage) {
		super(x, y, speed, health, damage);

		setEnemyAnimation(Assets.greenSlimeAnim.clone());
		setImage(Assets.greenSlime);
	}

	@Override
	public void update() {
		if (isSpawning()) {

			// Spawning
			fade();

			// The after spawn
			if (getCounter() >= Constants.ENEMY_SPAWN_TIME) {
				setSpawning(false);
				setEnemyAnimation(Assets.greenSlimeAnim.clone());
			}
		} else {
			if (getHealth() <= 0) {
				setAlive(false);
			}

			if (c) {
				if (MainCharacter.getCharacterInstance().getX() > getX()) {
					setX(getX() + getSpeed());
				} else if (MainCharacter.getCharacterInstance().getX() < getX()) {
					setX(getX() - getSpeed());
				}
				if (MainCharacter.getCharacterInstance().getY() > getY()) {
					setY(getY() + getSpeed());
				} else if (MainCharacter.getCharacterInstance().getY() < getY()) {
					setY(getY() - getSpeed());
				}
			}
			if (a > 0 && !d) {
				a--;
				if (a == 0) {
					d = true;
					c = false;
					b = (int) ((Math.random() * Constants.GREEN_SLIME_MOVE_MULTI_1) + 1)
							* Constants.GREEN_SLIME_COOLDOWN_MULT;
				}
			}
			if (b > 0 && !c) {
				b--;
				if (b == 0) {
					c = true;
					d = false;
					a = (int) ((Math.random() * Constants.GREEN_SLIME_MOVE_MULTI_2) + 1)
							* Constants.GREEN_SLIME_COOLDOWN_MULT;
				}
			}

			enemyHitBox.setRect(getX() + Constants.GREEN_SLIME_X_DISPLACE,
					getY() - Constants.GREEN_SLIME_Y_DISPLACE,
					Constants.GREEN_SLIME_WIDTH, Constants.GREEN_SLIME_HEIGHT);
		}
	}
}
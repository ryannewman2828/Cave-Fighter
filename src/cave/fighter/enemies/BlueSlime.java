package cave.fighter.enemies;

import cave.fighter.utilities.Assets;
import cave.fighter.utilities.Constants;

public class BlueSlime extends Enemy {

	public BlueSlime(int x, int y, int speed, int health, int damage) {
		super(x, y, speed, health, damage);
		
		setEnemyAnimation(Assets.blueSlimeAnim.clone());
		setImage(Assets.blueSlime);
	}

	@Override
	public void update() {
		if (isSpawning()) {

			fade();

			//The after spawn
			if (getCounter() >= Constants.ENEMY_SPAWN_TIME) {
				setSpawning(false);
				setEnemyAnimation(Assets.blueSlimeAnim.clone());
			}
		} else {

			if (getHealth() <= 0) {
				setAlive(false);
			}
			
			enemyHitBox.setRect(getX() - 16, getY() - 12, 32, 24);
		}
	}
}
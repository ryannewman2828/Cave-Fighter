package cave.fighter.enemies;

import cave.fighter.utilities.Assets;

public class Tentacle extends Enemy {
	
	public Tentacle(int x, int y, int speed, int health, int damage) {
		super(x, y, speed, health, damage);

		setSpawning(true);
		setEnemyAnimation(Assets.tentacleEntrance.clone());
	}

	@Override
	public void update() {
		if (isSpawning()) {

			setCounter(getCounter() + 1);
			
			if (getCounter() >= 80) {
				setSpawning(false);
				setEnemyAnimation(Assets.tentacle.clone());
			}
		} else {

			if (getHealth() <= 0) {
				setAlive(false);
			}

			enemyHitBox.setRect(getX() - 12, getY() - 45, 25, 90);
		}
	}
}
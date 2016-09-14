package cave.fighter.enemies;

import cave.fighter.character.MainCharacter;
import cave.fighter.utilities.Assets;
import cave.fighter.utilities.Constants;

public class RedSlime extends Enemy {

	private int a = (int) (Math.random() * 4) * 30 + 30;
	private int b = (int) (Math.random() * 4) * 30 + 30;
	private boolean c = true;
	private boolean d = false;

	public RedSlime(int x, int y, int speed, int health, int damage) {
		super(x, y, speed, health, damage);
		
		setEnemyAnimation(Assets.redSlimeAnim.clone());
		setImage(Assets.redSlime);
	}

	@Override
	public void update() {
		if (isSpawning()) {

			//Spawning
			fade();

			//The after spawn
			if (getCounter() >= Constants.ENEMY_SPAWN_TIME) {
				setSpawning(false);
				setEnemyAnimation(Assets.redSlimeAnim.clone());
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
					b = (int) (Math.random() * 4) * 30 + 30;
				}
			}
			if (b > 0 && !c) {
				b--;
				if (b == 0) {
					c = true;
					d = false;
					a = (int) (Math.random() * 6) * 30 + 30;
				}
			}

			enemyHitBox.setRect(getX() - 14, getY() - 10, 27, 21);
		}
	}
}
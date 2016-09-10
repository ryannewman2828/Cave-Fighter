package cave.fighter.enemies;

import cave.fighter.character.MainCharacter;
import cave.fighter.utilities.Assets;

public class RedSlime extends Enemy {

	private int a = (int) (Math.random() * 4) * 30 + 30;
	private int b = (int) (Math.random() * 4) * 30 + 30;
	private boolean c = true;
	private boolean d = false;

	public RedSlime(int x, int y, int speed, int health, int damage) {
		super(x, y, speed, health, damage);
		
		setEnemyAnimation(Assets.redSlimeAnim);
		setImage(Assets.redSlime);
	}

	@Override
	public void update() {
		if (isSpawning()) {

			//Spawning
			fade();

			//The after spawn
			if (getCounter() >= 120) {
				setSpawning(false);
				setEnemyAnimation(Assets.redSlimeAnim);
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
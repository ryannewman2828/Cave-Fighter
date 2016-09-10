package cave.fighter.enemies;

import cave.fighter.character.MainCharacter;
import cave.fighter.utilities.Assets;

public class YellowSlime extends Enemy {

	private boolean chargeLeft = false;
	private boolean chargeRight = false;

	public YellowSlime(int x, int y, int speed, int health, int damage) {
		super(x, y, speed, health, damage);
		
		setEnemyAnimation(Assets.yellowSlimeAnim);
		setImage(Assets.yellowSlime);
	}

	@Override
	public void update() {
		if(isSpawning()){
			
			//Spawning
			fade();
			
			//The after spawn
			if (getCounter() >= 120) {
				setSpawning(false);
				setEnemyAnimation(Assets.yellowSlimeAnim);
			}
			
		} else {
		if (getHealth() <= 0) {
			setAlive(false);
		}
		
		if (MainCharacter.getCharacterInstance().getY() > getY() + 5 && (!chargeLeft && !chargeRight)) {
			setY(getY() + getSpeed());
		} else if (MainCharacter.getCharacterInstance().getY() < getY() - 5
				&& (!chargeLeft && !chargeRight)) {
			setY(getY() - getSpeed());
		} else {
			if (MainCharacter.getCharacterInstance().getX() >= getX() && !chargeLeft) {
				chargeRight = true;
			}
			if (MainCharacter.getCharacterInstance().getX() < getX() && !chargeRight) {
				chargeLeft = true;
			}
		}
		if (chargeLeft) {
			setX(getX() - getSpeed() - 3);
			if (getX() <= 100) {
				chargeLeft = false;
			}
		} else if (chargeRight) {
			setX(getX() + (getSpeed() + 3));
			if (getX() >= 700) {
				chargeRight = false;
			}
		}

		enemyHitBox.setRect(getX() - 17, getY() - 10, 33, 21);
		}
	}
}
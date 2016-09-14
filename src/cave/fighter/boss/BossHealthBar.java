package cave.fighter.boss;

import cave.fighter.utilities.Constants;

public class BossHealthBar {

	private int health;
	private int size;

	public BossHealthBar(int health) {
		this.health = health;
		size = 0;
	}

	public void update(int curHealth) {
		size = (int) (((double) curHealth / health) * Constants.BOSS_BAR_MAX);
	}

	public void incrementBar() {
		if (size < Constants.BOSS_BAR_MAX) {
			size += Constants.BOSS_BAR_INCREMENT;
		}
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
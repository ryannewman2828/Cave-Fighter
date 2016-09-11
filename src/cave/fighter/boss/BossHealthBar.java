package cave.fighter.boss;

public class BossHealthBar {

	private int health;
	private int size = 0;

	public BossHealthBar(int health) {
		this.health = health;
	}

	public void update(int curHealth) {
		size = (int) (((double) curHealth / health) * 200);
	}

	public void incrementBar() {
		if (size < 200) {
			size += 2;
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
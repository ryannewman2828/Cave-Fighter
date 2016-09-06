package cave.fighter.boss;

import game.Enemy;

import java.awt.Rectangle;
import java.util.ArrayList;

public abstract class Boss {

	public ArrayList<Rectangle> bossHitBoxes;

	public Boss() {
		// TODO Auto-generated constructor stub
	}

	public void update() {
		// TODO Auto-generated method stub
		
	}

	public boolean isSpawnEnemy() {
		// TODO Auto-generated method stub
		return false;
	}

	public int getDamage() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void damageBoss(int damage) {
		// TODO Auto-generated method stub
		
	}

	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Enemy getBossAnimation() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	public BossHealthBar getHpBar() {
		// TODO Auto-generated method stub
		return new BossHealthBar();
	}

	public class BossHealthBar{
		public int getSize(){
			return 0;
		}
	}
	
}

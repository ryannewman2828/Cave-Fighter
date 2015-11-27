/**
 * Ryan Newman
 * Cave Fighter
 * Version 1.1
 * November 26 2015 
*/
package game;

import java.awt.Image;
import java.awt.Rectangle;

public class BossHealthBar {

	//Class variables
	private int health;
	private int size = 0;

	public BossHealthBar(int health) {
		
		//Contructs the health bar
		this.health = health;
	}

	public void update(int curHealth) {
		//Decreases size as health decreases
		size = (int) (((double) curHealth / health) * 200);
	}

	//For the initialization of the health bar
	public void incrementBar() {
		if (size < 200) {
			size += 2;
		}
	}

	/**
	 * @return the health
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * @param health the health to set
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}
}
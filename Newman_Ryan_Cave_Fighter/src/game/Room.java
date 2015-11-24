/**
 * Ryan Newman
 * ICS4U - 1
 * Cave Fighter
 * June 10 2015 
*/
package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import animation.framework.Animation;

public class Room {

	// Connects this room to other rooms
	private Room leftRoom;
	private Room aboveRoom;
	private Room rightRoom;
	private Room bottomRoom;

	// Indicators for if there are adjacent rooms
	private boolean hasLeft = false;
	private boolean hasAbove = false;
	private boolean hasRight = false;
	private boolean hasBottom = false;

	// Are all the enemies gone yet?
	private boolean roomCleared = false;

	//Is this the boss room?
	private boolean bossRoom = false;

	//Is the boss collided?
	private boolean bossCollide;

	// Holds all the enemies
	private ArrayList<Enemy> enemies;

	//Holds the boss
	private Boss boss;

	// Object to create enemies randomly in the room
	private EnemyCreator ec;

	//Main character
	private MainCharacter character;

	//The graphics
	Graphics g2d;

	//The room image
	private Image originalImage;
	private Image image = new BufferedImage(800, 480,
			BufferedImage.TYPE_INT_ARGB);

	// The spawn zones for a single room in the map
	public Rectangle spawnTop = new Rectangle(361, 35, 90, 10);
	public Rectangle spawnBot = new Rectangle(361, 470, 90, 10);
	public Rectangle spawnLeft = new Rectangle(0, 220, 10, 80);
	public Rectangle spawnRight = new Rectangle(790, 220, 10, 80);

	// The block zones for the spawns of a single room
	public Rectangle blockTop = new Rectangle(360, 80, 90, 10);
	public Rectangle blockBot = new Rectangle(360, 460, 90, 10);
	public Rectangle blockLeft = new Rectangle(10, 220, 10, 80);
	public Rectangle blockRight = new Rectangle(780, 220, 10, 80);

	// Powerup variables
	private Image powerUp;
	private Rectangle powerUpHitBox = new Rectangle(0, 0, 0, 0);
	private int powerUpID;

	// Constructor for the normal rooms
	public Room(int difficulty, MainCharacter character) {
		this.character = character;
		ec = new EnemyCreator(difficulty, false, character);
		enemies = ec.getEnemyList();
		redrawRoom();
	}

	// Constructor for the starting room
	public Room(MainCharacter character) {
		this.character = character;
		enemies = new ArrayList<Enemy>();
		redrawRoom();
	}

	// Constructor for the boss room
	public Room(int difficulty, MainCharacter character, boolean spawning) {
		this.character = character;
		enemies = new ArrayList<Enemy>();
		ec = new EnemyCreator(difficulty, spawning, character);
		redrawRoom();
	}

	public void update() {
		
		//Updates the boss
		if (bossRoom) {
			boss.update();
			if (boss.isSpawnEnemy()) {
				enemies.add(ec.spawnEnemy());
			}
		}

		// Handles collisions between enemies and the main character
		ArrayList<Enemy> enemy = getEnemies();
		for (int i = 0; i < enemy.size(); i++) {
			if (character.rect.intersects(enemy.get(i).enemyHitBox)
					|| character.rect2.intersects(enemy.get(i).enemyHitBox)) {
				character.getHurt(enemy.get(i).getDamage());
			}
		}

		// Handles collisions between the enemy and the main character
		ArrayList<Projectile> proj = character.getProjectiles();
		for (int i = 0; i < enemy.size(); i++) {
			for (int j = 0; j < proj.size(); j++) {
				if (proj.get(j).bulletHitBox
						.intersects(enemy.get(i).enemyHitBox)) {
					enemy.get(i).damageEnemy(character.getDamage());
					proj.get(j).setActive(false);
				}
			}
		}

		// Handles collisions between the powerup and the player
		if (character.rect.intersects(powerUpHitBox)
				|| character.rect2.intersects(powerUpHitBox)) {
			powerUpHitBox.setRect(0, 0, 0, 0);
			powerUp = null;
			switch (powerUpID) {
			case 0:
				character.increaseHealth();
				break;
			case 1:
				character.heal();
				break;
			case 2:
				character.increaseSpeed();
				break;
			case 3:
				character.increaseDamage();
				break;
			case 4:
				character.decreaseFireRate();
				break;
			default:
				break;
			}
		}

		//Handles the collision between the boss and the player
		if (isBossRoom()) {
			bossCollide = false;
			for (int i = 0; i < getBoss().bossHitBoxes.size(); i++) {
				if (getBoss().bossHitBoxes.get(i).intersects(character.rect)
						|| getBoss().bossHitBoxes.get(i).intersects(
								character.rect2)) {
					bossCollide = true;
				}
			}
			if (bossCollide) {
				character.getHurt(boss.getDamage());
			}
			bossCollide = false;
			for (int i = 0; i < getBoss().bossHitBoxes.size(); i++) {
				for (int j = 0; j < proj.size(); j++) {
					if (getBoss().bossHitBoxes.get(i).intersects(
							proj.get(j).bulletHitBox)) {
						proj.remove(j);
						bossCollide = true;
					}
				}
			}
			if (bossCollide) {
				getBoss().damageBoss(character.getDamage());
			}
		}

		for (int i = 0; i < enemies.size(); i++) {
			// Updates the enemies in the current map
			enemies.get(i).update();

			// if the enemies are dead, they get removed from the list
			if (!enemies.get(i).isAlive()) {
				enemies.remove(i);

				// no enemies, room is cleared
				if (enemies.isEmpty()) {
					setRoomCleared(true);
					if (!isBossRoom()) {
						// spawns the powerup for the map
						powerUpHitBox.setRect(400, 240, 40, 40);
						powerUpID = (int) (Math.random() * 8);
						try {
							if (powerUpID < 5)
								powerUp = ImageIO.read(new File("data/icon"
										+ powerUpID + ".png"));
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

	}

	//Method to draw the individual parts of the room onto the main image
	public void redrawRoom() {
		g2d = (Graphics2D) image.getGraphics();
		try {
			originalImage = ImageIO.read(new File("data/background.png"));
			g2d.drawImage(originalImage, 0, 0, null);
			if (hasRight && (roomCleared || rightRoom.isRoomCleared())) {
				originalImage = ImageIO.read(new File("data/rightDoor.png"));
				g2d.drawImage(originalImage, 730, 212, null);
			}
			if (hasLeft && (roomCleared || leftRoom.isRoomCleared())) {
				originalImage = ImageIO.read(new File("data/leftDoor.png"));
				g2d.drawImage(originalImage, 31, 212, null);
			}
			if (hasBottom && (roomCleared || bottomRoom.isRoomCleared())) {
				originalImage = ImageIO.read(new File("data/downDoor.png"));
				g2d.drawImage(originalImage, 360, 418, null);
			}
			if (hasAbove && (roomCleared || aboveRoom.isRoomCleared())) {
				if (aboveRoom.isBossRoom()) {
					originalImage = ImageIO.read(new File("data/bossDoor.png"));
					g2d.drawImage(originalImage, 360, 0, null);
				} else {
					originalImage = ImageIO.read(new File("data/upDoor.png"));
					g2d.drawImage(originalImage, 360, 0, null);
				}
			} else if (hasAbove) {
				originalImage = ImageIO.read(new File("data/upDoor2.png"));
				g2d.drawImage(originalImage, 360, 0, null);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the leftRoom
	 */
	public Room getLeftRoom() {
		return leftRoom;
	}

	/**
	 * @param leftRoom
	 *            the leftRoom to set
	 */
	public void setLeftRoom(Room leftRoom) {
		this.leftRoom = leftRoom;
		hasLeft = true;
	}

	/**
	 * @return the aboveRoom
	 */
	public Room getAboveRoom() {
		return aboveRoom;
	}

	/**
	 * @param aboveRoom
	 *            the aboveRoom to set
	 */
	public void setAboveRoom(Room aboveRoom) {
		this.aboveRoom = aboveRoom;
		hasAbove = true;
	}

	/**
	 * @return the rightRoom
	 */
	public Room getRightRoom() {
		return rightRoom;
	}

	/**
	 * @param rightRoom
	 *            the rightRoom to set
	 */
	public void setRightRoom(Room rightRoom) {
		this.rightRoom = rightRoom;
		hasRight = true;
	}

	/**
	 * @return the bottomRoom
	 */
	public Room getBottomRoom() {
		return bottomRoom;
	}

	/**
	 * @param bottomRoom
	 *            the bottomRoom to set
	 */
	public void setBottomRoom(Room bottomRoom) {
		this.bottomRoom = bottomRoom;
		hasBottom = true;
	}

	/**
	 * @return the hasLeft
	 */
	public boolean isHasLeft() {
		return hasLeft;
	}

	/**
	 * @return the hasAbove
	 */
	public boolean isHasAbove() {
		return hasAbove;
	}

	/**
	 * @return the hasRight
	 */
	public boolean isHasRight() {
		return hasRight;
	}

	/**
	 * @return the hasBottom
	 */
	public boolean isHasBottom() {
		return hasBottom;
	}

	/**
	 * @return the roomCleared
	 */
	public boolean isRoomCleared() {
		return roomCleared;
	}

	/**
	 * @param roomCleared
	 *            the roomCleared to set
	 */
	public void setRoomCleared(boolean roomCleared) {
		this.roomCleared = roomCleared;
		
		//Removes blockers
		if (hasAbove) {
			blockTop = new Rectangle(0, 0, 0, 0);
			aboveRoom.blockBot = new Rectangle(0, 0, 0, 0);
		}
		if (hasBottom) {
			blockBot = new Rectangle(0, 0, 0, 0);
			bottomRoom.blockTop = new Rectangle(0, 0, 0, 0);
		}
		if (hasLeft) {
			blockLeft = new Rectangle(0, 0, 0, 0);
			leftRoom.blockRight = new Rectangle(0, 0, 0, 0);
		}
		if (hasRight) {
			blockRight = new Rectangle(0, 0, 0, 0);
			rightRoom.blockLeft = new Rectangle(0, 0, 0, 0);
		}
		
		//Redraws room
		redrawRoom();
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	/**
	 * @return the character
	 */
	public MainCharacter getCharacter() {
		return character;
	}

	/**
	 * @param character
	 *            the character to set
	 */
	public void setCharacter(MainCharacter character) {
		this.character = character;
	}

	/**
	 * @return the bossRoom
	 */
	public boolean isBossRoom() {
		return bossRoom;
	}

	/**
	 * @param bossRoom
	 *            the bossRoom to set
	 */
	public void setBossRoom(boolean bossRoom) {
		this.bossRoom = bossRoom;
	}

	/**
	 * @return the boss
	 */
	public Boss getBoss() {
		return boss;
	}

	/**
	 * @param boss
	 *            the boss to set
	 */
	public void setBoss(Boss boss) {
		this.boss = boss;
	}

	public Image getImage() {
		return image;
	}

	/**
	 * @return the powerUp
	 */
	public Image getPowerUp() {
		return powerUp;
	}

	/**
	 * @param powerUp
	 *            the powerUp to set
	 */
	public void setPowerUp(Image powerUp) {
		this.powerUp = powerUp;
	}
}
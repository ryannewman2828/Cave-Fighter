package cave.fighter.environment;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import cave.fighter.boss.Boss;
import cave.fighter.boss.Shade;
import cave.fighter.character.MainCharacter;
import cave.fighter.character.Projectile;
import cave.fighter.enemies.Enemy;
import cave.fighter.enemies.EnemyFactory;
import cave.fighter.utilities.Assets;
import cave.fighter.utilities.Constants;

public class Room {

	private Room leftRoom;
	private Room aboveRoom;
	private Room rightRoom;
	private Room bottomRoom;

	private boolean hasLeft;
	private boolean hasAbove;
	private boolean hasRight;
	private boolean hasBottom;

	private int difficulty;
	private boolean roomCleared;
	private boolean bossRoom;
	private boolean bossCollide;
	private ArrayList<Enemy> enemies;
	private Boss boss;
	private MainCharacter character;

	// The graphics
	private Graphics g2d;

	private Rectangle spawnTop;
	private Rectangle spawnBot;
	private Rectangle spawnLeft;
	private Rectangle spawnRight;

	private Rectangle blockTop;
	private Rectangle blockBot;
	private Rectangle blockLeft;
	private Rectangle blockRight;

	private Rectangle powerUpHitBox;
	private int powerUpID;

	// Constructor for the normal rooms
	public Room(int difficulty) {
		this();
		enemies = EnemyFactory.getEnemyList(difficulty);
	}

	// Constructor for the starting room
	public Room() {
		character = MainCharacter.getCharacterInstance();
		enemies = new ArrayList<Enemy>();

		hasLeft = false;
		hasAbove = false;
		hasRight = false;
		hasBottom = false;

		roomCleared = false;
		bossRoom = false;

		spawnTop = new Rectangle(361, 35, 90, 10);
		spawnBot = new Rectangle(361, 470, 90, 10);
		spawnLeft = new Rectangle(0, 220, 10, 80);
		spawnRight = new Rectangle(790, 220, 10, 80);

		blockTop = new Rectangle(360, 80, 90, 10);
		blockBot = new Rectangle(360, 460, 90, 10);
		blockLeft = new Rectangle(10, 220, 10, 80);
		blockRight = new Rectangle(780, 220, 10, 80);
		
		powerUpHitBox = new Rectangle(0, 0, 0, 0);
		powerUpID = 7;

		redrawRoom();
	}

	// Constructor for the boss room
	public Room(int difficulty, boolean spawning) {
		this();
		this.difficulty = difficulty;
	}

	public void update() {
		
		if (bossRoom) {
			boss.update();
			if (boss.isSpawnEnemy()) {
				if(boss instanceof Shade){
					enemies.add(EnemyFactory.spawnTentacle());
				} else {
					enemies.add(EnemyFactory.spawnEnemy(difficulty));
				}
			}
		}

		// Handles collisions between enemies and the main character
		ArrayList<Enemy> enemy = getEnemies();
		for (int i = 0; i < enemy.size(); i++) {
			if (character.getRect().intersects(enemy.get(i).enemyHitBox)
					|| character.getRect2()
							.intersects(enemy.get(i).enemyHitBox)) {
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
		if (character.getRect().intersects(powerUpHitBox)
				|| character.getRect2().intersects(powerUpHitBox)) {
			powerUpHitBox.setRect(0, 0, 0, 0);
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
			powerUpID = 7;
		}

		// Handles the collision between the boss and the player
		if (isBossRoom()) {
			bossCollide = false;
			for (int i = 0; i < getBoss().bossHitBoxes.size(); i++) {
				if (getBoss().bossHitBoxes.get(i).intersects(
						character.getRect())
						|| getBoss().bossHitBoxes.get(i).intersects(
								character.getRect2())) {
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
			enemies.get(i).update();

			if (!enemies.get(i).isAlive()) {
				enemies.remove(i);

				if (enemies.isEmpty()) {
					setRoomCleared(true);
					if (!isBossRoom()) {
						powerUpHitBox.setRect(Constants.POWERUP_X, Constants.POWERUP_Y, Constants.POWERUP_SIZE, Constants.POWERUP_SIZE);
						powerUpID = (int) (Math.random() * 8);
					}
				}
			}
		}

	}

	// Method to draw the individual parts of the room onto the main image
	public void redrawRoom() {
		
		g2d = (Graphics2D) Assets.roomImage.getGraphics();
		g2d.drawImage(Assets.background, 0, 0, null);
		
		if (hasRight && (roomCleared || rightRoom.isRoomCleared())) {
			g2d.drawImage(Assets.rightDoor, 730, 212, null);
		}
		if (hasLeft && (roomCleared || leftRoom.isRoomCleared())) {
			g2d.drawImage(Assets.leftDoor, 31, 212, null);
		}
		if (hasBottom && (roomCleared || bottomRoom.isRoomCleared())) {
			g2d.drawImage(Assets.downDoor, 360, 418, null);
		}
		if (hasAbove && (roomCleared || aboveRoom.isRoomCleared())) {
			if (aboveRoom.isBossRoom()) {
				g2d.drawImage(Assets.bossDoor, 360, 0, null);
			} else {
				g2d.drawImage(Assets.upDoorOn, 360, 0, null);
			}
		} else if (hasAbove) {
			g2d.drawImage(Assets.upDoorOff, 360, 0, null);
		}
	}

	public Room getLeftRoom() {
		return leftRoom;
	}

	public void setLeftRoom(Room leftRoom) {
		this.leftRoom = leftRoom;
		hasLeft = true;
	}

	public Room getAboveRoom() {
		return aboveRoom;
	}

	public void setAboveRoom(Room aboveRoom) {
		this.aboveRoom = aboveRoom;
		hasAbove = true;
	}

	public Room getRightRoom() {
		return rightRoom;
	}

	public void setRightRoom(Room rightRoom) {
		this.rightRoom = rightRoom;
		hasRight = true;
	}

	public Room getBottomRoom() {
		return bottomRoom;
	}

	public void setBottomRoom(Room bottomRoom) {
		this.bottomRoom = bottomRoom;
		hasBottom = true;
	}

	public boolean isHasLeft() {
		return hasLeft;
	}

	public boolean isHasAbove() {
		return hasAbove;
	}

	public boolean isHasRight() {
		return hasRight;
	}

	public boolean isHasBottom() {
		return hasBottom;
	}

	public boolean isRoomCleared() {
		return roomCleared;
	}

	public void setRoomCleared(boolean roomCleared) {
		this.roomCleared = roomCleared;

		// Removes blockers
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

		redrawRoom();
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	public boolean isBossRoom() {
		return bossRoom;
	}

	public void setBossRoom(boolean bossRoom) {
		this.bossRoom = bossRoom;
	}

	public Boss getBoss() {
		return boss;
	}

	public void setBoss(Boss boss) {
		this.boss = boss;
	}

	public Image getPowerUp() {
		if(powerUpID < Assets.powerUp.length){
			return Assets.powerUp[powerUpID];
		}
		return new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
	}

	public Rectangle getSpawnTop() {
		return spawnTop;
	}

	public Rectangle getSpawnBot() {
		return spawnBot;
	}

	public Rectangle getSpawnLeft() {
		return spawnLeft;
	}

	public Rectangle getSpawnRight() {
		return spawnRight;
	}

	public Rectangle getBlockTop() {
		return blockTop;
	}

	public Rectangle getBlockBot() {
		return blockBot;
	}

	public Rectangle getBlockLeft() {
		return blockLeft;
	}

	public Rectangle getBlockRight() {
		return blockRight;
	}

	public void setBlockBot(Rectangle blockBot) {
		this.blockBot = blockBot;
	}
}
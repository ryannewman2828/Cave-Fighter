/**
 * Ryan Newman
 * ICS4U - 1
 * Cave Fighter
 * June 10 2015 
*/
package game;

import game.GamePanel.gameStates;
import game.GamePanel.moveStates;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;

public class Map {
	
	//rooms list
	private ArrayList<Room> rooms = new ArrayList<>();
	
	//Current room
	private Room activeRoom;
	
	//For creation of the map
	private final int GRID_SIZE;
	private int roomsLeft;
	private boolean[][] layout;
	private boolean bossRoomSpawned;
	private ArrayList<Integer> indexes = new ArrayList<>();
	
	// A random x and y co-ordinate
	int randX, randY;
	
	//Main character 
	private MainCharacter character;

	// The current move state
	public static moveStates move = moveStates.STATIC;
	
	// The bounds of a single room in the map
	public Rectangle boundTop1 = new Rectangle(0, 0, 360, 90);
	public Rectangle boundTop2 = new Rectangle(450, 0, 360, 90);
	public Rectangle boundBot1 = new Rectangle(0, 460, 360, 20);
	public Rectangle boundBot2 = new Rectangle(450, 460, 360, 20);
	public Rectangle boundLeft1 = new Rectangle(0, 0, 20, 220);
	public Rectangle boundLeft2 = new Rectangle(0, 300, 20, 180);
	public Rectangle boundRight1 = new Rectangle(780, 0, 20, 220);
	public Rectangle boundRight2 = new Rectangle(780, 300, 20, 180);

	public Map(int difficulty, int size) {

		//sets main character
		character = new MainCharacter();

		switch (size) {
		case 1:
			// Sets the size of the boolean grid to 5
			GRID_SIZE = 5;
			// there will be either 9, 10 or 11 rooms
			roomsLeft = (int) (Math.random() * 3) + 9;
			break;
		case 2:
			// Sets the size of the boolean grid to 7
			GRID_SIZE = 7;
			// there will be either 19, 20 or 21 rooms
			roomsLeft = (int) (Math.random() * 3) + 19;
			break;
		case 3:
			// Sets the size of the boolean grid to 11
			GRID_SIZE = 11;
			// there will be either 29, 30 or 31 rooms
			roomsLeft = (int) (Math.random() * 3) + 29;
			break;
		default:
			// if no size specified, no map created
			GRID_SIZE = 0;
			roomsLeft = 0;
		}

		// The boolean representation of the rooms orientation
		layout = new boolean[GRID_SIZE][GRID_SIZE];

		// Initializes all the room objects
		rooms.add(new Room(character));
		for (int i = 1; i < roomsLeft; i++) {
			rooms.add(new Room(difficulty, character));
		}

		// Starts the user in room indexed at 0
		activeRoom = rooms.get(0);

		// Randomizes the 2d array
		randomize();

		// Connects all the rooms
		connectRooms();

		// SPAWN BOSS ROOM
		while (!bossRoomSpawned) {
			randX = (int) (Math.random() * GRID_SIZE);
			randY = (int) (Math.random() * (GRID_SIZE - 2) + 1);
			if (!layout[randY][randX] && layout[randY + 1][randX]) {
				rooms.add(new BossRoom(character, difficulty));
				bossRoomSpawned = true;
				rooms.get(indexes.indexOf((randY + 1) * GRID_SIZE + randX))
						.setAboveRoom(rooms.get(rooms.size() - 1));
			}
		}

		// sets the initial room to cleared, meaning no enemies
		rooms.get(0).setRoomCleared(true);
	}

	public void update() {
		
		//Updates the active room
		activeRoom.update();
		
		//Handles character movement
		switch (move) {
		case DOWN:
			character.setAnimationIndexBody(0);
			character.setAnimationIndexHead(0);
			if (!character.rect.intersects(boundBot1)
					&& !character.rect.intersects(boundBot2)
					&& !character.rect.intersects(activeRoom.blockBot)) {
				character.move(0, character.getSpeed());
			}
			break;
		case LEFT:
			character.setAnimationIndexBody(1);
			character.setAnimationIndexHead(1);
			if (!character.rect.intersects(boundLeft1)
					&& !character.rect.intersects(boundLeft2)
					&& !character.rect.intersects(activeRoom.blockLeft)) {
				character.move(-character.getSpeed(), 0);
			}
			break;
		case RIGHT:
			character.setAnimationIndexBody(2);
			character.setAnimationIndexHead(2);
			if (!character.rect.intersects(boundRight1)
					&& !character.rect.intersects(boundRight2)
					&& !character.rect.intersects(activeRoom.blockRight)) {
				character.move(character.getSpeed(), 0);
			}
			break;
		case STATIC:
			break;
		case UP:
			character.setAnimationIndexBody(3);
			character.setAnimationIndexHead(3);
			if (!character.rect.intersects(boundTop1)
					&& !character.rect.intersects(boundTop2)
					&& !character.rect.intersects(activeRoom.blockTop)) {
				character.move(0, -character.getSpeed());
			}
			break;
		default:
			break;
		}

		// Handles the movement throughout the map
		if (character.rect.intersects(activeRoom.spawnBot)) {
			moveBelowRoom();
			character.setY(100);
			character.getProjectiles().clear();
			activeRoom.redrawRoom();
		} else if (character.rect2.intersects(activeRoom.spawnTop)) {
			moveAboveRoom();
			character.setY(450);
			character.getProjectiles().clear();
			activeRoom.redrawRoom();
		} else if (character.rect.intersects(activeRoom.spawnLeft)) {
			moveLeftRoom();
			character.setX(740);
			character.getProjectiles().clear();
			activeRoom.redrawRoom();
		} else if (character.rect.intersects(activeRoom.spawnRight)) {
			moveRightRoom();
			character.setX(40);
			character.getProjectiles().clear();
			activeRoom.redrawRoom();
		}
		
		//Updates the main character
		character.update();
	}
	
	//Connects the rooms
	private void connectRooms() {
		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {
				if (layout[i][j]) {
					if (i > 0 && layout[i - 1][j]) {
						rooms.get(indexes.indexOf(i * GRID_SIZE + j))
								.setAboveRoom(
										rooms.get(indexes.indexOf((i - 1)
												* GRID_SIZE + j)));
					}
					if (i < GRID_SIZE - 1 && layout[i + 1][j]) {
						rooms.get(indexes.indexOf(i * GRID_SIZE + j))
								.setBottomRoom(
										rooms.get(indexes.indexOf((i + 1)
												* GRID_SIZE + j)));
					}
					if (j > 0 && layout[i][j - 1]) {
						rooms.get(indexes.indexOf(i * GRID_SIZE + j))
								.setLeftRoom(
										rooms.get(indexes.indexOf(i * GRID_SIZE
												+ (j - 1))));
					}
					if (j < GRID_SIZE - 1 && layout[i][j + 1]) {
						rooms.get(indexes.indexOf(i * GRID_SIZE + j))
								.setRightRoom(
										rooms.get(indexes.indexOf(i * GRID_SIZE
												+ (j + 1))));
					}
				}
			}
		}
	}

	
	//Randomizes the room placement
	private void randomize() {

		// Sets 5 rooms up in the middle
		layout[GRID_SIZE / 2][GRID_SIZE / 2] = true;
		indexes.add((GRID_SIZE / 2) * GRID_SIZE + GRID_SIZE / 2);
		layout[(GRID_SIZE / 2) + 1][GRID_SIZE / 2] = true;
		indexes.add(((GRID_SIZE / 2) + 1) * GRID_SIZE + GRID_SIZE / 2);
		layout[GRID_SIZE / 2][(GRID_SIZE / 2) - 1] = true;
		indexes.add((GRID_SIZE / 2) * GRID_SIZE + (GRID_SIZE / 2) - 1);
		layout[GRID_SIZE / 2][(GRID_SIZE / 2) + 1] = true;
		indexes.add((GRID_SIZE / 2) * GRID_SIZE + (GRID_SIZE / 2) + 1);
		layout[(GRID_SIZE / 2) - 1][GRID_SIZE / 2] = true;
		indexes.add(((GRID_SIZE / 2) - 1) * GRID_SIZE + GRID_SIZE / 2);
		roomsLeft -= 5;

		// randomizes room placement
		while (roomsLeft > 0) {
			randX = (int) (Math.random() * GRID_SIZE);
			randY = (int) (Math.random() * GRID_SIZE);
			if (!layout[randY][randX]) {
				if (randX > 0 && layout[randY][randX - 1]) {
					layout[randY][randX] = true;
					roomsLeft--;
					indexes.add(randY * GRID_SIZE + randX);
				} else if (randX < GRID_SIZE - 1 && layout[randY][randX + 1]) {
					layout[randY][randX] = true;
					roomsLeft--;
					indexes.add(randY * GRID_SIZE + randX);
				} else if (randY < GRID_SIZE - 1 && layout[randY + 1][randX]) {
					layout[randY][randX] = true;
					roomsLeft--;
					indexes.add(randY * GRID_SIZE + randX);
				} else if (randY > 0 && layout[randY - 1][randX]) {
					layout[randY][randX] = true;
					roomsLeft--;
					indexes.add(randY * GRID_SIZE + randX);
				}
			}
		}

		// Sorts the indexes array to match grid indexes with room indexes
		Collections.sort(indexes);
	}

	public void moveLeftRoom() {
		activeRoom = activeRoom.getLeftRoom();
	}

	public void moveRightRoom() {
		activeRoom = activeRoom.getRightRoom();
	}

	public void moveAboveRoom() {
		activeRoom = activeRoom.getAboveRoom();
		if (activeRoom.isBossRoom()) {
			GamePanel.setGameState(gameStates.BOSS_SPAWN);
			move = moveStates.STATIC;
			activeRoom.blockBot = new Rectangle(360, 460, 90, 10);
		}
	}

	public void moveBelowRoom() {
		activeRoom = activeRoom.getBottomRoom();
	}

	public Room getActiveRoom() {
		return activeRoom;
	}

	/**
	 * @return the character
	 */
	public MainCharacter getCharacter() {
		return character;
	}

	/**
	 * @param character the character to set
	 */
	public void setCharacter(MainCharacter character) {
		this.character = character;
	}

	//Prints map to console
	public void print() {
		for (int i = 0; i < layout.length; i++) {
			for (int j = 0; j < layout.length; j++) {
				if (layout[i][j]) {
					System.out.print(" ");
				} else {
					System.out.print("X");
				}
			}
			System.out.println();
		}
	}
}
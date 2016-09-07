package cave.fighter.environment;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;

import cave.fighter.GamePanel;
import cave.fighter.character.MainCharacter;
import cave.fighter.enums.GameStates;
import cave.fighter.enums.MoveStates;
import cave.fighter.utilities.Constants;

public class Map {

	private ArrayList<Room> rooms;
	private Room activeRoom;

	int randX, randY;
	private MainCharacter character;

	public MoveStates move = MoveStates.STATIC;

	private Rectangle boundTop1;
	private Rectangle boundTop2;
	private Rectangle boundBot1;
	private Rectangle boundBot2;
	private Rectangle boundLeft1;
	private Rectangle boundLeft2;
	private Rectangle boundRight1;
	private Rectangle boundRight2;

	// For creation of the map
	private final int GRID_SIZE;
	private int roomsLeft;
	private boolean[][] layout;
	private boolean bossRoomSpawned;
	private ArrayList<Integer> indexes;

	public Map(int difficulty, int size) {

		character = MainCharacter.getCharacterInstance();
		rooms = new ArrayList<>();
		indexes = new ArrayList<>();
		boundTop1 = new Rectangle(0, 0, 360, 90);
		boundTop2 = new Rectangle(450, 0, 360, 90);
		boundBot1 = new Rectangle(0, 460, 360, 20);
		boundBot2 = new Rectangle(450, 460, 360, 20);
		boundLeft1 = new Rectangle(0, 0, 20, 220);
		boundLeft2 = new Rectangle(0, 300, 20, 180);
		boundRight1 = new Rectangle(780, 0, 20, 220);
		boundRight2 = new Rectangle(780, 300, 20, 180);

		switch (size) {
		case 1:
			GRID_SIZE = 7;
			roomsLeft = (int) (Math.random() * Constants.MAP_ROOM_OFFSET) + Constants.MAP_ROOM_SMALL;
			break;
		case 2:
			GRID_SIZE = 9;
			roomsLeft = (int) (Math.random() * Constants.MAP_ROOM_OFFSET) + Constants.MAP_ROOM_MEDIUM;
			break;
		case 3:
			GRID_SIZE = 11;
			roomsLeft = (int) (Math.random() * Constants.MAP_ROOM_OFFSET) + Constants.MAP_ROOM_LARGE;
			break;
		default:
			GRID_SIZE = 0;
			roomsLeft = 0;
		}

		layout = new boolean[GRID_SIZE][GRID_SIZE];
		rooms.add(new Room());
		for (int i = 1; i < roomsLeft; i++) {
			rooms.add(new Room(difficulty));
		}
		activeRoom = rooms.get(0);

		randomize();

		connectRooms();

		while (!bossRoomSpawned) {
			randX = (int) (Math.random() * GRID_SIZE);
			randY = (int) (Math.random() * (GRID_SIZE - 2) + 1);
			if (!layout[randY][randX] && layout[randY + 1][randX]) {
				rooms.add(new BossRoom(difficulty));
				bossRoomSpawned = true;
				rooms.get(indexes.indexOf((randY + 1) * GRID_SIZE + randX))
						.setAboveRoom(rooms.get(rooms.size() - 1));
			}
		}

		rooms.get(0).setRoomCleared(true);
	}

	public void update() {

		activeRoom.update();

		// Handles character movement
		switch (move) {
		case DOWN:
			character.setAnimationIndexBody(0);
			character.setAnimationIndexHead(0);
			if (!character.getRect().intersects(boundBot1)
					&& !character.getRect().intersects(boundBot2)
					&& !character.getRect()
							.intersects(activeRoom.getBlockBot())) {
				character.move(0, character.getSpeed());
			}
			break;
		case LEFT:
			character.setAnimationIndexBody(1);
			character.setAnimationIndexHead(1);
			if (!character.getRect().intersects(boundLeft1)
					&& !character.getRect().intersects(boundLeft2)
					&& !character.getRect().intersects(
							activeRoom.getBlockLeft())) {
				character.move(-character.getSpeed(), 0);
			}
			break;
		case RIGHT:
			character.setAnimationIndexBody(2);
			character.setAnimationIndexHead(2);
			if (!character.getRect().intersects(boundRight1)
					&& !character.getRect().intersects(boundRight2)
					&& !character.getRect().intersects(
							activeRoom.getBlockRight())) {
				character.move(character.getSpeed(), 0);
			}
			break;
		case STATIC:
			break;
		case UP:
			character.setAnimationIndexBody(3);
			character.setAnimationIndexHead(3);
			if (!character.getRect().intersects(boundTop1)
					&& !character.getRect().intersects(boundTop2)
					&& !character.getRect()
							.intersects(activeRoom.getBlockTop())) {
				character.move(0, -character.getSpeed());
			}
			break;
		default:
			break;
		}

		// Handles the movement throughout the map
		if (character.getRect().intersects(activeRoom.getSpawnBot())) {
			moveBelowRoom();
			character.setY(Constants.MAP_ROOM_Y_START_TOP);
			character.getProjectiles().clear();
			activeRoom.redrawRoom();
		} else if (character.getRect2().intersects(activeRoom.getSpawnTop())) {
			moveAboveRoom();
			character.setY(Constants.MAP_ROOM_Y_START_BOT);
			character.getProjectiles().clear();
			activeRoom.redrawRoom();
		} else if (character.getRect().intersects(activeRoom.getSpawnLeft())) {
			moveLeftRoom();
			character.setX(Constants.MAP_ROOM_X_START_RIGHT);
			character.getProjectiles().clear();
			activeRoom.redrawRoom();
		} else if (character.getRect().intersects(activeRoom.getSpawnRight())) {
			moveRightRoom();
			character.setX(Constants.MAP_ROOM_X_START_LEFT);
			character.getProjectiles().clear();
			activeRoom.redrawRoom();
		}

		character.update();
	}

	// Connects the rooms
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
		print();
	}

	// Randomizes the room placement
	private void randomize() {

		ArrayList<Integer> openIndexes = new ArrayList<Integer>();
		int randIndex;

		// Sets 5 rooms up in the middle
		layout[GRID_SIZE / 2][GRID_SIZE / 2] = true;
		indexes.add((GRID_SIZE / 2) * GRID_SIZE + GRID_SIZE / 2);

		layout[(GRID_SIZE / 2) + 1][GRID_SIZE / 2] = true;
		indexes.add(((GRID_SIZE / 2) + 1) * GRID_SIZE + GRID_SIZE / 2);
		openIndexes.add((((GRID_SIZE / 2) + 2) * GRID_SIZE + GRID_SIZE / 2));
		openIndexes
				.add((((GRID_SIZE / 2) + 1) * GRID_SIZE + (GRID_SIZE / 2 + 1)));
		openIndexes
				.add((((GRID_SIZE / 2) + 1) * GRID_SIZE + (GRID_SIZE / 2 - 1)));

		layout[GRID_SIZE / 2][(GRID_SIZE / 2) - 1] = true;
		indexes.add((GRID_SIZE / 2) * GRID_SIZE + (GRID_SIZE / 2) - 1);
		openIndexes.add(((GRID_SIZE / 2) * GRID_SIZE + (GRID_SIZE / 2) - 2));

		layout[GRID_SIZE / 2][(GRID_SIZE / 2) + 1] = true;
		indexes.add((GRID_SIZE / 2) * GRID_SIZE + (GRID_SIZE / 2) + 1);
		openIndexes.add(((GRID_SIZE / 2) * GRID_SIZE + (GRID_SIZE / 2) + 2));

		layout[(GRID_SIZE / 2) - 1][GRID_SIZE / 2] = true;
		indexes.add(((GRID_SIZE / 2) - 1) * GRID_SIZE + GRID_SIZE / 2);
		openIndexes.add((((GRID_SIZE / 2) - 2) * GRID_SIZE + GRID_SIZE / 2));
		openIndexes
				.add((((GRID_SIZE / 2) - 1) * GRID_SIZE + (GRID_SIZE / 2 + 1)));
		openIndexes
				.add((((GRID_SIZE / 2) - 1) * GRID_SIZE + (GRID_SIZE / 2 - 1)));

		roomsLeft -= 5;

		// randomizes room placement
		while (roomsLeft > 0) {

			randIndex = (int) (Math.random() * openIndexes.size());
			indexes.add(openIndexes.get(randIndex));

			int y = openIndexes.get(randIndex) / GRID_SIZE;
			int x = openIndexes.get(randIndex) % GRID_SIZE;

			layout[y][x] = true;

			if (y > 0 && !openIndexes.contains(y * GRID_SIZE + x - 1)) {
				openIndexes.add(y * GRID_SIZE + x - 1);
			}
			if (y < GRID_SIZE - 1
					&& !openIndexes.contains(y * GRID_SIZE + x + 1)) {
				openIndexes.add(y * GRID_SIZE + x + 1);
			}
			if (x > 0 && !openIndexes.contains((y - 1) * GRID_SIZE + x)) {
				openIndexes.add((y - 1) * GRID_SIZE + x);
			}
			if (x < GRID_SIZE - 1
					&& !openIndexes.contains((y + 1) * GRID_SIZE + x)) {
				openIndexes.add((y + 1) * GRID_SIZE + x);
			}
			openIndexes.remove(randIndex);
			roomsLeft--;
		}

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
			GamePanel.gameState = GameStates.BOSS_SPAWN;
			move = MoveStates.STATIC;
			activeRoom.setBlockBot(new Rectangle(360, 460, 90, 10));
		}
	}

	public void moveBelowRoom() {
		activeRoom = activeRoom.getBottomRoom();
	}

	public Room getActiveRoom() {
		return activeRoom;
	}

	// Prints map to console
	public void print() {
		for (int i = 0; i < layout.length; i++) {
			for (int j = 0; j < layout.length; j++) {
				if (layout[i][j]) {
					System.out.print("0");
				} else {
					System.out.print("X");
				}
			}
			System.out.println();
		}
	}
}
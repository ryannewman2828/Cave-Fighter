/**
 * Ryan Newman
 * Cave Fighter
 * Version 1.1
 * November 26 2015 
*/
package game;

import java.io.IOException;
import java.util.ArrayList;

public class EnemyCreator {

	// The amount of different enemies
	private final int ENEMY_AMOUNT = 4;

	//The difficulty the enemy should be
	private int difficulty;

	//spawn an enemy?
	private boolean spawning;

	//The main character
	private MainCharacter character = new MainCharacter();

	// Holds a list of co-ordinates that are good for spawning enemies
	// 3 combos for each # of enemies
	private int[][] possibleSpawnsX = { { 400 }, { 200 }, { 400 },
			{ 200, 650 }, { 550, 320 }, { 150, 660 }, { 230, 400, 580 },
			{ 470, 410, 350 }, { 270, 650, 650 }, { 150, 680, 150, 680 },
			{ 412, 412, 350, 470 }, { 275, 375, 475, 575 },
			{ 150, 680, 150, 680, 400 }, { 275, 415, 540, 415, 415 },
			{ 285, 530, 415, 285, 530 } };
	private int[][] possibleSpawnsY = { { 240 }, { 120 }, { 200 },
			{ 160, 350 }, { 250, 250 }, { 350, 160 }, { 250, 250, 250 },
			{ 300, 250, 300 }, { 250, 160, 380 }, { 150, 150, 360, 360 },
			{ 215, 300, 250, 250 }, { 260, 260, 260, 260 },
			{ 150, 150, 360, 360, 260 }, { 260, 260, 260, 180, 340 },
			{ 320, 320, 260, 200, 200 } };

	// The number of enemies to be spawned in the map
	private int numEnemies;

	// Gets returned to be used in the room
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();

	public EnemyCreator(int difficulty, boolean spawning,
			MainCharacter character) {

		this.difficulty = difficulty;
		this.character = character;
		this.spawning = spawning;

		// Randomizes the amount of enemies in the current room
		// Easy: 1 - 3
		// Medium: 2 - 4
		// Hard: 3 - 5
		numEnemies = (int) (difficulty + (3 * Math.random()));

		// 3 sets for each # of enemies, this chooses one of those
		int randomNum = (int) ((Math.random() + numEnemies - 1) * 3);

		// spawns enemies into the current room
		for (int i = 0; i < numEnemies; i++) {
			// randomizes enemy indexes
			int rand = (int) (Math.random() * ENEMY_AMOUNT);

			int speed;
			int health;
			int damage;
			switch (rand) {

			// Spawns green slime
			case 0:
				speed = 1;
				health = (int) (Math.random() * difficulty * 2) + difficulty
						* 3;
				damage = difficulty;
				enemies.add(new GreenSlime(possibleSpawnsX[randomNum][i],
						possibleSpawnsY[randomNum][i], speed, health, damage,
						spawning, character));
				break;
			// Spawns blue slime
			case 1:
				speed = 1;
				health = (int) (Math.random() * difficulty * 2) + difficulty
						* 3;
				damage = difficulty + 2;
				enemies.add(new BlueSlime(possibleSpawnsX[randomNum][i],
						possibleSpawnsY[randomNum][i], speed, health, damage,
						spawning, character));
				break;
			// Spawns yellow slime
			case 2:
				speed = 1;
				health = (int) (Math.random() * difficulty * 2) + difficulty
						* 3;
				damage = difficulty;
				enemies.add(new YellowSlime(possibleSpawnsX[randomNum][i],
						possibleSpawnsY[randomNum][i], speed, health, damage,
						spawning, character));
				break;
			// Spawns red slime
			case 3:
				speed = 1;
				health = (int) (Math.random() * difficulty * 2) + difficulty
						* 3 + 2;
				damage = difficulty + 1;
				enemies.add(new RedSlime(possibleSpawnsX[randomNum][i],
						possibleSpawnsY[randomNum][i], speed, health, damage,
						spawning, character));
				break;
			}
		}
	}

	public ArrayList<Enemy> getEnemyList() {
		return enemies;
	}

	//Spawns a single enemy
	public Enemy spawnEnemy() {

		int speed;
		int health;
		int damage;

		if (difficulty != 3) {
			// randomizes enemy indexes
			int rand = (int) (Math.random() * ENEMY_AMOUNT);

			switch (rand) {

			// Spawns green slime
			case 0:
				speed = 1;
				health = (int) (Math.random() * difficulty * 2) + difficulty
						* 3;
				damage = difficulty;
				return (new GreenSlime(400, 140, speed, health, damage,
						spawning, character));
				// Spawns blue slime
			case 1:
				speed = 1;
				health = (int) (Math.random() * difficulty * 2) + difficulty
						* 3;
				damage = difficulty + 2;
				return (new BlueSlime(400, 140, speed, health, damage,
						spawning, character));
				// Spawns yellow slime
			case 2:
				speed = 1;
				health = (int) (Math.random() * difficulty * 2) + difficulty
						* 3;
				damage = difficulty;
				return (new YellowSlime(400, 140, speed, health, damage,
						spawning, character));
				// Spawns red slime
			case 3:
				speed = 1;
				health = (int) (Math.random() * difficulty * 2) + difficulty
						* 3 + 2;
				damage = difficulty + 1;
				return (new RedSlime(400, 140, speed, health, damage, spawning,
						character));
			}
		} else {
			
			//Spawns a tentacle
			speed = 0;
			health = 7;
			damage = 2;
			return (new Tentacle(character.getX(), character.getY(), speed, health, damage, spawning,
					character));
		}
		return null;
	}
}
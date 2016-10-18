package cave.fighter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import cave.fighter.character.MainCharacter;
import cave.fighter.character.Projectile;
import cave.fighter.enemies.Enemy;
import cave.fighter.enums.AttackStates;
import cave.fighter.enums.GameStates;
import cave.fighter.enums.MoveStates;
import cave.fighter.environment.Map;
import cave.fighter.utilities.Assets;
import cave.fighter.utilities.Constants;

public class GamePanel extends CaveFighterPanel {

	private int counter;
	private int keyCounter;

	private Map map;
	private BitKeys keys;
	public static GameStates gameState;

	public GamePanel() throws IOException {
		
		counter = 0;
		keyCounter = 45;
		
		gameState = GameStates.RUNNING;
		keys= new BitKeys();
		setSwitchPanel(false);
		addKeyListener(keys);
	}

	public void setUpMap() {
		map = new Map(getDifficulty(), getMapSize());
	}

	@Override
	public void paintComponent(Graphics g) {

		// Double rendering
		Graphics2D g2d = (Graphics2D) g;

		switch (gameState) {
		case RUNNING:

			drawRoom(g2d);
			drawCharacter(g2d);

			ArrayList<Enemy> enemies = map.getActiveRoom().getEnemies();
			for (int i = 0; i < enemies.size(); i++) {
				g2d.drawImage(enemies.get(i).getEnemyAnimation().getImage(),
						enemies.get(i).getX()
								- enemies.get(i).getEnemyAnimation().getImage()
										.getWidth(getParent()) / 2, enemies
								.get(i).getY()
								- enemies.get(i).getEnemyAnimation().getImage()
										.getHeight(getParent()) / 2, this);
			}

			g2d.drawImage(map.getActiveRoom().getPowerUp(), Constants.POWERUP_X, Constants.POWERUP_Y, this);
			g2d.drawImage(Assets.walls, 0, 0, this);
			break;
		case BOSS_SPAWN:
		case BOSS_BATTLE:

			drawRoom(g2d);

			drawCharacter(g2d);

			enemies = map.getActiveRoom().getEnemies();
			for (int i = 0; i < enemies.size(); i++) {
				g2d.drawImage(enemies.get(i).getEnemyAnimation().getImage(),
						enemies.get(i).getX()
								- enemies.get(i).getEnemyAnimation().getImage()
										.getWidth(getParent()) / 2, enemies
								.get(i).getY()
								- enemies.get(i).getEnemyAnimation().getImage()
										.getHeight(getParent()) / 2, this);
			}

			g2d.drawImage(map.getActiveRoom().getBoss().getBossAnimation()
					.getImage(), map.getActiveRoom().getBoss().getX()
					- map.getActiveRoom().getBoss().getBossAnimation()
							.getImage().getWidth(getParent()) / 2, map
					.getActiveRoom().getBoss().getY()
					- map.getActiveRoom().getBoss().getBossAnimation()
							.getImage().getHeight(getParent()) / 2, this);

			g2d.drawImage(Assets.walls, 0, 0, this);

			g2d.setColor(Color.BLACK);
			g2d.fillRect(Constants.BAR_OUTSIDE_X, Constants.BAR_OUTSIDE_Y, Constants.BAR_WIDTH + Constants.BAR_DISPLACE, Constants.BAR_HEIGHT + Constants.BAR_DISPLACE);
			g2d.setColor(Color.RED);
			g2d.fillRect(Constants.BAR_INSIDE_X, Constants.BAR_INSIDE_Y, Constants.BAR_WIDTH, Constants.BAR_HEIGHT);
			g2d.setColor(Color.GREEN);
			g2d.fillRect(Constants.BAR_INSIDE_X, Constants.BAR_INSIDE_Y, map.getActiveRoom().getBoss().getHpBar()
					.getSize(), Constants.BAR_HEIGHT);
			break;
		case DEAD:

			drawRoom(g2d);
			g2d.drawImage(Assets.walls, 0, 0, this);
			g2d.drawImage(Assets.dead, 0, 0, this);
			break;
		case GAME_OVER:

			drawRoom(g2d);
			drawCharacter(g2d);
			g2d.drawImage(Assets.walls, 0, 0, this);
			g2d.drawImage(Assets.gameOver, 0, 0, this);
			break;
		default:
			break;
		}

	}

	@Override
	public void run() {

		switch (gameState) {
		case BOSS_SPAWN:
			map.update();
			break;
		case RUNNING:
		case BOSS_BATTLE:
			if (keys.isKeyPressed(KeyEvent.VK_W)) {
				map.move = MoveStates.UP;
				map.moveUp();
			} if (keys.isKeyPressed(KeyEvent.VK_S)) {
				map.move = MoveStates.DOWN;
				map.moveDown();
			} if (keys.isKeyPressed(KeyEvent.VK_A)) {
				map.move = MoveStates.LEFT;
				map.moveLeft();
			} if (keys.isKeyPressed(KeyEvent.VK_D)) {
				map.move = MoveStates.RIGHT;
				map.moveRight();
			} if(!keys.areMoveKeysPressed()) {
				map.move = MoveStates.STATIC;
			}
			map.update();

			if (keys.isKeyPressed(KeyEvent.VK_UP)
					&& MainCharacter.getCharacterInstance().getHeadCounter() == 0) {
				MainCharacter.getCharacterInstance().attack = AttackStates.UP;
				MainCharacter.getCharacterInstance().setHeadCounter(Constants.CHAR_HEAD_COOLDOWN);
			} else if (keys.isKeyPressed(KeyEvent.VK_LEFT)
					&& MainCharacter.getCharacterInstance().getHeadCounter() == 0) {
				MainCharacter.getCharacterInstance().attack = AttackStates.LEFT;
				MainCharacter.getCharacterInstance().setHeadCounter(Constants.CHAR_HEAD_COOLDOWN);
			} else if (keys.isKeyPressed(KeyEvent.VK_RIGHT)
					&& MainCharacter.getCharacterInstance().getHeadCounter() == 0) {
				MainCharacter.getCharacterInstance().attack = AttackStates.RIGHT;
				MainCharacter.getCharacterInstance().setHeadCounter(Constants.CHAR_HEAD_COOLDOWN);
			} else if (keys.isKeyPressed(KeyEvent.VK_DOWN)
					&& MainCharacter.getCharacterInstance().getHeadCounter() == 0) {
				MainCharacter.getCharacterInstance().attack = AttackStates.DOWN;
				MainCharacter.getCharacterInstance().setHeadCounter(Constants.CHAR_HEAD_COOLDOWN);
			}
			break;
		case DEAD:
		case GAME_OVER:
			if (keyCounter > 0){
				keyCounter--;
			} else if (!keys.isEmpty()){
				MainCharacter.getCharacterInstance().resetCharacter();
				setSwitchPanel(true);
			}
			break;
		default:
			break;

		}
		
		if(!MainCharacter.getCharacterInstance().getAlive()){
			gameState = GameStates.DEAD;
		}
		
		animate();
		repaint();
	}

	private void animate() {
		if (gameState == GameStates.RUNNING
				|| gameState == GameStates.BOSS_BATTLE
				|| gameState == GameStates.BOSS_SPAWN
				|| gameState == GameStates.DEAD
				|| gameState == GameStates.GAME_OVER) {

			if (map.move != MoveStates.STATIC) {
				MainCharacter.getCharacterInstance().getBodyAnimation()
						.update(Constants.CHAR_ANIMATION);
				MainCharacter.getCharacterInstance().getHeadAnimation()
						.update(Constants.CHAR_ANIMATION);
			}
			
			ArrayList<Projectile> projectiles = MainCharacter
					.getCharacterInstance().getProjectiles();
			for (int i = 0; i < projectiles.size(); i++) {
				Projectile p = (Projectile) projectiles.get(i);
				p.getBulletAnimation().update(Constants.CHAR_ANIMATION);
			}

			if (map.getActiveRoom().isBossRoom()) {
				map.getActiveRoom().getBoss().getBossAnimation().update(Constants.CHAR_ANIMATION);
			}

			ArrayList<Enemy> enemies = map.getActiveRoom().getEnemies();
			for (int i = 0; i < enemies.size(); i++) {
				enemies.get(i).getEnemyAnimation().update(Constants.ENEMY_ANIMATION);
			}
		}
	}

	private void drawCharacter(Graphics g) {

		if (map.move == MoveStates.STATIC) {
			g.drawImage(MainCharacter.getCharacterInstance().getBodyImage(),
					MainCharacter.getCharacterInstance().getX()
							- MainCharacter.getCharacterInstance()
									.getBodyImage().getWidth(getParent()) / 2,
					MainCharacter.getCharacterInstance().getY()
							- MainCharacter.getCharacterInstance()
									.getBodyImage().getHeight(getParent()) / 2,
					this);
			if (MainCharacter.getCharacterInstance().attack == AttackStates.STATIC) {
				g.drawImage(
						MainCharacter.getCharacterInstance().getHeadImage(),
						MainCharacter.getCharacterInstance().getX()
								- MainCharacter.getCharacterInstance()
										.getHeadImage().getWidth(getParent())
								/ 2, MainCharacter.getCharacterInstance()
								.getY()
								- Constants.CHAR_DISPLACE
								- MainCharacter.getCharacterInstance()
										.getHeadImage().getHeight(getParent())
								/ 2, this);
			} else {
				g.drawImage(MainCharacter.getCharacterInstance()
						.getHeadAnimation().getImage(),
						MainCharacter.getCharacterInstance().getX()
								- MainCharacter.getCharacterInstance()
										.getHeadAnimation().getImage()
										.getWidth(getParent()) / 2,
						MainCharacter.getCharacterInstance().getY()
								- Constants.CHAR_DISPLACE
								- MainCharacter.getCharacterInstance()
										.getHeadAnimation().getImage()
										.getHeight(getParent()) / 2, this);
			}
		} else {
			g.drawImage(
					MainCharacter.getCharacterInstance().getBodyAnimation()
							.getImage(),
					MainCharacter.getCharacterInstance().getX()
							- MainCharacter.getCharacterInstance()
									.getBodyAnimation().getImage()
									.getWidth(getParent()) / 2,
					MainCharacter.getCharacterInstance().getY()
							- MainCharacter.getCharacterInstance()
									.getBodyAnimation().getImage()
									.getHeight(getParent()) / 2, this);
			g.drawImage(
					MainCharacter.getCharacterInstance().getHeadAnimation()
							.getImage(),
					MainCharacter.getCharacterInstance().getX()
							- MainCharacter.getCharacterInstance()
									.getHeadAnimation().getImage()
									.getWidth(getParent()) / 2,
					MainCharacter.getCharacterInstance().getY()
							- Constants.CHAR_DISPLACE
							- MainCharacter.getCharacterInstance()
									.getHeadAnimation().getImage()
									.getHeight(getParent()) / 2, this);
		}
	}

	private void drawRoom(Graphics g) {

		g.drawImage(Assets.roomImage, 0, 0, this);

		ArrayList<Projectile> projectiles = MainCharacter.getCharacterInstance()
				.getProjectiles();
		for (int i = 0; i < projectiles.size(); i++) {
			Projectile p = (Projectile) projectiles.get(i);
			g.drawImage(p.getBulletAnimation().getImage(), p.getX()
					- p.getBulletAnimation().getImage().getWidth(getParent())
					/ 2, p.getY()
					- p.getBulletAnimation().getImage().getHeight(getParent())
					/ 2, this);
		}

		counter = MainCharacter.getCharacterInstance().getCurHealth();
		for (int i = 0; i < MainCharacter.getCharacterInstance().getHealth() / 2; i++) {
			if (counter - 2 >= 0) {
				g.drawImage(Assets.fullHeart, Constants.HEART_X_LOCATION_MULTIPLIER * i + Constants.HEART_DISPLACE, Constants.HEART_Y, this);
				counter -= 2;
			} else if (counter - 1 >= 0) {
				g.drawImage(Assets.halfHeart, Constants.HEART_X_LOCATION_MULTIPLIER * i + Constants.HEART_DISPLACE, Constants.HEART_Y, this);
				counter -= 1;
			} else {
				g.drawImage(Assets.emptyHeart, Constants.HEART_X_LOCATION_MULTIPLIER * i + Constants.HEART_DISPLACE, Constants.HEART_Y, this);
			}
		}
	}
}
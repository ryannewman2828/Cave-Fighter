package cave.fighter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

public class GamePanel extends CaveFighterPanel implements KeyListener {

	private boolean wPressed;
	private boolean sPressed;
	private boolean aPressed;
	private boolean dPressed;
	private boolean upPressed;
	private boolean downPressed;
	private boolean leftPressed;
	private boolean rightPressed;
	private boolean keyPressed;

	private int counter;
	private int keyCounter;

	private Map map;

	public static GameStates gameState;

	public GamePanel() throws IOException {
		
		counter = 0;
		keyCounter = 30;
		
		wPressed = false;
		sPressed = false;
		aPressed = false;
		dPressed = false;
		upPressed = false;
		downPressed = false;
		leftPressed = false;
		rightPressed = false;
		keyPressed = false;
		
		gameState = GameStates.RUNNING;
		
		setSwitchPanel(false);
		addKeyListener(this);
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
			if (wPressed) {
				map.move = MoveStates.UP;
			} else if (sPressed) {
				map.move = MoveStates.DOWN;
			} else if (aPressed) {
				map.move = MoveStates.LEFT;
			} else if (dPressed) {
				map.move = MoveStates.RIGHT;
			} else {
				map.move = MoveStates.STATIC;
			}

			map.update();

			if (upPressed
					&& MainCharacter.getCharacterInstance().getHeadCounter() == 0) {
				MainCharacter.getCharacterInstance().attack = AttackStates.UP;
				MainCharacter.getCharacterInstance().setHeadCounter(Constants.CHAR_HEAD_COOLDOWN);
			} else if (leftPressed
					&& MainCharacter.getCharacterInstance().getHeadCounter() == 0) {
				MainCharacter.getCharacterInstance().attack = AttackStates.LEFT;
				MainCharacter.getCharacterInstance().setHeadCounter(Constants.CHAR_HEAD_COOLDOWN);
			} else if (rightPressed
					&& MainCharacter.getCharacterInstance().getHeadCounter() == 0) {
				MainCharacter.getCharacterInstance().attack = AttackStates.RIGHT;
				MainCharacter.getCharacterInstance().setHeadCounter(Constants.CHAR_HEAD_COOLDOWN);
			} else if (downPressed
					&& MainCharacter.getCharacterInstance().getHeadCounter() == 0) {
				MainCharacter.getCharacterInstance().attack = AttackStates.DOWN;
				MainCharacter.getCharacterInstance().setHeadCounter(Constants.CHAR_HEAD_COOLDOWN);
			}
			break;
		case DEAD:
		case GAME_OVER:
			if (keyPressed && keyCounter == 0){
				MainCharacter.getCharacterInstance().resetCharacter();
				setSwitchPanel(true);
			}
			if (keyCounter > 0){
				keyCounter--;
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

	public void drawRoom(Graphics g) {

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

	@Override
	public void keyPressed(KeyEvent e) {
		keyPressed = true;
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			upPressed = true;
			break;
		case KeyEvent.VK_DOWN:
			downPressed = true;
			break;
		case KeyEvent.VK_LEFT:
			leftPressed = true;
			break;
		case KeyEvent.VK_RIGHT:
			rightPressed = true;
			break;

		case KeyEvent.VK_W:
			wPressed = true;
			break;
		case KeyEvent.VK_A:
			aPressed = true;
			break;
		case KeyEvent.VK_S:
			sPressed = true;
			break;
		case KeyEvent.VK_D:
			dPressed = true;
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		keyPressed = false;
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			upPressed = false;
			break;
		case KeyEvent.VK_DOWN:
			downPressed = false;
			break;
		case KeyEvent.VK_LEFT:
			leftPressed = false;
			break;
		case KeyEvent.VK_RIGHT:
			rightPressed = false;
			break;

		case KeyEvent.VK_W:
			wPressed = false;
			break;
		case KeyEvent.VK_A:
			aPressed = false;
			break;
		case KeyEvent.VK_S:
			sPressed = false;
			break;
		case KeyEvent.VK_D:
			dPressed = false;
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
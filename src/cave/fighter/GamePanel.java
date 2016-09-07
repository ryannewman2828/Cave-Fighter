package cave.fighter;

import game.Enemy;
import game.Projectile;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

import cave.fighter.character.MainCharacter;
import cave.fighter.enums.AttackStates;
import cave.fighter.enums.GameStates;
import cave.fighter.enums.MoveStates;
import cave.fighter.environment.Map;
import cave.fighter.utilities.Assets;
import cave.fighter.utilities.Constants;

public class GamePanel extends CaveFighterPanel implements KeyListener {

	private boolean wPressed = false;
	private boolean sPressed = false;
	private boolean aPressed = false;
	private boolean dPressed = false;
	private boolean upPressed = false;
	private boolean downPressed = false;
	private boolean leftPressed = false;
	private boolean rightPressed = false;

	private int counter = 0;

	private Map map;

	public static GameStates gameState = GameStates.RUNNING;

	public GamePanel() throws IOException {

		setSwitchPanel(false);

		addKeyListener(this);
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

			g2d.drawImage(map.getActiveRoom().getPowerUp(), 400, 240, this);
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
			g2d.fillRect(495, 40, 210, 30);
			g2d.setColor(Color.RED);
			g2d.fillRect(500, 45, 200, 20);
			g2d.setColor(Color.GREEN);
			g2d.fillRect(500, 45, map.getActiveRoom().getBoss().getHpBar()
					.getSize(), 20);
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

		// Handles gamestates
		switch (gameState) {
		case BOSS_SPAWN:

			// Updates the map
			map.update();
			break;
		case RUNNING:
		case BOSS_BATTLE:

			// Handles updating the moveStates
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

			// Updates the map
			map.update();

			// Handles the updating of the attack States
			if (upPressed
					&& MainCharacter.getCharacterInstance().getHeadCounter() == 0) {
				MainCharacter.getCharacterInstance().attack = AttackStates.UP;
				MainCharacter.getCharacterInstance().setHeadCounter(15);
			} else if (leftPressed
					&& MainCharacter.getCharacterInstance().getHeadCounter() == 0) {
				MainCharacter.getCharacterInstance().attack = AttackStates.LEFT;
				MainCharacter.getCharacterInstance().setHeadCounter(15);
			} else if (rightPressed
					&& MainCharacter.getCharacterInstance().getHeadCounter() == 0) {
				MainCharacter.getCharacterInstance().attack = AttackStates.RIGHT;
				MainCharacter.getCharacterInstance().setHeadCounter(15);
			} else if (downPressed
					&& MainCharacter.getCharacterInstance().getHeadCounter() == 0) {
				MainCharacter.getCharacterInstance().attack = AttackStates.DOWN;
				MainCharacter.getCharacterInstance().setHeadCounter(15);
			}
			break;
		case DEAD:
		case GAME_OVER:

			break;
		default:
			break;

		}

		animate();
		repaint();
	}

	public void animate() {
		if (gameState == GameStates.RUNNING
				|| gameState == GameStates.BOSS_BATTLE
				|| gameState == GameStates.BOSS_SPAWN
				|| gameState == GameStates.DEAD
				|| gameState == GameStates.GAME_OVER) {

			if (map.move != MoveStates.STATIC) {
				MainCharacter.getCharacterInstance().getBodyAnimation()
						.update(10);
				MainCharacter.getCharacterInstance().getHeadAnimation()
						.update(10);
			}

			ArrayList<Projectile> projectiles = MainCharacter
					.getCharacterInstance().getProjectiles();
			for (int i = 0; i < projectiles.size(); i++) {
				Projectile p = (Projectile) projectiles.get(i);
				p.getBulletAnimation().update(10);
			}

			if (map.getActiveRoom().isBossRoom()) {
				//TODO uncomment this line
				//map.getActiveRoom().getBoss().getBossAnimation().update(10);
			}

			ArrayList<Enemy> enemies = map.getActiveRoom().getEnemies();
			for (int i = 0; i < enemies.size(); i++) {
				enemies.get(i).getEnemyAnimation().update(15);
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
								- 12
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
								- 12
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
							- 12
							- MainCharacter.getCharacterInstance()
									.getHeadAnimation().getImage()
									.getHeight(getParent()) / 2, this);
		}
	}

	public void drawRoom(Graphics g) {

		g.drawImage(map.getActiveRoom().getImage(), 0, 0, this);

		ArrayList projectiles = MainCharacter.getCharacterInstance()
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
				g.drawImage(Assets.fullHeart, 60 * i + 40, 30, this);
				counter -= 2;
			} else if (counter - 1 >= 0) {
				g.drawImage(Assets.halfHeart, 60 * i + 40, 30, this);
				counter -= 1;
			} else {
				g.drawImage(Assets.emptyHeart, 60 * i + 40, 30, this);
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
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
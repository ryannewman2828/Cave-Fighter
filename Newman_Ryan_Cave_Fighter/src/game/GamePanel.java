/**
 * Ryan Newman
 * Cave Fighter
 * Version 1.1
 * November 26 2015 
 * 
 * WARNING: May take long to load the maps
 */
package game;

import java.applet.Applet;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.util.ArrayList;

import animation.framework.Animation;

public class GamePanel extends Applet implements Runnable, KeyListener,
		MouseMotionListener, MouseListener {

	// Variables for the game and the menu
	private Image image;
	private Graphics second;
	private URL base;
	private boolean wPressed = false;
	private boolean sPressed = false;
	private boolean aPressed = false;
	private boolean dPressed = false;
	private boolean upPressed = false;
	private boolean downPressed = false;
	private boolean leftPressed = false;
	private boolean rightPressed = false;
	private boolean mouseClicked = false;
	private int mouseX, mouseY;
	private int mouseCounter = 0;
	private int pointerOneY, pointerTwoY;

	// "Hitboxes for the menu buttons and the mouse
	private Rectangle mouse = new Rectangle(0, 0, 0, 0);
	private ArrayList<Rectangle> menuButtons = new ArrayList<Rectangle>();

	// Counts the main characters health
	// Used for drawing the hearts
	private int counter = 0;

	// The difficulty and the size of the game selected in the menu
	private int difficulty = 0, size = 0;

	// Images for the background/map
	private Image imgWalls;

	// Images for the menu
	private Image menu, selection, htp;

	// Images for the hearts in the top left of the running screen
	private Image fullHeart, halfHeart, emptyHeart;

	// The Y co-ordinates of the pointers pointing towards the selected
	// difficuty and size
	private Image pointerOne, pointerTwo;

	// Images for the victory/defeat screen
	private Image dead, gameOver;

	// The map object which hold all rooms/enemies/other metadata stuff
	private Map map;

	// The possible move states
	public enum moveStates {
		UP, DOWN, LEFT, RIGHT, STATIC
	}

	// The possible attack states
	public enum attackStates {
		UP, DOWN, LEFT, RIGHT, STATIC
	}

	// Declares possible gameStates
	public enum gameStates {
		MENU, RUNNING, DEAD, DIFFICULTY_SELECTION, BOSS_SPAWN, BOSS_BATTLE, GAME_OVER, HOW_TO_PLAY
	}

	// Sets default GameState
	public static gameStates gameState = gameStates.MENU;

	@Override
	public void init() {

		// Initializes the game
		setSize(800, 480);
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Cave Fighter");
		try {
			base = getDocumentBase();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Sets up the 3 menu buttons
		for (int i = 0; i < 3; i++) {
			menuButtons.add(new Rectangle(0, 0, 0, 0));
		}

		// Image wall Setup
		imgWalls = getImage(base, "data/walls.png");

		// Heart images setup
		emptyHeart = getImage(base, "data/heart0.png");
		halfHeart = getImage(base, "data/heart1.png");
		fullHeart = getImage(base, "data/heart2.png");

		// Menu images setup
		menu = getImage(base, "data/menu1.png");
		selection = getImage(base, "data/menu2.png");
		htp = getImage(base, "data/howtoplay.png");

		// Pointer images setup
		pointerOne = getImage(base, "data/pointer0.png");
		pointerTwo = getImage(base, "data/pointer1.png");

		// End game images setup
		dead = getImage(base, "data/defeat.png");
		gameOver = getImage(base, "data/victory.png");
	}

	@Override
	public void start() {
		// Starts thread
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void stop() {
		// Not used in current project
	}

	@Override
	public void destroy() {
		// Not used in current project
	}

	@Override
	public void run() {

		// 60 FPS gameloop
		while (true) {

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
					map.move = moveStates.UP;
				} else if (sPressed) {
					map.move = moveStates.DOWN;
				} else if (aPressed) {
					map.move = moveStates.LEFT;
				} else if (dPressed) {
					map.move = moveStates.RIGHT;
				} else {
					map.move = moveStates.STATIC;
				}

				// Updates the map
				map.update();

				// Handles the updating of the attack States
				if (upPressed && map.getCharacter().getHeadCounter() == 0) {
					map.getCharacter().attack = attackStates.UP;
					map.getCharacter().setHeadCounter(15);
				} else if (leftPressed
						&& map.getCharacter().getHeadCounter() == 0) {
					map.getCharacter().attack = attackStates.LEFT;
					map.getCharacter().setHeadCounter(15);
				} else if (rightPressed
						&& map.getCharacter().getHeadCounter() == 0) {
					map.getCharacter().attack = attackStates.RIGHT;
					map.getCharacter().setHeadCounter(15);
				} else if (downPressed
						&& map.getCharacter().getHeadCounter() == 0) {
					map.getCharacter().attack = attackStates.DOWN;
					map.getCharacter().setHeadCounter(15);
				}
				break;
			case MENU:
				// Updates the "hitboxes"
				mouse.setRect(mouseX, mouseY, 1, 1);
				for (int i = 0; i < menuButtons.size(); i++) {
					menuButtons.get(i).setRect(125, 110 * i + 142, 545, 85);
				}

				if (mouseClicked && mouseCounter == 0) {
					if (mouse.intersects(menuButtons.get(0))) {

						// Moves into difficulty selection
						gameState = gameStates.DIFFICULTY_SELECTION;
						for (int i = 0; i < 5; i++) {
							menuButtons.add(new Rectangle(0, 0, 0, 0));
						}
						for (int i = 0; i < 3; i++) {
							for (int j = 0; j < 2; j++) {
								menuButtons.get(i * 2 + j).setRect(
										75 + 350 * j, 35 + 120 * i, 300, 73);
							}
						}
						menuButtons.get(6).setRect(50, 403, 190, 45);
						menuButtons.get(7).setRect(558, 403, 190, 45);
						mouseCounter = 30;
					} else if (mouse.intersects(menuButtons.get(1))) {

						// Moves into How To Play section
						menuButtons.clear();
						menuButtons.add(new Rectangle(50, 403, 190, 45));
						gameState = gameStates.HOW_TO_PLAY;
						mouseCounter = 30;
					} else if (mouse.intersects(menuButtons.get(2))) {

						// Exits the game
						System.exit(0);
					}
				}
				break;
			case DIFFICULTY_SELECTION:

				// Updates mouse "hitbox"
				mouse.setRect(mouseX, mouseY, 1, 1);

				// Selects the difficulty and size
				if (mouseClicked && mouseCounter == 0) {
					for (int i = 0; i < 3; i++) {
						if (mouse.intersects(menuButtons.get(2 * i))) {
							difficulty = i + 1;
							pointerOneY = 40 + i * 120;
						}
						if (mouse.intersects(menuButtons.get(2 * i + 1))) {
							size = i + 1;
							pointerTwoY = 40 + i * 120;
						}
					}
					if (mouse.intersects(menuButtons.get(6))) {

						// Moves back into the menu
						gameState = gameStates.MENU;
						menuButtons.clear();
						mouseCounter = 30;
						for (int i = 0; i < 3; i++) {
							menuButtons.add(new Rectangle(0, 0, 0, 0));
						}
					} else if (mouse.intersects(menuButtons.get(7))) {

						// Moves into the game and generates the selected map
						if (pointerOneY != 0 && pointerTwoY != 0) {
							gameState = gameStates.RUNNING;
							map = new Map(difficulty, size);

							// Resets menu variables
							difficulty = 0;
							size = 0;
							pointerOneY = 0;
							pointerTwoY = 0;

							// Sets up for the end game button
							menuButtons.clear();
							menuButtons.add(new Rectangle(300, 237, 200, 66));
						}
					}
				}
				break;
			case DEAD:
			case GAME_OVER:

				// Updates mouse
				mouse.setRect(mouseX, mouseY, 1, 1);

				// Moves into the menu
				if (mouseClicked && mouse.intersects(menuButtons.get(0))) {
					gameState = gameStates.MENU;
					map = null;
					menuButtons.clear();
					mouseCounter = 30;
					for (int i = 0; i < 3; i++) {
						menuButtons.add(new Rectangle(0, 0, 0, 0));
					}
				}
				break;
			case HOW_TO_PLAY:

				// Updates the mouse
				mouse.setRect(mouseX, mouseY, 1, 1);

				// Moves back into the menu
				if (mouseClicked && mouseCounter == 0) {
					if (mouse.intersects(menuButtons.get(0))) {
						gameState = gameStates.MENU;
						menuButtons.clear();
						mouseCounter = 30;
						for (int i = 0; i < 3; i++) {
							menuButtons.add(new Rectangle(0, 0, 0, 0));
						}
					}
				}
			default:
				break;

			}

			// Decrements mouse counter
			if (mouseCounter > 0) {
				mouseCounter--;
			}

			// Animates and repaints images
			animate();
			repaint();

			// Responsible for the 60 FPS
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void animate() {
		if (gameState == gameStates.RUNNING
				|| gameState == gameStates.BOSS_BATTLE
				|| gameState == gameStates.BOSS_SPAWN
				|| gameState == gameStates.DEAD
				|| gameState == gameStates.GAME_OVER) {

			// Animates the main character
			if (map.move != moveStates.STATIC) {
				map.getCharacter().getBodyAnimation().update(10);
				map.getCharacter().getHeadAnimation().update(10);
			}

			// Animates the bullets
			ArrayList<Projectile> projectiles = map.getActiveRoom()
					.getCharacter().getProjectiles();
			for (int i = 0; i < projectiles.size(); i++) {
				Projectile p = (Projectile) projectiles.get(i);
				p.getBulletAnimation().update(10);
			}

			// Animates the boss
			if (map.getActiveRoom().isBossRoom()) {
				map.getActiveRoom().getBoss().getBossAnimation().update(10);
			}

			// Animates Enemies
			ArrayList<Enemy> enemies = map.getActiveRoom().getEnemies();
			for (int i = 0; i < enemies.size(); i++) {
				enemies.get(i).getEnemyAnimation().update(15);
			}
		}
	}

	@Override
	public void update(Graphics g) {

		// Default
		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}

		// Declares the image
		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);

		// Paints the image
		g.drawImage(image, 0, 0, this);
	}

	@Override
	public void paint(Graphics g) {

		// Double rendering
		Graphics2D g2d = (Graphics2D) g;

		switch (gameState) {
		case RUNNING:

			// Draws the current room
			drawRoom(g2d);

			// Drawing the main character
			drawCharacter(g2d);

			// Draws the enemies
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

			// Draws the active powerup
			g.drawImage(map.getActiveRoom().getPowerUp(), 400, 240, this);

			// Draws the walls
			g2d.drawImage(imgWalls, 0, 0, this);
			break;
		case BOSS_SPAWN:
		case BOSS_BATTLE:

			// Draws the current room
			drawRoom(g2d);

			// Drawing the main character
			drawCharacter(g2d);

			// Draws the enemies
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

			// Draws the boss
			g2d.drawImage(map.getActiveRoom().getBoss().getBossAnimation()
					.getImage(), map.getActiveRoom().getBoss().getX()
					- map.getActiveRoom().getBoss().getBossAnimation()
							.getImage().getWidth(getParent()) / 2, map
					.getActiveRoom().getBoss().getY()
					- map.getActiveRoom().getBoss().getBossAnimation()
							.getImage().getHeight(getParent()) / 2, this);

			// Draws the walls
			g2d.drawImage(imgWalls, 0, 0, this);

			// Health bar index render
			g2d.setColor(Color.BLACK);
			g2d.fillRect(495, 40, 210, 30);
			g2d.setColor(Color.RED);
			g2d.fillRect(500, 45, 200, 20);
			g2d.setColor(Color.GREEN);
			g2d.fillRect(500, 45, map.getActiveRoom().getBoss().getHpBar()
					.getSize(), 20);
			break;
		case DEAD:

			// Draws the current room
			drawRoom(g2d);

			// Draws the walls
			g2d.drawImage(imgWalls, 0, 0, this);

			// Draws the "Defeat" image
			g2d.drawImage(dead, 0, 0, this);
			break;
		case GAME_OVER:

			// Draws the current room
			drawRoom(g2d);
			
			// Drawing the main character
			drawCharacter(g2d);


			// Draws the walls
			g2d.drawImage(imgWalls, 0, 0, this);

			// Draws the "Victory" image
			g2d.drawImage(gameOver, 0, 0, this);
			break;

		case MENU:

			// Draws the menu image
			g2d.drawImage(menu, 0, 0, this);
			break;
		case HOW_TO_PLAY:

			// Draws the "How To Play" image
			g2d.drawImage(htp, 0, 0, this);
			break;
		case DIFFICULTY_SELECTION:

			// Draws the selection image
			g2d.drawImage(selection, 0, 0, this);

			// Draws the pointer images
			if (pointerOneY != 0)
				g2d.drawImage(pointerOne, 5, pointerOneY, this);
			if (pointerTwoY != 0)
				g2d.drawImage(pointerTwo, 725, pointerTwoY, this);
			break;
		default:
			break;
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {

		// Sets the attack state to whatever key is pressed
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

		// Sets the move direction to whatever key gets pressed
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

		// When any move key gets released
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

		// When any attack key gets released
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

	/**
	 * @return the gameState
	 */
	public gameStates getGameState() {
		return gameState;
	}

	/**
	 * @param gameState
	 *            the gameState to set
	 */
	public static void setGameState(gameStates gameStateI) {
		gameState = gameStateI;
	}

	private void drawCharacter(Graphics g) {

		// Drawing the main character
		if (map.move == moveStates.STATIC) {
			g.drawImage(
					map.getCharacter().getBodyImage(),
					map.getCharacter().getX()
							- map.getCharacter().getBodyImage()
									.getWidth(getParent()) / 2,
					map.getCharacter().getY()
							- map.getCharacter().getBodyImage()
									.getHeight(getParent()) / 2, this);
			if (map.getCharacter().attack == attackStates.STATIC) {
				g.drawImage(
						map.getCharacter().getHeadImage(),
						map.getCharacter().getX()
								- map.getCharacter().getHeadImage()
										.getWidth(getParent()) / 2,
						map.getCharacter().getY()
								- 12
								- map.getCharacter().getHeadImage()
										.getHeight(getParent()) / 2, this);
			} else {
				g.drawImage(map.getCharacter().getHeadAnimation().getImage(),
						map.getCharacter().getX()
								- map.getCharacter().getHeadAnimation()
										.getImage().getWidth(getParent()) / 2,
						map.getCharacter().getY()
								- 12
								- map.getCharacter().getHeadAnimation()
										.getImage().getHeight(getParent()) / 2,
						this);
			}
		} else {
			g.drawImage(map.getCharacter().getBodyAnimation().getImage(), map
					.getCharacter().getX()
					- map.getCharacter().getBodyAnimation().getImage()
							.getWidth(getParent()) / 2, map.getCharacter()
					.getY()
					- map.getCharacter().getBodyAnimation().getImage()
							.getHeight(getParent()) / 2, this);
			g.drawImage(map.getCharacter().getHeadAnimation().getImage(), map
					.getCharacter().getX()
					- map.getCharacter().getHeadAnimation().getImage()
							.getWidth(getParent()) / 2, map.getCharacter()
					.getY()
					- 12
					- map.getCharacter().getHeadAnimation().getImage()
							.getHeight(getParent()) / 2, this);
		}
	}

	public void drawRoom(Graphics g) {

		// Draws the current room
		g.drawImage(map.getActiveRoom().getImage(), 0, 0, this);

		// paints the bullets
		ArrayList projectiles = map.getCharacter().getProjectiles();
		for (int i = 0; i < projectiles.size(); i++) {
			Projectile p = (Projectile) projectiles.get(i);
			g.drawImage(p.getBulletAnimation().getImage(), p.getX()
					- p.getBulletAnimation().getImage().getWidth(getParent())
					/ 2, p.getY()
					- p.getBulletAnimation().getImage().getHeight(getParent())
					/ 2, this);
		}

		// Draws the characters hearts
		counter = map.getCharacter().getCurHealth();
		for (int i = 0; i < map.getCharacter().getHealth() / 2; i++) {
			if (counter - 2 >= 0) {
				g.drawImage(fullHeart, 60 * i + 40, 30, this);
				counter -= 2;
			} else if (counter - 1 >= 0) {
				g.drawImage(halfHeart, 60 * i + 40, 30, this);
				counter -= 1;
			} else {
				g.drawImage(emptyHeart, 60 * i + 40, 30, this);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {

		// Mouse is being pressed
		mouseClicked = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		// Mouse has been released
		mouseClicked = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {

		// Updates the co-ordinates of the mouse
		mouseX = e.getX();
		mouseY = e.getY();
	}
}
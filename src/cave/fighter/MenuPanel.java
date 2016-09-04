package cave.fighter;

import game.Map;
import game.GamePanel.gameStates;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;

import cave.fighter.utilities.Assets;

public class MenuPanel extends CaveFighterPanel implements MouseMotionListener,
		MouseListener {

	private enum MenuStates {
		MENU, SELECTION, HTP
	}

	private MenuStates menuState;

	// "Hitboxes for the menu buttons and the mouse
	private Rectangle mouse = new Rectangle(0, 0, 0, 0);
	private ArrayList<Rectangle> menuButtons = new ArrayList<Rectangle>();
	private int mouseX, mouseY;
	private int mouseCounter = 0;
	private int pointerOneY, pointerTwoY;
	private boolean mouseClicked = false;
	private int difficulty = 0, size = 0;

	public MenuPanel() throws IOException {

		setSwitchPanel(false);

		menuState = MenuStates.MENU;

		// Sets up the 3 menu buttons
		for (int i = 0; i < 3; i++) {
			menuButtons.add(new Rectangle(0, 0, 0, 0));
		}

		addMouseListener(this);
		addMouseMotionListener(this);
	}

	@Override
	public void paintComponent(Graphics g) {

		// Double rendering
		Graphics2D g2d = (Graphics2D) g;

		switch (menuState) {
		case HTP:
			g2d.drawImage(Assets.htp, 0, 0, this);
			break;
		case MENU:
			g2d.drawImage(Assets.menu, 0, 0, this);
			break;
		case SELECTION:
			g2d.drawImage(Assets.selection, 0, 0, this);

			if (pointerOneY != 0)
				g2d.drawImage(Assets.pointer1, 5, pointerOneY, this);
			if (pointerTwoY != 0)
				g2d.drawImage(Assets.pointer2, 725, pointerTwoY, this);
			break;
		default:
			break;
		}

		// DEBUGGING
		for (int i = 0; i < menuButtons.size(); i++) {
			g2d.draw(menuButtons.get(i));
		}

	}

	@Override
	public void run() {

		if (mouseCounter > 0) {
			mouseCounter--;
		}

		switch (menuState) {
		case HTP:
			// Updates the mouse
			mouse.setRect(mouseX, mouseY, 1, 1);

			// Moves back into the menu
			if (mouseClicked && mouseCounter == 0) {
				if (mouse.intersects(menuButtons.get(0))) {
					menuState = MenuStates.MENU;
					menuButtons.clear();
					mouseCounter = 30;
					for (int i = 0; i < 3; i++) {
						menuButtons.add(new Rectangle(0, 0, 0, 0));
					}
				}
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
					menuState = MenuStates.SELECTION;
					for (int i = 0; i < 5; i++) {
						menuButtons.add(new Rectangle(0, 0, 0, 0));
					}
					for (int i = 0; i < 3; i++) {
						for (int j = 0; j < 2; j++) {
							menuButtons.get(i * 2 + j).setRect(75 + 350 * j,
									35 + 120 * i, 300, 73);
						}
					}
					menuButtons.get(6).setRect(50, 403, 190, 45);
					menuButtons.get(7).setRect(558, 403, 190, 45);
					mouseCounter = 30;
				} else if (mouse.intersects(menuButtons.get(1))) {

					// Moves into How To Play section
					menuButtons.clear();
					menuButtons.add(new Rectangle(50, 403, 190, 45));
					menuState = MenuStates.HTP;
					mouseCounter = 30;
				} else if (mouse.intersects(menuButtons.get(2))) {

					// Exits the game
					System.exit(0);
				}
			}
			break;
		case SELECTION:
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
					menuState = MenuStates.MENU;
					menuButtons.clear();
					mouseCounter = 30;
					for (int i = 0; i < 3; i++) {
						menuButtons.add(new Rectangle(0, 0, 0, 0));
					}
				} else if (mouse.intersects(menuButtons.get(7))) {

					// Moves into the game and generates the selected map
					if (pointerOneY != 0 && pointerTwoY != 0) {
						setSwitchPanel(false);

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
		default:
			break;
		}

		// redraw everything in the GamePanel
		repaint();

	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseClicked = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseClicked = false;
	}
}

package cave.fighter;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;

import cave.fighter.utilities.Assets;
import cave.fighter.utilities.Constants;

public class MenuPanel extends CaveFighterPanel implements MouseMotionListener,
		MouseListener {

	private enum MenuStates {
		MENU, SELECTION, HTP
	}

	private int mouseX, mouseY;
	private int mouseCounter = 0;
	private int pointerOneY, pointerTwoY;
	private int difficulty = 0, size = 0;
	private boolean mouseClicked = false;
	private MenuStates menuState;
	private Rectangle mouse = new Rectangle(0, 0, 0, 0);
	private ArrayList<Rectangle> menuButtons = new ArrayList<Rectangle>();


	public MenuPanel() throws IOException {

		setSwitchPanel(false);

		menuState = MenuStates.MENU;

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
				g2d.drawImage(Assets.pointer1, Constants.POINTER_1_X, pointerOneY, this);
			if (pointerTwoY != 0)
				g2d.drawImage(Assets.pointer2, Constants.POINTER_2_X, pointerTwoY, this);
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
			mouse.setRect(mouseX, mouseY, 1, 1);

			if (mouseClicked && mouseCounter == 0) {
				if (mouse.intersects(menuButtons.get(0))) {
					menuState = MenuStates.MENU;
					menuButtons.clear();
					mouseCounter = Constants.MOUSE_COOLDOWN;
					for (int i = 0; i < 3; i++) {
						menuButtons.add(new Rectangle(0, 0, 0, 0));
					}
				}
			}
			break;
		case MENU:
			mouse.setRect(mouseX, mouseY, 1, 1);
			for (int i = 0; i < menuButtons.size(); i++) {
				menuButtons.get(i).setRect(Constants.MENU_BUTTON_X, 
						Constants.MENU_BUTTON_BETWEEN_DISPLACE * i + Constants.MENU_BUTTON_TOP_DISPLACE, 
						Constants.MENU_BUTTON_WIDTH, Constants.MENU_BUTTON_HEIGHT);
			}

			if (mouseClicked && mouseCounter == 0) {
				if (mouse.intersects(menuButtons.get(0))) {
					menuState = MenuStates.SELECTION;
					
					for (int i = 0; i < 5; i++) {
						menuButtons.add(new Rectangle(0, 0, 0, 0));
					}
					
					for (int i = 0; i < 3; i++) {
						for (int j = 0; j < 2; j++) {
							menuButtons.get(i * 2 + j).setRect(Constants.SELECTION_BUTTON_LEFT_DISPLACE + Constants.SELECTION_BUTTON_X_DISPLACE * j,
									Constants.SELECTION_BUTTON_TOP_DISPLACE + Constants.SELECTION_BUTTON_Y_DISPLACE * i, 
									Constants.SELECTION_BUTTON_WIDTH, Constants.SELECTION_BUTTON_HEIGHT);
						}
					}
					
					menuButtons.get(6).setRect(Constants.BOTTOM_BUTTON_X_1, Constants.BOTTOM_BUTTON_Y, Constants.BOTTOM_BUTTON_WIDTH, Constants.BOTTOM_BUTTON_HEIGHT);
					menuButtons.get(7).setRect(Constants.BOTTOM_BUTTON_X_2, Constants.BOTTOM_BUTTON_Y, Constants.BOTTOM_BUTTON_WIDTH, Constants.BOTTOM_BUTTON_HEIGHT);
					mouseCounter = Constants.MOUSE_COOLDOWN;
				} else if (mouse.intersects(menuButtons.get(1))) {
					menuButtons.clear();
					menuButtons.add(new Rectangle(Constants.BOTTOM_BUTTON_X_1, Constants.BOTTOM_BUTTON_Y, Constants.BOTTOM_BUTTON_WIDTH, Constants.BOTTOM_BUTTON_HEIGHT));
					menuState = MenuStates.HTP;
					mouseCounter = Constants.MOUSE_COOLDOWN;
				} else if (mouse.intersects(menuButtons.get(2))) {
					System.exit(0);
				}
			}
			break;
		case SELECTION:
			mouse.setRect(mouseX, mouseY, 1, 1);

			if (mouseClicked && mouseCounter == 0) {
				for (int i = 0; i < 3; i++) {
					if (mouse.intersects(menuButtons.get(2 * i))) {
						difficulty = i + 1;
						pointerOneY = Constants.POINTER_X_DISPLACE + i * Constants.POINTER_BETWEEN_DISPLACE;
					}
					
					if (mouse.intersects(menuButtons.get(2 * i + 1))) {
						size = i + 1;
						pointerTwoY = Constants.POINTER_X_DISPLACE + i * Constants.POINTER_BETWEEN_DISPLACE;
					}
				}
				if (mouse.intersects(menuButtons.get(6))) {
					menuState = MenuStates.MENU;
					menuButtons.clear();
					mouseCounter = Constants.MOUSE_COOLDOWN;
					
					for (int i = 0; i < 3; i++) {
						menuButtons.add(new Rectangle(0, 0, 0, 0));
					}
				} else if (mouse.intersects(menuButtons.get(7))) {

					if (pointerOneY != 0 && pointerTwoY != 0) {
						setSwitchPanel(false);

						difficulty = 0;
						size = 0;
						pointerOneY = 0;
						pointerTwoY = 0;

						menuButtons.clear();
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

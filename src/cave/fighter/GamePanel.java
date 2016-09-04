package cave.fighter;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JPanel;

import cave.fighter.utilities.Constants;

public class GamePanel extends CaveFighterPanel implements KeyListener {

	public GamePanel() throws IOException {
		
		switchPanel = false;
		
		addKeyListener(this);
	}

	@Override
	public void paintComponent(Graphics g) {

	}

	@Override
	public void run() {

		// redraw everything in the GamePanel
		repaint();

		System.out.println("game panel");
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
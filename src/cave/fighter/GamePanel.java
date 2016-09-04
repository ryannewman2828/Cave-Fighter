package cave.fighter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements MouseMotionListener,
		KeyListener {

	GamePanel() throws IOException {
		
		// Adds mouse listeners
		addMouseMotionListener(this);
		addKeyListener(this);
	}

	@Override
	public void paintComponent(Graphics g) {

	}

	// The main run loop
	public void run() throws InterruptedException, IOException {

		// Infinite loop
		while (true) {

			// redraw everything in the GamePanel
			repaint();

			// pause every 17 milliseconds
			// (runs program at 60 frames per second)
			Thread.sleep(17);

		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
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
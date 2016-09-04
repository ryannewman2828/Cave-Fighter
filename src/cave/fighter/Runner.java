package cave.fighter;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import cave.fighter.utilities.Constants;

public class Runner {
	
	public static void main(String[] args) throws IOException,
			InterruptedException {
		// Create and setup the JFrame
		JFrame myFrame = new JFrame();
		myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		myFrame.setVisible(true);
		myFrame.setSize(Constants.PANEL_WIDTH, Constants.PANEL_HEIGHT);
		myFrame.setResizable(false);
		myFrame.setTitle("Cave Fighter");
		myFrame.setLocationRelativeTo(null);

		// Create and insert our GamePanel into the JFrame
		GamePanel myPanel = new GamePanel();
		myPanel.setSize(Constants.PANEL_WIDTH, Constants.PANEL_HEIGHT);
		myPanel.setVisible(true);
		myFrame.setContentPane(myPanel);

		// calls on the run method
		myPanel.run();
	}

}

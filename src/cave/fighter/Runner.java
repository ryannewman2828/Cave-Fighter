package cave.fighter;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import cave.fighter.utilities.Assets;
import cave.fighter.utilities.Constants;

public class Runner {

	public static void main(String[] args) throws IOException,
			InterruptedException {
		
		Assets.init();
		
		JFrame myFrame = new JFrame();
		myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		myFrame.setVisible(true);
		myFrame.setSize(Constants.PANEL_WIDTH, Constants.PANEL_HEIGHT);
		myFrame.setResizable(false);
		myFrame.setTitle("Cave Fighter");
		myFrame.setLocationRelativeTo(null);

		PanelManager pm = new PanelManager(myFrame);
		
		pm.run();
	}

}

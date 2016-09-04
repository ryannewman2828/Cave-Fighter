package cave.fighter;

import java.io.IOException;

import cave.fighter.utilities.Constants;

public class PanelManager {

	private CaveFighterPanel curPanel;
	private final CaveFighterPanel menuPanel;
	private final CaveFighterPanel gamePanel;
	
	public PanelManager() throws IOException {
		menuPanel = new MenuPanel();
		menuPanel.setSize(Constants.PANEL_WIDTH, Constants.PANEL_HEIGHT);
		menuPanel.setVisible(true);

		gamePanel = new GamePanel();
		gamePanel.setSize(Constants.PANEL_WIDTH, Constants.PANEL_HEIGHT);
		gamePanel.setVisible(true);
		
		curPanel = menuPanel;
	}	
	
	public void run() throws InterruptedException{
	
		while (true) {

			curPanel.run();
			
			// pause every 17 milliseconds
			// (runs program at 60 frames per second)
			Thread.sleep(17);
		}
	}

}

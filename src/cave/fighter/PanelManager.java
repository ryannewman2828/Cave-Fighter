package cave.fighter;

import java.io.IOException;

import javax.swing.JFrame;

import cave.fighter.utilities.Constants;

public class PanelManager {

	private CaveFighterPanel curPanel;
	private final CaveFighterPanel menuPanel;
	private final CaveFighterPanel gamePanel;
	private final JFrame jFrame;
	
	public PanelManager(JFrame jFrame) throws IOException {
		this.jFrame = jFrame;
		
		menuPanel = new MenuPanel();
		menuPanel.setSize(Constants.PANEL_WIDTH, Constants.PANEL_HEIGHT);
		menuPanel.setVisible(true);

		gamePanel = new GamePanel();
		gamePanel.setSize(Constants.PANEL_WIDTH, Constants.PANEL_HEIGHT);
		gamePanel.setVisible(true);
		
		curPanel = menuPanel;
		jFrame.setContentPane(curPanel);
	}	
	
	// TODO make this more extendable later
	public void switchPanel(){
		if(curPanel == menuPanel){
			gamePanel.setSize(curPanel.getMapSize());
			gamePanel.setDifficulty(curPanel.getDifficulty());
			curPanel = gamePanel;
			((GamePanel) gamePanel).setUpMap();
		} else {
			curPanel = menuPanel;
		}
		jFrame.setContentPane(curPanel);
	}
	
	public void run() throws InterruptedException{
	
		while (true) {

			curPanel.run();
						
			if(curPanel.isSwitchPanel()){
				switchPanel();
			}
			
			// (runs program at 60 frames per second)
			Thread.sleep(17);
		}
	}

}

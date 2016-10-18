package cave.fighter;

import java.io.IOException;

import javax.swing.JFrame;

import cave.fighter.utilities.Constants;

public class PanelManager {

	private CaveFighterPanel curPanel;
	private CaveFighterPanel menuPanel;
	private CaveFighterPanel gamePanel;
	private final JFrame jFrame;
	
	public PanelManager(JFrame jFrame) throws IOException {
		this.jFrame = jFrame;

		startMenu();
		startGame();
		
		curPanel = menuPanel;
		jFrame.setContentPane(curPanel);
	}	
	
	public void switchPanel() throws IOException{
		if(curPanel == menuPanel){
			gamePanel.setSize(curPanel.getMapSize());
			gamePanel.setDifficulty(curPanel.getDifficulty());
			curPanel = gamePanel;
			((GamePanel) gamePanel).setUpMap();
			startMenu();
		} else {
			curPanel = menuPanel;
			startGame();
		}
		jFrame.setContentPane(curPanel);
		curPanel.requestFocus();
	}
	
	public void run() throws InterruptedException, IOException{
	
		while (true) {

			curPanel.run();
						
			if(curPanel.isSwitchPanel()){
				switchPanel();
			}
			
			// (runs program at 60 frames per second)
			Thread.sleep(17);
		}
	}
	
	private void startMenu() throws IOException{
		menuPanel = new MenuPanel();
		menuPanel.setSize(Constants.PANEL_WIDTH, Constants.PANEL_HEIGHT);
		menuPanel.setVisible(true);
	}
	
	private void startGame() throws IOException{
		gamePanel = new GamePanel();
		gamePanel.setSize(Constants.PANEL_WIDTH, Constants.PANEL_HEIGHT);
		gamePanel.setVisible(true);
	}

}

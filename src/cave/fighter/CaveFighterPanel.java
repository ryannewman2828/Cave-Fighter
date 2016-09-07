package cave.fighter;

import javax.swing.JPanel;

public abstract class CaveFighterPanel extends JPanel {

	private boolean switchPanel;
	private int mapSize;
	private int mapDifficulty;
	
	public abstract void run();

	public boolean isSwitchPanel() {
		return switchPanel;
	}

	public void setSwitchPanel(boolean switchPanel) {
		this.switchPanel = switchPanel;
	}

	public int getMapSize() {
		return mapSize;
	}

	public void setSize(int size) {
		this.mapSize = size;
	}

	public int getDifficulty() {
		return mapDifficulty;
	}

	public void setDifficulty(int difficulty) {
		this.mapDifficulty = difficulty;
	}
	
}

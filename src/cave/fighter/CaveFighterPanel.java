package cave.fighter;

import javax.swing.JPanel;

public abstract class CaveFighterPanel extends JPanel {

	private boolean switchPanel;
	
	public abstract void run();

	public boolean isSwitchPanel() {
		return switchPanel;
	}

	public void setSwitchPanel(boolean switchPanel) {
		this.switchPanel = switchPanel;
	}
	
}

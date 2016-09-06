package cave.fighter.environment;

import cave.fighter.boss.ArchMageBoss;
import cave.fighter.boss.MageBoss;
import cave.fighter.boss.Shade;
import cave.fighter.character.MainCharacter;


public class BossRoom extends Room {

	public BossRoom(int difficulty) {

		super(difficulty, true);

		//Indicates that this is the boss room
		setBossRoom(true);

		switch (difficulty) {
		case 1:
			setBoss(new MageBoss());
			break;
		case 2:
			setBoss(new ArchMageBoss());
			break;
		case 3:
			setBoss(new Shade());
			break;
		}

	}
}
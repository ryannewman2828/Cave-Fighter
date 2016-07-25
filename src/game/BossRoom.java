/**
 * Ryan Newman
 * Cave Fighter
 * Version 1.1
 * November 26 2015 
*/
package game;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BossRoom extends Room {

	public BossRoom(MainCharacter character, int difficulty) {

		super(difficulty, character, true);
		
		//Sets the character
		setCharacter(character);

		//Indicates that this is the boss room
		setBossRoom(true);

		//Sets the boss depending on difficulty
		switch (difficulty) {
		case 1:
			setBoss(new MageBoss(character));
			break;
		case 2:
			setBoss(new ArchMageBoss(character));
			break;
		case 3:
			setBoss(new Shade(character));
			break;
		}

	}
}
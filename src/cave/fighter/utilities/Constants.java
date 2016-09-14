package cave.fighter.utilities;

public final class Constants {

	public static final int PANEL_HEIGHT = 505;
	public static final int PANEL_WIDTH = 805;
	
	public static final int POINTER_1_X = 5;
	public static final int POINTER_2_X = 725;
	public static final int POINTER_X_DISPLACE = 40;
	public static final int POINTER_BETWEEN_DISPLACE = 120;
	
	public static final int MENU_BUTTON_WIDTH = 545;
	public static final int MENU_BUTTON_HEIGHT = 85;
	public static final int MENU_BUTTON_X = 125;
	public static final int MENU_BUTTON_TOP_DISPLACE = 142;
	public static final int MENU_BUTTON_BETWEEN_DISPLACE = 110;
	
	public static final int SELECTION_BUTTON_WIDTH = 300;
	public static final int SELECTION_BUTTON_HEIGHT = 73;
	public static final int SELECTION_BUTTON_LEFT_DISPLACE = 75;
	public static final int SELECTION_BUTTON_TOP_DISPLACE = 35;
	public static final int SELECTION_BUTTON_X_DISPLACE = 350;
	public static final int SELECTION_BUTTON_Y_DISPLACE = 120;
	
	public static final int BOTTOM_BUTTON_WIDTH = 190;
	public static final int BOTTOM_BUTTON_HEIGHT = 45;
	public static final int BOTTOM_BUTTON_Y = 403;
	public static final int BOTTOM_BUTTON_X_1 = 50;
	public static final int BOTTOM_BUTTON_X_2 = 558;
	
	public static final int MOUSE_COOLDOWN = 15;
	
	public static final int POWERUP_X = 400;
	public static final int POWERUP_Y = 240;
	public static final int POWERUP_SIZE = 40;
	
	public static final int CHAR_MAX_HEALTH = 10;
	public static final int CHAR_START_HEALTH = 6;
	public static final int CHAR_MAX_SPEED = 6;
	public static final int CHAR_START_SPEED = 6; //3;
	public static final int CHAR_START_BULLET_SPEED = 4;
	public static final int CHAR_MAX_FIRE_RATE = 10;
	public static final int CHAR_START_FIRE_RATE = 25;
	public static final int CHAR_DECREASE_FIRE_RATE = 5;
	public static final int CHAR_MAX_DAMAGE = 5;
	public static final int CHAR_START_DAMAGE = 5; //1;
	public static final int CHAR_START_X = 400;
	public static final int CHAR_START_Y = 240;
	public static final int CHAR_HITBOX_1_X_DISPLACE = 15;
	public static final int CHAR_HITBOX_2_X_DISPLACE = 12;
	public static final int CHAR_HITBOX_1_Y_DISPLACE = 9;
	public static final int CHAR_HITBOX_2_Y_DISPLACE = 23;
	public static final int CHAR_HITBOX_1_WIDTH = 29;
	public static final int CHAR_HITBOX_2_WIDTH = 24;
	public static final int CHAR_HITBOX_1_HEIGHT = 18;
	public static final int CHAR_HITBOX_2_HEIGHT = 22;
	public static final int CHAR_BULLET_Y_DISPLACE = 12;
	public static final int CHAR_BULLET_COOLDOWN = 120;
	public static final int CHAR_DISPLACE = 12;
	public static final int CHAR_HEAD_COOLDOWN = 15;
	public static final int CHAR_ANIMATION = 10;
	public static final int BULLET_FULL_SIZE = 32;
	public static final int BULLET_HALF_SIZE = 16;
	
	public static final int MAP_ROOM_OFFSET = 3;
	public static final int MAP_ROOM_SMALL = 9;
	public static final int MAP_ROOM_MEDIUM = 19;
	public static final int MAP_ROOM_LARGE = 29;
	public static final int MAP_ROOM_Y_START_TOP = 100;
	public static final int MAP_ROOM_Y_START_BOT = 450;
	public static final int MAP_ROOM_X_START_LEFT = 40;
	public static final int MAP_ROOM_X_START_RIGHT = 740;
	public static final int MAP_LEFT_BOUND = 10;
	public static final int MAP_RIGHT_BOUND = 780;
	public static final int MAP_TOP_BOUND = 80;
	public static final int MAP_BOTTOM_BOUND = 460;
	
	public static final int NUM_ENEMY_TYPE = 4;
	public static final int ENEMY_SPAWN_X = 400;
	public static final int ENEMY_SPAWN_Y = 140;
	public static final int ENEMY_ANIMATION = 15;
	public static final int ENEMY_SPAWN_TIME = 120;
	public static final int TENTACLE_SPAWN_TIME = 80;
	
	public static final int TENTACLE_SPEED = 0;
	public static final int TENTACLE_HEALTH = 7;
	public static final int TENTACLE_DAMAGE = 2;
	
	public static final int BOSS_X = 420;
	public static final int BOSS_Y = 240;
	public static final float ALPHA = 0.04f;
	public static final int MAGE_SPEED = 1;
	public static final int MAGE_HEALTH = 100;
	public static final int MAGE_DAMAGE = 1;
	public static final int MAGE_WIDTH = 85;
	public static final int MAGE_HEIGHT = 94;
	public static final int MAGE_SPAWN_COUNT = 140;
	public static final int ARCH_MAGE_SPEED = 1;
	public static final int ARCH_MAGE_HEALTH = 200;
	public static final int ARCH_MAGE_DAMAGE = 2;
	public static final int ARCH_MAGE_WIDTH = 122;
	public static final int ARCH_MAGE_HEIGHT = 110;
	public static final int ARCH_MAGE_SPAWN_COUNT = 140;
	public static final int SHADE_SPEED = 1;
	public static final int SHADE_HEALTH = 500;
	public static final int SHADE_DAMAGE = 3;
	public static final int SHADE_WIDTH = 80;
	public static final int SHADE_HEIGHT = 70;
	public static final int SHADE_START_SPAWN_COUNT = 40;
	public static final int SHADE_SPAWN_COUNT = 120;
	public static final int MOVE_POSSIBILITIES = 5;
	public static final int SHADE_MOVE_POSSIBILITIES = 5;
	public static final int SHADE_SPAWNER_COUNT = 180;
	
	public static final int MAGE_HITBOX_X_DISPLACE_1 = -9;
	public static final int MAGE_HITBOX_X_DISPLACE_2 = -17;
	public static final int MAGE_HITBOX_X_DISPLACE_3 = -33;
	public static final int MAGE_HITBOX_X_DISPLACE_4 = -23;
	public static final int MAGE_HITBOX_Y_DISPLACE_1 = -40;
	public static final int MAGE_HITBOX_Y_DISPLACE_2 = -20;
	public static final int MAGE_HITBOX_Y_DISPLACE_3 = 32;
	public static final int MAGE_HITBOX_Y_DISPLACE_4 = 18;
	public static final int MAGE_WIDTH_1 = 15;
	public static final int MAGE_WIDTH_2 = 33;
	public static final int MAGE_WIDTH_3 = 61;
	public static final int MAGE_WIDTH_4 = 41;
	public static final int MAGE_HEIGHT_1 = 85;
	public static final int MAGE_HEIGHT_2 = 20;
	public static final int MAGE_HEIGHT_3 = 14;
	public static final int MAGE_HEIGHT_4 = 14;
	
	public static final int ARCH_MAGE_HITBOX_X_DISPLACE_1 = -1;
	public static final int ARCH_MAGE_HITBOX_X_DISPLACE_2 = -23;
	public static final int ARCH_MAGE_HITBOX_X_DISPLACE_3 = -16;
	public static final int ARCH_MAGE_HITBOX_Y_DISPLACE_1 = -34;
	public static final int ARCH_MAGE_HITBOX_Y_DISPLACE_2 = -20;
	public static final int ARCH_MAGE_HITBOX_Y_DISPLACE_3 = 34;
	public static final int ARCH_MAGE_WIDTH_1 = 14;
	public static final int ARCH_MAGE_WIDTH_2 = 58;
	public static final int ARCH_MAGE_WIDTH_3 = 43;
	public static final int ARCH_MAGE_HEIGHT_1 = 83;
	public static final int ARCH_MAGE_HEIGHT_2 = 36;
	public static final int ARCH_MAGE_HEIGHT_3 = 14;
	
	public static final int SHADE_HITBOX_X_DISPLACE_1 = -6;
	public static final int SHADE_HITBOX_X_DISPLACE_2 = 8;
	public static final int SHADE_HITBOX_X_DISPLACE_3 = -24;
	public static final int SHADE_HITBOX_X_DISPLACE_4 = -40;
	public static final int SHADE_HITBOX_Y_DISPLACE_1 = -33;
	public static final int SHADE_HITBOX_Y_DISPLACE_2 = -13;
	public static final int SHADE_HITBOX_Y_DISPLACE_3 = 26;
	public static final int SHADE_HITBOX_Y_DISPLACE_4 = -22;
	public static final int SHADE_WIDTH_1 = 14;
	public static final int SHADE_WIDTH_2 = 12;
	public static final int SHADE_WIDTH_3 = 62;
	public static final int SHADE_WIDTH_4 = 28;
	public static final int SHADE_HEIGHT_1 = 65;
	public static final int SHADE_HEIGHT_2 = 45;
	public static final int SHADE_HEIGHT_3 = 6;
	public static final int SHADE_HEIGHT_4 = 15;
	
	public static final int BOSS_DELAY_INCREMENT = 20;
	public static final int SECONDARY_DELAY_INCREMENT = 20;
	public static final int DELAY_INCREMENT = 30;
	public static final int DELAY_MULTIPLIER = 10;
	public static final int SPEED_DEFLATER = 5;
	public static final int MAGE_DEFLATER = 25;
	public static final int ARCH_MAGE_DEFLATER = 50;
	
	public static final int HEART_X_LOCATION_MULTIPLIER = 60;
	public static final int HEART_DISPLACE = 40;
	public static final int HEART_Y = 30;
	
	public static final int BOSS_BAR_INCREMENT = 2;
	public static final int BOSS_BAR_MAX = 200;
	public static final int BAR_OUTSIDE_X = 495;
	public static final int BAR_INSIDE_X = 500;
	public static final int BAR_OUTSIDE_Y = 40;
	public static final int BAR_INSIDE_Y = 45;
	public static final int BAR_WIDTH = 200;
	public static final int BAR_HEIGHT = 20;
	public static final int BAR_DISPLACE = 10;

	private Constants() 
	{/* Utility Class */}

}

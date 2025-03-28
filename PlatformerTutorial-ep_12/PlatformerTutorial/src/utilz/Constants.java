package utilz;

import main.Game;

public class Constants {

	public static class UI {
		public static class Buttons {
			public static final int B_WIDTH_DEFAULT = 140;
			public static final int B_HEIGHT_DEFAULT = 56;
			public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
			public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
		}
	}

	public static class Directions {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}

	public static class PlayerConstants {
		public static final int IDLE = 0;
		public static final int WALK = 1;
		public static final int RUNNING = 2;
		public static final int FALLING= 3;
		public static final int JUMP = 4;
		public static final int HIT = 5;
		public static final int DEAD = 6;
		public static final int ATTACK_1 = 7;
		public static final int ATTACK_JUMP_1 = 8;

		public static int GetSpriteAmount(int player_action) {
			switch (player_action) {
			case RUNNING:
				return 8;
			case IDLE:
				return 4;
			case HIT:
				return 2;
			case JUMP:
			case ATTACK_1:
			case FALLING:
				return 8;
				case ATTACK_JUMP_1:
			default:
				return 1;
			}
		}
	}

}

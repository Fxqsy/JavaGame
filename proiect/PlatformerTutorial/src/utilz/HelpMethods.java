package utilz;

import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.Set;

import main.Game;

public class HelpMethods {

	private static final Set<Integer> TRANSP_TILES = new HashSet<>();
	static {
		TRANSP_TILES.add(95);
		TRANSP_TILES.add(84);
		TRANSP_TILES.add(72);
		TRANSP_TILES.add(85);
		TRANSP_TILES.add(73);
		TRANSP_TILES.add(86);
		TRANSP_TILES.add(74);
		TRANSP_TILES.add(61);
		TRANSP_TILES.add(23);
		TRANSP_TILES.add(45);
		TRANSP_TILES.add(46);
		TRANSP_TILES.add(47);
		TRANSP_TILES.add(57);
		TRANSP_TILES.add(58);
		TRANSP_TILES.add(59);
		TRANSP_TILES.add(10);
		TRANSP_TILES.add(11);
		TRANSP_TILES.add(27);
		TRANSP_TILES.add(28);
		TRANSP_TILES.add(39);
		TRANSP_TILES.add(22);
		TRANSP_TILES.add(40);
		TRANSP_TILES.add(29);
		TRANSP_TILES.add(30);
		TRANSP_TILES.add(31);
		TRANSP_TILES.add(32);
		TRANSP_TILES.add(41);
		TRANSP_TILES.add(42);
		TRANSP_TILES.add(43);
		TRANSP_TILES.add(44);
		TRANSP_TILES.add(51);
		TRANSP_TILES.add(52);
		TRANSP_TILES.add(53);
	}

	private static final Set<Integer> SOLID_TILES = new HashSet<>();
	static{
		SOLID_TILES.add(1);
		SOLID_TILES.add(2);
		SOLID_TILES.add(3);
		SOLID_TILES.add(4);
		SOLID_TILES.add(5);
		SOLID_TILES.add(6);
		SOLID_TILES.add(7);
		SOLID_TILES.add(8);
		SOLID_TILES.add(9);
		SOLID_TILES.add(13);
		SOLID_TILES.add(14);
		SOLID_TILES.add(15);
		SOLID_TILES.add(16);
		SOLID_TILES.add(17);
		SOLID_TILES.add(18);
		SOLID_TILES.add(19);
		SOLID_TILES.add(20);
		SOLID_TILES.add(21);
		SOLID_TILES.add(24);
		SOLID_TILES.add(25);
		SOLID_TILES.add(26);

	}

	public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
		if (!IsSolid(x, y, lvlData))
			if (!IsSolid(x + width, y + height, lvlData))
				if (!IsSolid(x + width, y, lvlData))
					if (!IsSolid(x, y + height, lvlData))
						return true;
		return false;
	}

	private static boolean IsSolid(float x, float y, int[][] lvlData) {
		int maxWidth = lvlData[0].length * Game.TILES_SIZE;
		if (x < 0 || x >= maxWidth)
			return true;
		if (y < 0 || y >= Game.GAME_HEIGHT)
			return true;
		float xIndex = x / Game.TILES_SIZE;
		float yIndex = y / Game.TILES_SIZE;

		int value = lvlData[(int) yIndex][(int) xIndex];

		if (value >= 96 || value < 0 || SOLID_TILES.contains(value))
			return true;
		return false;
	}

	public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
		int currentTile = (int) (hitbox.x / Game.TILES_SIZE);
		if (xSpeed > 0) {
			// Right
			int tileXPos = currentTile * Game.TILES_SIZE;
			int xOffset = (int) (Game.TILES_SIZE - hitbox.width);
			return tileXPos + xOffset - 1;
		} else
			// Left
			return currentTile * Game.TILES_SIZE;
	}

	public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
		int currentTile = (int) (hitbox.y / Game.TILES_SIZE);
		if (airSpeed > 0) {
			// Falling - touching floor
			int tileYPos = currentTile * Game.TILES_SIZE;
			int yOffset = (int) (Game.TILES_SIZE - hitbox.height);
			return tileYPos + yOffset - 1;
		} else
			// Jumping
			return currentTile * Game.TILES_SIZE;

	}

	public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
		// Check the pixel below bottomleft and bottomright
		if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
			if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
				return false;

		return true;

	}

}
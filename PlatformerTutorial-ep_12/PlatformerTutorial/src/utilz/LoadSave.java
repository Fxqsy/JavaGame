package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.Game;

public class LoadSave {

	public static final String PLAYER_ATLAS = "char_anim.png";
	public static final String LEVEL_ATLAS = "free.png";
	public static final String LEVEL_ONE_DATA = "harta.png";
	public static final String MENU_BUTTONS = "button_atlas.png";
	public static final String MENU_BACKGROUND = "menu_background.png";

	public static BufferedImage GetSpriteAtlas(String fileName) {
		BufferedImage img = null;
		InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
		try {
			img = ImageIO.read(is);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return img;
	}

	public static int[][] GetLevelData() {
		int[][] lvlData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
		BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);

		for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
			for (int i = 0; i < Game.TILES_IN_WIDTH; i++) {
				if (j < img.getHeight() && i < img.getWidth()) {
					// Only process pixels inside the image dimensions
					Color color = new Color(img.getRGB(i, j));
					int value = color.getRed();
					if (value >= 80) {
						value = 0;
					}
					lvlData[j][i] = value;
				} else {
					// Fallback value for out-of-bounds cases (value 0)
					lvlData[j][i] = 0;
				}
			}
		}
		return lvlData;
	}

}

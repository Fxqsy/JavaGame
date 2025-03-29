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
//	public static final String LEVEL_ONE_DATA = "pixil-frame-0.png";
	public static final String LEVEL_ONE_DATA = "lvl_long.png";
	public static final String MENU_BUTTONS = "button_atlas1.png";
	public static final String MENU_BACKGROUND = "Menu_bg.png";
	public static final String PAUSE_BACKGROUND = "pause_menu1.png";
	public static final String SOUND_BUTTONS = "sound_button1.png";
	public static final String URM_BUTTONS = "urm_buttons1.png";
	public static final String VOLUME_BUTTONS = "volume_buttons1.png";


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
		BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
		int[][] lvlData = new int[img.getHeight()][img.getWidth()];

		for (int j = 0; j < img.getHeight(); j++) {
			for (int i = 0; i < img.getWidth(); i++) {
					Color color = new Color(img.getRGB(i, j));
					int value = color.getRed();
					if (value >= 96) {
						value = 0;
					}
					lvlData[j][i] = value;
				}


		}
		return lvlData;
	}

}

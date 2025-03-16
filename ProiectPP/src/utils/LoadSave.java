package utils;

import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {
    public static final String LEVEL_ATLAS = "outside_sprites.png";
    public static final String PLAYER_ATLAS = "anim_sheet2_final.png";
    public static final String LEVEL_ONE_DATA = "level_one_data.png";
    public static final String LEVEL_VISUAL = "1.png";
    public static final String LEVEL_MASK = "1_data.png";


    public static BufferedImage GetSpriteAtlas(String fileName){
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

    public static int[][] GetLevelData(){
        int[][] lvlData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
        BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);

        for(int j=0;j<img.getHeight();j++)
            for(int i=0;i<img.getWidth();i++) {
                Color color = new Color(img.getRGB(i,j));
                int value = color.getRed();
                if(value >= 48)
                    value = 0;
                lvlData[j][i] = color.getRed();
            }
        return lvlData;
    }

//    public static int[][] GetLevelData() {
//        BufferedImage img = GetLevelMask(); // Load the collision mask
//        int[][] lvlData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
//
//        for (int j = 0; j < img.getHeight(); j++) {
//            for (int i = 0; i < img.getWidth(); i++) {
//                Color color = new Color(img.getRGB(i, j));
//
//                // Check if the pixel is ground (255, 242, 0)
//                if (color.getRed() == 255 && color.getGreen() == 242 && color.getBlue() == 0) {
//                    lvlData[j][i] = 1; // Ground (solid)
//                }
//                // Check if the pixel is walkable (0, 0, 0)
//                else if (color.getRed() == 0 && color.getGreen() == 0 && color.getBlue() == 0) {
//                    lvlData[j][i] = 0; // Walkable (non-solid)
//                }
//                // Default to non-solid for any other color
//                else {
//                    lvlData[j][i] = 0; // Non-solid
//                }
//            }
//        }
//        return lvlData;
//    }

    public static BufferedImage GetLevelVisual(){
        return GetSpriteAtlas(LEVEL_VISUAL);
    }

    public static BufferedImage GetLevelMask(){
        return GetSpriteAtlas(LEVEL_MASK);
    }
}

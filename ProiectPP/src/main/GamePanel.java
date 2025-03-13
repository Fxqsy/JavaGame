package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import static utils.Constants.PlayerConstants.*;
import static utils.Constants.Directions.*;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private float xDelta=100, yDelta=100;
    private BufferedImage img;
    private BufferedImage[][] animations;
    private int aniTick,aniIndex,aniSpeed=15;
    private int playerAction = IDLE;
    private int playerDir = -1;
    private boolean moving = false;
    public GamePanel(){

        mouseInputs=new MouseInputs(this);
        importImg();
        loadAnimations();

        setFocusable(true);
        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void loadAnimations() {
        animations = new BufferedImage[2][39];
        for(int j=0;j<animations.length;j++)
            for(int i=0;i<animations[j].length;i++){
            animations[j][i]=img.getSubimage(i*122,j*200,122,200);
        }
    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/69.png");

        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setPanelSize() {
        Dimension size= new Dimension(1080,720);
        setPreferredSize(size);
    }

    public void setDirection(int direction){
        this.playerDir=direction;
        moving = true;
    }

    public void setMoving(boolean moving){
        this.moving = moving;
    }

    private void updateAnimationTick() {
        aniTick++;
        if(aniTick >= aniSpeed){
            aniTick=0;
            aniIndex++;
            if(aniIndex >= GetSpriteAmount(playerAction)){
                aniIndex=0;
            }
        }

    }

    private void setAnimation() {
        if(moving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;
    }

    private void updatePos() {
        if(moving){
            switch(playerDir){
                case LEFT:
                    xDelta-=2;
                    break;
                case UP:
                    yDelta-=2;
                    break;
                case RIGHT:
                    xDelta+=2;
                    break;
                case DOWN:
                    yDelta+=2;
                    break;
            }
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        updateAnimationTick();

        setAnimation();
        updatePos();
        g.drawImage(animations[playerAction][aniIndex],(int)xDelta,(int)yDelta,null);


    }




}


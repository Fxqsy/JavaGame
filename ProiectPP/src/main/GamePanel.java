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

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private float xDelta=100, yDelta=100;
    private BufferedImage img;
    private BufferedImage[] idleAni,running;
    private int aniTick,aniIndex,aniSpeed=15;

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
        idleAni = new BufferedImage[9];
        for(int i=0;i<idleAni.length;i++){
            idleAni[i]=img.getSubimage(i*91,0,91,150);
        }
    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/1.png");

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

    public void changeXDelta(int value){
        this.xDelta+=value;
        repaint();
    }

    public void changeYDelta(int value){
        this.yDelta+=value;
        repaint();
    }

    public void setRectPos(int x,int y){
        this.xDelta=x;
        this.yDelta=y;
        repaint();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        updateAnimationTick();
        
        g.drawImage(idleAni[aniIndex],(int)xDelta,(int)yDelta,null);


    }

    private void updateAnimationTick() {
        aniTick++;
        if(aniTick >= aniSpeed){
            aniTick=0;
            aniIndex++;
            if(aniIndex >= idleAni.length){
                aniIndex=0;
            }
        }

    }

}


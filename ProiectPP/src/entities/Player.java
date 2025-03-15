package entities;

import main.Game;
import utils.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utils.Constants.Directions.*;
import static utils.Constants.Directions.DOWN;
import static utils.Constants.PlayerConstants.*;
import static utils.HelpMethods.CanMoveHere;

public class Player extends Entity{

    private BufferedImage[][] animations;
    private int aniTick,aniIndex,aniSpeed=15;
    private int playerAction = IDLE;
    private boolean moving = false , attacking = false;
    private boolean contAttack = false;
    private int attackTimer = 0;
    private  final int ATTACK_DURATION = 30;
    private boolean left, up, right, down;
    private float playerSpeed = 2.0f;
    private int[][] lvlData;

    private float yDrawOffset = 235 *Game.SCALE;
    private float xDrawOffset = 198 *Game.SCALE;

    public Player(float x, float y,int width, int height) {
        super(x, y,width,height);
        loadAnimations();
        initHitbox(x,y,109*Game.SCALE,169*Game.SCALE);
    }

    public void update() {

        updatePos();
        updateAnimationTick();
        setAnimation();

    }

    public void render(Graphics g) {
// scalare caracter 500 x 570 original
        g.drawImage(animations[playerAction][aniIndex],(int)(hitbox.x- xDrawOffset),(int)(hitbox.y-yDrawOffset) ,(int)(500*Game.SCALE),(int)(570*Game.SCALE),null);
        drawHitbox(g);
    }


    private void updateAnimationTick() {
        aniTick++;
        if(aniTick >= aniSpeed){
            aniTick=0;
            aniIndex++;
            if(aniIndex >= GetSpriteAmount(playerAction)){
                aniIndex=0;
                attacking = false;
            }
        }

    }

    private void setAnimation() {
        int startAni = playerAction;

        if(moving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;

        if(attacking)
        {
            playerAction = ATTACK_1;

        }

//        if(contAttack){
//            playerAction = ATTACK_1;
//            System.out.println(aniIndex);
//            switch (playerAction){
//                case ATTACK_1:
//                    playerAction= ATTACK_2;
//                    aniIndex=16;
//                    break;
//                case ATTACK_2:
//                    playerAction = ATTACK_3;
//                    aniIndex=17;
//                    break;
//                case ATTACK_3:
//                    playerAction = ATTACK_1;
//                    aniIndex= 18;
//                    break;
//            }
//        }
        if(startAni != playerAction){
            resetAniTick();
        }
    }

    private void resetAniTick() {
        aniTick=0;
        aniIndex=0;
    }

    private void updatePos() {

        moving = false;
        if(!left && !right && !up && !down)
            return;

        float xSpeed=0, ySpeed=0;

        if(left && !right)
            xSpeed=-playerSpeed;
        else if(right && !left)
            xSpeed=playerSpeed;


        if(up && !down)
            ySpeed=-playerSpeed;
        else if(down && !up)
            ySpeed=playerSpeed;

//        if(CanMoveHere(x+xSpeed,y+ySpeed,width,height,lvlData)){
//            this.x+=xSpeed;
//            this.y+=ySpeed;
//            moving=true;
//        }
        if(CanMoveHere(hitbox.x+xSpeed,hitbox.y+ySpeed,hitbox.width,hitbox.height,lvlData)){
            hitbox.x+=xSpeed;
            hitbox.y+=ySpeed;
            moving=true;
        }

    }

    private void loadAnimations() {

            BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
            animations = new BufferedImage[9][35];
            for(int j=0;j<animations.length;j++)
                for(int i=0;i<animations[j].length;i++){
                    int subimageWidth = 500;
                    int subimageHeight = 570;
                    int x = i * subimageWidth;
                    int y = j * subimageHeight;

                    if (x + subimageWidth <= img.getWidth() && y + subimageHeight <= img.getHeight()) {
                        animations[j][i] = img.getSubimage(x, y, subimageWidth, subimageHeight);
                    } else {
//                    System.err.println("Subimage out of bounds: (" + x + ", " + y + ")");
                    }
//                animations[j][i]=img.getSubimage(i*500,j*570,500,570);
                }

    }


    public void loadLvlData(int[][] lvlData){
        this.lvlData = lvlData;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void resetDirBooleans() {
        left = false;
        right = false;
        up = false;
        down = false;
    }

    public void setAttacking(boolean attacking){
        this.attacking = attacking;
    }

    public void setContAttacking(boolean contAttack){
        this.contAttack = contAttack;
    }

}

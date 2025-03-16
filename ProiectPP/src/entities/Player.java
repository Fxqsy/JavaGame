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
import static utils.HelpMethods.*;


public class Player extends Entity{

    private BufferedImage[][] animations;
    private int aniTick,aniIndex,aniSpeed=15;
    private int playerAction = IDLE;
    private boolean moving = false , attacking = false, contAttack = false;
    private boolean left, up, right, down, jump;
    private float playerSpeed = 1.0f * Game.SCALE;
    private int[][] lvlData;

    private float yDrawOffset = 235/3f *Game.SCALE;
    private float xDrawOffset = 198/3f *Game.SCALE;

    //jumping // gravity
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f *Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;

    public Player(float x, float y,int width, int height) {
        super(x, y,width,height);
        loadAnimations();
        initHitbox(x,y,(int)(109*Game.SCALE/3),(int)(168*Game.SCALE/3));



    }

    public void update() {

        updatePos();
        updateAnimationTick();
        setAnimation();

    }

    public void render(Graphics g, int lvlOffset) {
// scalare caracter 500 x 570 original
        g.drawImage(animations[playerAction][aniIndex],(int)(hitbox.x- xDrawOffset) - lvlOffset,(int)(hitbox.y-yDrawOffset) ,(int)(500*Game.SCALE/3),(int)(570*Game.SCALE/3),null);
//        drawHitbox(g);
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

        if(inAir){
            if(airSpeed < 0)
                playerAction = JUMP;
            else
                playerAction = FALLING;
        }

        if(attacking)
            playerAction = ATTACK_1;

        if(contAttack)
            playerAction = ATTACK_HOLD;


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
        if(jump)
            jump();
//        if(!left && !right && !inAir)
//            return;
        if(!inAir){
            if((!left && !right) || (right && left))
                return;
        }
        float xSpeed=0;

        if(left)
            xSpeed-=playerSpeed;
        if(right)
            xSpeed+=playerSpeed;

        if(inAir)
            if(!IsEntityOnFloor(hitbox, lvlData))
                inAir = true;

        if(inAir){
            if(CanMoveHere(hitbox.x,hitbox.y+ airSpeed,hitbox.width,hitbox.height,lvlData)){
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            }else{
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox,airSpeed);
                if(airSpeed > 0){
                    resetInAir();
                }
                else{
                    airSpeed = fallSpeedAfterCollision;
                }
                updateXPos(xSpeed);
            }

        }else
            updateXPos(xSpeed);

        moving = true;


    }

    private void jump() {
        if(inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;

    }

    private void resetInAir() {
        inAir=false;
        airSpeed=0;
    }

    private void updateXPos(float xSpeed) {
        if(CanMoveHere(hitbox.x+xSpeed,hitbox.y,hitbox.width,hitbox.height,lvlData)){
            hitbox.x+=xSpeed;
        }else {
            hitbox.x = GetEntityXPosNextToWall(hitbox,xSpeed);
        }
    }

    private void loadAnimations() {

            BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
            animations = new BufferedImage[10][32];
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
        if(!IsEntityOnFloor(hitbox,lvlData))
            inAir=true;
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

    public void setJump(boolean jump){
        this.jump=jump;
    }

}

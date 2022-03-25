package com.sxt;

import java.awt.*;
import java.util.Random;

public class Bot extends Tank {
    int moveTime = 0;
    public Bot(String img, int x, int y, GamePanel gamePanel,
               String upImg, String leftImg,
               String rightImg, String downImg) {
        super(img, x, y, gamePanel, upImg, leftImg, rightImg, downImg);
    }

    //电脑坦克随机方向
    public Direction getRandomDirection() {
        Random random = new Random();
        int rnum = random.nextInt(4);
        switch(rnum) {
            case 0:
                return Direction.UP;
            case 1:
                return Direction.RIGHT;
            case 2:
                return Direction.LEFT;
            default:
                return Direction.DOWN;
        }
    }

    public void go(){
        attack();
        if(moveTime>=20) {
            direction=getRandomDirection();
            moveTime=0;
        }else {
            moveTime++;
        }
        switch(direction) {
            case UP:
                upward();
                break;
            case DOWN:
                downward();
                break;
            case RIGHT:
                rightward();
                break;
            case LEFT:
                leftward();
                break;
        }
    }
    //电脑坦克有1%的几率攻击
    public void attack(){
        Point p = getHeadPoint();
        Random random = new Random();
        int rnum = random.nextInt(100);
        if(rnum<4){
            this.gamePanel.bulletList.add(new EnemyBullet("images/bullet/bulletYellow.gif",p.x,p.y,
                    this.gamePanel, direction));
        }
    }





    @Override
    public void paintSelf(Graphics g) {
        g.drawImage(img,x,y,null);
        go();
    }

    @Override
    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
    }
}

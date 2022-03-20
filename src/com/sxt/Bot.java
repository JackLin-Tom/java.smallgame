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
    public Direction getrandomDirection() {
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
        if(moveTime>=20) {
            direction=getrandomDirection();
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




    @Override
    public void paintSelf(Graphics g) {
        g.drawImage(img,x,y,null);


    }

    @Override
    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
    }
}

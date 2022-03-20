package com.sxt;

import java.awt.*;
import java.util.ArrayList;

public class Bullet extends GameObject {
    //尺寸
    int width = 10;
    int height = 10;
    //速度
    int speed = 7;
    //方向
    Direction direction;

    public Bullet(String img, int x, int y, GamePanel gamePanel, Direction direction) {
        super(img, x, y, gamePanel);
        this.direction = direction;

    }
    public void leftward(){
        x-=speed;

    }

    public void upward(){
        y-=speed;

    }

    public void rightward(){
        x+=speed;

    }
    public void downward(){
        y+=speed;

    }
    public void go(){
        switch(direction){
            case LEFT:
                leftward();
                break;
            case RIGHT:
                rightward();
                break;
            case UP:
                upward();
                break;
            case DOWN:
                downward();
                break;
        }
    }
    //我方子弹与敌方坦克的碰撞检测
    public void hitBot(){
        ArrayList<Bot>bots = this.gamePanel.botList;
        for(Bot bot: bots){
            if(this.getRec().intersects(bot.getRec())){
                this.gamePanel.botList.remove(bot);
                this.gamePanel.removeList.add(this);
                break;
            }
        }
    }
    @Override
    public void paintSelf(Graphics g) {
        g.drawImage(img,x,y,null);
        this.go();
        hitBot();
    }

    @Override
    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
    }
}

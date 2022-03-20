package com.sxt;

import java.awt.*;

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
    @Override
    public void paintSelft(Graphics g) {
        g.drawImage(img,x,y,null);
        this.go();
    }

    @Override
    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
    }
}

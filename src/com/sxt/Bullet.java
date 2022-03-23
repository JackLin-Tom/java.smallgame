package com.sxt;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
        moveToBorder();
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
        this.hitWall();
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
    //子弹与wall碰撞
    public void hitWall(){
        //围墙列表
        ArrayList<Wall> walls = this.gamePanel.wallList;
        //遍历列表
        for(Wall wall: walls){
            //与每个围墙进行碰撞检测
            if(this.getRec().intersects(wall.getRec())){
                //删去围墙和子弹
                this.gamePanel.wallList.remove(wall);
                this.gamePanel.removeList.add(this);
                break;
            }
        }
    }

/*    public void hitWallA(){
        Rectangle next = this.getRec();
        List<Wall> walls = this.gamePanel.wallList;
        for(Wall wall: walls) {
            if (wall.getRec().intersects(next)) {
                this.gamePanel.wallList.remove(wall);
                this.gamePanel.removeList.add(this);
                break;
            }
        }
    }*/
    public void moveToBorder(){
        if(x<0||x>this.gamePanel.getWidth()-width){
            this.gamePanel.removeList.add(this);
            System.out.println("bullet hit border");
        }
        if(y<0||y>this.gamePanel.getHeight()-height){
            this.gamePanel.removeList.add(this);
            System.out.println("bullet hit border");
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

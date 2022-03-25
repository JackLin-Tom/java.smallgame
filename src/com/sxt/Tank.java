package com.sxt;

import java.awt.*;
import java.util.ArrayList;

public abstract  class Tank extends GameObject{
    //尺寸
    public int width = 40;
    public int height =50;
    //坦克的初始方向（向上）
    Direction direction = Direction.UP;
    //生命——初始三条命
    public int alive = 1;
    //速度
    private int speed = 5;
    //四个方向的图片
    private String upImg;
    private String leftImg;
    private String rightImg;
    private String downImg;
    //攻击冷却状态
    private boolean attackCoolDown = true;
    //攻击冷却时间毫秒间隔1000ms
    private int attackCoolTime = 500;


    public Tank(String img,int x,int y,GamePanel gamePanel
            ,String upImg,String leftImg,String rightImg,String downImg){
        super(img,x,y,gamePanel);
        this.upImg=upImg;
        this.leftImg=leftImg;
        this.downImg=downImg;
        this.rightImg=rightImg;
    }
    public void setImg(String img){
        this.img=Toolkit.getDefaultToolkit().getImage(img);
    }
    public void leftward() {
        direction = Direction.LEFT;
        setImg(leftImg);
        if(!hitWall(x-speed, y) && !moveToBorder(x-speed, y)){
            x-=speed;
        }
    }

    public void upward() {
        direction = Direction.UP;
        setImg(upImg);
        if(!hitWall(x,y-speed) && !moveToBorder(x,y-speed)){
            y-=speed;
        }
    }

    public void rightward() {
        setImg(rightImg);
        direction = Direction.RIGHT;
        if(!hitWall(x+speed,y) && !moveToBorder(x+speed,y)){
            x+=speed;
        }
    }
    public void downward() {
        setImg(downImg);
        direction = Direction.DOWN;
        if(!hitWall(x,y+speed) && !moveToBorder(x,y+speed)){
            y+=speed;
        }
    }
    public void attack(){
        if(attackCoolDown && !(alive == 0)){
            Point P = this.getHeadPoint();
            Bullet bullet = new Bullet("images/bullet/bulletGreen.gif",P.x,P.y,
                    this.gamePanel, direction);
            this.gamePanel.bulletList.add(bullet);
            //线程开始
            new AttackCD().start();
        }


    }
    // 新线程
    class AttackCD extends Thread{
        public void run(){
            //攻击功能设置为冷却状态
            attackCoolDown = false;
            //休眠一秒
            try{
                Thread.sleep(attackCoolTime);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //将攻击功能解除冷却状态
            attackCoolDown = true;
            //线程终止
            this.stop();
        }
    }
    public Point getHeadPoint(){
        switch (direction){
            case LEFT:
                return new Point(x,y+height/2);
            case RIGHT:
                return new Point(x+width,y+height/2);
            case UP:
                return new Point(x+width/2,y);
            case DOWN:
                return new Point(x+width/2,y+height);
            default:
                return null;

        }
    }
    //与围墙碰撞检测
    public boolean hitWall(int x,int y){
        //围墙列表
        ArrayList<Wall> walls = this.gamePanel.wallList;
        //写下一步矩形
        Rectangle next = new Rectangle(x,y,width,height);
        for(Wall wall: walls){
            //每个围墙进行碰撞检验
            if(next.intersects(wall.getRec())){
                return true;
            }
        }
        return false;
    }

    public boolean moveToBorder(int x,int y){
        if(x<0){
            return true;
        }
        else if(x>this.gamePanel.getWidth()-width){
            return true;
        }
        else if(y<0){
            return true;
        }
        else if (y>this.gamePanel.getHeight()-height) {
            return true;
        }
        return false;
    }

    @Override
    public abstract void paintSelf(Graphics g);
    @Override
    public abstract Rectangle getRec();

}

package com.sxt;

import java.awt.*;

public abstract  class Tank extends GameObject{
    //尺寸
    public int width = 40;
    public int height =50;
    //坦克的初始方向（向上）
    Direction direction = Direction.UP;
    private int speed = 3;
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
        x-=speed;
        setImg(leftImg);
        direction = Direction.LEFT;
    }

    public void upward() {
       y-=speed;
        setImg(upImg);
        direction = Direction.UP;
    }

    public void rightward() {
        x+=speed;
        setImg(rightImg);
        direction = Direction.RIGHT;
    }
    public void downward() {
        y+=speed;
        setImg(downImg);
        direction = Direction.DOWN;
    }
    public void attack(){
        if(attackCoolDown){
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


    @Override
    public abstract void paintSelf(Graphics g);
    @Override
    public abstract Rectangle getRec();

}

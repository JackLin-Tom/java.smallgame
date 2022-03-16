package com.sxt;

import com.sun.javafx.scene.traversal.Direction;

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

    public void uptward() {
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


    @Override
    public abstract void paintSelft(Graphics g);
    @Override
    public abstract Rectangle getRec();

}

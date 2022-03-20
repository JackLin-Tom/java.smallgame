package com.sxt;

import java.awt.*;

public class Bot extends Tank {


    public Bot(String img, int x, int y, GamePanel gamePanel,
               String upImg, String leftImg,
               String rightImg, String downImg) {
        super(img, x, y, gamePanel, upImg, leftImg, rightImg, downImg);
    }



    @Override
    public void paintSelft(Graphics g) {
        g.drawImage(img,x,y,null);


    }

    @Override
    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
    }
}

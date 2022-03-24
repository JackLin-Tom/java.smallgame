package com.sxt;

import java.awt.*;

public class BlastObj extends GameObject{
    //爆炸图集
    static Image[] imgs = new Image[8];
    int explodeCount = 0;

    static{
        for(int i = 0;i < 8;i++){
            imgs[i] = Toolkit.getDefaultToolkit().getImage(
                    "images/blast/blast"+(i + 1)+".gif");
        }
    }
    public BlastObj(String img, int x, int y, GamePanel gamePanel) {
        super(img, x, y, gamePanel);
    }


    @Override
    public void paintSelf(Graphics g) {
        if(explodeCount < 8){
            g.drawImage(imgs[explodeCount],x,y,null);
            explodeCount++;
        }
    }

    @Override
    public Rectangle getRec() {
        return null;
    }
}

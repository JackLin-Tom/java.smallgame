package com.sxt;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import com.sun.org.apache.xerces.internal.impl.xpath.XPath;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;


public class GamePanel extends JFrame {

    //定义双缓存图片
    Image offscreenImage = null;


    //窗口长度
    int width = 800;
    int height = 610;

    //指针图片
    Image select = Toolkit.getDefaultToolkit().getImage("images/selecttank.gif");
    //指针初始坐标
    int y = 150;
    //游戏模式 0 单人模式 1 双人模式 5 游戏胜利
    int state = 0;
    int a = 1;
    //敌方坦克重绘速度
    int count = 0;
    //已生成敌人数量
    int enemyCount = 0;
    //游戏元素列表
    ArrayList<Bullet>bulletList = new ArrayList<Bullet>();
    //敌方坦克列表
    ArrayList<Bot>botList = new ArrayList<Bot>();
    //删除子弹列表
    ArrayList<Bullet>removeList = new ArrayList<Bullet>();
    //玩家列表
    ArrayList<Tank>playerList = new ArrayList<Tank>();
    ArrayList<Wall>wallList = new ArrayList<Wall>();
    ArrayList<Base>baseList = new ArrayList<Base>();
    ArrayList<BlastObj>blastList = new ArrayList<BlastObj>();

    //PlayerOne
    PlayerOne playerOne = new PlayerOne("Images/player1/p1tankU.gif",125,510,this,
            "Images/player1/p1tankU.gif","Images/player1/p1tankL.gif",
            "Images/player1/p1tankR.gif","images/player1/p1tankD.gif");
    //PlayerTwo
    PlayerTwo playerTwo= new PlayerTwo("Images/player2/p2tankU.gif",625,510,this,
            "Images/player2/p2tankU.gif","Images/player2/p2tankL.gif",
            "Images/player2/p2tankR.gif","images/player2/p2tankD.gif");

    //Bot
    Bot bot = new Bot("images/enemy/enemy1U.gif",500,110,
            this,"images/enemy/enemy1U.gif","images/enemy/enemy1L.gif",
            "images/enemy/enemy1R.gif","images/enemy/enemy1D.gif");
    //基地
    Base base = new Base("images/star.gif",375,570,this);
    //窗口启动方法
    public void launch() {
        //标题
        setTitle("坦克大战");
        //窗口初始大小
        setSize(width, height);
        //屏幕居中
        setLocationRelativeTo(null);
        //添加关闭事件;
        setDefaultCloseOperation(3);
        //用户不能调整大小
        setResizable(true);
        //使窗口可见
        setVisible(true);
        //添加键盘监视器
        this.addKeyListener(new GamePanel.KeyMonitor());
        //添加围墙
        for(int i = 0; i< 14; i ++){
            wallList.add(new Wall("images/walls.gif", i*60,170,this ));
        }
        wallList.add(new Wall("images/walls.gif", 305 ,560,this ));
        wallList.add(new Wall("images/walls.gif", 305 ,500,this ));
        wallList.add(new Wall("images/walls.gif", 365 ,500,this ));
        wallList.add(new Wall("images/walls.gif", 425 ,500,this ));
        wallList.add(new Wall("images/walls.gif", 425 ,560,this ));
        baseList.add(base);
        //重绘
        while(true){
            //游戏胜利判定
            if(botList.size() == 0 && enemyCount == 10){
                state = 5;
            }

            //游戏失败判定
            if((playerList.size() == 0 && (state == 1 || state == 2)) || baseList.size() == 0 ){
                state = 4;
            }
            //添加电脑坦克
            if(count%100 == 1 && enemyCount < 10 && (state == 1 || state == 2)){
                Random random = new Random();
                int rnum = random.nextInt(800);
                botList.add(new Bot("images/enemy/enemy1U.gif",rnum,110,
                        this,"images/enemy/enemy1U.gif",
                        "images/enemy/enemy1L.gif",
                        "images/enemy/enemy1R.gif",
                        "images/enemy/enemy1D.gif"));
                enemyCount++;
            }
            repaint();
            try {
                //线程休眠 1秒 = 1000毫秒
                Thread.sleep(25);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    //paint（）方法
    @Override
    public void paint(Graphics g){
//        System.out.println(bulletList.size());
        //创建和容器一样大小的Image图片
        if(offscreenImage==null){
            offscreenImage = this.createImage(width,height);
        }
        //获得该图片的画笔
        Graphics gImage = offscreenImage.getGraphics();
        //设置背景颜色
        gImage.setColor(Color.gray);
        gImage.fillRect(0,0,width,height);
        //挂变画笔颜色
        gImage.setColor(Color.blue);
        gImage.setFont(new Font("仿宋",Font.BOLD,50));
        //state = 0 游戏开始；
        if(state == 0) {
            gImage.drawString("选择游戏模式", 200, 100);
            gImage.drawString("单人模式", 200, 200);
            gImage.drawString("双人模式", 200, 300);
            gImage.drawString("按1,2选择模式，按回车开始游戏",0,400);
            //绘制指针
            gImage.drawImage(select, 160, y, null);
        }
        else if (state == 1 || state == 2) {
            gImage.setFont(new Font("仿宋",Font.BOLD,30));
            gImage.setColor(Color.red);
            gImage.drawString("剩余敌人"+botList.size(),5,65);
            //添加游戏元素
            for(Tank player: playerList){
                player.paintSelf(gImage);
            }
            for(Bullet bullet: bulletList){
                bullet.paintSelf(gImage);
            }
            bulletList.removeAll(removeList);

//            bot.paintSelf(gImage);//绘制敌方坦克/*试绘坦克*/
            for(Bot bot:botList){
                bot.paintSelf(gImage);
            }

            for(Wall wall: wallList){
                wall.paintSelf(gImage);
            }
            for(Base base: baseList){
                base.paintSelf(gImage);
            }
            for(BlastObj blast: blastList){
                blast.paintSelf(gImage);
            }
            //重绘一次
            count++;
        }
        else if(state == 5){
            gImage.drawString("游戏胜利",220,200);
        }
        else if(state == 4){
            gImage.drawString("游戏失败",220,200);
        }
        else if(state == 3){
            gImage.drawString("游戏暂停",220,200);
        }
        /**将缓存区绘制好的图形整个绘制到容器的画布中**/
        g.drawImage(offscreenImage,0,0,null);
    }

    //键盘监视器
    class KeyMonitor extends KeyAdapter{
        //按下键盘
        @Override
        public void keyPressed(KeyEvent e){
            //返回键值
            System.out.println(e.getKeyChar());
            int key = e.getKeyCode();
            switch(key) {
                case KeyEvent.VK_NUMPAD1:
                    a = 1;
                    y = 150;
                    break;
                case KeyEvent.VK_NUMPAD2:
                    a = 2;
                    y = 250;
                    break;
                case KeyEvent.VK_ENTER:
                    state = a;
                    playerList.add(playerOne);
                    //PlayerTwo
                    if(state == 2){
                        playerList.add(playerTwo);
                        playerTwo.alive = 1;
                    }

                    playerOne.alive = 1;
                    break;
                case KeyEvent.VK_P:
                    if(state != 3){
                        a = state;
                        state =3;
                    }
                    else {
                        state = a;
                        if(a == 0){
                           a = 1;
                        }
                    }
                default:
                    playerOne.keyPressed(e);
                    playerTwo.keyPressed(e);
                    break;
            }
        }
        //键盘松开的方法
        @Override
        public void keyReleased(KeyEvent e){
            playerOne.keyReleased(e);
            playerTwo.keyReleased(e);
        }
    }






    //main
    public static void main(String[] args) {
        GamePanel gp = new GamePanel();
        gp.launch();
    }
}


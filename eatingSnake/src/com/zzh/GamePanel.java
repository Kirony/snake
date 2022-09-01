package com.zzh;

import javax.security.auth.kerberos.KerberosTicket;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener, ActionListener {

    int length;
    int[] snakeX = new int[600];
    int[] snakeY = new int[500];
    String fx;
    boolean isStart;
    boolean isFail;

    int score;

    int foodx;
    int foody;
    Random random = new Random();

    Timer timer = new Timer(100,this);

    public GamePanel() {
        init();
        this.setFocusable(true);
        this.addKeyListener(this);
    }

    public void init(){
        length = 3;
        snakeX[0] = 100; snakeY[0] = 100;
        snakeX[1] = 75; snakeY[1] = 100;
        snakeX[2] = 50; snakeY[2] = 100;
        fx = "R";
        isStart = false;
        isFail = false;
        timer.start();

        score = 0;

        foodx = 25+25*random.nextInt(34);
        foody = 75+25*random.nextInt(24);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.BLACK);
        Date.header.paintIcon(this,g,25,11);
        g.fillRect(25,75,850,600);

        g.setColor(Color.white);
        g.setFont(new Font("微软雅黑",Font.BOLD,18));
        g.drawString("长度"+length,750,35);
        g.drawString("分数"+score,750,50);

        Date.food.paintIcon(this,g,foodx,foody);

        if(fx.equals("R")){
            Date.right.paintIcon(this,g,snakeX[0],snakeY[0]);
        }else if(fx.equals("U")){
            Date.up.paintIcon(this,g,snakeX[0],snakeY[0]);
        }else if(fx.equals("L")){
            Date.left.paintIcon(this,g,snakeX[0],snakeY[0]);
        }else if(fx.equals("D")){
            Date.down.paintIcon(this,g,snakeX[0],snakeY[0]);
        }

        for (int i = 1; i < length; i++) {
            Date.body.paintIcon(this,g,snakeX[i],snakeY[i]);
        }

        if (!isStart){
            g.setColor(Color.white);
            g.setFont(new Font("微软雅黑",Font.BOLD,40));
            g.drawString("按下空格开始游戏",300,300);
        }

        if (isFail){
            g.setColor(Color.red);
            g.setFont(new Font("微软雅黑",Font.BOLD,40));
            g.drawString("胜败乃兵家常事，大侠请重新来过",150,300);
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE){
            if (isFail){
                isFail = false;
                init();
            }else {
                isStart = !isStart;
            }
            repaint();
        }
        if(keyCode==KeyEvent.VK_UP){
            fx = "U";
        }else if(keyCode==KeyEvent.VK_DOWN){
            fx = "D";
        }else if(keyCode==KeyEvent.VK_LEFT){
            fx = "L";
        }else if(keyCode==KeyEvent.VK_RIGHT){
            fx = "R";
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(isStart && isFail == false){

            if (snakeX[0] == foodx && snakeY[0] == foody){
                length++;
                score++;
                foodx = 25+25*random.nextInt(34);
                foody = 75+25*random.nextInt(24);
            }

            for (int i = length-1; i > 0 ; i--) {
                    snakeX[i] = snakeX[i-1];
                    snakeY[i] = snakeY[i-1];
            }

            if(fx=="U"){
                snakeY[0] = snakeY[0]-25;
                if(snakeY[0]<75){
                    snakeY[0] = 650;
                }
            }else if(fx=="D"){
                snakeY[0] = snakeY[0]+25;
                if(snakeY[0]>650){
                    snakeY[0] = 75;
                }
            }else if(fx=="L"){
                snakeX[0] = snakeX[0]-25;
                if(snakeX[0]<25){
                    snakeX[0] = 850;
                }
            }else if(fx=="R") {
                snakeX[0] = snakeX[0]+25;
                if(snakeX[0]>850){
                    snakeX[0] = 25;
                }
            }

            for (int i = 1; i < length; i++) {
                if (snakeY[0] == snakeY[i] && snakeX[0] == snakeX[i]) {
                    boolean flag = false;
                    for (int r = 2; r < length; r++) {
                        if (snakeX[1] == snakeX[r] && snakeY[1] == snakeY[r]) {
                            flag = true;//判断是否有重叠
                        }
                    }
                    if ((snakeY[0] == snakeY[i - 2] && snakeX[0] == snakeX[i - 2])||flag) {
                    } else {
                        isFail = true;
                    }
                }
            }

            repaint();
        }
        timer.start();
    }
}



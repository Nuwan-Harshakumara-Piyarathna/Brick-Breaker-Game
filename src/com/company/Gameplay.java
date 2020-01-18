package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//keyListener for detecting arrow keys
//actionlistener for moving the ball
public class Gameplay extends JPanel implements KeyListener, ActionListener {
    //    add properties
    private boolean play = false;//after game starts it should play.not before.
    private int score = 0;//starting score
    private int totalBricks = 21;

    //    setting a timer(how pass ball should move)
    private Timer timer;
    //    speed that give to timer
    private int delay = 8;

    //    x and y axis for slider and ball both
    private int playerX = 310;//starting position of slider

    private int ballposX = 120;//starting position for ball
    private int ballposY = 350;

    //    set the direction of the ball
    private int defaultXdir = -1;
    private int ballXdir = defaultXdir;
    private int ballYdir = -2;

    //create an object of MapGenerator class
    private MapGenerator map;

    public Gameplay() {
        map = new MapGenerator(3 , 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay , this);
        timer.start();
    }

//    creating graphics like slider , balls , tiles
    public void paint(Graphics g) {
//        background
        g.setColor(Color.black);
        g.fillRect(1 ,1,692,592);

//        drawing brick map
        map.draw((Graphics2D)g);

//        borders
        g.setColor(Color.yellow);
        g.fillRect(0,0,3,592); //left
        g.fillRect(0,0,692,3); //top
        g.fillRect(691,0,3,592); //right
//        No border for downside (moving ball downside will end the game)

//        colors
        g.setColor(Color.white);
        g.setFont(new Font("serif",Font.BOLD , 25));
        g.drawString(""+score,550,30);

//        paddle
        g.setColor(Color.green);
        g.fillRect(playerX ,550,100,8);

//        the ball
        g.setColor(Color.yellow);
        g.fillOval(ballposX ,ballposY,20,20);

//        winning the game
        if(totalBricks <= 0) {
            ballposY = -750;
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.green);
            g.setFont(new Font("serif",Font.BOLD , 30));
            g.drawString("Congratulations! you won!!!",150,300);

            //Restart game
            g.setFont(new Font("serif",Font.BOLD , 20));
            g.drawString("Press Enter to Restart",190,350);
        }

//        losing the game
        if(ballposY > 570) {
            ballposY = -750;
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD , 30));
            g.drawString("Game Over , Scores : "+score,150,300);

            //Restart game
            g.setFont(new Font("serif",Font.BOLD , 20));
            g.drawString("Press Enter to Restart",190,350);
        }

        g.dispose();//destroy and clean up JFrame window
    }


    @Override

    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (play) {
            if(new Rectangle(ballposX , ballposY ,20,20).intersects(new Rectangle(playerX,550,100,8))) {
                ballYdir = -ballYdir;
            }

            A:for(int i = 0;i < map.map.length ; i++) {
                for(int j = 0;j <map.map[0].length ; j++) {
                    if(map.map[i][j] == 1) {
                        int brickX = j * map.brickWidth + 74;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        //create a rectangle to detect brick
                        Rectangle rect = new Rectangle(brickX,brickY,brickWidth,brickHeight);
                        //create a rectangle to detect the ball
                        Rectangle ballRect = new Rectangle(ballposX,ballposY,20,20);
                        Rectangle brickRect = rect;

                        //check if ball and brick are intersect or not
                        if(ballRect.intersects(brickRect)) {
                            map.setBrickValue(0 , i , j);
                            totalBricks--;
                            score += 5;

                            if(ballposX + 19 <=brickRect.x || ballposX + 1 >= brickRect .x + brickRect.width) {
                                ballXdir = -ballXdir;//if it intersects from x direction
                            }
                            else{
                                ballYdir = -ballYdir;//else it must intersects from y direction
                            }

                            break A;//exit from for loops
                        }
                    }
                }
            }

            ballposX += ballXdir;
            ballposY += ballYdir;
            if(ballposX < 0) {
                ballXdir = -ballXdir;
            }
            if(ballposY < 0) {
                ballYdir = -ballYdir;
            }
            if(ballposX > 570) {
                ballXdir = -ballXdir;
            }
        }
        repaint(); //recall paint method and draw each and every method again
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            if (playerX >= 500) {
                playerX = 500;
            }
            else {
                moveRight(); //increment playerX
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_LEFT){
            if (playerX <= 10) {
                playerX = 10;
            }
            else {
                moveLeft(); //decrement playerX
            }
        }

        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(!play) {
                play = true;
                ballposX = 120;
                ballposY = 350;
                defaultXdir = -defaultXdir;
                ballXdir = defaultXdir;
                ballYdir = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(3 , 7);

                repaint();
            }
        }
    }

    public void moveRight() {
        play = true;
        playerX += 20;
    }

    public void moveLeft() {
        play = true;
        playerX -= 20;
    }


}

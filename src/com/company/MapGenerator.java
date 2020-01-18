package com.company;

import java.awt.*;

public class MapGenerator {
    public int map[][];
    public int brickWidth;
    public int brickHeight;

    public MapGenerator(int row , int col) {
        map = new int[row][col];
        for(int i = 0;i < map.length ; i++) {
            for(int j = 0;j < map[0].length ; j++) {
                map[i][j] = 1; //value 1 will tell that this particular brick is not intersected with the ball
                //if brick is not there value will be 0
            }
        }
        brickWidth = 450/col;
        brickHeight = 150/row;
    }

//    drawing bricks
    public void draw(Graphics2D g) {
        for(int i = 0;i < map.length ; i++) {
            for(int j = 0;j < map[0].length ; j++) {
                //check if brick is available or not
                if(map[i][j] == 1) {
                    g.setColor(Color.white);
                    g.fillRect(j * brickWidth + 74 , i * brickHeight + 50 , brickWidth , brickHeight);

                    //set a border to brick
                    g.setStroke(new BasicStroke(3));
                    //add color two border
                    g.setColor(Color.black);
                    g.drawRect(j * brickWidth + 74 , i * brickHeight + 50 , brickWidth , brickHeight);
                }
            }
        }
    }
    public void setBrickValue(int value , int row , int col ) {
        map[row][col] = value;
    }

}

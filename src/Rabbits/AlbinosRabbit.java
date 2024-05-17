package Rabbits;

import Controller.Habitat;

import javax.swing.*;
import java.awt.*;

public class AlbinosRabbit extends Rabbit {
    private boolean flag;
    public AlbinosRabbit(int x, int y, int timeBorn, int timeLife) {
        super(x, y, timeBorn, timeLife);
        this.flag = true;
    }
    @Override
    public void jump(int speed) {
        int widht = Habitat.getWidth();
        if(x == widht){
            flag = false;
        }else if(x == 0){
            flag = true;
        }
        if(flag){
            x += speed;
        }else{
            x -= speed;
        }
    }
    @Override
    public void draw(Graphics g) {
        Image image = new ImageIcon("черный кролик.jpg").getImage();
        g.drawImage(image,x,y,null);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "AlbinosRabbit{" +
                "x=" + x +
                ", y=" + y +
                ", timeBorn=" + timeBorn +
                ", timeLife=" + timeLife +
                '}';
    }
}

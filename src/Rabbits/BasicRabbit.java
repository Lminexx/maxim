package Rabbits;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class BasicRabbit extends Rabbit {
    private int direction;
    public BasicRabbit(int x, int y, int timeBorn, int timeLife) {
        super(x, y, timeBorn, timeLife);
        Timer timer = new Timer(2000, e -> {
            Random random = new Random();
            direction = random.nextInt(4);
        });
        timer.start();
    }
    @Override
    public void jump(int speed) {
        switch (direction){
            case 0 -> x+=speed;
            case 1 -> x-=speed;
            case 2 -> y+=speed;
            case 3 -> y-=speed;
        }

    }

    @Override
    public void draw(Graphics g) {
        Image image = new ImageIcon("белый кролик.jpg").getImage();
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
        return "BasicRabbit{" +
                "x=" + x +
                ", y=" + y +
                ", timeBorn=" + timeBorn +
                ", timeLife=" + timeLife +
                '}';
    }
}

package Rabbits;

import java.awt.*;
public abstract class Rabbit implements IBehaviour {
    protected int x;
    protected int y;
    public int timeBorn;
    public int timeLife;

    public Rabbit(int x, int y, int timeBorn, int timeLife) {
        this.x = x;
        this.y = y;
        this.timeBorn = timeBorn;
        this.timeLife = timeLife;
    }
    public abstract void draw(Graphics g);
}

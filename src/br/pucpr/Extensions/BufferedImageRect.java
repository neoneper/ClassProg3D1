package br.pucpr.Extensions;

import com.sun.javafx.geom.Vec2d;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by devsecond on 30/03/2018.
 */
public class BufferedImageRect {

    public BufferedImage image;
    private int x;
    private int y;
    private int widht;
    private int height;
    private int xOffset;
    private int widthOffset;
    private int yOffset;
    private int heightOffset;


    public BufferedImageRect(BufferedImage image) {
        this.image = image;
        widht = image.getWidth();
        height = image.getHeight();
        x = 0;
        y = 0;
        xOffset = 0;
        yOffset = 0;
        widthOffset = 0;
        heightOffset = 0;
    }

    public void setOffSet(int x, int y, int w, int h) {
        xOffset = x;
        yOffset = y;
        widthOffset = w;
        heightOffset = h;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setDefaultWidth() {
        this.widht = image.getWidth();
        this.height = image.getHeight();
    }

    public int getX() {
        return x + xOffset;
    }

    public int getY() {
        return y + yOffset;
    }

    public int getWidht() {
        return widht + widthOffset;
    }

    public int getHeight() {
        return height + heightOffset;
    }

    public int getBottom()
    {
        return getY() + getHeight();
    }
    public int getRight()
    {
        return getX() + getWidht();
    }
    public void drawGraphic2D(Graphics2D graphics2D) {
        graphics2D.drawRect(getX(), getY(), getWidht(), getHeight());

        int w = heightOffset;
    }

}

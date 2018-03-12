package br.pucpr.aula1;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SimpleBufferImage {

    private static BufferedImage img;

    public static  void main(String[] args)
    {
       img = new BufferedImage(100,100, BufferedImage.TYPE_INT_RGB);


       //Divido a quantidade maxima de cor por lina
       float lineGradient = 255.0f / img.getHeight();
       float currentColor = 0.0f;

        for(int y =0; y < img.getHeight(); y++)
        {
            for (int x = 0; x < img.getWidth(); x++) {
                Color green = new Color(0, (int)currentColor, 0);
                img.setRGB(x, y, green.getRGB());
            }

            currentColor += lineGradient;
        }

        try {
            ImageIO.write(img, "png", new File("verde.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

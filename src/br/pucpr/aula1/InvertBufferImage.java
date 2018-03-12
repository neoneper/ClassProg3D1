package br.pucpr.aula1;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class InvertBufferImage {


    private static BufferedImage out;
    private static  BufferedImage meninas;

    public static  void main(String[] args) throws IOException {

        meninas = ImageIO.read(new File("meninas.jpg"));
        out = new BufferedImage(meninas.getWidth(),meninas.getHeight(), BufferedImage.TYPE_INT_RGB);



        for(int y =0; y < out.getHeight(); y++)
        {
            for (int x = 0; x < out.getWidth(); x++) {
                Color color = new Color( meninas.getRGB(x,y));
                out.setRGB(x, y,new Color(color.getBlue(),color.getGreen(),color.getRed()).getRGB());
            }
        }


            ImageIO.write(out, "jpg", new File("meninasInvert.png"));

    }
}

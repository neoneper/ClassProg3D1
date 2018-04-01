package br.pucpr.tde1;


import br.pucpr.Extensions.ColorExtensions;
import br.pucpr.Extensions.SimplexNoise;
import br.pucpr.Extensions.Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by devsecond on 31/03/2018.
 * PRIMEIROS TESteS COM O PErLIN NOISe.
 * Proximas atualizaões vao conter
 * > Randomização de mapa
 * > Gerador de Imagem automatico
 * > Utilização de Texturas
 * > Blend de Texturas
 */
public class TDE1_PerlinNoise {

    public static void main(String[] args) throws IOException {

        SimplexNoise simplexNoise = new SimplexNoise();

        float[][] data = simplexNoise.generateOctavedSimplexNoise(512, 512, 3, 0.1f, 150);

        BufferedImage image = new BufferedImage(data.length, data[0].length, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {

                int datavalue = (int) (data[x][y] * 255);

                Color color = Color.white;
                String epa = "";
                if (datavalue > 0) {


                    if (datavalue < 100)
                        color = ColorExtensions.HexToColor("#E5D152");
                    else if (datavalue < 200)
                        color = ColorExtensions.HexToColor("#2FB874");
                    else
                        color = ColorExtensions.HexToColor("#71B72F");

                } else {
                    if (datavalue == 0) {
                        color = ColorExtensions.HexToColor("#8CD3EA");
                        epa = "1";

                    } else if (Math.abs(datavalue) > 0 && Math.abs(datavalue) <= 80) {
                        color = ColorExtensions.HexToColor("#7BB9CE");
                        epa = "2";
                    } else if (Math.abs(datavalue) > 0) {
                        color = ColorExtensions.HexToColor("#4E7582");
                        epa = "3";
                    }
                }


                image.setRGB(x, y, color.getRGB());

            }
        }

        ImageIO.write(image, "jpg", new File("simplesNoise.jpg"));
    }

}

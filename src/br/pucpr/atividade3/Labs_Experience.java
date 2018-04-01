package br.pucpr.atividade3;


import br.pucpr.Extensions.BufferedImageEqualizeType;
import br.pucpr.Extensions.BufferedImageOperation;

import java.awt.image.BufferedImage;
import java.io.IOException;


public class Labs_Experience {


    public static void main(String[] args) throws IOException {

        //Inicializando objeto que me permite trabalhar com convolução, histogramas e efeitos
        BufferedImageOperation imageOperation = new BufferedImageOperation();

        //Carregando imagens para equalizar
        BufferedImage image = imageOperation.LoadImage("car-wallpapers19.jpg");

        //Equalizando e gerando novas imagens equalizadas
        BufferedImage out_image = imageOperation.Equalize(image, BufferedImageEqualizeType.BRIGHTNESS);
        BufferedImage out_image_sat = imageOperation.Equalize(image, BufferedImageEqualizeType.SATURATION);
        //Experiência equalizando HSV e RGB
        BufferedImage out_image_rgb = imageOperation.EqualizeRGB(image);
        BufferedImage out_image_hsv = imageOperation.EqualizeHSV(image);

        //Salvando imagens equalizadas
        imageOperation.SaveImage(out_image,"jpg","out_brit.jpg");
        imageOperation.SaveImage(out_image_sat,"jpg","out_sat.jpg");
        imageOperation.SaveImage(out_image_rgb,"jpg","out_rgb.jpg");
        imageOperation.SaveImage(out_image_hsv,"jpg","out_hsv.jpg");
    }

}

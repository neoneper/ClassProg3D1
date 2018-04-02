package br.pucpr.atividade3;


import br.pucpr.Extensions.BufferedImageEqualizeType;
import br.pucpr.Extensions.BufferedImageOperation;

import java.awt.image.BufferedImage;
import java.io.IOException;

/*Crie duas funções, que trabalham em imagens
coloridas usando espaço de cor HSV:
 BufferedImage equalizeSaturation(BufferedImage img)
 BufferedImage equalizeBrightness(BufferedImage img)
 Aplique-as sobre a imagem lara.png, disponível na
pasta cor.
*/
public class Desafio4_ColorHistograma {


    public static void main(String[] args) throws IOException {

        //Inicializando objeto que me permite trabalhar com convolução, histogramas e efeitos
        BufferedImageOperation imageOperation = new BufferedImageOperation();

        //Carregando imagens para equalizar
        BufferedImage lara = imageOperation.LoadImage("lara.png");

        //Equalizando e gerando novas imagens equalizadas
        BufferedImage out_lara_brit = imageOperation.Equalize(lara, BufferedImageEqualizeType.BRIGHTNESS);
        BufferedImage out_lara_sat = imageOperation.Equalize(lara, BufferedImageEqualizeType.SATURATION);
        //Experiência equalizando HSV e RGB
        BufferedImage out_lara_rgb = imageOperation.EqualizeRGB(lara);
        BufferedImage out_lara_hsv = imageOperation.EqualizeHSV(lara);
        BufferedImage out_lara_sv = imageOperation.EqualizeSV(lara);

        //Salvando imagens equalizadas
        imageOperation.SaveImage(out_lara_brit,"jpg","out_lara_brit.jpg");
        imageOperation.SaveImage(out_lara_sat,"jpg","out_lara_sat.jpg");
        imageOperation.SaveImage(out_lara_rgb,"jpg","out_lara_rgb.jpg");
        imageOperation.SaveImage(out_lara_hsv,"jpg","out_lara_hsv.jpg");
        imageOperation.SaveImage(out_lara_sv,"jpg","out_lara_sv2.jpg");
    }

}

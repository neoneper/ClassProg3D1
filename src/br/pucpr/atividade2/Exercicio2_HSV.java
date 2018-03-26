package br.pucpr.atividade2;

import br.pucpr.Extensions.BufferedImageOperation;
import br.pucpr.Extensions.BufferedImageOperationType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * Created by devsecond on 25/03/2018.
 Utilize o HSV e faça alguns testes como:
  Diminuir e aumentar o brilho de uma imagem
  Diminuir e aumentar a saturação de uma imagem
  Alterar o matiz de uma imagem em alguns graus
 */
public class Exercicio2_HSV {
    public static void main(String[] args) throws IOException {
        //Inicializando objeto que me permite trabalhar com convolução e efeitos visuais na imagem origem
        BufferedImageOperation imageOperation = new BufferedImageOperation();
        //Carregando imagem para manipulação
        BufferedImage inImage = ImageIO.read(new File("puppy.jpg"));
        //Criando imagem com modificação hsv
        BufferedImage outImage = imageOperation.HSV(inImage,0.2f,0.3f,0.3f);

        //Salvando nova Imagem
        ImageIO.write(outImage, "jpg", new File("puppy_hsv.jpg"));
    }
}

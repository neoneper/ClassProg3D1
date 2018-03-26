package br.pucpr.atividade2;

import br.pucpr.Extensions.BufferedImageOperation;
import br.pucpr.Extensions.BufferedImageOperationType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by devsecond on 25/03/2018.
Deteccao de bordas Prewitt
 */
public class Exercicio1_Prewitt {

    public static void main(String[] args) throws IOException
    {
        //Inicializando objeto que me permite trabalhar com convolução e efeitos visuais na imagem origem
        BufferedImageOperation imageOperation = new BufferedImageOperation();
        //Carregando imagem para manipulação
        BufferedImage inImage = ImageIO.read(new File("metroid.jpg"));
        //Criando imagem com kernel de borda prewitt
        BufferedImage outImage = imageOperation.Convolve(inImage, BufferedImageOperationType.PREWITT);

        //Salvando nova Imagem
        ImageIO.write(outImage, "jpg", new File("metroid_prewitt.jpg"));
    }
}

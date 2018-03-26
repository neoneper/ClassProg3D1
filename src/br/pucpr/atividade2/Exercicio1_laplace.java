package br.pucpr.atividade2;

import br.pucpr.Extensions.BufferedImageOperation;
import br.pucpr.Extensions.BufferedImageOperationType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by devsecond on 25/03/2018.
 Utilize o decaimento como forma de tratar as bordas;
 */
public class Exercicio1_laplace {

    public static void main(String[] args) throws IOException
    {
        //Inicializando objeto que me permite trabalhar com convolução e efeitos visuais na imagem origem
        BufferedImageOperation imageOperation = new BufferedImageOperation();
        //Carregando imagem para manipulação
        BufferedImage inImage = ImageIO.read(new File("metroid.jpg"));
        //Criando imagem com kernel de borda laplace
        BufferedImage outImage = imageOperation.Convolve(inImage, BufferedImageOperationType.LAPLACE_DIAGONAL);

        //Salvando nova Imagem
        ImageIO.write(outImage, "jpg", new File("metroid_laplace.jpg"));
    }
}

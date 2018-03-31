package br.pucpr.atividade3;

import br.pucpr.Extensions.BufferedImageEqualizeType;
import br.pucpr.Extensions.BufferedImageOperation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by devsecond on 31/03/2018.
 * Crie a função
 BufferedImage drawHistogram(int[] histogram)
 que receba o histograma e desenhe-o em imagem
 de 512x600.
  Utilize o método createGraphics() da imagem para obter
 um objeto do tipo Graphics. Esse objeto possui métodos
 de desenho, como drawLine.

 */
public class Exercicio2_histogramGraphic
{

    public static void main(String[] args) throws IOException {

        //Inicializando objeto que me permite trabalhar com convolução, histogramas e efeitos
        BufferedImageOperation imageOperation = new BufferedImageOperation();
        //Carregando imagem em tons de cinza para ser equalizada
        BufferedImage inImage =imageOperation.LoadImage("montanha.jpg");
        //Gerando nova imagem em tons de cinza ja Equalizada
        BufferedImage outImage = imageOperation.Equalize(inImage, BufferedImageEqualizeType.GRAYSCALE);

        //Gerando 2 Graficos com o histograma da imagem antes da equalização e depois da equalização
        //Histogramas antes e depois
        int[] inHistogram = imageOperation.GetGrayHistogram(inImage);
        int[] outHistogram = imageOperation.GetGrayHistogram(outImage);
        //Imagem contendo grafico do antes e depois
        BufferedImage graphics1 = imageOperation.HistogramGraphic2D(inHistogram,512,600,6);
        BufferedImage graphics2 = imageOperation.HistogramGraphic2D(outHistogram,512,600,6);

        //Salvando imagens
        ImageIO.write(graphics1, "jpg", new File("graphyc1.jpg"));
        ImageIO.write(graphics2, "jpg", new File("graphyc2.jpg"));


    }
}

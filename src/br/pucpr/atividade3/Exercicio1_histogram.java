package br.pucpr.atividade3;

import br.pucpr.Extensions.BufferedImageEqualizeType;
import br.pucpr.Extensions.BufferedImageOperation;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by devsecond on 31/03/2018.
 * Considerando apenas imagens em tons de cinza:
 a) Crie a função  int[] histogram(BufferedImage img), que retorne o array do histograma da imagem.
 b) Crie a função int[] accumHistogram(int[] histogram), que retorne o array do histograma acumulado da
 imagem
 */
public class Exercicio1_histogram
{
    public static void main(String[] args) throws IOException {
        //Inicializando objeto que me permite trabalhar com convolução, histogramas e efeitos
        BufferedImageOperation imageOperation = new BufferedImageOperation();
        //Carregando imagem para gerar seu histograma
        BufferedImage inImage = imageOperation.LoadImage("montanha.jpg");

        //Gera histograma quantitativo da imagem em tons de cinza
        int[] histogram = imageOperation.GetHistogram(inImage, BufferedImageEqualizeType.GRAYSCALE);
        //Gera histograma acumulativo da imagem em tons de cinza
        int[] histogramAcc = imageOperation.GetHistogramAcc(inImage, BufferedImageEqualizeType.GRAYSCALE);
        //Gera histograma normalizado do histograma acumulativo (0,255)
        int[] histogramNormalized = imageOperation.GetHistogramEqualized(inImage, BufferedImageEqualizeType.GRAYSCALE);

        BufferedImage histoNormal = imageOperation.HistogramGraphic2D(histogramNormalized,512,600,6);
        imageOperation.SaveImage(histoNormal,"jpg","graphicNormalized.jpg");
    }

}



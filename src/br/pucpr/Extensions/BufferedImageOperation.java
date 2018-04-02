package br.pucpr.Extensions;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * Created by devsecond on 24/03/2018.
 * Esta classe permite trabalhar com diverças operações de manipulação de imagens e efeitos visuais.
 * Contem metodos uteis para trabalhar com Convolução de Imagens utilizando Filtros e Kernel bem como
 * para trabalhar com manipulação de efeitos Visuais e palleta de cores
 */
public class BufferedImageOperation {

    /**
     * Convolução da origem para o destino.
     * Convolução usando um kernel onde calcula-se o pixel de saída de um pixel de entrada multiplicando o kernel
     * com os valores de modificação para o pixel de entrada. Isso permite que o pixel de saída seja afetado pela vizinhança
     * do dos pixels cujo a origem é o pixel baseado no kernel central.
     * Essa classe opera com dados de BufferedImage.
     *
     * @param img   Imagem Origem
     * @param otype Um Kernel automático para gerar o pixel de saida.
     * @return Imagem modificada pela Convolução do Kernel
     */
    public BufferedImage Convolve(BufferedImage img, BufferedImageOperationType otype) {

        BufferedImage filteredImage = null;

        //Imagens temporarios serao usadas para os filtros que necessitam de combinacao de resultados
        //tais como SOBEL, PREWITT
        BufferedImage tmpImage1 = null;
        BufferedImage tmpImage2 = null;

        switch (otype) {
            case BOXBLUR:
                filteredImage = Convolve(img, BufferedImageOperationKernel.Kernel_BoxBlur);
                break;
            case CROSBLUR:
                filteredImage = Convolve(img, BufferedImageOperationKernel.Kernel_CrossBlur);
                break;
            case GAUSIANBLUR:
                filteredImage = Convolve(img, BufferedImageOperationKernel.Kernel_GausianBlur);
                break;
            case SHARPBLUR:
                filteredImage = Convolve(img, BufferedImageOperationKernel.Kernel_SharpBlur);
                break;
            case SOBEL:
                tmpImage1 = Convolve(img, BufferedImageOperationKernel.Kernel_Sobelx);
                tmpImage2 = Convolve(img, BufferedImageOperationKernel.Kernel_Sobely);
                filteredImage = CombineToBorder(tmpImage1, tmpImage2);
                break;
            case PREWITT:
                tmpImage1 = Convolve(img, BufferedImageOperationKernel.Kernel_Prewittx);
                tmpImage2 = Convolve(img, BufferedImageOperationKernel.Kernel_Prewitty);
                filteredImage = CombineToBorder(tmpImage1, tmpImage2);
                break;
            case LAPLACE:
                filteredImage = Convolve(img, BufferedImageOperationKernel.Kernel_Laplace);
                break;
            case LAPLACE_DIAGONAL:
                filteredImage = Convolve(img, BufferedImageOperationKernel.Kernel_LaplaceDiagonal);
                break;
            case SHARPEN:
                filteredImage = Convolve(img, BufferedImageOperationKernel.Kernel_Sharpen);
                break;
            case ENBOSS_OUT:
                filteredImage = Convolve(img, BufferedImageOperationKernel.Kernel_EnbosOut);
                break;
            case ENBOSS_INNER:
                filteredImage = Convolve(img, BufferedImageOperationKernel.Kernel_EnbosInner);
                break;
            case ENBOSS_CENTER:
                filteredImage = Convolve(img, BufferedImageOperationKernel.Kernel_EnbosCenter);
                break;
        }

        return filteredImage;
    }

    /**
     * Convolução da origem para o destino.
     * Convolução usando um kernel onde calcula-se o pixel de saída de um pixel de entrada multiplicando o kernel
     * com os valores de modificação para o pixel de entrada. Isso permite que o pixel de saída seja afetado pela vizinhança
     * do dos pixels cujo a origem é o pixel baseado no kernel central.
     * Essa classe opera com dados de BufferedImage.
     *
     * @param img    Imagem Origem
     * @param kernel Array 2D Contendo os valores manuais de multiplicação Kernel para gerar o pixel de saida.
     * @return Imagem modificada pela Convolução do Kernel
     * @see BufferedImageOperationType como parametro para subistituir um kernel manual por um automático gerado pelo sistema
     */
    public BufferedImage Convolve(BufferedImage img, float[][] kernel) {

        BufferedImage filteredImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        int height = img.getHeight();
        int width = img.getWidth();

        //Vou utilzizar o tamanho do kernel para limitar a iteração de pixels da IMAGEM para dentro
        //do Bounding. Ou seja, pretendo navegar apenas nas bordas da imagem, para evitar pixel nullo.
        //Assim sendo eu estarei sempre a metade do tamanho do meu kernel para dentro do bounding da imagem.
        //Esta diferença de pixel que não serao iterados aqui, serão levados em consideracao na Iteracao do Kernel
        //no Metodo GetKernelPixelColor.
        int kernel_height = kernel[0].length;
        int kernel_width = kernel[1].length;

        //Iterando os Pixels da imagem.
        for (int y = kernel_height / 2; y < (height - kernel_height / 2); y++) {
            for (int x = kernel_width / 2; x < (width - kernel_width / 2); x++) {
                int kernelPixelRGB = GetKernelPixelColor(img, x, y, kernel);
                filteredImage.setRGB(x, y, kernelPixelRGB);
            }
        }

        return filteredImage;
    }

    /**
     * Pixaliza imagem de origem utilizando um tamanho de pixel especificado.
     *
     * @param img  Imagem de origem
     * @param size Tamanho dos Pixels a serem gerados na nova imagem
     * @return Retorna imagem pixelada
     */
    public BufferedImage Pixelate(BufferedImage img, int size) {

        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                int px = (x / size) * size;
                int py = (y / size) * size;
                out.setRGB(x, y, img.getRGB(px, py));
            }
        }
        return out;
    }

    /**
     * Modifica os valores de Hue255, Saturation e Brightness255 da Imagem. Os valores de HSV podem variar de
     * -1 a 1 sendo o valor zero o atual HSV da imagem.
     *
     * @param img        Imagem origem
     * @param hue        HUE -1 a 1 sendo 0 o Hue255 atual da imagem
     * @param saturation Saturação -1 a 1 sendo 0 a saturação atual da imagem
     * @param brightness Brilho -1 a 1 sendo 0 o Brilho atual da imagem
     * @return Retorna imagem com os valores HSV modificados.
     */
    public BufferedImage HSV(BufferedImage img, float hue, float saturation, float brightness) {

        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                Color pixelColor = new Color(img.getRGB(x, y));
                Color modfyColor = ColorExtensions.ColorHSB(pixelColor, hue, saturation, brightness);
                out.setRGB(x, y, modfyColor.getRGB());
            }
        }
        return out;
    }

    /**
     * Modifica os valores de Hue255 da Imagem. Os valores de HUE podem variar de
     * -1 a 1 sendo o valor zero o atual HUE da imagem.
     *
     * @param img imagem de origem
     * @param hue HUE -1 a 1 sendo 0 o Hue255 atual da imagem
     * @return Imagem com Hue255 Modificado
     */
    public BufferedImage HUE(BufferedImage img, float hue) {

        return HSV(img, hue, 0, 0);
    }

    /**
     * Modifica os valores de Saturação da Imagem. Os valores de Saturação podem variar de
     * -1 a 1 sendo o valor zero o atual Saturaçao da imagem.
     *
     * @param img        imagem de origem
     * @param saturation Saturaçao -1 a 1 sendo 0 o Saturaçao atual da imagem
     * @return Imagem com Saturaçao Modificado
     */
    public BufferedImage SATURATION(BufferedImage img, float saturation) {

        return HSV(img, 0, saturation, 0);
    }

    /**
     * Modifica os valores de brightness da Imagem. Os valores de brightness podem variar de
     * -1 a 1 sendo o valor zero o atual brightness da imagem.
     *
     * @param img        imagem de origem
     * @param brightness brightness -1 a 1 sendo 0 o Hue255 atual da imagem
     * @return Imagem com brightness Modificado
     */
    public BufferedImage BRIGHTNESS(BufferedImage img, float brightness) {

        return HSV(img, 0, 0, brightness);
    }


    /**
     * Utilize este metodo para modificar a palleta de caores da imagem original por uma pre-estabelecidada.
     * Tenha em mente que a subistituição dos pixels originais da imagem serão feitas mediante a cor mais proxima
     * da palleta.
     *
     * @param image   Imagem origem
     * @param pallete paleta de cores Hexadecimais
     * @return Imagem com pixels subistituidos por cores da paleta.
     */
    public BufferedImage ChangeColorPallet(BufferedImage image, int[] pallete) {

        BufferedImage changedImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int y = 0; y < changedImage.getHeight(); y++) {
            for (int x = 0; x < changedImage.getWidth(); x++) {
                Color colorFrom = new Color(image.getRGB(x, y));
                Color colorToChange = ColorExtensions.ColorNear(colorFrom, pallete);
                changedImage.setRGB(x, y, colorToChange.getRGB());
            }
        }

        return changedImage;
    }

    /*
    * Retorna a cor modificada por um kernel dos pixel nas coordenadas especificadas da imagem de origem
    * */
    private int GetKernelPixelColor(BufferedImage image, int x, int y, float[][] kernel) {
        int height = image.getHeight();
        int width = image.getWidth();

        int kernel_height = kernel[0].length;
        int kernel_width = kernel[1].length;

        //Centro do kernel, so funciona para kerneis IMPAR e Quadrado. (3x3), (5,5), (7,7)...
        //Ex: k[3] = [0,(Center 1),2]
        int kernel_centerX = kernel_width / 2;
        int kernel_centerY = kernel_height / 2;

        int r = 0;
        int g = 0;
        int b = 0;

        for (int ky = 0; ky < kernel_height; ky++) {
            for (int kx = 0; kx < kernel_width; kx++) {
                //Indice do Kernel no array de pixels da imagem
                int pixel_x = x + kx - kernel_centerX;
                int pixel_y = y + ky - kernel_centerY;

                //Capturando PIXEL que sera tratado pelo kernel e aplicando
                //as modificações de RGB.
                Color color = new Color(image.getRGB(pixel_x, pixel_y));
                r += color.getRed() * kernel[kx][ky];
                g += color.getGreen() * kernel[kx][ky];
                b += color.getBlue() * kernel[kx][ky];

            }
        }

        return ColorExtensions.RGBHexMinMax(r, g, b);
    }

    /*
    * Combina 2 Imagens tulizando a formula de SOBEL e PREWITT para gerar bordas.
    * */
    private BufferedImage CombineToBorder(BufferedImage img1, BufferedImage img2) {
        int height = img1.getHeight();
        int width = img1.getWidth();

        BufferedImage combinedImage = new BufferedImage(width, height, img1.getType());

        //Iterando os Pixels da imagem.
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color pixelColor_img1 = new Color(img1.getRGB(x, y));
                Color pixelColor_img2 = new Color(img2.getRGB(x, y));

                //Aplicando formula SOBEL ou Prewit de combinação de pixel
                Color combinedColor = ColorExtensions.CombineSobel(pixelColor_img1, pixelColor_img2);
                //Aplicando formula de saturação para garantir que a cor nao tenha valores irregulares <0 > 255
                combinedColor = ColorExtensions.ColorMinMax(combinedColor);
                //Setndo Cor modificada na Imagem resultante
                combinedImage.setRGB(x, y, combinedColor.getRGB());
            }
        }

        return combinedImage;
    }

    /**
     * Gera uma lista de 256 tons com quantificação de tons de cinza,
     * contendo quantas vezes determinado tom na escala 255 se repete.
     *
     * @param image Imagem a ser gerado o histograma
     * @return Lista quantificada onde cada elemento do array é o tom e o valor é a quantidade
     * de vezes que este tom se repete
     */
    public int[] GetGrayHistogram(BufferedImage image) {
        int[] accTone = new int[256];

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixelGrayTone = ColorExtensions.GetGrayTone(image.getRGB(x, y));
                //Quantificando na escala de cores 255 quantas vezes este TON se repete
                accTone[pixelGrayTone] += 1;
            }
        }

        return accTone;
    }

    /**
     * Gera uma lista de 255 tons com quantificação de tons de Vermelho,
     * contendo quantas vezes determinado tom na escala 255 se repete.
     *
     * @param image Imagem a ser gerado o histograma
     * @return Lista quantificada onde cada elemento do array é o tom e o valor é a quantidade
     * de vezes que este tom se repete
     */
    public int[] GetRedHistogram(BufferedImage image) {
        int[] accTone = new int[256];

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixelTone = ColorExtensions.Red(image.getRGB(x, y));
                //Quantificando na escala de cores 255 quantas vezes este TON se repete
                accTone[pixelTone] += 1;
            }
        }

        return accTone;
    }

    /**
     * Gera uma lista de 256 tons com quantificação de tons de Verde,
     * contendo quantas vezes determinado tom na escala 255 se repete.
     *
     * @param image Imagem a ser gerado o histograma
     * @return Lista quantificada onde cada elemento do array é o tom e o valor é a quantidade
     * de vezes que este tom se repete
     */
    public int[] GetGreenHistogram(BufferedImage image) {
        int[] accTone = new int[256];

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixelTone = ColorExtensions.Green(image.getRGB(x, y));
                //Quantificando na escala de cores 255 quantas vezes este TON se repete
                accTone[pixelTone] += 1;
            }
        }

        return accTone;
    }

    /**
     * Gera uma lista de 256 tons com quantificação de tons de Azul,
     * contendo quantas vezes determinado tom na escala 255 se repete.
     *
     * @param image Imagem a ser gerado o histograma
     * @return Lista quantificada onde cada elemento do array é o tom e o valor é a quantidade
     * de vezes que este tom se repete
     */
    public int[] GetBlueHistogram(BufferedImage image) {
        int[] accTone = new int[256];

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixelTone = ColorExtensions.Blue(image.getRGB(x, y));
                //Quantificando na escala de cores 255 quantas vezes este TON se repete
                accTone[pixelTone] += 1;
            }
        }

        return accTone;
    }

    /**
     * Gera uma lista de 256 tons com quantificação de tons de BRILHO,
     * contendo quantas vezes determinado tom na escala 255 se repete.
     *
     * @param image Imagem a ser gerado o histograma
     * @return Lista quantificada onde cada elemento do array é o tom e o valor é a quantidade
     * de vezes que este tom se repete
     */
    public int[] GetBrightnessHistogram(BufferedImage image) {
        int[] accTone = new int[256];

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixelTone = ColorExtensions.Brightness255(image.getRGB(x, y));
                //Quantificando na escala de cores 255 quantas vezes este TON se repete
                accTone[pixelTone] += 1;
            }
        }

        return accTone;
    }

    /**
     * Gera uma lista de 256 tons com quantificação de tons de Saturação,
     * contendo quantas vezes determinado tom na escala 255 se repete.
     *
     * @param image Imagem a ser gerado o histograma
     * @return Lista quantificada onde cada elemento do array é o tom e o valor é a quantidade
     * de vezes que este tom se repete
     */
    public int[] GetSaturationHistogram(BufferedImage image) {
        int[] accTone = new int[256];

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixelTone = ColorExtensions.Saturations255(image.getRGB(x, y));
                //Quantificando na escala de cores 255 quantas vezes este TON se repete
                accTone[pixelTone] += 1;
            }
        }

        return accTone;
    }

    /**
     * Gera uma lista de 256 tons com quantificação de tons de HUE,
     * contendo quantas vezes determinado tom na escala 255 se repete.
     *
     * @param image Imagem a ser gerado o histograma
     * @return Lista quantificada onde cada elemento do array é o tom e o valor é a quantidade
     * de vezes que este tom se repete
     */
    public int[] GetHueHistogram(BufferedImage image) {
        int[] accTone = new int[256];

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixelTone = ColorExtensions.Hue255(image.getRGB(x, y));
                //Quantificando na escala de cores 255 quantas vezes este TON se repete
                accTone[pixelTone] += 1;
            }
        }

        return accTone;
    }

    /**
     * Gera uma lista de 256 tons com quantificação e Acumulação dos tons de Cinza,
     * contendo a acumulação de determinado tom na escala 255
     *
     * @param image imagem origem
     * @return Array de 255 elementos contendo o acumulo dos 255 tons de cinza da imagem origem
     */
    public int[] GetGrayHistogramAcc(BufferedImage image) {
        int[] grayHistogram = GetGrayHistogram(image);
        int[] grayHistogramAcc = GetHistogramAcc(grayHistogram);

        return grayHistogramAcc;
    }

    /**
     * Gera uma lista de 256 tons com quantificação e Acumulação dos tons de Vermelho,
     * contendo a acumulação de determinado tom na escala 255
     *
     * @param image imagem origem
     * @return Array de 255 elementos contendo o acumulo dos 255 tons de Vermelho da imagem origem
     */
    public int[] GetRedHistogramAcc(BufferedImage image) {
        int[] histogram = GetRedHistogram(image);
        int[] histogramAcc = GetHistogramAcc(histogram);

        return histogram;
    }

    /**
     * Gera uma lista de 256 tons com quantificação e Acumulação dos tons de Verde,
     * contendo a acumulação de determinado tom na escala 255
     *
     * @param image imagem origem
     * @return Array de 255 elementos contendo o acumulo dos 255 tons de Verde da imagem origem
     */
    public int[] GetGreenHistogramAcc(BufferedImage image) {
        int[] histogram = GetGreenHistogram(image);
        int[] histogramAcc = GetHistogramAcc(histogram);

        return histogram;
    }

    /**
     * Gera uma lista de 256 tons com quantificação e Acumulação dos tons de Azul,
     * contendo a acumulação de determinado tom na escala 255
     *
     * @param image imagem origem
     * @return Array de 255 elementos contendo o acumulo dos 255 tons de Azul da imagem origem
     */
    public int[] GetBlueHistogramAcc(BufferedImage image) {
        int[] histogram = GetBlueHistogram(image);
        int[] histogramAcc = GetHistogramAcc(histogram);

        return histogram;
    }

    /**
     * Gera uma lista de 256 tons com quantificação e Acumulação dos tons de Brilho,
     * contendo a acumulação de determinado tom na escala 255
     *
     * @param image imagem origem
     * @return Array de 255 elementos contendo o acumulo dos 255 tons de Brilho da imagem origem
     */
    public int[] GetBrightnessHistogramAcc(BufferedImage image) {
        int[] histogram = GetBrightnessHistogram(image);
        int[] histogramAcc = GetHistogramAcc(histogram);

        return histogram;
    }

    /**
     * Gera uma lista de 256 tons com quantificação e Acumulação dos tons de Saturação,
     * contendo a acumulação de determinado tom na escala 255
     *
     * @param image imagem origem
     * @return Array de 255 elementos contendo o acumulo dos 255 tons de Saturação da imagem origem
     */
    public int[] GetSaturationHistogramAcc(BufferedImage image) {
        int[] histogram = GetSaturationHistogram(image);
        int[] histogramAcc = GetHistogramAcc(histogram);

        return histogram;
    }

    /**
     * Gera uma lista de 256 tons com quantificação e Acumulação dos tons de HUE,
     * contendo a acumulação de determinado tom na escala 255
     *
     * @param image imagem origem
     * @return Array de 255 elementos contendo o acumulo dos 255 tons de HUE da imagem origem
     */
    public int[] GetHueHistogramAcc(BufferedImage image) {
        int[] histogram = GetHueHistogram(image);
        int[] histogramAcc = GetHistogramAcc(histogram);

        return histogram;
    }

    /**
     * Gera uma lista de tons de cinza acumulados e equalizados da escala de 256 da imagem origem.
     *
     * @param image imagem origem
     * @return Array com 255 elementos representando os 255 tons da cor e a quantidade equalizada referente
     * a cada tom de cinza.
     */
    public int[] GetGrayHistogramEqualized(BufferedImage image) {
        int totalPixel = GetTotalPixel(image);
        int[] histogram = GetGrayHistogram(image);

        int[] equalized = GetEqualizedHistogram(histogram, totalPixel);

        return equalized;
    }

    public int[] GetRedHistogramEqualized(BufferedImage image) {
        int totalPixel = GetTotalPixel(image);
        int[] histogram = GetRedHistogram(image);
        int[] equalized = GetEqualizedHistogram(histogram, totalPixel);

        return equalized;
    }

    public int[] GetGreenHistogramEqualized(BufferedImage image) {
        int totalPixel = GetTotalPixel(image);
        int[] histogram = GetGreenHistogram(image);
        int[] equalized = GetEqualizedHistogram(histogram, totalPixel);

        return equalized;
    }

    public int[] GetBlueHistogramEqualized(BufferedImage image) {
        int totalPixel = GetTotalPixel(image);
        int[] histogram = GetBlueHistogram(image);
        int[] equalized = GetEqualizedHistogram(histogram, totalPixel);

        return equalized;
    }

    public int[] GetBrightnessHistogramEqualized(BufferedImage image) {
        int totalPixel = GetTotalPixel(image);
        int[] histogram = GetBrightnessHistogram(image);
        int[] equalized = GetEqualizedHistogram(histogram, totalPixel);

        return equalized;
    }

    public int[] GetSaturationHistogramEqualized(BufferedImage image) {
        int totalPixel = GetTotalPixel(image);
        int[] histogram = GetSaturationHistogram(image);
        int[] equalized = GetEqualizedHistogram(histogram, totalPixel);

        return equalized;
    }

    public int[] GetHueHistogramEqualized(BufferedImage image) {
        int totalPixel = GetTotalPixel(image);
        int[] histogram = GetHueHistogram(image);
        int[] equalized = GetEqualizedHistogram(histogram, totalPixel);

        return equalized;
    }

    public int[] GetHistogram(BufferedImage image, BufferedImageEqualizeType equalizeType) {
        {
            int[] histogram = null;

            switch (equalizeType) {
                case GRAYSCALE:
                    histogram = GetGrayHistogram(image);
                    break;
                case RED:
                    histogram = GetRedHistogram(image);
                    break;
                case GREEN:
                    histogram = GetGreenHistogram(image);
                    break;
                case BLUE:
                    histogram = GetBlueHistogram(image);
                    break;
                case BRIGHTNESS:
                    histogram = GetBrightnessHistogram(image);
                    break;
                case SATURATION:
                    histogram = GetSaturationHistogram(image);
                    break;
                case HUE:
                    histogram = GetHueHistogram(image);
                    break;

            }

            return histogram;
        }
    }

    public int[] GetHistogramAcc(BufferedImage image, BufferedImageEqualizeType equalizeType) {
        int[] histogram = null;

        switch (equalizeType) {
            case GRAYSCALE:
                histogram = GetGrayHistogramAcc(image);
                break;
            case RED:
                histogram = GetRedHistogramAcc(image);
                break;
            case GREEN:
                histogram = GetGreenHistogramAcc(image);
                break;
            case BLUE:
                histogram = GetBlueHistogramAcc(image);
                break;
            case BRIGHTNESS:
                histogram = GetBrightnessHistogramAcc(image);
                break;
            case SATURATION:
                histogram = GetSaturationHistogramAcc(image);
                break;
            case HUE:
                histogram = GetHueHistogramAcc(image);
                break;

        }

        return histogram;
    }

    public int[] GetHistogramEqualized(BufferedImage image, BufferedImageEqualizeType equalizeType) {
        int[] map = null;

        switch (equalizeType) {
            case GRAYSCALE:
                map = GetGrayHistogramEqualized(image);
                break;
            case RED:
                map = GetRedHistogramEqualized(image);
                break;
            case GREEN:
                map = GetGreenHistogramEqualized(image);
                break;
            case BLUE:
                map = GetBlueHistogramEqualized(image);
                break;
            case BRIGHTNESS:
                map = GetBrightnessHistogramEqualized(image);
                break;
            case SATURATION:
                map = GetSaturationHistogramEqualized(image);
                break;
            case HUE:
                map = GetHueHistogramEqualized(image);
                break;

        }

        return map;
    }

    public BufferedImage Equalize(BufferedImage image, BufferedImageEqualizeType equalizeType) {

        BufferedImage equalizedImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        int totalPixel = GetTotalPixel(image);

        int[] map = GetHistogramEqualized(image, equalizeType);

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y));
                Color equalizedColor = GetEqualizedHistogramColor(image, map, x, y, equalizeType);

                equalizedImage.setRGB(x, y, equalizedColor.getRGB());
            }
        }

        return equalizedImage;
    }

    public BufferedImage EqualizeHSV(BufferedImage image) {

        BufferedImage equalizedImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        int[] mapSaturation = GetSaturationHistogramEqualized(image);
        int[] mapBrightness = GetBrightnessHistogramEqualized(image);
        int[] mapHue = GetHueHistogramEqualized(image);

        for (int y = 0; y < image.getHeight(); y++)
        {
            for (int x = 0; x < image.getWidth(); x++)
            {
                float eBrightness = (float)(mapBrightness[ColorExtensions.Brightness255(image.getRGB(x, y))]/255);
                float eSaturation = (float)(mapSaturation[ColorExtensions.Saturations255(image.getRGB(x, y))]/255);
                float eHue = (float)(mapSaturation[ColorExtensions.Hue255(image.getRGB(x, y))]/255);

                Color newColor = new Color(Color.HSBtoRGB(eHue,eSaturation,eBrightness));
                equalizedImage.setRGB(x,y, newColor.getRGB());
            }
        }
        return equalizedImage;
    }
    public BufferedImage EqualizeSV(BufferedImage image) {

        BufferedImage equalizedImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        int[] mapSaturation = GetSaturationHistogramEqualized(image);
        int[] mapBrightness = GetBrightnessHistogramEqualized(image);

        for (int y = 0; y < image.getHeight(); y++)
        {
            for (int x = 0; x < image.getWidth(); x++)
            {
                Color color = new Color(image.getRGB(x, y));
                float hsv[] = color.RGBtoHSB(color.getRed(),color.getGreen(),color.getBlue(),null);

                float eBrightness = (float)(mapBrightness[ColorExtensions.Brightness255(image.getRGB(x, y))]/255);
                float eSaturation = (float)(mapSaturation[ColorExtensions.Saturations255(image.getRGB(x, y))]/255);

                Color newColor = new Color(Color.HSBtoRGB(hsv[0],eSaturation,eBrightness));
                equalizedImage.setRGB(x,y, newColor.getRGB());
            }
        }
        return equalizedImage;
    }

    public BufferedImage EqualizeRGB(BufferedImage image) {

        BufferedImage equalizedImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        int totalPixel = GetTotalPixel(image);

        int[] mapRed = GetRedHistogramEqualized(image);
        int[] mapGreen = GetGreenHistogramEqualized(image);
        int[] mapBlue = GetBlueHistogramEqualized(image);

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y));
                Color REC = GetEqualizedHistogramColor(image, mapRed, x, y, BufferedImageEqualizeType.RED);
                Color GEC = GetEqualizedHistogramColor(image, mapGreen, x, y, BufferedImageEqualizeType.GREEN);
                Color BEC = GetEqualizedHistogramColor(image, mapBlue, x, y, BufferedImageEqualizeType.BLUE);
                Color equalizedColor = new Color(REC.getRed(), GEC.getGreen(), BEC.getBlue());
                equalizedImage.setRGB(x, y, equalizedColor.getRGB());
            }
        }

        return equalizedImage;
    }

    public Color GetEqualizedHistogramColor(BufferedImage image, int[] map, int x, int y, BufferedImageEqualizeType equalizeType) {

        Color color = new Color(image.getRGB(x, y));
        float hsv[] = color.RGBtoHSB(color.getRed(),color.getGreen(),color.getBlue(),null);

        float equalizedTone = 0;

        Color newColor= null;

        switch (equalizeType) {
            case GRAYSCALE:
                equalizedTone = map[ColorExtensions.GetGrayTone(color)];
                newColor = new Color((int)equalizedTone, (int)equalizedTone, (int)equalizedTone);
                break;
            case RED:
                equalizedTone = map[color.getRed()];
                newColor = new Color((int)equalizedTone, color.getGreen(), color.getBlue());
                break;
            case GREEN:
                equalizedTone = map[color.getGreen()];
                newColor = new Color(color.getRed(), (int)equalizedTone, color.getBlue());
                break;
            case BLUE:
                equalizedTone = map[color.getBlue()];
                newColor = new Color(color.getRed(), color.getGreen(), (int)equalizedTone);
                break;
            case BRIGHTNESS:


                equalizedTone = (float)(map[ColorExtensions.Brightness255(image.getRGB(x, y))]/255);
                newColor = new Color(Color.HSBtoRGB(hsv[0],hsv[1],equalizedTone));

                break;
            case SATURATION:
                equalizedTone = (float)(map[ColorExtensions.Saturations255(image.getRGB(x, y))]/255);
                newColor = new Color(Color.HSBtoRGB(hsv[0],equalizedTone,hsv[2]));

                break;
            case HUE:
                equalizedTone = (float)(map[ColorExtensions.Hue255(image.getRGB(x, y))]/255);
                newColor = new Color(Color.HSBtoRGB(equalizedTone,hsv[1],hsv[2]));

                break;
        }


        return newColor;
    }

    public BufferedImage ChangeRedColor(BufferedImage image, int redColor) {
        BufferedImage changedColorImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color pixelColor = new Color(image.getRGB(x, y));
                Color newColor = new Color(redColor, pixelColor.getGreen(), pixelColor.getBlue());
                changedColorImage.setRGB(x, y, newColor.getRGB());
            }
        }

        return changedColorImage;
    }

    public BufferedImage ChangGreenColor(BufferedImage image, int greenColor) {
        BufferedImage changedColorImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color pixelColor = new Color(image.getRGB(x, y));
                Color newColor = new Color(greenColor, pixelColor.getGreen(), pixelColor.getBlue());
                changedColorImage.setRGB(x, y, newColor.getRGB());
            }
        }

        return changedColorImage;
    }

    public BufferedImage ChangeBlueColor(BufferedImage image, int blueColor) {
        BufferedImage changedColorImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color pixelColor = new Color(image.getRGB(x, y));
                Color newColor = new Color(blueColor, pixelColor.getGreen(), pixelColor.getBlue());
                changedColorImage.setRGB(x, y, newColor.getRGB());
            }
        }

        return changedColorImage;
    }

    public BufferedImage ChangeColor(BufferedImage image, Color color) {
        BufferedImage changedColorImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                changedColorImage.setRGB(x, y, color.getRGB());
            }
        }

        return changedColorImage;
    }


    /**
     * Gera o histograma acumulado apartir do histograma quantitativo
     *
     * @param histogram Histograma quantitativo contendo quantas vezes um tom da escala de 255
     *                  se repete na imagem
     * @return
     */
    public int[] GetHistogramAcc(int[] histogram) {
        int[] acc = new int[256];
        acc[0] = histogram[0];

        for (int a = 1; a < histogram.length; a++) {
            acc[a] += histogram[a] + acc[a - 1];
        }
        return acc;
    }

    /**
     * Gera lista de 255 tons equalizados apartir de um histograma quantitativo.
     *
     * @param histogram   Histograma quantitativo e não Acumulativo a ser utilizado na equalização.
     *                    Tenha em mente que a acumulação sera feita automaticamente por este metodo sendo
     *                    retornado nova lista contendo tons acumulados e equalizados.
     * @param totalPixels Total de pixels da imagem a ser gerado o histograma equalizado
     * @return retornado nova lista contendo tons acumulados e equalizados.
     */
    public int[] GetEqualizedHistogram(int[] histogram, int totalPixels) {

        int[] equalized = new int[256];

        //Hitograma acumulado (ha)
        int[] ha = GetHistogramAcc(histogram);

        //Quantidade de pixel no primeiro tom de cinza.
        //Esse é o ponto onde os níveis de cinza começam a existir.
        float hMin = ArrayExtensions.GetFirsNonZero(histogram);

        for (int i = 0; i < 256; i++) {
            equalized[i] = Math.round(((ha[i] - hMin) / (totalPixels - hMin)) * 255);
        }
        return equalized;
    }

    public int GetTotalPixel(BufferedImage image) {
        return image.getWidth() * image.getHeight();
    }

    public BufferedImage HistogramGraphic2D(int[] histogram, int width, int height, int ruleScale) {

        //Plano de fundo para o grafico
        BufferedImage gImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        gImage = ChangeColor(gImage, Color.WHITE);

        //Criando grafico
        Graphics2D graphics = gImage.createGraphics();

        //Criando fonte
        Font font = new Font("Arial", 0, 9);
        graphics.setFont(font);
        graphics.setPaint(Color.black);
        //FontMetrics fontMetrics = graphics.getFontMetrics();

        //Desenhando regua
        BufferedImageRect rect = new BufferedImageRect(gImage);
        rect.setPosition(0, 0);
        rect.setOffSet(50, 50, -100, -100);
        rect.drawGraphic2D(graphics);

        //Inner padding para desenhar o grafico dentro da borda
        BufferedImageRect padding = new BufferedImageRect(gImage);
        padding.setPosition(0, 0);
        padding.setOffSet(80, 80, -160, -160);

        //Maior valor do histograma, usado para limitar o grafico no topo
        int mostValue = ArrayExtensions.GetMaxValue(histogram);

        //Draw Rule Scale
        graphics.drawString("Rule Scale by: " + ruleScale, rect.getRight() / 2, rect.getY() - 20);

        //Draw Bottom Rule
        for (int a = 0; a <= ruleScale; a++) {
            String message = ((256 * 10 / ruleScale * 10) * a) / 100 + "";
            int eachXByScale = padding.getWidht() / ruleScale;
            graphics.drawString(message, padding.getX() + (eachXByScale * a), padding.getBottom() + 50);
        }

        //Draw Left Rule
        for (int a = 0; a <= ruleScale; a++) {
            String message = ((mostValue * 10 / ruleScale * 10) * a) / 100 + "";
            int eachYByScale = padding.getHeight() / ruleScale;
            graphics.drawString(message, rect.getX() - 35, padding.getBottom() - (eachYByScale * a));
        }

        //Draw Line Histogram Values
        for (int a = 0; a < 256; a++) {

            //Horizontal
            float eachX = (float) padding.getWidht() / 256;
            int px = padding.getX() + (int) (a * eachX);

            //Vertical
            float eachY = (float) padding.getHeight() / mostValue;
            int py = padding.getBottom() - (int) ((float) histogram[a] * eachY);

            graphics.drawLine(px, padding.getBottom(), px, py);
        }

        return gImage;

    }

    public BufferedImage LoadImage(String filePath) throws IOException {
        return ImageIO.read(new File(filePath));
    }

    public boolean SaveImage(BufferedImage image, String extensions, String filePath) throws IOException {

        return ImageIO.write(image, extensions, new File(filePath));
    }

}

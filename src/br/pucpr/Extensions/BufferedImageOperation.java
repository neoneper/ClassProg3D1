package br.pucpr.Extensions;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


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
     * Modifica os valores de Hue, Saturation e Brightness da Imagem. Os valores de HSV podem variar de
     * -1 a 1 sendo o valor zero o atual HSV da imagem.
     *
     * @param img        Imagem origem
     * @param hue        HUE -1 a 1 sendo 0 o Hue atual da imagem
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
     * Modifica os valores de Hue da Imagem. Os valores de HUE podem variar de
     * -1 a 1 sendo o valor zero o atual HUE da imagem.
     * @param img imagem de origem
     * @param hue HUE -1 a 1 sendo 0 o Hue atual da imagem
     * @return Imagem com Hue Modificado
     */
    public BufferedImage HUE(BufferedImage img, float hue) {

        return HSV(img,hue,0,0);
    }
    /**
     * Modifica os valores de Saturação da Imagem. Os valores de Saturação podem variar de
     * -1 a 1 sendo o valor zero o atual Saturaçao da imagem.
     * @param img imagem de origem
     * @param saturation Saturaçao -1 a 1 sendo 0 o Saturaçao atual da imagem
     * @return Imagem com Saturaçao Modificado
     */
    public BufferedImage SATURATION(BufferedImage img, float saturation) {

        return HSV(img,0,saturation,0);
    }
    /**
     * Modifica os valores de brightness da Imagem. Os valores de brightness podem variar de
     * -1 a 1 sendo o valor zero o atual brightness da imagem.
     * @param img imagem de origem
     * @param brightness brightness -1 a 1 sendo 0 o Hue atual da imagem
     * @return Imagem com brightness Modificado
     */
    public BufferedImage BRIGHTNESS(BufferedImage img, float brightness) {

        return HSV(img,0,0,brightness);
    }


    /**
     * Utilize este metodo para modificar a palleta de caores da imagem original por uma pre-estabelecidada.
     * Tenha em mente que a subistituição dos pixels originais da imagem serão feitas mediante a cor mais proxima
     * da palleta.
     * @param image Imagem origem
     * @param pallete paleta de cores Hexadecimais
     * @return Imagem com pixels subistituidos por cores da paleta.
     */
    public BufferedImage ChangeColorPallet(BufferedImage image, int[] pallete)
    {

        BufferedImage changedImage = new BufferedImage(image.getWidth(),image.getHeight(),image.getType());

        for(int y = 0; y < changedImage.getHeight();y++)
        {
            for(int x =0; x< changedImage.getWidth();x++)
            {
                Color colorFrom = new Color( image.getRGB(x,y));
                Color colorToChange = ColorExtensions.ColorNear(colorFrom,pallete);
                changedImage.setRGB(x, y, colorToChange.getRGB());
            }
        }

        return  changedImage;
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

        BufferedImage combinedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

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


    public int[] GetHistogram(BufferedImage image)
    {

        List<Integer> intList = new ArrayList<Integer>();

        for(int y = 0; y < image.getHeight();y++)
        {
            for(int x =0; x< image.getWidth();x++)
            {
                Color colorFrom = new Color( image.getRGB(x,y));


            }
        }

        return  changedImage;
    }
    public int[] GetHistogramAcc(int[] histogram)
    {

    }
    public int[] GetHistogramAcc(BufferedImage img)
    {

    }

}

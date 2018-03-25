package br.pucpr.Extensions;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by devsecond on 24/03/2018.
 */
public class BufferedImageOperation {

    private static final float[][] Kernel_BoxBlur = {{1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f}, {1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f}, {1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f},};
    private static final float[][] Kernel_GausianBlur = {{0, 0, 0, 5, 0, 0, 0}, {0, 5, 18, 32, 18, 5, 0}, {0, 18, 64, 100, 64, 18, 0}, {5, 32, 100, 100, 100, 32, 5}, {0, 18, 64, 100, 64, 18, 0}, {0, 5, 18, 32, 18, 5, 0}, {0, 0, 0, 5, 0, 0, 0},};
    private static final float[][] Kernel_SharpBlur = {{1.0f / 16.0f, 2.0f / 16, 1.0f / 16}, {1.0f / 9.0f, 4.0f / 16, 1.0f / 9.0f}, {1.0f / 16, 2.0f / 16, 1.0f / 16},};
    private static final float[][] Kernel_CrossBlur = {{0, 1.0f / 5.0f, 0}, {1.0f / 5.0f, 1.0f / 5.0f, 1.0f / 5.0f}, {0, 1.0f / 5.0f, 0},};
    private static final float Kernel_Laplace[][] = {{0, 1, 0}, {1, -4, 1}, {0, 1, 0}};
    private static final float Kernel_LaplaceDiagonal[][] = {{0.5f, 1, 0.5f}, {1, -6, 1}, {0.5f, 1, 0.5f}};
    private static final float Kernel_Sharpen[][] = {{0, -1, 0}, {-1, 5, -1}, {0, -1, 0}};
    private static final float Kernel_EnbosOut[][] = {{-2, -2, 0}, {-2, 6, 0}, {0, 0, 0}};
    private static final float Kernel_EnbosInner[][] = {{0, 0, 0}, {0, 6, -2}, {0, -2, -2}};
    private static final float Kernel_EnbosCenter[][] = {{-2, -1, 0}, {-1, -1, 1}, {0, 1, 2}};
    private static final float Kernel_Sobelx[][] = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
    private static final float Kernel_Sobely[][] = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
    private static final float Kernel_Prewittx[][] = {{-1, 0, 1}, {-1, 0, 1}, {-1, 0, 1}};
    private static final float Kernel_Prewitty[][] = {{1, 0, 1}, {1, 0, 1}, {1, 0, 1}};

    public BufferedImage Convolve(BufferedImage img, BufferedImageOperationType otype) {

        BufferedImage filteredImage = null;

        //Imagens temporarios serao usadas para os filtros que necessitam de combinacao de resultados
        //tais como SOBEL, PREWITT
        BufferedImage tmpImage1 = null;
        BufferedImage tmpImage2 = null;

        switch (otype) {
            case BOXBLUR:
                filteredImage = Convolve(img, Kernel_BoxBlur);
                break;
            case CROSBLUR:
                filteredImage = Convolve(img, Kernel_CrossBlur);
                break;
            case GAUSIANBLUR:
                filteredImage = Convolve(img, Kernel_GausianBlur);
                break;
            case SHARPBLUR:
                filteredImage = Convolve(img, Kernel_SharpBlur);
                break;
            case SOBEL:
                tmpImage1 = Convolve(img, Kernel_Sobelx);
                tmpImage2 = Convolve(img, Kernel_Sobely);
                filteredImage = CombineToBorder(tmpImage1, tmpImage2);
                break;
            case PREWITT:
                tmpImage1 = Convolve(img, Kernel_Prewittx);
                tmpImage2 = Convolve(img, Kernel_Prewitty);
                filteredImage = CombineToBorder(tmpImage1, tmpImage2);
                break;
            case LAPLACE:
                filteredImage = Convolve(img, Kernel_Laplace);
                break;
            case LAPLACE_DIAGONAL:
                filteredImage = Convolve(img, Kernel_LaplaceDiagonal);
                break;
            case SHARPEN:
                filteredImage = Convolve(img, Kernel_Sharpen);
                break;
            case ENBOSS_OUT:
                filteredImage = Convolve(img, Kernel_EnbosOut);
                break;
            case ENBOSS_INNER:
                filteredImage = Convolve(img, Kernel_EnbosInner);
                break;
            case ENBOSS_CENTER:
                filteredImage = Convolve(img, Kernel_EnbosCenter);
                break;
        }

        return filteredImage;
    }

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

    public BufferedImage Pixelate(BufferedImage img, int size){

        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                int px = (x/size) * size;
                int py = (y/size) * size;
                out.setRGB(x, y, img.getRGB(px,py));
            }
        }
        return out;
    }
    public BufferedImage HSV(BufferedImage img, float hue, float saturation, float brightness){

        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < img.getHeight(); y++)
        {
            for (int x = 0; x < img.getWidth(); x++)
            {
                Color pixelColor = new Color(img.getRGB(x,y));
                Color modfyColor = ColorExtensions.ColorHSB(pixelColor,hue,saturation,brightness);
                out.setRGB(x,y,modfyColor.getRGB());
            }
        }
        return out;
    }


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

}

package br.pucpr.Extensions;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by devsecond on 24/03/2018.
 */
public class BufferedImageOperation {

    private static final float[][] Kernel_BoxBlur = {
            {1.0f/9.0f, 1.0f/9.0f, 1.0f/9.0f},
            {1.0f/9.0f, 1.0f/9.0f, 1.0f/9.0f},
            {1.0f/9.0f, 1.0f/9.0f, 1.0f/9.0f},
    };
    private static final float[][] Kernel_GausianBlur = {
            {0,0,0,5,0,0,0},
            {0,5,18,32,18,5,0},
            {0,18,64,100,64,18,0},
            {5,32,100,100,100,32,5},
            {0,18,64,100,64,18,0},
            {0,5,18,32,18,5,0},
            {0,0,0,5,0,0,0},
    };
    private static final float[][] Kernel_SharpBlur = {
            {1.0f/16.0f, 2.0f/16, 1.0f/16},
            {1.0f/9.0f,  4.0f/16, 1.0f/9.0f},
            {1.0f/16,  2.0f/16, 1.0f/16},
    };
    private static final float[][] Kernel_CrossBlur = {
            {0, 1.0f/5.0f, 0},
            {1.0f/5.0f, 1.0f/5.0f, 1.0f/5.0f},
            {0, 1.0f/5.0f, 0},
    };


    public BufferedImage convolve(BufferedImage img, BufferedImageOperationType otype)
    {
        BufferedImage filteredImage=null;

        switch (otype)
        {
            case BOXBLUR:
                filteredImage = convolve(img, Kernel_BoxBlur);
                break;
            case CROSBLUR:
                filteredImage = convolve(img, Kernel_CrossBlur);
                break;
            case GAUSIANBLUR:
                filteredImage = convolve(img, Kernel_GausianBlur);
                break;
            case SHARPBLUR:
                filteredImage = convolve(img, Kernel_SharpBlur);
                break;
        }

        return filteredImage;
    }

    public BufferedImage convolve(BufferedImage img, float[][] kernel)
    {
        BufferedImage filteredImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        int rows = img.getHeight();
        int cols = img.getWidth();

        //Iterando Pixel a Pixel da Imagem para verificar o valor de kernel
        for(int y = 0; y < rows; y++)
        {
            for(int x =0; x < cols; x++)
            {
                int kernelPixelColor = GetKernelPixelColor(img, x, y, kernel);
                filteredImage.setRGB(x,y, kernelPixelColor);
            }
        }


        return filteredImage;
    }


    private int GetKernelPixelColor(BufferedImage image, int x, int y, float[][] kernel)
    {
        int rows = image.getHeight();
        int cols = image.getWidth();


        //BiDimencional array podem ser avaliando como um array de array.
        //Para medir o tamanho basta verificar os indices do objeto array.
        //Sendo um array 2D terei array[0] e array[1] ocupados com outro array.
        int kernel_rows = kernel[0].length;
        int kernel_cols = kernel[1].length;

        //Centro do kernel, so funciona para kerneis IMPAR e Quadrado. (3x3), (5,5), (7,7)...
        //Ex: k[3] = [0,(Center 1),2]
        int kernel_centerX = kernel_cols/2;
        int kernel_centerY = kernel_rows/2;

        int r = 0;
        int g = 0;
        int b = 0;

        for (int ky=0; ky < kernel_rows; ky++)
        {
            for(int kx=0;kx<kernel_cols;kx++)
            {
                //Indices Flipado do Kernel Array. Ex ( cols: 3 - 1 -itY:0 = 2). (1). (0).
                int kernelIndex_x = (kernel_cols -1 ) - kx;
                int kerneIndex_y = (kernel_rows - 1) - ky;

                //Indice do Kernel no array de pixels da imagem
                int pixel_x = x + (kx - kernel_centerX);
                int pixel_y = y + (ky - kernel_centerY);

                //Verificando o Bounding da imagem pra ver se o pixel esta dentro
                if( pixel_y >= 0 && pixel_y < rows && pixel_x >= 0 && pixel_x < cols )
                {
                    //Capturando PIXEL que sera tratado pelo kernel e aplicando
                    //as modificações de RGB
                    Color color = new Color(image.getRGB(pixel_x,pixel_y));
                    r += color.getRed() * kernel[kernelIndex_x][kerneIndex_y];
                    g += color.getGreen() * kernel[kernelIndex_x][kerneIndex_y];
                    b += color.getBlue() * kernel[kernelIndex_x][kerneIndex_y];
                }
            }
        }

        return ColorExtensions.RGBHexSaturate(r,g,b);
    }

}

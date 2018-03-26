package br.pucpr.Extensions;

/**
 * Created by devsecond on 25/03/2018.
 * Esta classe contém diverços arrays 2D para serem utilizados como Kernel em Convolução de imagens.
 * @see BufferedImageOperation para trabalhar com convolução de imagene
 */
public class BufferedImageOperationKernel {

    //Suavisação
    public static final float[][] Kernel_BoxBlur =
            {
                    {1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f},
                    {1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f},
                    {1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f},
            };
    public static final float[][] Kernel_GausianBlur = {{0, 0, 0, 5, 0, 0, 0}, {0, 5, 18, 32, 18, 5, 0}, {0, 18, 64, 100, 64, 18, 0}, {5, 32, 100, 100, 100, 32, 5}, {0, 18, 64, 100, 64, 18, 0}, {0, 5, 18, 32, 18, 5, 0}, {0, 0, 0, 5, 0, 0, 0},};
    public static final float[][] Kernel_SharpBlur = {{1.0f / 16.0f, 2.0f / 16, 1.0f / 16}, {1.0f / 9.0f, 4.0f / 16, 1.0f / 9.0f}, {1.0f / 16, 2.0f / 16, 1.0f / 16},};
    public static final float[][] Kernel_CrossBlur = {{0, 1.0f / 5.0f, 0}, {1.0f / 5.0f, 1.0f / 5.0f, 1.0f / 5.0f}, {0, 1.0f / 5.0f, 0},};
    //Bordas
    public static final float Kernel_Laplace[][] = {{0, 1, 0}, {1, -4, 1}, {0, 1, 0}};
    public static final float Kernel_LaplaceDiagonal[][] = {{0.5f, 1, 0.5f}, {1, -6, 1}, {0.5f, 1, 0.5f}};
    public static final float Kernel_Sobelx[][] = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
    public static final float Kernel_Sobely[][] = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
    public static final float Kernel_Prewittx[][] = {{-1, 0, 1}, {-1, 0, 1}, {-1, 0, 1}};
    public static final float Kernel_Prewitty[][] = {{1, 1, 1}, {0, 0, 0}, {-1, -1, -1}};
    //Nitides
    public static final float Kernel_Sharpen[][] = {{0, -1, 0}, {-1, 5, -1}, {0, -1, 0}};
    public static final float Kernel_EnbosOut[][] = {{-2, -2, 0}, {-2, 6, 0}, {0, 0, 0}};
    public static final float Kernel_EnbosInner[][] = {{0, 0, 0}, {0, 6, -2}, {0, -2, -2}};
    public static final float Kernel_EnbosCenter[][] = {{-2, -1, 0}, {-1, -1, 1}, {0, 1, 2}};

}

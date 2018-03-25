package br.pucpr.atividade2;

import br.pucpr.Extensions.BufferedImageOperation;
import br.pucpr.Extensions.BufferedImageOperationType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by devsecond on 24/03/2018.
 */
public class Teste {

    public static void main(String[] args) throws IOException {

        BufferedImageOperation imageOp = new BufferedImageOperation();

        BufferedImage inImage = ImageIO.read(new File( "puppy.jpg"));
        BufferedImage outImage =  imageOp.Pixelate(inImage,7);
        outImage = imageOp.HSV(outImage,0,0,0.5f);
        outImage = imageOp.Convolve(outImage, BufferedImageOperationType.SHARPEN);


        ImageIO.write(outImage, "jpg",new File("metroidKernel.jpg"));
    }
}

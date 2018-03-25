package br.pucpr.atividade2;

import br.pucpr.Extensions.BufferedImageOperation;
import br.pucpr.Extensions.BufferedImageOperationType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by devsecond on 24/03/2018.
 */
public class Teste {

    public static void main(String[] args) throws IOException {

        BufferedImageOperation imageOp = new BufferedImageOperation();

        BufferedImage inImage = ImageIO.read(new File( "metroid.jpg"));
        BufferedImage outImage = imageOp.convolve(inImage, BufferedImageOperationType.ENBOSS_OUT);

        ImageIO.write(outImage, "jpg",new File("metroidKernel.jpg"));
    }
}

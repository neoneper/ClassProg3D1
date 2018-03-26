package br.pucpr.atividade2;

import br.pucpr.Extensions.BufferedImageOperation;
import br.pucpr.Extensions.BufferedImageOperationType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by devsecond on 24/03/2018.
 * Crie o filtro de "Pixelate" que mistura 3 técnicas:
 - Alterar a imagem para ficar pixelada
 – o usuário deve
 poder escolher o tamanho do pixel
 - Aplicar contraste
 */
public class Atividade2 {

    public static void main(String[] args) throws IOException {

        //Inicializando objeto que me permite trabalhar com convolução e efeitos visuais na imagem origem
        BufferedImageOperation imageOperation = new BufferedImageOperation();
        //Carregando imagem para manipulação
        BufferedImage inImage = ImageIO.read(new File( "puppy.jpg"));
        //Criando imagem pixalate
        BufferedImage outImage =  imageOperation.Pixelate(inImage,7);
        //Aplicando Brilho
        outImage = imageOperation.BRIGHTNESS(outImage,0.5f);
        //Aplicando Kernel Sharpen
        outImage = imageOperation.Convolve(outImage, BufferedImageOperationType.SHARPEN);
        //Salvando nova Imagem
        ImageIO.write(outImage, "jpg",new File("puppy_pixels.jpg"));
    }
}

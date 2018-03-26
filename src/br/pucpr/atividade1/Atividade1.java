package br.pucpr.atividade1;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import br.pucpr.Extensions.BufferedImageOperation;
import br.pucpr.Extensions.ColorExtensions;

/**
 * Created by devsecond on 17/03/2018.
 * - Exercício 1 - Paleta de Cores:
 * Faça um programa que leia a imagem /img/cor/puppy.png e converta suas cores para essa palheta de 48 cores do pinterest
 * - Solução:
 * Converto Hexadecimal para Color e Color para Vector3D.
 * Com ambos sendo vetores eu utilizo o teorema de pitagoras para calcular a distância entre 2 Vetores
 * Dist = Sqrt( (x1-x2)^2 + (y1-y2)^2 ).
 * Uma vez sabendo a distancia entre os 2 vetores de cores eu seleciono na lista de cores da palleta
 * a cor cuja distãncia seja a menor comparando com uma cor de referencia.
 * - Extensions:
 * Classe e metodos uteis utilizados para conversão e calculo de distãncia podem ser acessados apartir do pacote
 * br.pucpr.Extensions.ColorExtensions
 */
public class Atividade1 {

    public static int[] pallete48 = {  //pinterest

            0xD2E3F5, 0x2F401E, 0x3E0A11, 0x4B3316, 0xA5BDE5, 0x87A063,
            0x679327, 0x3A1B0F, 0x928EB1, 0xBFE8AC, 0xA4DA65, 0x5A3810,
            0x47506D, 0x98E0E8, 0x989721, 0x8E762C, 0x0B205C, 0x55BEd7,
            0xB8B366, 0xD8C077, 0x134D9C, 0x2A6E81, 0xE1EAB6, 0xF0DEA6,
            0xFFF3D0, 0x610A0A, 0x7D000E, 0x45164B, 0xFFFCCC, 0x6B330F,
            0x990515, 0x250D3B, 0xB24801, 0x8B4517, 0xE0082D, 0x50105A,

            0xFFF991, 0xB96934, 0xC44483, 0x8E2585, 0xDF5900, 0xF8A757,
            0xC44483, 0xD877CF, 0xFFEF00, 0xDF7800, 0xF847CE, 0xF0A6E8
    };

    public static void main(String[] args) throws IOException {

        //Inicializando objeto que me permite trabalhar com convolução e efeitos visuais na imagem origem
        BufferedImageOperation imageOperation = new BufferedImageOperation();
        //Imagem carregada para manipulação das cores
        BufferedImage loadedImage = ImageIO.read(new File("puppy.jpg"));
        //Imagem de saida contendo as cores modificadas pela palleta especificada
        BufferedImage outImage = imageOperation.ChangeColorPallet(loadedImage, pallete48);
        //Salvando Imagem
        ImageIO.write(outImage, "jpg", new File("puppy2.jpg"));
    }
}

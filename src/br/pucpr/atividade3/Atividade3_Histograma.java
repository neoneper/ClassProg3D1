package br.pucpr.atividade3;


import br.pucpr.Extensions.ArrayExtensions;
import br.pucpr.Extensions.BufferedImageEqualizeType;
import br.pucpr.Extensions.BufferedImageOperation;
import br.pucpr.Extensions.BufferedImageRect;
import org.w3c.dom.css.Rect;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

/*Faça a função BufferedImage equalize(BufferedImage img)
para equalizar imagens em tons de cinza.
 Teste nas imagens car.png, cars.jpg, crowd.png,
montanha.jpg e university.png, disponíveis napasta gray.
*/
public class Atividade3_Histograma {


    public static void main(String[] args) throws IOException {

        //Inicializando objeto que me permite trabalhar com convolução, histogramas e efeitos
        BufferedImageOperation imageOperation = new BufferedImageOperation();

        //Carregando imagens para equalizar
        BufferedImage montanha = imageOperation.LoadImage("montanha.jpg");
        BufferedImage car = imageOperation.LoadImage("car.jpg");
        BufferedImage cars = imageOperation.LoadImage("cars.jpg");
        BufferedImage crowd = imageOperation.LoadImage("crowd.jpg");
        BufferedImage university = imageOperation.LoadImage("university.jpg");

        //Equalizando e gerando novas imagens equalizadas
        BufferedImage out_montanha = imageOperation.Equalize(montanha, BufferedImageEqualizeType.GRAYSCALE);
        BufferedImage out_cars = imageOperation.Equalize(cars, BufferedImageEqualizeType.GRAYSCALE);
        BufferedImage out_car = imageOperation.Equalize(car, BufferedImageEqualizeType.GRAYSCALE);
        BufferedImage out_crowd = imageOperation.Equalize(crowd, BufferedImageEqualizeType.GRAYSCALE);
        BufferedImage out_university = imageOperation.Equalize(university, BufferedImageEqualizeType.GRAYSCALE);

        //Salvando imagens equalizadas
        imageOperation.SaveImage(out_montanha,"jpg","out_montanha.jpg");
        imageOperation.SaveImage(out_car,"jpg","out_car.jpg");
        imageOperation.SaveImage(out_cars,"jpg","out_cars.jpg");
        imageOperation.SaveImage(out_crowd,"jpg","out_crowd.jpg");
        imageOperation.SaveImage(out_university,"jpg","out_university.jpg");

    }

}

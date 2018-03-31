package br.pucpr.Extensions;

import com.sun.javafx.geom.Vec3d;

import java.awt.*;

/**
 * Created by devsecond on 17/03/2018.
 * Esta classe contém metodos estaticos uteis para trabalhar com Objetos do tipo Color.
 * - Calculo de distancia entre dois Objectos Color
 * - COnverção de Cor em Formato Hexadecimal para Objeto Color
 * - Pesquisa da cor mais proxima apartir de uma lista de cores em formato Hexadecimal
 */
public class ColorExtensions {

    /**
     * Calcula a distância entre 2 Objetos do tipo Color e retorna o resultado
     *
     * @param color1 Cor origem
     * @param color2 Cor destino
     * @return Retorna a distância entre as duas cores.
     */
    public static float Distance(Color color1, Color color2) {
        Vec3d colorVec1 = new Vec3d(color1.getRed(), color1.getGreen(), color1.getBlue());
        Vec3d colorVec2 = new Vec3d(color2.getRed(), color2.getGreen(), color2.getBlue());

        return Distance(colorVec1, colorVec2);
    }

    /**
     * Calcula a distância entre 2 Vetores3D e retorna o resultado
     *
     * @param vec1 Vetor Origem
     * @param vec2 Vetor Destino
     * @return Retorna a distância entre dois vetores
     */
    public static float Distance(Vec3d vec1, Vec3d vec2) {

        double xPow = Math.pow(vec1.x - vec2.x, 2);
        double yPow = Math.pow(vec1.y - vec2.y, 2);
        double zPow = Math.pow(vec1.z - vec2.z, 2);

        double result = Math.sqrt(xPow + yPow + zPow);

        return (float) result;

    }

    /**
     * Converte uma cor Hexadecimal para um Objeto do tipo Color
     *
     * @param hex hexadecimal da cor origem
     * @return Retorna um Objeto do tipo Color
     */
    public static Color HexToColor(int hex) {
        int r = (hex & 0xFF0000) >> 16;
        int g = (hex & 0xFF00) >> 8;
        int b = (hex & 0xFF);

        return new Color(r, g, b);
    }

    /**
     * Utilizando uma cor de referência, este método procura em uma lista de cores Hexadecimais
     * a cor mais proxima da cor de rerência. Caso nenhuma seja encontrada a cor PRETA será retornada.
     * Tenha em mente que a lista passada como parâmetro de conter cores no formato exadecimal 0xFF0000
     *
     * @param colorRerence Cor de referencia, utilizada para procurar uma cor mais proxima da lsita de cores
     * @param hexPallet    Lista de cores hexadecimais onde a pesquisa pela cor mais proxima será feita.
     * @return A Cor mais proxima da cor de referencia ou Preto caso não exista nenhuma
     */
    public static Color ColorNear(Color colorRerence, int[] hexPallet) {

        float minDistance = Float.MAX_VALUE;
        Color result = new Color(0, 0, 0);

        //Listando todas as cores da palleta e verificando a distância da cor de rerencia com a pesquisada
        //da paleta. A cor da pelta cuja a distância seja a menor será escolhida para ser retornada.
        for (int hcolor : hexPallet) {
            //Converte a cor Hexadecimal da paleta para um Objeto Color
            Color colorTarget = HexToColor(hcolor);
            //Calculo a distancia da cor de referencia com a cor da lista.
            float current_dist = Distance(colorRerence, colorTarget);
            //Se a cor for menor do que a ultima selecionada entao eu subistituo ela pela de menor distancia
            if (minDistance > current_dist) {
                minDistance = current_dist;
                result = colorTarget;
            }
        }

        return result;
    }

    /**
     * Converte os canais manuais de Rgb do parametro em um RGB Hexadecimal
     *
     * @param r Red
     * @param g Green
     * @param b Blue
     * @return RGB Hexadecimal
     */
    public static int RGBHex(int r, int g, int b) {
        Color color = new Color(r, g, b);
        return color.getRGB();
    }

    public static int Red(int HexRGBColor) {
        Color color = new Color(HexRGBColor);
        return color.getRed();
    }

    public static int Green(int HexRGBColor) {
        Color color = new Color(HexRGBColor);
        return color.getGreen();
    }

    public static int Blue(int HexRGBColor) {
        Color color = new Color(HexRGBColor);
        return color.getBlue();
    }

    public static int Brightness(int HexRGBColor) {
        Color color = new Color(HexRGBColor);

        float[] hsb = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        return  (int)(hsb[2] * 255);

    }

    public static int Saturations(int HexRGBColor) {
        Color color = new Color(HexRGBColor);

        float[] hsb = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        return  (int)(hsb[1] * 255);

    }

    public static int Hue(int HexRGBColor) {
        Color color = new Color(HexRGBColor);
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);

        return (int) hsb[0] * 255;
    }

    /**
     * Converte os canais manuais de Rgb do parametro em um RGB Hexadecimal, respeitando os valores
     * máximos permitido pelos pixeis de cor cuja variacão é 0 -255
     *
     * @param r Red
     * @param g Green
     * @param b Blue
     * @return RGB Hexadecimal com os valores dos canais RGB variando de 0 a 255
     */
    public static int RGBHexMinMax(int r, int g, int b) {
        r = PixelMinMax(r);
        g = PixelMinMax(g);
        b = PixelMinMax(b);

        Color color = new Color(r, g, b);
        return color.getRGB();
    }

    /**
     * Converte um objeto do tipo Color em um RGB Hexadecimal, respeitando os valores
     * máximos permitido pelos pixeis de cor cuja variacão é 0 -255
     *
     * @param color Objeto cor
     * @return RGB Hexadecimal com os valores dos canais RGB variando de 0 a 255
     */
    public static int RGBHexMinMax(Color color) {

        int r = PixelMinMax(color.getRed());
        int g = PixelMinMax(color.getGreen());
        int b = PixelMinMax(color.getBlue());

        Color ncolor = new Color(r, g, b);
        return ncolor.getRGB();
    }

    /**
     * Reguraliza um objeto do tipo cor cujo os valores de RGB não estejam nos intervalos de 0 a 255
     * Não tenho certeza o Java permite a criacao de um objeto Color com os canais fora do padrao mas se permitir
     * utilize este metodo para manter o mesmo correto.
     *
     * @param color Objeto cor origem
     * @return Objeto Color com os canais RGB dentro do intervalo 0-255
     */
    public static Color ColorMinMax(Color color) {

        int r = PixelMinMax(color.getRed());
        int g = PixelMinMax(color.getGreen());
        int b = PixelMinMax(color.getBlue());

        return new Color(r, g, b);

    }

    /**
     * Converte os canais manuais de Rgb do parametro em um Objeto Color, respeitando os valores
     * máximos permitido pelos pixeis de cor cuja variacão é 0 -255.
     *
     * @param r Red
     * @param g Green
     * @param b Blue
     * @return Objeto Color com os canais RGB dentro do intervalo 0-255
     */
    public static Color ColorMinMax(int r, int g, int b) {

        int sr = PixelMinMax(r);
        int sg = PixelMinMax(g);
        int sb = PixelMinMax(b);

        return new Color(sr, sg, sb);

    }

    /**
     * Verifica um valor nos itervalos de 0 a 255, padrão dos canais RGB de um pixel mantendo
     * o mesmo dentro deste intervalo
     *
     * @param v Valor a ser verificado
     * @return Retorna o valor especificado ou modificado para manter o padrao normalizado 0 a 255
     */
    public static int PixelMinMax(int v) {
        int result = v > 255 ? 255 : v < 0 ? 0 : v;
        return result;
    }

    /**
     * Combina 2 objetos de cores utilizando a formula de SOBEL e PREWITT para gerar uma nova cor,
     * resultado desta combinação.
     *
     * @param color1
     * @param color2
     * @return Combinação das 2 Cores utilizando a formula de SOBEL e PREWITT
     */
    public static Color CombinePrewitt(Color color1, Color color2) {

        return CombineSobel(color1, color2);

    }

    /**
     * Combina 2 objetos de cores utilizando a formula de SOBEL e PREWITT para gerar uma nova cor,
     * resultado desta combinação
     *
     * @param color1
     * @param color2
     * @return Combinação das 2 Cores utilizando a formula de SOBEL e PREWITT
     */
    public static Color CombineSobel(Color color1, Color color2) {
        //RGB Imagem1
        int r1 = color1.getRed();
        int g1 = color1.getGreen();
        int b1 = color1.getBlue();

        //RGB Imagem2
        int r2 = color2.getRed();
        int g2 = color2.getGreen();
        int b2 = color2.getBlue();

        //Aplicando formula SOBEL de combinacao [ G = sqr(gx^, gy^)]
        // JAVA DOCS: hypot(double x, double y)
        // Returns sqrt(x2 +y2) without intermediate overflow or underflow.
        //https://docs.oracle.com/javase/7/docs/api/java/lang/Math.html
        int R = (int) Math.hypot(r1, r2);
        int G = (int) Math.hypot(g1, g2);
        int B = (int) Math.hypot(b1, b2);

        return ColorExtensions.ColorMinMax(R, G, B);

    }

    /**
     * Permite manipular os canais Hue, Saturation e Brightness de um objeto do tipo Color, gerando um novo
     * Objeto Color modificado.
     * Tenha em mente que os valores HSB serão relativos aos valores ja existentes na cor origem.
     *
     * @param color      Objeto Cor origem
     * @param hue        HUE 0-1
     * @param saturation Saturação 0-1
     * @param brightness Brilho 0-1
     * @return
     */
    public static Color ColorHSB(Color color, float hue, float saturation, float brightness) {

        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);

        float pixelHue = hsb[0] + hue > 1.0f ? 1.0f : hsb[0] + hue < 0.0f ? 0 : hsb[0] + hue;
        float pixelSaturation = hsb[1] + saturation > 1.0f ? 1.0f : hsb[1] + saturation < 0.0f ? 0 : hsb[1] + saturation;
        float pixelBright = hsb[2] + brightness > 1.0f ? 1.0f : hsb[2] + brightness < 0.0f ? 0 : hsb[2] + brightness;

        return new Color(Color.HSBtoRGB(pixelHue, pixelSaturation, pixelBright));
    }

    /**
     * Gertorna o tom de Cinza do Objeto Color
     *
     * @param color
     */
    public static int GetGrayTone(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        int gray = (r + g + b) / 3;

        return gray;
    }

    public static int GetGrayTone(int HexRGBColor) {
        Color color = HexToColor(HexRGBColor);
        return GetGrayTone(color);
    }

}

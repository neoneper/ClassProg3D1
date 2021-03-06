# PROG3D

Exercícios, Atividades, Desafios e TDE referentes a disciplina de Programação de Jogos 3D (Turma U) - Curso Superior deTecnologia em Jogos Digitais - 2018 / 1º Sem
# [WIKI - HOME PAGE](https://github.com/neoneper/ClassProg3D1/wiki)
Entre aqui para ender como tudo funciona:

# PROCESSAMENTO DE IMAGENS - ATIVIDADES

* [Atividade1] Paleta de Cores:

[![N|Solid](https://docs.google.com/uc?id=1bRwnf6KnkCWMrYgakZf1plA_DHMlqTp_)](https://github.com/neoneper/ClassProg3D1/wiki/Pallet-Colors-Change)

* [Atividade2] - Pixalate + Sharpen + Brightness 50%

[![N|Solid](https://docs.google.com/uc?id=1-XNsEudz14nD_eKd0VTLDQl9Wx5SCl3I)](https://github.com/neoneper/ClassProg3D1/wiki/Image-Pixalate)

* [Atividade3] - Histogramas Equalização:

[![N|Solid](https://docs.google.com/uc?id=1KcbUVOg2rBt0wR1ENQJDB-tUziKDGX5x)](https://github.com/neoneper/ClassProg3D1/wiki/Histograma-Equalization)


# PROCESSAMENTO DE IMAGENS - EXERCÍCIOS
- [Kernel de Suavisação](https://github.com/neoneper/ClassProg3D1/wiki/Kernel-Smooth)
- [Kernel de Bordas](https://github.com/neoneper/ClassProg3D1/wiki/Kernel-Border)
- [Kernel de Nitidez](https://github.com/neoneper/ClassProg3D1/wiki/Kernel-Nitidez)
- [HSV (Hue, Saturation, Light)](https://github.com/neoneper/ClassProg3D1/wiki/HSV)
- Erode and Dilate Operation
- Open and Close Operation
- Border dectection with Dilate and Subtraction
- [Histograma (Quantitativo, Acumulativo, Normalizdo)](https://github.com/neoneper/ClassProg3D1/wiki/Histograma)
- [Histograma Grafico2D](https://github.com/neoneper/ClassProg3D1/wiki/Histograma-Graphics)
- [Histograma Equalização de imagens](https://github.com/neoneper/ClassProg3D1/wiki/Histograma-Equalization)
# DESAFIOS:
- Desafio1 -
- Desafio2 -
- Desafio3 -
- [Desafio4 - Histograma em imagens coloridas usando Brightness e Saturate](https://github.com/neoneper/ClassProg3D1/wiki/Histograma-Equalization-Color)
# A BIBLIOTECA:
Conheça os objetos criados para trabalhar com professamento de imagens:
# [Extensions](https://github.com/neoneper/ClassProg3D1/tree/master/src/br/pucpr/Extensions)
Contém todas as classes da biblioteca para trabalhar com manipulação de BufferedImages e objetos do tipo Color.

* [ColorExtensions](https://github.com/neoneper/ClassProg3D1/blob/master/src/br/pucpr/Extensions/ColorExtensions.java): Contém métodos estáticos uteis para trabalhar com Objetos do tipo Color.
 * - Calculo de distancia entre dois Objectos Color
 * - Conversão de Cor em Formato Hexadecimal para Objeto Color e vice versa.
 * - Pesquisa da cor mais próxima a partir de uma lista de cores em formato Hexadecimal
 * - Manipulação do HSV da cor com conversão automática para objeto
 
* [BufferedImageOperation](https://github.com/neoneper/ClassProg3D1/blob/master/src/br/pucpr/Extensions/BufferedImageOperation.java)
 * - Permite trabalhar com diversas operações de manipulação de imagens e efeitos visuais.
 * - Convolução de Imagens utilizando Filtros e Kernel
 * - HSV

* [BufferedImageOperationKernel](https://github.com/neoneper/ClassProg3D1/blob/master/src/br/pucpr/Extensions/BufferedImageOperationKernel.java)
* - Esta classe contém diversos arrays 2D para serem utilizados como Kernel em Convolução de imagens automaticamente.

* [BufferedImageOperationType](https://github.com/neoneper/ClassProg3D1/blob/master/src/br/pucpr/Extensions/BufferedImageOperationType.java)
* - Enum contendo todos as matrizes de kernel cadastradas em _BufferedImageOperationKernel_.

* [BufferedImageEqualizeType](https://github.com/neoneper/ClassProg3D1/blob/master/src/br/pucpr/Extensions/BufferedImageEqualizeType.java)
* - Enum contendo todas as equalizações possiveis para geração de Histogramas.
Tenha em mente que existem 2 Metodos possiveis que não estão listados nestes Enumeradoes sendo eles as equalizações de RGB e HSV. Ambos são metodos experimentais. EqualizeHSV(...) e EqualizeRGB(...)
* [BufferedImageRect](https://github.com/neoneper/ClassProg3D1/blob/master/src/br/pucpr/Extensions/BufferedImageRect.java)
* - Permite setar um BOUNDING para trabalhar com BufferedImage. Muito util para trabalhar com Graphics2D

# ONDE ENCONTRO AS ATIVIDADES E EXERCÍCIOS?
Todas as atividades feitas em sala de aula bem como passadas pelo BlackBoard estão sendo armazenadas em:
[https://github.com/neoneper/ClassProg3D1/tree/master/src/br/pucpr](https://github.com/neoneper/ClassProg3D1/tree/master/src/br/pucpr)

# FEATURES:

[HSV](https://github.com/neoneper/ClassProg3D1/wiki/HSV)
Manipulando Hue, Saturation e Brightness da imagem;

[PIXALATE](https://github.com/neoneper/ClassProg3D1/wiki/Image-Pixalate)
Aplicando Pixelate á Imagem.

[BORDERS](https://github.com/neoneper/ClassProg3D1/wiki/Kernel-Border)
Usando Kernel de bordas.

[SHARP](https://github.com/neoneper/ClassProg3D1/wiki/Kernel-Nitidez)
Usando Kernel de nitidez

[BLUR](https://github.com/neoneper/ClassProg3D1/wiki/Kernel-Smooth)
Usando Kernel de Suavização

[PALLET](https://github.com/neoneper/ClassProg3D1/wiki/Pallet-Colors-Change)
Modificando Paletas de cores

[HISTOGRAMA](https://github.com/neoneper/ClassProg3D1/wiki/Histograma)
Gerando Histogramas:

[EQUALIZAÇÃO DE HISTOGRMAS](https://github.com/neoneper/ClassProg3D1/wiki/Histograma-Equalization)
Equalizando Histogramas:

[GRAFICO DE HISTOGRAMAS](https://github.com/neoneper/ClassProg3D1/wiki/Histograma-Graphics)
Gerando gráficos de histogramas:

# PROG3D!

[Atividade1]: <https://github.com/neoneper/ClassProg3D1/wiki/Pallet-Colors-Change> 
[Atividade2]: <https://github.com/neoneper/ClassProg3D1/wiki/Image-Pixalate>
[Atividade3]: <https://github.com/neoneper/ClassProg3D1/wiki/Histograma-Equalization>
[kborder]: <https://github.com/neoneper/ClassProg3D1/wiki/Kernel-Border>
[ksmooth]:<https://github.com/neoneper/ClassProg3D1/wiki/Kernel-Smooth>
[ksharp]:<https://github.com/neoneper/ClassProg3D1/wiki/Kernel-Nitidez>

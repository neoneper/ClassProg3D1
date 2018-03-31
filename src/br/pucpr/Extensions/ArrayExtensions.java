package br.pucpr.Extensions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;

/**
 * Created by devsecond on 30/03/2018.
 */
public class ArrayExtensions {


    public static int GetFirsNonZero(int[] intArray)
    {
       int result = 0;

        for (int a : intArray)
        {
            if (a > 0)
            {
                result = a;
                break;
            }
        }

        return result;
    }
    public static int GetMinValue(int[] intArray) {

        int min = Integer.MAX_VALUE;

        for (int a : intArray)
        {
            if (a > 0)
            {
                if (a < min)
                {
                    min = a;
                }
            }
        }

        return min;
    }

    public static int GetMaxValue(int[] intArray) {
        IntSummaryStatistics stat = Arrays.stream(intArray).summaryStatistics();
        int max = stat.getMax();
        return max;
    }

    /**
     * Remove todos os elementos com valor zero do array, retornando um array novo array sem
     * estes elementos
     *
     * @param array
     * @return Arrau com todos os valores do array original menos os com valroes zero
     */
    public static int[] RemoveZero(int[] array) {
        List<Integer> intList = new ArrayList<Integer>();

        for (int a : array) {
            if (a > 0)
                intList.add(a);
        }

        int[] resultArray = new int[intList.size()];
        resultArray = intList.stream().mapToInt(i -> i).toArray();

        return resultArray;
    }

    /**
     * Conta quantos vezes um determinado valor se repete na lista
     *
     * @param intArray Lista contendo os valores para pesquisa
     * @param element  O valor que deve ser pesquisado
     * @return a quantidade de vezes que o valor pesquisado é repetido na lista
     */
    public static int CountElement(int[] intArray, int element) {
        int count = 0;
        for (int a : intArray) {
            if (a == element)
                count++;
        }

        return count;
    }
}

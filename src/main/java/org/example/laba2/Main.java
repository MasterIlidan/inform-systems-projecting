package org.example.laba2;

import org.example.utils.JSONReader;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    HashMap<Integer, Integer> errorsAndIntervals;

    int xi;

    /*    Ме́тод после́довательных приближе́ний (метод повторных подстановок, метод простой итерации),
    один из общих методов приближённого решения уравнений. В ряде случаев хорошая сходимость построенных
     этим методом приближений позволяет применять его в практике вычислений.*/
    Main() {
        try {
            errorsAndIntervals = JSONReader.getDataFromJSON("errors.json");
        } catch (IOException e) {
            System.out.println("IO Exception:" + e.getMessage());
            throw new RuntimeException(e);
        } catch (ParseException e) {
            System.out.println("ParseException:" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        int[] ints = new int[26];

        for (int i = 0; i < ints.length; i++) {
            ints[i] = i + 1;
        }


    }

    public int getGeneralCountOfErrors() {
        ArrayList<Integer> iValues = new ArrayList<>(errorsAndIntervals.keySet()); //все значения i

        double resultLeft; //результат левой части уравнения
        double resultRight; //результат правой части уравнения
        double epsilon = 0.1; //задаваемая максимальная погрешность
        int b = iValues.stream().max(Integer::compareTo).get() + 1;

        do {
            System.out.println("Current b is " + b);
            resultLeft = 0;
            resultRight = 0;

            resultRight = (double) (26 * 250) / ((b + 1) * 250 - 4258);
            //i номер ошибки
            for (int i : iValues) {
                resultLeft += (double) 1 / (b - i + 1);
            }

            b++;

        } while ((resultLeft - resultRight) > epsilon);
        System.out.println("B = " + b);
        return b;
    }

    /**
     * @param iValues все возможные варианты i
     * @return результат Σxi
     */
    public int getSigmaResult(ArrayList<Integer> iValues) {
        int result = 0;
        for (Integer i : iValues) {
            result += errorsAndIntervals.get(i);
        }
        return result;
    }

    /**
     *
     * @param n
     * количество найденных ошибок
     * @param xi
     * @return
     * интервал времени перед i-ой ошибкой
     * результат Σ(i*xi)
     */
/*    public int getSigmaMultiplyIResult(int n, int xi) {

    }*/

}

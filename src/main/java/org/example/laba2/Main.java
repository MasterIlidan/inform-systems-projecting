package org.example.laba2;

import org.example.utils.JSONReader;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Logger;


public class Main {
    HashMap<Integer, Integer> errorsAndIntervals;
    Logger logger = Logger.getLogger(Main.class.getName());

    /*Ме́тод после́довательных приближе́ний (метод повторных подстановок, метод простой итерации),
    один из общих методов приближённого решения уравнений. В ряде случаев хорошая сходимость построенных
     этим методом приближений позволяет применять его в практике вычислений.
     https://bigenc.ru/c/metod-posledovatel-nykh-priblizhenii-beeb45
     */
    Main(String filename) {
        try {
            errorsAndIntervals = JSONReader.getDataFromJSON(filename);
        } catch (IOException e) {
            System.out.println("IO Exception:" + e.getMessage());
            throw new RuntimeException(e);
        } catch (ParseException e) {
            System.out.println("ParseException:" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String filename = "";

        for (String arg : args) { //передача пути до файла в параметраз запуска
            if (arg.equals("errors.json")) {
                filename = arg;
            }
        }

        if (filename.isEmpty()) { //если в аргументах не нашлось пути до файла, то ввод пользователем
            System.out.println("""
        Enter path to JSON data. examples:
        errors.json
        ./folder/errors.json
        C:/folder/errors.json
        """);
            Scanner scanner = new Scanner(System.in);
            filename = scanner.nextLine();
        }
        Main main = new Main(filename);
        int b = main.getGeneralCountOfErrors();
        System.out.println("b = " + b);
    }

    public int getGeneralCountOfErrors() {
        ArrayList<Integer> iValues = new ArrayList<>(errorsAndIntervals.keySet()); //все значения i

        double resultLeft; //результат левой части уравнения
        double resultRight; //результат правой части уравнения
        double epsilon = 0.01; //задаваемая максимальная погрешность
        int b = iValues.stream().max(Integer::compareTo).get() + 1;

        do {
            logger.fine("Current b is " + b);
            resultLeft = 0;
            resultRight = 0;

            resultRight = (double) (26 * getSigmaResult(iValues)) / ((b + 1) * getSigmaResult(iValues) - getSigmaMultiplyIResult(iValues));
            //i номер ошибки
            for (int i : iValues) {
                resultLeft += (double) 1 / (b - i + 1);
            }

            b++;

        } while ((resultLeft - resultRight) > epsilon);
        logger.info("B = %d".formatted(b));
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
        logger.fine("getSigmaResult result = %d".formatted(result));
        return result;
    }

    /**
     *
     * @param iValues
     * все возможные варианты i
     * @return
     * интервал времени перед i-ой ошибкой
     * результат Σ(i*xi)
     */
    public int getSigmaMultiplyIResult(ArrayList<Integer> iValues) {
        int result = 0;
        for (Integer i : iValues) {
            result += errorsAndIntervals.get(i) * i;
        }
        logger.fine("getSigmaMultiplyIResult result = %d".formatted(result));
        return result;
    }

}

package org.example.laba3;

import org.example.utils.JSONReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Main {

    JSONObject root;

    public static void main(String[] args) {
        String filename = "";

        if (args.length > 0) {
            //передача пути до файла в параметраз запуска
            filename = args[0];
        } else {
            System.out.println("""
                    Usage: Laba3 <filename.json>
                    Examples:
                    targetParams.json
                    C:\\targetParams.json
                    """);
            System.exit(1);
        }

        Main main = new Main(filename);

        int allParams =  main.getInternalAndExternalParams();
        System.out.printf("Count of operands: %d\n", allParams);
        double programVolume = main.getPotentialProgramVolume(allParams);
        System.out.printf("Potential program Volume: %.4f\n", programVolume);
        double potentialErrorCount = main.getPotentialErrorCount(programVolume);
        System.out.println("Potential Error Count: " + potentialErrorCount);
    }

    Main(String filename) {
        try {
            root = JSONReader.getDataFromJSONLaba3(filename);
        } catch (IOException e) {
            System.out.println("IO Exception:" + e.getMessage());
            throw new RuntimeException(e);
        } catch (ParseException e) {
            System.out.println("ParseException:" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param minNumOfOperands
     * минимальное число различных операндов, получаемый в getInternalAndExternalParams
     * @return
     * потенциальный объем программы V*
     */
    public double getPotentialProgramVolume(int minNumOfOperands) {
        return (minNumOfOperands + 2) * Math.log(minNumOfOperands + 2);
    }

    /**
     * расчет ведется из данных из таблицы входных данных:
     * (<Число одновременно сопровождаемых целей> * <Количество измерений каждого отслеживаемого параметра>
     *     * <Количество отслеживаемых параметров>) + (<Число одновременно сопровождаемых целей> * <Количество рассчитываемых
     * параметров по каждой цели>)
     * @return
     * минимальное число различных операндов, в роли которого обычно выступает
     * число независимых входных и выходных параметров;
     *
     */
    public int getInternalAndExternalParams() {
        long result = 1;
        result *= (Long) root.get("countOfTargets") *
                (Long) root.get("measuresForEveryTrackedParams") *
                (Long) root.get("trackedParamsForEveryTarget");

        result += (Long) root.get("calculatedParamsForEveryTarget") * (Long) root.get("countOfTargets");
        return (int) result;
    }
    public double getPotentialErrorCount(double programVolume){
        if (root.get("languageLevel") == null) {
            throw new RuntimeException("Language Level not found in JSON");
        }
        double languageLevel = (Double)root.get("languageLevel");
        return Math.pow(programVolume, 2)/ (3000 * languageLevel);
    }

}

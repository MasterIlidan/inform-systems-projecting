package org.example.laba3;

import org.example.utils.JSONReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

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

        int allParams = main.getInternalAndExternalParams();
        System.out.printf("Count of operands: %d\n", allParams);

        double programVolume = main.getPotentialProgramVolume(allParams);
        System.out.printf("Potential program Volume: %.4f\n", programVolume);

        double potentialErrorCount = main.getPotentialErrorCount(programVolume);
        System.out.println("Potential Error Count: " + potentialErrorCount);


        /*------------ЗАДАНИЕ 2-----------------*/


        System.out.println("\nЗадание №2");

        int countOfModules = main.getCountOfModules(allParams);
        System.out.println("Число модулей: " + countOfModules);

        int countOfLevels = main.getCountOfLevels(allParams);
        System.out.println("Число уровней: " + countOfLevels);

        int lengthOfProgram = main.getLengthOfProgram(countOfModules);
        System.out.println("Длинна программы: " + lengthOfProgram);

        int volumeOfProgram = main.getVolumeOfProgram(countOfModules);
        System.out.println("Объем прогрммного обеспечения: " + volumeOfProgram);

        int assemblerCommandsCount = main.getAssemblerCommandsCount(lengthOfProgram);
        System.out.println("Количество команд ассемблера: " + assemblerCommandsCount);

        int countOfProgrammers = 5;
        int performancePerProgrammer = 20;
        int programCalendarTime = main.getProgramCalendarTime(lengthOfProgram, countOfProgrammers, performancePerProgrammer);
        System.out.println("Время программирования (дни): " + programCalendarTime);

        int potentialErrorsCount = main.getPotentialErrorsCount(volumeOfProgram);
        System.out.println("Потенциальное количество ошибок: " + potentialErrorsCount);

        int programReliability = main.getProgramReliability(programCalendarTime, potentialErrorsCount);
        System.out.println("Начальная надежность программного обеспечения: " + programReliability);


        /*------------ЗАДАНИЕ 3-----------------*/


        System.out.println();

        int startRating = ((Long) main.root.get("startedRating")).intValue();
        int countOfPrograms = ((Long) main.root.get("countOfProgramsInPeriod")).intValue();
        ArrayList<Integer> volume = new ArrayList<>();
        for (Object value : (JSONArray) main.root.get("volumeOfPrograms")) {
            volume.add(((Long) value).intValue());
        }
        ArrayList<Integer> countOfErrors = new ArrayList<>();
        for (Object value : (JSONArray) main.root.get("countOfErrors")) {
            countOfErrors.add(((Long) value).intValue());
        }

        double prevRating = main.getRatingByIteration(startRating, volume.get(0), countOfErrors.get(0), 1);
        System.out.printf("Count of program %d rating %f\n", 1, prevRating);
        for (int i = 1; i < countOfPrograms; i++) {
            prevRating = main.getRatingByIteration(prevRating, volume.get(i), countOfErrors.get(i), 1);
            System.out.printf("Count of program %d rating %f\n", i + 1, prevRating);
        }

        prevRating = main.getRatingByIteration(startRating, volume.get(0), countOfErrors.get(0), 2);
        System.out.printf("Count of program %d rating %f\n", 1, prevRating);
        for (int i = 1; i < countOfPrograms; i++) {
            prevRating = main.getRatingByIteration(prevRating, volume.get(i), countOfErrors.get(i), 2);
            System.out.printf("Count of program %d rating %f\n", i + 1, prevRating);
        }

        prevRating = main.getRatingByIteration(startRating, volume.get(0), countOfErrors.get(0), 3);
        System.out.printf("Count of program %d rating %f\n", 1, prevRating);
        for (int i = 1; i < countOfPrograms; i++) {
            prevRating = main.getRatingByIteration(prevRating, volume.get(i), countOfErrors.get(i), 3);
            System.out.printf("Count of program %d rating %f\n", i + 1, prevRating);
        }

        int estimatedSizeOfProgram = ((Long) main.root.get("estimatedSizeOfProgram")).intValue();
        for (int i = 1; i <= 3; i++) {
            double expectedErrors = main.getExpectedErrors(startRating,
                    estimatedSizeOfProgram,
                    i);
            System.out.printf("Expected Errors (type %d): %.4f\n", i, expectedErrors);
        }

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
     * @param minNumOfOperands минимальное число различных операндов, получаемый в getInternalAndExternalParams
     * @return потенциальный объем программы V*
     */
    public double getPotentialProgramVolume(int minNumOfOperands) {
        return (minNumOfOperands + 2) * Math.log(minNumOfOperands + 2);
    }

    /**
     * расчет ведется из данных из таблицы входных данных:
     * (<Число одновременно сопровождаемых целей> * <Количество измерений каждого отслеживаемого параметра>
     * * <Количество отслеживаемых параметров>) + (<Число одновременно сопровождаемых целей> * <Количество рассчитываемых
     * параметров по каждой цели>)
     *
     * @return минимальное число различных операндов, в роли которого обычно выступает
     * число независимых входных и выходных параметров;
     */
    public int getInternalAndExternalParams() {
        long result = 1;
        result *= (Long) root.get("countOfTargets") *
                (Long) root.get("measuresForEveryTrackedParams") *
                (Long) root.get("trackedParamsForEveryTarget");

        result += (Long) root.get("calculatedParamsForEveryTarget") * (Long) root.get("countOfTargets");
        return (int) result;
    }

    /**
     * <потенциальный объем программы> и <уровень языка>
     *
     * @param programVolume результат метода getPotentialProgramVolume
     * @return Потенциальное число ошибок
     */
    public double getPotentialErrorCount(double programVolume) {
        if (root.get("languageLevel") == null) {
            throw new RuntimeException("Language Level not found in JSON");
        }
        double languageLevel = (Double) root.get("languageLevel");
        return Math.pow(programVolume, 2) / (3000 * languageLevel);
    }

    public int getCountOfModules(int allParams) {
        double countOfModules = (double) allParams / 8;
        if (countOfModules > 8) {
            countOfModules = ((double) allParams / 8) + ((double) allParams / (Math.pow(8, 2)));
        } else {
            return (int) Math.round(countOfModules);
        }
        return (int) Math.round(countOfModules);
    }

    public int getCountOfLevels(int allParams) {
        return (int) Math.round((Math.log(allParams) / 3) + 1);
    }

    public int getLengthOfProgram(double countOfModules) {
        return (int)((220 * countOfModules) + (countOfModules * Math.log(countOfModules)));
    }

    public int getVolumeOfProgram(double programModules) {
        return (int) (programModules * 220 * (Math.log(48)));
    }

    public int getAssemblerCommandsCount(double programLength) {
        return (int) ((3 * programLength) / 8);
    }

    public int getProgramCalendarTime(double programLength, int countOfProgrammers, int performancePerProgrammer) {
        return (int) ((3 * programLength) / (8 * countOfProgrammers * performancePerProgrammer));
    }

    public int getPotentialErrorsCount(double programVolume) {
        return (int) ( programVolume / 3000);
    }

    public int getProgramReliability(double x, double potentialErrorsCount) {
        return (int) ( x * 8 / 2 * Math.log(potentialErrorsCount));
    }

    public double getRatingByIteration(double rating, double volume, double errorCount, int factorType) {
        switch (factorType) {
            case 1 -> {
                return rating * (1 + (double) 1 / 1000 * (volume - (errorCount / getFactorA((Double) root.get("languageLevel"), rating))));
            }
            case 2 -> {
                return rating * (1 + (double) 1 / 1000 * (volume - (errorCount / getFactorB((Double) root.get("languageLevel"), rating))));
            }
            case 3 -> {
                return rating * (1 + (double) 1 / 1000 * (volume - (errorCount / getFactorC((Double) root.get("languageLevel"), rating))));
            }
            default -> throw new RuntimeException("Unsupported factor type: " + factorType);
        }
    }

    public double getFactorA(double languageLevel, double previousRating) {
        return 1 / (languageLevel + previousRating);
    }

    public double getFactorB(double languageLevel, double previousRating) {
        return 1 / (languageLevel * previousRating);
    }

    public double getFactorC(double languageLevel, double previousRating) {
        return 1 / languageLevel + 1 / previousRating;
    }

    public double getExpectedErrors(double lastRating, int expectedProgramVolume, int factorType) {
        switch (factorType) {
            case 1 -> {
                return getFactorA((Double) root.get("languageLevel"), lastRating) * expectedProgramVolume;
            }
            case 2 -> {
                return getFactorB((Double) root.get("languageLevel"), lastRating) * expectedProgramVolume;
            }
            case 3 -> {
                return getFactorC((Double) root.get("languageLevel"), lastRating) * expectedProgramVolume;
            }
            default -> throw new RuntimeException("Unsupported factor type: " + factorType);
        }
    }
}

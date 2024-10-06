package org.example.laba5;

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

        double troubleProofChance = main.getTroubleProofChance();
        System.out.printf("H0401 Trouble proof chance is %.5f%n", (troubleProofChance));
        double averageTimeRecover = main.getAvgTimeRecoverMark();
        System.out.printf("H0501 Average time recover is %.5f%n", (averageTimeRecover));
        double durationOfChange = main.getDurationOfChangeMark();
        System.out.printf("H0502 Duration of change mark is %.5f%n", durationOfChange);
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

    public double getTroubleProofChance() {
        int countOfFailures = ((Long) root.get("countOfFailures")).intValue();
        int countOfExperiments = ((Long) root.get("countOfExperiments")).intValue();
        return 1 - (double) countOfFailures / countOfExperiments;
    }

    public double getAvgTimeRecoverMark() {
        double averageTimeRecover = getAverageTimeRecover(100);
        double allowedRecoveryTime = (Double) root.get("allowedRecoveryTime");
        if (averageTimeRecover <= allowedRecoveryTime) {
            return 1;
        } else {
            return allowedRecoveryTime / averageTimeRecover;
        }
    }

    private double getAverageTimeRecover(int steps) {
        double minInterval = (Double) ((JSONArray) root.get("avgRecoveryTime")).get(0);
        double maxInterval = (Double) ((JSONArray) root.get("avgRecoveryTime")).get(1);
        double step = getStepValue(minInterval, maxInterval, steps);
        double sum = 0;
        for (double i = minInterval; i <= maxInterval; i += step) {
            sum += i;
        }
        return sum / steps;
    }

    private double getStepValue(double minInterval, double maxInterval, int steps) {
        return Math.abs(minInterval - maxInterval) / steps;
    }

    public double getDurationOfChangeMark() {
        ArrayList<Double> durationOfChange = getDurationOfChange(200);
        double sum = 0;
        for (double num : durationOfChange) {
            sum += num;
        }
        return sum / durationOfChange.size();


    }
    public ArrayList<Double> getDurationOfChange(int steps) {
        long minInterval = (Long) ((JSONArray) root.get("factDurationOfChange")).get(0);
        long maxInterval = (Long) ((JSONArray) root.get("factDurationOfChange")).get(1);
        long allowedDurationOfChange = (Long) root.get("allowedDurationOfChange");
        double step = getStepValue(minInterval, maxInterval, steps);
        ArrayList<Double> durationOfChange = new ArrayList<>();
        for (double i = minInterval; i < maxInterval; i += step) {
            if (i <= allowedDurationOfChange) {
                durationOfChange.add(1d);
            } else {
                durationOfChange.add(allowedDurationOfChange / i);
            }
        }
        return durationOfChange;
    }
}

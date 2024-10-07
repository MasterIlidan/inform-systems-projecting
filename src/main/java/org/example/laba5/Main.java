package org.example.laba5;

import org.example.utils.JSONReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    JSONObject root;

    double basicCriteria = 0.95d;

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

        List<Double> metricsM4 = new ArrayList<>();
        List<Double> metricsM5 = new ArrayList<>();

        double troubleProofChance = main.getTroubleProofChance();
        System.out.printf("H0401 Trouble proof chance is %.5f%n", (troubleProofChance));
        metricsM4.add(troubleProofChance);

        double averageTimeRecover = main.getAvgTimeRecoverMark();
        System.out.printf("H0501 Average time recover is %.5f%n", (averageTimeRecover));
        metricsM5.add(averageTimeRecover);
        double durationOfChange = main.getDurationOfChangeMark();
        System.out.printf("H0502 Duration of change mark is %.5f%n", durationOfChange);
        metricsM4.add(durationOfChange);
        double m4 = main.calculateMetriks(metricsM4);
        System.out.printf("M4 метрика: %.5f%n", m4);
        double m5 = main.calculateMetriks(metricsM5);
        System.out.printf("M5 метрика: %.5f%n", m5);

        double absoluteCriteriaFactor = main.getAbsolutCriteriaFactor(m4, m5);
        System.out.printf("Абсолютные показатели криетериев: %.5f%n", absoluteCriteriaFactor);

        double relativeCriteriaFactor = main.getRelativeCriteriaFactor(absoluteCriteriaFactor);
        System.out.printf("Относительный показатель криетрия: %.5f%n", relativeCriteriaFactor);

        double qualityFactor = main.getQualityFactor(absoluteCriteriaFactor, relativeCriteriaFactor);
        System.out.printf("Фактор качества: %.5f%n", qualityFactor);
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
        double sum = 0;
        for (double i = 0; i < steps; i++) {
            sum += new Random().nextInt(steps+1) * (maxInterval - minInterval) / steps + minInterval;
        }
        return sum / steps;
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
        ArrayList<Double> durationOfChange = new ArrayList<>();
        for (double i = 0; i < steps; i++) {
            double _Tpi = (double) (new Random().nextInt(steps + 1) * (maxInterval - minInterval)) / steps + minInterval;
            if (_Tpi <= allowedDurationOfChange) {
                durationOfChange.add(1d);
            } else {
                durationOfChange.add(allowedDurationOfChange / i);
            }
        }
        return durationOfChange;
    }

    public double calculateMetriks(List<Double> _metriksData){
        //Получим сумму метрик
        double _sumM = 0;
        for (double _mdata : _metriksData) {
            _sumM += _mdata;
        }
        //Разделим на количесво метрик
        return _sumM/_metriksData.size();
    }

    private double getAbsolutCriteriaFactor(double m4, double m5) {
        return (m4 + m5) / 2;
    }

    private double getRelativeCriteriaFactor(double _absolutCriteria) {
        return _absolutCriteria / basicCriteria;
    }

    private double getQualityFactor(double _absolutCriteria, double _relativeCriteria) {
        return (_absolutCriteria + _relativeCriteria + basicCriteria) / 3;
    }

}

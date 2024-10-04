package org.example.laba3z2;

public class laba3z2 {

      static double countModules(int x) {
        double countmodules = (double) x / 8;
        if (countmodules > 8){
            countmodules = ( (double) x / 8 ) + ((double) x / (8 * 8));
        }
        else {
            return countmodules;
        }
        return countmodules;
    }

    static double countLevel (int x){
        double countlvel = (Math.log(x) / Math.log(2)) / 3 + 1;
        return countlvel;
    }

    static double lenghtProgramm(double x)    {
        double lenghtprogramm = (220 * x) + (x * Math.log(x) / Math.log(2));
        return lenghtprogramm;
    }

    static double obemProgramm (double x){
        double obemprogramm = x * 220 * (Math.log(48) / Math.log(2));
        return obemprogramm;
    }

    static double countAssamb (double x){
        double countassamb = (3*x) / 8;
        return countassamb;
    }
    static double countTime (double x, int y, int z){
        double counttime = (3 * x) / (8 * y * z);
        return counttime;
    }

    static double countError (double x){
        double counterror =  x / 3000;
        return counterror;
    }

    static double nadeznostProg (double x, double y){
        double nadeznostprog = x * 8 / 2 * Math.log(y);
        return nadeznostprog;
    }

    public static void main(String[] args) {
        double lambda = 1.53;
        int c = 25;
        int p = 28;
        int opt = 8;
        int rpp = 3;
        int in = c * p * opt;
        int out = c * rpp;
        int n2 = in + out;

        System.out.println("\nЗадание №2");
        double result1 = countModules(n2);
        System.out.println("Число модулей: " + result1);
        double result2 = countLevel(n2);
        System.out.println("Число уровней: " + result2);
        double result3 = lenghtProgramm(result1);
        System.out.println("Длинна программы: " + result3);
        double result4 = obemProgramm(result1);
        System.out.println("Объем прогрммного обеспечения: " + result4);
        double result5 = countAssamb(result3);
        System.out.println("Количество команд ассемблера: " + result5);
        int chisloprogramistov = 5;
        int proizvoditelnost = 20;
        double result6 = countTime(result3, chisloprogramistov, proizvoditelnost);
        System.out.println("Время программирования (дни): " + result6);
        double result7 = countError(result4);
        System.out.println("Потенциальное количество ошибок: " + result7);
        double result8 = nadeznostProg(result6, result7);
        System.out.println("Начальная надежность программного обеспечения: " + result8);
    }
}

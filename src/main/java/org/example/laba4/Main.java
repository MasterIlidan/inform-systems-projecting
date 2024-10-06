package org.example.laba4;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    @SuppressFBWarnings("UP_UNUSED_PARAMETER")
    public static void testOperator(String[] args, boolean flag) {
        Number n;
        if (flag) {
            n = Integer.valueOf(1);
        } else
        {
            n = Double.valueOf(2.0);
        }
        System.out.println(n);
    }

    public static void testInteger(boolean flag1, boolean flag2) {
        String n;
        if( flag1 )
            n = String.valueOf(Integer.valueOf(1));
        else {
            if( flag2 )
                n = String.valueOf(Integer.valueOf(2));
            else {
                n = String.valueOf((Object) null);
            }
        }
        System.out.println(n);
    }

    static double[] vals = new double[] {1.0, 2.0, 3.0};
    @SuppressFBWarnings("USBR_UNNECESSARY_STORE_BEFORE_RETURN")
    static String getVal(int idx) {
        String str;
        str = String.valueOf((idx < 0 || idx >= vals.length) ? null : vals[idx]);
        return str;
    }

    private static final String Pattern = "yyyy-MM-dd HH:mm:ss";

    private static String getDate() {
        final DateFormat format = new SimpleDateFormat(Pattern);
        return format.format(new Date());
    }

    public static void main(String[] args) {
        testOperator(args, false);
        testInteger(false, false);
        System.out.println ( getVal(5));
        System.out.println (getDate());
        System.out.println(BigDecimal.valueOf(1.1));

        BigDecimal d1 = new BigDecimal("1.1");
        BigDecimal d2 = new BigDecimal("1.10");
        System.out.println(d1.compareTo(d2));

        System.out.printf("%s%n", "str#1");
        System.out.println("str#2");

    }
}

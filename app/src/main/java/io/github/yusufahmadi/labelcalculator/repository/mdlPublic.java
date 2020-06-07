package io.github.yusufahmadi.labelcalculator.repository;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.github.yusufahmadi.labelcalculator.MainActivity;

public class mdlPublic {
    //Activity
    public static int Activity_RibbonInput = 10001;
    public static int Activity_LabelInput = 10002;
    public static int Activity_TeffetaInput = 10003;
    public static int Activity_MasterBahanRibbon = 10004;
    public static int Activity_MasterBahanRibbonInput = 10005;
    //End Activity

    public static MainActivity mainActivity;

    public static long getDateDiff(String oldDate, String newDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", new DateFormatSymbols(Locale.US));
        try {
            return TimeUnit.DAYS.convert(format.parse(newDate).getTime() - format.parse(oldDate).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}

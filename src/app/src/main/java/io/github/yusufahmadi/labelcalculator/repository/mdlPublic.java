package io.github.yusufahmadi.labelcalculator.repository;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import io.github.yusufahmadi.labelcalculator.MainActivity;

public class mdlPublic {
    //Activity
    public static int Activity_RibbonInput = 10001;
    public static int Activity_LabelInput = 10002;
    public static int Activity_TeffetaInput = 10003;
    public static int Activity_MasterBahanRibbon = 10004;
    public static int Activity_MasterBahanLabel = 10005;
    public static int Activity_MasterTypeTaffeta = 10006;
    //End Activity

    public static MainActivity mainActivity;

    public static long getDateDiff(SimpleDateFormat format, String oldDate, String newDate) {
        try {
            return TimeUnit.DAYS.convert(format.parse(newDate).getTime() - format.parse(oldDate).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String PREFS_PASSCODE = "Key.Passcode";
    public static String PWD_RESET_PASSCODE = "P@a5sC0t";
    public static String PWD_DEFAULT_PASSCODE = "000000";
}

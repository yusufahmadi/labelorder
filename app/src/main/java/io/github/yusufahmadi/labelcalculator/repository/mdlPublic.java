package io.github.yusufahmadi.labelcalculator.repository;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.github.yusufahmadi.labelcalculator.MainActivity;
import io.github.yusufahmadi.labelcalculator.adapter.BahanAdapter;
import io.github.yusufahmadi.labelcalculator.model.Bahan;
import io.github.yusufahmadi.labelcalculator.model.PO;

public class mdlPublic {
    //Activity
    public static int Activity_RibbonInput = 10001;
    public static int Activity_LabelInput = 10002;
    //End Activity

    public static List<Bahan> ListBahan = new ArrayList<>();
    public static List<String> ListStrBahan = new ArrayList<>();

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

    private BahanAdapter SpinBahan;
    public static String getBahanByID(int id) {
        String rst="";
        for(Bahan bhn: ListBahan) {
            if(bhn.id==id) {
                //found it!
                rst=bhn.nama;
            }
        }
        return rst;
    }

    public static PO[] ListPO = new PO[0];
}

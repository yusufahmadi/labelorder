package io.github.yusufahmadi.labelorder.repository;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.github.yusufahmadi.labelorder.MainActivity;
import io.github.yusufahmadi.labelorder.adapter.BahanAdapter;
import io.github.yusufahmadi.labelorder.model.Bahan;
import io.github.yusufahmadi.labelorder.model.PO;

public class mdlPublic {

    public static Bahan[] ListBahan = new Bahan[0];
    public static List<String> ListStrBahan = new ArrayList<String>();

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
            if(bhn.getId()==id) {
                //found it!
                rst =bhn.getNama();
            }
        }
        return rst;
    }

    public static PO[] ListPO = new PO[0];
}

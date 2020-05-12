package io.github.yusufahmadi.labelorder.ui.dashboard;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.DecimalFormat;
import java.util.ArrayList;

import io.github.yusufahmadi.labelorder.R;
import io.github.yusufahmadi.labelorder.BahanAdapter;
import io.github.yusufahmadi.labelorder.Bahan;
import io.github.yusufahmadi.labelorder.mdlPublic;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    DecimalFormat n0 = new DecimalFormat("#,###,###");
    DecimalFormat n2 = new DecimalFormat("#,###,###.##");
    DecimalFormat n4 = new DecimalFormat("#,###,###.####");
    private BahanAdapter SpinBahan;
    private Context context;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
//        final TextView textView = root.findViewById(R.id.text_dashboard);
//        dashboardViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        final EditText editTextLebar = root.findViewById(R.id.editTextLebar);
        final EditText editTextPanjang = root.findViewById(R.id.editTextPanjang);
        final MaterialSpinner spinner_bahann = root.findViewById(R.id.spinner_bahan);
        final TextView textViewModalPerM2 = root.findViewById(R.id.textViewHargaM2);

        final EditText editTextModal = root.findViewById(R.id.editTextModal);
        final TextView textView10Persen = root.findViewById(R.id.textView10Persen);
        final TextView textView15Persen = root.findViewById(R.id.textView15Persen);
        final TextView textView25Persen = root.findViewById(R.id.textView25Persen);
        final TextView textView35Persen = root.findViewById(R.id.textView35Persen);
        final TextView textView45Persen = root.findViewById(R.id.textView45Persen);
        final TextView textView55Persen = root.findViewById(R.id.textView55Persen);
        final TextView textView65Persen = root.findViewById(R.id.textView65Persen);
        final TextView textView75Persen = root.findViewById(R.id.textView75Persen);

        final EditText editTextQty = root.findViewById(R.id.editTextQty);
        final EditText editTextJualRoll = root.findViewById(R.id.editTextJualRoll);
        final EditText editTextJumlahProfitKotor = root.findViewById(R.id.editTextJumlahProfitKotor);

        final EditText editTextTransport = root.findViewById(R.id.editTextTransport);
        final EditText editTextKomisiSalesProsen = root.findViewById(R.id.editTextKomisiSalesProsen);
        final EditText editTextKomisiSalesNominal = root.findViewById(R.id.editTextKomisiSalesNominal);
        final EditText editTextNetProfit = root.findViewById(R.id.editTextNetProfit);

        editTextLebar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    try
                    {
                        Double HargaModal=0.0;
                        textViewModalPerM2.setText(n0.format(Harga));
                        if (Harga>0 ) {
                            if (editTextLebar.getText().length() >= 1 && editTextPanjang.getText().length() >= 1) {
                                //'=(Lebar/1000)*Panjang*Harga\n'
                                HargaModal = (Double.valueOf(editTextLebar.getText().toString()) / 1000) * Double.valueOf(editTextPanjang.getText().toString()) * Harga;
                                editTextModal.setText(n0.format(HargaModal));
                                textView10Persen.setText(n0.format(HargaModal));
                                textView10Persen.setText("10% => " + n0.format(HargaModal+(HargaModal*10/100)));
                                textView15Persen.setText("15% => " +n0.format(HargaModal+(HargaModal*15/100)));
                                textView25Persen.setText("25% => " +n0.format(HargaModal+(HargaModal*25/100)));
                                textView35Persen.setText("35% => " +n0.format(HargaModal+(HargaModal*35/100)));
                                textView45Persen.setText("45% => " +n0.format(HargaModal+(HargaModal*45/100)));
                                textView55Persen.setText("55% => " +n0.format(HargaModal+(HargaModal*55/100)));
                                textView65Persen.setText("65% => " +n0.format(HargaModal+(HargaModal*65/100)));
                                textView75Persen.setText("75% => " +n0.format(HargaModal+(HargaModal*75/100)));
                            }
                            else {
                                editTextModal.setText("0");
                                textView10Persen.setText("10% => 0");
                                textView15Persen.setText("15% => 0");
                                textView25Persen.setText("25% => 0");
                                textView35Persen.setText("35% => 0");
                                textView45Persen.setText("45% => 0");
                                textView55Persen.setText("55% => 0");
                                textView65Persen.setText("65% => 0");
                                textView75Persen.setText("75% => 0");
                            }
                        }
                        else {
                            editTextModal.setText("0");
                            textView10Persen.setText("10% => 0");
                            textView15Persen.setText("15% => 0");
                            textView25Persen.setText("25% => 0");
                            textView35Persen.setText("35% => 0");
                            textView45Persen.setText("45% => 0");
                            textView55Persen.setText("55% => 0");
                            textView65Persen.setText("65% => 0");
                            textView75Persen.setText("75% => 0");
                        }
                    }catch (Exception ex)
                    {
                        Toast.makeText(context, "Error " + ex.toString(), Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }
        });
        editTextPanjang.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    try
                    {
                        Double HargaModal=0.0;
                        textViewModalPerM2.setText(n0.format(Harga));
                        if (Harga>0 ) {
                            if (editTextLebar.getText().length() >= 1 && editTextPanjang.getText().length() >= 1) {
                                //'=(Lebar/1000)*Panjang*Harga\n'
                                HargaModal = (Double.valueOf(editTextLebar.getText().toString()) / 1000) * Double.valueOf(editTextPanjang.getText().toString()) * Harga;
                                editTextModal.setText(n2.format(HargaModal));
                                textView10Persen.setText(n0.format(HargaModal));
                                textView10Persen.setText("10% => " + n0.format(HargaModal+(HargaModal*10/100)));
                                textView15Persen.setText("15% => " +n0.format(HargaModal+(HargaModal*15/100)));
                                textView25Persen.setText("25% => " +n0.format(HargaModal+(HargaModal*25/100)));
                                textView35Persen.setText("35% => " +n0.format(HargaModal+(HargaModal*35/100)));
                                textView45Persen.setText("45% => " +n0.format(HargaModal+(HargaModal*45/100)));
                                textView55Persen.setText("55% => " +n0.format(HargaModal+(HargaModal*55/100)));
                                textView65Persen.setText("65% => " +n0.format(HargaModal+(HargaModal*65/100)));
                                textView75Persen.setText("75% => " +n0.format(HargaModal+(HargaModal*75/100)));
                            }
                            else {
                                editTextModal.setText("0");
                                textView10Persen.setText("10% => 0");
                                textView15Persen.setText("15% => 0");
                                textView25Persen.setText("25% => 0");
                                textView35Persen.setText("35% => 0");
                                textView45Persen.setText("45% => 0");
                                textView55Persen.setText("55% => 0");
                                textView65Persen.setText("65% => 0");
                                textView75Persen.setText("75% => 0");
                            }
                        }
                        else {
                            editTextModal.setText("0");
                            textView10Persen.setText("10% => 0");
                            textView15Persen.setText("15% => 0");
                            textView25Persen.setText("25% => 0");
                            textView35Persen.setText("35% => 0");
                            textView45Persen.setText("45% => 0");
                            textView55Persen.setText("55% => 0");
                            textView65Persen.setText("65% => 0");
                            textView75Persen.setText("75% => 0");
                        }
                    }catch (Exception ex)
                    {
                        Toast.makeText(context, "Error " + ex.toString(), Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }
        });

        callData();
        SpinBahan  = new BahanAdapter(context,R.layout.spinner_item, mdlPublic.ListBahan);
//        SpinBahan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner_bahann.setAdapter(SpinBahan);

        spinner_bahann.setItems(mdlPublic.ListStrBahan);
        spinner_bahann.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                // Here you get the current item (a User object) that is selected by its position
                try
                {
                    Double HargaModal=0.0;
                    getBahan(position);
                    textViewModalPerM2.setText(n0.format(Harga));
                    if (Harga>0 ) {
                        if (editTextLebar.getText().length() >= 1 && editTextPanjang.getText().length() >= 1) {
                            //'=(Lebar/1000)*Panjang*Harga\n'
                            HargaModal = (Double.valueOf(editTextLebar.getText().toString()) / 1000) * Double.valueOf(editTextPanjang.getText().toString()) * Harga;
                            editTextModal.setText(n0.format(HargaModal));
                            textView10Persen.setText(n0.format(HargaModal));
                            textView10Persen.setText("10% => " + n0.format(HargaModal+(HargaModal*10/100)));
                            textView15Persen.setText("15% => " +n0.format(HargaModal+(HargaModal*15/100)));
                            textView25Persen.setText("25% => " +n0.format(HargaModal+(HargaModal*25/100)));
                            textView35Persen.setText("35% => " +n0.format(HargaModal+(HargaModal*35/100)));
                            textView45Persen.setText("45% => " +n0.format(HargaModal+(HargaModal*45/100)));
                            textView55Persen.setText("55% => " +n0.format(HargaModal+(HargaModal*55/100)));
                            textView65Persen.setText("65% => " +n0.format(HargaModal+(HargaModal*65/100)));
                            textView75Persen.setText("75% => " +n0.format(HargaModal+(HargaModal*75/100)));
                        }
                        else {
                            editTextModal.setText("0");
                            textView10Persen.setText("10% => 0");
                            textView15Persen.setText("15% => 0");
                            textView25Persen.setText("25% => 0");
                            textView35Persen.setText("35% => 0");
                            textView45Persen.setText("45% => 0");
                            textView55Persen.setText("55% => 0");
                            textView65Persen.setText("65% => 0");
                            textView75Persen.setText("75% => 0");
                        }
                    }
                    else {
                        editTextModal.setText("0");
                        textView10Persen.setText("10% => 0");
                        textView15Persen.setText("15% => 0");
                        textView25Persen.setText("25% => 0");
                        textView35Persen.setText("35% => 0");
                        textView45Persen.setText("45% => 0");
                        textView55Persen.setText("55% => 0");
                        textView65Persen.setText("65% => 0");
                        textView75Persen.setText("75% => 0");
                    }
                }catch (Exception ex)
                {
                    Toast.makeText(context, "Error " + ex.toString(), Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

        editTextQty.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    try
                    {
                        Double Jumlah=0.0;
                        if (editTextQty.getText().length() >= 1 && editTextJualRoll.getText().length() >= 1)
                        {
                            //'=(Lebar/1000)*Panjang*Harga\n'
                            Jumlah = (Double.valueOf(editTextJualRoll.getText().toString()) - n2.parse(editTextModal.getText().toString()).doubleValue())* Double.valueOf(editTextQty.getText().toString()) ;
                            editTextJumlahProfitKotor.setText(n0.format(Jumlah));

                            Double Komisi=0.0,KomisiPersen=0.0;
                            if (!TextUtils.isEmpty(editTextKomisiSalesProsen.getText().toString()) || !editTextKomisiSalesProsen.getText().toString().trim().equals(""))
                            {
                                if (Double.valueOf(editTextKomisiSalesProsen.getText().toString())<0 && Double.valueOf(editTextKomisiSalesProsen.getText().toString())>100)
                                {
                                    Toast.makeText(context, "% Komisi out of range (0-100)", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                else if (Double.valueOf(editTextKomisiSalesProsen.getText().toString())>0 )
                                {
                                    Komisi= Jumlah * n2.parse(editTextKomisiSalesProsen.getText().toString()).doubleValue()/100;
                                    editTextKomisiSalesNominal.setText(n0.format(Komisi));
                                }
                                else
                                {

                                }
                            }
                            else if (!TextUtils.isEmpty(editTextKomisiSalesNominal.getText().toString()) || !editTextKomisiSalesNominal.getText().toString().trim().equals(""))
                            {
                                if (Double.valueOf(editTextKomisiSalesNominal.getText().toString())>0 )
                                {
                                    KomisiPersen= Jumlah / (n2.parse(editTextKomisiSalesNominal.getText().toString()).doubleValue()*100);
                                    editTextKomisiSalesNominal.setText(n2.format(KomisiPersen));
                                }
                                else
                                {

                                }
                            }
                            else {
                                editTextKomisiSalesProsen.setText(n2.format(0.0));
                                editTextKomisiSalesNominal.setText(n0.format(0));
                            }

                            editTextNetProfit.setText(n0.format(Jumlah -
                                    Double.valueOf(editTextTransport.getText().toString()) -
                                    Double.valueOf(editTextKomisiSalesNominal.getText().toString())));

                        }
                        else {
                            editTextJumlahProfitKotor.setText("0");
                            editTextTransport.setText("0");
                            editTextKomisiSalesProsen.setText("0 ");
                            editTextKomisiSalesNominal.setText("0");
                            editTextNetProfit.setText("0");
                        }
                    }catch (Exception ex)
                    {
                        Toast.makeText(context, "Error " + ex.toString(), Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }
        });

        editTextJualRoll.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    try
                    {
                        Double Jumlah=0.0;
                        if (editTextQty.getText().length() >= 1 && editTextJualRoll.getText().length() >= 1)
                        {
                            //'=(Lebar/1000)*Panjang*Harga\n'
                            Jumlah = (Double.valueOf(editTextJualRoll.getText().toString()) - n2.parse(editTextModal.getText().toString()).doubleValue())* Double.valueOf(editTextQty.getText().toString()) ;
                            editTextJumlahProfitKotor.setText(n0.format(Jumlah));

                            Double Komisi=0.0,KomisiPersen=0.0;
                            if (!TextUtils.isEmpty(editTextKomisiSalesProsen.getText().toString()) || !editTextKomisiSalesProsen.getText().toString().trim().equals(""))
                            {
                                if (Double.valueOf(editTextKomisiSalesProsen.getText().toString())<0 && Double.valueOf(editTextKomisiSalesProsen.getText().toString())>100)
                                {
                                    Toast.makeText(context, "% Komisi out of range (0-100)", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                else if (Double.valueOf(editTextKomisiSalesProsen.getText().toString())>0 )
                                {
                                    Komisi= Jumlah * n2.parse(editTextKomisiSalesProsen.getText().toString()).doubleValue()/100;
                                    editTextKomisiSalesNominal.setText(n0.format(Komisi));
                                }
                                else
                                {

                                }
                            }
                            else if (!TextUtils.isEmpty(editTextKomisiSalesNominal.getText().toString()) || !editTextKomisiSalesNominal.getText().toString().trim().equals(""))
                            {
                                if (Double.valueOf(editTextKomisiSalesNominal.getText().toString())>0 )
                                {
                                    KomisiPersen= Jumlah / (n2.parse(editTextKomisiSalesNominal.getText().toString()).doubleValue() *100);
                                    editTextKomisiSalesNominal.setText(n2.format(KomisiPersen));
                                }
                                else
                                {

                                }
                            }
                            else {
                                editTextKomisiSalesProsen.setText(n2.format(0.0));
                                editTextKomisiSalesNominal.setText(n0.format(0));
                            }

                            editTextNetProfit.setText(n0.format(Jumlah -
                                    Double.valueOf(editTextTransport.getText().toString()) -
                                    Double.valueOf(editTextKomisiSalesNominal.getText().toString())));

                        }
                        else {
                            editTextJumlahProfitKotor.setText("0");
                            editTextTransport.setText("0");
                            editTextKomisiSalesProsen.setText("0 ");
                            editTextKomisiSalesNominal.setText("0");
                            editTextNetProfit.setText("0");
                        }
                    }catch (Exception ex)
                    {
                        Toast.makeText(context, "Error " + ex.toString(), Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }
        });
        editTextTransport.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    try
                    {
                        Double Jumlah=0.0;
                        if (editTextTransport.getText().length() >= 1)
                        {
                            editTextNetProfit.setText(n0.format(n2.parse(editTextJumlahProfitKotor.getText().toString()).doubleValue()  -
                                    n2.parse(editTextTransport.getText().toString()).doubleValue() -
                                    n2.parse(editTextKomisiSalesNominal.getText().toString()).doubleValue()));
                        }
                        else {
                            editTextTransport.setText("0");
                        }
                    }catch (Exception ex)
                    {
                        Toast.makeText(context, "Error " + ex.toString(), Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }
        });

        editTextKomisiSalesProsen.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    try
                    {
                        Double Jumlah=0.0;
                        if (editTextQty.getText().length() >= 1 && editTextJualRoll.getText().length() >= 1)
                        {
                            //'=(Lebar/1000)*Panjang*Harga\n'
                            Jumlah = (n2.parse(editTextJualRoll.getText().toString()).doubleValue() - n2.parse(editTextModal.getText().toString()).doubleValue())* n0.parse(editTextQty.getText().toString()).doubleValue() ;
                            editTextJumlahProfitKotor.setText(n0.format(Jumlah));

                            Double Komisi=0.0,KomisiPersen=0.0;
                            if (!TextUtils.isEmpty(editTextKomisiSalesProsen.getText().toString()) || !editTextKomisiSalesProsen.getText().toString().trim().equals(""))
                            {
                                if (Double.valueOf(editTextKomisiSalesProsen.getText().toString())<0 && Double.valueOf(editTextKomisiSalesProsen.getText().toString())>100)
                                {
                                    Toast.makeText(context, "% Komisi out of range (0-100)", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                else if (Double.valueOf(editTextKomisiSalesProsen.getText().toString())>0 )
                                {
                                    Komisi= Jumlah * n2.parse(editTextKomisiSalesProsen.getText().toString()).doubleValue()/100;
                                    editTextKomisiSalesNominal.setText(n0.format(Komisi));
                                }
                                else
                                {

                                }
                            }
                            else {
                                editTextKomisiSalesProsen.setText(n2.format(0.0));
                                editTextKomisiSalesNominal.setText(n0.format(0));
                            }

                            editTextNetProfit.setText(n0.format(Jumlah -
                                    n2.parse(editTextTransport.getText().toString()).doubleValue() -
                                    n2.parse(editTextKomisiSalesNominal.getText().toString()).doubleValue()));

                        }
                        else {
                            editTextJumlahProfitKotor.setText("0");
                            editTextTransport.setText("0");
                            editTextKomisiSalesProsen.setText("0 ");
                            editTextKomisiSalesNominal.setText("0");
                            editTextNetProfit.setText("0");
                        }
                    }catch (Exception ex)
                    {
                        Toast.makeText(context, "Error " + ex.toString(), Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }
        });
        editTextKomisiSalesNominal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    try
                    {
                        Double Jumlah=0.0;
                        if (editTextQty.getText().length() >= 1 && editTextJualRoll.getText().length() >= 1)
                        {
                            //'=(Lebar/1000)*Panjang*Harga\n'
                            Jumlah = (n2.parse(editTextJualRoll.getText().toString()).doubleValue() - n2.parse(editTextModal.getText().toString()).doubleValue())* n0.parse(editTextQty.getText().toString()).doubleValue() ;
                            editTextJumlahProfitKotor.setText(n0.format(Jumlah));

                            Double Komisi=0.0,KomisiPersen=0.0;
                             if (!TextUtils.isEmpty(editTextKomisiSalesNominal.getText().toString()) || !editTextKomisiSalesNominal.getText().toString().trim().equals(""))
                            {
                                if (n2.parse(editTextKomisiSalesNominal.getText().toString()).doubleValue()>0 )
                                {
                                    KomisiPersen= Jumlah / (n2.parse(editTextKomisiSalesNominal.getText().toString()).doubleValue()*100);
                                    editTextKomisiSalesProsen.setText(n2.format(KomisiPersen));
                                }
                                else
                                {

                                }
                            }
                            else {
                                editTextKomisiSalesProsen.setText(n2.format(0.0));
                                editTextKomisiSalesNominal.setText(n0.format(0));
                            }

                            editTextNetProfit.setText(n0.format(Jumlah -
                                    n2.parse(editTextTransport.getText().toString()).doubleValue() -
                                    n2.parse(editTextKomisiSalesNominal.getText().toString()).doubleValue()));

                        }
                        else {
                            editTextJumlahProfitKotor.setText("0");
                            editTextTransport.setText("0");
                            editTextKomisiSalesProsen.setText("0 ");
                            editTextKomisiSalesNominal.setText("0");
                            editTextNetProfit.setText("0");
                        }
                    }catch (Exception ex)
                    {
                        Toast.makeText(context, "Error " + ex.toString(), Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }
        });
        return root;
    }

    ProgressDialog pDialog;

    public int IDBahan,Harga;
    public String NamaBahan;
    private void getBahan(int position) {
        Bahan bhn = SpinBahan.getItem(position);
        // Here you can do the action you want to...
        if (bhn != null) {
            IDBahan = bhn.getId();
            NamaBahan =bhn.getNama();
            Harga =  bhn.getHarga();
        } else {
            IDBahan = 0;
            NamaBahan ="";
            Harga =  0;
        }
    }
    public static final String TAG = "";
    private void callData() {
        pDialog = new ProgressDialog(context);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading...");
        showDialog();
//        DataHelper db = new DataHelper(context);
//        db.refreshBahan();

//        mdlPublic.ListStrBahan = new ArrayList<String>();
        mdlPublic.ListBahan = new Bahan[5];
        for (int i = 0; i < 5; i++) {
            mdlPublic.ListBahan[i] = new Bahan();
            mdlPublic.ListBahan[i].setId(i);
            switch (i){
                case  0:
                    mdlPublic.ListBahan[i].setNama("Pilih Bahan");
                    mdlPublic.ListBahan[i].setHarga(0);
                    break;
                case 1:
                    mdlPublic.ListBahan[i].setNama("Wax");
                    mdlPublic.ListBahan[i].setHarga(1650);
                    break;
                case 2:
                    mdlPublic.ListBahan[i].setNama("Wax Resin");
                    mdlPublic.ListBahan[i].setHarga(3140);
                    break;
                case 3:
                    mdlPublic.ListBahan[i].setNama("Resin / CL");
                    mdlPublic.ListBahan[i].setHarga(5500);
                    break;
                case 4:
                    mdlPublic.ListBahan[i].setNama("Resin Frosen");
                    mdlPublic.ListBahan[i].setHarga(6300);
                    break;
            }
//            Wax	 Rp1,650
//            Wax Resin	 Rp3,410
//            Resin / CL	 Rp5,500
//            Resin Frosen	 Rp6,380
        }

        mdlPublic.ListStrBahan.clear();
        mdlPublic.ListStrBahan.add("Pilih Bahan");
        mdlPublic.ListStrBahan.add("Wax");
        mdlPublic.ListStrBahan.add("Wax Resin");
        mdlPublic.ListStrBahan.add("Resin / CL");
        mdlPublic.ListStrBahan.add("Resin Frosen");

        hideDialog();
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
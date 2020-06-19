package io.github.yusufahmadi.labelcalculator.ui.paket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.github.yusufahmadi.labelcalculator.R;
import io.github.yusufahmadi.labelcalculator.model.Bahan;
import io.github.yusufahmadi.labelcalculator.model.Paket;
import io.github.yusufahmadi.labelcalculator.repository.DataAccess;

public class PaketInputActivity extends AppCompatActivity {
    private Paket Obj;

    private DecimalFormat df = new DecimalFormat("###,###,###", new DecimalFormatSymbols(Locale.US));
    private DecimalFormat df2 = new DecimalFormat("###,###,###.##", new DecimalFormatSymbols(Locale.US));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_paket);

        initToolbar();
        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                Obj = (Paket) extras.get("key.Paket");
            } else {
                Obj = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            initLayout();
            InitValue(Obj);
        }
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Input Paket");
    }

    private void InitData() {

    }

    private List<Bahan> ListBahan = new ArrayList<>();
    private List<String> ListStrBahan = new ArrayList<>();

    private TextInputEditText editTextcustomer_minta_bikin_jadinya_line,  editTextqty_order_customer_pcs, editTextisi_roll , editTextxtotal_jadi_roll ,editTextlebar,editTexttinggi,
            editTextpisau_yang_digunakan, editTextxlebar_bahan,editTextx1roll_jadiny_pcs ,editTextxjadi_belanja_bahan_baku_label_dalam_Roll ,editTextdibulatkan,
            editTextlebar_ribbon ,editTextpanjang_ribbon ,editTextx1roll_ribbon_bisa_cetak_pcs  ,editTextxmaka_kebutuhan_ribbonnya;
    private TextInputEditText  editTextCatatan;

    private void initLayout() {
        try {
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                    getApplicationContext(), android.R.layout.simple_dropdown_item_1line,
                    ListStrBahan);

            editTextcustomer_minta_bikin_jadinya_line      = findViewById(R.id.editTextcustomer_minta_bikin_jadinya_line);
            editTextqty_order_customer_pcs           = findViewById(R.id.editTextqty_order_customer_pcs);
            editTextisi_roll          = findViewById(R.id.editTextisi_roll);
            editTextxtotal_jadi_roll             = findViewById(R.id.editTextxtotal_jadi_roll);
            editTextlebar           = findViewById(R.id.editTextlebar);

            editTexttinggi   = findViewById(R.id.editTexttinggi);
            editTextpisau_yang_digunakan            = findViewById(R.id.editTextpisau_yang_digunakan);

            editTextxlebar_bahan   = findViewById(R.id.editTextxlebar_bahan);
            editTextx1roll_jadiny_pcs     = findViewById(R.id.editTextx1roll_jadiny_pcs);

            editTextxjadi_belanja_bahan_baku_label_dalam_Roll  = findViewById(R.id.editTextxjadi_belanja_bahan_baku_label_dalam_Roll);
            editTextdibulatkan   = findViewById(R.id.editTextdibulatkan);

            editTextlebar_ribbon      = findViewById(R.id.editTextlebar_ribbon);
            editTextpanjang_ribbon      = findViewById(R.id.editTextpanjang_ribbon);
            editTextx1roll_ribbon_bisa_cetak_pcs     = findViewById(R.id.editTextx1roll_ribbon_bisa_cetak_pcs);
            editTextxmaka_kebutuhan_ribbonnya   = findViewById(R.id.editTextxmaka_kebutuhan_ribbonnya);
            editTextCatatan         = findViewById(R.id.editTextCatatan);



            editTextcustomer_minta_bikin_jadinya_line.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    try {
                        TextInputEditText ed = editTextcustomer_minta_bikin_jadinya_line;
                        if (!hasFocus) {
                            if (ed.getText().toString().isEmpty()) {
                                ed.setText(df.format(0));
                            } else {
                                ed.setText(df.format(Long.valueOf(ed.getText().toString())));
                            }
                            Hitung();
                        } else {
                            ed.setText(String.valueOf(df.parse(ed.getText().toString()).longValue()));
                            ed.setSelection(0, ed.getText().toString().length());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            editTextqty_order_customer_pcs.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    try {
                        TextInputEditText ed = editTextqty_order_customer_pcs;
                        if (!hasFocus) {
                            if (ed.getText().toString().isEmpty()) {
                                ed.setText(df2.format(0.0));
                            } else {
                                ed.setText(df2.format(Double.valueOf(ed.getText().toString())));
                            }
                            Hitung();
                        } else {
                            ed.setText(String.valueOf(df2.parse(ed.getText().toString()).doubleValue()));
                            ed.setSelection(0, ed.getText().toString().length());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });


            editTextisi_roll.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    try {
                        TextInputEditText ed = editTextisi_roll;
                        if (!hasFocus) {
                            if (ed.getText().toString().isEmpty()) {
                                ed.setText(df.format(0));
                            } else {
                                ed.setText(df.format(Double.valueOf(ed.getText().toString())));
                            }
                            Hitung();
                        } else {
                            ed.setText(String.valueOf(df.parse(ed.getText().toString()).doubleValue()));
                            ed.setSelection(0, ed.getText().toString().length());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            editTextlebar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    try {
                        TextInputEditText ed = editTextlebar;
                        if (!hasFocus) {
                            if (ed.getText().toString().isEmpty()) {
                                ed.setText(df2.format(0.0));
                            } else {
                                ed.setText(df2.format(Double.valueOf(ed.getText().toString())));
                            }
                            Hitung();
                        } else {
                            ed.setText(String.valueOf(df2.parse(ed.getText().toString()).doubleValue()));
                            ed.setSelection(0, ed.getText().toString().length());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            editTexttinggi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    try {
                        TextInputEditText ed = editTexttinggi;
                        if (!hasFocus) {
                            if (ed.getText().toString().isEmpty()) {
                                ed.setText(df2.format(0.0));
                            } else {
                                ed.setText(df2.format(Double.valueOf(ed.getText().toString())));
                            }
                            Hitung();
                        } else {
                            ed.setText(String.valueOf(df.parse(ed.getText().toString()).doubleValue()));
                            ed.setSelection(0, ed.getText().toString().length());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            editTextpisau_yang_digunakan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    try {
                        TextInputEditText ed = editTextpisau_yang_digunakan;
                        if (!hasFocus) {
                            if (ed.getText().toString().isEmpty()) {
                                ed.setText(df.format(0));
                            } else {
                                ed.setText(df.format(Long.valueOf(ed.getText().toString())));
                            }
                            Hitung();
                        } else {
                            ed.setText(String.valueOf(df.parse(ed.getText().toString()).longValue()));
                            ed.setSelection(0, ed.getText().toString().length());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            editTextdibulatkan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    try {
                        TextInputEditText ed = editTextdibulatkan;
                        if (!hasFocus) {
                            if (ed.getText().toString().isEmpty()) {
                                ed.setText(df.format(0));
                            } else {
                                ed.setText(df.format(Long.valueOf(ed.getText().toString())));
                            }
                            Hitung();
                        } else {
                            ed.setText(String.valueOf(df.parse(ed.getText().toString()).longValue()));
                            ed.setSelection(0, ed.getText().toString().length());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            editTextlebar_ribbon.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    try {
                        TextInputEditText ed = editTextlebar_ribbon;
                        if (!hasFocus) {
                            if (ed.getText().toString().isEmpty()) {
                                ed.setText(df2.format(0));
                            } else {
                                ed.setText(df2.format(Double.valueOf(ed.getText().toString())));
                            }
                            Hitung();
                        } else {
                            ed.setText(String.valueOf(df2.parse(ed.getText().toString()).doubleValue()));
                            ed.setSelection(0, ed.getText().toString().length());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            editTextpanjang_ribbon.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    try {
                        TextInputEditText ed = editTextpanjang_ribbon;
                        if (!hasFocus) {
                            if (ed.getText().toString().isEmpty()) {
                                ed.setText(df2.format(0.0));
                            } else {
                                ed.setText(df2.format(Double.valueOf(ed.getText().toString())));
                            }
                            Hitung();
                        } else {
                            ed.setText(String.valueOf(df2.parse(ed.getText().toString()).doubleValue()));
                            ed.setSelection(0, ed.getText().toString().length());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });


            editTextCatatan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    try {
                        TextInputEditText ed = editTextCatatan;
                        if (!hasFocus) {
                            if (ed.getText().toString().isEmpty()) {
                                ed.setText("");
                            } else {
                                ed.setText(ed.getText().toString());
                            }
                            Hitung();
                        } else {
                            ed.setText(ed.getText().toString());
                            ed.setSelection(0, ed.getText().toString().length());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });


            MaterialButton btnSave  = findViewById(R.id.buttonSave);
            MaterialButton btnClear = findViewById(R.id.buttonClear);
            btnClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InitValue(null);
                }
            });
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isValidasi = true;
                    try {
                        if (isValidasi && editTextCatatan.getText().length()==0) {
                            Toast.makeText(getApplicationContext(),
                                    "Catatan / No Dokumen harus diisi.",
                                    Toast.LENGTH_SHORT).show();
                            isValidasi = false;
                        }
                        if (isValidasi &&
                                !DataAccess.cekValidasiPaket(getApplication(), editTextCatatan.getText().toString(), (Obj != null ? Obj.no : -1))) {
                            Toast.makeText(getApplicationContext(),
                                    "Catatan/ No Dokumen sudah dipakai.",
                                    Toast.LENGTH_SHORT).show();
                            isValidasi = false;
                        }

                        if (isValidasi) {
                            if (Obj != null) {
                            } else {
                                Obj = new Paket();
                                Obj.no = -1;
                            }
                            Obj.dokumen = editTextCatatan.getText().toString();
                            Date c = Calendar.getInstance().getTime();
                            Obj.tgl             = c;
                            Obj.customer_minta_bikin_jadinya_line     =  df.parse(editTextcustomer_minta_bikin_jadinya_line.getText().toString()).intValue();
                            Obj.lebar           =  df.parse(editTextlebar.getText().toString()).doubleValue();
                            Obj.tinggi          = df.parse(editTexttinggi.getText().toString()).doubleValue();
                            Obj.qty_order_customer_pcs             = df.parse(editTextqty_order_customer_pcs.getText().toString()).doubleValue();
                            Obj.isi_roll           = df.parse(editTextisi_roll.getText().toString()).longValue();
//                            Obj.pembulatan      = df.parse(editTextxtotal_jadi_roll.getText().toString()).doubleValue();
                            Obj.pisau_yang_digunakan       = df.parse(editTextpisau_yang_digunakan.getText().toString()).doubleValue();
//                            Obj.jual_sesuai_order        = df.parse(editTextxlebar_bahan.getText().toString()).doubleValue();
//                            Obj.biaya_pisau     = df.parse(editTextx1roll_jadiny_pcs.getText().toString()).doubleValue();
//                            Obj.biaya_tinta     = df.parse(editTextxjadi_belanja_bahan_baku_label_dalam_Roll.getText().toString()).doubleValue();
                            Obj.dibulatkan    = df.parse(editTextdibulatkan.getText().toString()).doubleValue();
                            Obj.lebar_ribbon  = df.parse(editTextlebar_ribbon.getText().toString()).doubleValue();
                            Obj.panjang_ribbon  = df.parse(editTextpanjang_ribbon.getText().toString()).doubleValue();

                            if (DataAccess.savePaket(getApplicationContext(), Obj)) {
                                GoBackMenu(RESULT_OK, null);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("Save", e.getMessage(), e);
                    }
                }
            });

            InitData();
        } catch (Exception e) {
            Log.e("initLayout", e.getMessage(), e);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                GoBackMenu(RESULT_CANCELED, null);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_detail_produk, menu);
//
//        menuChart = menu.findItem(R.id.cart);
//
//        View actionView = menuChart.getActionView();
//        textCartItemCount = actionView.findViewById(R.id.cart_badge);
//
//        setupBadge();
//
//        actionView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onOptionsItemSelected(menuChart);
//            }
//        });

        return true;
    }

    private void GoBackMenu(int Result, Intent intent) {
        try {
            if (intent!=null) {
                this.setResult(Result, intent);
            } else {
                this.setResult(Result);
            }
            this.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            GoBackMenu(RESULT_CANCELED, null);
        }

        return false;
    }

    private void InitValue(Paket Obj) {
        try {
            if (Obj == null) {

                editTextcustomer_minta_bikin_jadinya_line.setText(df.format(0));
                editTextqty_order_customer_pcs.setText(df.format(0.0));
                editTextisi_roll.setText(df.format(0.0));
                editTextxtotal_jadi_roll.setText(df.format(0.0));
                editTextlebar.setText(df.format(0.0));
                editTexttinggi.setText(df2.format(0.0));
                editTextpisau_yang_digunakan.setText(df.format(0));
                editTextxlebar_bahan.setText(df.format(0));
                editTextx1roll_jadiny_pcs.setText(df.format(0));
                editTextxjadi_belanja_bahan_baku_label_dalam_Roll.setText(df.format(0));
                editTextdibulatkan.setText(df.format(0));
                editTextlebar_ribbon.setText(df.format(0.0));
                editTextpanjang_ribbon.setText(df.format(0.0));
                editTextCatatan.setText("");
            } else {
                editTextcustomer_minta_bikin_jadinya_line.setText(df.format(Obj.customer_minta_bikin_jadinya_line));
                editTextqty_order_customer_pcs.setText(df.format(Obj.qty_order_customer_pcs));
                editTextisi_roll.setText(df.format(Obj.isi_roll));
//                editTextxtotal_jadi_roll.setText(df.format(Obj.x));
                editTextlebar.setText(df.format(Obj.lebar));
                editTexttinggi.setText(df.format(Obj.tinggi));
                editTextpisau_yang_digunakan.setText(df2.format(Obj.pisau_yang_digunakan));
//                editTextxlebar_bahan.setText(df.format(Obj.biaya_tinta));
//                editTextx1roll_jadiny_pcs.setText(df.format(Obj.biaya_toyobo));
//                editTextxjadi_belanja_bahan_baku_label_dalam_Roll.setText(df.format(Obj.biaya_operator));
                editTextdibulatkan.setText(df.format(Obj.dibulatkan));
                editTextlebar_ribbon.setText(df.format(Obj.lebar));
                editTextpanjang_ribbon.setText(df.format(Obj.panjang_ribbon));
                editTextCatatan.setText(Obj.dokumen);
            }
            Hitung();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Hitung() {
        double xtotal_jadi_roll=0,xlebar_bahan=0.0 ,x1roll_jadiny_pcs = 0.0,xjadi_belanja_bahan_baku_label_dalam_Roll =0.0,x1roll_ribbon_bisa_cetak_pcs =0.0, xmaka_kebutuhan_ribbonnya =0.0;
        try {

//            Qty_Order_Customer_Pcs/Isi_Roll
            xtotal_jadi_roll =df2.parse(editTextqty_order_customer_pcs.getText().toString()).doubleValue()/df2.parse(editTextisi_roll.getText().toString()).doubleValue();
            editTextxtotal_jadi_roll.setText(df2.format(xtotal_jadi_roll));

//            (Lebar * Pisau_yang_digunakan )+(3*(Pisau_yang_digunakan - 1)+8)
            xlebar_bahan=(df2.parse(editTextlebar.getText().toString()).doubleValue() * df2.parse(editTextpisau_yang_digunakan.getText().toString()).doubleValue() ) +
                    (3*(df2.parse(editTextpisau_yang_digunakan.getText().toString()).doubleValue() - 1)+8);
            editTextxlebar_bahan.setText(df2.format(xlebar_bahan));

//=	(975000/( Tinggi +3)*(Pisau Yang digunakan))
            x1roll_jadiny_pcs=(975000/( df2.parse(editTexttinggi.getText().toString()).doubleValue() +3)*(df2.parse(editTextpisau_yang_digunakan.getText().toString()).doubleValue()));
            editTextx1roll_jadiny_pcs.setText(df2.format(x1roll_jadiny_pcs));

//            Qty_Order_Customer_Pcs / XJadi Belanja Bahan BakuLabel dalam Roll
            xjadi_belanja_bahan_baku_label_dalam_Roll=df2.parse(editTextqty_order_customer_pcs.getText().toString()).doubleValue()  / df2.parse(editTextx1roll_jadiny_pcs.getText().toString()).doubleValue() ;
            editTextxjadi_belanja_bahan_baku_label_dalam_Roll.setText(df2.format(xjadi_belanja_bahan_baku_label_dalam_Roll));

//            =	(PanjangRibbon*1000)/(Tinggi +3)*Customer_Minta_Bikin_Jadinya_Line
            x1roll_ribbon_bisa_cetak_pcs= (df2.parse(editTextpanjang_ribbon.getText().toString()).doubleValue()*1000)/(df2.parse(editTexttinggi.getText().toString()).doubleValue() +3)*df2.parse(editTextcustomer_minta_bikin_jadinya_line.getText().toString()).doubleValue();
            editTextx1roll_ribbon_bisa_cetak_pcs.setText(df2.format(x1roll_ribbon_bisa_cetak_pcs));

//            Qty_Order_Customer_Pcs /  X1_Roll_Ribbon_bisa_cetak_Pcs
            xmaka_kebutuhan_ribbonnya=  df2.parse(editTextqty_order_customer_pcs.getText().toString()).doubleValue() /  df2.parse(editTextx1roll_ribbon_bisa_cetak_pcs.getText().toString()).doubleValue();
            editTextxmaka_kebutuhan_ribbonnya.setText(df2.format(xmaka_kebutuhan_ribbonnya));

        } catch (Exception e) {
            Log.e("hitung", e.getMessage(), e);
        }
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE)
                && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int[] scrcoords = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}

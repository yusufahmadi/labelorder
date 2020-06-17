package io.github.yusufahmadi.labelcalculator.ui.taffeta;

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
import io.github.yusufahmadi.labelcalculator.model.Taffeta;
import io.github.yusufahmadi.labelcalculator.repository.DataAccess;

public class TaffetaInputActivity extends AppCompatActivity {
    private DecimalFormat df = new DecimalFormat("###,###,###", new DecimalFormatSymbols(Locale.US));
    private DecimalFormat df2 = new DecimalFormat("###,###,###.##", new DecimalFormatSymbols(Locale.US));
    private DecimalFormat df3 = new DecimalFormat("###,###,###.###", new DecimalFormatSymbols(Locale.US));
    private Taffeta Obj;
    private List<Bahan> ListBahan = new ArrayList<>();
    private List<String> ListStrBahan = new ArrayList<>();

    private int idbahan =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_taffeta);

        initToolbar();
        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                Obj = (Taffeta) extras.get("key.Taffeta");
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

    private void InitData() {
        ListBahan = DataAccess.getListTypeTaffeta(this, "", -1, -1);
        if (ListBahan.size()>=1) {
            for (int cc=0; cc < ListBahan.size(); cc++) {
                ListStrBahan.add(ListBahan.get(cc).nama);
            }
        } else {
            ListStrBahan.clear();
        }
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                getApplicationContext(), android.R.layout.simple_dropdown_item_1line,
                ListStrBahan);
        spinner_bahan.setAdapter(arrayAdapter);
    }

    private TextInputEditText editTextHargaModal, editTextKurs, editTextLebar, editTextPanjang, editTextModal,editTextCatatan;
    private AutoCompleteTextView spinner_bahan;
    private TextInputEditText  editTextQty, editTextJualRoll, editTextJumlahProfitKotor , editTextTransport, editTextKomisiSalesProsen,editTextKomisiSalesNominal, editTextNetProfit;
    private TextView textView30Persen,textView50Persen,textView100Persen,textView75Persen;
    private void initLayout() {
        try {
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                    getApplicationContext(), android.R.layout.simple_dropdown_item_1line,
                    ListStrBahan);

            spinner_bahan           = findViewById(R.id.spinner_bahan);
            editTextHargaModal      = findViewById(R.id.editTextHargaModal);
            editTextKurs          = findViewById(R.id.editTextKurs);
            editTextLebar           = findViewById(R.id.editTextLebar);
            editTextPanjang         = findViewById(R.id.editTextPanjang);
            editTextModal           = findViewById(R.id.editTextHargaModalPerRoll);

            textView30Persen = findViewById(R.id.textView30Persen);
            textView50Persen = findViewById(R.id.textView50Persen);
            textView75Persen = findViewById(R.id.textView75Persen);
            textView100Persen = findViewById(R.id.textView100Persen);

            editTextCatatan                      = findViewById(R.id.editTextCatatan);
            editTextQty                             = findViewById(R.id.editTextQtyOrderPcs);
            editTextJualRoll                       = findViewById(R.id.editTextJualRoll);
            editTextJumlahProfitKotor       = findViewById(R.id.editTextJumlahProfitKotor);
            editTextTransport                  = findViewById(R.id.editTextTransport);
            editTextKomisiSalesProsen    = findViewById(R.id.editTextKomisiSalesProsen);
            editTextKomisiSalesNominal   = findViewById(R.id.editTextKomisiSalesNominal);
            editTextNetProfit                    = findViewById(R.id.editTextNetProfit);

            editTextKurs.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    try {
                        TextInputEditText ed = editTextKurs;
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
            editTextQty.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    try {
                        TextInputEditText ed = editTextQty;
                        if (!hasFocus) {
                            if (ed.getText().toString().isEmpty()) {
                                ed.setText(df.format(0.0));
                            } else {
                                ed.setText(df.format(Double.valueOf(ed.getText().toString())));
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
            editTextJualRoll.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    try {
                        TextInputEditText ed = editTextJualRoll;
                        if (!hasFocus) {
                            if (ed.getText().toString().isEmpty()) {
                                ed.setText(df.format(0.0));
                            } else {
                                ed.setText(df.format(Double.valueOf(ed.getText().toString())));
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
            editTextTransport.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    try {
                        TextInputEditText ed = editTextTransport;
                        if (!hasFocus) {
                            if (ed.getText().toString().isEmpty()) {
                                ed.setText(df.format(0.0));
                            } else {
                                ed.setText(df.format(Double.valueOf(ed.getText().toString())));
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
            editTextKomisiSalesProsen.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    try {
                        TextInputEditText ed = editTextKomisiSalesProsen;
                        if (!hasFocus) {
                            Hitung();
//                            double KomisiSalesNominal = 0.0;
                            if (ed.getText().toString().isEmpty()) {
                                ed.setText(df2.format(0.0));
//                                KomisiSalesNominal = 0.0;
//                                editTextKomisiSalesNominal.setText(df.format(Double.valueOf(KomisiSalesNominal)));
                            } else {
                                ed.setText(df2.format(Double.valueOf(ed.getText().toString())));
//                                KomisiSalesNominal = df.parse(editTextJumlahProfitKotor.getText().toString()).doubleValue() * df2.parse(ed.getText().toString()).doubleValue() /100;
//                                editTextKomisiSalesNominal.setText(df.format(Double.valueOf(KomisiSalesNominal)));
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

            spinner_bahan.setAdapter(arrayAdapter);
            spinner_bahan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View arg0) {
                    spinner_bahan.showDropDown();
                }
            });
            spinner_bahan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    idbahan = ListBahan.get(position).id;
                    editTextHargaModal.setText(df3.format(ListBahan.get(position).harga));
                    Hitung();
                }
            });
            spinner_bahan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    editTextHargaModal.setText(df.format(ListBahan.get(position).harga));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    editTextHargaModal.setText(df.format(0.0));
                }
            });
            editTextLebar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    try {
                        TextInputEditText ed = editTextLebar;
                        if (!hasFocus) {
                            if (ed.getText().toString().isEmpty()) {
                                ed.setText(df.format(0.0));
                            } else {
                                ed.setText(df.format(Double.valueOf(ed.getText().toString())));
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
            editTextPanjang.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    try {
                        TextInputEditText ed = editTextPanjang;
                        if (!hasFocus) {
                            if (ed.getText().toString().isEmpty()) {
                                ed.setText(df.format(0.0));
                            } else {
                                ed.setText(df.format(Double.valueOf(ed.getText().toString())));
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
            editTextHargaModal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    try {
                        TextInputEditText ed = editTextHargaModal;
                        if (!hasFocus) {
                            if (ed.getText().toString().isEmpty()) {
                                ed.setText(df3.format(0.0));
                            } else {
                                ed.setText(df3.format(Double.valueOf(ed.getText().toString())));
                            }
                            Hitung();
                        } else {
                            ed.setText(String.valueOf(df3.parse(ed.getText().toString()).doubleValue()));
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
                    DefaultValue();
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
                        if (isValidasi && idbahan < 0) {
                            Toast.makeText(getApplicationContext(),
                                    "Bahan harus diisi.",
                                    Toast.LENGTH_SHORT).show();
                            isValidasi = false;
                        }
                        if (isValidasi &&
                                !DataAccess.cekValidasiTaffeta(getApplication(), editTextCatatan.getText().toString(), (Obj != null ? Obj.no : -1))) {
                            Toast.makeText(getApplicationContext(),
                                    "Catatan/ No Dokumen sudah dipakai.",
                                    Toast.LENGTH_SHORT).show();
                            isValidasi = false;
                        }
                        if (isValidasi && df.parse(editTextLebar.getText().toString()).doubleValue() <= 0) {
                            Toast.makeText(getApplicationContext(),
                                    "Isi Lebar (mm).",
                                    Toast.LENGTH_SHORT).show();
                            isValidasi = false;
                        }
                        if (isValidasi && df.parse(editTextPanjang.getText().toString()).doubleValue() <= 0) {
                            Toast.makeText(getApplicationContext(),
                                    "Isi Panjang (M).",
                                    Toast.LENGTH_SHORT).show();
                            isValidasi = false;
                        }
                        if (isValidasi) {
                            if (Obj != null) {
                            } else {
                                Obj = new Taffeta();
                                Obj.no = -1;
                            }

                            Obj.dokumen = editTextCatatan.getText().toString();
                            Date c = Calendar.getInstance().getTime();
                            Obj.tgl             = c;
                            Obj.id_bahan = idbahan;
                            Obj.kurs = df2.parse(editTextKurs.getText().toString()).doubleValue();
                            Obj.harga_modal =  df3.parse(editTextHargaModal.getText().toString()).doubleValue();
                            Obj.lebar =  df.parse(editTextLebar.getText().toString()).doubleValue();
                            Obj.panjang = df.parse(editTextPanjang.getText().toString()).doubleValue();
                            Obj.modal = df.parse(editTextModal.getText().toString()).doubleValue();
                            Obj.qty = df.parse(editTextQty.getText().toString()).doubleValue();
                            Obj.jual_roll = df.parse(editTextJualRoll.getText().toString()).doubleValue();
                            Obj.jumlah_profit_kotor = df.parse(editTextJumlahProfitKotor.getText().toString()).doubleValue();
                            Obj.transport = df.parse(editTextTransport.getText().toString()).doubleValue();
                            Obj.komisisalesprosen = df.parse(editTextKomisiSalesProsen.getText().toString()).doubleValue();
                            Obj.netprofit = df.parse(editTextNetProfit.getText().toString()).doubleValue();

                            if (DataAccess.saveTaffeta(getApplicationContext(), Obj)) {
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
            DefaultValue();
        } catch (Exception e) {
            Log.e("initLayout", e.getMessage(), e);
        }
//        final NestedScrollView nested = findViewById(R.id.nestedScrollView);
//        final MaterialCardView footer = findViewById(R.id.footer);
//
//        nested.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//            @Override
//            public void onScrollChanged() {
//                if (nested != null) {
//
////                    if (nested.getChildAt(0).getBottom() <= (nested.getHeight() + nested.getScrollY())) {
////                        relativeLayout.setVisibility(View.VISIBLE);
////                    }
////                    else {
////                        relativeLayout.setVisibility(View.INVISIBLE);
////                    }
//                }
//
//                //measuring for alpha
//                int toolBarHeight = footer.getMeasuredHeight();
//                int appBarHeight = nested.getMeasuredHeight();
//                Float f = ((((float) appBarHeight - toolBarHeight) + (nested.getHeight() + nested.getScrollY())) / ( (float) appBarHeight - toolBarHeight)) * 255;
//                footer.setAlpha(Math.round(f));
//            }
//        });
    }

    private void DefaultValue() {
        //Default Value
        editTextLebar.setText(df.format(0.0));
        editTextPanjang.setText(df.format(0.0));
        editTextModal.setText(df.format(0.0));
        editTextHargaModal.setText(df3.format(0.0));
        spinner_bahan.setText("");
        editTextKurs.setText(df2.format(0.0));

        textView30Persen.setText(df.format(0));
        textView50Persen.setText(df.format(0));
        textView75Persen.setText(df.format(0));
        textView100Persen.setText(df.format(0));

        editTextCatatan.setText("");
        editTextJualRoll.setText(df.format(0));
        editTextQty.setText(df.format(0));
        editTextJumlahProfitKotor.setText(df.format(0));
        editTextTransport.setText(df.format(0));
        editTextKomisiSalesProsen.setText(df2.format(0.0));
        editTextKomisiSalesNominal.setText(df.format(0));
        editTextNetProfit.setText(df.format(0));
    }

    private void Simpan() {
        try {
            //
            DefaultValue();
        } catch (Exception e) {
            Log.e("save", e.getMessage(), e);
        }
    }

    private void Hitung() {
        double modalperroll = 0.0, profitkotor= 0.0, netprofit = 0.0,KomisiSalesNominal=0.0;
        try {
            //HargaModal*Kurs*Lebar
            modalperroll = df3.parse(editTextHargaModal.getText().toString()).doubleValue()  * df2.parse(editTextKurs.getText().toString()).doubleValue()
                                    * df2.parse(editTextLebar.getText().toString()).doubleValue();
            editTextModal.setText(df.format(modalperroll));

            textView30Persen.setText(df.format(modalperroll*1.30));
            textView50Persen.setText(df.format(modalperroll*1.50));
            textView75Persen.setText(df.format(modalperroll*1.75));
            textView100Persen.setText(df.format(modalperroll*2.0));

            profitkotor =  (df.parse(editTextJualRoll.getText().toString()).doubleValue()-modalperroll) * df.parse(editTextQty.getText().toString()).doubleValue();
            editTextJumlahProfitKotor.setText(df.format(profitkotor));

            KomisiSalesNominal = df.parse(editTextJumlahProfitKotor.getText().toString()).doubleValue() * df2.parse(editTextKomisiSalesProsen.getText().toString()).doubleValue() /100;
            editTextKomisiSalesNominal.setText(df.format(KomisiSalesNominal));
            netprofit = profitkotor - df.parse(editTextTransport.getText().toString()).doubleValue() - df.parse(editTextKomisiSalesNominal.getText().toString()).doubleValue();
            editTextNetProfit.setText(df.format(netprofit));

        } catch (Exception e) {
            Log.e("hitung", e.getMessage(), e);
        }
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Input Taffeta");
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
        // getMenuInflater().inflate(R.menu.menu_detail_produk, menu);

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

    private void InitValue(Taffeta Obj) {
        try {
            if (Obj == null) {
                spinner_bahan.setText("");
                idbahan = -1;
                spinner_bahan.setText("");
                editTextKurs.setText(df2.format(0.0));
                editTextHargaModal.setText(df3.format(0.0));
                editTextHargaModal.setText(df.format(0.0));
                editTextLebar.setText(df.format(0.0));
                editTextPanjang.setText(df.format(0.0));
                editTextModal.setText(df.format(0.0));


                textView30Persen.setText(df.format(0));
                textView50Persen.setText(df.format(0));
                textView75Persen.setText(df.format(0));
                textView100Persen.setText(df.format(0));

                editTextJualRoll.setText(df.format(0));
                editTextQty.setText(df.format(0));
                editTextJumlahProfitKotor.setText(df.format(0));
                editTextTransport.setText(df.format(0));
                editTextKomisiSalesProsen.setText(df2.format(0.0));
                editTextKomisiSalesNominal.setText(df.format(0));
                editTextNetProfit.setText(df.format(0));
                editTextCatatan.setText("");
            } else {
                for (int i=0; i < ListBahan.size(); i++) {
                    if (ListBahan.get(i).id == Obj.id_bahan) {
                        idbahan = ListBahan.get(i).id;
                        spinner_bahan.setText(ListBahan.get(i).nama);
                        break;
                    }
                }
                editTextHargaModal.setText(df3.format(Obj.harga_modal));
                editTextKurs.setText(df2.format(Obj.kurs));
                editTextLebar.setText(df.format(Obj.lebar));
                editTextPanjang.setText(df.format(Obj.panjang));
                editTextModal.setText(df.format(Obj.modal));
                editTextQty.setText(df.format(Obj.qty));
                editTextJualRoll.setText(df2.format(Obj.jual_roll));
                editTextJumlahProfitKotor.setText(df.format(Obj.jumlah_profit_kotor));
                editTextTransport.setText(df.format(Obj.transport));
                editTextKomisiSalesProsen.setText(df.format(Obj.komisisalesprosen));
//                editTextKomisiSalesNominal.setText(df.format(Obj.get_komisisales_nominal()));
                editTextNetProfit.setText(df.format(Obj.netprofit));
                editTextCatatan.setText(Obj.dokumen);
            }
            Hitung();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

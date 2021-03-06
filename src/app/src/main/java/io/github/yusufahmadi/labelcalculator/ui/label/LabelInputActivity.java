package io.github.yusufahmadi.labelcalculator.ui.label;

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
import androidx.appcompat.widget.LinearLayoutCompat;
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
import io.github.yusufahmadi.labelcalculator.model.Label;
import io.github.yusufahmadi.labelcalculator.repository.DataAccess;
import io.github.yusufahmadi.labelcalculator.repository.mdlPublic;

public class LabelInputActivity extends AppCompatActivity {
    private Label Obj;

    private DecimalFormat df = new DecimalFormat("###,###,###", new DecimalFormatSymbols(Locale.US));
    private DecimalFormat df2 = new DecimalFormat("###,###,###.##", new DecimalFormatSymbols(Locale.US));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_label);

        initToolbar();
        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                Obj = (Label) extras.get("key.Label");
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
        if (mdlPublic.level==mdlPublic.Level.Super) {
            setTitle("Input Label");
        }else
        {
            setTitle("Hitung Bahan Label");
        }
    }

    private void InitData() {
        ListBahan = DataAccess.getListBahanLabel(this, "", -1, -1);
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

    private int idbahan =0;
    private List<Bahan> ListBahan = new ArrayList<>();
    private List<String> ListStrBahan = new ArrayList<>();
    private TextInputEditText editTextHargaModal, editTextLebar, editTextTinggi, editTextGap,editTextPisau,
            editTextLebarBahanBelanja,editText1RollPcs,editTextSaranOrderPcs,editTextQtyOrderPcs,editTextPembulatanRoll,editTextKebutuhanRoll,editTextBiayaPisau,editTextBiayaTinta,editTextBiayaToyobo,editTextBiayaOperator,editTextBiayaKirim, editTextBiayaTotal;
    private AutoCompleteTextView spinner_bahan;
    private LinearLayoutCompat panelBahan,panelUkuran,panelProfitKotor,panelBiaya,panelJual,panelAnalisa,panelEstimasiHarga;
    private TextInputEditText  editTextCatatan, editTextModalBahanUtuh ,editTextModalPerPcs ,editTextJualSesuaiOrder ,editTextProfit1 ,editTextJualSesuaiSaran ,editTextProfit2;
    private TextView textView30Persen,textView50Persen,textView100Persen,textView100Persen2nd,textView125Persen,textView150Persen,textView175Persen,textView200Persen,textView75Persen;

    private void initLayout() {
        try {
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                    getApplicationContext(), android.R.layout.simple_dropdown_item_1line,
                    ListStrBahan);

            spinner_bahan           = findViewById(R.id.spinner_bahan);
            editTextHargaModal      = findViewById(R.id.editTextHargaModal);
            editTextLebar           = findViewById(R.id.editTextLebar);
            editTextTinggi          = findViewById(R.id.editTextTinggi);
            editTextGap             = findViewById(R.id.editTextGap);
            editTextPisau           = findViewById(R.id.editTextPisau);

            editTextHargaModal.setSelectAllOnFocus(true);
            editTextLebar.setSelectAllOnFocus(true);
            editTextTinggi.setSelectAllOnFocus(true);
            editTextGap.setSelectAllOnFocus(true);
            editTextPisau.setSelectAllOnFocus(true);

            editTextLebarBahanBelanja   = findViewById(R.id.editTextLebarBahanBelanja);
            editText1RollPcs            = findViewById(R.id.editText1RollPcs);

            editTextLebarBahanBelanja.setSelectAllOnFocus(true);
            editText1RollPcs.setSelectAllOnFocus(true);

            editTextSaranOrderPcs   = findViewById(R.id.editTextSaranOrderPcs);
            editTextQtyOrderPcs     = findViewById(R.id.editTextQtyOrderPcs);

            editTextSaranOrderPcs.setSelectAllOnFocus(true);
            editTextQtyOrderPcs.setSelectAllOnFocus(true);

            editTextPembulatanRoll  = findViewById(R.id.editTextPembulatanRoll);
            editTextKebutuhanRoll   = findViewById(R.id.editTextKebutuhanRoll);

            editTextPembulatanRoll.setSelectAllOnFocus(true);
            editTextKebutuhanRoll.setSelectAllOnFocus(true);

            editTextBiayaPisau      = findViewById(R.id.editTextBiayaPisau);
            editTextBiayaTinta      = findViewById(R.id.editTextBiayaTinta);
            editTextBiayaToyobo     = findViewById(R.id.editTextBiayaToyobo);
            editTextBiayaOperator   = findViewById(R.id.editTextBiayaOperator);
            editTextBiayaKirim      = findViewById(R.id.editTextBiayaKirim);
            editTextBiayaTotal      = findViewById(R.id.editTextBiayaTotal);

            editTextBiayaPisau.setSelectAllOnFocus(true);
            editTextBiayaTinta.setSelectAllOnFocus(true);
            editTextBiayaToyobo.setSelectAllOnFocus(true);
            editTextBiayaOperator.setSelectAllOnFocus(true);
            editTextBiayaKirim.setSelectAllOnFocus(true);
            editTextBiayaTotal.setSelectAllOnFocus(true);

            textView30Persen        = findViewById(R.id.textView30Persen);
            textView50Persen        = findViewById(R.id.textView50Persen);
            textView75Persen        = findViewById(R.id.textView75Persen);
            textView100Persen       = findViewById(R.id.textView100Persen);
            textView100Persen2nd = findViewById(R.id.textView100Persen2nd);

            textView125Persen       = findViewById(R.id.textView125Persen);
            textView150Persen       = findViewById(R.id.textView150Persen);
            textView175Persen       = findViewById(R.id.textView175Persen);
            textView200Persen       = findViewById(R.id.textView200Persen);

            editTextCatatan         = findViewById(R.id.editTextCatatan);

            editTextCatatan.setSelectAllOnFocus(true);

            editTextModalBahanUtuh  = findViewById(R.id.editTextModalBahanUtuh);
            editTextModalPerPcs     = findViewById(R.id.editTextModalPerPcs);
            editTextJualSesuaiOrder = findViewById(R.id.editTextJualSesuaiOrder);
            editTextProfit1         = findViewById(R.id.editTextProfit1);
            editTextJualSesuaiSaran = findViewById(R.id.editTextJualSesuaiSaran);
            editTextProfit2         = findViewById(R.id.editTextProfit2);

            editTextModalBahanUtuh.setSelectAllOnFocus(true);
            editTextModalPerPcs.setSelectAllOnFocus(true);
            editTextJualSesuaiOrder.setSelectAllOnFocus(true);
            editTextProfit1.setSelectAllOnFocus(true);
            editTextJualSesuaiSaran.setSelectAllOnFocus(true);
            editTextProfit2.setSelectAllOnFocus(true);

            spinner_bahan.setAdapter(arrayAdapter);

            panelBahan          = findViewById(R.id.panelBahan);
            panelUkuran         = findViewById(R.id.panelUkuran);
            panelProfitKotor    = findViewById(R.id.panelProfitKotor);
            panelBiaya             = findViewById(R.id.panelBiaya);
            panelJual           = findViewById(R.id.panelJual);
            panelAnalisa         = findViewById(R.id.panelAnalisa);
            panelEstimasiHarga = findViewById(R.id.panelEstimasiHarga);
            if (mdlPublic.level==mdlPublic.Level.User){
//                spinner_bahan.setVisibility(View.GONE);
                editTextHargaModal.setVisibility(View.GONE);
                //editTextSaranOrderPcs.setVisibility(View.GONE);

                //editTextPembulatanRoll.setVisibility(View.GONE);
                //editTextKebutuhanRoll.setVisibility(View.GONE);

                editTextBiayaPisau.setVisibility(View.GONE);
                editTextBiayaTinta.setVisibility(View.GONE);
                editTextBiayaToyobo.setVisibility(View.GONE);
                editTextBiayaOperator.setVisibility(View.GONE);
                editTextBiayaKirim.setVisibility(View.GONE);
                editTextBiayaTotal.setVisibility(View.GONE);

               // panelBahan.setVisibility(View.GONE);
                panelProfitKotor.setVisibility(View.GONE);
                panelBiaya.setVisibility(View.GONE);
                panelJual.setVisibility(View.GONE);
               // panelAnalisa.setVisibility(View.GONE);

                editTextModalBahanUtuh.setVisibility(View.GONE);
                editTextModalPerPcs.setVisibility(View.GONE);
                editTextJualSesuaiOrder.setVisibility(View.GONE);
                editTextProfit1.setVisibility(View.GONE);
                editTextJualSesuaiSaran.setVisibility(View.GONE);
                editTextProfit2.setVisibility(View.GONE);
            }else {
                panelEstimasiHarga.setVisibility(View.GONE);
            }
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
                    editTextHargaModal.setText(df.format(ListBahan.get(position).harga));
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
            editTextTinggi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    try {
                        TextInputEditText ed = editTextTinggi;
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
            editTextGap.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    try {
                        TextInputEditText ed = editTextGap;
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

            editTextPisau.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    try {
                        TextInputEditText ed = editTextPisau;
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

            editTextQtyOrderPcs.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    try {
                        TextInputEditText ed = editTextQtyOrderPcs;
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
            editTextPembulatanRoll.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    try {
                        TextInputEditText ed = editTextPembulatanRoll;
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
            editTextBiayaPisau.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    try {
                        TextInputEditText ed = editTextBiayaPisau;
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
            editTextBiayaTinta.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    try {
                        TextInputEditText ed = editTextBiayaTinta;
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
            editTextBiayaToyobo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    try {
                        TextInputEditText ed = editTextBiayaToyobo;
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
            editTextBiayaOperator.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    try {
                        TextInputEditText ed = editTextBiayaOperator;
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
            editTextBiayaKirim.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    try {
                        TextInputEditText ed = editTextBiayaKirim;
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
            editTextBiayaTotal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    try {
                        TextInputEditText ed = editTextBiayaTotal;
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

            editTextJualSesuaiOrder.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    try {
                        TextInputEditText ed = editTextJualSesuaiOrder;
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
                        if (isValidasi && idbahan < 0 && mdlPublic.level == mdlPublic.Level.Super) {
                            Toast.makeText(getApplicationContext(),
                                    "Bahan harus diisi.",
                                    Toast.LENGTH_SHORT).show();
                            isValidasi = false;
                        }
                        if (isValidasi &&
                                !DataAccess.cekValidasiLabel(getApplication(), editTextCatatan.getText().toString(), (Obj != null ? Obj.no : -1))) {
                            Toast.makeText(getApplicationContext(),
                                    "Catatan/ No Dokumen sudah dipakai.",
                                    Toast.LENGTH_SHORT).show();
                            isValidasi = false;
                        }
                        if (isValidasi && df.parse(editTextJualSesuaiOrder.getText().toString()).doubleValue() <= 0 && mdlPublic.level == mdlPublic.Level.Super) {
                            Toast.makeText(getApplicationContext(),
                                    "Isi jual sesuai saran.",
                                    Toast.LENGTH_SHORT).show();
                            isValidasi = false;
                        }

                        if (isValidasi) {
                            if (Obj != null) {
                            } else {
                                Obj = new Label();
                                Obj.no = -1;
                            }

                            Obj.dokumen = editTextCatatan.getText().toString();
                            Date c = Calendar.getInstance().getTime();
                            Obj.tgl             = c;
                            Obj.id_bahan = idbahan;
                            Obj.harga_modal     =  df.parse(editTextHargaModal.getText().toString()).doubleValue();
                            Obj.lebar           =  df.parse(editTextLebar.getText().toString()).doubleValue();
                            Obj.tinggi          = df.parse(editTextTinggi.getText().toString()).doubleValue();
                            Obj.gap             = df.parse(editTextGap.getText().toString()).doubleValue();
                            Obj.pisau           = df.parse(editTextPisau.getText().toString()).doubleValue();
                            Obj.pembulatan      = df.parse(editTextPembulatanRoll.getText().toString()).doubleValue();
                            Obj.qty_order       = df.parse(editTextQtyOrderPcs.getText().toString()).doubleValue();
                            Obj.jual_sesuai_order        = df.parse(editTextJualSesuaiOrder.getText().toString()).doubleValue();
                            Obj.biaya_pisau     = df.parse(editTextBiayaPisau.getText().toString()).doubleValue();
                            Obj.biaya_tinta     = df.parse(editTextBiayaTinta.getText().toString()).doubleValue();
                            Obj.biaya_toyobo    = df.parse(editTextBiayaToyobo.getText().toString()).doubleValue();
                            Obj.biaya_operator  = df.parse(editTextBiayaOperator.getText().toString()).doubleValue();
                            Obj.biaya_kirim  = df.parse(editTextBiayaKirim.getText().toString()).doubleValue();

                            if (DataAccess.saveLabel(getApplicationContext(), Obj)) {
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

    private void InitValue(Label Obj) {
        try {
            if (Obj == null) {
                spinner_bahan.setText("");
                idbahan = -1;
                editTextHargaModal.setText(df.format(0.0));
                editTextPisau.setText(df.format(0.0));
                editTextTinggi.setText(df.format(0.0));
                editTextLebar.setText(df.format(0.0));
                editTextGap.setText(df.format(0.0));
                editTextPembulatanRoll.setText(df2.format(0.0));
                editTextBiayaPisau.setText(df.format(0));
                editTextBiayaTinta.setText(df.format(0));
                editTextBiayaToyobo.setText(df.format(0));
                editTextBiayaOperator.setText(df.format(1000000)); //df.format(0)
                editTextBiayaKirim.setText(df.format(0));
                editTextQtyOrderPcs.setText(df.format(0.0));
                editTextJualSesuaiOrder.setText(df.format(0.0));
                editTextCatatan.setText("");
            } else {
                for (int i=0; i < ListBahan.size(); i++) {
                    if (ListBahan.get(i).id == Obj.id_bahan) {
                        idbahan = ListBahan.get(i).id;
                        spinner_bahan.setText(ListBahan.get(i).nama);
                        break;
                    }
                }
                editTextHargaModal.setText(df.format(Obj.harga_modal));
                editTextPisau.setText(df.format(Obj.pisau));
                editTextTinggi.setText(df.format(Obj.tinggi));
                editTextLebar.setText(df.format(Obj.lebar));
                editTextGap.setText(df.format(Obj.gap));
                editTextPembulatanRoll.setText(df2.format(Obj.pembulatan));
                editTextBiayaPisau.setText(df.format(Obj.biaya_pisau));
                editTextBiayaTinta.setText(df.format(Obj.biaya_tinta));
                editTextBiayaToyobo.setText(df.format(Obj.biaya_toyobo));
                editTextBiayaOperator.setText(df.format(Obj.biaya_operator));
                editTextBiayaKirim.setText(df.format(Obj.biaya_kirim));
                editTextQtyOrderPcs.setText(df.format(Obj.qty_order));
                editTextJualSesuaiOrder.setText(df.format(Obj.jual_sesuai_order));
                editTextCatatan.setText(Obj.dokumen);
            }
            Hitung();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Hitung() {
        double Pembulatan=0,RollBahan=0.0 ,Kebutuhannya = 0.0,SaranPcs =0.0,LebarBahanBelanjanya =0.0, ModalBahanUtuh =0.0,ModalperPcs =0.0,TotalBiaya=0.0 ,NetProfit1=0.0 ,NetProfit2=0.0;
        try {
//            LebarBahanBelanjanya = SUM(Lebar*Pisau)+(8)+((Pisau-1)*Gap)
            LebarBahanBelanjanya = (df2.parse(editTextLebar.getText().toString()).doubleValue()*df2.parse(editTextPisau.getText().toString()).doubleValue()) + 8 +
                    ((df2.parse(editTextPisau.getText().toString()).doubleValue()-1)*df2.parse(editTextGap.getText().toString()).doubleValue());
            editTextLebarBahanBelanja.setText(df.format(LebarBahanBelanjanya));

                //            1 Roll Bah    = 975000/(Tinggi+Gap)*Mata Pisau
                RollBahan = ((df2.parse(editTextTinggi.getText().toString()).doubleValue()+df2.parse(editTextGap.getText().toString()).doubleValue()) == 0.0 ? 0 : 975000.0 /
                        (df2.parse(editTextTinggi.getText().toString()).doubleValue()+df2.parse(editTextGap.getText().toString()).doubleValue())*df2.parse(editTextPisau.getText().toString()).doubleValue());
            editText1RollPcs.setText(df.format(RollBahan));

//            Kebutuhannya = Order brp Pcs/ 1 Roll Bah
            Kebutuhannya = (RollBahan==0 ? 0.0 : df2.parse(editTextQtyOrderPcs.getText().toString()).doubleValue()/RollBahan);
            editTextKebutuhanRoll.setText(df2.format(Kebutuhannya));

            Pembulatan = df2.parse(editTextPembulatanRoll.getText().toString()).doubleValue();

//            Saran...Pcs = 1 Roll Bah * Pembulatan
            SaranPcs =  RollBahan * Pembulatan;
            editTextSaranOrderPcs.setText(df.format(SaranPcs));

//            Modal Bahan Utuh =HargaTaxInc*Lebar Bahan Belanjaanya*Pembulatan
            ModalBahanUtuh = df2.parse(editTextHargaModal.getText().toString()).doubleValue() * LebarBahanBelanjanya * Pembulatan;
            editTextModalBahanUtuh.setText(df.format(ModalBahanUtuh));
            //            Modal per Pcs =  Modal Bahan Utuh / Order brp Pcs
            ModalperPcs = df2.parse(editTextQtyOrderPcs.getText().toString()).doubleValue();
            ModalperPcs =  (ModalperPcs==0 ? 0 : ModalBahanUtuh / ModalperPcs);
            editTextModalPerPcs.setText(df2.format(ModalperPcs));

            //Profit Kotor
            textView30Persen.setText(df.format(ModalperPcs*(1+0.3)));
            textView50Persen.setText(df.format(ModalperPcs*(1+0.5)));
            textView75Persen.setText(df.format(ModalperPcs*(1+0.75)));
            textView100Persen.setText(df.format(ModalperPcs*(1+1.0)));
            textView100Persen2nd.setText(df.format(ModalperPcs*(1+1.0)));
            textView125Persen.setText(df.format(ModalperPcs*(1+1.25)));
            textView150Persen.setText(df.format(ModalperPcs*(1+1.5)));
            textView175Persen.setText(df.format(ModalperPcs*(1+1.75)));
            textView200Persen.setText(df.format(ModalperPcs*(1+2.0)));

//            Total Biaya = T Biaya Pisau + T  Tinta 	+ T Toyobo + T Waktu x Operator + T Biaya Kirim
            TotalBiaya =  df2.parse(editTextBiayaPisau.getText().toString()).doubleValue() +
                    df2.parse(editTextBiayaTinta.getText().toString()).doubleValue() +
                    df2.parse(editTextBiayaToyobo.getText().toString()).doubleValue() +
                    df2.parse(editTextBiayaOperator.getText().toString()).doubleValue()+
                    df2.parse(editTextBiayaKirim.getText().toString()).doubleValue();
            editTextBiayaTotal.setText(df.format(TotalBiaya));

//            NetProfit1 = ((Jual sesuai order - Modal per Pcs) * Order brp Pcs ) - Total Biaya
            NetProfit1 = ((df2.parse(editTextJualSesuaiOrder.getText().toString()).doubleValue()-ModalperPcs)*df2.parse(editTextQtyOrderPcs.getText().toString()).doubleValue()) - TotalBiaya;
//            NetProfit2 = ((Jual sesuai saran - Modal per Pcs) * Saran Order brp Pcs ) - Total Biaya
            editTextJualSesuaiSaran.setText(editTextJualSesuaiOrder.getText().toString());
            NetProfit2 = ((df2.parse(editTextJualSesuaiSaran.getText().toString()).doubleValue()-ModalperPcs)*df2.parse(editTextSaranOrderPcs.getText().toString()).doubleValue()) - TotalBiaya;
            editTextProfit1.setText(df.format(NetProfit1));
            editTextProfit2.setText(df.format(NetProfit2));
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

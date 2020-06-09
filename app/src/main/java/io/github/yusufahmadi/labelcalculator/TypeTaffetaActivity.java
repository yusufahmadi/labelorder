package io.github.yusufahmadi.labelcalculator;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.github.yusufahmadi.labelcalculator.adapter.TypeTaffetaRecyclerAdapter;
import io.github.yusufahmadi.labelcalculator.model.Bahan;
import io.github.yusufahmadi.labelcalculator.repository.DataAccess;
import io.github.yusufahmadi.labelcalculator.repository.RecyclerItemClickListener;

public class TypeTaffetaActivity extends AppCompatActivity {
    private SearchView menuSearch = null;
    private RecyclerView recyclerView = null;
    private TypeTaffetaRecyclerAdapter recyclerAdapter;
    private int item_limit = 10, last_item_count =0;
    private static String TAG = "TypeTaffetaActivity";
    private ImageView imgNotFound;
    private TextView tvNotFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        //Setting Into Dark Theme
        getDelegate().setLocalNightMode(
                AppCompatDelegate.MODE_NIGHT_YES);

        initToolbar();
        initLayout();
        initData();
    }

    private void initData() {
        refreshList("", 1, item_limit);
        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh,R.color.refresh1,R.color.refresh2);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        if (menuSearch != null) {
                            menuSearch.setQuery("", true);
                        }
                        refreshList("", 1, item_limit);
                    }
                },3000);
            }
        });
    }

    private void initLayout() {
        try {
            imgNotFound = findViewById(R.id.imgNotFound);
            tvNotFound  = findViewById(R.id.tvNotFound);

            recyclerView = findViewById(R.id.recyclerView1);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            recyclerView.setHasFixedSize(true);
            recyclerAdapter = new TypeTaffetaRecyclerAdapter(this, recyclerView, new ArrayList<Bahan>(), item_limit);
            recyclerView.setAdapter(recyclerAdapter);
            recyclerAdapter.setOnLoadMoreListener(new TypeTaffetaRecyclerAdapter.OnLoadMoreListener() {
                @Override
                public void onLoadMore(int current_page) {
                    if (last_item_count >=1 && current_page != 0) {
                        int next_page = current_page + 1;
                        refreshList(Cari, next_page, item_limit);
                    } else {
                        recyclerAdapter.setLoaded();
                    }
                }
            });
            recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    final Bahan ObjNews;
                    try {
                        ObjNews = recyclerAdapter.getItem(position);

                        if (ObjNews != null) {
                            ShowDetail(ObjNews);
                        }
                    } catch (Exception ex) {
                        Snackbar.make(findViewById(R.id.coordinator),
                                "Error : " + ex.getMessage(),
                                Snackbar.LENGTH_SHORT).show();
                        Log.e(TAG, ex.getMessage());
                        ex.printStackTrace();
                    }
                }

                @Override
                public void onLongItemClick(View view, int position) {

                }
            }));

            FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
            fabAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowDetail(null);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("initLayout", e.getMessage(), e);
        }
    }

    private void initToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Type Taffeta");
        toolbar.setTitle("Type Taffeta");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuSearch = this.findViewById(R.id.txtSearch);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        menuSearch.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        final View closeButton = menuSearch.findViewById(R.id.search_close_btn);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuSearch.setQuery("", true);
                refreshList("", 1, item_limit);
            }
        });

        menuSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                refreshList(query, 1, item_limit);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                if (TextUtils.isEmpty(newText)) {
//                    adapter.get .clearTextFilter();
//                } else {
//                    myList.getAdapter().getFilter().filter(newText);
//                }
                return false;
            }
        });
        return true;
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

    private int Page = 0;
    private String Cari = "";
    private void refreshList(String Filter, int iPage, int iLimit) {
        List<Bahan> Items = new ArrayList<>();
        try {
            recyclerAdapter.setLoading();

            if (iPage<=0) {
                iPage = 1;
            }
            this.Page = iPage;
            this.Cari = Filter;

            Items = DataAccess.getListTypeTaffeta(getApplicationContext(), Filter, iPage, iLimit);
            last_item_count = Items.size();

        } catch (Exception e) {
            Toast.makeText(TypeTaffetaActivity.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } finally {
            try {
                if (iPage<=1) {
                    recyclerAdapter.resetListData();
                }
                recyclerAdapter.insertData(Items);

                if (recyclerAdapter.getItemCount()>=1) {
                    imgNotFound.setVisibility(View.GONE);
                    tvNotFound.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    imgNotFound.setVisibility(View.VISIBLE);
                    tvNotFound.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            } catch (Exception ex) {
                Log.e("App", ex.getMessage(), ex);
                ex.printStackTrace();
            }
        }
    }

    private void ShowDetail(final Bahan obj) {
        final DecimalFormat df = new DecimalFormat("###,###,###.###", new DecimalFormatSymbols(Locale.US));
        try {
            View modelBottomSheet = View.inflate(TypeTaffetaActivity.this, R.layout.dialog_bahan_label, null);
            final BottomSheetDialog dialog = new BottomSheetDialog(TypeTaffetaActivity.this);
            dialog.setContentView(modelBottomSheet);
            dialog.setCancelable(true);

            final TextInputEditText txtDesc         = dialog.findViewById(R.id.txtDescription);
            final TextInputEditText txtCode         = dialog.findViewById(R.id.txtCode);
            final TextInputEditText txtHargaJual    = dialog.findViewById(R.id.txtHargaModal);
            txtHargaJual.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    try {
                        if (!hasFocus) {
                            if (txtHargaJual.getText().length()==0) {
                                txtHargaJual.setText(df.format(0.0));
                            } else {
                                txtHargaJual.setText(df.format(txtHargaJual.getText()));
                            }
                        } else {
                            txtHargaJual.setText(String.valueOf(df.parse(txtHargaJual.getText().toString()).doubleValue()));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("ShowDetail", e.getMessage(), e);
                    }
                }
            });

            if (obj != null) {
                txtDesc.setText(obj.nama);
                txtHargaJual.setText(df.format(obj.harga));
                txtCode.setText(obj.code);
            } else {
                txtDesc.setText("");
                txtCode.setText("");
                txtHargaJual.setText(df.format(0.0));
            }

            Button btnSave  = dialog.findViewById(R.id.btnSave);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isValidasi = true;
                    try {
                        if (isValidasi && txtDesc.getText().length()==0) {
                            Toast.makeText(getApplicationContext(),
                                    "Deskripsi harus diisi.",
                                    Toast.LENGTH_SHORT).show();
                            isValidasi = false;
                        }
                        if (isValidasi && df.parse(txtHargaJual.getText().toString()).doubleValue()<0) {
                            Toast.makeText(getApplicationContext(),
                                    "Harga Modal harus diisi.",
                                    Toast.LENGTH_SHORT).show();
                            isValidasi = false;
                        }
                        if (isValidasi &&
                                !DataAccess.cekValidasiTypeTaffeta(getApplication(), txtDesc.getText().toString(), (obj != null ? obj.id : -1))) {
                            Toast.makeText(getApplicationContext(),
                                    "Deskripsi bahan sudah dipakai.",
                                    Toast.LENGTH_SHORT).show();
                            isValidasi = false;
                        }

                        if (isValidasi) {
                            Bahan bahan = new Bahan();
                            if (obj != null) {
                                bahan.id = obj.id;
                            } else {
                                bahan.id = -1;
                            }
                            bahan.harga = df.parse(txtHargaJual.getText().toString()).doubleValue();
                            bahan.nama = txtDesc.getText().toString();
                            bahan.code = txtCode.getText().toString();
                            if (DataAccess.saveTypeTaffeta(getApplication(), bahan)) {
                                refreshList("", 1, item_limit);
                                dialog.dismiss();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("Save", e.getMessage(), e);
                    }
                }
            });
            Button btnClose = dialog.findViewById(R.id.btnTutup);
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Error", e.getMessage());
        }
    }
}

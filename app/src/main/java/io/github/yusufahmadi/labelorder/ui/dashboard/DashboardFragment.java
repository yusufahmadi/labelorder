package io.github.yusufahmadi.labelorder.ui.dashboard;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.github.yusufahmadi.labelorder.DataHelper;
import io.github.yusufahmadi.labelorder.R;
import io.github.yusufahmadi.labelorder.BahanAdapter;
import io.github.yusufahmadi.labelorder.Bahan;
import io.github.yusufahmadi.labelorder.mdlPublic;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

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
        final Spinner spinner_bahann = root.findViewById(R.id.spinner_bahann);

        callData();
        ArrayAdapter<Bahan> adapter = new ArrayAdapter<Bahan>(context,
                R.layout.spinner_item, mdlPublic.ListBahan);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_bahann.setAdapter(adapter);
        spinner_bahann.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    Toast.makeText(context, item.toString(),
                            Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(context, "Selected",
                        Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub

            }
        });

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


        return root;
    }

    ProgressDialog pDialog;

    public static final String TAG = "";
    private void callData() {
        mdlPublic.ListBahan.clear();
        pDialog = new ProgressDialog(context);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading...");
        showDialog();
        DataHelper db = new DataHelper(context);
        db.refreshBahan();
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
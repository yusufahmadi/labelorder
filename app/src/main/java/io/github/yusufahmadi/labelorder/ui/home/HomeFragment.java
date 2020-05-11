package io.github.yusufahmadi.labelorder.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.text.DecimalFormat;

import io.github.yusufahmadi.labelorder.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);

        final EditText txtPanjangBahan = root.findViewById(R.id.txtPanjangBahan);
        final EditText txtLebar = root.findViewById(R.id.txtLebar);
        final EditText txtGap = root.findViewById(R.id.txtGap);
        final Button btnCalc = root.findViewById(R.id.buttonCalculate);
        final TextView labelOutput = root.findViewById(R.id.labelOutput);

        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        btnCalc.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           try {
                                               if (btnCalc.isEnabled()) {
                                                   final DecimalFormat f2 = new DecimalFormat("#,###,###.####");
                                                   double mxLabel =0.0;
                                                   boolean isValid = true;
                                                   if(TextUtils.isEmpty(txtPanjangBahan.getText().toString()) || txtPanjangBahan.getText().toString().matches("") || txtPanjangBahan.getText().toString().equals(""))
                                                   {
                                                       isValid= false;
                                                       txtPanjangBahan.setError("Input Panjang Bahan");
                                                       return;
                                                   }
                                                   if(TextUtils.isEmpty(txtLebar.getText().toString()) || txtLebar.getText().toString().matches("") || txtLebar.getText().toString().equals(""))
                                                   {
                                                       isValid= false;
                                                       txtLebar.setError("Input Lebar Label");
                                                       return;
                                                   }
                                                   if(TextUtils.isEmpty(txtGap.getText().toString()) || txtGap.getText().toString().matches("") || txtGap.getText().toString().equals(""))
                                                   {
                                                       isValid= false;
                                                       txtGap.setError("Input Gap Label");
                                                       return;
                                                   }
                                                   if (isValid){
                                                       mxLabel = (Double.valueOf(txtPanjangBahan.getText().toString())*100)/(Double.valueOf(txtLebar.getText().toString())+Double.valueOf(txtGap.getText().toString()));
                                                       labelOutput.setText("Label Maksimal : " + f2.format(mxLabel).toString());
                                                   }
                                               }
                                           } catch (Exception ex) {
                                               Log.e("", ex.getMessage());
                                               ex.printStackTrace();
                                           }
                                       }
                                   }
        );
        return root;

    }
}
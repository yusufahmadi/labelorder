package io.github.yusufahmadi.labelcalculator.ui.master;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import io.github.yusufahmadi.labelcalculator.BahanActivity;
import io.github.yusufahmadi.labelcalculator.BahanLabelActivity;
import io.github.yusufahmadi.labelcalculator.R;
import io.github.yusufahmadi.labelcalculator.TypeTaffetaActivity;
import io.github.yusufahmadi.labelcalculator.repository.mdlPublic;

public class MasterFragment extends Fragment {

    private MasterViewModel masterViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        masterViewModel =
                ViewModelProviders.of(this).get(MasterViewModel.class);
        View root = inflater.inflate(R.layout.fragment_master, container, false);

        CardView cvBahan = root.findViewById(R.id.cvBahanRibbon);
        cvBahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BahanActivity.class);
                startActivityForResult(intent, mdlPublic.Activity_MasterBahanRibbon);
            }
        });

        CardView cvBahanLabel = root.findViewById(R.id.cvBahanLabel);
        cvBahanLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BahanLabelActivity.class);
                startActivityForResult(intent, mdlPublic.Activity_MasterBahanLabel);
            }
        });

        CardView cvTypeTaffeta = root.findViewById(R.id.cvTypeTaffeta);
        cvTypeTaffeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TypeTaffetaActivity.class);
                startActivityForResult(intent, mdlPublic.Activity_MasterTypeTaffeta);
            }
        });

        return root;
    }
}
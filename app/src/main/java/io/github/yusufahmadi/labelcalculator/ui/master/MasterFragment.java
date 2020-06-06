package io.github.yusufahmadi.labelcalculator.ui.master;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import io.github.yusufahmadi.labelcalculator.R;

public class MasterFragment extends Fragment {

    private MasterViewModel masterViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        masterViewModel =
                ViewModelProviders.of(this).get(MasterViewModel.class);
        View root = inflater.inflate(R.layout.fragment_master, container, false);

        return root;
    }
}
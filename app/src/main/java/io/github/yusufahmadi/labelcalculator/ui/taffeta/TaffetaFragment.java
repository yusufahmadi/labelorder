package io.github.yusufahmadi.labelcalculator.ui.taffeta;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.github.yusufahmadi.labelcalculator.R;

public class TaffetaFragment extends Fragment {

    private TaffetaViewModel taffetaViewModel;

    private Context context;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        taffetaViewModel =
                ViewModelProviders.of(this).get(TaffetaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_recyclerview, container, false);

        FloatingActionButton fabAdd = root.findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return root;
    }
}
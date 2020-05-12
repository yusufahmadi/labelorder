package io.github.yusufahmadi.labelorder.ui.label;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.github.yusufahmadi.labelorder.R;
import io.github.yusufahmadi.labelorder.repository.mdlPublic;

public class LabelFragment extends Fragment {

    private LabelViewModel labelViewModel;

    private Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        labelViewModel =
                ViewModelProviders.of(this).get(LabelViewModel.class);
        View root = inflater.inflate(R.layout.fragment_recyclerview, container, false);

        FloatingActionButton fabAdd = root.findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LabelInputActivity.class);
                startActivityForResult(intent, mdlPublic.Activity_LabelInput);
            }
        });

        return root;

    }
}
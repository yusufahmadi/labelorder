package io.github.yusufahmadi.labelcalculator.ui.ribbon;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.github.yusufahmadi.labelcalculator.R;
import io.github.yusufahmadi.labelcalculator.repository.mdlPublic;

public class RibbonFragment extends Fragment {
    private RibbonViewModel ribbonViewModel;
    private SearchView menuSearch = null;
    private Context context;

    private int item_limit = 10;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        ribbonViewModel =
                ViewModelProviders.of(this).get(RibbonViewModel.class);
        View root = inflater.inflate(R.layout.fragment_recyclerview, container, false);

        menuSearch = root.findViewById(R.id.txtSearch);
        menuSearch.setQueryHint("Cari data Ribbon ...");
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        menuSearch.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
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

        FloatingActionButton fabAdd = root.findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RibbonInputActivity.class);
                startActivityForResult(intent, mdlPublic.Activity_RibbonInput);
            }
        });

        return root;
    }

    private int page;
    private int item_last_count;
    private void refreshList(String Filter, int page, int limit) {

    }
}
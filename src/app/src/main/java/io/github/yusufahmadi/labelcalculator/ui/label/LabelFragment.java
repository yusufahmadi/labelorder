package io.github.yusufahmadi.labelcalculator.ui.label;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import io.github.yusufahmadi.labelcalculator.R;
import io.github.yusufahmadi.labelcalculator.adapter.LabelRecyclerAdapter;
import io.github.yusufahmadi.labelcalculator.model.Label;
import io.github.yusufahmadi.labelcalculator.repository.DataAccess;
import io.github.yusufahmadi.labelcalculator.repository.RecyclerItemClickListener;
import io.github.yusufahmadi.labelcalculator.repository.mdlPublic;

import static android.app.Activity.RESULT_OK;

public class LabelFragment extends Fragment {
    private SearchView menuSearch = null;
    private RecyclerView recyclerView = null;
    private LabelRecyclerAdapter recyclerAdapter;
    private int item_limit = 10, last_item_count =0;
    private static String TAG = "LabelActivity";
    private ImageView imgNotFound;
    private TextView tvNotFound;

    private LabelViewModel labelViewModel;
    private Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        labelViewModel =
                ViewModelProviders.of(this).get(LabelViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_recyclerview, container, false);

        menuSearch = root.findViewById(R.id.txtSearch);
        menuSearch.setQueryHint("Cari data Label ...");
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

        imgNotFound = root.findViewById(R.id.imgNotFound);
        tvNotFound  = root.findViewById(R.id.tvNotFound);

        recyclerView = root.findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerAdapter = new LabelRecyclerAdapter(getActivity(), recyclerView, new ArrayList<Label>(), item_limit);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnLoadMoreListener(new LabelRecyclerAdapter.OnLoadMoreListener() {
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
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                final Label ObjNews;
                try {
                    ObjNews = recyclerAdapter.getItem(position);

                    if (ObjNews != null) {
                        ShowDetail(ObjNews);
                    }
                } catch (Exception ex) {
                    Snackbar.make(root.findViewById(R.id.coordinator),
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

        FloatingActionButton fabAdd = root.findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDetail(null);
            }
        });

        final SwipeRefreshLayout swipeRefreshLayout = root.findViewById(R.id.swipe_layout);
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

        refreshList("", 1, item_limit);
        return root;
    }

    private int Page = 0;
    private String Cari = "";
    private void refreshList(String Filter, int iPage, int iLimit) {
        List<Label> Items = new ArrayList<>();
        try {
            recyclerAdapter.setLoading();

            if (iPage<=0) {
                iPage = 1;
            }
            this.Page = iPage;
            this.Cari = Filter;

            Items = DataAccess.getListLabel(getActivity(), Filter, iPage, iLimit);
            last_item_count = Items.size();

        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void ShowDetail(Label obj) {
        Intent intent = new Intent(getActivity(), LabelInputActivity.class);
        intent.putExtra("key.Label", obj);
        startActivityForResult(intent, mdlPublic.Activity_LabelInput);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == mdlPublic.Activity_LabelInput) {
            if (resultCode == RESULT_OK) {
                refreshList("", 1, item_limit);
            }
        }
    }
}
package io.github.yusufahmadi.labelcalculator.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.github.yusufahmadi.labelcalculator.R;
import io.github.yusufahmadi.labelcalculator.model.Bahan;

public class TypeTaffetaRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private int VIEW_REC = 0;

    public static List<Bahan> items = new ArrayList<>();
    private DecimalFormat df = new DecimalFormat("###,###,###.###", new DecimalFormatSymbols(Locale.US));
    private Context context;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean loading;
    private int item_limit;

    public interface OnLoadMoreListener{
        void onLoadMore(int current_page);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener){
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public Bahan getItem(int position) {
        return items.get(position);
    }

    public TypeTaffetaRecyclerAdapter(Context context, RecyclerView view, List<Bahan> items, int item_limit) {
        this.context = context;
        this.items = items;
        this.item_limit = item_limit;
        lastViewItemDetector(view);
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    private String getNameForItem(int position) {
        String Hasil = "";
        try {
            Hasil = items.get(position).nama;
        } catch (Exception ex) {
            Hasil = "";
        }
        return Hasil;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if(viewType == VIEW_ITEM){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_bahan, parent, false);
            vh = new OriginalViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        if(viewHolder instanceof OriginalViewHolder){
            try {
                double Harga = 0.0;
                // getting movie data for the row
                final Bahan m = items.get(position);
                OriginalViewHolder holder = (OriginalViewHolder) viewHolder;

                holder.Desc.setText(m.nama);
                holder.Harga.setText("Rp. " + df.format(m.harga));
                holder.Code.setText(m.code);
                holder.Code.setVisibility(View.VISIBLE);
            } catch (Exception ex) {
                Toast.makeText(context, "Error : " + ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            ((ProgressViewHolder) viewHolder).progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class OriginalViewHolder extends RecyclerView.ViewHolder {
        // Layouting item
        public TextView Desc, Code, Harga;

        public OriginalViewHolder(@NonNull View v) {
            super(v);
            Desc        = v.findViewById(R.id.tvNama);
            Harga       = v.findViewById(R.id.tvHarga);
            Code        = v.findViewById(R.id.tvCode);
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder{
        public ProgressBar progressBar;
        public ProgressViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress_loading);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return this.items.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    public void insertData(List<Bahan> items){
        setLoaded();
        int positionStart = getItemCount();
        int itemCount = items.size();
        this.items.addAll(items);
        notifyItemRangeInserted(positionStart, itemCount);
    }

    public void setLoaded(){
        loading = false;
        for(int i = 0; i < getItemCount(); i++){
            if(items.get(i) == null){
                items.remove(i);
                notifyItemRemoved(i);
            }
        }
    }

    public void setLoading() {
        if(getItemCount() != 0){
            this.items.add(null);
            notifyItemInserted(getItemCount() - 1);
            loading = true;
        }
    }

    public void resetListData(){
        this.items = new ArrayList<>();
        notifyDataSetChanged();
    }

    private void lastViewItemDetector(RecyclerView recyclerView){
        if(recyclerView.getLayoutManager() instanceof LinearLayoutManager){
            final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int lastPos = layoutManager.findLastVisibleItemPosition();
                    if(!loading && lastPos == getItemCount() - 1 && onLoadMoreListener != null){
                        loading = true;
                        int current_page = getItemCount() / item_limit;
                        onLoadMoreListener.onLoadMore(current_page);
                    }
                }
            });
        }
    }
}

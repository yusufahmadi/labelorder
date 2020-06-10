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

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.github.yusufahmadi.labelcalculator.R;
import io.github.yusufahmadi.labelcalculator.model.Label;
import io.github.yusufahmadi.labelcalculator.repository.mdlPublic;

public class LabelRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private int VIEW_REC = 0;

    public static List<Label> items = new ArrayList<>();
    private DecimalFormat df    = new DecimalFormat("###,###,###", new DecimalFormatSymbols(Locale.US));
    private SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd", new DateFormatSymbols(Locale.US));
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

    public Label getItem(int position) {
        return items.get(position);
    }

    public LabelRecyclerAdapter(Context context, RecyclerView view, List<Label> items, int item_limit) {
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
            Hasil = items.get(position).dokumen;
        } catch (Exception ex) {
            Hasil = "";
        }
        return Hasil;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if(viewType == VIEW_ITEM){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_label, parent, false);
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
            SimpleDateFormat dt3 = new SimpleDateFormat("dd MMMM yyyy", new DateFormatSymbols(Locale.US));
            long dateDiff = 0;
            try {
                double Harga = 0.0;
                // getting movie data for the row
                final Label m = items.get(position);
                OriginalViewHolder holder = (OriginalViewHolder) viewHolder;

                holder.Document.setText(m.dokumen);
                Date TanggalNow = new Date();

                dateDiff = (int) mdlPublic.getDateDiff (
                        dt,
                        dt.format(m.tgl),
                        dt.format(TanggalNow));

                if (dateDiff == 0) {
                    holder.Tanggal.setText("Today");
                } else if (dateDiff > 0 && dateDiff <= 2) {
                    holder.Tanggal.setText("Yesterday");
                } else {
                    holder.Tanggal.setText(dt3.format(m.tgl));
                }
                holder.Bahan.setText(m.bahan);
                holder.HargaBahan.setText("Rp. " + df.format(m.harga_modal));
                holder.Lebar.setText(df.format(m.lebar));
                holder.Tinggi.setText(df.format(m.tinggi));
                holder.Gap.setText(df.format(m.gap));
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
        public TextView Document, Tanggal, Bahan, HargaBahan, Tinggi, Lebar, Gap;

        public OriginalViewHolder(@NonNull View v) {
            super(v);
            Document    = v.findViewById(R.id.tvDocument);
            Tanggal     = v.findViewById(R.id.tvTgl);
            Bahan       = v.findViewById(R.id.tvBahan);
            HargaBahan  = v.findViewById(R.id.tvHargaBahan);
            Tinggi      = v.findViewById(R.id.tvTinggi);
            Lebar       = v.findViewById(R.id.tvLebar);
            Gap         = v.findViewById(R.id.tvGap);
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

    public void insertData(List<Label> items){
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

package io.github.yusufahmadi.labelorder;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class POAdapter  extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<PO> newsItems;

    public POAdapter(Activity activity, List<PO> newsItems) {
        this.activity = activity;
        this.newsItems = newsItems;
    }

    @Override
    public int getCount() {
        return newsItems.size();
    }

    @Override
    public Object getItem(int location) {
        return newsItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //Date Tanggal = new Date();
        Date TanggalNow = new Date();
        int dateDiff = 0;

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.listviewpo, null);

        try {
//            ImageView thumbNail = convertView.findViewById(R.id.thumbnail);
            TextView pkid = convertView.findViewById(R.id.textViewpkid);
            TextView tanggal = convertView.findViewById(R.id.textViewTanggal);
            TextView catatan = convertView.findViewById(R.id.textViewCatatan);
            TextView lebar = convertView.findViewById(R.id.textViewLebar);
            TextView panjang = convertView.findViewById(R.id.textViewPanjang);
            TextView bahan = convertView.findViewById(R.id.textViewBahan);
            TextView HargaM2 = convertView.findViewById(R.id.textViewHargaM2);
            TextView ModalPerRoll = convertView.findViewById(R.id.textViewModalPerRoll);
            TextView qty = convertView.findViewById(R.id.textViewQty);
            TextView jualroll = convertView.findViewById(R.id.textViewJualRoll);
            TextView profitkotor = convertView.findViewById(R.id.textViewProfitKotor);
            TextView transport = convertView.findViewById(R.id.textViewTransport);
            TextView komisisales = convertView.findViewById(R.id.textViewKomisiSales);
            TextView netprofit = convertView.findViewById(R.id.textViewNetProfit);
//            TextView pkid = convertView.findViewById(R.id.textView);
//            TextView pkid = convertView.findViewById(R.id.textView);
//            TextView pkid = convertView.findViewById(R.id.textView);
//            TextView pkid = convertView.findViewById(R.id.textView);
//            TextView pkid = convertView.findViewById(R.id.textView);
//        Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
//        Typeface normalTypeface = Typeface.defaultFromStyle(Typeface.NORMAL);
            DecimalFormat n0 = new DecimalFormat("#,###,###");
            DecimalFormat n2 = new DecimalFormat("#,###,###.##");

            // getting movie data for the row
            PO m = newsItems.get(position);

            // thumbnail image
//            thumbNail.setImageResource(R.mipmap.pic_placeholder);

            //Tanggal = m.getTanggal();
            dateDiff = (int) mdlPublic.getDateDiff (
                    simpleDateFormat.format(m.get_tanggal()),
                    simpleDateFormat.format(TanggalNow));

            pkid.setText(m.get_id());
//          SATU FOTMAT
            simpleDateFormat = new SimpleDateFormat("dd MMM yy HH:mm");
            tanggal.setText(simpleDateFormat.format(m.get_tanggal()));
//            if (dateDiff == 0) {
//                simpleDateFormat = new SimpleDateFormat("dd MMM yy HH:mm");
//                tanggal.setText(simpleDateFormat.format(m.getTanggal()));
//            } else if (dateDiff <= 2) {
//                simpleDateFormat = new SimpleDateFormat("HH:mm");
//                tanggal.setText("Yesterday " + simpleDateFormat.format(m.getTanggal()));
//            } else if (dateDiff <= 7) {
//                simpleDateFormat = new SimpleDateFormat("EEE HH:mm");
//                tanggal.setText(simpleDateFormat.format(m.getTanggal()));
//            } else {
//                simpleDateFormat = new SimpleDateFormat("dd MMM yyy");
//                tanggal.setText(simpleDateFormat.format(m.getTanggal()));
//            }
            catatan.setText(m.get_catatan());
            lebar.setText(n2.format(m.get_lebar()));
            panjang.setText(n2.format(m.get_panjang()));
            HargaM2.setText(n2.format(m.get_hargaM2()));
//            bahan.setText(m.get_idbahan());
            bahan.setText(mdlPublic.getBahanByID(m.get_idbahan()));
            ModalPerRoll.setText(n2.format(m.get_modalperroll()));
             qty.setText(n0.format(m.get_qty()));
             jualroll.setText(n2.format(m.get_jualroll()));
             profitkotor.setText(n2.format(m.get_jumlahprofitkotor()));
             transport.setText(n2.format(m.get_transportasi()));
             komisisales.setText(n2.format(m.get_komisisalesnominal()));
             netprofit.setText(n2.format(m.get_netprofit()));

//            total.setText("T : " + format.format(m.getTotal()));
//            bayar.setText("B : " + format.format(m.getBayar()));
//            sisa.setText("S : " + format.format(m.getSisa()));
//            if (m.isPosted()) {
//                status.setText("Posted");
//                status.setTextColor(Color.parseColor("#0000FF"));
//                status.setBackgroundColor(Color.parseColor("#00FF00"));
//            } else {
//                status.setText("Open");
//                status.setTextColor(Color.parseColor("#FF0000"));
//                status.setBackgroundColor(Color.TRANSPARENT);
//            }
        } catch (Exception ex) {
            //Toast.makeText(parent.getContext(), "Error : " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return convertView;
    }

}


package io.github.yusufahmadi.labelorder;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class BahanAdapter extends ArrayAdapter<Bahan> {

    // Your sent context
    private Context contexts;
    // Your custom values for the spinner (User)
    private Bahan[] values;

    public BahanAdapter(Context context, int textViewResourceId,
                          Bahan[] values) {
        super(context, textViewResourceId, values);
        this.contexts = context;
        this.values = values;
    }

    public int getCount(){
        return values.length;
    }

    public Bahan getItem(int position){
        return values[position];
    }

    public long getItemId(int position){
        return position;
    }


    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = new TextView(contexts);
        label.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(values[position].getNama());

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = new TextView(contexts);
        label.setTextColor(Color.BLACK);
        label.setText(values[position].getNama());

        return label;
    }
}

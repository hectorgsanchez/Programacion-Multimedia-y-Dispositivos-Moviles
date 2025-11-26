package com.example.listadelacompra;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ShoppingListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Item> items;

    public ShoppingListAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() { return items.size(); }

    @Override
    public Object getItem(int i) { return items.get(i); }

    @Override
    public long getItemId(int i) { return i; }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        if(convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_shopping, parent, false);
        }

        ImageView img = convertView.findViewById(R.id.imgItem);
        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvQty = convertView.findViewById(R.id.tvQty);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);

        Item item = items.get(i);

        img.setImageResource(item.getImageId());
        tvName.setText(item.getName());
        tvQty.setText("x" + item.getQuantity());

        btnDelete.setOnClickListener(view -> {
            items.remove(i);
            notifyDataSetChanged();
        });

        return convertView;
    }
}

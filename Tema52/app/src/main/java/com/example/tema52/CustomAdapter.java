package com.example.tema52;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private List<ListItem> items;
    private int selectedPosition = -1;
    public CustomAdapter(Context context, List<ListItem> items) {
        this.context = context;
        this.items = items;
    }
    @Override
    public int getCount() {
        return items.size();
    }
    @Override
    public Object getItem(int position) {
        return items.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        if (converView == null) {
            converView = LayoutInflater.from(context).inflate(R.layout.list_item, parent);
        }
        ImageView imageView = converView.findViewById(R.id.itemImage);
        TextView titleTextView = converView.findViewById(R.id.itemTitle);
        TextView contentTextView = converView.findViewById(R.id.itemContent);
        RadioButton radioButton = converView.findViewById(R.id.itemRadioButton);
        ListItem item = items.get(position);
        imageView.setImageResource(item.getImageResId());
        titleTextView.setText(item.getTitle());
        contentTextView.setText(item.getContent());
        radioButton.setChecked(position == selectedPosition);
        radioButton.setOnClickListener(v -> {
            selectedPosition = position;
            notifyDataSetChanged();
        });
        return converView;
    }
}

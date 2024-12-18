package com.example.ltapp;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ProfitAdapter extends BaseAdapter {
    private Context context;
    private List<Pair<String, Integer>> profits;

    public ProfitAdapter(Context context, List<Pair<String, Integer>> profits) {
        this.context = context;
        this.profits = profits;
    }

    @Override
    public int getCount() {
        return profits.size();
    }

    @Override
    public Object getItem(int position) {
        return profits.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_profit, parent, false);
        }

        TextView profitTitle = view.findViewById(R.id.tvProfitTitle);
        TextView profitAmount = view.findViewById(R.id.tvProfitAmount);

        Pair<String, Integer> profit = (Pair<String, Integer>) getItem(position);

        profitTitle.setText(profit.first);
        profitAmount.setText(String.valueOf(profit.second) + " đ");

        return view;
    }
}


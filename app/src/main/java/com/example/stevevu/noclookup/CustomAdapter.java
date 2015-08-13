package com.example.stevevu.noclookup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {
    private final Context context;
    private final ArrayList<NOC> n;

    public CustomAdapter(Context context,ArrayList<NOC> n){
        super(context,R.layout.level1,n);
        this.context = context;
        this.n = n;
    }

    @Override
    public View getView(int position,View converView,ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.level1,parent,false);

        TextView nocCode = (TextView) rowView.findViewById(R.id.noc_code);
        TextView nocDesc = (TextView) rowView.findViewById(R.id.noc_desc);

        nocCode.setText(n.get(position).getCode());
        nocDesc.setText(n.get(position).getDesc());
        return rowView;
    }
}

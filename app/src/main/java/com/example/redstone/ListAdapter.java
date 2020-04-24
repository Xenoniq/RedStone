package com.example.redstone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter<T> extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private ArrayList<T> arrayMyData;

    public ListAdapter(Context context, ArrayList<T> arrayMyData) {
        this.layoutInflater = LayoutInflater.from(context);
        this.arrayMyData = arrayMyData;
    }

    public LayoutInflater getLayoutInflater() {
        if (layoutInflater == null)
            throw new NullPointerException("ListAdapter -> getLayoutInflater() -> null");
        return layoutInflater;
    }

    public ArrayList<T> getArrayMyData() {
        if (arrayMyData == null)
            throw new NullPointerException("ListAdapter -> getArrayMyData() -> null");
        return arrayMyData;
    }

    @Override
    public int getCount() {
        return arrayMyData.size();
    }

    @Override
    public Object getItem(int position) {
        if (position >= 0 && position < getCount())
            return arrayMyData.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        Object ob =  getItem(position);
        if (ob != null)
            return position;
        return -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = getLayoutInflater().inflate(R.layout.list_item, null);

        TextView productTw = convertView.findViewById(R.id.productTw),
                weightTw = convertView.findViewById(R.id.weightTw),
                valueTw = convertView.findViewById(R.id.valueTw);

        ProductInfo productInfo = (ProductInfo) getItem(position);
        if (productInfo != null) {
            productTw.setText(productInfo.getName());
            weightTw.setText(String.valueOf(productInfo.getWeight()));
            valueTw.setText(String.valueOf(productInfo.getValue()));
        }
        return convertView;
    }
}

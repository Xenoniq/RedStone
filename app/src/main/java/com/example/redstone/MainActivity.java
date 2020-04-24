package com.example.redstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<ProductInfo> products;
    Button addBt, toStaffBt;
    DialogFragment dialog;
    ListView productLw;
    ListAdapter<ProductInfo> productInfoListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addBt = findViewById(R.id.addBt);
        toStaffBt = findViewById(R.id.toStaffBt);
        products = new ArrayList<>(10);
        productLw = findViewById(R.id.productLw);
        productInfoListAdapter = new ListAdapter<ProductInfo>(this, products);
        productLw.setAdapter(productInfoListAdapter);

        addBt.setOnClickListener(addBtOnClick());
        toStaffBt.setOnClickListener(toStaffBtOnClick());

        dialog = new AddItemDialog();
    }

    public boolean addProduct(String name, int weight, int value){
        boolean ok = products.add(new ProductInfo(name, weight, value));
        if (ok)
            productInfoListAdapter.notifyDataSetChanged();
        return ok;
    }

    private View.OnClickListener toStaffBtOnClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OptimizationTask.toStuff(products);
                productInfoListAdapter.notifyDataSetChanged();
            }
        };
    }

    private View.OnClickListener addBtOnClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show(getSupportFragmentManager(), "AddItemDialog");
            }
        };
    }
}

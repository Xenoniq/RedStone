package com.example.redstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<ProductInfo> productsArr;
    Button addBt, toStaffBt;
    DialogFragment dialog;
    ListView productLw;
    ListAdapter<ProductInfo> productInfoListAdapter;
    DBServer dbServer;
    Dialog dialogError;
    DBServer.Products products;
    public Integer max_weight, min_price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Диалоговое окно ошибка - нач
        dialogError = new Dialog(this);
        dialogError.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogError.setContentView(R.layout.error_dialog);
        dialogError.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogError.setCancelable(false);
        Button but_close = (Button)dialogError.findViewById(R.id.butclose);
            but_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        dialogError.dismiss();
                    }catch (Exception e){

                    }
                }
            });

        //Диалоговое окно ошибка - кц

        dbServer = new DBServer(this);
        products = dbServer.new Products();

        Bundle arg = getIntent().getExtras();
        if (arg != null) {
            max_weight = arg.getInt("maxWeight");
            min_price = arg.getInt("minPrice");
        }

        addBt = findViewById(R.id.addBt);
        toStaffBt = findViewById(R.id.toStaffBt);
        if (!fillProductsArray())
            productsArr = new ArrayList<>(10);
        productLw = findViewById(R.id.productLw);
        productInfoListAdapter = new ListAdapter<ProductInfo>(this, productsArr);
        productLw.setAdapter(productInfoListAdapter);
        registerForContextMenu(productLw);

        addBt.setOnClickListener(addBtOnClick());
        toStaffBt.setOnClickListener(toStaffBtOnClick());

        dialog = new AddItemDialog();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.delete:
                products.delete(productsArr.get(info.position));
                productsArr.remove(info.position);
                productInfoListAdapter.notifyDataSetChanged();
                return true;
            case R.id.edit:
                Bundle bundle = new Bundle();
                bundle.putSerializable(AddItemDialog.BUNDLE, productsArr.get(info.position));
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(), "AddItemDialog");
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public boolean fillProductsArray(){
        productsArr = products.selectAll();
        return productsArr.size() > 0;
    }

    public boolean addProduct(String name, int weight, int value){
        boolean ok = productsArr.add(new ProductInfo(name, weight, value));
        if (ok) {
            productInfoListAdapter.notifyDataSetChanged();
            Toast.makeText(this, R.string.products_added, Toast.LENGTH_SHORT).show();
        }
        return ok;
    }

    private View.OnClickListener toStaffBtOnClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(OptimizationTask.toStuff(productsArr,max_weight,min_price) != null){
                    productInfoListAdapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this,
                            "Укомплектовано", Toast.LENGTH_LONG).show();
                }
                else {
                    dialogError.show();
                }

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

    public void saveArraysInDb(){
        products.deleteAll();
        for(ProductInfo curProd : productsArr){
            products.insert(curProd);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveArraysInDb();
    }
    //Системная кнопка назад - начало
    @Override
    public void onBackPressed() {
        try {
            Intent intent = new Intent(MainActivity.this, EnterPrice.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {

        }
    }
    //Сиситемная кнопка назад - конец

}

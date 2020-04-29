package com.example.redstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnterPrice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_price);
        final Button next = (Button) findViewById(R.id.nextBut);
        final Toast errorPrice =Toast.makeText(this, R.string.error_price, Toast.LENGTH_LONG);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText minPriceEt = (EditText)findViewById(R.id.minPrice);
                try {
                    int minPrice = Integer.parseInt(minPriceEt.getText().toString());
                    if (minPrice > 0) {
                        Intent intent = new Intent(EnterPrice.this, MainActivity.class);
                        Bundle bundle = null;
                        if (getIntent().getExtras() != null && !getIntent().getExtras().isEmpty())
                            bundle = getIntent().getExtras();
                        else
                            bundle = new Bundle();
                        bundle.putInt("minPrice", minPrice);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    } else {
                        errorPrice.show();
                    }
                } catch (NumberFormatException e) {
                    errorPrice.show();
                }

            }
        });
        Button back = (Button) findViewById(R.id.backBut);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnterPrice.this, EnterWeight.class);
                startActivity(intent);
            }
        });
    }



    //Системная кнопка назад - начало
    @Override
    public void onBackPressed() {
        try {
            Intent intent = new Intent(EnterPrice.this, EnterWeight.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {

        }
    }
    //Сиситемная кнопка назад - конец
}


package com.example.redstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnterWeight extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_weight);
        Button  next = (Button) findViewById(R.id.nextBut);
        final Toast errorWeight = Toast.makeText(this, R.string.error_weight, Toast.LENGTH_LONG);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText maxWeightEt = (EditText)findViewById(R.id.maxWeight);
                int maxWeight = Integer.parseInt(maxWeightEt.getText().toString());
                    if(maxWeight > 0) {
                        Intent intent = new Intent(EnterWeight.this, MainActivity.class);
                        intent.putExtra("maxWeight", maxWeight);
                        Intent i = new Intent(EnterWeight.this, EnterPrice.class);
                        startActivity(i);finish();
                } else {
                    errorWeight.show();
                }
            }
        });

    }


    //Системная кнопка назад - начало
    @Override
    public void onBackPressed() {
        try {
            Intent intent = new Intent(EnterWeight.this, IntroActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {

        }
    }
    //Сиситемная кнопка назад - конец
}

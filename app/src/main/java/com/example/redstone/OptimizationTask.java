package com.example.redstone;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

    public class OptimizationTask {
    public static Integer maxProfit = 0;
    boolean price;
    static ArrayList<ProductInfo> toStuff(ArrayList<ProductInfo> products, int max_weight, int min_price) {
        int[][] table = new int[products.size() + 1][max_weight + 1];

        for (int j = 0; j < max_weight + 1; j++) {
            table[0][j] = 0;
        }

        Iterator<ProductInfo> iterator = products.iterator();
        for (int i = 1; i < products.size() + 1; i++) {
            ProductInfo curObj = iterator.next();
            for (int j = 0; j < max_weight + 1; j++) {
                int objWeight = curObj.getWeight(),
                        objValue = curObj.getValue();
                if (j >= objWeight)
                    table[i][j] = Math.max(
                            table[i - 1][j],
                            table[i - 1][j - objWeight] + objValue);
                else
                    table[i][j] = table[i - 1][j];
            }
        }

        for (int i = products.size(), j = max_weight; i > 0; i--) {
            if (table[i][j] == table[i - 1][j])
                products.remove(i - 1);
            else
                j -= products.get(i - 1).getWeight();
        }

        maxProfit = table[products.size()][max_weight];

        if (maxProfit < min_price) {
            return products;
        } else {
            return null;
        }
    }



}

package com.example.redstone;

import java.util.ArrayList;
import java.util.Iterator;

public class OptimizationTask {
    private static final int MAX_WEIGHT = 30;
    public static Integer maxProfit = 0;
    static ArrayList<ProductInfo> toStuff(ArrayList<ProductInfo> products){

        int[][] table = new int[products.size() + 1][MAX_WEIGHT + 1];

        for (int j = 0; j < MAX_WEIGHT + 1; j++) {
            table[0][j] = 0;
        }

        Iterator<ProductInfo> iterator = products.iterator();
        for (int i = 1; i < products.size() + 1; i++) {
            ProductInfo curObj = iterator.next();
            for (int j = 0; j < MAX_WEIGHT + 1; j++) {
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

        for (int i = products.size(), j = MAX_WEIGHT; i > 0; i--) {
            if (table[i][j] == table[i - 1][j])
                products.remove(i - 1);
            else
                j -= products.get(i-1).getWeight();
        }

        maxProfit = table[products.size()][MAX_WEIGHT];
        return products;
    }
}

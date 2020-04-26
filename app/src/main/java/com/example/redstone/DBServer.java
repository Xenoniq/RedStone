package com.example.redstone;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBServer {
    private static final String DATABASE_NAME = "data.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase database;

    public DBServer(Context context) {
        OpenHelper openHelper = new OpenHelper(context);
        this.database = openHelper.getWritableDatabase();
    }

    public class Products{
        private static final String TABLE_NAME = "products";

        private static final String COLUMN_NAME = "name";
        private static final String COLUMN_WEIGHT = "weight";
        private static final String COLUMN_VALUE = "value";

        private static final int NUM_COLUMN_NAME = 0;
        private static final int NUM_COLUMN_WEIGHT = 1;
        private static final int NUM_COLUMN_VALUE = 2;

        public long insert(String name, int weight, int value){
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name);
            cv.put(COLUMN_WEIGHT, weight);
            cv.put(COLUMN_VALUE, value);
            return database.insert(TABLE_NAME, null, cv);
        }

        public long insert(ProductInfo productInfo){
            return insert(productInfo.getName(), productInfo.getWeight(), productInfo.getValue());
        }

        public int update(ProductInfo productInfo){
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, productInfo.getName());
            cv.put(COLUMN_WEIGHT, productInfo.getWeight());
            cv.put(COLUMN_VALUE, productInfo.getValue());
            return database.update(TABLE_NAME, cv, COLUMN_NAME + " =?",
                    new String[]{productInfo.getName()});
        }

        public void deleteAll(){
            database.delete(TABLE_NAME, null, null);
        }

        public void delete(ProductInfo productInfo){
            database.delete(TABLE_NAME, COLUMN_NAME + " =?",
                    new String[]{productInfo.getName()});
        }

        public ProductInfo select(String name){
            Cursor cursor = database.query(TABLE_NAME, null, COLUMN_NAME + " =?",
                    new String[]{name}, null, null, null);

            ProductInfo productInfo = null;
            if (cursor.getCount() > 0){
                cursor.moveToFirst();
                int weigth = cursor.getInt(NUM_COLUMN_WEIGHT),
                        value = cursor.getInt(NUM_COLUMN_VALUE);
                productInfo = new ProductInfo(name, weigth, value);

            }
            cursor.close();
            return productInfo;
        }

        public ArrayList<ProductInfo> selectAll(){
            Cursor cursor = database.query(TABLE_NAME, null, null,
                    null, null, null, null);

            ArrayList<ProductInfo> arr = new ArrayList<>();
            if (cursor.getCount() > 0){
                cursor.moveToNext();
                while (!cursor.isAfterLast()){
                    String name = cursor.getString(NUM_COLUMN_NAME);
                    int weight = cursor.getInt(NUM_COLUMN_WEIGHT),
                            value = cursor.getInt(NUM_COLUMN_VALUE);
                    arr.add(new ProductInfo(name, weight, value));
                    cursor.moveToNext();
                }
            }
            cursor.close();
            return arr;
        }
    }

    private class OpenHelper extends SQLiteOpenHelper{

        public OpenHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + Products.TABLE_NAME + " (" +
                    Products.COLUMN_NAME + " TEXT PRIMARY KEY NOT NULL, " +
                    Products.COLUMN_WEIGHT + " INTEGER NOT NULL, " +
                    Products.COLUMN_VALUE + " INTEGER NOT NULL);";

            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + Products.TABLE_NAME);
            onCreate(db);
        }
    }

}

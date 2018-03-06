package com.example.smartorange_3.databasetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private MyDatabaseHelper databaseHelper;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new MyDatabaseHelper(this, "BookStore.db", null, 2);

        Button create_database = findViewById(R.id.create_database);
        Button add_data = findViewById(R.id.add_data);
        Button update_data = findViewById(R.id.update_data);
        Button delete_data = findViewById(R.id.delete_data);
        Button query_data = findViewById(R.id.query_data);

        create_database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.getWritableDatabase();
            }
        });
        /*
        * 添加数据
        * */
        add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                //开始组装第一条数据
                values.put("name", "The Da Vinci code");
                values.put("author", "Dan Brown");
                values.put("pages", 454);
                values.put("price", 16.96);
                db.insert("Book", null, values);//插入第一条数据
                values.clear();

                //开始组装第二条数据
                values.put("name", "The Lost Symbol");
                values.put("author", "Dan Brown");
                values.put("pages", 510);
                values.put("price", 19.95);
                db.insert("Book", null, values);
                values.clear();//切记 如果下面继续添加数据 就要清空上一条
                Toast.makeText(MainActivity.this, "添加数据完成", Toast.LENGTH_SHORT).show();


                //开始组装第三条数据
                values.put("category_name", "this is on.1");
                values.put("category_code", 8888);
                db.insert("Category", null, values);
            }
        });
         /*
        * 更新数据
        * */
        update_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price", 10.99);
                db.update("Book", values, "name = ?", new String[]{
                        "The Da Vinci Code"});
                Toast.makeText(MainActivity.this, "更新完成", Toast.LENGTH_SHORT).show();
            }
        });
         /*
        * 删除数据
        * */
        delete_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                db.delete("Book", "pages>?", new String[]{"500"});
            }
        });

         /*
        * 查询数据
        * */
        query_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                Cursor cursor = db.query("Category", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        //遍历 Cursor 对象，取出数据并打印；
                        String name = cursor.getString(cursor.getColumnIndex("category_name"));
                        String author = cursor.getString(cursor.getColumnIndex("category_code"));
//                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
//                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d(TAG, "书名 " + name);
                        Log.d(TAG, "作者 " + author);
//                        Log.d(TAG, "页数 "+pages);
//                        Log.d(TAG, "价格 "+price);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        });
    }
}

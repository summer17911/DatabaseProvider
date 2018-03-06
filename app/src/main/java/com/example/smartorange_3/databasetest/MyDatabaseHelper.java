package com.example.smartorange_3.databasetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by smartOrange_3 on 2018/2/22.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private  Context mcontext;

    public static  final  String CREATE_BOOK = "create table book("
            +"id integer primary key autoincrement,"
            +"author text,"
            +"price real,"
            +"pages integer,"
            +"name text)";
    public static final  String  CREATE_CATEGORY = "create table category("
            +"id integer primary key autoincrement ,"
            +"category_name text,"
            +"category_code integer)";


    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mcontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CATEGORY);
        Toast.makeText(mcontext, "create succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //如果在数据库中已经发现这两张表了就删除掉这两张表 然后在调用Oncreate的方法：

        db.execSQL("drop table if exists Book");
        db.execSQL("drop table if exists Category");
        onCreate(db);
    }
}

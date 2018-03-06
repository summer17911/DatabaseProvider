package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button create_database = findViewById(R.id.create_database);//创建数据库
        Button add_data = findViewById(R.id.add_data);              //添加内容
        Button update_data = findViewById(R.id.update_data);        //更新数据
        Button delete_data = findViewById(R.id.delete_data);        //删除数据
        Button query_data = findViewById(R.id.query_data);          //查询数据

        //创建数据库
        create_database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LitePal.getDatabase();
                Toast.makeText(MainActivity.this, "创建完成", Toast.LENGTH_SHORT).show();
            }
        });

        //添加数据
        add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book();
                book.setName("这是一个code码");
                book.setAuthor("Dan Brown");
                book.setPages(454);
                book.setPrice(16.96);
                book.setPress("Unknow");
                book.save();
                Log.d(TAG, "添加完成 ");
            }
        });
        //更新数据
        update_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book();
//                book.setName("失落的米芾");
//                book.setAuthor("陈钊");
//                book.setPages(510);
//                book.setPrice(19.95);
//                book.setPress("Unknow");
//                book.save();
//                book.setPrice(10.99);
//                book.save();
                book.setPrice(14.95);
                book.setPress("Anchor");
                book.updateAll("name=? and author=?","失落的秘府","陈钊");
                Log.d(TAG, "更新完成");
            }
        });
        //删除数据
        delete_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //使用Delete()方法直接删除
                DataSupport.deleteAll(Book.class,"price<?","15");//删除Book书中 价格低于15元的书

            }
        });

        //查询数据
        query_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                List<Book> books=DataSupport.findAll(Book.class);
//                for(Book book : books){
//                    Log.d(TAG, "book name is "+book.getName());
//                    Log.d(TAG, "作者："+book.getAuthor());
//                    Log.d(TAG, "页数："+book.getPages());
//                    Log.d(TAG, ""+book.getPress());
//                    Log.d(TAG, "价格："+book.getPrice());
//                }

//             Book firstBook = DataSupport.findFirst(Book.class);
//             firstBook.getName();
//                Log.d(TAG, "价格："+ firstBook.getName());

               Book lastBook = DataSupport.findLast(Book.class);
               lastBook.getName();
                Log.d(TAG, "名字："+ lastBook.getName());
            }
        });



    }
}

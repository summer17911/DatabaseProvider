package com.example.smartorange_3.databasetest;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class DatabaseProvider extends ContentProvider {
    public static final int BOOK_DIR =0;
    public static final int BOOK_ITEM = 1;
    public static final int CATEGORY_DIR = 2;
    private static final int CATEGORY_ITEM = 3;
    public static final String AUTHORITY = "com.example.databasetest.provider";
    private static UriMatcher uriMatcher;
    private MyDatabaseHelper databaseHelper;

    static {
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.example.app.provider", "table1", BOOK_DIR);
        uriMatcher.addURI("com.example.app.provider", "tanle1/#", BOOK_ITEM);
        uriMatcher.addURI("com.example.app.provider", "table2", CATEGORY_DIR);
        uriMatcher.addURI("com.example.app.provider", "table2/#", CATEGORY_ITEM);
    }

    public DatabaseProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        //删除数据
        SQLiteDatabase db=databaseHelper.getWritableDatabase();
        int deleteRows=0;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
                deleteRows=db.delete("Book",selection,selectionArgs);
                break;
            case BOOK_ITEM:
                String bookId = uri.getPathSegments().get(1);
                deleteRows=db.delete("Book","id=?",new String[]{bookId});
                break;
            case CATEGORY_DIR:
                deleteRows=db.delete("Category",selection,selectionArgs);
                break;
            case CATEGORY_ITEM:
                String categoryId = uri.getPathSegments().get(1);
                deleteRows=db.delete("Category","id=?",new String[]{categoryId});
                break;
                default:
                    break;
        }
        return deleteRows;
    }

    @Override
    public String getType(Uri uri) {
      switch (uriMatcher.match(uri)){
          case BOOK_DIR:
              return "vnd.android.cursor.dir/vnd.com.example.databasetest.provider.book";
          case BOOK_ITEM:
              return "vnd.android.cursor.item/vnd.com.example.databasetest.provider.book";
          case CATEGORY_DIR:
              return "vnd.android.cursor.dir/vnd.com.example.databasetest.provider.Category";
          case CATEGORY_ITEM:
              return "vnd.android.cursor.item/vnd.com.example.databasetest.provider.Category";
      }
      return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
     //添加数据
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Uri uriReturn= null;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
            case BOOK_ITEM:
                long newBookId=db.insert("Book",null,values);
                uriReturn = Uri.parse("content://"+AUTHORITY+"/book/"+newBookId);
                break;
            case CATEGORY_DIR:
            case CATEGORY_ITEM:
                long newCategoryId = db.insert("category",null,values);
                uriReturn=Uri.parse("content://"+AUTHORITY+"/book/"+newCategoryId);
                break;
                default:
                    break;
        }
        return uriReturn;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        databaseHelper=new MyDatabaseHelper(getContext(),"BookStore.db",null,2);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        //查询数据
        SQLiteDatabase db=databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
                cursor = db.query("Book",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case BOOK_ITEM:
               String bookId=uri.getPathSegments().get(1);
                cursor = db.query("Book",projection,"id=?",new String[]{bookId},null,null,sortOrder);
                break;
            case CATEGORY_DIR:
             cursor=db.query("Category",projection,selection,selectionArgs,null,null,sortOrder);
                    break;
            case CATEGORY_ITEM:
                String categoryId = uri.getPathSegments().get(1);
                cursor = db.query("Category",projection,"id=?",new String[]{categoryId},null,null,sortOrder);
                break;
                default:
                    break;
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
      //更新数据
        SQLiteDatabase db=databaseHelper.getWritableDatabase();
        int updatedRows=0;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
                updatedRows=db.update("Book",values,selection,selectionArgs);
                break;
            case BOOK_ITEM:
                String bookId=uri.getPathSegments().get(1);
                updatedRows=db.update("Book",values,"id=?",new String[]{bookId});
                break;
            case CATEGORY_DIR:
                updatedRows=db.update("Category",values,selection,selectionArgs);
                break;
            case CATEGORY_ITEM:
                String categoryId=uri.getPathSegments().get(1);
                updatedRows=db.update("Category",values,"id=?",new String[]{categoryId});
                break;
                default:
                    break;
        }
        return updatedRows;

    }
}
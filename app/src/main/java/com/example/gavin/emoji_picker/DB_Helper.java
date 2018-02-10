package com.example.gavin.emoji_picker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Gavin on 2018/1/4.
 */

public class DB_Helper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "emoji_database";
    private final static int DATABASE_VER = 1;
    private final static String TABLE_NAME = "emoji_table";
    private final static String FELID_ID = "_id";
    private final static String FELID_TEXT = "emoji_text";
    private String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
            + FELID_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + FELID_TEXT +
            " TEXT);";
    private SQLiteDatabase database;

    public DB_Helper (Context context){
        super (context,DATABASE_NAME,null,DATABASE_VER);
        database = this.getWritableDatabase();

        //2018 Feb 6 update
        //把getWritableDatabase()從insert()移到上面的DB_Helper後就可以了
        //不過現在有個問題是複製以後的內容卻是1，而不是"hhh"
        //是不是因為它取得的是id而不是String
        //Reference：https://stackoverflow.com/questions/30611534/sqlitedatabase-rawqueryjava-lang-string-java-lang-string-on-a-null-object#comment49290450_30611534
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);
        //執行上面String sql的內容
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
        //這個我還要再想一下  xd
    }

    public Cursor selecter(){
        Cursor cursor = database.query(TABLE_NAME,null,null,null,null,null,null);
        return cursor;
        //用cursor來指定資料庫的某個欄位，再回傳c (選到的資料...吧？)
    }

    public void insert(String itemText){
        ContentValues values = new ContentValues();
        values.put(FELID_TEXT,itemText);
        database.insert(TABLE_NAME,null,values);
        //ContentValues是一種對應的物件，用來告訴資料庫想要插入的欄位名稱及該資料
        //參考資料提供的code只設定兩個欄位，第一個是AUTOINCREMENT的"_id"，不用填寫
        //這裡輸入的值GET_TEXT的內容
    }

    public void delete(int id){
        database.delete(TABLE_NAME,FELID_ID + "=" + Integer.toString(id),null);
        //第一個參數是表格名稱，第二個用SQL語法講就是WHERE語法
    }

    public void update(int id,String itemText){
        ContentValues values = new ContentValues();
        values.put(FELID_TEXT,itemText);
        database.update(TABLE_NAME,values,FELID_ID + "=" + Integer.toString(id),null);
        //update內的參數說明跟delete一樣
    }

    public void close(){
        database.close();
        //資料庫用完記得關起來喔！BY原作者
    }

    //REFERENCE：
    //http://givemepass.blogspot.tw/2011/11/listview_09.html
    //剛好都是我需要的資料，佛心公司刷一波
    //真的覺得這部落格很棒

    //延伸閱讀：
    //https://developer.android.com/reference/android/database/sqlite/SQLiteOpenHelper.html
    //https://developer.android.com/training/basics/data-storage/databases.html?hl=zh-tw
    //跟Database有關的
    //http://givemepass.blogspot.tw/2011/10/listview.html
    //動態增減ListView


}

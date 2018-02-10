package com.example.gavin.emoji_picker;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/*
  Created by Gavin on 2017/12/5.

  2018/2/11

  原則上是Finish了，剩下就是優化版面那些，還有處理有時候android.UI會掛掉的問題...
*/

public class custom extends AppCompatActivity {
    ListView ListViewOfCustom;
    EditText editTextOfCustom;
    Button Input_Button;
    private DB_Helper custom_DB_helper;

    private Cursor c_atCustom;
    private SimpleCursorAdapter cursorAdapter_atCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        Bundle bundle = getIntent().getExtras();

        findview();

        initDB();
        initView();

        case_insert();


    }

    //將資料庫有的東西搜尋出來，若確定有東西，則將其放入ListView

    private void initDB() {

        custom_DB_helper = new DB_Helper(getApplicationContext());
        c_atCustom = custom_DB_helper.selecter();
        cursorAdapter_atCustom = new SimpleCursorAdapter(this,
                R.layout.adapter, c_atCustom,
                new String[]{"emoji_text"},
                new int[]{R.id.text},
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        //雖然Android有內建可以容納Cursor的Adapter，
        //但還是得宣告一個View(R.layout.adapter)dapter
        //這我也是不明覺厲，就先照樣做吧...Orz

        //new int[]{R.id.text}的text是對應在adapter.xml的@+id/text
    }

    private void initView(){

        ListViewOfCustom.setAdapter(cursorAdapter_atCustom);

        ListViewOfCustom.setOnItemClickListener(onClickListView_Customs);

        ListViewOfCustom.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                c_atCustom.moveToPosition(pos);

                new AlertDialog.Builder(custom.this)
                        .setMessage("確定要刪除嗎？")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                custom_DB_helper.delete(c_atCustom.getInt(0));
                                c_atCustom.requery();
                                cursorAdapter_atCustom.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Nope", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();

                //處理長按之後是否執行刪除之動作

                return true;

                //這真的很不可思議，在這裡Return true的話就只會執行LongClick的內容
                //看stack overflow的說明是，這裡Return的目的是要確認你是否要執行其他的動作
                //Reference：https://stackoverflow.com/questions/4621439/use-both-onclicklistener-and-onlongclicklistener-in-listview-android-1-6
            }
        });


    }


    private void findview() {
        ListViewOfCustom = (ListView) findViewById(R.id.ListViewOfCustom);
        editTextOfCustom = (EditText) findViewById(R.id.editTextOfCustom);
        Input_Button = (Button) findViewById(R.id.Input_Button);
    }

    //供xml使用，所以設為public
    //不過上次好像這裡有出錯過，就先放著吧
    private void case_insert() {
        Input_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //java.lang.NullPointerException: Attempt to invoke interface method
                // 'boolean android.database.Cursor.requery()' on a null object reference
                //似乎解決了......？好像是因為在這之前還沒有getWritableDatabase()的關係

                if (!editTextOfCustom.getText().toString().equals("")) {
                    custom_DB_helper.insert(editTextOfCustom.getText().toString());
                    c_atCustom.requery();   //不太懂
                    cursorAdapter_atCustom.notifyDataSetChanged();  //從字面上來看是「察覺資料改變」
                    editTextOfCustom.setText("");
                    Toast.makeText(custom.this,"資料輸入成功！",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(custom.this,"請輸入內容！",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private AdapterView.OnItemClickListener onClickListView_Customs = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            ClipboardManager clipBoard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
            ClipData theClip;

            try {
                c_atCustom.moveToPosition(position);
            }catch(Exception e){
                Toast.makeText(custom.this,"Exception happened at moveToPosition!",Toast.LENGTH_SHORT);
            }

            try {
                theClip = ClipData.newPlainText("text", c_atCustom.getString(1));
                //2018 Feb 6補充：把getString()裡面的參數從position改成 1 就可以了
                clipBoard.setPrimaryClip(theClip);
            }catch(Exception e){
                Toast.makeText(custom.this,"Exception happened at getString!",Toast.LENGTH_SHORT);
            }

            Toast.makeText(custom.this,"已複製到剪貼簿囉",Toast.LENGTH_SHORT).show();

        }
    };

}

    //不知道這個app關掉以後資料庫會不會自動close？
package com.example.gavin.emoji_picker;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class happy extends AppCompatActivity {
    ListView listView_happy;
    private ArrayAdapter arrayAdapter;
    private String[] strOfHappy = {"(ﾟ∀ﾟ)","(ﾟ∀。)","ヽ(●´∀`●)ﾉ","ｷﾀ━━━━(ﾟ∀ﾟ)━━━━!!","( ^ω^)",
    "(*´д`)","(・∀・)","_(:3 」∠ )_"};
    private String[] strOfAngry = {"ヽ(#`Д´)ﾉ","(； ･`д･´)","(´-ω-｀)","(＊゜ー゜)b"};
    private String[] strOfSad = {"(´・ω・`)","( ˘•ω•˘ )","(´ﾟдﾟ`)","(´;ω;`)","(((ﾟДﾟ;)))","｡ﾟ(ﾟ´ω`ﾟ)ﾟ｡"};

    getNum trying = new getNum();
    //做全域宣告  才能在各種class使用

    /*
        11/23  AM 01:22
        BUG REPORT
            System services not available to Activities before onCreate()
     */

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emoji_happy);

        Bundle bundle = getIntent().getExtras();
        int num = bundle.getInt("num");

        listView_happy = (ListView)findViewById(R.id.listView_happy);

        switch (num){
            case 0:
                arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,strOfHappy);
                trying.inputNum(0);
                break;
            case 1:
                arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,strOfAngry);
                trying.inputNum(1);
                break;
            case 2:
                arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,strOfSad);
                trying.inputNum(2);
                break;
            default:
                Toast.makeText(happy.this,"Exception found!",Toast.LENGTH_SHORT).show();
                break;
        }

        listView_happy.setAdapter(arrayAdapter);
        listView_happy.setOnItemClickListener(onClickListVIew);

    }

    private AdapterView.OnItemClickListener onClickListVIew = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            ClipboardManager clipBoard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);

            ClipData theClip;


            int gotNum = trying.returnNum();

            switch (gotNum){

                case 0:
                    theClip = ClipData.newPlainText("text",strOfHappy[position]);
                    clipBoard.setPrimaryClip(theClip);
                    break;
                case 1:
                    theClip = ClipData.newPlainText("text",strOfAngry[position]);
                    clipBoard.setPrimaryClip(theClip);
                    break;
                case 2:
                    theClip = ClipData.newPlainText("text",strOfSad[position]);
                    clipBoard.setPrimaryClip(theClip);
                    break;
                default:
                    Toast.makeText(happy.this,"Exception found!",Toast.LENGTH_SHORT).show();
                    break;
            }


            Toast.makeText(happy.this,"已複製到剪貼簿囉",Toast.LENGTH_SHORT).show();
        }
    };

    class getNum{
        private int got;
        private void inputNum(int i){
            got = i;
        }
        private int returnNum(){
            return got;
        }
    }

}
/*
    11/24  AM01:36  BUG REPORT
        gots 只會回傳0
 */
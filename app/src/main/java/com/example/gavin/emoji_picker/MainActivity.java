package com.example.gavin.emoji_picker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    Spinner spinner;
    Button nextPage;
    int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView)findViewById(R.id.textView);
        spinner = (Spinner)findViewById(R.id.spinner);
        imageView = (ImageView)findViewById(R.id.imageView);
        nextPage = (Button)findViewById(R.id.nextPage);

        final String[] emoji = {"喜","怒","哀","自訂"};

        ArrayAdapter<String> emojiList = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_spinner_dropdown_item,emoji);

        spinner.setAdapter(emojiList);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (position == 0){
                    num = position;
                }
                else if (position == 1){
                    //intent至頁面「怒」
                    num = position;
                }
                else if (position == 2){
                    //intent至頁面「哀」
                    num = position;
                }
                else if (position == 3){
                    num = position;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent();
                    if (num != 3) {
                        intent.setClass(MainActivity.this, happy.class);
                    }else if (num == 3){
                        intent.setClass(MainActivity.this,custom.class);
                    }
                    Bundle bundle = new Bundle();
                    bundle.putInt("num",num);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

        /*  @11/23  AM01:18
                BUG REPORT
                setOnItemClickListener cannot be used with a spinner.
        */

    }
/*
    public spinner.setOnItemSelectedListener onClickListView = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent();
            if (position == 0){
                intent.setClass(MainActivity.this,happy.class);
                startActivity(intent);
            }
            else if (position == 1){
                //intent至頁面「怒」
            }
            else if (position == 2){
                //intent至頁面「哀」
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            //空著即可
        }

    };
    */




package com.example.termproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

//기록화면
public class view_record extends Activity {
    Context context;
    LinearLayout layout;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //타이틀바 없애기
        setContentView(R.layout.view_record);

        //화면비율조절
        Display dp = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = (int)(dp.getWidth() * 0.95);
        getWindow().getAttributes().width = width;

        ImageView close = (ImageView)findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        layout = (LinearLayout) findViewById(R.id.input_record);
        context = this;
        File file = new File(data.inner_dir + data.FileName);
        if(!file.exists()){
            TextView txt = (TextView)findViewById(R.id.txt1);
            txt.setText("기록 없음");
        }
        try{
            FileInputStream inFs = openFileInput(data.FileName);
            InputStreamReader inputStreamReader = new InputStreamReader(inFs);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String tmp = null;
            while ((tmp = bufferedReader.readLine()) != null){
                if(tmp.startsWith("실행")){
                    //실행 윗줄에 2dp 선긋기
                    final int height =(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,2,
                            getResources().getDisplayMetrics());
                    View view = new View(context);
                    view.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,height
                    ));
                    view.setBackgroundColor(Color.parseColor("#808080"));
                    layout.addView(view);
                }
                TextView txt = new TextView(context);
                txt.setTextSize(15);
                txt.setText(tmp);
                layout.addView(txt);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

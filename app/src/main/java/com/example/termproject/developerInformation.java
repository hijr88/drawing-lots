package com.example.termproject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

//메뉴버튼 개발자정보
public class developerInformation extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.developerinformation);

        //화면비율 조절
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
    }
}

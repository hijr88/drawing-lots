package com.example.termproject;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

//결과창
public class resultActivity extends Activity {
    Context context;
    LinearLayout layout;
    @Override
    public void onBackPressed() {
        //SpannableStringBuilder를 사용하면 코드상에서 문자의 색,크기를 바꿀수 있다
        String []ss = {"종료하시겠습니까?","확인","취소"};
        SpannableStringBuilder[]ssBuilser = new SpannableStringBuilder[ss.length];
        for(int i=0;i<ss.length;i++){
            ssBuilser[i] = new SpannableStringBuilder(ss[i]);
            ssBuilser[i].setSpan(new ForegroundColorSpan(Color.WHITE), 0, ss[i].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        AlertDialog.Builder dlg = new AlertDialog.Builder(context);
        dlg.setTitle(ssBuilser[0]);
        dlg.setPositiveButton(ssBuilser[1], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishAffinity();
                System.runFinalization();
                System.exit(0);
            }
        });
        dlg.setNegativeButton(ssBuilser[2],null);
        dlg.show();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        final int N = MainActivity.p1;  //참가자 인원
        FileWrite();// 기록남기기

        Typeface ty = getResources().getFont(R.font.cookierunbold); //글꼴
        layout = (LinearLayout) findViewById(R.id.result);
        context = this;
        final EditText[] et = new EditText[N];
        final EditText[] et1 = new EditText[N];
        final LinearLayout [] sub_layout = new LinearLayout[N];
        final int width =(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,163,
                getResources().getDisplayMetrics());
        for(int i=0;i<N;i++)
        {
            sub_layout[i] = new LinearLayout(context);
            sub_layout[i].setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            et[i] = new EditText(context);
            et[i].setLayoutParams(new ViewGroup.LayoutParams(
                 width,ViewGroup.LayoutParams.WRAP_CONTENT));
            et[i].setTextColor(Color.parseColor("#000050"));
            et[i].setTypeface(ty);
            et[i].setSingleLine(true);
            et[i].setText(data.result[i][0]);
            et[i].setClickable(false);
            et[i].setFocusable(false);
            sub_layout[i].addView(et[i]);

            et1[i] = new EditText(context);
            et1[i].setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
            et1[i].setTextColor(Color.parseColor("#000050"));
            et1[i].setTypeface(ty);
            et1[i].setSingleLine(true);
            et1[i].setText("   :  " + data.result[i][1]);
            et1[i].setClickable(false);
            et1[i].setFocusable(false);
            sub_layout[i].addView(et1[i]);

            layout.addView(sub_layout[i]);
            Button re = (Button)findViewById(R.id.re);
            Button move_home = (Button)findViewById(R.id.move_home);
            re.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), runActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
            move_home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        }
    }
    public void FileWrite(){
        final int N = MainActivity.p1;  //참가자 인원
        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");  //날짜
        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");   //시간
        String tmp = "";
        try{
            FileOutputStream outFs = openFileOutput(data.FileName,Context.MODE_APPEND);
            tmp = "실행시간 : " + date.format(today) + " " + time.format(today) + "\n";
            outFs.write(tmp.getBytes());
            for(int i=0;i<N;i++)
            {
                tmp = String.format("%10s : %s\n",data.result[i][0],data.result[i][1]);
                outFs.write(tmp.getBytes());
            }
            outFs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

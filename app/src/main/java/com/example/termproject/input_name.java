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
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//이름 입력
public class input_name extends Activity {
    Context context;
    LinearLayout layout;
    Button skip,check;  //건너뛰기, 확인
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
        setContentView(R.layout.input_name);

        final int N = MainActivity.p1;  //참가자 인원
        Typeface ty = getResources().getFont(R.font.cookierunbold); //글꼴
        layout = (LinearLayout) findViewById(R.id.input_name);
        context = this;
        final EditText[] et = new EditText[N];
        for(int i=0;i<N;i++)
        {
            et[i] = new EditText(context);
            et[i].setHint((i+1) +"번째 참가자 이름");
            et[i].setHintTextColor(Color.parseColor("#808080"));
            et[i].setTextColor(Color.parseColor("#000050"));
            et[i].setTypeface(ty);//글꼴설정
            et[i].setSingleLine(true); //한줄
            et[i].setFilters(new InputFilter[] {new InputFilter.LengthFilter(5)});//글자수 제한
            layout.addView(et[i]);
            if(data.name.isEmpty() || data.name.size() <= i)
                continue;
            et[i].setText(data.name.get(i));
        }

        skip  = (Button)findViewById(R.id.skip);
        check = (Button)findViewById(R.id.check);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.name.clear();
                data.IsSkip_name = true;
                Intent intent = new Intent(getApplicationContext(), input_bomb.class);
                startActivity(intent);
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<N;i++) {
                    if (et[i].getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(),
                                (i + 1) + "번째 참가자 이름을 채워주세요.",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                data.name.clear();
                for(int i=0;i<N;i++){
                    data.name.add(et[i].getText().toString()) ;

                }
                data.IsSkip_name = false;
                Intent intent = new Intent(getApplicationContext(), input_bomb.class);
                startActivity(intent);
            }
        });
        TextView pre = (TextView)findViewById(R.id.pre);
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); //이전화면으로 돌아가기
            }
        });
    }
}

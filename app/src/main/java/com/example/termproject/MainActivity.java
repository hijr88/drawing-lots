package com.example.termproject;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.io.File;
import java.util.LinkedList;

//초기화면
public class MainActivity extends AppCompatActivity {
    public static Activity main_activity;

    ImageView popcorn; //메인페이지 상단그림
    TextView participant, bomb; //참가인원, 꽝인원
    public static int p1=2,b1=1; //참가,꽝 초기 인원수;
    int max=10,min=2; //
    Button minus_p,plus_p,minus_b,plus_b, start;
    final Context context = this;
    Intent intent;
    AlertDialog.Builder dlg;
    @Override
    public void onBackPressed() {
        //SpannableStringBuilder를 사용하면 코드상에서 문자의 색,크기를 바꿀수 있다
        String []ss = {"종료하시겠습니까?","확인","취소"};
        SpannableStringBuilder []ssBuilser = new SpannableStringBuilder[ss.length];
        for(int i=0;i<ss.length;i++){
            ssBuilser[i] = new SpannableStringBuilder(ss[i]);
            ssBuilser[i].setSpan(new ForegroundColorSpan(Color.WHITE), 0, ss[i].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        dlg = new AlertDialog.Builder(context);
        dlg.setTitle(ssBuilser[0]);
        dlg.setPositiveButton(ssBuilser[1], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        dlg.setNegativeButton(ssBuilser[2],null);
        dlg.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater mInflater = getMenuInflater();
        mInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.appInformation:
                intent = new Intent(getApplicationContext(), appInformation.class);
                startActivity(intent);
                return true;
            case R.id.developerInformation:
                intent = new Intent(getApplicationContext(), developerInformation.class);
                startActivity(intent);
                return true;
            case R.id.view_record:
                Intent intent = new Intent(getApplicationContext(), view_record.class);
                startActivity(intent);
                return true;
            case R.id.delete_record:
                deleteFile();
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_activity = MainActivity.this;

        /***메인페이지 상단그림***/
        popcorn = (ImageView)findViewById(R.id.popcorn);
        GlideDrawableImageViewTarget gifImage;
        gifImage = new GlideDrawableImageViewTarget(popcorn);
        Glide.with(this).load(R.drawable.popcorn).into(gifImage);
        /***메인페이지 상단그림***/

        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0); //앱바 그림자없애기

        participant = (TextView)findViewById(R.id.participant);
        bomb = (TextView)findViewById(R.id.bomb);
        participant.setText("   "+p1+"명"); bomb.setText("   "+b1+"명");

        minus_p = findViewById(R.id.minus_p);
        plus_p = findViewById(R.id.plus_p);

        minus_p.setOnClickListener(new View.OnClickListener() { //참가인원수를 줄임
            @Override
            public void onClick(View view) {
                if(p1==min) p1 = max;
                else p1--;
                if(p1<=b1){  //참가인원보다 꽝인원수가 많거나 같을경우 꽝인원을 자동으로 줄인다.
                    b1 = p1-1;
                    bomb.setText("   "+b1+"명");
                }
                participant.setText("   "+p1+"명");
            }
        });
        plus_p.setOnClickListener(new View.OnClickListener() { //참가인원수를 늘림
            @Override
            public void onClick(View view) {
                if(p1==max) p1 = min;  //참가인원이 최대일경우 최소로 돌아간다.
                else p1++;
                participant.setText("   "+p1+"명");
            }
        });

        minus_b = findViewById(R.id.minus_b);
        plus_b = findViewById(R.id.plus_b);

        minus_b.setOnClickListener(new View.OnClickListener() { //꽝 인원수를 줄임
            @Override
            public void onClick(View view) {
                if(b1==1) b1 = p1-1;  //꽝 인원수가 최소보다 작을경우 참가인원 -1명으로
                else b1--;
                bomb.setText("   "+b1+"명");
            }
        });
        plus_b.setOnClickListener(new View.OnClickListener() { //꽝 인원수를 늘림
            @Override
            public void onClick(View view) {
                if((b1 +1)  == p1) b1=1;  //꽝인원수가 참가인원이랑 같아지면 최소1명으로 돌아감
                else b1++;
                bomb.setText("   "+b1+"명");
            }
        });

        participant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  //참가인원 선택할수있는 다이얼로그
               final String [] p_list = new String[max-1];
               for(int i=0; i<p_list.length; i++){
                   p_list[i] = (i+2) + "명";
               }
                dlg = new AlertDialog.Builder(
                        MainActivity.this);
                String p_count="참가 인원";
                SpannableStringBuilder ssBuilser = new SpannableStringBuilder(p_count);
                ssBuilser.setSpan(new ForegroundColorSpan(Color.WHITE), 0, p_count.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                dlg.setTitle(ssBuilser);

                dlg.setSingleChoiceItems(p_list, (p1-2), new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int i) {
                       Toast.makeText(getApplicationContext(),
                               p_list[i] + " 선택했습니다.",
                               Toast.LENGTH_SHORT).show();
                       p1 = i+2;
                       if(p1<=b1){
                           b1 = p1 -1;
                           bomb.setText("   "+b1+"명");
                       }
                       participant.setText("   "+p1+"명");
                       dialog.dismiss();
                   }
               });
                AlertDialog alertDialog = dlg.create();
                alertDialog.show();
            }
        });
        bomb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {     //꽝인원 선택할수있는 다이얼로그
                final String [] b_list = new String[p1 -1];
                for(int i=0; i<b_list.length; i++){
                    b_list[i] = (i+1) + "명";
                }
                dlg = new AlertDialog.Builder(
                        context);
                String p_count="꽝 인원";
                SpannableStringBuilder ssBuilser = new SpannableStringBuilder(p_count);
                ssBuilser.setSpan(new ForegroundColorSpan(Color.WHITE), 0, p_count.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                dlg.setTitle(ssBuilser);


                dlg.setSingleChoiceItems(b_list, (b1-1), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Toast.makeText(getApplicationContext(),
                                b_list[i] + " 선택했습니다.",
                                Toast.LENGTH_SHORT).show();
                        b1 = i+1;
                        bomb.setText("   "+b1+"명");
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = dlg.create();
                alertDialog.show();
            }
        });
        start = (Button)findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), input_name.class);
                startActivity(intent);
            }
        });
        data.name = new LinkedList<String>(); //메인이 처음실행되면서 생성
        data.bomb = new LinkedList<String >();
    }
    public  void deleteFile(){  //기록삭제
        {
            File file = new File(data.inner_dir);
            File[] list = file.listFiles();

            for(int i=0;i<list.length;i++){
                String tmp = list[i].getName();
                if(tmp.equals(data.FileName)) list[i].delete();
            }
            Toast.makeText(getApplicationContext(),
                     "기록을 지웠습니다.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}

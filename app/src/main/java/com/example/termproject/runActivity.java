package com.example.termproject;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;

//실행화면
public class runActivity extends Activity {
    final int N = MainActivity.p1;  //참가자 인원
    final int B = MainActivity.b1;  //꽝 인원
    FrameLayout layout;
    int pointX, pointY; //좌표
    public static int count;
    String[] name;
    String[] bomb; //벌칙명
    String[] bo;   //벌칙 섞기
    String V = "승";
    Context context;
    Animation rotate;
    final public static ImageView image[] = new ImageView[MainActivity.p1];
    @Override
    public void onBackPressed() { //뒤로가기 버튼
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.running);
        context = this;
        rotate = AnimationUtils.loadAnimation(context,R.anim.scale);
        count = 0;
        getdata();
        shuffle();
        final TextView txt = new TextView(context);

        layout = (FrameLayout) findViewById(R.id.run_layout);
        ViewTreeObserver vto = layout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                pointX = layout.getWidth();  //레이아웃 가로,세로길이
                pointY = layout.getHeight();
                FrameLayout.LayoutParams []params = new FrameLayout.LayoutParams[N];
                int Rotation[] = {45,90,135,180,225,270,315,360};

                int character[] = {R.drawable.apeach1,R.drawable.apeach2,R.drawable.apeach3,R.drawable.apeach4,
                        R.drawable.frodo1,R.drawable.frodo2,R.drawable.jayg1,R.drawable.jayg2,R.drawable.muzi1,
                        R.drawable.muzi2,R.drawable.neo1,R.drawable.neo2,R.drawable.ryan1,R.drawable.ryan2,
                        R.drawable.ryan3,R.drawable.tube1,R.drawable.tube2,R.drawable.tube3,R.drawable.tube4,
                        R.drawable.tube5};

                for(int i=0;i<N;i++){  //이미지 랜덤위치에 배치
                    int x,y;
                    do{
                        x = (int) (Math.random() * pointX);
                    }while( x>(pointX-300) );
                    do{
                        y = (int) (Math.random() * pointY);
                    }while( y>(pointY-300) );

                    params[i] = new FrameLayout.LayoutParams(300,300);

                    params[i].leftMargin = x;
                    params[i].topMargin  = y;
                    image[i] = new ImageView(context);
                    int random_image = (int) (Math.random() * character.length);
                    image[i].setImageResource(character[random_image]);
                    int degree = (int) (Math.random() * Rotation.length);
                    image[i].setRotation(Rotation[degree]);
                    layout.addView(image[i],params[i]);
                }

                for(int j=0;j<N;j++){
                    final int m = j;
                    image[j].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            for(int h=0;h<N;h++)
                                image[h].setEnabled(false);
                            rotateAni(image[m]);
                            rotate.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {}
                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    image[m].setVisibility(View.GONE);
                                    count++;
                                    if(!data.shuffle_result[m][1].equals(V)){ //꽝일경우
                                        bombActivity.M =m;
                                        Intent intent = new Intent(getApplicationContext(), bombActivity.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        victoryActivity.M =m;
                                        Intent intent = new Intent(getApplicationContext(), victoryActivity.class);
                                        startActivity(intent);
                                    }
                                }
                                @Override
                                public void onAnimationRepeat(Animation animation) {}
                            });
                        }
                    });
                }
                TextView re = (TextView)findViewById(R.id.re);
                TextView move_home = (TextView)findViewById(R.id.move_home);
                re.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        recreate(); //다시하기
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

                ViewTreeObserver obs = layout.getViewTreeObserver();
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
                    obs.removeOnGlobalLayoutListener(this);
                } else{
                    obs.removeOnGlobalLayoutListener(this);
                }
            }
        });
    }
    private void getdata(){  //이름,벌칙명 설정하기
        if(data.IsSkip_name){ //이름 건너뛰기 했을경우
            name = new String[N];
            for(int i=0;i<N;i++)
                name[i] = (i+1) +"번째 참가자";
        }
        else name=data.name.toArray(new String[data.name.size()]);
        if(data.IsSkip_bomb){ //벌칙 건너뛰기 했을경우
            bomb = new String[B];
            for(int i=0;i<B;i++)
                bomb[i] = "꽝";
        }
        else bomb=data.bomb.toArray(new String[data.bomb.size()]);
    }
    private void shuffle(){
        bo = new String[N];
        int c=0;
        for(int i=0;i<N;i++){  //벌칙 인원만큼 할당
            if(i<B) bo[i] = "1";
            else bo[i] = "0";
        }
        Collections.shuffle(Arrays.asList(bo));
        Collections.shuffle(Arrays.asList(bomb));

        for(int i=0;i<N;i++){
            if(bo[i]=="1") bo[i] = bomb[c++];
            else bo[i] = V;
        }

        data.result = new String[N][2]; //이름과 벌칙을 지정하기

        for(int i=0;i<N;i++){
            for(int j=0;j<2;j++){
                if(j==0) data.result[i][j] = name[i];
                else data.result[i][j] = bo[i];
            }
        }
        int r;
        //다시 섞기//
        data.shuffle_result = new String[N][2]; //이름과 벌칙을 지정하기
        System.arraycopy(data.result,0,data.shuffle_result,0,data.result.length); //배열복사
        for(int i=0;i<N;i++){
            if(N==2)
                r = (int)(Math.random() * N);
            else {
                do{
                    r = (int)(Math.random() * N);
                }while (i==r);
            }
            String[]tmp = data.shuffle_result[r];
            data.shuffle_result[r] = data.shuffle_result[i];
            data.shuffle_result[i] = tmp;
        }
    }
    public void rotateAni(ImageView img){
        img.startAnimation(rotate);
    }
}

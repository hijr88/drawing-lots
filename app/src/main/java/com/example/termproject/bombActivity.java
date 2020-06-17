package com.example.termproject;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

//꽝 화면
public class bombActivity extends Activity {
    final int N = MainActivity.p1;  //참가자 인원
    public static int M;
    Context context = this;
    MediaPlayer mp;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mp != null){
            mp.release();
            mp = null;
        }
    }
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bomb);

        mp = MediaPlayer.create(this,R.raw.hing);
        mp.start();

        int character[] = {R.drawable.g_bomb1,R.drawable.g_bomb2,R.drawable.g_bomb3,R.drawable.g_bomb4,
                R.drawable.g_bomb5,R.drawable.g_bomb6};
        int random_image = (int) (Math.random() * character.length);
        ImageView bomb_img = (ImageView)findViewById(R.id.bomb_img);
        GlideDrawableImageViewTarget gifImage;
        gifImage = new GlideDrawableImageViewTarget(bomb_img);
        Glide.with(this).load(character[random_image]).into(gifImage);

        Button btn = (Button)findViewById(R.id.finish);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(runActivity.count == N) {
                    Intent intent = new Intent(getApplicationContext(), resultActivity.class);
                    startActivity(intent);
                }
                else {
                    for(int h=0;h<N;h++)
                        runActivity.image[h].setEnabled(true);
                    finish();
                }
            }
        });

        TextView name = (TextView)findViewById(R.id.bomb_name);
        name.setText(data.shuffle_result[M][0]+"\n"+data.shuffle_result[M][1]);
    }
}

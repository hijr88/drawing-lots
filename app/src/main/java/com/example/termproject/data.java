package com.example.termproject;

import android.app.Activity;

import java.util.LinkedList;

public class data extends Activity {
    public static LinkedList<String> name;  //이름입력
    public static LinkedList<String> bomb;  //벌칙명입력
    public static String[][] result;
    public static String[][] shuffle_result;
    public static boolean IsSkip_name;
    public static boolean IsSkip_bomb;
    public static String FileName = "result.txt";

    public static String inner_dir = "/data/data/com.example.termproject/files/";

}

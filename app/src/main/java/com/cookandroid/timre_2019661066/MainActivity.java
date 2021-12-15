package com.cookandroid.timre_2019661066;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    static final long START_TIME = 1000;
    TextView TextViewCountDown;
    TextView TextViewCount;
    Button ButtonsStartPause;
    Button ButtonsReset;
    Button ButtonSetReset;
    Button ButtonListReset;
    Button Button_Save;
    ImageButton secondsUp;
    ImageButton secondsDown;
    ImageButton minutesUp;
    ImageButton minutesDown;

    ////////////////////////////////////////////////////////////////////////
    private ArrayList<MainData> arrayList;
    private MainAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    ////////////////////////////////////////////////////////////////////////
    android.os.CountDownTimer CountDownTimer;
    boolean TimerRunning;
    long Time = START_TIME; //시작 시간
    int scout; //세트 횟수
//////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextViewCountDown = findViewById(R.id.text_view_Countdown);
        TextViewCount = findViewById(R.id.Count);
        ButtonsStartPause = findViewById(R.id.button_start_pause);
        ButtonsReset = findViewById(R.id.button_reset);
        ButtonSetReset = findViewById(R.id.button_Set_reset);
        ButtonListReset = findViewById(R.id.button_List_reset);
        Button_Save = findViewById(R.id.button_Save);

        secondsUp = (ImageButton)findViewById(R.id.button_S_up);
        secondsDown = (ImageButton)findViewById(R.id.button_S_down);
        minutesUp =   (ImageButton)findViewById(R.id.button_M_up);
        minutesDown = (ImageButton)findViewById(R.id.button_M_down);
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 리사이클러뷰
//
        recyclerView = (RecyclerView)findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
//// 어레이리스트
        arrayList = new ArrayList<>();
//
        mainAdapter = new MainAdapter(arrayList);
        recyclerView.setAdapter(mainAdapter);
//
//
        Button_Save.setOnClickListener(new View.OnClickListener() {
            ////////////////////////////////////////////////////////////////////////////////////////// 남은시간
            @Override
            public void onClick(View v) {
                int minutes = (int) (Time / 1000) / 60;
                int seconds = (int) (Time / 1000) % 60;
                String timeFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);//시간확인
                String countUp =  String.format(Locale.getDefault(),"%02d",scout); ////// 세트확인

                //////////////////////////////////////////////////////////////////////////////////////////////////
                MainData  mainData = new MainData(R.mipmap.ic_launcher, countUp + "세트", timeFormatted);
                arrayList.add(mainData);
                mainAdapter.notifyDataSetChanged();
            }
        });
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////// 시간조절 버튼

        secondsUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Time = Time + 1000;
                updateCountDownText();
            }
        });
        secondsDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Time = Time - 1000;
                updateCountDownText();
            }
        });
        minutesUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Time = Time + 60000;
                updateCountDownText();
            }
        });
        minutesDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Time = Time - 60000;
                updateCountDownText();
            }
        });
//////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////시작 버튼 누름
        ButtonsStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TimerRunning){
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        //리셋 버튼 누름
        ButtonsReset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
        updateCountDownText();

        ButtonSetReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scout = 0;
                updateCountText();
            }
        });
        ButtonListReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.clear();
            }
        });
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////
    void startTimer() {
        CountDownTimer = new CountDownTimer(Time,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Time = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                scout = scout + 1;
                TimerRunning = false;
                ButtonsStartPause.setText("시작");
                ButtonsStartPause.setVisibility(View.INVISIBLE);
                ButtonsReset.setVisibility(View.VISIBLE);
                updateCountText();
            }
        }.start();

        TimerRunning = true;
        ButtonsStartPause.setText("멈춤");
        ButtonsReset.setVisibility(View.INVISIBLE);
    }
    void pauseTimer() {
        CountDownTimer.cancel();
        TimerRunning = false;
        ButtonsStartPause.setText("시작");
        ButtonsReset.setVisibility(View.VISIBLE);
    }
    void resetTimer() {
        Time = START_TIME;
        updateCountDownText();
        ButtonsStartPause.setVisibility(View.VISIBLE);
        ButtonsReset.setVisibility(View.INVISIBLE);
    }

    void updateCountDownText(){
        int minutes = (int) (Time / 1000) / 60;
        int seconds = (int) (Time / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        TextViewCountDown.setText(timeFormatted);
    }
    void updateCountText()
    {
        String countUp =  String.format(Locale.getDefault(),"%02d",scout);
        TextViewCount.setText(countUp);
    }

}
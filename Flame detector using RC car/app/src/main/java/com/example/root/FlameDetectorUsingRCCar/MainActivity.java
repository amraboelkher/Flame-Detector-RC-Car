package com.example.root.FlameDetectorUsingRCCar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    static String Tag = "MainActivity";
    private static int TOBLUETOOTH = 1;
    Button W , E , S , N , SW , SE , NE , NW , toBluetooth;
    private static boolean set = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        W = (Button)(findViewById(R.id.W));
        E = (Button)(findViewById(R.id.E));
        N = (Button)(findViewById(R.id.N));
        S = (Button)(findViewById(R.id.S));

        SW = (Button)(findViewById(R.id.SW));
        SE = (Button)(findViewById(R.id.SE));
        NE = (Button)(findViewById(R.id.NE));
        NW = (Button)(findViewById(R.id.NW));

        toBluetooth = (Button)(findViewById(R.id.Bluetooth));

        init(set);

        W.setOnTouchListener(new myHandler(1));
        E.setOnTouchListener(new myHandler(2));
        N.setOnTouchListener(new myHandler(3));
        S.setOnTouchListener(new myHandler(4));

        NE.setOnTouchListener(new myHandler(5));
        NW.setOnTouchListener(new myHandler(6));
        SW.setOnTouchListener(new myHandler(7));
        SE.setOnTouchListener(new myHandler(8));

        toBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(new Intent(MainActivity.this , Bluetooth.class) , 1);
            }
        });
    }



    private void init(Boolean Activated){

        N.setEnabled(Activated);
        S.setEnabled(Activated);
        E.setEnabled(Activated);
        W.setEnabled(Activated);

        NE.setEnabled(Activated);
        NW.setEnabled(Activated);
        SE.setEnabled(Activated);
        SW.setEnabled(Activated);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == TOBLUETOOTH){
            if(resultCode == RESULT_OK){
                boolean Activated = Boolean.parseBoolean(data.getStringExtra("active"));
                init(Activated);
                set = Activated;


            }
        }
    }
}
class myHandler implements View.OnTouchListener{
    private Handler handler = null;
    int output ;
    Runnable rr  ;
    static int cnt = 0;
    private static String Tag = "myHandler";
    public myHandler(int s){
        output = s;
        rr = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(rr , 301);
            }
        };
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(handler != null) return true ; 
                handler = new Handler();
                handler.postDelayed(rr , 301);
                break;
            case MotionEvent.ACTION_UP:
                if(handler == null) return true; 
                handler.removeCallbacks(rr);
                handler = null;
                break;
        }
        return false;
    }
}

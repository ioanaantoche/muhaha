package com.example.p;

import ro.pub.cti.drawing.DrawingView;
import ro.pub.cti.utils.CommunicationInfo;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends Activity {

	DrawingView drawView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);        
        FrameLayout f = (FrameLayout) findViewById(R.id.frame);
        DrawingView dv = new DrawingView(this);
        f.addView(dv);
        
        ((Button) findViewById(R.id.button1)).setOnClickListener(new DrawingRequestButtonListener(this, dv));
        CommunicationInfo.getCommunicationInfo("http", "192.168.0.192", "9000");
        System.out.println(CommunicationInfo.getCommunicationInfo());
    }
}

package com.library1.example.perkins.opencvedgetest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.library1.example.perkins.edgedetectorlib.EdgeDetector;

import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {

    ImageView img;
    boolean bShow = false;
    Bitmap bmpOriginal = null;
    EdgeDetector myDetector = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!OpenCVLoader.initDebug()) {
            Log.e(this.getClass().getSimpleName(), "  OpenCVLoader.initDebug(), not working.");
        } else {
            Log.d(this.getClass().getSimpleName(), "  OpenCVLoader.initDebug(), working.");
        }

        //load and show source
        img = (ImageView)findViewById(R.id.image) ;
        img.setImageResource(R.drawable.img1);

        bmpOriginal= BitmapFactory.decodeResource(getResources(),R.drawable.img1);
        img.setImageBitmap(bmpOriginal);
    }


    public void doToggle(View view) {
        bShow = !bShow;
        if ( myDetector == null)
            myDetector= new EdgeDetector();

        //default to show orig
        Bitmap bmpOutlined = bmpOriginal;

        if (bShow)
            //change if its time
            bmpOutlined = myDetector.outline_Bitmap(bmpOriginal);

        img.setImageBitmap(bmpOutlined);
    }
}

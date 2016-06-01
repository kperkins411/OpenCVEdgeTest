package com.library1.example.perkins.opencvedgetest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.library1.example.perkins.edgedetectorlib.ColorChange_Processor;
import com.library1.example.perkins.edgedetectorlib.EdgeDetect_Processor;
import com.library1.example.perkins.edgedetectorlib.Image_Pipeline;

import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {

    ImageView img;
    boolean bShow = false;
    Bitmap bmpOriginal = null;

    boolean bEdgeDetection = false;
    boolean bColorChange = false;
    boolean bShowText = false;

    EdgeDetect_Processor myDetector = null;
    ColorChange_Processor myImgColorChange;

    Bitmap bmpChanged = null;
    int i = 0;


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


    public void oncheckBox_EdgeDetection(View view) {
        bEdgeDetection = ((CheckBox) view).isChecked();
    }

    public void oncheckBox_ColorChange(View view) {
        bColorChange = ((CheckBox) view).isChecked();
    }

    public void oncheckBox_Show_Text(View view) {
        bShowText = ((CheckBox) view).isChecked();
    }

    public void dobutton_RunPipeline(View view) {
//        //default to show orig
//        if (bmpChanged == null)
//            bmpChanged = bmpOriginal;

        Image_Pipeline.Builder myBuilder = new Image_Pipeline.Builder(bmpOriginal);
        if (bColorChange)
            myBuilder.setColorChange();
        if (bEdgeDetection)
            myBuilder.setEdgeDetect();
        if (bShowText)
            myBuilder.setShowText();

        Image_Pipeline mypipeline = myBuilder.build();

        mypipeline.setText(Integer.toString(i));
        i++;

        bmpChanged = mypipeline.process(bmpOriginal);

        img.setImageBitmap(bmpChanged);
    }


}


//    public void doToggle(View view) {
//        bShow = !bShow;
//        if ( myDetector == null)
//            myDetector= new EdgeDetect_Processor();
//
//        //default to show orig
//        Bitmap bmpOutlined = bmpOriginal;
//
//        if (bShow)
//            //change if its time
//            bmpOutlined = myDetector.process(bmpOriginal);
//
//        img.setImageBitmap(bmpOutlined);
//    }
//
//    public void doChangeImageColor(View view) {
//        //default to show orig
//        if (bmpChanged == null)
//            bmpChanged = bmpOriginal;
//
//        if ( myImgColorChange == null)
//            myImgColorChange = new ColorChange_Processor();
//
//        bmpChanged = myImgColorChange.process(bmpChanged);
//
//        img.setImageBitmap(bmpChanged);
//    }
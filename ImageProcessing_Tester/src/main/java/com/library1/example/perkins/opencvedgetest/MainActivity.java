package com.library1.example.perkins.opencvedgetest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;

//import com.library1.example.perkins.edgedetectorlib.ColorChange_Processor;
//import com.library1.example.perkins.edgedetectorlib.EdgeDetect_Processor;
import com.library1.example.perkins.edgedetectorlib.Image_Pipeline;

import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {

    ImageView img;
    boolean bShow = false;
    Bitmap bmpOriginal = null;

    boolean bEdgeDetection = false;
    boolean bColorChange = false;
    boolean bShowText = false;

    Bitmap bmpChanged;
    int i;
    RadioButton radioBRG;
    RadioButton radioGBR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!OpenCVLoader.initDebug()) {
            Log.e(this.getClass().getSimpleName(), "  OpenCVLoader.initDebug(), not working.");
        } else {
            Log.d(this.getClass().getSimpleName(), "  OpenCVLoader.initDebug(), working.");
        }

        radioBRG = (RadioButton)findViewById(R.id.radioBRG);
        radioGBR = (RadioButton)findViewById(R.id.radioGBR);

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
        radioBRG.setEnabled(bColorChange);
        radioGBR.setEnabled(bColorChange);
    }

    public void oncheckBox_Show_Text(View view) {
        bShowText = ((CheckBox) view).isChecked();
    }

    boolean bCycle = false;
    public void dobutton_ChangeColorShift(View view) {
        bCycle = true;
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
            //make this one the last in the list otherwise the edge or color filter may change it
            myBuilder.setShowText();

        //create the pipeline
        Image_Pipeline mypipeline = myBuilder.build();

        //modify the text to appear
        mypipeline.setText(Integer.toString(i++));

        //change the color shift from BGR to GBR
        if (radioGBR.isChecked())
            mypipeline.cycleColorShift();

        //applies all the image transforms one after another and returns modded bitmap
        bmpChanged = mypipeline.process(bmpOriginal);

        img.setImageBitmap(bmpChanged);
    }



}


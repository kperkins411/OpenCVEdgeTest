package com.library1.example.perkins.edgedetectorlib;

import android.graphics.Bitmap;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

/**
 * Created by Perkins on 6/1/2016.
 */
public class ShowText_Processor implements Processor {
    private int width = Constants.UNINITIALIZED;
    private int height = Constants.UNINITIALIZED;

    private String text;

    /**
     * create with width and height we will use for all images
     */
    public ShowText_Processor(int width, int height) {
        this.width = width;
        this.height = height;
        text = new String("?");
    }

    @Override
    public Bitmap process(Bitmap bmp_original) {
        if (bmp_original == null)
            throw new IllegalArgumentException(
                    "The provided Bitmap was null; please provide a valid bitmap!");

        //create empty MATs
        Mat im_original = new Mat(height, width, CvType.CV_8UC4);

        //convert source image to Mat
        Utils.bitmapToMat(bmp_original, im_original, true);

        Imgproc.putText(im_original, text, new org.opencv.core.Point(height / 4, width / 4), Core.FONT_ITALIC, new Double(2), new Scalar(255));

        Bitmap bitMap_combined = Bitmap.createBitmap(im_original.cols(), im_original.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(im_original, bitMap_combined);

        return bitMap_combined;
    }

    public void setText(String text) {
        this.text = text;
    }
}

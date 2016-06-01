package com.library1.example.perkins.edgedetectorlib;

import android.graphics.Bitmap;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
/**
 * <p>Description:<br>
 * Displays white text in upper left part of the image
 *
 * <p>Requirements:<br>
 * openCV for android see the following link for install help
 * http://stackoverflow.com/questions/27406303/opencv-in-android-studio
 *
 * Note that this is package private and thus not accessible outside of this package<BR>

 @author Keith Perkins
 */
class ShowText_Processor implements Processor {
    //TODO find a better way to decide scale and where text goes,
    //TODO this will not look good on different screen sizes
    private static final Double SCALE = new Double(1);
    private static final double X_START = 10;
    private static final double Y_START = 30;
    private int width = Constants.UNINITIALIZED;
    private int height = Constants.UNINITIALIZED;

    private static final int WHITE = 255;
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

        //place the text
        org.opencv.core.Point point_ref_from_upper_right =  new org.opencv.core.Point(X_START,Y_START); //new org.opencv.core.Point((im_original.rows() / 4) *2, (im_original.cols() / 5) * 4)
        Imgproc.putText(im_original, text,point_ref_from_upper_right , Core.FONT_ITALIC, SCALE, new Scalar(WHITE));

        Bitmap bitMap_combined = Bitmap.createBitmap(im_original.cols(), im_original.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(im_original, bitMap_combined);

        return bitMap_combined;
    }

    /**
     * changes the text that is displayed
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }
}

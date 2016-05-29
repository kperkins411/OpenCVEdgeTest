package com.library1.example.perkins.edgedetectorlib;

import android.graphics.Bitmap;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * <p>Description:<br>
 * Takes an image, uses Canny to find edges and store in Mat im_canny,
 * then merges the found edges with original image, has sharpening effect on image
 *
 * <p>Requirements:<br>
 * openCV for android see the following link for install help
 * http://stackoverflow.com/questions/27406303/opencv-in-android-studio
 * <p>Usage:<br>
 EdgeDetector myDetector= new EdgeDetector();<br>
 Bitmap bmpOutlined = myDetector.outline_Bitmap(bmpOriginal);<br>

 @author Keith Perkins
 */
public class EdgeDetector {
    private static final int UNINITIALIZED = -1;
    private int width = UNINITIALIZED;
    private int height = UNINITIALIZED;

    // for the canny edge detection algorithm, play with these to see different results
    private  static final int THRESHOLD_LOW_DEFAULT = 70;
    private  static final int THRESHOLD_HIGH_DEFAULT = 3*70;

    private int threshold_low;
    private int threshold_high;

    /**
     * creates with custom Canny thresholds
     */
    public EdgeDetector(int threshold_low,int threshold_high){
        setParams(threshold_low,threshold_high);
    }

    /**
     * create with default Canny thresholds
     */
    public EdgeDetector() {
        this(THRESHOLD_LOW_DEFAULT,THRESHOLD_HIGH_DEFAULT);
    }

    /**
     * These are used  by canny algo
     * @param threshold_low
     * @param threshold_high
     */
    public void setParams(int threshold_low,int threshold_high ){
        this.threshold_low = threshold_low;
        this.threshold_high = threshold_high;
    }

    /**
     * Canny implementation of an edge detector pass in a valid bitmap and it returns a bitmap
     * with the same images only the edges are outlined
     * If a null object parameter is passed to any method, then a
     * <tt>IllegalArgumentException</tt> will be thrown.
     * @param bmp_original
     * @return bitMap_combined the edge detected version of bmp_original
     *
     * @exception IllegalArgumentException do not pass in a null bmp_original
     */
    public Bitmap outline_Bitmap(Bitmap bmp_original){
        if (bmp_original ==null)
            throw new IllegalArgumentException(
                    "The provided Bitmap was null; please provide a valid bitmap!");

        if (width == UNINITIALIZED) {
            width = bmp_original.getWidth();
            height = bmp_original.getHeight();
        }
        //create empty MATs
        Mat im_original = new Mat (height, width, CvType.CV_8UC4);
        Mat im_canny= new Mat (height, width, CvType.CV_8UC4);
        Mat im_cannyInvert= new Mat (height, width, CvType.CV_8UC4);
        Mat im_combined= new Mat (height, width, CvType.CV_8UC4);

        //convert source image to Mat
        Utils.bitmapToMat(bmp_original, im_original,true);

        //create the canny edge detected image and display
        Imgproc.Canny(im_original, im_canny, threshold_low, threshold_high);

        //invert canny (Black<->White)
        Core.bitwise_not ( im_canny, im_cannyInvert );

        //convert from 1 channel greyscale to three channel color
        Mat im_cannyColor = new Mat (height, width, CvType.CV_8UC4);
        Imgproc.cvtColor(im_cannyInvert,im_cannyColor,Imgproc.COLOR_GRAY2BGRA);

        //and the two images together to get popped lines and display
        Core.bitwise_and(im_original,im_cannyColor,im_combined);

        //convert and return
        Bitmap bitMap_combined = Bitmap.createBitmap(im_combined.cols(), im_combined.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(im_combined, bitMap_combined);

        return bitMap_combined;
    }

}

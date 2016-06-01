package com.library1.example.perkins.edgedetectorlib;

import android.graphics.Bitmap;

/**
 * <p>Description:<br>
 * Takes an image, and rotates the RGB colors
 * call rotateColors           RGB -> BRG
 * call rotateColors again     BRG -> GBR
 * call rotateColors again     GBR -> RGB
 * <p/>
 * <p>Requirements:<br>
 * none<BR>
 * <p>Usage:<br>
 * ColorChange_Processor myColorChanger = new ColorChange_Processor();<br>
 * Bitmap bmpOutlined = myColorChanger.rotateColors(bmpOriginal);<br>
 *
 * @author Keith Perkins
 */
public class ColorChange_Processor implements Processor {

    private int width = Constants.UNINITIALIZED;
    private int height = Constants.UNINITIALIZED;

    //used to bit shift
    private static final int EIGHT_BIT_POSITIONS = 8;
    private static final int SIXTEEN_BIT_POSITIONS = 16;
    int sRight = EIGHT_BIT_POSITIONS;
    int sLeft = SIXTEEN_BIT_POSITIONS;

    /**
     * create with width and height we will use for all images
     */
    public ColorChange_Processor(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * @param bmp_original
     * @return bitMap_colorChanged a color shifted version of
     */
    public Bitmap process(Bitmap bmp_original) {

        if (bmp_original == null)
            throw new IllegalArgumentException(
                    "The provided Bitmap was null; please provide a valid bitmap!");

        //copy the pixels to operate on
        int[] pix = new int[width * height];
        bmp_original.getPixels(pix, 0, width, 0, 0, width, height);

        //holds what is about to be right shifted to oblivion
        int shifted_out_color = 0;  //  0x00BB0000
        int alpha = 0;  //  0xAA000000
        int two_remaining_colors = 0;  //  0x0000RRGG
        //+ 0xAABBRRGG

        //would love matrix version of the following
        for (int i = 0; i < pix.length; i++) {
            //save whats about to be shifted out right and move it over to the right place
            shifted_out_color = (pix[i] & 0x000000ff) << sLeft;

            //save alpha
            alpha = pix[i] & 0xff000000;

            //get RRGG and shift right
            two_remaining_colors = pix[i] >> sRight & 0x0000ffff;

            pix[i] = alpha | shifted_out_color | two_remaining_colors;
        }

        //convert and return
        Bitmap bitMap_colorChanged = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitMap_colorChanged.setPixels(pix, 0, width, 0, 0, width, height);

        return bitMap_colorChanged;
    }


}

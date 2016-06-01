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
 * Note that this is package private and thus not accessible outside of this package<BR>
 *
 * @author Keith Perkins
 */
class ColorChange_Processor implements Processor {

    public enum COLOR_SHIFT{BRG,GBR};   //notice there is no RGB as that would be a pointless NO-OP

    private int width = Constants.UNINITIALIZED;
    private int height = Constants.UNINITIALIZED;

    //used to bit shift
    private static final int EIGHT_BIT_POSITIONS = 8;
    private static final int SIXTEEN_BIT_POSITIONS = 16;

    /**
     * create with width and height we will use for all images
     */
    public ColorChange_Processor(int width, int height) {
        this.width = width;
        this.height = height;
        this.cs = COLOR_SHIFT.BRG;
        setColorShift(this.cs);
    }

    private int AlphaMask = 0xff000000;
    private int copy1Mask;
    private int shift1amount;
    private int copy2Mask ;
    private int shift2amount;

    private COLOR_SHIFT cs = ColorChange_Processor.COLOR_SHIFT.BRG;
    public void cycleColorShift(){
        if (cs == COLOR_SHIFT.BRG)
            cs = COLOR_SHIFT.GBR;
        else
            cs = COLOR_SHIFT.BRG;
        setColorShift(cs);
    }

    private void setColorShift(COLOR_SHIFT newCS) {
        switch(newCS) {
            case BRG:
                copy1Mask = 0x0000ffff;
                shift1amount = EIGHT_BIT_POSITIONS;
                copy2Mask = 0x000000ff;
                shift2amount = SIXTEEN_BIT_POSITIONS;
                break;
            case GBR:
                copy1Mask = 0x000000ff;
                shift1amount = SIXTEEN_BIT_POSITIONS;
                copy2Mask = 0x0000ffff;
                shift2amount = EIGHT_BIT_POSITIONS;
                break;
        }
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
        int part2 = 0;  //  0x00BB0000
        int alpha = 0;  //  0xAA000000
        int part1 = 0;  //  0x0000RRGG
        //+ 0xAABBRRGG

        //would love matrix version of the following
        for (int i = 0; i < pix.length; i++) {
            //save whats about to be shifted out right and move it over to the right place
            part2 = (pix[i] & copy2Mask) << shift2amount;

            //save alpha
            alpha = pix[i] & AlphaMask;

            //get RRGG and shift right
            part1 = pix[i] >> shift1amount & copy1Mask;

            pix[i] = alpha | part2 | part1;
        }

        //convert and return
        Bitmap bitMap_colorChanged = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitMap_colorChanged.setPixels(pix, 0, width, 0, 0, width, height);

        return bitMap_colorChanged;
    }
    /**
     * changes the text that is displayed
     * @param text
     */
    public void setText(String text) {
        //this.text = text;
    }
}

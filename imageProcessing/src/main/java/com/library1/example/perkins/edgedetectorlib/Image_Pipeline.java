package com.library1.example.perkins.edgedetectorlib;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description:<br>
 * Flexible image processing pipeline
 * creates a list of classes that implement the processor interface
 * And then executes each classes process(..) method when you call this.process()
 *
 * <p>Requirements:<br>
 *
 * <p>Usage:<br>
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
 * Note that this is the ONLY class in this package visible to other packages.
 * This restricted API should make it easier to use the pipeline<BR>

 @author Keith Perkins
 */
public class Image_Pipeline {

    //contains a list of all the image processors we will use
    private List<Processor> processor_list;

    //keep the builder around to adjust params
    private Builder b;

    private Image_Pipeline(Builder b) {
        processor_list = new ArrayList<Processor>();
        add(b.myCC);
        add(b.myED);
        add(b.myST);
        this.b = b;
    }

    private void add(Processor processor) {
        if (processor != null)
            processor_list.add(processor);
    }

    public Bitmap process(Bitmap bmp_original) {
        Bitmap bmp_new = bmp_original;
        for (Processor processor : processor_list) {
            bmp_new = processor.process(bmp_new);
        }
        return bmp_new;
    }

    //necessary evils if you want to adjust your pipeline
    public void setText(String myString) {
        if (b.myST != null)
            b.myST.setText(myString);
    }
    public void cycleColorShift() {
    //public void setColorShift() {
        if (b.myCC != null)
            b.myCC.cycleColorShift();
    }

    /**
     * @author keith builder pattern, see page 11 Effective Java UserData mydata
     */
    public static class Builder {
        private int width = Constants.UNINITIALIZED;
        private int height = Constants.UNINITIALIZED;
        private boolean isValidType = false;

        private EdgeDetect_Processor myED;
        private ColorChange_Processor myCC;
        private ShowText_Processor myST;

        //use a bitmap to define height width and type
        public Builder(Bitmap bmp) {
            this.width = bmp.getWidth();
            this.height = bmp.getHeight();
            if (bmp.getConfig() == Bitmap.Config.ARGB_8888)
                isValidType = true;
        }

        public void setText(String s) {
            myST.setText(s);
        }

        //NOTE Setters
        //each returns this builder
        //makes it suitable for chaining
        public Builder setEdgeDetect() {
            this.myED = new EdgeDetect_Processor(width, height);
            return this;
        }

        public Builder setColorChange() {
            this.myCC = new ColorChange_Processor(width, height);
            return this;
        }

        public Builder setShowText() {
            this.myST = new ShowText_Processor(width, height);
            return this;
        }

        //NOTE This is the only place where we can
        //construct a Image_Pipeline object
        //builder ensures that it is not partially constructed
        public Image_Pipeline build() {
            if (!isValidType)
                throw new IllegalArgumentException(
                        "The provided Bitmap is not ARGB_8888; this pipeline has only been tested with that type!");
            return new Image_Pipeline(this);
        }
    }
}

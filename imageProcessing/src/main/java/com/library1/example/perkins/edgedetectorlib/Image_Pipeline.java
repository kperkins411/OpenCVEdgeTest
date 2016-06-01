package com.library1.example.perkins.edgedetectorlib;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Perkins on 5/31/2016.
 */
public class Image_Pipeline {

    //contains a list of all the image processors we will use
    private List<Processor> processor_list;
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

    /**
     * @author lynn builder pattern, see page 11 Effective Java UserData mydata
     */
    public static class Builder {
        private int width = Constants.UNINITIALIZED;
        private int height = Constants.UNINITIALIZED;
        boolean isValidType = false;

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
        //construct a DEMOOptionalFields object
        //builder ensures that it is not partially constructed
        public Image_Pipeline build() {
            if (!isValidType)
                throw new IllegalArgumentException(
                        "The provided Bitmap was not ARGB_8888; this pipeline has only been tested with that type!");
            return new Image_Pipeline(this);
        }
    }
}

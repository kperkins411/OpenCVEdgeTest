# ImageProcessing and ImageProcessing_Tester
<p><B>Description:</b><br>
 Flexible image processing pipeline.  Creates a list of classes that implement the Processor interface
 And then executes each classes process(..) method in Image_Pipeline.Process()
 Add only the processors of interest. Has default processor implementations, also has pass through methods to 
 adjust individual processor parameters
 <p> Current processors are:  
 <p>     ColorChange_Processor
 <p>     EdgeDetect_Processor
 <p>     ShowText_Processor


<p><B>Requirements:Each processor has listed requirements<br>
 
 <p>Usage:<br>
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

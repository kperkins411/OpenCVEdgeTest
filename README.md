# ImageProcessing and ImageProcessing_Tester
<p><B>Description:</b><br>
<p> A class that performs a list of user selected transforms on bitmapss and a tester.
<p><b>ImageProcessing_Tester</b><br>
tester for ImageProcessing pipeline.  Lets you choose which filters(processors) will be applied to an image. Two views of the processed image   <br>
<img src="https://github.com/kperkins411/OpenCVEdgeTest/blob/master/Tester.png" width="200" height="200" />
<img src="https://github.com/kperkins411/OpenCVEdgeTest/blob/master/Tester1.png" width="200" height="200" />

<p><b>ImageProcessing</b> 
<B><p>Note that Image_Pipeline.java is the ONLY class in this package visible to other packages (the rest are package private). 
 This restricted API should make it easier to use since you have only one available class</B><BR>
 <p>Flexible image processing pipeline.  Creates a list of classes that implement the Processor interface
 And then executes each classes process(..) method in Image_Pipeline.Process().
 <p>Add only the processors of interest. Has default processor implementations, also has pass through methods to 
 adjust individual processor parameters
 <p> Current processors are:  
 <p>     ColorChange_Processor
 <p>     EdgeDetect_Processor
 <p>     ShowText_Processor
 
<p><B>Requirements:Each processor has listed requirements<br>
 
 <p>Usage: (see ImageProcessing_Tester->MainActivity-> dobutton_RunPipeline for this code<br>
 
 ![My image](https://github.com/kperkins411/OpenCVEdgeTest/blob/master/PipelineCode.png)


 

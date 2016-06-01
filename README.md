# ImageProcessing and ImageProcessing_Tester
<p><B>Description:</b><br>
<p><b>ImageProcessing_Tester</b> tester for ImageProcessing pipeline
![My image](https://github.com/kperkins411/OpenCVEdgeTest/blob/master/Tester.png)
<p><b>ImageProcessing</b> Flexible image processing pipeline.  Creates a list of classes that implement the Processor interface
 And then executes each classes process(..) method in Image_Pipeline.Process()
 Add only the processors of interest. Has default processor implementations, also has pass through methods to 
 adjust individual processor parameters
 <p> Current processors are:  
 <p>     ColorChange_Processor
 <p>     EdgeDetect_Processor
 <p>     ShowText_Processor
 
<p><B>Requirements:Each processor has listed requirements<br>
 
 <p>Usage: (see ImageProcessing_Tester->MainActivity-> dobutton_RunPipeline for this code<br>
 
 ![My image](https://github.com/kperkins411/OpenCVEdgeTest/blob/master/PipelineCode.png)


 * <B><U>Note that this is the ONLY class in this package visible to other packages.
 * This restricted API should make it easier to use the pipeline</B></U><BR>

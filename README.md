# OpenCVEdgeTest
<p> Class of interest is EdgeDetector.java which resides in edgedetectorlib library.
It takes an image, uses Canny to find edges then merges the found edges with original image, 
has sharpening effect on image

<p><B>Requirements:<br>
 You must use openCV for android in order to run this app.  See the following link for install help
 http://stackoverflow.com/questions/27406303/opencv-in-android-studio</B>
 <p>Usage:<br>
 EdgeDetector myDetector= new EdgeDetector();<br>
 Bitmap bmpOutlined = myDetector.outline_Bitmap(bmpOriginal);<br>

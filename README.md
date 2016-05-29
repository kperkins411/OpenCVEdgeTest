# OpenCVEdgeTest
Takes an image, uses Canny to find edges and store in Mat im_canny,
then merges the found edges with original image, has sharpening effect on image

<p><B>Requirements:<br>
 openCV for android see the following link for install help
 http://stackoverflow.com/questions/27406303/opencv-in-android-studio</B>
 <p>Usage:<br>
 EdgeDetector myDetector= new EdgeDetector();<br>
 Bitmap bmpOutlined = myDetector.outline_Bitmap(bmpOriginal);<br>

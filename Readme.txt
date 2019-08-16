1. download opencv:
   https://sourceforge.net/projects/opencvlibrary/files/opencv-android/3.3.0/

2. create new android project

3. integrate opencv
   follow this link from 04:00 (https://www.youtube.com/watch?v=VRLfzi5bdJs)
   (in 8th step copy all the libs)
   (you can skip 11th and 12th step by simply downloading Android studio's own NDK and by giving path of that ndk in local.properties)
   
4. make/build project

5. error
   -go to 'file'
   -click on 'link c++ projectt with gradle'
   -change build system from cmake to ndk build
   -browse this path from the current project 'build/intermediates/ndk/debug/Android.mk'
   -make/build project
   
6. the code for drowsiness detection is present in 'main2Activity'



object detection:

the code for object detection is present in "object" activity.
  
1. integrate opencv as above

2. Download MobileNet object detection model from https://github.com/chuanqi305/MobileNet-SSD. 
   We need a configuration file MobileNetSSD_deploy.prototxt and weights MobileNetSSD_deploy.caffemodel.

3. Put downloaded MobileNetSSD_deploy.prototxt and MobileNetSSD_deploy.caffemodel into app/build/intermediates/assets/debug folder.

4. make changes in android manifest, object class and xml file.
   

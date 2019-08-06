Real-time Driver Alert Mobile Application Based on Drowsiness detectiion and Pedestrian Detection Using Computer Vision techniques such as image processing, Template matching for tracking and object detection through OpenCV Library, HAAR cascades algorithms and Mobile net ssd.


Objective: 2 modules
   
   This Application is designed for the avoidance of accidents and to assist the driver while driving by 
1. detecting driver’s drowsiness through front camera
2. pedestrians detection through back camera that may come in front of the car and become a reason for an accident. 


Tools Used:

Android Studio 3.1.0

gradle:3.1.3

Android Device: Samsung galaxy grand prime 

SQLite Data Base of Android Studio

OpenCV 3.3.0 Version



Working Steps of drowsiness detection:

The system continuously captures image frames from real time video stream using the mobiles camera(image frames are not saved in database).
Calculate and continuously update location of driver through gps provider without internet

step 1.  Face and Eyes Region Detection
         (Iris is detected as the darkest point in the eye area)
         
step 2.  Match eye state indicating open or close or blink 

step 3.  No. of frames in which eyes are closed to detect Drowsiness is >2

step 4.  Generate alarm
         (Alarm continues to ring until the user eyes are closed. The alarm stops automatically when user opens his eyes and drowsiness                  detection will be started again) 
         
step 5.  Sends message along with location of driver to two recognizable people of driver if alarm  is not stopped
         (timer schedule for sending message is 5000 millisecs)



#import the necessary packages
import numpy as np
import cv2
import argparse
import os
import imutils

# load the games image
image = cv2.imread("~/posphotos/foo.jpeg")
#camera = cv2.VideoCapture(0)
#loop
while True:
    #grabs current frame
    #(grabbed, frame) = camera.read()
    print "start\n"
    print image
    print "stop\n"

    #resizes video
    image = imutils.resize(image, width = 300)
    
    #resizes image
    #r = 500.0 / frame.shape[1]
    #dim = (500, int(frame.shape[0] * r))
 

    #frame = cv2.resize(frame, dim, interpolation = cv2.INTER_AREA)
    #cv2.imshow("frame", frame)
    #cv2.waitKey(0)
    # find the red color game in the image
    upper = [200,250,0]
    lower = [20,180,0]

    upper = np.array(upper,dtype = "uint8")
    lower = np.array(lower,dtype = "uint8")
    mask = cv2.inRange(image, lower, upper)
 
    #find contours in the masked frame and keep the largest one
    (_, cnts, _) = cv2.findContours(mask.copy(), cv2.RETR_EXTERNAL,
        cv2.CHAIN_APPROX_SIMPLE)

    if len(cnts) !=0:
        c = max(cnts, key=cv2.contourArea)

        leftmost = tuple(c[c[:,:,0].argmin()][0])
        rightmost = tuple(c[c[:,:,0].argmax()][0])
        topmost = tuple(c[c[:,:,1].argmin()][0])
        bottommost = tuple(c[c[:,:,1].argmax()][0])
        print leftmost
        print rightmost
        print topmost
        print bottommost
        # approximate the contour
        peri = cv2.arcLength(c, True)
        approx = cv2.approxPolyDP(c, 0.05 * peri, True)
 
        # draw a green bounding box surrounding the red game
        cv2.drawContours(image, [approx], -1, (255, 0, 0), 4)
    cv2.imshow("Image", image)
    cv2.waitKey(1)

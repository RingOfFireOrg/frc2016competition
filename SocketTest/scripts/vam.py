#import the necessary packages
import numpy as np
import cv2
import argparse
import os
import imutils
import random
import sys
from PIL import Image
from flask import Flask, render_template, Response
from camera import VideoCamera

#setup for video feed

#setting MinMax values for tape postion

leftxmin = 140
leftxmax = 167

rightxmin = 191
rightxmax = 218

topxmin = 150
topxmax = 212

bottomxmin = 142
bottomxmax = 214


leftymin = 155
leftymax = 214

rightymin = 150
rightymax = 223

topymin = 149
topymax = 209

bottomymin = 176
bottomymax = 223

factormin = 1000
factormax = 3000


#making thresholds

thresh = 0



def threshhigh( val ):
    return val - thresh



def threshlow( val ):
    return val + thresh



def threshcheck(left, right, top, bottom):

    factor = (right[0] - left[0]) * (top[0] - bottom[0])
    if (left[0] >= threshlow(leftxmin) and
       left[0] <= threshhigh(leftxmax) and
       right[0] >= threshlow(rightxmin) and
       right[0] <= threshhigh(rightxmax) and
       top[0] >= threshlow(topxmin) and
       top[0] <= threshhigh(topxmax) and
       bottom[0] >= threshlow(bottomxmin) and
       bottom[0] <= threshhigh(bottomxmax) and
       left[1] >= threshlow(leftymin) and
       left[1] <= threshhigh(leftymax) and
       right[1] >= threshlow(rightymin) and
       right[1] <= threshhigh(rightymax) and
       top[1] >= threshlow(topymin) and
       top[1] <= threshhigh(topymax) and
       bottom[1] >= threshlow(bottomymin) and
       bottom[1] <= threshhigh(bottomymax) and
       factor <= factormax and
       factor >= factormin):
         return True
    else:
        return False


#frame = cv2.imread(sys.argv[1])

# load the games image
#image = cv2.imread("tower.jpeg")
camera = cv2.VideoCapture(0)
#loop
while True:

    #grabs current frame
    (grabbed, frame) = camera.read()


    #resizes video
    frame = imutils.resize(frame, width = 300)

    frame = imutils.rotate(frame, 180)

    # find the red color game in the image
    upper = [200,250,0]
    lower = [20,180,0]

    upper = np.array(upper,dtype = "uint8")
    lower = np.array(lower,dtype = "uint8")
    mask = cv2.inRange(frame, lower, upper)
    #find contours in the masked frame and keep the largest one
    (_, cnts, _) = cv2.findContours(mask.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

    leftmost = (0, 0)
    rightmost = (0, 0)
    topmost = (0, 0)
    bottommost = (0, 0)

    if len(cnts) !=0:
        c = max(cnts, key=cv2.contourArea)

        leftmost = tuple(c[c[:,:,0].argmin()][0])
        rightmost = tuple(c[c[:,:,0].argmax()][0])
        topmost = tuple(c[c[:,:,1].argmin()][0])
        bottommost = tuple(c[c[:,:,1].argmax()][0])
        # approximate the contour
        peri = cv2.arcLength(c, True)
        approx = cv2.approxPolyDP(c, 0.05 * peri, True)
 
        # draw a green bounding box surrounding the red game
        cv2.drawContours(frame, [approx], -1, (255, 0, 0), 4)

    cv2.imwrite('/home/pi/ramdisk/nextframe.jpeg', frame)
    os.rename('/home/pi/ramdisk/nextframe.jpeg','/home/pi/ramdisk/currentframe.jpeg')


    #  decide if it's a hit or not
    anwser = threshcheck(leftmost, rightmost, topmost, bottommost)


    #  write out the decision to a file, using a lock
    if anwser:
        wrfile = open("/home/pi/ramdisk/wrfile.txt", "w")
        wrfile.write("Hit\n")
        wrfile.close()

    else:
        wrfile = open("/home/pi/ramdisk/wrfile.txt", "w")
        wrfile.write("Miss\n")
        wrfile.close()
        
    os.rename('/home/pi/ramdisk/wrfile.txt','/home/pi/ramdisk/rdfile.txt')
    

    # see the result locally
    #   cv2.imshow("Frame", frame)
    cv2.waitKey(1)
    #  start over in the loop

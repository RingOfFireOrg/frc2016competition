#import the necessary packages
import numpy as np
import cv2
import argparse
import os
import imutils
import random
from flask import Flask, render_template, Response
from camera import VideoCamera

# load the games image
#image = cv2.imread("tower.jpeg")
camera = cv2.VideoCapture(0)
jpegframe = 0
#loop
while True:
    #grabs current frame
    (grabbed, frame) = camera.read()

    print frame

    #resizes video
    frame = imutils.resize(frame, width = 300)
    
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
    mask = cv2.inRange(frame, lower, upper)
 
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
        cv2.drawContours(frame, [approx], -1, (255, 0, 0), 4)

    ret, jpeg = cv2.imencode('.jpg', frame)
    framejpeg = jpeg.tobytes()

    cv2.imshow("Frame", frame)
    cv2.waitKey(1)
    #  decide if it's a hit or not

    hit = random.random()

    if hit > 0.5:
        wrfile = open("wrfile.txt", "w")
        wrfile.write("Testing123\n")

        wrfile.close()

    else:
        wrfile = open("wrfile.txt", "w")
        wrfile.write("Testing...\n")

        wrfile.close()

        
    os.rename('wrfile.txt','rdfile.txt')

    #  write out the decision to a file, using a lock
    #  start over in the loop

	#  streams video
    app = Flask(__name__)

    @app.route('/')
    def index():
        return render_template('index.html')

    def gen(camera):
        while True:
            frame = camera.get_frame()
            yield (b'--frame\r\n'
                b'Content-Type: image/jpeg\r\n\r\n' + frame + b'\r\n\r\n')
 
    @app.route('/video_feed')
    def video_feed():
        return Response(framejpeg,
                         mimetype='multipart/x-mixed-replace; boundary=frame')

 
    if __name__ == '__main__':
        app.run(host='0.0.0.0', debug=True)
 

#import the necessary packages
import numpy as np
import cv2
import argparse
import os
import imutils
import random
from PIL import Image
from flask import Flask, render_template, Response, send_file
from camera import VideoCamera

app = Flask(__name__)

@app.route('/')
def index():
    return render_template('index.html')

def getframe():
     # get a frame that is whatever is in "currentframe.jpeg" at the moment
     file = open('currentframe.jpeg', 'rb')
     frame = file.read()
     file.close()
     return frame

def gen():
    while True:
        frame = getframe()
        # yield is a crazy thing that python can do. If a function calls 
        # yield it really just puts the result in a queue or list that will
        # be read by the caller as if it returned, but then the function 
        # keeps going. weird... Here we are creating a list of frames read 
        # from a file whose name stays constant but whose contents are 
        # being replaced by find_game.py every frame. probably need to move
        # to a ramdisk to speed it all up, all the "--frame and content type 
        # stuff is there to trick the browser into loading new frames in a
        # hurry
        yield (b'--frame\r\n'
               b'Content-Type: image/jpeg\r\n\r\n' + frame + b'\r\n\r\n')

@app.route('/video_feed')
def video_feed():
    return Response(gen(),
                     mimetype='multipart/x-mixed-replace; boundary=frame')
#     return send_file('currentframe.jpeg')

if __name__=='__main__':
    print "I am in main\n"
    app.run(host='0.0.0.0',port=5800,debug=True)

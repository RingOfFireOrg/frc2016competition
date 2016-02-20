# import the necessary packages
import numpy as np
import cv2
import argparse
 
# load the games image
image = cv2.imread("tower.jpeg")

#resizes the image
r = 500.0 / image.shape[1]
dim = (500, int(image.shape[0] * r))
 

resized = cv2.resize(image, dim, interpolation = cv2.INTER_AREA)
#cv2.imshow("resized", resized)
#cv2.waitKey(0)
# find the red color game in the image
upper = [200,250,0]
lower = [20,180,0]

upper = np.array(upper,dtype = "uint8")
lower = np.array(lower,dtype = "uint8")
mask = cv2.inRange(resized, lower, upper)
 
# find contours in the masked image and keep the largest one
(_, cnts, _) = cv2.findContours(mask.copy(), cv2.RETR_EXTERNAL,
	cv2.CHAIN_APPROX_SIMPLE)
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
cv2.drawContours(resized, [approx], -1, (255, 0, 0), 4)
cv2.imshow("Resized", resized)
cv2.waitKey(0)

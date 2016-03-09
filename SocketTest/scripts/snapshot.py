#!/home/pi/.virtualenvs/cv3/bin/python

import os
import sys
import tempfile 

tempfilename = tempfile.mktemp() 
speed = sys.argv[1]
filename = speed + "_test_" + tempfilename + ".jpeg" 

filename = filename.replace("/tmp/","")
filename = filename.replace("tmp","")

print filename

#raspistill -o filename
os.system("raspistill -o " +  filename)


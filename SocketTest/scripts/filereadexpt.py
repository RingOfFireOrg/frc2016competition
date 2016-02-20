#!/c/Python27/python
import os

# Open a file
fo = open("bar.txt", "r")
msg = fo.read()
print "msg:", msg
# Close opend file
fo.close()

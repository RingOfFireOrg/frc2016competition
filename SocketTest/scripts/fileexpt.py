#!/c/Python27/python
import os

wrfile = open("wrfile.txt", "w")
wrfile.write("Testing...\n")

wrfile.close()

os.rename('wrfile.txt','rdfile.txt')
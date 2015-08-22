# -*- encoding: UTF-8 -*- 
# This test demonstrates how to use the ALFaceDetection module.
# Note that you might not have this module depending on your distribution
#
# - We first instantiate a proxy to the ALFaceDetection module
#     Note that this module should be loaded on the robot's naoqi.
#     The module output its results in ALMemory in a variable
#     called "FaceDetected"

# - We then read this ALMemory value and check whether we get
#   interesting things.

import time
import sys

from naoqi import ALProxy

IP = "nao.local"  # Replace here with your NaoQi's IP address.
PORT = 9559

if (len(sys.argv) < 2):
  print "usage: python face.py face_name"
  exit(0)

faceName = sys.argv[1]

# Create a proxy to ALFaceDetection
try:
  faceProxy = ALProxy("ALFaceDetection", IP, PORT)
except Exception, e:
  print "Error when creating face detection proxy:"
  print str(e)
  exit(1)

# Subscribe to the ALFaceDetection proxy
# This means that the module will write in ALMemory with
# the given period below
period = 500
faceProxy.subscribe("Test_Face", period, 0.0 )

# ALMemory variable where the ALFacedetection modules
# outputs its results
memValue = "FaceDetected"

# Create a proxy to ALMemory
try:
  memoryProxy = ALProxy("ALMemory", IP, PORT)
except Exception, e:
  print "Error when creating memory proxy:"
  print str(e)
  exit(1)


num = 0

# A simple loop that reads the memValue and checks whether faces are detected.
while True:
  time.sleep(0.5)
  val = memoryProxy.getData(memValue)

  print ""
  print "*****"
  print ""

  # Check whether we got a valid output.
  if(val and isinstance(val, list) and len(val) >= 2):

    # We detected faces !
    # For each face, we can read its shape info and ID.
    print "We detected faces !"

    # First Field = TimeStamp.
    timeStamp = val[0]

    # Second Field = array of face_Info's.
    faceInfoArray = val[1]

    try:
      # Browse the faceInfoArray to get info on each detected face.
      print "si acum caut fetze ", len(faceInfoArray)-1
      for j in range( len(faceInfoArray)-1 ):
        if faceProxy.learnFace(faceName):
          print "face ", faceName, " saved!"
        else:
          print "Failed to save ", faceName 
        faceInfo = faceInfoArray[j]

        # First Field = Shape info.
        faceShapeInfo = faceInfo[0]

        # Second Field = Extra info (empty for now).
        faceExtraInfo = faceInfo[1]

        print "  alpha %.3f - beta %.3f" % (faceShapeInfo[1], faceShapeInfo[2])
        print "  width %.3f - height %.3f" % (faceShapeInfo[3], faceShapeInfo[4])
      break
    except Exception, e:
      print "faces detected, but it seems getData is invalid. ALValue ="
      print val
      print "Error msg %s" % (str(e))
  else:
    print "No face detected"

# Unsubscribe the module.
faceProxy.unsubscribe("Test_Face")

print "Test terminated successfully."

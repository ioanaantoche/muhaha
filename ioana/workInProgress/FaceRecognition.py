import time
import sys

from naoqi import ALProxy
from description import *
import threading

IP = "nao.local"  # Replace here with your NaoQi's IP address.
PORT = 9559
#FaceDetected =
#    [
#      TimeStamp,
#      [ FaceInfo[N], Time_Filtered_Reco_Info ], -> recognisedFacesList
#      CameraPose_InTorsoFrame,
#      CameraPose_InRobotFrame,
#      Camera_Id
#    ]
class FaceDetected:
    def __init__(self, val):
        self.TimeStamp = TimeStamp(val[0])
        self.RecognisedFacesList = RecognisedFaces(val[1])
        self.CameraPose_InTorsoFrame = val[2]
        self.CameraPose_InRobotFrame = val[3]
        self.Camera_Id = val[4]
#TimeStamp =
#[
#  TimeStamp_Seconds,
#  Timestamp_Microseconds
#]
class TimeStamp:
    def __init__(self, val):
        self.TimeStamp_Seconds = val[0]
        self.Timestamp_Microseconds = val[1]

class RecognisedFaces:
    def __init__(self, faceInfoArray):
        self.RecognisedFacesList = []
        for i in xrange(len(faceInfoArray)-1):
            self.RecognisedFacesList.append(FaceInfo(faceInfoArray[i]))
        self.Time_Filtered_Reco_Info = faceInfoArray[len(faceInfoArray)-1]

#FaceInfo =
#[
#  ShapeInfo,
#  ExtraInfo[N]
#]
class FaceInfo:
    def __init__(self, faceInfo):
        self.ShapeInfo = ShapeInfo(faceInfo[0])
        self.ExtraInfo = ExtraInfo(faceInfo[1])

#ShapeInfo =
#[
#  0,
#  alpha,
#  beta,
#  sizeX,
#  sizeY
#]
class ShapeInfo:
    def __init__(self, val):
        self.alpha = val[1]
        self.beta = val[2]
        self.sizeX = val[3]
        self.sizeY = val[4]

#ExtraInfo =
#[
#  faceID,
#  scoreReco,
#  faceLabel,
#  leftEyePoints,
#  rightEyePoints,
#  leftEyebrowPoints,
#  rightEyebrowPoints,
#  nosePoints,
#  mouthPoints
#]
# Deocamdata nu ma intereseaza toate punctele
class ExtraInfo:
    def __init__(self, val):
        self.faceID = val[0]
        self.scoreReco = val[1]
        self.faceLabel = val[2]
        
class FaceRecognitionHandler(threading.Thread):
    faceProxy = None
    memoryProxy = None
    
    def __init__(self, faceProxy = ALProxy("ALFaceDetection", IP, PORT),
        memoryProxy = ALProxy("ALMemory", IP, PORT),
        presenter = PresentImage()):
        super(FaceRecognitionHandler, self).__init__()
        self.faceProxy = faceProxy
        self.memoryProxy = memoryProxy
        self.presenter = presenter
        self.language = "English"
        self.isRunning = False
        self.faces = []
        self.speaking = False

    def isUniversalFaceDetected(self, val):
        return (val and isinstance(val, list) and len(val) >= 2)

    #@input: FaceInfo
    def getRecognitionScore(self, faceInfo):
        return faceInfo.ExtraInfo.scoreReco

    #@input: FaceInfo
    def getRecognitionName(self, faceInfo):
        return faceInfo.ExtraInfo.faceLabel

    # returneaza o lista cu toate numele fetelor recunoscute
    def faceDetection(self):
        memValue = "FaceDetected"
        period = 500
        self.faceProxy.subscribe("Test_Face", period, 0.0 )
        val = self.memoryProxy.getData(memValue)
        # astept sa detectez o fata
        while not self.isUniversalFaceDetected(val):
            time.sleep(0.5)
            val = self.memoryProxy.getData(memValue)

        # am detectat cel putin o fata:
        fd = FaceDetected(val)
        recognisedFaces = []
        for faceInfo in fd.RecognisedFacesList.RecognisedFacesList:
            if len(self.getRecognitionName(faceInfo)) != 0:
                recognisedFaces.append(self.getRecognitionName(faceInfo))
        self.faceProxy.unsubscribe("Test_Face")
        return recognisedFaces

    def startFaceDetection(self):
        self.isRunning = True
        while self.isRunning:
            self.faces = self.faceDetection()
            if len(self.faces) > 0:
                self.speaking = True
                print "Faces were found"
                for self.face in self.faces:
                    fileName = self.language + "/" + str(constants.peopleFaces[self.face]) + ".txt"
                    self.presenter.present(fileName, 'utf-8', self.language)
                self.isRunning = False
                self.speaking = False

    def run(self):
        self.startFaceDetection()

if __name__ == '__main__':
    f = FaceRecognitionHandler(ALProxy("ALFaceDetection", IP, PORT), ALProxy("ALMemory", IP, PORT))
    while True:
        l = f.faceDetection()
        if len(l) > 0:
            print l

        





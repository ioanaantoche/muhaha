import BaseHTTPServer
import thread
import threading
import urlparse
import string
import time
from move import Move
from move import MoveInfo
from description import *
from FaceRecognition import FaceRecognitionHandler
from robotstatus import Status
from constants import *
import codecs
import math

move = Move()
status = Status()
description = PresentImage()



def sendResponse(s, code, message):
  print "... ", s.path
  s.send_response(code)
  s.send_header("Content-type", "text")
  s.end_headers()
  m = message
  s.wfile.write(m)

def hello_handler():
    pass
def handlerMoveBackward(move, nrSteps):
    move.moveBackward(nrSteps)

def handlerMoveForward(move, nrSteps):
    move.moveForward(nrSteps)

def handlerMoveRight(move, nrSteps):
    move.moveRight(nrSteps)

def handlerMoveLeft(move, nrSteps):
    move.moveLeft(nrSteps)

def handlerTurnLeft(move, dummy):
    move.turnLeft()    

def handlerTurnRight(move, dummy):
    move.turnRight()

def handlerExecute(move):
    move.turnRight()

def executeGenericMove(move, moveInfo):
    move.executeGenericMove(moveInfo)

def executeGenericTurn(move, moveInfo):
    move.executeGenericTurn(moveInfo)

def handlerPosture(move, posture):
    move.goToPosture(posture)

def handlerPersonalities(description,personalities):
    description.present("English/" + personalities + ".txt", 'utf-8', "English")

class StoppableThread(threading.Thread):

    def __init__(self, description):
        super(StoppableThread, self).__init__()
        self.running = False
        self.faceRecogn = FaceRecognitionHandler()
        self.motion = ALProxy("ALMotion", "nao.local", 9559)
        self.postureProxy = ALProxy("ALRobotPosture", "nao.local", 9559)

    def run(self):
        num = 0
        self.faceRecogn.start()
        while self.running:
            #print "here"
            if num % 2 == 0:
                sensRotireCap = 1
                num = 1
            else:
                sensRotireCap = -1
                num = 0
            #while self.faceRecogn.speaking:
            #    pass
            self.motion.moveTo(0.1, 0, 0,
                [ ["MaxStepX", 0.02],         # step of 2 cm in front
                  ["MaxStepY", 0.04],         # default value
                  ["MaxStepTheta", 0.4],      # default value
                  ["MaxStepFrequency", 0.0],  # low frequency
                  ["StepHeight", 0.01],       # step height of 1 cm
                  ["TorsoWx", 0.0],           # default value
                  ["TorsoWy", 0.1] ])         # torso bend 0.1 rad in front
            #while self.faceRecogn.speaking:
            #    pass
            self.motion.angleInterpolation(
                ["HeadYaw", ],
                [math.radians(90*sensRotireCap)],
                [15],
                True)
            time.sleep(3)
            print "Search again"
            self.faceRecogn.isRunning = self.faceRecogn.isRunning & self.running
        self.postureProxy.goToPosture("Crouch", 0.5)

    def reset(self):
        self.running = False

    def set(self):
        self.running = True

faceRecognThread = StoppableThread(description)  

class MainHandler(BaseHTTPServer.BaseHTTPRequestHandler):

    def do_HEAD(self):
        s.send_response(200)
    def do_GET(self):
        """Respond to a GET request."""
        global move
        global description
        global faceRecognThread
        global status
        if self.path == "/hello":
            try:
                thread.start_new_thread(hello_handler,())
                self.send_response(200)
            except:
                print "Error: cannot start the thread"
        url = self.path
        parsed = urlparse.urlparse(url)
        if string.find(self.path,"/faceRecognition") != -1:
            action = urlparse.parse_qs(parsed.query)['action'][0]
            if string.find(action,"start") != -1:
                move.StiffnessOn()
                faceRecognThread.set()
                faceRecognThread.start()
                print "start face recogn"
                sendResponse(self, 200, "start face recogn")
            else:
                faceRecognThread.reset()
                faceRecognThread = StoppableThread(description)
                print "stop face recogn ", faceRecognThread.running
                move.StiffnessOff()
                sendResponse(self, 200, "stop face recogn")
        if string.find(self.path,"/personality") != -1:
            thread.start_new_thread(handlerPersonalities, (description, urlparse.parse_qs(parsed.query)['id'][0]))
            sendResponse(self, 200, "personality")
        if string.find(self.path,"/posture") != -1:
            thread.start_new_thread(handlerPosture, (move, urlparse.parse_qs(parsed.query)['posture'][0]))
            sendResponse(self, 200, "posture")
        if string.find(self.path,"/moveBackward") != -1:
            nrSteps = 0
            nrSteps = int(urlparse.parse_qs(parsed.query)['nrSteps'][0])
            thread.start_new_thread(handlerMoveBackward, (move, nrSteps))
            sendResponse(self, 200, "handlerMoveBackward")
            return
        if string.find(self.path,"/moveForward") != -1:
            nrSteps = 0
            nrSteps = int(urlparse.parse_qs(parsed.query)['nrSteps'][0])
            thread.start_new_thread(handlerMoveForward, (move, nrSteps))
            sendResponse(self, 200, "")
            return
        if string.find(self.path,"/moveRight") != -1:
            nrSteps = 0
            nrSteps = int(urlparse.parse_qs(parsed.query)['nrSteps'][0])
            thread.start_new_thread(handlerMoveRight, (move, nrSteps))
            sendResponse(self, 200, "")
            return
        if string.find(self.path,"/moveLeft") != -1:
            nrSteps = 0
            nrSteps = int(urlparse.parse_qs(parsed.query)['nrSteps'][0])
            thread.start_new_thread(handlerMoveLeft, (move, nrSteps))
            sendResponse(self, 200, "")
            return
        if string.find(self.path,"/turnLeft") != -1:
            thread.start_new_thread(handlerTurnLeft, (move, 0))
            sendResponse(self, 200, "")
            return
        if string.find(self.path,"/turnRight") != -1:
            thread.start_new_thread(handlerTurnRight, (move, 0))
            sendResponse(self, 200, "")
            return
        if string.find(self.path,"/executeMove") != -1:    
            nrSteps = int(urlparse.parse_qs(parsed.query)['nrSteps'][0])
            x = float(urlparse.parse_qs(parsed.query)['x'][0])
            y = float(urlparse.parse_qs(parsed.query)['y'][0])
            tetha = float(urlparse.parse_qs(parsed.query)['tetha'][0])
            speed = float(urlparse.parse_qs(parsed.query)['speed'][0])
            component = urlparse.parse_qs(parsed.query)['component'][0]
            moveInfo = MoveInfo(component, x, y, tetha, speed, nrSteps)
            thread.start_new_thread(executeGenericMove, (move, moveInfo))
            sendResponse(self, 200, "")
            return
        if string.find(self.path,"/motorsOff") != -1:
            print "motorsOff"
            move.StiffnessOff()
            sendResponse(self, 200, "")
            return
        if string.find(self.path,"/motorsOn") != -1:
            move.StiffnessOn()
            sendResponse(self, 200, "")
            return
        if string.find(self.path,"/batteryStatus") != -1:
            sendResponse(self, 200, str(status.getBatteryStatus()) + '\n')
            return
        if string.find(self.path,"/CPUTemperature") != -1:
            sendResponse(self, 200, str(status.getCPUTemperature()) + '\n')
            return
        if string.find(self.path,"/getSpeakerVolume") != -1:
            sendResponse(self, 200, str(status.getSpeakerVolume()) + '\n')
            return
        if string.find(self.path,"/setSpeakerVolume") != -1:
            x = int(urlparse.parse_qs(parsed.query)['volume'][0])
            status.setSpeakerVolume(x)
            sendResponse(self, 200, "")
            return
        if string.find(self.path,"/turn") != -1:
            print "turn"
            x = float(urlparse.parse_qs(parsed.query)['x'][0])
            y = float(urlparse.parse_qs(parsed.query)['y'][0])
            tetha = float(urlparse.parse_qs(parsed.query)['tetha'][0])
            moveInfo = MoveInfo(None, x, y, tetha, None, None)
            thread.start_new_thread(executeGenericTurn, (move, moveInfo))
            sendResponse(self, 200, "")
            return


